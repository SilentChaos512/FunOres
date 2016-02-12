package net.silentchaos512.funores.compat.jei.dryingrack;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.compat.jei.FunOresPlugin;
import net.silentchaos512.funores.core.util.LocalizationHelper;

public class DryingRackRecipeCategory implements IRecipeCategory {

  public static final String CATEGORY = FunOres.MOD_ID + ":DryingRack";

  @Nonnull
  protected final IDrawable background;
  @Nonnull
  protected final IDrawableAnimated arrow;
  @Nonnull
  private final String localizedName = LocalizationHelper
      .getLocalizedString(("jei.funores.recipe.DryingRack"));

  public DryingRackRecipeCategory(IGuiHelper guiHelper) {

    ResourceLocation backgroundLocation = new ResourceLocation(
        FunOres.RESOURCE_PREFIX + "textures/gui/jei/DryingRack.png");

    background = FunOresPlugin.jeiHelper.getGuiHelper().createDrawable(backgroundLocation, 0, 0,
        120, 40);

    ResourceLocation furnaceLocation = new ResourceLocation("minecraft",
        "textures/gui/container/furnace.png");
    IDrawableStatic arrowDrawable = guiHelper.createDrawable(furnaceLocation, 176, 14, 24, 17);
    this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200,
        IDrawableAnimated.StartDirection.LEFT, false);
  }

  @Override
  public void drawAnimations(Minecraft mc) {

    arrow.draw(mc, 47, 10);
  }

  @Override
  public void drawExtras(Minecraft mc) {

  }

  @Override
  public IDrawable getBackground() {

    return background;
  }

  @Override
  public String getTitle() {

    return localizedName;
  }

  @Override
  public String getUid() {

    return CATEGORY;
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {

    recipeLayout.getItemStacks().init(0, true, 26, 11);
    recipeLayout.getItemStacks().init(1, false, 77, 11);

    if (recipeWrapper instanceof DryingRackRecipeJei) {
      DryingRackRecipeJei wrapper = (DryingRackRecipeJei) recipeWrapper;
      recipeLayout.getItemStacks().set(0, wrapper.getInputs());
      recipeLayout.getItemStacks().set(1, wrapper.getOutputs());
    }
  }
}
