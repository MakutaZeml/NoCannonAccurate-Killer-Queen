package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.mc.MCUtil;
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
        if(!world.isClientSide){
            if(userPower.getStamina() >249){
                Double range = 3*standEntity.getMaxRange();
                LivingEntity user = userPower.getUser();
                Double s_power = standEntity.getAttackDamage();
                LivingEntity entity = LivingEntityBomb(userPower,range);
                if(entity!= null){
                    double health = entity.getMaxHealth();
                    float ex_range = (float) (Math.sqrt(14*entity.getBbHeight()*entity.getBbWidth()*entity.getBbWidth()));

                    if(entity instanceof StandEntity){
                        StandEntity stand = (StandEntity)  entity;
                        LivingEntity use = stand.getUser();
                        entity.level.explode(user,use.getX(),use.getY(),use.getZ(),ex_range, Explosion.Mode.NONE);
                    }
                    else {
                        entity.level.explode(user,entity.getX(),entity.getY(),entity.getZ(),ex_range, Explosion.Mode.NONE);


                    }
                    String s_id = String.valueOf(user.getUUID());
                    entity.removeTag(s_id);
                    if (user instanceof ServerPlayerEntity) {
                        AddonPackets.sendToClient(new RemoveTagPacket(entity.getId(), s_id), (ServerPlayerEntity) user);
                    }
                }
            }

        }
    }


    public static LivingEntity LivingEntityBomb(@NotNull IStandPower standuser, Double range){
        LivingEntity user = standuser.getUser();
        String s_id =  String.valueOf(user.getUUID());
        return MCUtil.entitiesAround(LivingEntity.class,user,range,false,entity -> entity.getTags().contains(s_id)).stream().findFirst().orElse(null);

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
