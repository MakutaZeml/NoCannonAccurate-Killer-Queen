package com.zeml.rotp_zkq.entity.stand;


import com.github.standobyte.jojo.action.non_stand.VampirismBloodDrain;

import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zkq.entity.ia.MasterHurtByTargetGoal;
import com.zeml.rotp_zkq.entity.ia.MasterHurtTargetGoal;
import com.zeml.rotp_zkq.entity.ia.SheerHeartSwelGoal;
import com.zeml.rotp_zkq.init.InitSounds;
import com.zeml.rotp_zkq.init.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

import net.minecraft.potion.EffectInstance;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;


import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


public class SheerHeart extends MonsterEntity {
    protected static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager.defineId(SheerHeart.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Integer> DATA_SWELL_DIR = EntityDataManager.defineId(SheerHeart.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_IS_POWERED = EntityDataManager.defineId(SheerHeart.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_IS_IGNITED = EntityDataManager.defineId(SheerHeart.class, DataSerializers.BOOLEAN);
    private boolean summonedFromAbility = false;
    private int oldSwell;
    private int swell;
    private int maxSwell = 15;
    private int explosionRadius = 3;
    public SheerHeart(@NotNull World world){
        super(ModEntityTypes.SHEAR_HEART.get(), world);
    }

    public SheerHeart(EntityType<? extends MonsterEntity > type, World world) {
        super(type, world);
    }

    public void setSummonedFromAbility() {
        this.summonedFromAbility = true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(DATA_SWELL_DIR, -1);
        this.entityData.define(DATA_IS_POWERED, false);
        this.entityData.define(DATA_IS_IGNITED, false);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SheerHeartSwelGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MasterHurtByTargetGoal(this));
        this.targetSelector.addGoal(2,new MasterHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));

    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (getOwnerUUID() != null) {
            compound.putUUID("Owner", getOwnerUUID());
        }
        compound.putBoolean("AbilitySummon", summonedFromAbility);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        UUID ownerId = compound.hasUUID("Owner") ? compound.getUUID("Owner") : null;
        if (ownerId != null) {
            setOwnerUUID(ownerId);
        }
        summonedFromAbility = compound.getBoolean("AbilitySummon");
    }

    @Nullable
    private UUID getOwnerUUID() {
        return entityData.get(OWNER_UUID).orElse(null);
    }

    private void setOwnerUUID(@Nullable UUID uuid) {
        entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(LivingEntity owner) {
        setOwnerUUID(owner != null ? owner.getUUID() : null);
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    public boolean isEntityOwner(LivingEntity entity) {
        return entityData.get(OWNER_UUID).map(ownerId -> entity.getUUID().equals(ownerId)).orElse(false);
    }

    @Override
    protected int getExperienceReward(PlayerEntity player) {
        return isEntityOwner(player) ? 0 : super.getExperienceReward(player);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(InitSounds.KQ_BOMB.get(), 1.0F, 0.5F);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explodeCreeper();
            }
        }
        super.tick();
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(InitSounds.KQ_SH_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return !isEntityOwner(target) && super.canAttack(target);
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canHarmPlayer((PlayerEntity) target)) {
            return false;
        }
        return true;
    }

    @Override
    public Team getTeam() {
        LivingEntity owner = getOwner();
        if (owner != null) {
            return owner.getTeam();
        }
        return super.getTeam();
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        LivingEntity owner = getOwner();
        if (entity == owner) {
            return true;
        }
        if (owner != null) {
            return owner.isAlliedTo(entity);
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return !this.isLeashed() && isEntityOwner(player);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {}

    @Override
    public boolean isInvulnerableTo(DamageSource source){
        return true;

    }


    @Override
    public boolean doHurtTarget(Entity target) {
        if (super.doHurtTarget(target)) {
            if (getMainHandItem().isEmpty() && target instanceof LivingEntity) {
                LivingEntity livingTarget = (LivingEntity) target;
                if (livingTarget.getArmorCoverPercentage() <= random.nextFloat() && JojoModUtil.canBleed(livingTarget)) {
                    float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
                    if (VampirismBloodDrain.drainBlood(this, livingTarget, damage / 5F)) {
                        heal(damage / 2F);
                    }
                }
            }
            return true;
        }
        return false;
    }




//Creeper Behavior//
    public boolean isIgnited() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public int getSwellDir() {
        return this.entityData.get(DATA_SWELL_DIR);
    }
    public void setSwellDir(int p_70829_1_) {
        this.entityData.set(DATA_SWELL_DIR, p_70829_1_);
    }

    private void explodeCreeper() {
        if (!this.level.isClientSide) {
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            float f = this.isPowered() ? 2.0F : 1.0F;
            this.dead = true;

            LivingEntity explo = this.getOwner() == null?this:this.getOwner();

            this.level.explode(explo, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, explosion$mode);
            this.remove();
            this.spawnLingeringCloud();
        }

    }

    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }

    private void spawnLingeringCloud() {
        Collection<EffectInstance> collection = this.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
            areaeffectcloudentity.setRadius(2.5F);
            areaeffectcloudentity.setRadiusOnUse(-0.5F);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());

            for(EffectInstance effectinstance : collection) {
                areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
            }

            this.level.addFreshEntity(areaeffectcloudentity);
        }

    }


    public void die() {
        this.remove();
    }
}
