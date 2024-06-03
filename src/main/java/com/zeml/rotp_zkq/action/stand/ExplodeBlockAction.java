package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.lwjgl.system.MathUtil;

public class ExplodeBlockAction extends StandEntityLightAttack {

    public ExplodeBlockAction(StandEntityLightAttack.Builder builder){
        super(builder);
    }

    private static final BlockState AIR = Blocks.AIR.defaultBlockState();

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            KQStandEntity kqStand = (KQStandEntity) standEntity;
            BlockPos blockPos = kqStand.getBlockPos();

            if(world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK ||world.getBlockState(blockPos).getBlock() != Blocks.BARRIER ){
                world.setBlockAndUpdate(blockPos, AIR);
            }

            float multi = (float) Math.sqrt(JojoModConfig.getCommonConfigInstance(false).standDamageMultiplier.get().floatValue());

            world.explode(userPower.getUser(),blockPos.getX(),blockPos.getY(),blockPos.getZ(),multi*2F, Explosion.Mode.NONE);
            kqStand.setIsBlockBomb(false);
        }
    }




}
