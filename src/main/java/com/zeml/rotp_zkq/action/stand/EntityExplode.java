package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.action.stand.punch.PunchBomb;
import com.zeml.rotp_zkq.network.server.RemoveBombPacket;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.RemoveTagPacket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;


public class EntityExplode extends StandEntityLightAttack {
    public static final StandPose DETONATE = new StandPose("DETONATE");


    public EntityExplode(StandEntityLightAttack.Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        LivingEntity user = userPower.getUser();
        if(!world.isClientSide){
            if(userPower.getStamina() >249){
                double range = 3*standEntity.getMaxRange();
                Entity entity = PunchBomb.EntityRange(userPower,range);
                if(entity!= null){
                    float ex_range = (float) (Math.sqrt(14*entity.getBbHeight()*entity.getBbWidth()*entity.getBbWidth()));
                    if(entity instanceof StandEntity){
                        StandEntity stand = (StandEntity)  entity;
                        LivingEntity use = stand.getUser();
                        entity.level.explode(user,use.getX(),use.getY(),use.getZ(),ex_range, Explosion.Mode.NONE);
                    }
                    else {
                        entity.level.explode(user,entity.getX(),entity.getY(),entity.getZ(),ex_range, Explosion.Mode.NONE);
                    }
                }
            }
            GameplayHandler.userToBomb.remove(user);

        }
        if(user instanceof  ServerPlayerEntity){
            AddonPackets.sendToClient(new RemoveBombPacket(user.getId()),(ServerPlayerEntity) user);
        }
    }


    @Override
    public boolean cancelHeldOnGettingAttacked(IStandPower power, DamageSource dmgSource, float dmgAmount) {
        return true;
    }

    @Override
    public boolean stopOnHeavyAttack(StandEntityHeavyAttack.HeavyPunchInstance punch) {
        return true;
    }

    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, StandEntityAction.Phase phase, StandEntityTask task, int ticks) {
        if (standEntity.isArmsOnlyMode()) {
            standEntity.setArmsOnlyMode(true, false);
        }

    }


}
