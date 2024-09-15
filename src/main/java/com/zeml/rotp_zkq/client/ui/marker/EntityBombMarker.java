package com.zeml.rotp_zkq.client.ui.marker;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import com.zeml.rotp_zkq.init.InitStands;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;


public class EntityBombMarker extends MarkerRenderer {

    public EntityBombMarker (Minecraft mc){
        super(new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/action/kq_entitybomb.png"), mc);
    }

    /*
    @Override
    protected boolean shouldRender() {
        return GameplayHandler.userToBomb.containsKey(this.mc.player);
    }

     */

    protected static class Marker extends MarkerRenderer.MarkerInstance {
        public Marker(Vector3d pos, boolean outlined) {
            super(pos, outlined);
        }
    }



    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        MCUtil.entitiesAround(Entity.class,this.mc.player,100,false,
                livingEntity -> livingEntity.getUUID().equals(getTarget(this.mc.player)))
                .forEach(entity -> {
                    list.add(new Marker(entity.getPosition(partialTick).add(0,entity.getBbHeight()*1.1,0),true));
        });
    }

    @Override
    protected boolean shouldRender() {
        return !getTarget(this.mc.player).equals(this.mc.player.getUUID());
    }




    private UUID getTarget(PlayerEntity player){
        LazyOptional<LivingData> livingDataOptional = player.getCapability(LivingDataProvider.CAPABILITY);
        return livingDataOptional.map(LivingData::getBomb).isPresent()? livingDataOptional.map(LivingData::getBomb).get():player.getUUID();
    }
}
