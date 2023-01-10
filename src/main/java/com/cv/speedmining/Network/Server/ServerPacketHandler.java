package com.cv.speedmining.Network.Server;

import java.util.function.Supplier;

import com.cv.speedmining.Network.Client.ClientPacketHandler;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ServerPacketHandler {
    public float speed;

    public ServerPacketHandler() {
    }

    public ServerPacketHandler(float speed) {
        this.speed = speed;
        System.out.println("Generating Handler :" + speed);
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
