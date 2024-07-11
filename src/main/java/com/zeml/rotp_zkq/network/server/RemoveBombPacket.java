package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.ultil.GameplayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveBombPacket {
    private final int userID;
    public RemoveBombPacket(int userID){
        this.userID = userID;
    }


    public static void encode(RemoveBombPacket msg, PacketBuffer buf) {
        buf.writeInt(msg.userID);
    }
    public static RemoveBombPacket decode(PacketBuffer buf) {
        int userID = buf.readInt();
        return new RemoveBombPacket(userID);
    }

    public static void handle(RemoveBombPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity user = com.github.standobyte.jojo.client.ClientUtil.getEntityById( msg.userID);
            if (user instanceof LivingEntity) {
                GameplayHandler.userToBomb.remove(user);
            }
        });
        ctx.get().setPacketHandled(true);
    }


}
