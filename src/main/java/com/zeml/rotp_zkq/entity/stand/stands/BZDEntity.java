package com.zeml.rotp_zkq.entity.stand.stands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import net.minecraft.world.World;

public class BZDEntity extends StandEntity {
    public BZDEntity(StandEntityType<? extends StandEntity> type, World world) {
        super(type, world);
    }

    private final StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0, 0, 0);

    @Override
    public boolean isPickable(){ return false;}

    @Override
    public StandRelativeOffset getDefaultOffsetFromUser() {return offsetDefault;}
}