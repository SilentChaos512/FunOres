package net.silentchaos512.funores;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.api.FunOresAPI;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipeObject;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.gui.GuiHandlerFunOres;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModFluids;
import net.silentchaos512.funores.init.ModItems;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumDriedItem;
import net.silentchaos512.funores.lib.ExtraRecipes;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.ModDamageSources;
import net.silentchaos512.funores.proxy.CommonProxy;
import net.silentchaos512.funores.registry.FunOresRegistry;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.lib.SilentLib;
import net.silentchaos512.lib.registry.MC10IdRemapper;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.LocalizationHelper;
import net.silentchaos512.lib.util.LogHelper;

//@formatter:off
@Mod(modid = FunOres.MOD_ID,
    name = FunOres.MOD_NAME,
    version = FunOres.VERSION_NUMBER,
    dependencies = FunOres.DEPENDENCIES,
    acceptedMinecraftVersions = FunOres.ACCEPTED_MC_VERSIONS,
    updateJSON = "https://raw.githubusercontent.com/SilentChaos512/FunOres/master/update.json")
//@formatter:on
public class FunOres {

  public static final String MOD_ID = "funores";
  public static final String MOD_NAME = "Fun Ores";
  public static final String VERSION_NUMBER = "@VERSION@";
  public static final String VERSION_SILENTLIB = "SL_VERSION";
  public static final String DEPENDENCIES = "required-after:silentlib@[" + VERSION_SILENTLIB + ",);";
  //"required-after:forge@[13.19.0.2156,);required-after:silentlib;";
  public static final String ACCEPTED_MC_VERSIONS = "[1.10.2,1.11.2]";
  public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";

  public static final boolean DEBUG_MODE = true;

  public static Random random = new Random();
  public static LogHelper logHelper = new LogHelper(MOD_NAME);
  public static LocalizationHelper localizationHelper;

  public static FunOresRegistry registry = new FunOresRegistry(MOD_ID, logHelper);

  @Instance(MOD_ID)
  public static FunOres instance;

  @SidedProxy(clientSide = "net.silentchaos512.funores.proxy.ClientProxy", serverSide = "net.silentchaos512.funores.proxy.CommonProxy")
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {

    localizationHelper = new LocalizationHelper(MOD_ID).setReplaceAmpersand(true);
    SilentLib.instance.registerLocalizationHelperForMod(MOD_ID, localizationHelper);

    Config.init(event.getSuggestedConfigurationFile());

    ModFluids.init();
    ModBlocks.init();
    ModItems.init();
    ModDamageSources.init();

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerFunOres());
    MinecraftForge.EVENT_BUS.register(this);

    proxy.preInit(registry);
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {

    ExtraRecipes.init();

    Config.save();

    GameRegistry.registerWorldGenerator(new FunOresGenerator(), 0);

    proxy.init(registry);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

    initAlloySmelterRecipes();
    initDryingRackRecipes();

    ConfigOptionOreGenBonus.initItemKeys();
    ConfigItemDrop.listErrorsInLog();

    proxy.postInit(registry);
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
        EnumAlloy.BRONZE.getMetalName(), EnumAlloy.BRONZE.getIngot(), 4,
        200, 1.2f,
        "ingotCopper*3", "ingotTin*1");

    addAlloySmelterRecipe(
        EnumAlloy.BRASS.getMetalName(), EnumAlloy.BRASS.getIngot(), 4,
        200, 1.0f,
        "ingotCopper*3", "ingotZinc*1");

    ItemStack coal = new ItemStack(Items.COAL, 2);
    addAlloySmelterRecipe(
        EnumAlloy.STEEL.getMetalName(), EnumAlloy.STEEL.getIngot(), 1,
        800, 2.5f,
        "ingotIron*1", coal);

    addAlloySmelterRecipe(
        EnumAlloy.INVAR.getMetalName(), EnumAlloy.INVAR.getIngot(), 3,
        400, 2.0f,
        "ingotIron*2", "ingotNickel*1");

    addAlloySmelterRecipe(
        EnumAlloy.ELECTRUM.getMetalName(), EnumAlloy.ELECTRUM.getIngot(), 2,
        400, 2.0f,
        "ingotGold*1", "ingotSilver*1");

