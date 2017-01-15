package net.silentchaos512.funores.compat.jei.alloysmelter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;

public class AlloySmelterRecipeJei extends BlankRecipeWrapper {

  @Nonnull
  private final AlloySmelterRecipe recipe;

  public AlloySmelterRecipeJei(@Nonnull AlloySmelterRecipe recipe) {

    this.recipe = recipe;
  }

  public List<AlloySmelterRecipeObject> getInputObjects() {

    return Arrays.asList(recipe.getInputs());
  }

  //@Override
  public List getInputs() {

    List<ItemStack> list = Lists.newArrayList();
    for (AlloySmelterRecipeObject recipeObject : recipe.getInputs()) {
      list.addAll(recipeObject.getPossibleItemStacks());
    }
    return list;
  }

  //@Override
  public List getOutputs() {

    return Collections.singletonList(recipe.getOutput());
  }

  @Override
  public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
      int mouseY) {

    FontRenderer fontRender = minecraft.fontRendererObj;
    String str = String.format("%.1f XP", recipe.getExperience());
    fontRender.drawStringWithShadow(str, 63, 0, 0xFFFFFF);
    str = recipe.getCookTime() + "t";
    fontRender.drawStringWithShadow(str, 66, 28, 0xFFFFFF);
  }

  @Override
  public void getIngredients(IIngredients arg0) {

    // TODO Auto-generated method stub
    
  }
}
