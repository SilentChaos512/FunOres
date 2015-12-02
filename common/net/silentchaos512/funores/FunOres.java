package net.silentchaos512.funores;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.core.proxy.CommonProxy;
import net.silentchaos512.funores.core.registry.SRegistry;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.gui.GuiHandlerFunOres;
import net.silentchaos512.funores.item.ModItems;
import net.silentchaos512.funores.lib.ExtraRecipes;
import net.silentchaos512.funores.world.FunOresGenerator;

@Mod(modid = FunOres.MOD_ID, name = FunOres.MOD_NAME, version = FunOres.VERSION_NUMBER, useMetadata = true)
public class FunOres {

  public static final String MOD_ID = "FunOres";
  public static final String MOD_NAME = "Fun Ores";
  public static final String VERSION_NUMBER = "@VERSION@";
  public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
  
  public Random random = new Random();
  public static Logger logger = LogManager.getLogger(MOD_NAME);

  @Instance(MOD_ID)
  public static FunOres instance;
  
  @SidedProxy(clientSide = "net.silentchaos512.funores.core.proxy.ClientProxy", serverSide = "net.silentchaos512.funores.core.proxy.CommonProxy")
  public static CommonProxy proxy;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    
    Config.init(event.getSuggestedConfigurationFile());
    
    ModBlocks.init();
    ModItems.init();
    
    proxy.preInit();
    
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerFunOres());
  }
  
  @EventHandler
  public void load(FMLInitializationEvent event) {
    
    SRegistry.addRecipesAndOreDictEntries();
    ExtraRecipes.init();
    
    Config.save();
    
    proxy.init();
    
    GameRegistry.registerWorldGenerator(new FunOresGenerator(), 0);
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    
    proxy.postInit();
    
    ConfigOptionOreGenBonus.initItemKeys();
  }
  
  public static CreativeTabs tabFunOres = new CreativeTabs("tabFunOres") {
    
    @Override
    public Item getTabIconItem() {
      
      return Item.getItemFromBlock(ModBlocks.meatOre);
    }
  };
}
