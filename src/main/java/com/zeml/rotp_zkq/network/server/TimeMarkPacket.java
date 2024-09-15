package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeMarkPacket {
    private final int userID;
    private final long time;

    public TimeMarkPacket(int userID, long time){
        this.userID = userID;
        this.time = time;
    }

    public static void encode(TimeMarkPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.userID);
        buf.writeLong(msg.time);
    }


    public static TimeMarkPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        long time = buf.readLong();
        return new TimeMarkPacket(entityId, time);
    }

    public static void handle(TimeMarkPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.userID);
            if (entity instanceof LivingEntity) {

            }
        });
        ctx.get().setPacketHandled(true);
    }


}
