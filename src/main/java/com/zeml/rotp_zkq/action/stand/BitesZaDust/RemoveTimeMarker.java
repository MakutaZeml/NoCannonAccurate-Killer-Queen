package com.zeml.rotp_zkq.action.stand.BitesZaDust;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.init.InitSounds;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.RemoveBombPacket;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RemoveTimeMarker extends StandAction {
    public RemoveTimeMarker(StandAction.Builder builder) {
        super(builder);
    }

    @Nullable
    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if(target.getEntity() instanceof LivingEntity){
            return InitStands.PUT_VICTIM.get();
        }
        return super.replaceAction(power, target);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            BitesZaDustHandler.userToTime.remove(user.getName().getString());
        }
        if(user instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new RemoveBombPacket(user.getId()),(ServerPlayerEntity) user);

        }
    }

    @Override
    public void stoppedHolding(World world, LivingEntity user, IStandPower power, int ticksHeld, boolean willFire) {
        if(willFire){
            world.playSound(null,user.blockPosition(), InitSounds.KQ_BITES_ZA_DUST.get(),SoundCategory.PLAYERS,1F,1F);
        }
    }
}
