package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.action.stand.punch.PunchBomb;
import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import com.zeml.rotp_zkq.network.server.RemoveBombPacket;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
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

import java.util.UUID;


public class EntityExplode extends StandEntityLightAttack {
    public static final StandPose DETONATE = new StandPose("DETONATE");


    public EntityExplode(StandEntityLightAttack.Builder builder) {
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
        LivingEntity user = userPower.getUser();
        if(!world.isClientSide){
            if(userPower.getStamina() >249){
                double range = 3*standEntity.getMaxRange();
                Entity entity = PunchBomb.entityInRange(userPower,getTarget(userPower),16);
                if(entity!= null){
                    float ex_range = (float) (Math.sqrt(14*entity.getBbHeight()*entity.getBbWidth()*entity.getBbWidth()));
                    entity.level.explode(user,entity.getX(),entity.getY(),entity.getZ(),ex_range, Explosion.Mode.NONE);

                    LazyOptional<LivingData> livingDataOptional = user.getCapability(LivingDataProvider.CAPABILITY);
                    livingDataOptional.ifPresent(livingData -> {
                        livingData.setBomb(user.getUUID());
                    });

                }
            }

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
    public UUID getTarget(IStandPower power){
        LazyOptional<LivingData> livingDataOptional = power.getUser().getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getBomb).isPresent()? livingDataOptional.map(LivingData::getBomb).get():power.getUser().getUUID();

    }

}
