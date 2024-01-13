package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityActionModifier;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;




public class ItemFBombExplode extends StandEntityAction {
    public  static final StandPose BOMB = new StandPose("BOMB");
    public ItemFBombExplode(StandEntityAction.Builder builder) {
        super(builder);
    }
    
    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if (!world.isClientSide){
            LivingEntity user =userPower.getUser();
            double range= standEntity.getMaxRange();
            List<ItemEntity> items = getItemsinRange(user,range);
            ArrayList<ItemEntity> bombs = new ArrayList<>();
            int id = user.getId();

            for (ItemEntity item:items){
                if(item.getItem().hasTag()){
                    ItemStack iteminfo = item.getItem();
                    int itemid = iteminfo.getOrCreateTag().getInt("user");
                    if (itemid ==id){
                        bombs.add(item);
                    }
                }

            }


            List<PlayerEntity> players = getPlayersRange(user, range);
            List<LivingEntity> enties= getEntitiesRange(user, range);


            boolean gblok = JojoModConfig.getCommonConfigInstance(false).abilitiesBreakBlocks.get();
            ExplodePlayers(players,id,user);
            ExplodeEntity(enties,id,user);
            for (ItemEntity bomba:bombs) {
                bomba.level.explode(bomba,bomba.getX(),bomba.getY(), bomba.getZ(),2.0F, Explosion.Mode.NONE);
                bomba.remove();

            }

        }
    }

    public static List<ItemEntity> getItemsinRange(@NotNull LivingEntity user, Double range) {
        World world = user.level;
        return world.getEntitiesOfClass(ItemEntity.class, user.getBoundingBox().inflate(range), EntityPredicates.ENTITY_STILL_ALIVE).stream().collect(Collectors.toList());
    }

    public static List<PlayerEntity> getPlayersRange(@NotNull LivingEntity user, Double range){
        World world = user.level;
        return world.getEntitiesOfClass(PlayerEntity.class,user.getBoundingBox().inflate(range),EntityPredicates.ENTITY_STILL_ALIVE).stream().collect(Collectors.toList());
    }

    public static List<LivingEntity> getEntitiesRange(@NotNull LivingEntity user, Double range){
        World world = user.level;
        return world.getEntitiesOfClass(LivingEntity.class,user.getBoundingBox().inflate(range),EntityPredicates.ENTITY_STILL_ALIVE).stream().collect(Collectors.toList());
    }

    private static void ExplodePlayers(@Nullable List<PlayerEntity> list_player, int id, LivingEntity user){
        for (PlayerEntity player: list_player){
            for(int i=0;i<player.inventory.getContainerSize();i++){
                ItemStack p_item = player.inventory.getItem(i);
                if(!p_item.isEmpty() && p_item.hasTag()){
                    int id_it = p_item.getOrCreateTag().getInt("user");
                    if(id_it==id){
                        player.level.explode(player,player.getX(),player.getY(),player.getZ(),2.0F, Explosion.Mode.NONE);
                        player.inventory.removeItem(p_item);
                        player.hurt(DamageSource.explosion(user),14);
                    }
                }
            }
        }
    }

    private static void ExplodeEntity(@Nullable List<LivingEntity> entities, int id,LivingEntity user){
        for (Entity entity:entities) {
            Entity type = entity.getEntity();
            if(!(type instanceof PlayerEntity)){
                Iterable<ItemStack> Slots = entity.getAllSlots();
                for (ItemStack item:Slots) {
                    if(!item.isEmpty() && item.hasTag()){
                        int item_id =item.getOrCreateTag().getInt("user");
                        if (item_id==id){
                            entity.level.explode(entity,entity.getX(),entity.getY(),entity.getZ(),2.0F, Explosion.Mode.NONE);
                            item.shrink(item.getCount());
                            entity.hurt(DamageSource.explosion(user),14);
                        }
                    }
                }
            }
        }
    }

    public static class Builder extends StandEntityAction.AbstractBuilder<ItemFBombExplode.Builder>{
        private Supplier<StandEntityActionModifier> recoveryyAction = ()->null;

        public Builder() {
            standPose(ItemFBombExplode.BOMB).staminaCost(20F).standOffsetFromUser(2F,0F);
        }

        @Override
        protected Builder getThis() {
            return null;
        }
    }

}
