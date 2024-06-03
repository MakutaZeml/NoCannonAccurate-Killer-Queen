package com.zeml.rotp_zkq.client.ui.marker;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.List;
import java.util.Optional;

public class BlockBombMarker extends MarkerRenderer {
    public BlockBombMarker(Minecraft mc) {
        super(new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/action/kq_block_bomb.png"), mc);
    }

    @Override
    protected boolean shouldRender() {
        if(optionalKQStandEntity(this.mc.player).isPresent()){
            KQStandEntity kqStand = optionalKQStandEntity(this.mc.player).get();
            return kqStand.getIsBlockBomb();
        }
        return false;
    }

    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        if(optionalKQStandEntity(this.mc.player).isPresent()){
            KQStandEntity kqStand = optionalKQStandEntity(this.mc.player).get();
            Vector3d vector3d = new Vector3d(kqStand.getBlockPos().getX(),kqStand.getBlockPos().getY(),kqStand.getBlockPos().getZ());
            list.add(new EntityBombMarker.Marker(vector3d,true));
        }
    }


    private Optional<KQStandEntity> optionalKQStandEntity(PlayerEntity player){
        return player.level.getEntitiesOfClass(KQStandEntity.class, player.getBoundingBox().inflate(9), EntityPredicates.ENTITY_STILL_ALIVE)
                .stream().filter(stand -> stand.getUser().getName().getString().equals(player.getName().getString())).findAny();

    }

}
