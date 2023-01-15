package com.cv.speedmining.Commands.Logging;

import com.cv.speedmining.SpeedMining;
import com.cv.speedmining.Network.SimpleChannelPacketHandler;
import com.cv.speedmining.Network.Logging.LoggingServerPacketHandler;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class LoggingCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return LiteralArgumentBuilder.<CommandSourceStack>literal("debug")
                .then(Commands.literal("showDebugLogging")
                        .requires(cs -> cs.hasPermission(Commands.LEVEL_ADMINS))
                        .then(Commands.argument("on", BoolArgumentType.bool())
                                .executes(ctx -> setLogging(ctx.getSource(), BoolArgumentType.getBool(ctx, "on")))))

                .then(Commands.literal("displayMiningSpeed")
                        .executes(ctx -> displayMiningSpeed(ctx.getSource(), false))
                        .then(Commands.literal("displayToAll")
                                .requires(cs -> cs.hasPermission(Commands.LEVEL_GAMEMASTERS))
                                .executes(ctx -> displayMiningSpeed(ctx.getSource(), true)).build()));
    }

    public static int setLogging(CommandSourceStack cs, boolean on) {
        SpeedMining.LoggingHandlerInstance.ShowMiningLog = on;
        SimpleChannelPacketHandler.sendToClient(new LoggingServerPacketHandler(on), cs.getPlayer());
        return 1;
    }

    public static int displayMiningSpeed(CommandSourceStack cs, Boolean displayToAllPlayers) {
        var command = Component.literal(String.format("The current mining speed multiplier is %s",
                SpeedMining.BlockBreakSpeedHandlerInstance.MineSpeed));
        if (!displayToAllPlayers) {
            cs.getPlayer().displayClientMessage(command, true);
            return 1;
        }
        for (ServerPlayer player : cs.getLevel().players()) {
            player.displayClientMessage(command, true);
        }
        return 1;
    }
}
