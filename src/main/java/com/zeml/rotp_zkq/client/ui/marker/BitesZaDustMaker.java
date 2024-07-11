package com.zeml.rotp_zkq.client.ui.marker;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class BitesZaDustMaker extends MarkerRenderer {

    public BitesZaDustMaker (Minecraft mc){
        super(new ResourceLocation(RotpKillerQueen.MOD_ID,"textures/action/put_dust.png"), mc);
    }

    @Override
    protected boolean shouldRender() {
        return BitesZaDustHandler.userToVictim.containsKey(this.mc.player);
    }

    @Override
    protected void updatePositions(List<MarkerInstance> list, float partialTick) {
        MCUtil.entitiesAround(LivingEntity.class,this.mc.player,100,false,
                livingEntity -> livingEntity == BitesZaDustHandler.userToVictim.get(this.mc.player))
                .forEach(livingEntity -> {
                    list.add(new EntityBombMarker.Marker(livingEntity.getPosition(partialTick).add(0,livingEntity.getBbHeight()*1.1,0),true));
        });
    }
}
