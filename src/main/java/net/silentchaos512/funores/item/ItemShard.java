/*
 * Fun Ores -- ItemShard
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

package net.silentchaos512.funores.item;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.lib.registry.IAddRecipes;
import net.silentchaos512.lib.registry.ICustomModel;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.List;

public class ItemShard extends Item implements IDisableable, IAddRecipes, ICustomModel {
    private static final String[] NAMES = {"ender", "blaze", "ghast"};

    @Override
    public void addRecipes(RecipeMaker recipes) {
        ItemStack enderShard = new ItemStack(this, 1, 0);
        if (!FunOres.registry.isItemDisabled(enderShard)) {
            ItemStack enderPearl = new ItemStack(Items.ENDER_PEARL);
            recipes.addShaped("ender_pearl", enderPearl, "ss", "ss", 's', enderShard);
        }

        ItemStack blazeShard = new ItemStack(this, 1, 1);
        if (!FunOres.registry.isItemDisabled(blazeShard)) {
            ItemStack blazeRod = new ItemStack(Items.BLAZE_ROD);
            recipes.addShaped("blaze_rod", blazeRod, "ss", "ss", 's', blazeShard);
        }

        ItemStack ghastShard = new ItemStack(this, 1, 2);
        if (!FunOres.registry.isItemDisabled(ghastShard)) {
            ItemStack ghastTear = new ItemStack(Items.GHAST_TEAR);
            recipes.addShaped("ghast_tear", ghastTear, "ss", "ss", 's', ghastShard);
        }
    }

    @Override
    public List<ItemStack> getSubItems(Item item) {
        List<ItemStack> ret = Lists.newArrayList();
        for (int i = 0; i < NAMES.length; ++i)
            ret.add(new ItemStack(item, 1, i));
        return ret;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (!isInCreativeTab(tab)) return;

        for (ItemStack stack : getSubItems(this))
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + NAMES[MathHelper.clamp(stack.getItemDamage(), 0, NAMES.length - 1)];
    }

    @Override
    public void registerModels() {
        for (int i = 0; i < NAMES.length; ++i) {
            if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, i))) {
                ModelResourceLocation model = new ModelResourceLocation(FunOres.RESOURCE_PREFIX + "shard" + NAMES[i], "inventory");
                ModelLoader.setCustomModelResourceLocation(this, i, model);
            }
        }
    }
}
