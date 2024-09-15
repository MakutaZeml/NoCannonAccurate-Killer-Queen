package com.zeml.rotp_zkq.entity.ia;

import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

public class MasterHurtByTargetGoal extends TargetGoal {

    private final SheerHeart sheerHeart;
    private LivingEntity attacker;
    private int timestamp;

    public MasterHurtByTargetGoal(SheerHeart sheerHeart){
        super(sheerHeart,false);
        this.sheerHeart = sheerHeart;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = sheerHeart.getOwner();
        if (owner == null ) {
            return false;
        } else {
            attacker = owner.getLastHurtByMob();
            int i = owner.getLastHurtByMobTimestamp();
            return i != timestamp && canAttack(attacker, EntityPredicate.DEFAULT) && sheerHeart.wantsToAttack(attacker, owner);
        }
    }


    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingentity = this.sheerHeart.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }

}
