package com.zeml.rotp_zkq.client.render.entity.model.auxiliarstand;
// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.zeml.rotp_zkq.entity.stand.SheerHeart;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;


public class SheerHeartModel extends EntityModel<SheerHeart> {
	private final ModelRenderer bb_main;

	public SheerHeartModel() {
		texWidth = 64;
		texHeight = 64;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 13).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 4.0F, 8.0F, 0.0F, false);
		bb_main.texOffs(22, 25).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		bb_main.texOffs(24, 13).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-3.0F, -4.0F, -5.0F, 6.0F, 3.0F, 10.0F, 0.0F, false);
		bb_main.texOffs(8, 25).addBox(-2.0F, -3.0F, 4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);
		bb_main.texOffs(22, 0).addBox(-5.0F, -4.0F, -3.0F, 10.0F, 3.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(0, 0).addBox(-2.0F, -5.0F, -6.0F, 4.0F, 3.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 4).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(0, 6).addBox(-1.5F, -6.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(5, 5).addBox(0.5F, -6.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		bb_main.texOffs(14, 25).addBox(5.0F, -3.0F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(0, 25).addBox(-6.0F, -3.0F, -3.0F, 1.0F, 3.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(56, 0).addBox(-1.0F, -7.5F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(SheerHeart p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}