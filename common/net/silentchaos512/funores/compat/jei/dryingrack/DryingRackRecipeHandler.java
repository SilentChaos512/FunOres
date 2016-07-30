package net.silentchaos512.funores.compat.jei.dryingrack;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class DryingRackRecipeHandler implements IRecipeHandler<DryingRackRecipeJei> {

  @Override
  public String getRecipeCategoryUid() {

    return DryingRackRecipeCategory.CATEGORY;
  }

  //@Override // TODO: Uncomment in 1.10.x
  public String getRecipeCategoryUid(DryingRackRecipeJei arg0) {

    return DryingRackRecipeCategory.CATEGORY;
  }

  @Override
  public Class<DryingRackRecipeJei> getRecipeClass() {

    return DryingRackRecipeJei.class;
  }

  @Override
  public IRecipeWrapper getRecipeWrapper(DryingRackRecipeJei recipe) {

    return recipe;
  }

  @Override
  public boolean isRecipeValid(DryingRackRecipeJei recipe) {

    return recipe.getInputs().size() > 0 && recipe.getOutputs().size() > 0;
  }
}
