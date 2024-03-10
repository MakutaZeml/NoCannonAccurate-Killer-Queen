package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.zeml.rotp_zkq.init.InitStands;
import org.jetbrains.annotations.NotNull;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zkq.init.InitSounds;
import com.zeml.rotp_zkq.network.AddonPackets;
import com.zeml.rotp_zkq.network.server.AddTagPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;



public class EntityBomb extends StandEntityAction {

    public EntityBomb(StandEntityAction.Builder builder) {
        super(builder);
    }


    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        String s_id = String.valueOf(power.getUser().getUUID());
        Entity exist = EntityRange(power,16,s_id);
        if (exist != null){
            return InitStands.KQ_ENTITY_EX.get();
        }
        return super.replaceAction(power, target);
    }


    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        LivingEntity user = userPower.getUser();
        String s_id = String.valueOf(user.getUUID());
        double range = standEntity.getMaxRange();

        Entity exist = EntityRange(userPower,2*range,s_id);

        if (exist != null){
            LivingEntity entity = standEntity.isManuallyControlled() ? standEntity:user;

            RayTraceResult ray = JojoModUtil.rayTrace(entity.getEyePosition(1.0F),entity.getLookAngle(),
                    range,world,entity,e->!(e.is(standEntity) || e.is(user)),0,0);
            if(ray.getType() == RayTraceResult.Type.ENTITY) {
                Entity target = ((EntityRayTraceResult) ray).getEntity();
                standEntity.moveTo(target.position());
            }
        }
    }

    @Override
    public void standPerform(@NotNull World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
       if(!world.isClientSide){
           LivingEntity user = userPower.getUser();
           String s_id = String.valueOf(user.getUUID());
           double range = standEntity.getMaxRange();



           Entity exist = EntityRange(userPower,2*range,s_id);

           if(exist == null){
               LivingEntity entity = standEntity.isManuallyControlled() ? standEntity:user;

               RayTraceResult ray = JojoModUtil.rayTrace(entity.getEyePosition(1.0F),entity.getLookAngle(),
                       range,world,entity,e->!(e.is(standEntity) || e.is(user)),0,0);
               if(ray.getType() == RayTraceResult.Type.ENTITY){
                   Entity target =  ((EntityRayTraceResult) ray).getEntity();
                   standEntity.playSound(InitSounds.USER_KQ.get(), 1,1);
                   standEntity.moveTo(target.position());
                   target.addTag(s_id);
                   if (user instanceof ServerPlayerEntity) {
                       AddonPackets.sendToClient(new AddTagPacket(target.getId(), s_id), (ServerPlayerEntity) user);
                   }
               }
           }
       }


    }

   public static Entity EntityRange(@NotNull IStandPower userPower, double range, String id){
        LivingEntity user= userPower.getUser();
        World world =user.level;
        Entity entidad = world.getEntities(null,user.getBoundingBox().inflate(range)).stream().filter(entity -> entity.getTags().contains(id)).findFirst().orElse(null);
        return entidad;
   }


    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.KQ_ENTITY_EX.get(), InitStands.KQ_ENTITY_QUIT.get()};
    }
}
