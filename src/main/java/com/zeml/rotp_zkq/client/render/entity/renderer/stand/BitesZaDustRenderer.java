package com.zeml.rotp_zkq.client.render.entity.renderer.stand;

import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.render.entity.model.stand.BitesZaDustModel;
import com.zeml.rotp_zkq.client.render.entity.model.stand.KillerQueenModel;
import com.zeml.rotp_zkq.entity.stand.stands.BZDEntity;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BitesZaDustRenderer extends StandEntityRenderer<BZDEntity, BitesZaDustModel> {

    public BitesZaDustRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BitesZaDustModel(), new ResourceLocation(RotpKillerQueen.MOD_ID, "textures/entity/stand/void.png"), 0);
    }
}
