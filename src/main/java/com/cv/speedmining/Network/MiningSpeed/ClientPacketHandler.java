package com.cv.speedmining.Network.MiningSpeed;

import java.util.function.Supplier;

import org.slf4j.Logger;

import com.cv.speedmining.SpeedMining;
import com.mojang.logging.LogUtils;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientPacketHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    public void encode(FriendlyByteBuf output) {
    }

    public static ClientPacketHandler decode(FriendlyByteBuf input) {
        return new ClientPacketHandler();
    }

    public static void handleServerPacket(ServerPacketHandler msg, Supplier<Context> ctx) {
        SpeedMining.BlockBreakSpeedHandlerInstance.MineSpeed = msg.speed;

        LOGGER.info(String.format("Changed %s's mining speed to %s",
                ctx.get().getSender().getName().getString(),
                msg.speed));
    }

    public static void handler(ClientPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
        });
        ctx.get().setPacketHandled(true);
    }
}
