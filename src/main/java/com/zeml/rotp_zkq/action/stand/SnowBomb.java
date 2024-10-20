package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.item.TommyGunItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.standobyte.jojo.action.non_stand.HamonBubbleLauncher.getSoapItem;

public class SnowBomb extends StandAction {
    INonStandPower pow;
    public SnowBomb(StandAction.Builder builder) {
        super(builder);
    }



    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        return checkSoap(power.getUser()) ? InitStands.KQ_BUBBLE_BOMB.get(): super.replaceAction(power, target);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target){
        if (user instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) user;
            ItemStack stack = GetSnowItemsP(player, false);
            Item type = stack.getItem();
            if (type == Items.SNOWBALL && !BitesZaDustHandler.userToVictim.containsKey(user)){
                return ActionConditionResult.POSITIVE;
            }else {
                return ActionConditionResult.NEGATIVE;
            }

        }else {
            ItemStack stack = GetSnowItem(user);
            Item type = stack.getItem();
            if (type == Items.SNOWBALL && !BitesZaDustHandler.userToVictim.containsKey(user)){
                return ActionConditionResult.POSITIVE;
            }else {
                return ActionConditionResult.NEGATIVE;
            }
        }
    }


    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide) {
            if (user instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) user;
                ItemStack itemStack =GetSnowItemsP(player, true);
                Item type = itemStack.getItem();
                if(type == Items.SNOWBALL){
                    SnowBombEntity snowBomb = new SnowBombEntity(player, world,  power);
                    snowBomb.shootFromRotation(user,.25F,1F);
                    world.addFreshEntity(snowBomb);
                    user.playSound(SoundEvents.SNOWBALL_THROW,1.0F,1.0F);
                }
            }else
            {
                SnowBombEntity snowBomb = new SnowBombEntity(user, world,  power);
                snowBomb.shootFromRotation(user,.5F,1F);
                world.playSound(null,user.blockPosition(),SoundEvents.SNOWBALL_THROW, SoundCategory.PLAYERS,1.0F,1.0F);
            }
        }
    }

    public static ItemStack GetSnowItemsP(@NotNull PlayerEntity player, boolean delete){
        ItemStack itemStack = ItemStack.EMPTY;
        for(int i=0;i<player.inventory.getContainerSize();i++){
            ItemStack item = player.inventory.getItem(i);
            Item type = item.getItem();
            if(!item.isEmpty()){
                if(type == Items.SNOWBALL){
                    itemStack= item;
                    i=player.inventory.getContainerSize();
                    if(delete){
                        item.shrink(1);
                    }
                }
            }
        }
        return itemStack;
    }

    public static ItemStack GetSnowItem(@NotNull LivingEntity user){
        ItemStack itemStack= ItemStack.EMPTY;
        ItemStack der = user.getMainHandItem();
        ItemStack izq = user.getOffhandItem();
        Item d_type= der.getItem();
        Item i_type = izq.getItem();
        if(d_type==Items.SNOWBALL){
            itemStack = der;
        } else if (i_type == Items.SNOWBALL) {
            itemStack = izq;
        }
        return itemStack;
    }

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.KQ_BUBBLE_BOMB.get()};
    }


    public static boolean checkSoap(LivingEntity entity) {
        ItemStack item = getSoapItem(entity);
        if (item.isEmpty()) {
            return false;
        }
        return item.getItem() != ModItems.BUBBLE_GLOVES.get() || TommyGunItem.getAmmo(item) > 0;
    }

}
