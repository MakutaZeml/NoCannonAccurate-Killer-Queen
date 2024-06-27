package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import com.zeml.rotp_zkq.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class SnowBomb extends StandEntityAction {
    INonStandPower pow;
    public SnowBomb(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        AtomicBoolean change = new AtomicBoolean(false);
        INonStandPower.getNonStandPowerOptional(power.getUser()).ifPresent(ipower->{
            Optional<HamonData> hamonOp = ipower.getTypeSpecificData(ModPowers.HAMON.get());
            if(hamonOp.isPresent()){
                HamonData hamon = hamonOp.get();
                if(hamon.isSkillLearned(ModHamonSkills.BUBBLE_LAUNCHER.get())){
                    change.set(true);
                }
            }
        }
        );
        if(change.get()){
            return InitStands.KQ_BUBBLE_BOMB.get();
        }
        return super.replaceAction(power, target);
    }


    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target){
        if (user instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) user;
            ItemStack stack = GetSnowItemsP(player);
            Item type = stack.getItem();
            if (type == Items.SNOWBALL){
                return ActionConditionResult.POSITIVE;
            }else {
                return ActionConditionResult.NEGATIVE;
            }

        }else {
            ItemStack stack = GetSnowItem(user);
            Item type = stack.getItem();
            if (type == Items.SNOWBALL){
                return ActionConditionResult.POSITIVE;
            }else {
                return ActionConditionResult.NEGATIVE;
            }
        }
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if (!world.isClientSide){
            LivingEntity user = userPower.getUser();
            if(user instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) user;
                ItemStack itemStack =GetSnowItemsP(player);
                Item type = itemStack.getItem();
                if(type == Items.SNOWBALL){
                    SnowBombEntity snowBomb = new SnowBombEntity(player, world,  userPower);
                    standEntity.shootProjectile(snowBomb,0.5F,1F);
                    standEntity.playSound(SoundEvents.SNOWBALL_THROW,1.0F,1.0F);
                }

            }
            else
            {
                ItemStack itemStack = GetSnowItem(user);
                SnowBombEntity snowBomb = new SnowBombEntity(user, world,  userPower);
                standEntity.shootProjectile(snowBomb,0.5F,1F);
                standEntity.playSound(SoundEvents.SNOWBALL_THROW,1.0F,1.0F);
            }
        }
    }



    public static ItemStack GetSnowItemsP(@NotNull PlayerEntity player){
        ItemStack itemStack = ItemStack.EMPTY;
        for(int i=0;i<player.inventory.getContainerSize();i++){
            ItemStack item = player.inventory.getItem(i);
            Item type = item.getItem();
            if(!item.isEmpty()){
                if(type == Items.SNOWBALL){
                    itemStack= item;
                    i=player.inventory.getContainerSize();
                    item.shrink(item.getCount());
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

}
