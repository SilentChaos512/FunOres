/*
 * Fun Ores -- ItemIngotAlloy
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
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.Arrays;
import java.util.List;

public class ItemIngotAlloy extends ItemBaseMetal {

    public ItemIngotAlloy() {
        super("ingot", "ingot");
    }

    @Override
    public List<IMetal> getMetals(Item item) {
//        List<IMetal> list = new ArrayList<>(Arrays.asList(EnumAlloy.values()));
//        return list; // Build fails if not assigned to a variable?
        return Arrays.asList(EnumAlloy.values());
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        for (EnumAlloy metal : EnumAlloy.values()) {
            boolean disabledNugget = FunOres.registry.isItemDisabled(metal.getNugget());
            boolean disabledIngot = FunOres.registry.isItemDisabled(metal.getIngot());
            boolean disabledBlock = FunOres.registry.isItemDisabled(metal.getBlock());

            // Ingots <--> Blocks
            if (!disabledIngot && !disabledBlock)
                recipes.addCompression("block_" + metal.getMetalName(), metal.getIngot(), metal.getBlock(), 9);
            // Nuggets <--> Ingots
            if (!disabledNugget && !disabledIngot)
                recipes.addCompression("ingot_" + metal.getMetalName(), metal.getNugget(), metal.getIngot(), 9);
        }
    }
}
