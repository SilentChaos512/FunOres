/*
 * Fun Ores -- ItemDustMetal
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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.registry.FunOresRegistry;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemDustMetal extends ItemBaseMetal {

    public ItemDustMetal() {
        super("dust", "dust");
    }

    @Override
    public List<IMetal> getMetals(Item item) {
        List<IMetal> list = new ArrayList<>(Arrays.asList(EnumMetal.values()));
        list.addAll(Arrays.asList(EnumVanillaMetal.values()));
        return list;
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        FunOresRegistry reg = FunOres.registry;
        for (IMetal metal : getMetals(this)) {
            ItemStack dust = metal.getDust();
            ItemStack ingot = metal.getIngot();
            if (!reg.isItemDisabled(dust) && !reg.isItemDisabled(ingot))
                recipes.addSmelting(dust, ingot, 0.6f);
        }
    }
}
