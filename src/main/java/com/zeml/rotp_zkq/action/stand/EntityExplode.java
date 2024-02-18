package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.effect.StandEffectInstance;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.client.render.entity.model.stand.KillerQueenModel;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;


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
                entity.level.explode(entity,entity.getX(),entity.getY(),entity.getZ(),ex_range, Explosion.Mode.NONE);
                entity.hurt(DamageSource.explosion(user),damage);
                entity.removeTag(String.valueOf(user.getUUID()));
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
