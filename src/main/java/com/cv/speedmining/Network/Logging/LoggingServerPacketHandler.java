package com.cv.speedmining.Network.Logging;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class LoggingServerPacketHandler {
    public boolean logging;

    public LoggingServerPacketHandler(boolean logging) {
        this.logging = logging;
    }

    public static LoggingServerPacketHandler decode(FriendlyByteBuf buffer) {
        return new LoggingServerPacketHandler(buffer.readBoolean());
    }

    public static void encode(LoggingServerPacketHandler msg, FriendlyByteBuf buffer) {
        buffer.writeBoolean(msg.logging);
    }

    public static void handler(LoggingServerPacketHandler msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
        // Make sure it's only executed on the physical client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> LoggingClientPacketHandler.handleServerPacket(msg, ctx)));
        ctx.get().setPacketHandled(true);
    }
}
