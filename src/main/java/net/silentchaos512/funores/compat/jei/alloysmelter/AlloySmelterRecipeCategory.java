/*
 * Fun Ores -- AlloySmelterRecipeCategory
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.compat.jei.alloysmelter;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;
import net.silentchaos512.funores.compat.jei.FunOresPlugin;
import net.silentchaos512.funores.tile.TileAlloySmelter;

import javax.annotation.Nonnull;

public class AlloySmelterRecipeCategory implements IRecipeCategory {

    public static final String CATEGORY = FunOres.MOD_ID + ":AlloySmelter";

    @Nonnull
    protected final IDrawable background;
    @Nonnull
    protected final IDrawableAnimated flame;
    @Nonnull
    protected final IDrawableAnimated arrow;
    @Nonnull
    private final String localizedName = FunOres.instance.localizationHelper
            .getLocalizedString(("jei.funores.recipe.AlloySmelter"));

    public AlloySmelterRecipeCategory(IGuiHelper guiHelper) {

        ResourceLocation backgroundLocation = new ResourceLocation(
                FunOres.RESOURCE_PREFIX + "textures/gui/jei/AlloySmelter.png");

        background = FunOresPlugin.jeiHelper.getGuiHelper().createDrawable(backgroundLocation, 0, 0,
                120, 40);

        ResourceLocation furnaceLocation = new ResourceLocation("minecraft",
                "textures/gui/container/furnace.png");
        IDrawableStatic flameDrawable = guiHelper.createDrawable(furnaceLocation, 176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(flameDrawable, 300,
                IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(furnaceLocation, 176, 14, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200,
                IDrawableAnimated.StartDirection.LEFT, false);
    }

    //@Override
    public void drawAnimations(Minecraft mc) {

        flame.draw(mc, 2, 4);
        arrow.draw(mc, 62, 10);
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

//  @Override
//  public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
//
//    // TODO
//    setRecipe(recipeLayout, recipeWrapper, null);
//  }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

        recipeLayout.getItemStacks().init(0, true, 25, 0);
        recipeLayout.getItemStacks().init(1, true, 43, 0);
        recipeLayout.getItemStacks().init(2, true, 25, 18);
        recipeLayout.getItemStacks().init(3, true, 43, 18);
        recipeLayout.getItemStacks().init(TileAlloySmelter.SLOT_FUEL, true, 0, 15);
        recipeLayout.getItemStacks().init(TileAlloySmelter.SLOT_OUTPUT, false, 98, 10);

        if (recipeWrapper instanceof AlloySmelterRecipeJei) {
            AlloySmelterRecipeJei wrapper = (AlloySmelterRecipeJei) recipeWrapper;
            // Set inputs
            for (int i = 0; i < wrapper.getInputObjects().size(); ++i) {
                Object obj = wrapper.getInputObjects().get(i);
                AlloySmelterRecipeObject recipeObject = (AlloySmelterRecipeObject) obj;
                recipeLayout.getItemStacks().set(i, recipeObject.getPossibleItemStacks());
            }
            // Set output
            recipeLayout.getItemStacks().set(TileAlloySmelter.SLOT_OUTPUT, wrapper.getOutputs());
        }
    }

    @Override
    public IDrawable getIcon() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getModName() {

        return FunOres.MOD_NAME;
    }
}
