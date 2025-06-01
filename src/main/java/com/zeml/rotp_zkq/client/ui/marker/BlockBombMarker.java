package com.zeml.rotp_zkq.client.ui.marker;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.client.render.entity.model.stand.KillerQueenModel;
import com.zeml.rotp_zkq.entity.stand.stands.KQStandEntity;
import com.zeml.rotp_zkq.init.InitStands;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class BlockBombMarker extends MarkerRenderer {
    public BlockBombMarker(Minecraft mc) {
        super(new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/action/kq_block_bomb.png"), mc);
    }

    @Override
    protected boolean shouldRender() {
        boolean render = false;

        IStandManifestation standManifestation = IStandPower.getPlayerStandPower(this.mc.player).getStandManifestation();
        if(standManifestation instanceof KQStandEntity){
           render = ((KQStandEntity) standManifestation).getIsBlockBomb();

        }
        return render;
    }


    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        IStandManifestation standManifestation = IStandPower.getPlayerStandPower(this.mc.player).getStandManifestation();
        if(standManifestation instanceof KQStandEntity){
            KQStandEntity kqStand = (KQStandEntity) standManifestation;
            Vector3d vector3d = new Vector3d(kqStand.getBlockPos().getX()+.5F,kqStand.getBlockPos().getY()+.5F,kqStand.getBlockPos().getZ()+.5F);
            list.add(new EntityBombMarker.Marker(vector3d,true));
        }
    }

}
