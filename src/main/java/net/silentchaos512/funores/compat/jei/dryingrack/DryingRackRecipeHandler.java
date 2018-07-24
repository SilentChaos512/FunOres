/*
 * Fun Ores -- DryingRackRecipeHandler
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

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class DryingRackRecipeHandler implements IRecipeHandler<DryingRackRecipeJei> {

    @Override
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
