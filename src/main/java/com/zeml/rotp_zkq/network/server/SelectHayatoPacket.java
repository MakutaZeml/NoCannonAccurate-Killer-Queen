package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectHayatoPacket {
    private final int userId;
    private final long entityId;

    public SelectHayatoPacket(int userID, long entityId) {
        this.userId = userID;
        this.entityId = entityId;
    }

    public static void encode(SelectHayatoPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.userId);
        buf.writeLong(msg.entityId);
    }

    public static SelectHayatoPacket decode(PacketBuffer buf) {
        int userID = buf.readInt();
        long entityID = buf.readLong();
        return new SelectHayatoPacket(userID, entityID);
    }


    public static void handle(SelectHayatoPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity userID = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.userId);
            Entity entityID = com.github.standobyte.jojo.client.ClientUtil.getEntityById((int) msg.entityId);
            if (userID instanceof LivingEntity && entityID instanceof LivingEntity) {
                BitesZaDustHandler.userToVictim.put((LivingEntity) userID,(LivingEntity) entityID);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
