package com.zeml.rotp_zkq.client.render.entity.model.stand;

import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransition;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransitionMultiple;
import com.zeml.rotp_zkq.action.stand.ItemFBombExplode;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class KillerQueenModel extends HumanoidStandModel<KQStandEntity> {
	private final ModelRenderer belt;

	public KillerQueenModel() {
		super();

		addHumanoidBaseBoxes(null);
		texWidth = 128;
		texHeight = 128;

		head.texOffs(88, 35).addBox(-3.5F, -8.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		head.texOffs(90, 42).addBox(-3.5F, -9.0F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		head.texOffs(92, 53).addBox(-3.5F, -10.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		head.texOffs(102, 42).addBox(2.5F, -9.0F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		head.texOffs(102, 35).addBox(2.5F, -8.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		head.texOffs(104, 49).addBox(2.5F, -10.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);


		belt = new ModelRenderer(this);
		belt.setPos(0.0F, 24.0F, 0.0F);
		torso.addChild(belt);
		belt.texOffs(24, 73).addBox(-2.5F, -20.0F, -2.3F, 5.0F, 6.0F, 1.0F, 0.0F, false);
		belt.texOffs(87, 12).addBox(-1.0F, -13.0F, -3.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
		belt.texOffs(81, 2).addBox(-2.0F, -12.0F, -3.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		belt.texOffs(95, 1).addBox(1.0F, -12.0F, -3.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		belt.texOffs(112, 119).addBox(-5.0F, -14.0F, -2.5F, 3.0F, 4.0F, 5.0F, 0.0F, false);
		belt.texOffs(118, 103).addBox(-2.0F, -13.0F, 1.5F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		belt.texOffs(112, 108).addBox(2.0F, -14.0F, -2.5F, 3.0F, 4.0F, 5.0F, 0.0F, false);
		belt.texOffs(110, 95).addBox(-2.0F, -14.0F, -2.5F, 4.0F, 1.0F, 5.0F, 0.0F, false);

		leftArm.texOffs(119, 24).addBox(1.5F, -1.5F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		rightArm.texOffs(0, 108).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

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

		actionAnim.put(ItemFBombExplode.BOMB, new PosedActionAnimation.Builder<KQStandEntity>()
				.addPose(StandEntityAction.Phase.WINDUP,new ModelPoseTransition<>(bomb1_pose,bomb2_pose))
				.addPose(StandEntityAction.Phase.PERFORM,new ModelPoseTransitionMultiple.Builder<>(bomb2_pose)
						.addPose(0.5F,bomb3_pose).build(bomb4_pose))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(bomb4_pose)
						.addPose(0.5F,bomb4_pose).build(idlePose))
				.build(idlePose));

		actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<KQStandEntity>()
				.addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPose<>(new RotationAngle[] {
						new RotationAngle(body, 0.0F, -0.48F, 0.0F),
						new RotationAngle(leftArm, 0.0F, 0.0F, 0.0F),
						new RotationAngle(leftForeArm, 0.0F, 0.0F, 0.0F),
						new RotationAngle(rightArm, -1.0908F, 0.0F, 1.5708F),
						new RotationAngle(rightForeArm, 0.0F, 0.0F, 0.0F)
				}))
				.build(idlePose));

		super.initActionPoses();
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