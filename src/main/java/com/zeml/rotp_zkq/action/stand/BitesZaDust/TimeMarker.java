package com.zeml.rotp_zkq.action.stand.BitesZaDust;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.TimeMarkPacket;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.block.BedBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimeMarker extends StandAction {
    public TimeMarker(StandAction.Builder builder) {
        super(builder);
    }



    /*
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if(power.getUser() != null && BitesZaDustHandler.userToVictim.containsKey(power.getUser())){
            return InitStands.QUIT_VICTIM.get();
        }else if(BitesZaDustHandler.userToTime.containsKey(power.getUser().getName().getString())){
            return InitStands.KQ_RESET.get();
        }
        return super.replaceAction(power, target);
    }
     */


    @Nullable
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if(hasTimeMark(power)){
            if(BitesZaDustHandler.userToVictim.containsKey(power.getUser())){
                return InitStands.QUIT_VICTIM.get();
            }
            return InitStands.KQ_RESET.get();
        }
        return super.replaceAction(power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        System.out.println("perform start");
        long time = world.getDayTime();
        if(!world.isClientSide){
            LazyOptional<LivingData> livingDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
            livingDataOptional.ifPresent(livingData -> {
                livingData.setHasTimeMark(true);
                livingData.setTimeMark(time);
            });



            System.out.println(power.getAllUnlockedActions());


            ((ServerWorld) world).players().forEach(player -> {
                System.out.println(player);
                Map<LivingEntity, ServerWorld> dimMap = new HashMap<>();
                dimMap.put(player, (ServerWorld) player.level);
                BitesZaDustHandler.bombDimensionAtMark.put(time, dimMap);
                Map<LivingEntity, Vector3d> vectorMap = new HashMap<>();
                vectorMap.put(player,player.position());
                BitesZaDustHandler.bombPositionAtMark.put(time,vectorMap);
            });
        }
        if(user instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new TimeMarkPacket(user.getId(),time),(ServerPlayerEntity) user);
        }
    }

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] {InitStands.KQ_RESET.get(), InitStands.KQ_REMOVE_MARK.get(),InitStands.PUT_VICTIM.get(), InitStands.QUIT_VICTIM.get()};
    }

    public boolean hasTimeMark(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getHasTimeMarker).isPresent()? livingDataOptional.map(LivingData::getHasTimeMarker).get():false;

    }
}
