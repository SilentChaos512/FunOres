/*
 * Fun Ores -- ItemIngotMetal
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
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemIngotMetal extends ItemBaseMetal {

    public ItemIngotMetal() {
        super(Names.METAL_INGOT, "Ingot", "ingot");
    }

    @Override
    public List<IMetal> getMetals(Item item) {
        List<IMetal> list = new ArrayList(Arrays.asList(EnumMetal.values())); // No vanilla metals for ingots!
        return list; // Build fails if not assigned to a variable?
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        for (EnumMetal metal : EnumMetal.values()) {
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

        // Iron
        if (!FunOres.registry.isItemDisabled(EnumVanillaMetal.IRON.getNugget())) {
            ItemStack nugget = EnumVanillaMetal.IRON.getNugget();
            ItemStack ingot = EnumVanillaMetal.IRON.getIngot();
            recipes.addCompression("ingot_iron", nugget, ingot, 9);
        }
    }

    @Override
    public void addOreDict() {
        super.addOreDict();

        // Alternative spelling of aluminium
        if (!FunOres.registry.isItemDisabled(EnumMetal.ALUMINIUM.getIngot()))
            OreDictionary.registerOre("ingotAluminum", EnumMetal.ALUMINIUM.getIngot());
    }
}
