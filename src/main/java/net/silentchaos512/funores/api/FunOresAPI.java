package net.silentchaos512.funores.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipeObject;
import net.silentchaos512.lib.util.StackHelper;

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

    ItemStack newOutput = StackHelper.safeCopy(output);
    if (StackHelper.isValid(newOutput)) {
      newOutput = StackHelper.setCount(newOutput, outputCount);
      addAlloySmelterRecipe(newOutput, cookTime, experience, inputs);
    }
  }

  /**
   * Adds a drying rack recipe.
   * 
   * @param output
   *          The result of drying the input.
   * @param dryTime
   *          The base drying time.
   * @param experience
   *          The experience resulting from the recipe.
   * @param input
   *          Can be an ItemStack or a String (ore dictionary name).
   */
  public static void addDryingRackRecipe(ItemStack output, int dryTime, float experience,
      Object input) {

    DryingRackRecipeObject recipeObject = null;
    if (input instanceof ItemStack) {
      recipeObject = new DryingRackRecipeObject((ItemStack) input);
    } else if (input instanceof String) {
      recipeObject = new DryingRackRecipeObject((String) input);
    }

    if (recipeObject != null) {
      DryingRackRecipe.addRecipe(output, recipeObject, dryTime, experience);
    }
  }

  // TODO: Maybe the hammer should be in the ore dictionary? Or maybe an interface for hammers?
  private static final ItemStack HAMMER = new ItemStack(Item.getByNameOrId("FunOres:Hammer"));

  /**
   * Adds a plate recipe. Just so you don't need to grab the hammer item yourself.
   * 
   * @param plate
   *          The resulting item.
   * @param ingot
   *          The ore dictionary name of the ingot.
   */
  public static void addPlateRecipe(ItemStack plate, String ingot) {

    FunOres.registry.recipes.addShapedOre("plate_" + ingot.toLowerCase(), plate, "h", "i", "i", 'h', HAMMER, 'i', ingot);
  }
}
