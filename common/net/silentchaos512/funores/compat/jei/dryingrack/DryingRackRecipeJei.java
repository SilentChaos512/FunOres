package net.silentchaos512.funores.compat.jei.dryingrack;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;

public class DryingRackRecipeJei extends BlankRecipeWrapper {

  @Nonnull
  private final DryingRackRecipe recipe;

  public DryingRackRecipeJei(@Nonnull DryingRackRecipe recipe) {

    this.recipe = recipe;
  }

  @Override
  public List getInputs() {

    return recipe.getInput().getStacks();
  }

  @Override
  public List getOutputs() {

    return Collections.singletonList(recipe.getOutput());
  }

  @Override
  public void drawInfo(@Nonnull Minecraft mc, int recipeWidth, int recipeHeight, int mouseX,
      int mouseY) {

    FontRenderer fontRender = mc.fontRendererObj;
    String str = String.format("%.1f XP", recipe.getExperience());
    fontRender.drawStringWithShadow(str, 46, 0, 0xFFFFFF);
    str = recipe.getDryTime() + "t";
    fontRender.drawStringWithShadow(str, 46, 28, 0xFFFFFF);
  }

  @Override
  public void getIngredients(IIngredients arg0) {

    // TODO Auto-generated method stub
    
  }
}
