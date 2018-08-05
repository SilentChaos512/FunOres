/*
 * Fun Ores -- DryingRackRecipeJei
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

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class DryingRackRecipeWrapper implements IRecipeWrapper {
    private final DryingRackRecipe recipe;

    public DryingRackRecipeWrapper(DryingRackRecipe recipe) {
        this.recipe = recipe;
    }

    List<ItemStack> getInputs() {
        return recipe.getInput().getStacks();
    }

    List<ItemStack> getOutputs() {
        return Collections.singletonList(recipe.getOutput());
    }

    @Override
    public void drawInfo(@Nonnull Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer fontRender = mc.fontRenderer;
        String str = String.format("%.1f XP", recipe.getExperience());
        fontRender.drawStringWithShadow(str, 46, 0, 0xFFFFFF);
        str = (recipe.getDryTime() / 20) + "s";
        fontRender.drawStringWithShadow(str, 46, 28, 0xFFFFFF);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, this.getInputs());
        ingredients.setOutput(ItemStack.class, this.recipe.getOutput());
    }
}
