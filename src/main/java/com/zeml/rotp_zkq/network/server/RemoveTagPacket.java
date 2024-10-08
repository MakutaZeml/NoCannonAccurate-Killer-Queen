package com.zeml.rotp_zkq.network.server;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class    RemoveTagPacket {
    private final int entityId;
    private final String tag;

    public RemoveTagPacket(int entityId, String tag) {
        this.entityId = entityId;
        this.tag = tag;
    }
    
    

    public static void encode(RemoveTagPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeUtf(msg.tag);
    }

    public static RemoveTagPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        String tag = buf.readUtf();
        return new RemoveTagPacket(entityId, tag);
    }

    public static void handle(RemoveTagPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityId);
            if (entity != null) {
                entity.removeTag(msg.tag);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
