package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveHayatoPacket {
    private final int userId;
    public RemoveHayatoPacket(int userID) {
        this.userId = userID;
    }

    public static void encode(RemoveHayatoPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.userId);
    }

    public static RemoveHayatoPacket decode(PacketBuffer buf) {
        int userID = buf.readInt();
        return new RemoveHayatoPacket(userID);
    }


    public static void handle(RemoveHayatoPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity userID = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.userId);
            if (userID instanceof LivingEntity ) {
                BitesZaDustHandler.userToVictim.remove((LivingEntity) userID);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
