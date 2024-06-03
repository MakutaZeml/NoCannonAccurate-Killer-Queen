package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
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


    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target){
        List<SheerHeart> ListSH = SheerHeartAttack.isSHpresent(power);
        String s_id = String.valueOf(user.getUUID());
        boolean able = true;
        for(SheerHeart sheerHeart:ListSH){
            if (sheerHeart.getOwner()==user){
                able=false;
            }
        }
        if(!able){
            return ActionConditionResult.POSITIVE;
        }else {
            return  ActionConditionResult.NEGATIVE;
        }
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        List<SheerHeart> ListSH = SheerHeartAttack.isSHpresent(userPower);
        LivingEntity user = userPower.getUser();
        if (!ListSH.isEmpty()){
            for (SheerHeart sheerHeart: ListSH){
                if(sheerHeart.getOwner()==user){
                    sheerHeart.die();
                }
            }
        }
    }



}
