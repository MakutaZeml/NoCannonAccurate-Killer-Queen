package com.zeml.rotp_zkq.client.render.entity.renderer.auxiliarstand;

import com.zeml.rotp_zkq.RotpKillerQueen;

import com.zeml.rotp_zkq.client.render.entity.model.auxiliarstand.SheerHeartModel;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SheerHeartRenderer extends MobRenderer<SheerHeart, SheerHeartModel> {

   protected static final ResourceLocation TEXTURE = new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/entity/stand/sheer_heart.png");

   public SheerHeartRenderer(EntityRendererManager rendererManager){
       super(rendererManager,new SheerHeartModel(),0F);
   }

    @Override
    public ResourceLocation getTextureLocation(SheerHeart p_110775_1_) {
        return TEXTURE;
    }


}
