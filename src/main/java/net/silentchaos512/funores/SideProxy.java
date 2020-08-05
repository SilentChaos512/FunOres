package net.silentchaos512.funores;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.funores.data.DataGenerators;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;
import net.silentchaos512.funores.init.ModLoot;
import net.silentchaos512.funores.world.FunOresWorldFeatures;

class SideProxy {
    SideProxy() {
        IEventBus eventBus = getLifeCycleEventBus();

        eventBus.addListener(DataGenerators::gatherData);

        eventBus.addListener(this::commonSetup);

        eventBus.addGenericListener(Block.class, ModBlocks::registerAll);
        eventBus.addGenericListener(Feature.class, FunOresWorldFeatures::registerFeatures);
        eventBus.addGenericListener(Item.class, ModItems::registerAll);
        eventBus.addGenericListener(Placement.class, FunOresWorldFeatures::registerPlacements);

        ModLoot.init();
    }

    private static IEventBus getLifeCycleEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        Config.init();
        DeferredWorkQueue.runLater(FunOresWorldFeatures::addFeaturesToBiomes);
    }

    static class Client extends SideProxy {
        Client() {
            SideProxy.getLifeCycleEventBus().addListener(this::clientSetup);
        }

        private void clientSetup(FMLClientSetupEvent event) {
        }
    }

    static class Server extends SideProxy {
        Server() {
            SideProxy.getLifeCycleEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {
        }
    }
}
