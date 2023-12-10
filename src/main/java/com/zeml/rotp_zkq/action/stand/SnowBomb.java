package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import org.jetbrains.annotations.NotNull;

public class SnowBomb extends StandEntityAction {
    INonStandPower pow;
    public SnowBomb(StandEntityAction.Builder builder) {
        super(builder);
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
                    snowBomb.setShootingPosOf(player);
                    snowBomb.shootFromRotation(player, 2.0F,standEntity.getProjectileInaccuracy(1.0F));
                    standEntity.addProjectile(snowBomb);
                    itemStack.shrink(1);
                    standEntity.playSound(SoundEvents.SNOWBALL_THROW,1.0F,1.0F);
                }

            }
            else
            {
                ItemStack itemStack = GetSnowItem(user);
                SnowBombEntity snowBomb = new SnowBombEntity(user, world,  userPower);
                snowBomb.setShootingPosOf(user);
                snowBomb.shootFromRotation(user, 2.0F,standEntity.getProjectileInaccuracy(1.0F));
                standEntity.addProjectile(snowBomb);
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

}
