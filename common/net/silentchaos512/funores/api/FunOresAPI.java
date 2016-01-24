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

  /**
   * Adds an alloy smelter recipe. Don't forget to set stack sizes on everything! And copy the item stacks before you
   * modify them...
   * 
   * Inputs can be any mix of AlloySmelterRecipeObjects, Strings, or ItemStacks. See AlloySmelterRecipeObject for
   * details on how to format the strings, or FunOres.java for examples.
   * 
   * @param output
   *          The recipe result. You must set the stack size!
   * @param cookTime
   *          The time in ticks to smelt the recipe.
   * @param experience
   *          The experience awarded for smelting.
   * @param inputs
   *          The ingredients (see above for details).
   */
  public static void addAlloySmelterRecipe(ItemStack output, int cookTime, float experience,
      Object... inputs) {

    AlloySmelterRecipe.addRecipe(output, cookTime, experience, inputs);
  }

  /**
   * Same as above, but copies the output and sets its size for you. Handy.
   * 
   * @param output
   *          The recipe result.
   * @param outputCount
   *          The stack size of the result.
   * @param cookTime
   *          The time in ticks to smelt the recipe.
   * @param experience
   *          The experience awarded for smelting.
   * @param inputs
   *          The ingredients (see previous method for details).
   */
  public static void addAlloySmelterRecipe(ItemStack output, int outputCount, int cookTime,
      float experience, Object... inputs) {

    ItemStack newOutput = output.copy();
    newOutput.stackSize = outputCount;
    addAlloySmelterRecipe(newOutput, cookTime, experience, inputs);
  }
}
