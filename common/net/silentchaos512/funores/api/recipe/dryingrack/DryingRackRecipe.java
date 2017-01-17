package net.silentchaos512.funores.api.recipe.dryingrack;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.lib.compat.StackHelper;

public class DryingRackRecipe {

  public static final List<DryingRackRecipe> allRecipes = Lists.newArrayList();
  public static final Set<DryingRackRecipeObject> allIngredients = Sets.newHashSet();

  private DryingRackRecipeObject input;
  private ItemStack output;
  private int dryTime;
  private float experience;

  public DryingRackRecipe(ItemStack output, DryingRackRecipeObject input, int dryTime,
      float experience) {

    this.input = input;
    this.output = output;
    this.dryTime = dryTime;
    this.experience = experience;
  }

  public static void addRecipe(ItemStack output, DryingRackRecipeObject input, int dryTime,
      float experience) {

    if (FunOres.registry.isItemDisabled(new ItemStack(ModBlocks.dryingRack))) return;

    DryingRackRecipe newRecipe = new DryingRackRecipe(output, input, dryTime, experience);
    allIngredients.add(newRecipe.input);
    allRecipes.add(newRecipe);
  }

  public static DryingRackRecipe getMatchingRecipe(ItemStack inputStack) {

    if (inputStack != null && !StackHelper.isEmpty(inputStack)) {
      for (DryingRackRecipe recipe : allRecipes) {
        if (recipe.matches(inputStack)) {
          return recipe;
        }
      }
    }
    return null;
  }

  public boolean matches(ItemStack inputStack) {

    return inputStack != null && input != null && output != null && input.matches(inputStack);
  }

  public int getDryTime() {

    return dryTime;
  }

  public float getExperience() {

    return experience;
  }

  public DryingRackRecipeObject getInput() {

    return input;
  }

  public ItemStack getOutput() {

    return StackHelper.copy(output);
  }
}
