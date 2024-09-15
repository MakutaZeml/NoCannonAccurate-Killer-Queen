package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveTimerPacket {
    private final int entityId;

    public RemoveTimerPacket(int entityId){
        this.entityId = entityId;
    }

    public static void encode(RemoveTimerPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
    }

    public static RemoveTimerPacket decode(PacketBuffer buf){
        int entityId = buf.readInt();
        return new RemoveTimerPacket(entityId);
    }

    public static void handle(RemoveTimerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityId);
            if(entity instanceof LivingEntity){
                BitesZaDustHandler.timer.remove(entity);
            }
        });
    }


}
