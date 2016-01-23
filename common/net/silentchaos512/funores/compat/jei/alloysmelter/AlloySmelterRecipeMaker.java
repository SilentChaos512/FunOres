package net.silentchaos512.funores.compat.jei.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.recipe.alloysmelter.AlloySmelterRecipeObject;

public class AlloySmelterRecipeMaker {

  @Nonnull
  public static List<AlloySmelterRecipeJei> getRecipes() {

    ArrayList<AlloySmelterRecipeJei> recipes = new ArrayList<AlloySmelterRecipeJei>();

    for (AlloySmelterRecipe smelterRecipe : AlloySmelterRecipe.allRecipes) {
      List<AlloySmelterRecipeObject> inputs = Lists.newArrayList();

      for (AlloySmelterRecipeObject recipeObject : smelterRecipe.getInputs()) {
        inputs.add(recipeObject);
      }

      ItemStack output = smelterRecipe.getOutput();
      AlloySmelterRecipeJei jeiRecipe = new AlloySmelterRecipeJei(inputs, output);
      recipes.add(jeiRecipe);
    }

    return recipes;
  }
}
