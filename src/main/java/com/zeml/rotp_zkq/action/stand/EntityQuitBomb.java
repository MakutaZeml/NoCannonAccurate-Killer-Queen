package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.action.stand.punch.PunchBomb;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.RemoveBombPacket;
import com.zeml.rotp_zkq.network.server.RemoveTagPacket;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class EntityQuitBomb extends StandEntityAction {
    public EntityQuitBomb(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if(!world.isClientSide){
            Double range = 3*standEntity.getMaxRange();
            Entity entity = PunchBomb.EntityRange(userPower,range*3);
            if(entity!= null && userPower.getUser() != null){
                GameplayHandler.userToBomb.remove(userPower.getUser());
            }
        }
        LivingEntity user = userPower.getUser();
        if(user instanceof  ServerPlayerEntity){
            AddonPackets.sendToClient(new RemoveBombPacket(user.getId()),(ServerPlayerEntity) user);
        }
    }
}
