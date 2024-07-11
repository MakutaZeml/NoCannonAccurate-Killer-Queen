package com.zeml.rotp_zkq.ultil;

import com.github.standobyte.jojo.entity.itemprojectile.StandArrowEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.google.common.eventbus.DeadEvent;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.ui.marker.BlockBombMarker;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.zeml.rotp_zkq.init.InitStands;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RotpKillerQueen.MOD_ID)
public class GameplayHandler {

    public static final Map<LivingEntity, UUID> userToBomb = new HashMap<>();
    public static final Map<LivingEntity,Boolean> changeDust = new HashMap<>();

    @SubscribeEvent(priority =  EventPriority.LOW)
    public static void onPlayerHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntityLiving();
        if(!entity.level.isClientSide){
            IStandPower.getStandPowerOptional(entity).ifPresent(power -> {
                if(power.getHeldAction() == InitStands.KQ_ENTITY_EX.get() && event.getAmount()>10){
                    power.setCooldownTimer(InitStands.KQ_ENTITY_EX.get(),200);
                    power.setCooldownTimer(InitStands.KQ_RESET.get(),200);

                }
                if(power.getHeldAction() == InitStands.KQ_RESET.get() && event.getAmount()>10){
                    power.setCooldownTimer(InitStands.KQ_ENTITY_EX.get(),200);
                    power.setCooldownTimer(InitStands.KQ_RESET.get(),1000);
                }

                StandType<?> KQ = InitStands.KQ_STAND.getStandType();
                if(power.getType() == KQ && event.getSource().getDirectEntity() instanceof StandArrowEntity){
                    power.unlockAction(InitStands.KQ_TIME_MARKER.get());
                    power.unlockAction(InitStands.PUT_VICTIM.get());
                    power.unlockAction(InitStands.KQ_RESET.get());
                }
            });
        }
    }

    @SubscribeEvent(priority =  EventPriority.LOW)
    public static void onEntityDead(LivingDeathEvent event){
        if(!event.getEntity().level.isClientSide){
            if(event.getEntity() instanceof LivingEntity){
                userToBomb.remove(event.getEntityLiving());
            }
        }
    }

    @SubscribeEvent(priority =  EventPriority.LOW)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(!event.player.level.isClientSide){
            PlayerEntity player = event.player;
            IStandPower.getStandPowerOptional(player).ifPresent(power -> {
                StandType<?> BZD = InitStands.STAMD_BITES_ZA_DUST.getStandType();
                if(power.getType() == BZD){
                    if(!BitesZaDustHandler.userToVictim.containsKey(player) || BitesZaDustHandler.userToVictim.get(player)==null){
                        int resolve = power.getResolveLevel();
                        MCUtil.runCommand(player,"stand clear @s");
                        MCUtil.runCommand(player,"stand give @s rotp_zkq:killer_queen");
                        power.setResolveLevel(resolve);
                    }
                }
                StandType<?> KQ = InitStands.KQ_STAND.getStandType();
                if(power.getType() != KQ){
                    userToBomb.remove(player);
                }
            });
        }
    }


}
