package com.zeml.rotp_zkq.client.ui.marker;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.RotpKillerQueen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;


import java.util.List;
import java.util.stream.Stream;


public class EntityBombMarker extends MarkerRenderer {

    public EntityBombMarker (Minecraft mc){
        super(new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/action/kq_entitybomb.png"), mc);
    }

    @Override
    protected boolean shouldRender() {
        return true;
        }

    protected static class Marker extends MarkerRenderer.MarkerInstance {
        public Marker(Vector3d pos, boolean outlined) {
            super(pos, outlined);
        }
    }

    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        IStandPower.getStandPowerOptional(this.mc.player).ifPresent((stand) ->{

            Targets(this.mc.player).forEach((livingEntity -> {
                list.add(new Marker(livingEntity.getPosition(partialTick).add(0,livingEntity.getBbHeight()*1.1,0),true));

            }));
        });
    }


    public static Stream<LivingEntity> Targets(@NotNull LivingEntity user){
        World world = user.level;
        String s_id = String.valueOf(user.getUUID());
        Stream<LivingEntity> entidades = world.getEntitiesOfClass(LivingEntity.class,user.getBoundingBox().inflate(100),
                EntityPredicates.ENTITY_STILL_ALIVE).stream().filter(entity -> entity.getTags().contains(s_id));
        return entidades;
    }

}
