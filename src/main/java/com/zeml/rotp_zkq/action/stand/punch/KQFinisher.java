package com.zeml.rotp_zkq.action.stand.punch;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.zeml.rotp_zkq.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.Objects;

public class KQFinisher extends StandEntityHeavyAttack {

    public KQFinisher(StandEntityHeavyAttack.Builder builder){
        super(builder);
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource){
        if(stand.getUser() != null && target != null){
            String s_id =String.valueOf(Objects.requireNonNull(stand.getUser()).getUUID());
            target.addTag(s_id);
        }
        return super.punchEntity(stand, target,dmgSource).addKnockback(0.5F + stand.getLastHeavyFinisherValue())
                .knockbackXRot(-60F);
    }



}
