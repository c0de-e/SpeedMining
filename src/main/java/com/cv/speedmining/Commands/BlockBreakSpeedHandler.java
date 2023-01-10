package com.cv.speedmining.Commands;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakSpeedHandler {
    private static final Logger LOGGER = LogUtils.getLogger();
    public float MineSpeed = 1.0f;

    public BlockBreakSpeedHandler(float mineSpeed) {
        MineSpeed = mineSpeed;
    }

    public BlockBreakSpeedHandler() {
    }

    @SubscribeEvent
    public void playerEventHandler(PlayerEvent.BreakSpeed event) {
        // if (!event.getEntity().level.isClientSide)
        // return;
        // var player = event.getEntity();
        // boolean isBlockInstaminable =
        // event.getState().getBlockHardness(player.getEntityWorld(), event.getPos()) ==
        // 0;

        event.setNewSpeed(event.getOriginalSpeed() * MineSpeed);
        LOGGER.info(String.format("\nSide:%s\nPlayer:%s\nMultiplier:%s\nOriginal Speed: %s\nNew Speed:%s",
                event.getEntity().level.isClientSide ? "Client" : "Server", event.getEntity().getName().getString(),
                MineSpeed,
                event.getOriginalSpeed(), event.getNewSpeed()));
    }

    @SubscribeEvent
    public void onBlockDestroy(BlockEvent.BreakEvent event) {
        LOGGER.info("Broke block: " + event.getState().getBlock().getName().getString());
    }
}
