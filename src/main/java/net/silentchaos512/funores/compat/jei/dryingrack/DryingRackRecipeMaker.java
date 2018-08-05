/*
 * Fun Ores -- DryingRackRecipeMaker
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

import com.google.common.collect.Lists;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public class DryingRackRecipeMaker {
    @Nonnull
    public static List<DryingRackRecipeWrapper> getRecipes() {
        List<DryingRackRecipeWrapper> recipes = Lists.newArrayList();
        for (DryingRackRecipe rackRecipe : DryingRackRecipe.ALL_RECIPES) {
            recipes.add(new DryingRackRecipeWrapper(rackRecipe));
        }
        return recipes;
    }
}
