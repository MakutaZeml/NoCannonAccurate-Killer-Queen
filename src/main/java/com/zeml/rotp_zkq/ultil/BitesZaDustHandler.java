package com.zeml.rotp_zkq.ultil;

import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.init.InitSounds;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.RemoveBombPacket;
import com.zeml.rotp_zkq.network.server.RemoveHayatoPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
            //Quit Time Maker after a day
            if(userToTime.containsKey(player.getName().getString())){
                if(player.level.getDayTime()-userToTime.get(player.getName().getString())>24000){
                    bombPositionAtMark.remove(userToTime.get(player.getName().getString()));
                    bombDimensionAtMark.remove(userToTime.get(player.getName().getString()));
                    userToTime.remove(player.getName().getString());
                    if(player instanceof ServerPlayerEntity){
                        AddonPackets.sendToClient(new RemoveBombPacket(player.getId()),(ServerPlayerEntity) player);
                    }
                }
            }
            if(playerAndItsDeath.containsKey(player)){
                Map<Long, LivingEntity> innerMap = playerAndItsDeath.get(player);
                if(innerMap.containsKey(player.level.getDayTime()) && userToTime.containsKey(innerMap.get(player.level.getDayTime()).getName().getString())){
                    timer.putIfAbsent(player,0);
                }
            }
        }
        /* Timer to explode*/
        if(timer.containsKey(player)){
            System.out.println(playerAndItsDeath+" "+player.level.getDayTime());
            timer.put(player, timer.get(player)+1);
            if(timer.get(player)==2){
                player.playSound(InitSounds.USER_DUST.get(),1F,1F);
            }
            if(timer.get(player)==59){
                player.playSound(InitSounds.KQ_BOMB.get(),1F,1F);
            }
            if(timer.get(player) >= 60){
                if(!player.level.isClientSide){
                    MCUtil.runCommand(player,"particle minecraft:campfire_cosy_smoke ~ ~1 ~ .1 .2 .1 .025 20");
                    MCUtil.runCommand(player,"particle minecraft:flame ~ ~1 ~ .1 .2 .1 .1 10");
                    for(int i =0; i<=100;i++){
                        if(playerAndItsDeath.get(player).containsKey(player.level.getDayTime()-80+i)){
                            LivingEntity userAttacker = playerAndItsDeath.get(player).get(player.level.getDayTime() - 80 + i);
                            if(firstTime.get(player)) {
                                long time = userToTime.get(userAttacker.getName().getString());
                                MCUtil.runCommand(player, "time set " + time + "t");
                                firstTime.put(player, false);
                                MCUtil.entitiesAround(PlayerEntity.class,player,50,false,LivingEntity::isAlive).forEach(playerEntity -> {
                                    Vector3d vector3d = BitesZaDustHandler.bombPositionAtMark.get(time).get(playerEntity);
                                    playerEntity.teleportTo(vector3d.x,vector3d.y,vector3d.z);
                                    if(playerEntity ==userAttacker){
                                        userAttacker.setHealth(userAttacker.getMaxHealth());
                                    }
                                });
                            }
                            player.hurt(DamageSource.explosion(userAttacker),Float.POSITIVE_INFINITY);
                            MCUtil.runCommand(player,"");
                            i = 100;
                        }
                    }
                    timer.remove(player);
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
            BitesZaDustHandler.playerAndItsDeath.forEach(((playerEntity, longLivingEntityMap) -> {
                longLivingEntityMap.forEach((aLong, livingEntity) -> {
                    if(livingEntity == entity){
                        longLivingEntityMap.remove(aLong);
                    }
                });
            }));
        }
    }



}
