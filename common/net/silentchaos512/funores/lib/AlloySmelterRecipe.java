package net.silentchaos512.funores.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.item.AlloyIngot;
import net.silentchaos512.funores.tile.TileAlloySmelter;

public class AlloySmelterRecipe {

  public static final ArrayList<AlloySmelterRecipe> allRecipes = new ArrayList<AlloySmelterRecipe>();
  public static final Set allIngredients = Sets.newHashSet();

  public static final int MAX_INPUTS = TileAlloySmelter.SLOTS_INPUT.length;
  public static final String CONFIG_CATEGORY = "recipe_alloy_smelter";
  public static final String CONFIG_CATEGORY_COMMENT = "You can disable alloy smelter recipes here. Set to false to disable the recipe.";

  private Object[] inputs;
  private ItemStack output;
  private int cookTime;

  public AlloySmelterRecipe(ItemStack output, int cookTime, Object... inputs) {

    this.inputs = inputs;
    this.output = output;
    this.cookTime = cookTime;
  }

  public static void initRecipes() {

    Config.getConfiguration().setCategoryComment(CONFIG_CATEGORY, CONFIG_CATEGORY_COMMENT);

    addRecipe("Bronze", getIngot(EnumAlloy.BRONZE, 4), 200, "ingotCopper*3", "ingotTin*1");
    addRecipe("Brass", getIngot(EnumAlloy.BRASS, 4), 200, "ingotCopper*3", "ingotZinc*1");
    ItemStack coal = new ItemStack(Items.coal);
    coal.stackSize = 2;
    addRecipe("Steel", getIngot(EnumAlloy.STEEL, 1), 800, "ingotIron*1", coal);
    addRecipe("Invar", getIngot(EnumAlloy.INVAR, 3), 400, "ingotIron*2", "ingotNickel*1");
    addRecipe("Electrum", getIngot(EnumAlloy.ELECTRUM, 2), 400, "ingotGold*1", "ingotSilver*1");
    ItemStack enderEyes = new ItemStack(Items.ender_eye);
    enderEyes.stackSize = 4;
    addRecipe("Enderium", getIngot(EnumAlloy.ENDERIUM, 4), 800, "ingotTin*2", "ingotSilver*1",
        "ingotPlatinum*1", enderEyes);

    // Test
    addRecipe("Test", getIngot(EnumAlloy.STEEL, 5), 300, AlloyIngot.ORE_DICT_COPPER_ALLOY + "*5",
        "ingotIron*2");
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

  public static void addRecipe(String recipeName, ItemStack output, int cookTime,
      Object... inputs) {

    boolean enabled = Config.getConfiguration().get(CONFIG_CATEGORY, recipeName, true).getBoolean();
    if (enabled) {
      addRecipe(output, cookTime, inputs);
    }
  }

  /**
   * Creates a new AlloySmelterRecipe and adds it to allRecipes.
   * 
   * @param output
   *          The resulting stack.
   * @param inputs
   *          All input stacks. Stack size is considered. Length should be greater than 0, less than 5.
   */
  public static void addRecipe(ItemStack output, int cookTime, Object... inputs) {

    AlloySmelterRecipe newRecipe = new AlloySmelterRecipe(output, cookTime, inputs);
    for (Object obj : inputs) {
      allIngredients.add(obj);
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
  public static AlloySmelterRecipe getMatchingRecipe(IInventory inv) {

    for (AlloySmelterRecipe recipe : allRecipes) {
      if (recipe.matches(inv)) {
        return recipe;
      }
    }

    return null;
  }

  public static boolean isValidIngredient(ItemStack stack) {

    AlloySmelterRecipe dummy = allRecipes.get(0);
    for (Object recipeObject : allIngredients) {
      if (dummy.itemStackMatchesForInput(stack, recipeObject)) {
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
    if (inv.getSizeInventory() < MAX_INPUTS) {
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

    int i;

    // Get inputs
    ItemStack stack;
    ArrayList<ItemStack> inputList = new ArrayList<ItemStack>();
    for (i = 0; i < MAX_INPUTS; ++i) {
      stack = inv.getStackInSlot(i);
      if (stack != null) {
        inputList.add(stack);
      }
    }

    // Check that inputs match recipe. Order does not matter.
    boolean[] matches = new boolean[MAX_INPUTS];
    for (ItemStack inputStack : inputList) {
      for (i = 0; i < inputs.length; ++i) {
        if (inputs[i] == null) {
          // Inputs shouldn't be null, but that is acceptable.
          matches[i] = true;
        } else if (itemStackMatchesForInput(inputStack, inputs[i])) {
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
   * Determines if the input item matches the recipe, and that the stack size is greater than or equal to the recipe's
   * requirement.
   * 
   * @param inputStack
   *          The stack from the inventory.
   * @param requiredStack
   *          The stack from the recipe.
   * @return True if stack meets requirements, false otherwise.
   */
  public boolean itemStackMatchesForInput(ItemStack inputStack, Object recipeItem) {

    if (inputStack == null || recipeItem == null) {
      return false;
    }

    boolean itemMatch = false;
    int requiredSize = getRecipeObjectStackSize(recipeItem);
    if (recipeItem instanceof String) {
      // String in recipe.
      String str = (String) recipeItem;
      String[] parts = str.split("\\*");
      if (parts.length < 2) {
        LogHelper.warning("AlloySmelterRecipe.itemStackMatchesForInput - bad string: " + str);
        return false;
      }

      // Ore name
      String oreName = parts[0];
      for (int inputOreId : OreDictionary.getOreIDs(inputStack)) {
        if (OreDictionary.getOreName(inputOreId).equals(oreName)) {
          itemMatch = true;
        }
      }
    } else if (recipeItem instanceof ItemStack) {
      // ItemStack in recipe.
      ItemStack requiredStack = (ItemStack) recipeItem;
      itemMatch = requiredStack.isItemEqual(inputStack);
    } else {
      LogHelper.warning("Object of type " + recipeItem.getClass().toString()
          + " is not valid for AlloySmelterRecipe!");
      return false;
    }

    return itemMatch && inputStack.stackSize >= requiredSize;
  }

  public static int getRecipeObjectStackSize(Object recipeItem) {

    if (recipeItem instanceof String) {
      String str = (String) recipeItem;
      String[] parts = str.split("\\*");
      if (parts.length < 2) {
        return 0;
      }

      // Required count
      try {
        return Integer.parseInt(parts[1]);
      } catch (NumberFormatException ex) {
        LogHelper
            .warning("AlloySmelterRecipe.itemStackMatchesForInput - bad number in string: " + str);
        return 0;
      }
    } else if (recipeItem instanceof ItemStack) {
      // ItemStack in recipe.
      ItemStack requiredStack = (ItemStack) recipeItem;
      return requiredStack.stackSize;
    } else {
      return 0;
    }
  }

  public int getCookTime() {

    return cookTime;
  }

  /**
   * Gets a copy of the input stacks for the recipe.
   * 
   * @return
   */
  public Object[] getInputs() {

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
