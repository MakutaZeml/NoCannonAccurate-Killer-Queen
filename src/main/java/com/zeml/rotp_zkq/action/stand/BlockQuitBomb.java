package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BlockQuitBomb extends StandEntityLightAttack {

    public BlockQuitBomb(StandEntityLightAttack.Builder builder){
        super(builder);
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if(!world.isClientSide){
            KQStandEntity kqStand = (KQStandEntity) standEntity;
            kqStand.setIsBlockBomb(false);
        }
    }
}
