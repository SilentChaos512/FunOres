/*
 * Fun Ores -- AlloySmelterRecipeHandler
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

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class AlloySmelterRecipeHandler implements IRecipeHandler<AlloySmelterRecipeJei> {

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