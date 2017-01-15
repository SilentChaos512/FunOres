package net.silentchaos512.funores.compat.jei.alloysmelter;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class AlloySmelterRecipeHandler implements IRecipeHandler<AlloySmelterRecipeJei> {

//  @Override
//  public String getRecipeCategoryUid() {
//
//    return AlloySmelterRecipeCategory.CATEGORY;
//  }

  @Override
  public String getRecipeCategoryUid(AlloySmelterRecipeJei arg0) {

    return AlloySmelterRecipeCategory.CATEGORY;
  }

  @Override
  public Class<AlloySmelterRecipeJei> getRecipeClass() {

    return AlloySmelterRecipeJei.class;
  }

  @Override
  public IRecipeWrapper getRecipeWrapper(@Nonnull AlloySmelterRecipeJei recipe) {

    return recipe;
  }

  @Override
  public boolean isRecipeValid(AlloySmelterRecipeJei recipe) {

    return recipe.getInputObjects().size() > 0 && recipe.getOutputs().size() > 0;
  }

}
