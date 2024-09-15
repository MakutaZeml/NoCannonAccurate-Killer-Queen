package com.zeml.rotp_zkq.network.server;

import com.zeml.rotp_zkq.capability.entity.LivingData;
import com.zeml.rotp_zkq.capability.entity.LivingDataProvider;
import com.zeml.rotp_zkq.ultil.BitesZaDustHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SetCapabilityBomb {
    private final int entityID;
    private final UUID targetUUID;

    public SetCapabilityBomb(int entityID, UUID UUID){
        this.entityID =  entityID;
        this.targetUUID = UUID;

    }

    public static void encode(SetCapabilityBomb msg, PacketBuffer buf) {
        buf.writeInt(msg.entityID);
        buf.writeUUID(msg.targetUUID);
    }

    public static SetCapabilityBomb decode(PacketBuffer buf){
        int userID = buf.readInt();
        UUID targetUUID = buf.readUUID();
        return new SetCapabilityBomb(userID,targetUUID);
    }

    public static void handle(SetCapabilityBomb msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.entityID);
            if(entity instanceof LivingEntity){
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData ->{
                    playerData.setBomb(msg.targetUUID);
                });
            }
        });
    }
}
