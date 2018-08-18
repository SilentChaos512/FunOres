package net.silentchaos512.funores.api.recipe.alloysmelter;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.silentchaos512.lib.util.StackHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an object in an alloy smelter recipe. Unlike an ItemStack, this represents all possibilities (everything
 * with the same ore dictionary key, or a custom list).
 *
 * Typically, you will not construct this object directly. I recommend passing it an "item key" whenever possible. An
 * item key is a String consisting of the ore dictionary key, followed by an asterisk (*), then the stack size (item
 * count).
 *
 * Example item key: "ingotCopper*3" (minus quotes) for a stack of three copper ingots from any mod.
 *
 * @author SilentChaos512
 *
 */
public class AlloySmelterRecipeObject {

  /**
   * The regex used to split the item key into the ore name and stack size components.
   */
  public static final String ITEM_KEY_SPLITTER = "\\*";

  private List<String> itemKeys = Lists.newArrayList();
  private List<ItemStack> possibleStacks = Lists.newArrayList();

  public static AlloySmelterRecipeObject[] getFromObjectArray(Object... objects) {

    List<AlloySmelterRecipeObject> list = Lists.newArrayList();
    for (Object obj : objects) {
      list.add(from(obj));
    }
    return list.toArray(new AlloySmelterRecipeObject[] {});
  }

  public AlloySmelterRecipeObject(String oreName, int count) {

    this(oreName + "*" + count);
  }

  /**
   * Construct a recipe object with one or more item keys. Typically, you will only use one key. Item keys must be in
   * the format "oreName:count" (minus the quotes).
   *
   * @param itemKeys
   */
  public AlloySmelterRecipeObject(String... itemKeys) {

    for (String itemKey : itemKeys) {
      this.itemKeys.add(itemKey);
      possibleStacks.addAll(getStacksFromKey(itemKey));
    }
  }

  /**
   * Construct a recipe object with a specific item stack or stacks. Use this for items not in the ore dictionary, for
   * example. Don't forget to set the stack size! You may also want to copy the stack before you do that though...
   *
   * @param stacks
   */
  public AlloySmelterRecipeObject(ItemStack... stacks) {

    possibleStacks.addAll(Arrays.asList(stacks));
  }

  public static AlloySmelterRecipeObject from(Object obj) {

    if (obj instanceof String) {
      return new AlloySmelterRecipeObject((String) obj);
    } else if (obj instanceof ItemStack) {
      return new AlloySmelterRecipeObject((ItemStack) obj);
    } else if (obj instanceof AlloySmelterRecipeObject) {
      return (AlloySmelterRecipeObject) obj;
    } else {
      throw new IllegalArgumentException(
          "AlloySmelterRecipeObject: don't know how to use object of type " + obj.getClass());
    }
  }

  /**
   * Determines if the input stack matches for the recipe object.
   *
   * @param inputStack
   * @return True if the stack matches, false otherwise.
   */
  public boolean matches(ItemStack inputStack) {

    return getMatchingStack(inputStack) != null;
  }

  /**
   * Gets the first stack in possible stacks list that matches the input stack.
   *
   * @param inputStack
   * @return First matching stack, or null if there is no match.
   */
  public ItemStack getMatchingStack(ItemStack inputStack) {

    if (inputStack.isEmpty())
      return null;

    for (ItemStack recipeStack : possibleStacks) {
      if (!recipeStack.isEmpty() && inputStack.isItemEqual(recipeStack) && inputStack.getCount() >= recipeStack.getCount()) {
        return recipeStack.copy();
      }
    }
    return null;
  }

  /**
   * Gets a copy of the item keys list.
   *
   * @return
   */
  public List<String> getItemKeys() {

    return Lists.newArrayList(itemKeys);
  }

  /**
   * Gets a copy of the possible item stacks list.
   *
   * @return
   */
  public List<ItemStack> getPossibleItemStacks() {

    return Lists.newArrayList(possibleStacks);
  }

  /**
   * Gets all possible stacks from the ore dictionary that match the item key. It copies the original stack and sets the
   * stack size as well.
   *
   * @param key
   * @return
   */
  private List<ItemStack> getStacksFromKey(String key) {

    String[] parts = key.split(ITEM_KEY_SPLITTER);

    if (parts.length != 2) {
      throw new IllegalArgumentException("Item key must be in the format \"oreName*count\".");
    }

    String oreName = parts[0];
    int stackSize = Integer.parseInt(parts[1]);
    if (stackSize < 0) {
      throw new IllegalArgumentException("Item count must greater than zero!");
    }
    List<ItemStack> result = new ArrayList<ItemStack>();

    ItemStack copy;
    for (ItemStack stack : StackHelper.getOres(oreName)) {
      copy = stack.copy();
      // Should stack size be checked? Probably not...
//      if (stackSize > copy.getMaxStackSize()) {
//        throw new IllegalArgumentException("Item count for stack " + copy + " is too big!");
//      }
      copy.setCount(stackSize);
      result.add(copy);
    }

    return result;
  }
}
