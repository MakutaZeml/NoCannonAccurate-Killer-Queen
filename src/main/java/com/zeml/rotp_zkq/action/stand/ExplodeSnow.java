package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.damaging.projectile.BubbleBombEntity;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ExplodeSnow extends StandEntityLightAttack {
    public ExplodeSnow (StandEntityLightAttack.Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if(!BitesZaDustHandler.userToVictim.containsKey(user)){
            return ActionConditionResult.POSITIVE;
        }
        return ActionConditionResult.NEGATIVE;
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if(!world.isClientSide){
            List<SnowBombEntity> list = getSnow(standEntity.getUser());
            List<BubbleBombEntity>blist =getBubbles(standEntity.getUser());
            double mul = JojoModConfig.getCommonConfigInstance(false).standDamageMultiplier.get();
            for (SnowBombEntity snow:list){
                snow.level.explode(standEntity.getUser(),snow.getX(),snow.getY(),snow.getZ(), 2*(float)mul, Explosion.Mode.NONE);
                snow.remove();
            }
            for (BubbleBombEntity snow:blist){
                snow.level.explode(standEntity.getUser(),snow.getX(),snow.getY(),snow.getZ(), 2*(float)mul, Explosion.Mode.NONE);
                snow.remove();
            }
        }
    }


    private List<SnowBombEntity> getSnow(LivingEntity user){
        World world = user.level;
        return world.getEntitiesOfClass(SnowBombEntity.class,user.getBoundingBox().inflate(20),EntityPredicates.ENTITY_STILL_ALIVE)
                .stream().filter(snowBombEntity -> snowBombEntity.getOwner()==user).collect(Collectors.toList());
    }

    private List<BubbleBombEntity> getBubbles(LivingEntity user){
        World world = user.level;
        return world.getEntitiesOfClass(BubbleBombEntity.class,user.getBoundingBox().inflate(20),EntityPredicates.ENTITY_STILL_ALIVE)
                .stream().filter(snowBombEntity -> snowBombEntity.getOwner()==user).collect(Collectors.toList());
    }
}
