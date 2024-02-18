package com.zeml.rotp_zkq.client.render.entity.renderer.damaging.projectile;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.render.entity.model.projectile.SnowBombModel;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

    public class SnowBombRenderer extends SimpleEntityRenderer<SnowBombEntity, SnowBombModel> {

    public SnowBombRenderer(EntityRendererManager rendererManager){
        super(rendererManager,new SnowBombModel(), new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/entity/projectiles/snow_bomb.png"));
    }

}
