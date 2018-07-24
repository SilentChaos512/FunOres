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
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeCategory;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeHandler;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeMaker;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeCategory;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeHandler;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeMaker;
import net.silentchaos512.funores.gui.GuiAlloySmelter;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.Names;

import java.util.List;

@JEIPlugin
public class FunOresPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelper;
    public static List<ItemStack> disabledItems = Lists.newArrayList();

    @Override
    public void register(IModRegistry reg) {

        jeiHelper = reg.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();

        for (ItemStack stack : disabledItems)
            jeiHelper.getItemBlacklist().addItemToBlacklist(stack);

        reg.addRecipeCategories(new AlloySmelterRecipeCategory(guiHelper));
        reg.addRecipeCategories(new DryingRackRecipeCategory(guiHelper));

        reg.addRecipeHandlers(new AlloySmelterRecipeHandler());
        reg.addRecipeHandlers(new DryingRackRecipeHandler());

        reg.addRecipes(AlloySmelterRecipeMaker.getRecipes());
        reg.addRecipes(DryingRackRecipeMaker.getRecipes());

        reg.addRecipeClickArea(GuiAlloySmelter.class, 80, 34, 25, 16,
                AlloySmelterRecipeCategory.CATEGORY);

        reg.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.alloySmelter),
                AlloySmelterRecipeCategory.CATEGORY);
        reg.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.dryingRack),
                DryingRackRecipeCategory.CATEGORY);

        String descPrefix = "jei.funores.desc.";
        reg.addDescription(new ItemStack(ModBlocks.metalFurnace), descPrefix + Names.METAL_FURNACE);
        reg.addDescription(new ItemStack(ModBlocks.alloySmelter), descPrefix + Names.ALLOY_SMELTER);
        reg.addDescription(new ItemStack(ModBlocks.dryingRack), descPrefix + Names.DRYING_RACK);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {

        // TODO Auto-generated method stub
    }

    @Override
    public void registerIngredients(IModIngredientRegistration arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry arg0) {

        // TODO Auto-generated method stub

    }
}
