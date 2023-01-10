package com.cv.speedmining.Commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class SpeedMiningCommands {
    public SpeedMiningCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("speedMining")
                        .then(MiningSpeedCommand.register()));
    }
}
