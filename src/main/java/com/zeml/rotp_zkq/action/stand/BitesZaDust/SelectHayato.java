package com.zeml.rotp_zkq.action.stand.BitesZaDust;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.SelectHayatoPacket;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class SelectHayato extends StandAction {
    public SelectHayato(StandAction.Builder builder) {
        super(builder);
    }

    @Nullable
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if(power.getUser() != null && BitesZaDustHandler.userToVictim.containsKey(power.getUser())){
            return InitStands.QUIT_VICTIM.get();
        }
        return super.replaceAction(power, target);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target){
        AtomicBoolean standUser = new AtomicBoolean(false);
        if(target.getEntity() instanceof LivingEntity){
            if(BitesZaDustHandler.victimToUser.containsKey((LivingEntity) target.getEntity())){
                return conditionMessage("bites_za_has");
            }
            IStandPower.getStandPowerOptional((LivingEntity) target.getEntity()).ifPresent(power1 -> {
                standUser.set(power1.hadAnyStand());
            });
            if(!standUser.get()){
                return ActionConditionResult.POSITIVE;

            }else {
                return conditionMessage("is_stand_user");
            }
        }
        return ActionConditionResult.NEGATIVE;
    }

    @Override
    public void onPerform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        LivingEntity living = (LivingEntity) target.getEntity();
        if(!world.isClientSide){
            BitesZaDustHandler.userToVictim.put(user,living);
            BitesZaDustHandler.victimToUser.put(living,user);
            System.out.println(BitesZaDustHandler.victimToUser);
        }
        if(user instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new SelectHayatoPacket(user.getId(),living.getId()),(ServerPlayerEntity) user);
        }
    }

}
