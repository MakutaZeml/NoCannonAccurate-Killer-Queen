package com.zeml.rotp_zkq.action.stand.punch;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.LivingEntity;

public class KQHeavyPunch extends StandEntityHeavyAttack {
    public KQHeavyPunch(StandEntityHeavyAttack.Builder builder){
        super(builder);
    }

    @Override
    public ActionConditionResult checkConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if(!BitesZaDustHandler.userToVictim.containsKey(user)){
            return ActionConditionResult.POSITIVE;
        }
        return ActionConditionResult.NEGATIVE;
    }
}