    ItemStack enderEyes = new ItemStack(Items.ENDER_EYE, 4);
    addAlloySmelterRecipe(
        EnumAlloy.ENDERIUM.getMetalName(), EnumAlloy.ENDERIUM.getIngot(), 4,
        800, 4.0f,
        "ingotTin*2", "ingotSilver*1", "ingotPlatinum*1", enderEyes);

    ItemStack prismarineCrystals = new ItemStack(Items.PRISMARINE_CRYSTALS, 12);
    AlloySmelterRecipeObject gemsForPrismarine =
        new AlloySmelterRecipeObject("gemSapphire*2", "gemDiamond*1");
    addAlloySmelterRecipe(
        EnumAlloy.PRISMARIINIUM.getMetalName(), EnumAlloy.PRISMARIINIUM.getIngot(), 4,
        800, 5.0f,
        "ingotSilver*2", gemsForPrismarine, "ingotTitanium*1", prismarineCrystals);

    //@formatter:on
  }

  private void addAlloySmelterRecipe(String recipeName, ItemStack output, int outputCount,
      int cookTime, float experience, Object... inputs) {

    // Make sure the output is not disabled...
    if (FunOres.registry.isItemDisabled(output))
      return;

    boolean enabled = Config.getConfiguration()
        .get(Config.CATEGORY_RECIPE_ALLOY_SMELTER, recipeName, true).getBoolean();
    if (enabled) {
      FunOresAPI.addAlloySmelterRecipe(output, outputCount, cookTime, experience, inputs);
    }
  }

  private void initDryingRackRecipes() {

    int jerkyDryTime = 9000;
    float jerkyXp = 0.8f;
    addDryingRackRecipe("Dried Flesh", EnumDriedItem.DRIED_FLESH.getItem(),
        new ItemStack(Items.ROTTEN_FLESH), jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Beef Jerky", EnumDriedItem.BEEF_JERKY.getItem(), new ItemStack(Items.BEEF),
        jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Chicken Jerky", EnumDriedItem.CHICKEN_JERKY.getItem(),
        new ItemStack(Items.CHICKEN), jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Pork Jerky", EnumDriedItem.PORK_JERKY.getItem(),
        new ItemStack(Items.PORKCHOP), jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Mutton Jerky", EnumDriedItem.MUTTON_JERKY.getItem(),
        new ItemStack(Items.MUTTON), jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Rabbit Jerky", EnumDriedItem.RABBIT_JERKY.getItem(),
        new ItemStack(Items.RABBIT), jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Cod Jerky", EnumDriedItem.COD_JERKY.getItem(), new ItemStack(Items.FISH),
        jerkyDryTime, jerkyXp);
    addDryingRackRecipe("Salmon Jerky", EnumDriedItem.SALMON_JERKY.getItem(),
        new ItemStack(Items.FISH, 1, 1), jerkyDryTime, jerkyXp);

    addDryingRackRecipe("Sponge Drying", new ItemStack(Blocks.SPONGE),
        new ItemStack(Blocks.SPONGE, 1, 1), 2400, 0.4f);
  }

  private void addDryingRackRecipe(String recipeName, ItemStack output, Object input, int dryTime,
      float experience) {

    // Make sure the output is not disabled...
    if (FunOres.registry.isItemDisabled(output))
      return;

    if (input instanceof String) {
      DryingRackRecipeObject recipeObject = new DryingRackRecipeObject((String) input);
      DryingRackRecipe.addRecipe(output, recipeObject, dryTime, experience);
    } else if (input instanceof ItemStack) {
      DryingRackRecipeObject recipeObject = new DryingRackRecipeObject((ItemStack) input);
      DryingRackRecipe.addRecipe(output, recipeObject, dryTime, experience);
    } else {
      logHelper.warning("FunOres.addDryingRackRecipe: Don't know how to handle object of type "
          + input.getClass());
    }
  }

  public static CreativeTabs tabFunOres = new CreativeTabs("tabFunOres") {

    @Override
    public ItemStack getTabIconItem() {

      return new ItemStack(ModBlocks.meatOre);
    }
  };

  @EventHandler
  public void onMissingMapping(FMLMissingMappingsEvent event) {

    for (FMLMissingMappingsEvent.MissingMapping mismap : event.get()) {
      MC10IdRemapper.remap(mismap);
    }
  }
}
