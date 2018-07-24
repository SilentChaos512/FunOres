/*
 * Fun Ores -- AlloySmelterRecipe
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

package net.silentchaos512.funores.api.recipe.alloysmelter;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.init.ModBlocks;

import javax.annotation.Nullable;
import java.util.*;

public class AlloySmelterRecipe {
    private static final List<AlloySmelterRecipe> allRecipes = new ArrayList<>();
    private static final Set<AlloySmelterRecipeObject> allIngredients = new HashSet<>();

    private static final int MAX_INPUTS = 4;

    private final AlloySmelterRecipeObject[] inputs;
    private final ItemStack output;
    private final int cookTime;
    private final float experience;

    private AlloySmelterRecipe(ItemStack output, int cookTime, float experience, Object... inputs) {
        this.inputs = AlloySmelterRecipeObject.getFromObjectArray(inputs);
        this.output = output;
        this.cookTime = cookTime;
        this.experience = experience;
    }

    /**
     * Creates a new AlloySmelterRecipe and adds it to allRecipes.
     *
     * @param output The resulting stack.
     * @param inputs All inputs. Can be Strings, ItemStacks, or AlloySmelterRecipeObjects. Stack
     *               size is considered. Length should be greater than 0, less than 5.
     */
    public static void addRecipe(ItemStack output, int cookTime, float experience, Object... inputs) {
        if (FunOres.registry.isItemDisabled(new ItemStack(ModBlocks.alloySmelter))) return;
        AlloySmelterRecipe newRecipe = new AlloySmelterRecipe(output, cookTime, experience, inputs);
        Collections.addAll(allIngredients, newRecipe.getInputs());
        allRecipes.add(newRecipe);
    }

    /**
     * Gets the first recipe that matches for the given inventory.
     *
     * @param inputList The alloy smelter inventory
     * @return The first matching recipe, or null if none match.
     */
    @Nullable
    public static AlloySmelterRecipe getMatchingRecipe(List<ItemStack> inputList) {
        for (AlloySmelterRecipe recipe : allRecipes) {
            if (recipe.matches(inputList)) {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    public static AlloySmelterRecipe getRecipeByOutput(ItemStack stack) {
        for (AlloySmelterRecipe recipe : allRecipes) {
            if (recipe.getOutput().isItemEqual(stack)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredient(ItemStack stack) {
        if (stack.isEmpty()) return false;
        ItemStack copy = stack.copy();
        copy.setCount(64);
        for (AlloySmelterRecipeObject recipeObject : allIngredients) {
            if (recipeObject.matches(copy)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given recipes matches the inventory.
     *
     * @param inputList The alloy smelter inventory
     * @return True if the recipe is a match (ie, will smelt), false otherwise.
     */
    public boolean matches(List<ItemStack> inputList) {
        // No inputs or outputs?
        if (inputs.length == 0 || output.isEmpty()) return false;

        // Check that inputs match recipe. Order does not matter.
        boolean[] matches = new boolean[MAX_INPUTS];
        int i;
        for (ItemStack inputStack : inputList) {
            for (i = 0; i < inputs.length; ++i) {
                if (inputs[i] == null) {
                    // Inputs shouldn't be null, but that is acceptable.
                    matches[i] = true;
                } else if (inputs[i].matches(inputStack)) {
                    // Correct item.
                    matches[i] = true;
                }
            }
        }
        for (i = inputs.length; i < MAX_INPUTS; ++i) {
            matches[i] = true;
        }

        // All ingredients match up (or are null)?
        for (i = 0; i < MAX_INPUTS; ++i) {
            if (!matches[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the number of ticks to smelt this recipe.
     *
     * @return Time to smelt in ticks
     */
    public int getCookTime() {
        return cookTime;
    }

    /**
     * Gets the experience awarded for cooking this recipe.
     *
     * @return Experience awarded for smelting
     */
    public float getExperience() {
        return experience;
    }

    /**
     * Gets a copy of the input stacks for the recipe.
     *
     * @return Copy of the inputs
     */
    public AlloySmelterRecipeObject[] getInputs() {

        return inputs.clone();
    }

    /**
     * Gets a copy of the output stack for the recipe.
     *
     * @return Copy of the output stack
     */
    public ItemStack getOutput() {

        return output.copy();
    }
}
