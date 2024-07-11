package com.zeml.rotp_zkq.network;

import java.util.Optional;

import com.zeml.rotp_zkq.RotpKillerQueen;
import com.zeml.rotp_zkq.network.server.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class AddonPackets {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel channel;

    public static void init() {
        channel = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RotpKillerQueen.MOD_ID, "main_channel"))
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .simpleChannel();

        int packetIndex = 0;
        channel.registerMessage(packetIndex++, AddTagPacket.class, 
                AddTagPacket::encode, AddTagPacket::decode, AddTagPacket::handle, 
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, RemoveTagPacket.class, 
                RemoveTagPacket::encode, RemoveTagPacket::decode, RemoveTagPacket::handle, 
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, RemoveBombPacket.class,
                RemoveBombPacket::encode, RemoveBombPacket::decode, RemoveBombPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, TimeMarkPacket.class,
                TimeMarkPacket::encode, TimeMarkPacket::decode, TimeMarkPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, RemoveTimeMarkPacket.class,
                RemoveTimeMarkPacket::encode, RemoveTimeMarkPacket::decode, RemoveTimeMarkPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, SelectHayatoPacket.class,
                SelectHayatoPacket::encode, SelectHayatoPacket::decode, SelectHayatoPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(packetIndex++, RemoveHayatoPacket.class,
                RemoveHayatoPacket::encode, RemoveHayatoPacket::decode, RemoveHayatoPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
    
    public static void sendToClient(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            channel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    public static void sendToClientsTracking(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static void sendToClientsTrackingAndSelf(Object msg, Entity entity) {
        channel.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }
}
