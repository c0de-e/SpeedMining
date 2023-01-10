package com.cv.speedmining.Server;

import com.cv.speedmining.SpeedMining;
import com.cv.speedmining.Client.ClientPacketHandler;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SimpleChannelPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    private static int PACKET_ID = 22;
    public static SimpleChannel INSTANCE;

    public static void sendToClient(Object packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        System.out.println("Setting up instance!");
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(SpeedMining.MODID, "main"), () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals);

        INSTANCE.messageBuilder(ClientPacketHandler.class, PACKET_ID++)
                .decoder(ClientPacketHandler::decode)
                .encoder(ClientPacketHandler::encode)
                .consumerNetworkThread(ClientPacketHandler::handler)
                .add();

        INSTANCE.messageBuilder(ServerPacketHandler.class, PACKET_ID++)
                .decoder(ServerPacketHandler::decode)
                .encoder(ServerPacketHandler::encode)
                .consumerNetworkThread(ServerPacketHandler::handler)
                .add();
    }
}