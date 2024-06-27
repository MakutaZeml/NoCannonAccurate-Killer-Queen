package com.zeml.rotp_zkq.action.stand;

import java.util.List;
import java.util.stream.Collectors;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import org.jetbrains.annotations.NotNull;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import com.zeml.rotp_zkq.init.InitSounds;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;

public class SheerHeartAttack extends StandEntityAction {
    public SheerHeartAttack(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target){
        List<SheerHeart> ListSH =isSHpresent(power);
        boolean able = true;
        for(SheerHeart sheerHeart:ListSH){
            if (sheerHeart.getOwner()==user){
                able=false;
            }
        }
        if(able){
            return ActionConditionResult.POSITIVE;
        }else {
            return  ActionConditionResult.NEGATIVE;
        }
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if(!world.isClientSide){
            List<SheerHeart> ListSH =isSHpresent(userPower);
            boolean able = true;
            LivingEntity user = userPower.getUser();
            String s_id = String.valueOf(user.getUUID()) ;
            for(SheerHeart sheerHeart:ListSH){
                if (sheerHeart.getOwner()==user){
                    able=false;
                }
            }
            if(able){
                SheerHeart sh = new SheerHeart(world);
                sh.copyPosition(standEntity);
                sh.setSummonedFromAbility();
                sh.setOwner(user);
                sh.setInvulnerable(true);
                if(task.getTarget().getEntity() != null && task.getTarget().getEntity() instanceof LivingEntity){
                    sh.setTarget((LivingEntity) task.getTarget().getEntity());
                }
                world.addFreshEntity(sh);
                able = false;
            }

        }
    }


    public static List<SheerHeart> isSHpresent(@NotNull IStandPower userPower){
        LivingEntity user = userPower.getUser();
        World world = user.level;
        return world.getEntitiesOfClass(SheerHeart.class, user.getBoundingBox().inflate(140), EntityPredicates.ENTITY_STILL_ALIVE).stream().collect(Collectors.toList());
    }




}