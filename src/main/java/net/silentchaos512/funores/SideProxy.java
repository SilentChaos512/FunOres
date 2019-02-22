package net.silentchaos512.funores;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;
import net.silentchaos512.funores.world.FunOresWorldFeatures;

class SideProxy {
    SideProxy() {
        getLifeCycleEventBus().addListener(this::commonSetup);
        getLifeCycleEventBus().addListener(this::imcEnqueue);
        getLifeCycleEventBus().addListener(this::imcProcess);

        getLifeCycleEventBus().addListener(ModBlocks::registerAll);
        getLifeCycleEventBus().addListener(ModItems::registerAll);

//        ModLoot.init();
    }

    private static IEventBus getLifeCycleEventBus() {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(FunOresWorldFeatures::addFeaturesToBiomes);
    }

    private void imcEnqueue(InterModEnqueueEvent event) {
    }

    private void imcProcess(InterModProcessEvent event) {
    }

    static class Client extends SideProxy {
        Client() {
            SideProxy.getLifeCycleEventBus().addListener(this::clientSetup);
        }

        private void clientSetup(FMLClientSetupEvent event) {
            FunOres.LOGGER.info("Gems clientSetup");
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
