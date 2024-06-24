package com.zeml.rotp_zkq.client.render.entity.model.stand;

import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransitionMultiple;
import com.zeml.rotp_zkq.action.stand.punch.PunchBomb;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;

import net.minecraft.client.renderer.model.ModelRenderer;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

public class KillerQueenModel extends HumanoidStandModel<KQStandEntity> {

	private final ModelRenderer belt;
	private final ModelRenderer belt_r1;
	private final ModelRenderer belt_r2;
	private final ModelRenderer belt_r3;

	public KillerQueenModel() {
		super();

		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;

		head.texOffs(17, 28).addBox(-4.0F, -9.0F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, true);
		head.texOffs(24, 33).addBox(-3.925F, -10.0F, -0.575F, 1.0F, 2.0F, 2.0F, 0.0F, true);
		head.texOffs(17, 28).addBox(3.0F, -9.0F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		head.texOffs(24, 33).addBox(3.025F, -10.0F, -0.575F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		torso.texOffs(25, 24).addBox(-4.0F, 5.7F, -1.5F, 8.0F, 6.0F, 3.0F, 0.4F, false);
		torso.texOffs(0, 16).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 6.0F, 5.0F, 0.0F, false);

		belt = new ModelRenderer(this);
		belt.setPos(0.0F, 24.0F, 0.0F);
		torso.addChild(belt);

		belt_r1 = new ModelRenderer(this);
		belt_r1.setPos(0.0F, -12.75F, 2.575F);
		belt.addChild(belt_r1);
		setRotationAngle(belt_r1, 0.1309F, 0.0F, 0.0F);
		belt_r1.texOffs(47, 24).addBox(-3.75F, -2.45F, -0.875F, 8.0F, 4.0F, 1.0F, 0.3F, false);

		belt_r2 = new ModelRenderer(this);
		belt_r2.setPos(0.1F, -11.55F, -2.425F);
		belt.addChild(belt_r2);
		setRotationAngle(belt_r2, -0.1484F, 0.0F, 0.0F);
		belt_r2.texOffs(56, 5).addBox(-2.1F, -3.15F, -0.875F, 4.0F, 4.0F, 1.0F, 0.1F, false);

		belt_r3 = new ModelRenderer(this);
		belt_r3.setPos(0.0F, -12.75F, -1.625F);
		belt.addChild(belt_r3);
		setRotationAngle(belt_r3, -0.1484F, 0.0F, 0.0F);
		belt_r3.texOffs(48, 0).addBox(-4.0F, -2.45F, -0.875F, 8.0F, 4.0F, 1.0F, 0.3F, false);

		leftArm.texOffs(44, 15).addBox(-1.5F, -2.7F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, false);
		leftArm.texOffs(0, 0).addBox(2.5F, -1.95F, -1.55F, 1.0F, 3.0F, 3.0F, 0.0F, false);

		leftForeArm.texOffs(52, 37).addBox(-2.5F, 0.0F, -2.25F, 4.0F, 1.0F, 4.0F, 0.3F, false);
		leftForeArm.texOffs(33, 46).addBox(-1.1F, -0.25F, 1.5F, 2.0F, 2.25F, 1.0F, 0.0F, false);

		rightArm.texOffs(44, 15).addBox(-2.5F, -2.7F, -2.0F, 4.0F, 5.0F, 4.0F, 0.1F, true);
		rightArm.texOffs(0, 0).addBox(-3.5F, -1.95F, -1.55F, 1.0F, 3.0F, 3.0F, 0.0F, true);

		rightForeArm.texOffs(52, 37).addBox(-1.5F, 0.0F, -2.25F, 4.0F, 1.0F, 4.0F, 0.3F, true);
		rightForeArm.texOffs(29, 0).addBox(-0.9F, -0.25F, 1.5F, 2.0F, 2.25F, 1.0F, 0.0F, false);

		leftLeg.texOffs(44, 49).addBox(-1.8F, 4.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.3F, false);
		leftLeg.texOffs(34, 43).addBox(-1.0F, 4.0F, -2.5F, 2.0F, 2.25F, 1.0F, 0.0F, false);

		leftLowerLeg.texOffs(43, 29).addBox(-1.75F, 2.25F, -2.25F, 4.0F, 4.0F, 4.0F, 0.3F, false);

		rightLeg.texOffs(44, 49).addBox(-2.0F, 4.75F, -2.0F, 4.0F, 1.0F, 4.0F, 0.3F, true);
		rightLeg.texOffs(34, 43).addBox(-1.0F, 4.0F, -2.5F, 2.0F, 2.25F, 1.0F, 0.0F, true);

		rightLowerLeg.texOffs(43, 29).addBox(-2.0F, 2.25F, -2.25F, 4.0F, 4.0F, 4.0F, 0.3F, true);
	}

	@Override
	protected RotationAngle[][] initSummonPoseRotations() {
		return new RotationAngle[][] {
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 20.02f, 22.45f, 8.71f),
						RotationAngle.fromDegrees(body, -17.15f, -34.42f, 4.66f),
						RotationAngle.fromDegrees(leftArm, -56.31f, 25.66f, 16.1f),
						RotationAngle.fromDegrees(leftForeArm, 42.5f, 0f, 0f),
						RotationAngle.fromDegrees(rightArm, -59.62f, -8.65f, -5.04f),
						RotationAngle.fromDegrees(rightArmJoint,-30f, 0f, -35f),
						RotationAngle.fromDegrees(rightForeArm, -52.93f, -26.95f, -18.9f),
						RotationAngle.fromDegrees(leftLeg, -22.28f, -1.57f, -15.88f),
						RotationAngle.fromDegrees(leftLegJoint,22.5f, 0f, 0f),
						RotationAngle.fromDegrees(leftLowerLeg, 45f, 0f, 0f),
						RotationAngle.fromDegrees(rightLeg, 20f, 0f, 32.5f),
						RotationAngle.fromDegrees(rightLowerLeg, 14.66f, -0.25f, -3.21f)
				},
				new RotationAngle[] {
						RotationAngle.fromDegrees(body, -46.21f, 65.13f, -29.27f),
						RotationAngle.fromDegrees(leftArm, -75.97f, 26.8f, 6.43f),
						RotationAngle.fromDegrees(leftArmJoint,-30f, 0f, 0f),
						RotationAngle.fromDegrees(leftForeArm, -65f, 0f, 0f),
						RotationAngle.fromDegrees(rightArm, -46.15f, -12.81f, -12.03f),
						RotationAngle.fromDegrees(rightForeArm, 0f, 0f, -57.5f),
						RotationAngle.fromDegrees(leftLeg, 0f, 0f, -15f),
						RotationAngle.fromDegrees(leftLowerLeg, 25f, 0f, 0f),
						RotationAngle.fromDegrees(rightLeg, 0f, 0f, 27.5f),
						RotationAngle.fromDegrees(rightLowerLeg, 16.39f, -3.3f, -5.68f)
				}

		};
	}

	@Override
	protected void initActionPoses() {
		ModelPose<KQStandEntity> bomb1_pose = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(leftArm,10.01f, -2.46f, -15.43f),
				RotationAngle.fromDegrees(rightArm,-37.5f, 0f, 0f),
				RotationAngle.fromDegrees(rightArmJoint,-17.5f, 0f, 0f),
				RotationAngle.fromDegrees(rightForeArm,-17.5f, 0f, 0f),
				RotationAngle.fromDegrees(leftLeg,0f, 0f, -5f),
				RotationAngle.fromDegrees(leftLowerLeg,10f, -2.5f, 0f),
				RotationAngle.fromDegrees(rightLeg,0f, 0f, 10f),
				RotationAngle.fromDegrees(rightLowerLeg,10f, 0f, 0f)
		});

		ModelPose<KQStandEntity> bomb2_pose = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(leftArm,25.08f, -7.39f, -16.31f),
				RotationAngle.fromDegrees(rightArm,-65.41f, -38.04f, -11.44f),
				RotationAngle.fromDegrees(rightForeArm,-30.91f, -10.59f, -17.06f)

		});

		ModelPose<KQStandEntity> bomb3_pose = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(rightForeArm,-8.41f, -10.59f, -17.06f)

		});

		ModelPose<KQStandEntity> bomb4_pose = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(rightForeArm,-38.41f, -10.59f, -17.06f),
				RotationAngle.fromDegrees(leftArm,10f, 0f, -15f),
				RotationAngle.fromDegrees(leftForeArm,-15f, 0f, 0f)

		});


		actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<KQStandEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(bomb1_pose)
						.addPose(0.5F, bomb2_pose)
						.build(bomb3_pose))
				.addPose(StandEntityAction.Phase.PERFORM, new ModelPoseTransitionMultiple.Builder<>(bomb1_pose)
						.addPose(0.5F, bomb3_pose)
						.build(bomb4_pose))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(bomb4_pose)
						.addPose(0.5F, bomb4_pose)
						.build(idlePose))
				.build(idlePose));

		super.initActionPoses();

		ModelPose<KQStandEntity> punchBomb1 = new ModelPose<>(
				RotationAngle.fromDegrees(head,20.02f, 22.45f, 8.71f),
				RotationAngle.fromDegrees(body,-17.15f, -34.42f, 4.66f),
				RotationAngle.fromDegrees(leftArm,-56.31f, 25.66f, 16.1f),
				RotationAngle.fromDegrees(leftForeArm,42.5f, 0f, 0f),
				RotationAngle.fromDegrees(rightArm,-59.62f, -8.65f, -5.04f),
				RotationAngle.fromDegrees(rightForeArm,-52.93f, -26.95f, -18.9f),
				RotationAngle.fromDegrees(leftLeg,-22.28f, -1.57f, -15.88f),
				RotationAngle.fromDegrees(leftLegJoint,22.5f, 0f, 0f),
				RotationAngle.fromDegrees(leftLowerLeg,45f, 0f, 0f),
				RotationAngle.fromDegrees(rightLeg,20f, 0f, 32.5f),
				RotationAngle.fromDegrees(rightLowerLeg,14.66f, -0.25f, -3.21f)
		);

		ModelPose<KQStandEntity> punchBomb2 = new ModelPose<>(
				RotationAngle.fromDegrees(leftArm,36.19f, 25.66f, 16.1f),
				RotationAngle.fromDegrees(leftForeArm,-40f, 0f, 0f),
				RotationAngle.fromDegrees(rightArm,-143.99f, 38.8f, -52.91f)
		);

		ModelPose<KQStandEntity> punchBomb3 = new ModelPose<>(
				RotationAngle.fromDegrees(body,-16.05f, 28.39f, -13.02f),
				RotationAngle.fromDegrees(leftArm,-43.72f, 42.96f, -52.36f),
				RotationAngle.fromDegrees(leftForeArm,-45f, 0f, 0f),
				RotationAngle.fromDegrees(rightArm,-210.73f, 29.62f, -77.87f)
		);

		ModelPose<KQStandEntity> punchBomb4 = new ModelPose<>(
				RotationAngle.fromDegrees(body,-23.96f, -53.21f, 14.35f),
				RotationAngle.fromDegrees(rightArm,-110.08f, 41.41f, -20.2f)
		);

		ModelPose<KQStandEntity> punchBomb5 = new ModelPose<>(
				RotationAngle.fromDegrees(body,-23.96f, -53.21f, 14.35f),
				RotationAngle.fromDegrees(rightArm,-29.21f, -60.54f, 12.3f),
				RotationAngle.fromDegrees(leftLeg,17.72f, -1.57f, -15.88f),
				RotationAngle.fromDegrees(rightLeg,32.4f, 50.33f, 58.53f),
				RotationAngle.fromDegrees(rightLowerLeg,14.66f, -0.25f, -3.21f)

		);

		actionAnim.put(PunchBomb.BOMB_PUNCH, new PosedActionAnimation.Builder<KQStandEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(punchBomb1)
						.addPose(.5F,punchBomb2)
						.build(punchBomb3))
				.addPose(StandEntityAction.Phase.PERFORM,new ModelPoseTransitionMultiple.Builder<>(punchBomb3)
						.addPose(.5F,punchBomb4)
						.build(punchBomb5))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(punchBomb5)
						.addPose(.5F,punchBomb5)
						.build(idlePose)
				).build(idlePose));
	}

	@Override
	protected ModelPose<KQStandEntity> initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, -5F, 30F, 0.0F),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, 12.5F, -30F, -15F),
				RotationAngle.fromDegrees(leftForeArm, -12.5F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 10F, 30F, 15F),
				RotationAngle.fromDegrees(rightForeArm, -15F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftLeg, 20F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftLowerLeg, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLowerLeg, 5F, 0.0F, 0.0F)
		});
	}

	@Override
	protected ModelPose<KQStandEntity> initIdlePose2Loop() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(leftArm, 7.5F, -30F, -15F),
				RotationAngle.fromDegrees(leftForeArm, -17.5F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 12.5F, 30F, 15F),
				RotationAngle.fromDegrees(rightForeArm, -17.5F, 0.0F, 0.0F)
		});
	}
}