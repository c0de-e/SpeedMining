package com.cv.speedmining.Network.MiningSpeed;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ServerPacketHandler {
    public float speed;

    public ServerPacketHandler(float speed) {
        this.speed = speed;
    }

    public ServerPacketHandler(FriendlyByteBuf buffer) {
        decode(buffer);
    }

    public static ServerPacketHandler decode(FriendlyByteBuf buffer) {
        return new ServerPacketHandler(buffer.readFloat());
    }

    public static void encode(ServerPacketHandler msg, FriendlyByteBuf buffer) {
        buffer.writeFloat(msg.speed);
    }

    public static void handler(ServerPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        // Make sure it's only executed on the physical client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleServerPacket(msg, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
