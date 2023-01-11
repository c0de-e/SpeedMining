package com.cv.speedmining.Commands.MiningSpeed;

import org.slf4j.Logger;

import com.cv.speedmining.SpeedMining;
import com.mojang.logging.LogUtils;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakSpeedHandler {
    public float MineSpeed = 1.0f;
    private static final Logger LOGGER = LogUtils.getLogger();

    public BlockBreakSpeedHandler(float mineSpeed) {
        MineSpeed = mineSpeed;
    }

    public BlockBreakSpeedHandler() {
    }

    @SubscribeEvent
    public void playerEventHandler(PlayerEvent.BreakSpeed event) {
        event.setNewSpeed(event.getOriginalSpeed() * MineSpeed);
        if (SpeedMining.LoggingHandlerInstance.ShowMiningLog)
            LOGGER.info(String.format("\nSide:%s\nPlayer:%s\nMultiplier:%s\nOriginal Speed: %s\nNew Speed:%s",
                    event.getEntity().level.isClientSide ? "Client" : "Server", event.getEntity().getName().getString(),
                    MineSpeed,
                    event.getOriginalSpeed(), event.getNewSpeed()));
    }
}
