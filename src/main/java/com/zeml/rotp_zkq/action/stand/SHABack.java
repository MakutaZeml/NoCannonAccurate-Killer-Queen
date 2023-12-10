package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import com.zeml.rotp_zkq.init.InitSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SHABack extends StandEntityAction {
    public SHABack(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        List<SheerHeart> ListSH = SheerHeartAttack.isSHpresent(userPower);
        LivingEntity user = userPower.getUser();
        if (!ListSH.isEmpty()){
            for (SheerHeart sheerHeart: ListSH){
                if(sheerHeart.getOwner()==user){
                    sheerHeart.kill();
                    standEntity.playSound(InitSounds.KQ_UNSUMMON.get(),1F,1F);
                }
            }
        }
    }



}
