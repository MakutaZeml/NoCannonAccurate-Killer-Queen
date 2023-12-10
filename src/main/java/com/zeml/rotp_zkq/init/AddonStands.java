package com.zeml.rotp_zkq.init;

import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;

public class AddonStands {

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<KQStandEntity>>
    KQ_STAND = new EntityStandSupplier<>(InitStands.KQ_STAND);
}
