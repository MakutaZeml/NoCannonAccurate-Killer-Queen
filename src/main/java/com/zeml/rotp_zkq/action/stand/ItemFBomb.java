package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import org.jetbrains.annotations.NotNull;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemFBomb extends StandEntityAction {
    public ItemFBomb(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            LivingEntity user = userPower.getUser();
            ItemStack itembomb = itembomb(user);
            if (itembomb != ItemStack.EMPTY) {
                ItemStack stack = itembomb.copy();
                int userid = user.getId();
                stack.shrink(itembomb.getCount() - 1);
                itembomb.shrink(1);
                stack.getOrCreateTag().putInt("user",userid);
                user.setItemInHand(Hand.MAIN_HAND, stack);
                user.setItemInHand(Hand.OFF_HAND, itembomb);
            }
        }
    }



    private @NotNull ItemStack itembomb(@NotNull LivingEntity entity){
        return entity.getOffhandItem();
    }
}
