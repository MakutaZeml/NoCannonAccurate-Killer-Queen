package com.zeml.rotp_zkq.client.ui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RotpKillerQueen.MOD_ID,bus =  Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BitesZaDust {
    protected static final ResourceLocation BITES_ZA_DUST_1 = new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/misc/bites_za_dust.png");
    protected static final ResourceLocation BITES_ZA_DUST_2 = new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/misc/bites_za_dust_2.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event){
        MatrixStack matrixStack = event.getMatrixStack();
        if(event.getType() == RenderGameOverlayEvent.ElementType.HELMET){
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null && BitesZaDustHandler.timer.containsKey(mc.player)){
                int screenWidth = event.getWindow().getGuiScaledWidth();
                mc.getTextureManager().bind(BITES_ZA_DUST_1);
                int textureWidth = 256;
                int textureHeight = 256;
                int x = (screenWidth - textureWidth) / 2;

                AbstractGui.blit(matrixStack, x, 0, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
            }
        }
    }

}
