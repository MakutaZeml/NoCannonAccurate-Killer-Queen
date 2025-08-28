package com.zeml.rotp_zkq.client.render.entity.renderer.stand;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.render.entity.model.stand.KillerQueenModel;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class KillerQueenRenderer extends StandEntityRenderer<KQStandEntity, StandEntityModel<KQStandEntity>> {
    
    public KillerQueenRenderer(EntityRendererManager renderManager) {
        super(renderManager, StandModelRegistry.registerModel(new ResourceLocation(RotpKillerQueen.MOD_ID,"killer_queen"),KillerQueenModel::new),
                new ResourceLocation(RotpKillerQueen.MOD_ID, "textures/entity/stand/killer_queen.png"), 0);
    }

}
