package com.zeml.rotp_zkq.action.stand.BitesZaDust;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Dimension;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class ResetDay extends StandEntityAction {
    long timer;
    public ResetDay(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        long time = BitesZaDustHandler.userToTime.get(userPower.getUser().getName().getString());
        this.timer = time;
        if(!world.isClientSide){
            MCUtil.entitiesAround(PlayerEntity.class,userPower.getUser(),100,true, EntityPredicates.ENTITY_STILL_ALIVE)
                    .forEach(living->{
                        Vector3d vector3d = BitesZaDustHandler.bombPositionAtMark.get(time).get(living);
                        living.teleportTo(vector3d.x,vector3d.y,vector3d.z);
                    });
            ((ServerWorld)world).setDayTime(time);
            userPower.getUser().setHealth(userPower.getUser().getMaxHealth());
        }else {
            ((ClientWorld)world).setDayTime(time);
        }
    }

}
