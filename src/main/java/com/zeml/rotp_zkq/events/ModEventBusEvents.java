package com.zeml.rotp_zkq.events;

import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import com.zeml.rotp_zkq.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RotpKillerQueen.MOD_ID,bus= Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void addEntityAttribues(EntityAttributeCreationEvent event){
        event.put(ModEntityTypes.SHEAR_HEART.get(), SheerHeart.createMobAttributes().build());
    }
}
