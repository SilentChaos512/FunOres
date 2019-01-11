/*
 * Fun Ores -- FunOres
 * Copyright (C) 2018-2019 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;

@Mod(FunOres.MOD_ID)
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FunOres {
    public static final String MOD_ID = "funores";
    public static final String MOD_NAME = "Fun Ores";
    public static final String VERSION = "2.0.0";
    public static final String VERSION_SILENTLIB = "3.0.9";
    public static int BUILD_NUM = 0;
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase(Locale.ROOT) + ":";

    public static FunOres INSTANCE;
    private static SideProxy PROXY;

    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);

//    public static CreativeTabs tabFunOres = registry.makeCreativeTab("tabFunOres", () ->
//            new ItemStack(ModBlocks.meatOre, 1, random.nextInt(EnumMeat.values().length)));

    public FunOres() {
        INSTANCE = this;
        PROXY = DistExecutor.runForDist(() -> () -> new SideProxy.Client(), () -> () -> new SideProxy.Server());

        FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLModLoadingContext.get().getModEventBus().addListener(this::postInit);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void preInit(FMLPreInitializationEvent event) {
//        registry.setMod(this);

//        Config.init(event.getSuggestedConfigurationFile());

//        MinecraftForge.EVENT_BUS.register(this);

        PROXY.preInit(event);
    }

    private void init(FMLInitializationEvent event) {
//        Config.save();

//        FunOresGenerator worldGenerator = new FunOresGenerator();
//        GameRegistry.registerWorldGenerator(worldGenerator, 0);
//        MinecraftForge.ORE_GEN_BUS.register(worldGenerator);

        PROXY.init(event);
    }

    private void postInit(FMLPostInitializationEvent event) {
//        ConfigOptionOreGenBonus.initItemKeys();
//        ConfigItemDrop.listErrorsInLog();

        PROXY.postInit(event);
    }

    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        ModBlocks.registerAll(event);
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event) {
        ModItems.registerAll(event);
    }

//    @SubscribeEvent
//    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
//        EntityPlayer player = event.player;
//        if (player != null) {
//            ConfigItemDrop.listErrorsInChat(player);
//        }
//    }
}
