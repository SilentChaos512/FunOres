package net.silentchaos512.funores.lib;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ExtraRecipes {

  public static final String CATEGORY = "recipe_extra";

  public static Configuration config;

  public static void init() {

    config.setCategoryComment(CATEGORY, "Various recipes for vanilla things using other ingots.");
    String comment;
    ItemStack result;

    // Chests
    comment = "Eight logs to four chests.";
    result = new ItemStack(Blocks.chest, 4);
    add("ChestsFromLogs", comment, result, "lll", "l l", "lll", 'l', "logWood");
    // Pistons
    comment = "Extra piston recipe using an ingot other than iron.";
    result = new ItemStack(Blocks.piston);
    add("Piston.Aluminium", comment, result, "ppp", "cic", "crc", 'p', "plankWood", 'c',
        "cobblestone", 'i', "ingotAluminium", 'r', "dustRedstone");
    add("Piston.Aluminum", comment, result, "ppp", "cic", "crc", 'p', "plankWood", 'c',
        "cobblestone", 'i', "ingotAluminum", 'r', "dustRedstone");
    add("Piston.Bronze", comment, result, "ppp", "cic", "crc", 'p', "plankWood", 'c', "cobblestone",
        'i', "ingotBronze", 'r', "dustRedstone");
    // Hoppers
    comment = "Hopper recipe using an ingot other than iron.";
    result = new ItemStack(Blocks.hopper);
    add("Hopper.Aluminium", comment, result, "a a", "aca", " a ", 'a', "ingotAluminium", 'c',
        Blocks.chest);
    add("Hopper.Aluminum", comment, result, "a a", "aca", " a ", 'a', "ingotAluminum", 'c',
        Blocks.chest);
    // Shears
    comment = "Shears recipe using an ingot other than iron.";
    result = new ItemStack(Items.shears);
    add("Shears.Aluminium", comment, result, " a", "a ", 'a', "ingotAluminium");
    add("Shears.Aluminum", comment, result, " a", "a ", 'a', "ingotAluminum");
  }

  public static void add(String configName, String comment, ItemStack result, Object... params) {

    boolean enabled = config.getBoolean(configName, CATEGORY, true, comment);
    if (enabled) {
      GameRegistry.addRecipe(new ShapedOreRecipe(result, params));
    }
  }
}
