package com.zeml.rotp_zkq.ultil;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = RotpKillerQueen.MOD_ID)
public class GameplayHandler {


}
