package net.silentchaos512.funores;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class SideProxy {
    void preInit(FMLPreInitializationEvent event) { }

    void init(FMLInitializationEvent event) { }

    void postInit(FMLPostInitializationEvent event) { }

    public static class Client extends SideProxy {
        // empty
    }

    public static class Server extends SideProxy {
        // empty
    }
}
