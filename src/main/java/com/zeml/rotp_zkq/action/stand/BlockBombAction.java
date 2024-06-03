package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.zeml.rotp_zkq.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Optional;

public class BlockBombAction extends StandEntityAction {

    public BlockBombAction(StandEntityAction.Builder builder){
        super(builder);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if(power.getUser() != null){
            Optional<KQStandEntity> kq = power.getUser().level.getEntitiesOfClass(KQStandEntity.class,power.getUser().getBoundingBox().inflate(9), EntityPredicates.ENTITY_STILL_ALIVE)
                    .stream().filter(stand -> stand.getUser().getName().getString().equals(power.getUser().getName().getString())).findAny();
            if(kq.isPresent()){
                KQStandEntity kqStand = kq.get();
                if(kqStand.getIsBlockBomb()){
                    return InitStands.KQ_BLOCK_EXPLODE.get();
                }
            }
        }

        return super.replaceAction(power, target);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if(!world.isClientSide){
            if(userPower.getStamina() >124){
                LivingEntity user = userPower.getUser();
                double range = standEntity.getMaxRange();
                KQStandEntity killer = (KQStandEntity) standEntity;
                LivingEntity entity = standEntity.isManuallyControlled() ? standEntity:user;

                RayTraceResult ray = JojoModUtil.rayTrace(entity.getEyePosition(1.0F),entity.getLookAngle(),
                        range,world,entity,e->!(e.is(standEntity) || e.is(user)),0,0);
                if(ray.getType() == RayTraceResult.Type.BLOCK){
                    standEntity.moveTo(ray.getLocation());
                    BlockPos blockPos = ((BlockRayTraceResult)ray).getBlockPos();
                    killer.setBlockPos(blockPos);
                    killer.setIsBlockBomb(true);
                }
            }
        }

    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return target.getType() == ActionTarget.TargetType.BLOCK;
    }



    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.KQ_BLOCK_EXPLODE.get(), InitStands.KQ_BLOCK_QUIT.get()};
    }

}
