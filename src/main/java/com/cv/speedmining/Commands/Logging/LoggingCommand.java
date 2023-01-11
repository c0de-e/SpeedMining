package com.cv.speedmining.Commands.Logging;

import com.cv.speedmining.SpeedMining;
import com.cv.speedmining.Network.SimpleChannelPacketHandler;
import com.cv.speedmining.Network.Logging.LoggingServerPacketHandler;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class LoggingCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("showDebugLogging")
                .requires(cs -> cs.hasPermission(Commands.LEVEL_ADMINS))
                .then(Commands.argument("on", BoolArgumentType.bool())
                        .executes(ctx -> setLogging(ctx.getSource(), BoolArgumentType.getBool(ctx, "on"))));
    }

    public static int setLogging(CommandSourceStack cs, boolean on) {
        SpeedMining.LoggingHandlerInstance.ShowMiningLog = on;
        SimpleChannelPacketHandler.sendToClient(new LoggingServerPacketHandler(on), cs.getPlayer());
        return 1;
    }
}
