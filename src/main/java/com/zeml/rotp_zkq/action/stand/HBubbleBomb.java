package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.damaging.projectile.BubbleBombEntity;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class HBubbleBomb extends StandEntityAction {
    public HBubbleBomb(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if (!world.isClientSide){
            LivingEntity user = standEntity.getUser();
            BubbleBombEntity bubble = new BubbleBombEntity(user, world,  userPower);
            standEntity.shootProjectile(bubble,.25F,1F);
        }
    }

}
