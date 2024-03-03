package com.zeml.rotp_zkq.action.stand;

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


public class EntityExplode extends StandEntityAction {


    public EntityExplode(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if(!world.isClientSide){
            Double range = 3*standEntity.getMaxRange();
            LivingEntity user = userPower.getUser();
            Double s_power = standEntity.getAttackDamage();
            LivingEntity entity = LivingEntityBomb(userPower,range);
            if(entity!= null){
                double health = entity.getMaxHealth();
                float ex_range = (float) (Math.log(health*s_power/2)/1.2);
                float damage = (float) Math.sqrt(health*s_power);

                if(entity instanceof StandEntity){
                    StandEntity stand = (StandEntity)  entity;
                    LivingEntity use = stand.getUser();
                    entity.level.explode(user,use.getX(),use.getY(),use.getZ(),ex_range, Explosion.Mode.NONE);
                    user.hurt(DamageSource.explosion(user),damage);
                }
                else {
                    entity.level.explode(user,entity.getX(),entity.getY(),entity.getZ(),ex_range, Explosion.Mode.NONE);
                    entity.hurt(DamageSource.explosion(user),damage);

                }
                String s_id = String.valueOf(user.getUUID());
                entity.removeTag(s_id);
                if (user instanceof ServerPlayerEntity) {
                    AddonPackets.sendToClient(new RemoveTagPacket(entity.getId(), s_id), (ServerPlayerEntity) user);
                }
            }

        }
    }


    public static LivingEntity LivingEntityBomb(@NotNull IStandPower standuser, Double range){
        LivingEntity user = standuser.getUser();
        World world =user.level;
        String s_id =  String.valueOf(user.getUUID());
        LivingEntity entidad = world.getEntitiesOfClass(LivingEntity.class,user.getBoundingBox().inflate(range),
                EntityPredicates.ENTITY_STILL_ALIVE).stream().filter(entity -> entity.getTags().contains(s_id)).findFirst().orElse(null);
        return entidad;

    }




}
