package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.damaging.projectile.BubbleBombEntity;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HBubbleBomb extends StandAction {
    public HBubbleBomb(StandAction.Builder builder) {
        super(builder);
    }


    @Override
    protected ActionConditionResult checkHeldItems(LivingEntity user, IStandPower power) {
        return HamonBubbleLauncher.checkSoap(user);
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if(!world.isClientSide){
            HamonBubbleLauncher.consumeSoap(user,1);
            BubbleBombEntity bubble = new BubbleBombEntity(user, world,  power);
            bubble.shootFromRotation(user,.25F,1F);
            world.addFreshEntity(bubble);
        }
    }
}
