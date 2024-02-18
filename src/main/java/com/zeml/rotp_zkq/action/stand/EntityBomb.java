package com.zeml.rotp_zkq.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.zeml.rotp_zkq.init.InitSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;



public class EntityBomb extends StandEntityAction {
    public EntityBomb(StandEntityAction.Builder builder) {
        super(builder);
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




}
