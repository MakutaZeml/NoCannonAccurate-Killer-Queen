package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveTimeMarkPacket {
    private final int userID;

    public RemoveTimeMarkPacket(int userID, long time){
        this.userID = userID;
    }

    public static void encode(RemoveTimeMarkPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.userID);
    }


    public static RemoveTimeMarkPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        long time = buf.readLong();
        return new RemoveTimeMarkPacket(entityId, time);
    }

    public static void handle(RemoveTimeMarkPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.userID);
            if (entity instanceof LivingEntity) {
                BitesZaDustHandler.userToTime.remove(entity.getName().getString());
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
