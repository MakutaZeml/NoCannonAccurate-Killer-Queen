package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TimerPacket {
    private final int entityId;
    private final long timer;

    public TimerPacket(int entityId, long timer){
        this.entityId = entityId;
        this.timer = timer;
    }

    public static void encode(TimerPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeLong(msg.timer);
    }


    public static TimerPacket decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        long timer = buf.readLong();
        return new TimerPacket(entityId, timer);
    }

    public static void handle(TimerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityId);
            if(entity instanceof LivingEntity){
                BitesZaDustHandler.timer.put((LivingEntity) entity,(int) msg.timer);
            }
        });
    }
}
