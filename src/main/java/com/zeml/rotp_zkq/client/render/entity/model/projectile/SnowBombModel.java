package com.zeml.rotp_zkq.client.render.entity.model.projectile;

// Made with Blockbench 4.9.0
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zkq.entity.damaging.projectile.SnowBombEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import org.jetbrains.annotations.NotNull;

public class SnowBombModel extends EntityModel<SnowBombEntity> {
	private final ModelRenderer bb_main;


	public SnowBombModel() {
		texWidth = 64;
		texHeight = 64;
		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(-2, -2).addBox(-2.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(-10, -10).addBox(-2.0F, -8.0F, -6.0F, 4.0F, 4.0F, 12.0F, 0.0F, false);
		bb_main.texOffs(-2, -2).addBox(-6.0F, -8.0F, -2.0F, 12.0F, 4.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(-4, -4).addBox(-5.0F, -9.0F, -3.0F, 10.0F, 6.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(-8, -8).addBox(-3.0F, -9.0F, -5.0F, 6.0F, 6.0F, 10.0F, 0.0F, false);
		bb_main.texOffs(-4, -4).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 10.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
	}


	public void setRotationAngle(@NotNull ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(SnowBombEntity entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
		bb_main.yRot = yRotationOffset * ((float)Math.PI / 180F);
		bb_main.xRot = xRotation * ((float)Math.PI / 180F);

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);


	}
}