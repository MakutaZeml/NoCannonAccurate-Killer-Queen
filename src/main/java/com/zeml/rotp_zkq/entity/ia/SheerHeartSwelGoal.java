package com.zeml.rotp_zkq.entity.ia;

import java.util.EnumSet;

import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;


public class SheerHeartSwelGoal extends Goal {

    private final SheerHeart shearheart;
    private LivingEntity target;

    public SheerHeartSwelGoal(SheerHeart shearheart) {
        this.shearheart = shearheart;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        LivingEntity livingentity = this.shearheart.getTarget();
        return this.shearheart.getSwellDir() > 0 || livingentity != null && this.shearheart.distanceToSqr(livingentity) < 9.0D;
    }

    public void start() {
        this.shearheart.getNavigation().stop();
        this.target = this.shearheart.getTarget();
    }


    public void stop() {
        this.target = null;
    }

    public void tick() {
        if (this.target == null) {
            this.shearheart.setSwellDir(-1);
        } else if (this.shearheart.distanceToSqr(this.target) > 49.0D) {
            this.shearheart.setSwellDir(-1);
        } else if (!this.shearheart.getSensing().canSee(this.target)) {
            this.shearheart.setSwellDir(-1);
        } else {
            this.shearheart.setSwellDir(1);
        }
    }
}
