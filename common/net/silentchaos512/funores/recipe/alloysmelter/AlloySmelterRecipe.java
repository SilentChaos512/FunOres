package net.silentchaos512.funores.recipe.alloysmelter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.tile.TileAlloySmelter;

public class AlloySmelterRecipe {

  public static final ArrayList<AlloySmelterRecipe> allRecipes = new ArrayList<AlloySmelterRecipe>();
  public static final Set<AlloySmelterRecipeObject> allIngredients = Sets.newHashSet();

  public static final int MAX_INPUTS = TileAlloySmelter.SLOTS_INPUT.length;
  public static final String CONFIG_CATEGORY = "recipe_alloy_smelter";
  public static final String CONFIG_CATEGORY_COMMENT = "You can disable alloy smelter recipes here. Set to false to disable the recipe.";

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

  public static void initRecipes() {

    Config.getConfiguration().setCategoryComment(CONFIG_CATEGORY, CONFIG_CATEGORY_COMMENT);

    addRecipe("Bronze", getIngot(EnumAlloy.BRONZE, 4), 200, 0.5f, "ingotCopper*3", "ingotTin*1");
    addRecipe("Brass", getIngot(EnumAlloy.BRASS, 4), 200, 0.5f, "ingotCopper*3", "ingotZinc*1");
    ItemStack coal = new ItemStack(Items.coal);
    coal.stackSize = 2;
    addRecipe("Steel", getIngot(EnumAlloy.STEEL, 1), 800, 0.7f, "ingotIron*1", coal);
    addRecipe("Invar", getIngot(EnumAlloy.INVAR, 3), 400, 0.7f, "ingotIron*2", "ingotNickel*1");
    addRecipe("Electrum", getIngot(EnumAlloy.ELECTRUM, 2), 400, 1.0f, "ingotGold*1",
        "ingotSilver*1");
    ItemStack enderEyes = new ItemStack(Items.ender_eye);
    enderEyes.stackSize = 4;
    addRecipe("Enderium", getIngot(EnumAlloy.ENDERIUM, 4), 800, 2.0f, "ingotTin*2", "ingotSilver*1",
        "ingotPlatinum*1", enderEyes);

    // Test
    // addRecipe("Test", getIngot(EnumAlloy.STEEL, 5), 300, AlloyIngot.ORE_DICT_COPPER_ALLOY + "*5",
    // "ingotIron*2");
  }

  public static ItemStack getIngot(EnumMetal metal, int count) {

    ItemStack result = metal.getIngot();
    result.stackSize = count;
    return result;
  }

  public static ItemStack getIngot(EnumAlloy metal, int count) {

    ItemStack result = metal.getIngot();
    result.stackSize = count;
    return result;
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

    AlloySmelterRecipe newRecipe = new AlloySmelterRecipe(output, cookTime, experience, inputs);
    for (AlloySmelterRecipeObject recipeObject : newRecipe.getInputs()) {
      allIngredients.add(recipeObject);
    }
    allRecipes.add(newRecipe);
  }

  /**
   * Creates a new AlloySmelterRecipe and a config to disable it in FunOres.cfg. Adds to allRecipes if enabled.
   * 
   * @param recipeName
   * @param output
   * @param cookTime
   * @param experience
   * @param inputs
   */
  private static void addRecipe(String recipeName, ItemStack output, int cookTime, float experience,
      Object... inputs) {

    boolean enabled = Config.getConfiguration().get(CONFIG_CATEGORY, recipeName, true).getBoolean();
    if (enabled) {
      addRecipe(output, cookTime, experience, inputs);
    }
  }

  /**
   * Gets the first recipe that matches for the given inventory.
   * 
   * @param inv
   *          The alloy smelter inventory.
   * @return The first matching recipe, or null if none match.
   */
  public static AlloySmelterRecipe getMatchingRecipe(IInventory inv) {

    for (AlloySmelterRecipe recipe : allRecipes) {
      if (recipe.matches(inv)) {
        return recipe;
      }
    }

    return null;
  }

  public static boolean isValidIngredient(ItemStack stack) {

    for (AlloySmelterRecipeObject recipeObject : allIngredients) {
      if (recipeObject.matches(stack)) {
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
  public boolean matches(IInventory inv) {

    // Wrong inventory?
    if (!(inv instanceof TileAlloySmelter)) {
      return false;
    }

    // No inputs?
    if (inputs.length == 0) {
      return false;
    }

    // No outputs?
    if (output == null) {
      return false;
    }

    // Get inputs
    TileAlloySmelter tile = (TileAlloySmelter) inv;
    List<ItemStack> inputList = tile.getInputStacks();

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

    return output.copy();
  }
}
