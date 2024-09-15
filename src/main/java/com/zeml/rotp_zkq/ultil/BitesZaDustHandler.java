package com.zeml.rotp_zkq.ultil;

import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import com.zeml.rotp_zkq.init.InitSounds;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = RotpKillerQueen.MOD_ID)
public class BitesZaDustHandler {
    public static final Map<String,Long> userToTime = new HashMap<>();
    public static final Map<LivingEntity,Boolean> firstTime = new HashMap<>();
    public static final Map<LivingEntity,LivingEntity> userToVictim = new HashMap<>();
    public static final Map<LivingEntity,LivingEntity> victimToUser = new HashMap<>();
    public static final Map<Long,Map<LivingEntity, Vector3d>> bombPositionAtMark = new HashMap<>();
    public static final Map<Long,Map<LivingEntity, ServerWorld>> bombDimensionAtMark = new HashMap<>();
    public static final Map<PlayerEntity,Map<Long,LivingEntity>> playerAndItsDeath = new HashMap<>();
    public static final Map<LivingEntity,Integer> timer = new HashMap<>();


    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        PlayerEntity player = event.player;
        if(!event.player.level.isClientSide){
            //Reset spawn point when cross dimension
            bombDimensionAtMark.forEach(((aLong, livingEntityServerWorldMap) -> {
                if(livingEntityServerWorldMap.containsKey(player)){
                    if(livingEntityServerWorldMap.get(player) != player.level && bombPositionAtMark.get(aLong).get(player) != player.position()){
                        Map<LivingEntity,ServerWorld> innerMap2 = new HashMap<>();
                        innerMap2.put(player,(ServerWorld) player.level);
                        bombDimensionAtMark.put(aLong,innerMap2);
                        Map<LivingEntity, Vector3d> innerMap1 = new HashMap<>();
                        innerMap1.put(player,player.position());
                        bombPositionAtMark.put(aLong,innerMap1);
                    }
                }
            }));


            //Sync cap with map
            LazyOptional<LivingData> livingDataOptional = player.getCapability(LivingDataProvider.CAPABILITY);
            if(livingDataOptional.map(LivingData::getHasTimeMarker).get()){
                userToTime.put(player.getName().getString(),livingDataOptional.map(LivingData::getTimeMark).get());
            }

            //Quit Time Maker after a day
            if(livingDataOptional.map(LivingData::getTimeMark).get() > 0 && livingDataOptional.map(LivingData::getHasTimeMarker).get()){
                bombPositionAtMark.remove(livingDataOptional.map(LivingData::getTimeMark).get());
                bombPositionAtMark.remove(livingDataOptional.map(LivingData::getTimeMark).get());
                livingDataOptional.ifPresent(livingData -> {
                    livingData.setHasTimeMark(false);
                    livingData.setTimeMark(-1);
                });
                userToTime.remove(player.getName().getString());
            }

            if(playerAndItsDeath.containsKey(player)){
                Map<Long, LivingEntity> innerMap = playerAndItsDeath.get(player);
                if(innerMap.containsKey(player.level.getDayTime()) && userToTime.containsKey(innerMap.get(player.level.getDayTime()).getName().getString())){
                    timer.putIfAbsent(player,0);
                    if(player instanceof ServerPlayerEntity){
                        AddonPackets.sendToClient(new PutTimerPacket(player.getId()),(ServerPlayerEntity) player);
                    }
                }
            }
        }
        /* Timer to explode*/
        if(timer.containsKey(player)){
            timer.put(player, timer.get(player)+1);
            if(player instanceof ServerPlayerEntity){
                AddonPackets.sendToClient(new TimeMarkPacket(player.getId(),timer.get(player)+1),(ServerPlayerEntity) player);
            }
            if(timer.get(player)==2){
                player.playSound(InitSounds.USER_DUST.get(),1F,1F);
            }
            if(timer.get(player)==79){
                player.playSound(InitSounds.KQ_BOMB.get(),1F,1F);
            }
            if(timer.get(player) >= 80/(5/2)){
                if(!player.level.isClientSide){
                    MCUtil.runCommand(player,"particle minecraft:campfire_cosy_smoke ~ ~1 ~ .1 .2 .1 .025 20");
                    MCUtil.runCommand(player,"particle minecraft:flame ~ ~1 ~ .1 .2 .1 .1 10");
                    for(int i =0; i<=120/(5/2);i++){

                        if(playerAndItsDeath.get(player).containsKey(player.level.getDayTime()-100/(5/2)+i)){
                            LivingEntity userAttacker = playerAndItsDeath.get(player).get(player.level.getDayTime() - 100/(5/2) + i);
                            if(firstTime.get(player)) {
                                long time = userToTime.get(userAttacker.getName().getString());
                                MCUtil.runCommand(player, "time set " + time + "t");
                                firstTime.put(player, false);
                                MCUtil.entitiesAround(PlayerEntity.class,player,50,false,LivingEntity::isAlive).forEach(playerEntity -> {
                                    if(playerEntity != player){
                                        Vector3d vector3d = BitesZaDustHandler.bombPositionAtMark.get(time).get(playerEntity);
                                        MCUtil.runCommand(playerEntity,"/tp "+playerEntity.getName().getString()+" "+vector3d.x+" "+vector3d.y+" "+vector3d.z);
                                    }
                                    if(playerEntity ==userAttacker){
                                        userAttacker.setHealth(userAttacker.getMaxHealth());
                                    }
                                });
                            }
                            player.hurt(DamageSource.explosion(userAttacker),Float.POSITIVE_INFINITY);
                            i = 120/(5/2);
                        }
                    }
                    timer.remove(player);
                    if(player instanceof ServerPlayerEntity){
                        AddonPackets.sendToClient(new RemoveTimerPacket((player.getId())),(ServerPlayerEntity) player);
                    }
                }else {
                    player.playSound(SoundEvents.GENERIC_EXPLODE,1F,1F);

                }

            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEntityHurt(LivingHurtEvent event){
        if(!event.getEntity().level.isClientSide){
            if(event.getEntity() instanceof LivingEntity){
                LivingEntity living = (LivingEntity) event.getEntity();
                if(victimToUser.containsKey(living)){
                    event.setAmount(0);
                    if( event.getSource().getEntity() != victimToUser.get(living) && event.getSource().getEntity() instanceof LivingEntity){
                        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();

                        if(attacker instanceof  PlayerEntity){
                            timer.putIfAbsent(attacker,0);
                            if(attacker instanceof ServerPlayerEntity){
                                AddonPackets.sendToClient(new PutTimerPacket(attacker.getId()),(ServerPlayerEntity) attacker);
                            }
                            if(!playerAndItsDeath.containsKey(attacker)){
                                Map<Long,LivingEntity> innerMap = new HashMap<>();
                                innerMap.put(living.level.getDayTime(),victimToUser.get(living));
                                playerAndItsDeath.put((PlayerEntity) attacker,innerMap);
                            }else {
                                Map<Long,LivingEntity> innerMap = playerAndItsDeath.get((PlayerEntity) attacker);
                                innerMap.put(living.level.getDayTime(),victimToUser.get(living));
                                playerAndItsDeath.put((PlayerEntity) attacker,innerMap);
                            }
                            firstTime.put(attacker,true);
                        }else {
                            MCUtil.runCommand(attacker,"particle minecraft:campfire_cosy_smoke ~ ~1 ~ .1 .2 .1 .025 20");
                            MCUtil.runCommand(attacker,"particle minecraft:flame ~ ~1 ~ .1 .2 .1 .1 10");
                            attacker.hurt(DamageSource.explosion(victimToUser.get(living)),Float.POSITIVE_INFINITY);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent(priority =  EventPriority.LOW)
    public static void onPlayerDeath(LivingDeathEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!entity.level.isClientSide){
            LivingEntity living = BitesZaDustHandler.userToVictim.get(entity);
            BitesZaDustHandler.victimToUser.remove(living);
            BitesZaDustHandler.userToVictim.remove(entity);
            if(living instanceof ServerPlayerEntity){
                AddonPackets.sendToClient(new RemoveBombPacket(living.getId()),(ServerPlayerEntity) living);
                AddonPackets.sendToClient(new RemoveHayatoPacket((living.getId())),(ServerPlayerEntity) living);
            }
            BitesZaDustHandler.playerAndItsDeath.forEach((playerEntity, longLivingEntityMap) -> {
                Iterator<Map.Entry<Long, LivingEntity>> iterator = longLivingEntityMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Long, LivingEntity> entry = iterator.next();
                    if (entry.getValue() == entity) {
                        iterator.remove();
                    }
                }
            });
        }
    }



    //If the player isn't in the time mark its added
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerJoining(EntityJoinWorldEvent event){
        if(!event.getEntity().level.isClientSide){
            if(event.getEntity() instanceof PlayerEntity){
                if(!userToTime.isEmpty()){
                    PlayerEntity player = (PlayerEntity) event.getEntity();
                    bombPositionAtMark.forEach(((aLong, livingEntityVector3dMap) -> {
                        Map<LivingEntity,Vector3d> innerMap = bombPositionAtMark.get(aLong);
                        innerMap.put(player,player.position());

                        Map<LivingEntity,ServerWorld> innerMap2 = bombDimensionAtMark.get(aLong);
                        innerMap2.put(player,((ServerPlayerEntity) player).getLevel());

                    }));
                }
            }
        }
    }


}
