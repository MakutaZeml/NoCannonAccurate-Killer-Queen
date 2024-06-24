package com.zeml.rotp_zkq.client.ui.marker;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
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
        AtomicBoolean render = new AtomicBoolean(false);
        IStandPower.getStandPowerOptional(this.mc.player).ifPresent(power ->{
                    StandType<?> KQ = InitStands.KQ_STAND.getStandType();
                    if(power.getType() == KQ){
                        Stream<KQStandEntity> stream = streamKQStandEntity(this.mc.player);
                        if(stream != null){
                            Optional<KQStandEntity> optionalKQStand = Optional.ofNullable(stream.findAny().orElse(null));
                            if(optionalKQStand != null && optionalKQStand.isPresent()){
                                KQStandEntity kqStand = optionalKQStand.get();
                                if(kqStand != null){
                                    render.set(kqStand.getIsBlockBomb());
                                }
                            }
                        }
                    }
                }
        );
        return render.get();
    }


    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        if(optionalKQStandEntity(this.mc.player).isPresent()){
            KQStandEntity kqStand = optionalKQStandEntity(this.mc.player).get();
            Vector3d vector3d = new Vector3d(kqStand.getBlockPos().getX()+.5F,kqStand.getBlockPos().getY()+.5F,kqStand.getBlockPos().getZ()+.5F);
            list.add(new EntityBombMarker.Marker(vector3d,true));
        }
    }


    private Optional<KQStandEntity> optionalKQStandEntity(PlayerEntity player){
        return Optional.ofNullable(player.level.getEntitiesOfClass(KQStandEntity.class, player.getBoundingBox().inflate(9), EntityPredicates.ENTITY_STILL_ALIVE)
                .stream().filter(stand -> stand.getUser().getName().getString().equals(player.getName().getString())).findAny().orElse(null));

    }

    private Stream<KQStandEntity> streamKQStandEntity(PlayerEntity player){
        return player.level.getEntitiesOfClass(KQStandEntity.class, player.getBoundingBox().inflate(9), EntityPredicates.ENTITY_STILL_ALIVE)
                .stream().filter(stand -> stand.getUser().getName().getString().equals(player.getName().getString()));
    }

}
