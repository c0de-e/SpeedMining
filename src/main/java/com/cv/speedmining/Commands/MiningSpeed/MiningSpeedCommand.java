package com.cv.speedmining.Commands.MiningSpeed;

import org.slf4j.Logger;

import com.cv.speedmining.SpeedMining;
import com.cv.speedmining.Network.SimpleChannelPacketHandler;
import com.cv.speedmining.Network.MiningSpeed.ServerPacketHandler;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.network.PacketDistributor;

public class MiningSpeedCommand {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("setMiningSpeedMultiplier")
                .requires(cs -> cs.hasPermission(Commands.LEVEL_ADMINS))
                .then(Commands.argument("speed", FloatArgumentType.floatArg())
                        .executes(ctx -> setSpeed(ctx.getSource(), FloatArgumentType.getFloat(ctx, "speed"))));
    }

    private static int setSpeed(CommandSourceStack cs, Float speed) {
        var level = cs.getLevel();
        SpeedMining.BlockBreakSpeedHandlerInstance.MineSpeed = speed;
        try {
            if (!level.isClientSide)
                SimpleChannelPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new ServerPacketHandler(speed));
            // SimpleChannelPacketHandler.sendToClient(new ServerPacketHandler(speed),
            // cs.getPlayer());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        System.out.println(String.format("%s changed the mining speed to %s!", cs.getPlayer().getName().getString(),
                SpeedMining.BlockBreakSpeedHandlerInstance.MineSpeed));

        for (var player : level.players()) {
            LogUtils.getLogger().debug(player.getName().getString() + "\'s latency is " + player.latency);
        }

        return 1;
    }
}
