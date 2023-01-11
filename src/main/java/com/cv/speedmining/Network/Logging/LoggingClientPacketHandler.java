package com.cv.speedmining.Network.Logging;

import java.util.function.Supplier;

import com.cv.speedmining.Commands.Logging.LoggingCommand;

import net.minecraftforge.network.NetworkEvent.Context;

public class LoggingClientPacketHandler {

    public static void handleServerPacket(LoggingServerPacketHandler msg, Supplier<Context> ctx) {
        LoggingCommand.setLogging(null, msg.logging);
    }

}
