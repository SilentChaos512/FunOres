package net.silentchaos512.funores.compat.jei.dryingrack;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;

public class DryingRackRecipeMaker {

  @Nonnull
  public static List<DryingRackRecipeJei> getRecipes() {

    List<DryingRackRecipeJei> recipes = Lists.newArrayList();

    for (DryingRackRecipe rackRecipe : DryingRackRecipe.allRecipes) {
      recipes.add(new DryingRackRecipeJei(rackRecipe));
    }

    return recipes;
  }
}
