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
		texWidth = 16;
		texHeight = 16;
		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 0.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.texOffs(6, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, 0.2F, false);
		bb_main.texOffs(0, 4).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.2F, false);
		bb_main.texOffs(6, 2).addBox(-0.5F, -1.5F, -1.0F, 1.0F, 1.0F, 2.0F, 0.2F, false);
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