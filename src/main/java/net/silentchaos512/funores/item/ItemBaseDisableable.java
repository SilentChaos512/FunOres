/*
 * Fun Ores -- ItemBaseDisableable
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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.lib.registry.ICustomModel;

public abstract class ItemBaseDisableable extends Item implements IDisableable, ICustomModel {
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (!this.isInCreativeTab(tab)) return;
        for (ItemStack stack : getSubItems(this))
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
    }
}
