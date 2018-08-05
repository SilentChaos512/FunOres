/*
 * Fun Ores -- FunOresPlugin
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

package net.silentchaos512.funores.compat.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeCategory;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeMaker;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeWrapper;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeCategory;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeMaker;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeWrapper;
import net.silentchaos512.funores.gui.GuiAlloySmelter;
import net.silentchaos512.funores.init.ModBlocks;

import java.util.List;

@JEIPlugin
public class FunOresPlugin implements IModPlugin {
    public static List<ItemStack> disabledItems = Lists.newArrayList();

    @Override
    public void register(IModRegistry reg) {
        // Blacklist disabled items
        IIngredientBlacklist ingredientBlacklist = reg.getJeiHelpers().getIngredientBlacklist();
        disabledItems.forEach(ingredientBlacklist::addIngredientToBlacklist);

        reg.handleRecipes(AlloySmelterRecipe.class, AlloySmelterRecipeWrapper::new, AlloySmelterRecipeCategory.UID);
        reg.handleRecipes(DryingRackRecipe.class, DryingRackRecipeWrapper::new, DryingRackRecipeCategory.UID);

        reg.addRecipes(AlloySmelterRecipeMaker.getRecipes(), AlloySmelterRecipeCategory.UID);
        reg.addRecipes(DryingRackRecipeMaker.getRecipes(), DryingRackRecipeCategory.UID);

        reg.addRecipeClickArea(GuiAlloySmelter.class, 80, 34, 25, 16, AlloySmelterRecipeCategory.UID);

        ItemStack metalFurnace = new ItemStack(ModBlocks.metalFurnace);
        ItemStack alloySmelter = new ItemStack(ModBlocks.alloySmelter);
        ItemStack dryingRack = new ItemStack(ModBlocks.dryingRack);

        // Recipe catalysts
        reg.addRecipeCatalyst(metalFurnace, VanillaRecipeCategoryUid.FUEL, VanillaRecipeCategoryUid.SMELTING);
        reg.addRecipeCatalyst(alloySmelter, AlloySmelterRecipeCategory.UID);
        reg.addRecipeCatalyst(dryingRack, DryingRackRecipeCategory.UID);

        // Description pages
        String descPrefix = "jei.funores.desc.";
        reg.addIngredientInfo(metalFurnace, ItemStack.class, descPrefix + "metal_furnace");
        reg.addIngredientInfo(alloySmelter, ItemStack.class, descPrefix + "alloy_smelter");
        reg.addIngredientInfo(dryingRack, ItemStack.class, descPrefix + "drying_rack");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AlloySmelterRecipeCategory(guiHelper));
        registry.addRecipeCategories(new DryingRackRecipeCategory(guiHelper));
    }
}
