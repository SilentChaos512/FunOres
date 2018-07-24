package net.silentchaos512.funores.compat.jei.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;

public class AlloySmelterRecipeMaker {

  @Nonnull
  public static List<AlloySmelterRecipeJei> getRecipes() {

    ArrayList<AlloySmelterRecipeJei> recipes = new ArrayList<AlloySmelterRecipeJei>();

    for (AlloySmelterRecipe smelterRecipe : AlloySmelterRecipe.allRecipes) {
      recipes.add(new AlloySmelterRecipeJei(smelterRecipe));
    }

    return recipes;
  }
}
