/*
 * Fun Ores -- AlloySmelterRecipeMaker
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

import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AlloySmelterRecipeMaker {
    @Nonnull
    public static List<AlloySmelterRecipeWrapper> getRecipes() {
        List<AlloySmelterRecipeWrapper> recipes = new ArrayList<>();
        for (AlloySmelterRecipe smelterRecipe : AlloySmelterRecipe.ALL_RECIPES) {
            recipes.add(new AlloySmelterRecipeWrapper(smelterRecipe));
        }
        return recipes;
    }
}
