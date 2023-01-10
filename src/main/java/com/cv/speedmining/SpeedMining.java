package com.cv.speedmining;

import org.slf4j.Logger;

import com.cv.speedmining.Commands.BlockBreakSpeedHandler;
import com.cv.speedmining.Commands.SpeedMiningCommands;
import com.cv.speedmining.Network.Server.SimpleChannelPacketHandler;
import com.mojang.logging.LogUtils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.command.ConfigCommand;

// The value here should match an entry in the META-INF/mods.toml file
// @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
@Mod(SpeedMining.MODID)
public class SpeedMining {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "speedmining";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static BlockBreakSpeedHandler BlockBreakSpeedHandlerInstance;

    public SpeedMining() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        BlockBreakSpeedHandlerInstance = new BlockBreakSpeedHandler(1.25f);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(BlockBreakSpeedHandlerInstance);

        registerCommonEvents(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public void registerCommonEvents(IEventBus eventBus) {
        eventBus.register(SimpleChannelPacketHandler.class);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
        new SpeedMiningCommands(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
        MinecraftForge.EVENT_BUS.register(event.getDispatcher());
    }

    // You can use EventBusSubscriber to automatically register all static methods
    // in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info(event.description());
        }
    }
}
