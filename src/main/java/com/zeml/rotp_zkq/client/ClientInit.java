package com.zeml.rotp_zkq.client;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.render.entity.renderer.auxiliarstand.SheerHeartRenderer;
import com.zeml.rotp_zkq.client.render.entity.renderer.damaging.projectile.BubbleBombRenderer;
import com.zeml.rotp_zkq.client.render.entity.renderer.damaging.projectile.SnowBombRenderer;
import com.zeml.rotp_zkq.client.render.entity.renderer.stand.KillerQueenRenderer;
import com.zeml.rotp_zkq.client.ui.marker.EntityBombMarker;
import com.zeml.rotp_zkq.init.AddonStands;

import com.zeml.rotp_zkq.init.ModEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpKillerQueen.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    private static final IItemPropertyGetter STAND_ITEM_INVISIBLE = (itemStack, clientWorld, livingEntity) -> {
        return !ClientUtil.canSeeStands() ? 1 : 0;
    };
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = event.getMinecraftSupplier().get();

        RenderingRegistry.registerEntityRenderingHandler(AddonStands.KQ_STAND.getEntityType(), KillerQueenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SNOW_BOMB.get(), SnowBombRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BUBBLE_BOMB.get(), BubbleBombRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SHEAR_HEART.get(), SheerHeartRenderer::new);


        MarkerRenderer.Handler.addRenderer(new EntityBombMarker(mc));
    }
}
