package silent.funores;

import java.util.Random;

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
import silent.funores.block.ModBlocks;
import silent.funores.configuration.Config;
import silent.funores.configuration.ConfigOptionOreGenBonus;
import silent.funores.core.proxy.CommonProxy;
import silent.funores.core.registry.SRegistry;
import silent.funores.core.util.LogHelper;
import silent.funores.gui.GuiHandlerFunOres;
import silent.funores.item.ModItems;
import silent.funores.world.FunOresGenerator;

@Mod(modid = FunOres.MOD_ID, name = FunOres.MOD_NAME, version = FunOres.VERSION_NUMBER, useMetadata = true)
public class FunOres {

  public static final String MOD_ID = "FunOres";
  public static final String MOD_NAME = "Fun Ores";
  public static final String VERSION_NUMBER = "@VERSION@";
  public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";

  @Instance(MOD_ID)
  public static FunOres instance;
  
  @SidedProxy(clientSide = "silent.funores.core.proxy.ClientProxy", serverSide = "silent.funores.core.proxy.CommonProxy")
  public static CommonProxy proxy;
  
  public Random random = new Random();
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    
    Config.init(event.getSuggestedConfigurationFile());
    
    ModBlocks.init();
    ModItems.init();
    
    Config.save();
    
    proxy.preInit();
    
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerFunOres());
  }
  
  @EventHandler
  public void load(FMLInitializationEvent event) {
    
    SRegistry.addRecipesAndOreDictEntries();
    
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
