package net.silentchaos512.funores.api.recipe.alloysmelter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.lib.util.StackHelper;

public class AlloySmelterRecipe {

  public static final ArrayList<AlloySmelterRecipe> allRecipes = new ArrayList<AlloySmelterRecipe>();
  public static final Set<AlloySmelterRecipeObject> allIngredients = Sets.newHashSet();

  public static final int MAX_INPUTS = 4;

  private AlloySmelterRecipeObject[] inputs;
  private ItemStack output;
  private int cookTime;
  private float experience;

  public AlloySmelterRecipe(ItemStack output, int cookTime, float experience, Object... inputs) {

    this.inputs = AlloySmelterRecipeObject.getFromObjectArray(inputs);
    this.output = output;
    this.cookTime = cookTime;
    this.experience = experience;
  }

  /**
   * Creates a new AlloySmelterRecipe and adds it to allRecipes.
   * 
   * @param output
   *          The resulting stack.
   * @param inputs
   *          All inputs. Can be Strings, ItemStacks, or AlloySmelterRecipeObjects. Stack size is considered. Length
   *          should be greater than 0, less than 5.
   */
  public static void addRecipe(ItemStack output, int cookTime, float experience, Object... inputs) {

    if (FunOres.registry.isItemDisabled(new ItemStack(ModBlocks.alloySmelter))) return;

    AlloySmelterRecipe newRecipe = new AlloySmelterRecipe(output, cookTime, experience, inputs);
    for (AlloySmelterRecipeObject recipeObject : newRecipe.getInputs()) {
      allIngredients.add(recipeObject);
    }
    allRecipes.add(newRecipe);
  }

  /**
   * Gets the first recipe that matches for the given inventory.
   * 
   * @param inv
   *          The alloy smelter inventory.
   * @return The first matching recipe, or null if none match.
   */
  public static AlloySmelterRecipe getMatchingRecipe(List<ItemStack> inputList) {

    for (AlloySmelterRecipe recipe : allRecipes) {
      if (recipe.matches(inputList)) {
        return recipe;
      }
    }

    return null;
  }

  public static AlloySmelterRecipe getRecipeByOutput(ItemStack stack) {

    for (AlloySmelterRecipe recipe : allRecipes) {
      if (recipe.getOutput().isItemEqual(stack)) {
        return recipe;
      }
    }

    return null;
  }

  public static boolean isValidIngredient(ItemStack stack) {

    if (StackHelper.isEmpty(stack))
      return false;

    ItemStack copy = stack.copy();
    copy.setCount(64);
    for (AlloySmelterRecipeObject recipeObject : allIngredients) {
      if (recipeObject.matches(copy)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the given recipes matches the inventory.
   * 
   * @param inv
   *          The alloy smelter inventory.
   * @return True if the recipe is a match (ie, will smelt), false otherwise.
   */
  public boolean matches(List<ItemStack> inputList) {

    // No inputs?
    if (inputs.length == 0) {
      return false;
    }

    // No outputs?
    if (StackHelper.isEmpty(output)) {
      return false;
    }

    // Check that inputs match recipe. Order does not matter.
    boolean[] matches = new boolean[MAX_INPUTS];
    int i;
    for (ItemStack inputStack : inputList) {
      for (i = 0; i < inputs.length; ++i) {
        if (inputs[i] == null) {
          // Inputs shouldn't be null, but that is acceptable.
          matches[i] = true;
        } else if (inputs[i].matches(inputStack)) {
          // Correct item.
          matches[i] = true;
        }
      }
    }
    for (i = inputs.length; i < MAX_INPUTS; ++i) {
      matches[i] = true;
    }

    // All ingredients match up (or are null)?
    for (i = 0; i < MAX_INPUTS; ++i) {
      if (!matches[i]) {
        return false;
      }
    }

    return true;
  }

  /**
   * Gets the number of ticks to smelt this recipe.
   * 
   * @return
   */
  public int getCookTime() {

    return cookTime;
  }

  /**
   * Gets the experience awarded for cooking this recipe.
   * 
   * @return
   */
  public float getExperience() {

    return experience;
  }

  /**
   * Gets a copy of the input stacks for the recipe.
   * 
   * @return
   */
  public AlloySmelterRecipeObject[] getInputs() {

    return inputs.clone();
  }

  /**
   * Gets a copy of the output stack for the recipe.
   * 
   * @return
   */
  public ItemStack getOutput() {

    return StackHelper.safeCopy(output);
  }
}
