package com.zeml.rotp_zkq.entity.damaging.projectile;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BubbleBombEntity extends ModdedProjectileEntity {
    @Nullable
    private IStandPower userStandPower;
    public BubbleBombEntity(LivingEntity shooter, World world, @Nullable IStandPower standPower){
        super(ModEntityTypes.BUBBLE_BOMB.get(), shooter,world);
        userStandPower =standPower;
    }
    public BubbleBombEntity(EntityType<? extends BubbleBombEntity> type, World world){
        super(type, world);
    }




    @Override
    public int ticksLifespan() {
        return 100;
    }

    @Override
    protected float getBaseDamage() {
        return 0;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0;
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    @Override
    protected boolean hurtTarget(Entity target, LivingEntity owner){
        double mul = JojoModConfig.getCommonConfigInstance(false).standDamageMultiplier.get();

        level.explode(this, target.getX(),target.getY(),target.getZ(),1, Explosion.Mode.NONE);
        return target.hurt(DamageSource.explosion(owner),14* (float)mul);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        double mul = JojoModConfig.getCommonConfigInstance(false).standDamageMultiplier.get();
        level.explode(this, this.getX(),this.getY(),this.getZ(),2, Explosion.Mode.NONE);

    }

}
