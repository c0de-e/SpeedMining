package com.cv.speedmining;

import org.slf4j.Logger;

import com.cv.speedmining.Commands.SpeedMiningCommands;
import com.cv.speedmining.Commands.Logging.LoggingHandler;
import com.cv.speedmining.Commands.MiningSpeed.BlockBreakSpeedHandler;
import com.cv.speedmining.Network.SimpleChannelPacketHandler;
import com.cv.speedmining.Network.MiningSpeed.ServerPacketHandler;
import com.mojang.logging.LogUtils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.server.command.ConfigCommand;

// The value here should match an entry in the META-INF/mods.toml file
// @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
@Mod(SpeedMining.MODID)
public class SpeedMining {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "speedmining";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static BlockBreakSpeedHandler BlockBreakSpeedHandlerInstance;
    public static LoggingHandler LoggingHandlerInstance;

    public SpeedMining() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockBreakSpeedHandlerInstance = new BlockBreakSpeedHandler(1.25f);
        LoggingHandlerInstance = new LoggingHandler(false);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BlockBreakSpeedHandlerInstance);

        registerCommonEvents(modEventBus);
    }

    public void registerCommonEvents(IEventBus eventBus) {
        eventBus.register(SimpleChannelPacketHandler.class);
    }

    // @SubscribeEvent
    // public void onPlayerTickUpdate(PlayerTickEvent event) {
    //     double speedMult = 1.05;
    //     event.player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speedMult * 0.15);
    // }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        SimpleChannelPacketHandler.INSTANCE.sendTo(
                new ServerPacketHandler(SpeedMining.BlockBreakSpeedHandlerInstance.MineSpeed),
                ((ServerPlayer) event.getEntity()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
        new SpeedMiningCommands(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
        MinecraftForge.EVENT_BUS.register(event.getDispatcher());
    }
}
