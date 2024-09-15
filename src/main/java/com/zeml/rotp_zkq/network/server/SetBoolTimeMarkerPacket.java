package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SetBoolTimeMarkerPacket {
    private final int entityID;
    private final boolean isTime;

    public SetBoolTimeMarkerPacket(int entityID, boolean isTime){
        this.entityID = entityID;
        this.isTime = isTime;
    }

    public static void encode(SetBoolTimeMarkerPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeBoolean(msg.isTime);
    }

    public static SetBoolTimeMarkerPacket decode(PacketBuffer buf){
        int userID = buf.readInt();
        boolean isTime = buf.readBoolean();
        return new SetBoolTimeMarkerPacket(userID,isTime);
    }

    public static void handle(SetBoolTimeMarkerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityID);
            if(entity instanceof LivingEntity){
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setHasTimeMark(msg.isTime);
                });
            }
        });
    }
}
