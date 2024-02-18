package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import net.minecraft.entity.Entity;

public class KQFinisher extends StandEntityHeavyAttack {

    public KQFinisher(StandEntityHeavyAttack.Builder builder){
        super(builder);
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource){
        String s_id =String.valueOf(stand.getUser().getUUID());
        target.addTag(s_id);
        return super.punchEntity(stand, target,dmgSource).addKnockback(0.5F + stand.getLastHeavyFinisherValue())
                .knockbackXRot(-60F);
    }


}
