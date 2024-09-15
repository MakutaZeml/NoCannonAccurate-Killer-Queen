package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PutTimerPacket {
    private final int userID;

    public PutTimerPacket(int entityId){
        this.userID = entityId;
    }

    public static void encode(PutTimerPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.userID);
    }

    public static PutTimerPacket decode(PacketBuffer buf){
        int userID = buf.readInt();
        return new PutTimerPacket(userID);
    }

    public static void handle(PutTimerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.userID);
            if(entity instanceof LivingEntity){
                BitesZaDustHandler.timer.putIfAbsent((LivingEntity) entity,0);
            }
        });
    }

}
