package com.zeml.rotp_zkq.action.stand.punch;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.ibm.icu.util.EthiopicCalendar;
import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import com.zeml.rotp_zkq.init.InitSounds;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.AddTagPacket;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class PunchBomb extends StandEntityLightAttack {
    boolean manifest=true;
    public static final StandPose BOMB_PUNCH = new StandPose("BOMB_PUNCH");


    public PunchBomb(StandEntityLightAttack.Builder builder) {
        super(builder);
    }


    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if (entityInRange(power,getTarget(power),16) != null) {
            return InitStands.KQ_ENTITY_EX.get();
        }
        return super.replaceAction(power, target);
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
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource){
        if (target instanceof LivingEntity && !(target instanceof StandEntity)){
            if(stand.getUser() != null){
                LivingEntity user = stand.getUser();
                LazyOptional<LivingData> livingDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
                livingDataOptional.ifPresent(livingData -> {
                    livingData.setBomb(target.getUUID());
                });
            }
        }
        return super.punchEntity(stand, target,dmgSource).damage(1F);
    }


    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, StandEntityAction.Phase phase, StandEntityTask task, int ticks) {
        if (standEntity.isArmsOnlyMode() && standEntity.swingingArm == Hand.OFF_HAND) {
            standEntity.setArmsOnlyMode(true, false);
        }
        if(standEntity.isArmsOnlyMode()){
            manifest = false;
        }

    }

    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        int mul = standEntity.isArmsOnlyMode()?1:(3/2);
        return mul*StandStatFormulas.getHeavyAttackWindup(standEntity.getAttackSpeed(), standEntity.getFinisherMeter());
    }


    /*
    public static Entity EntityRange(@NotNull IStandPower userPower, double range) {
        LivingEntity user = userPower.getUser();
        return MCUtil.entitiesAround(LivingEntity.class,user,range,false,livingEntity -> livingEntity.getUUID().equals(GameplayHandler.userToBomb.get(user))).stream().findFirst().orElse(null);
    }

     */

    public static Entity entityInRange( IStandPower userPower, UUID uuid, double range){
        LivingEntity user = userPower.getUser();
        return MCUtil.entitiesAround(LivingEntity.class,user,range,false,livingEntity -> livingEntity.getUUID().equals(uuid)).stream().findFirst().orElse(null);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }



    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.KQ_ENTITY_EX.get(), InitStands.KQ_ENTITY_QUIT.get()};
    }


    public UUID getTarget(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getBomb).isPresent()? livingDataOptional.map(LivingData::getBomb).get():power.getUser().getUUID();

    }



}
