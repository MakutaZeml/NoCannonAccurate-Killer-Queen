package com.zeml.rotp_zkq.action.stand.BitesZaDust;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.RemoveHayatoPacket;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;

public class RemoveHayato extends StandAction {
    public RemoveHayato(StandAction.Builder builder) {
        super(builder);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            LivingEntity living = BitesZaDustHandler.userToVictim.get(user);
            BitesZaDustHandler.victimToUser.remove(living);
            BitesZaDustHandler.userToVictim.remove(user);
            int resolve = power.getResolveLevel();
            MCUtil.runCommand(user,"stand clear @s");
            MCUtil.runCommand(user,"stand give @s rotp_zkq:killer_queen");
            power.setResolveLevel(resolve);
            BitesZaDustHandler.playerAndItsDeath.forEach(((playerEntity, longLivingEntityMap) -> {
                longLivingEntityMap.forEach((aLong, livingEntity) -> {
                    if(livingEntity == user){
                        longLivingEntityMap.remove(aLong);
                    }
                });
            }));
        }
        if(user instanceof ServerPlayerEntity){
            AddonPackets.sendToClient(new RemoveHayatoPacket((user.getId())),(ServerPlayerEntity) user);

        }
    }
}
