/*
 * Fun Ores -- DryingRackRecipeCategory
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

package net.silentchaos512.funores.compat.jei.dryingrack;

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

public class DryingRackRecipeCategory implements IRecipeCategory {
    public static final String UID = FunOres.RESOURCE_PREFIX + "drying_rack";

    private final IDrawable background;
    private final IDrawableAnimated arrow;
    private final String localizedName = FunOres.localizationHelper.getLocalizedString("jei.funores.recipe.drying_rack");

    public DryingRackRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation backgroundLocation = new ResourceLocation(FunOres.RESOURCE_PREFIX + "textures/gui/jei/DryingRack.png");
        background = guiHelper.createDrawable(backgroundLocation, 0, 0, 120, 40);

        ResourceLocation furnaceLocation = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(furnaceLocation, 176, 14, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft mc) {
        arrow.draw(mc, 47, 10);
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
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 26, 11);
        recipeLayout.getItemStacks().init(1, false, 77, 11);

        if (recipeWrapper instanceof DryingRackRecipeWrapper) {
            DryingRackRecipeWrapper wrapper = (DryingRackRecipeWrapper) recipeWrapper;
            recipeLayout.getItemStacks().set(0, wrapper.getInputs());
            recipeLayout.getItemStacks().set(1, wrapper.getOutputs());
        }
    }

    @Override
    public String getModName() {
        return FunOres.MOD_NAME;
    }
}
