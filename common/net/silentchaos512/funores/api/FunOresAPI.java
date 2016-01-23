package net.silentchaos512.funores.api;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;

/**
 * Here I will try my best to create some methods to make interacting with Fun Ores a bit easier.
 * 
 * I've never made an API before, so... feel free to let me know if I do something stupid.
 * 
 * @author SilentChaos512
 *
 */
public class FunOresAPI {

  public static void addAlloySmelterRecipe(ItemStack output, int cookTime, float experience,
      String... inputs) {

    AlloySmelterRecipe.addRecipe(output, cookTime, experience, (Object[]) inputs);
  }

  public static void addAlloySmelterRecipe(ItemStack output, int cookTime, float experience,
      ItemStack... inputs) {

    AlloySmelterRecipe.addRecipe(output, cookTime, experience, (Object[]) inputs);
  }

  public static void addAlloySmelterRecipe(ItemStack output, int cookTime, float experience,
      AlloySmelterRecipeObject... inputs) {

    AlloySmelterRecipe.addRecipe(output, cookTime, experience, (Object[]) inputs);
  }

  public static void addAlloySmelterRecipe(ItemStack output, int cookTime, float experience,
      Object... inputs) {

    AlloySmelterRecipe.addRecipe(output, cookTime, experience, inputs);
  }
}
