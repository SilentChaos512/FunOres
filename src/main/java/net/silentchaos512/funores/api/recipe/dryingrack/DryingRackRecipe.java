/*
 * Fun Ores -- DryingRackRecipe
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

package net.silentchaos512.funores.api.recipe.dryingrack;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.lib.util.StackHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DryingRackRecipe {
    private static final List<DryingRackRecipe> allRecipes = new ArrayList<>();
    private static final Set<DryingRackRecipeObject> allIngredients = new HashSet<>();

    private final DryingRackRecipeObject input;
    private final ItemStack output;
    private final int dryTime;
    private final float experience;

    public DryingRackRecipe(ItemStack output, DryingRackRecipeObject input, int dryTime, float experience) {
        this.input = input;
        this.output = output;
        this.dryTime = dryTime;
        this.experience = experience;
    }

    public static void addRecipe(ItemStack output, DryingRackRecipeObject input, int dryTime, float experience) {
        if (FunOres.registry.isItemDisabled(new ItemStack(ModBlocks.dryingRack))) return;
        DryingRackRecipe newRecipe = new DryingRackRecipe(output, input, dryTime, experience);
        allIngredients.add(newRecipe.input);
        allRecipes.add(newRecipe);
    }

    @Nullable
    public static DryingRackRecipe getMatchingRecipe(ItemStack inputStack) {
        if (inputStack.isEmpty()) return null;
        for (DryingRackRecipe recipe : allRecipes) {
            if (recipe.matches(inputStack)) {
                return recipe;
            }
        }
        return null;
    }

    public boolean matches(ItemStack inputStack) {
        return StackHelper.isValid(inputStack) && StackHelper.isValid(output) && input.matches(inputStack);
    }

    public int getDryTime() {
        return dryTime;
    }

    public float getExperience() {
        return experience;
    }

    public DryingRackRecipeObject getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output.copy();
    }
}
