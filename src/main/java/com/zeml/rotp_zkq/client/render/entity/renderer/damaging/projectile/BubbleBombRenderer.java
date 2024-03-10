package com.zeml.rotp_zkq.client.render.entity.renderer.damaging.projectile;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.zeml.rotp_zkq.client.render.entity.model.projectile.BubbleBombModel;
import com.zeml.rotp_zkq.entity.damaging.projectile.BubbleBombEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BubbleBombRenderer extends SimpleEntityRenderer<BubbleBombEntity, BubbleBombModel> {
    public BubbleBombRenderer(EntityRendererManager rendererManager){
        super(rendererManager,new BubbleBombModel(), new ResourceLocation(JojoMod.MOD_ID, "textures/entity/projectiles/hamon_bubble.png"));
    }
}
