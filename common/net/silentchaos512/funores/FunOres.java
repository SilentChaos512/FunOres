package net.silentchaos512.funores;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.api.FunOresAPI;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.core.proxy.CommonProxy;
import net.silentchaos512.funores.core.registry.SRegistry;
import net.silentchaos512.funores.gui.GuiHandlerFunOres;
import net.silentchaos512.funores.item.ModItems;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.ExtraRecipes;
import net.silentchaos512.funores.lib.ModDamageSources;
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
    ModDamageSources.init();

    proxy.preInit();

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerFunOres());
    FMLCommonHandler.instance().bus().register(this);
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
    initAlloySmelterRecipes();

    ConfigOptionOreGenBonus.initItemKeys();
    ConfigItemDrop.listErrorsInLog();
  }

  @SubscribeEvent
  public void onPlayerLoggedIn(PlayerLoggedInEvent event) {

    EntityPlayer player = event.player;
    if (player != null) {
      ConfigItemDrop.listErrorsInChat(player);
    }
  }

  /*
   * Alloy Smelter recipe stuff.
   */

  private void initAlloySmelterRecipes() {

    //@formatter:off

    addAlloySmelterRecipe(
        EnumAlloy.BRONZE.getName(), EnumAlloy.BRONZE.getIngot(), 4,
        200, 0.5f,
        "ingotCopper*3", "ingotTin*1");

    addAlloySmelterRecipe(
        EnumAlloy.BRASS.getName(), EnumAlloy.BRASS.getIngot(), 4,
        200, 0.5f,
        "ingotCopper*3", "ingotZinc*1");

    ItemStack coal = new ItemStack(Items.coal, 2);
    addAlloySmelterRecipe(
        EnumAlloy.STEEL.getName(), EnumAlloy.STEEL.getIngot(), 1,
        800, 0.7f,
        "ingotIron*1", coal);

    addAlloySmelterRecipe(
        EnumAlloy.INVAR.getName(), EnumAlloy.INVAR.getIngot(), 3,
        400, 0.7f,
        "ingotIron*2", "ingotNickel*1");

    addAlloySmelterRecipe(
        EnumAlloy.ELECTRUM.getName(), EnumAlloy.ELECTRUM.getIngot(), 2,
        400, 1.0f,
        "ingotGold*1", "ingotSilver*1");

    ItemStack enderEyes = new ItemStack(Items.ender_eye);
    enderEyes.stackSize = 4;
    addAlloySmelterRecipe(
        EnumAlloy.ENDERIUM.getName(), EnumAlloy.ENDERIUM.getIngot(), 4,
        800, 2.0f,
        "ingotTin*2", "ingotSilver*1", "ingotPlatinum*1", enderEyes);

    ItemStack prismarineCrystals = new ItemStack(Items.prismarine_crystals, 12);
    AlloySmelterRecipeObject gemsForPrismarine =
        new AlloySmelterRecipeObject("gemSapphire*2", "gemDiamond*1");
    addAlloySmelterRecipe(
        EnumAlloy.PRISMARIINIUM.getName(), EnumAlloy.PRISMARIINIUM.getIngot(), 4,
        800, 2.0f,
        "ingotSilver*2", gemsForPrismarine, "ingotTitanium*1", prismarineCrystals);

    //@formatter:on
  }

  private void addAlloySmelterRecipe(String recipeName, ItemStack output, int outputCount,
      int cookTime, float experience, Object... inputs) {

    boolean enabled = Config.getConfiguration()
        .get(Config.CATEGORY_RECIPE_ALLOY_SMELTER, recipeName, true).getBoolean();
    if (enabled) {
      FunOresAPI.addAlloySmelterRecipe(output, outputCount, cookTime, experience, inputs);
    }
  }

  public static CreativeTabs tabFunOres = new CreativeTabs("tabFunOres") {

    @Override
    public Item getTabIconItem() {

      return Item.getItemFromBlock(ModBlocks.meatOre);
    }
  };
}
