package com.zeml.rotp_zkq.client;

import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.render.entity.renderer.auxiliarstand.SheerHeartRenderer;
import com.zeml.rotp_zkq.client.render.entity.renderer.damaging.projectile.SnowBombRenderer;
import com.zeml.rotp_zkq.client.render.entity.renderer.stand.KillerQueenRenderer;
import com.zeml.rotp_zkq.init.AddonStands;

import com.zeml.rotp_zkq.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpKillerQueen.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.KQ_STAND.getEntityType(), KillerQueenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SNOW_BOMB.get(), SnowBombRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHEAR_HEART.get(), SheerHeartRenderer::new);
    }
}
