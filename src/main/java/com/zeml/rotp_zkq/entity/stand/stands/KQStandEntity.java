package com.zeml.rotp_zkq.entity.stand.stands;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import net.minecraft.block.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class KQStandEntity extends StandEntity {
    private static final DataParameter<Boolean> IS_BLOCK_BOMB = EntityDataManager.defineId(KQStandEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> X = EntityDataManager.defineId(KQStandEntity.class,DataSerializers.FLOAT);
    private static final DataParameter<Float> Y = EntityDataManager.defineId(KQStandEntity.class,DataSerializers.FLOAT);
    private static final DataParameter<Float> Z = EntityDataManager.defineId(KQStandEntity.class,DataSerializers.FLOAT);


    public KQStandEntity(StandEntityType<KQStandEntity> type, World world) {
        super(type, world);
    }


    @Override
    public void tick(){
        if(!level.isClientSide){
            if(this.distanceToSqr(entityData.get(X),entityData.get(Y),entityData.get(Z))>2500){
                entityData.set(IS_BLOCK_BOMB,false);
            }
            if(level.getBlockState(getBlockPos()).getBlock()== Blocks.AIR || level.getBlockState(getBlockPos()).getBlock()== Blocks.WATER
            || level.getBlockState(getBlockPos()).getBlock()== Blocks.LAVA){
                entityData.set(IS_BLOCK_BOMB,false);
            }
        }
        super.tick();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_BLOCK_BOMB, false);
        entityData.define(X,0F);
        entityData.define(Y,0F);
        entityData.define(Z,0F);
    }

    public void setIsBlockBomb(boolean bomb){
        entityData.set(IS_BLOCK_BOMB,bomb);
    }

    public void setBlockPos(BlockPos blockPos){
        entityData.set(X, (float) blockPos.getX());
        entityData.set(Y,(float) blockPos.getY());
        entityData.set(Z,(float) blockPos.getZ());
    }

    public boolean getIsBlockBomb(){
        return entityData.get(IS_BLOCK_BOMB);
    }

    public BlockPos getBlockPos(){
        return new BlockPos(entityData.get(X),entityData.get(Y),entityData.get(Z));
    }

}
