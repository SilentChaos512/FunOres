/*
 * Fun Ores -- ItemCraftingItem
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.*;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.Arrays;
import java.util.List;

public class ItemCraftingItem extends ItemBaseMetal {
    public static final int BASE_METALS_COUNT = 18;

    public final String craftingItemName;
    public final boolean isAlloy;
    public final boolean isGear;
    public final boolean isPlate;

    public ItemCraftingItem(String name, boolean isAlloy) {
        super(Names.CRAFTING_ITEM, name, name.toLowerCase());
        this.craftingItemName = name;
        this.isAlloy = isAlloy;
        this.isGear = craftingItemName.equals(Names.GEAR);
        this.isPlate = craftingItemName.equals(Names.PLATE);
    }

    @Override
    public List<IMetal> getMetals(Item item) {
        List<IMetal> metals = Lists.newArrayList();

        if (isAlloy) {
            // Alloys
            metals.addAll(Arrays.asList(EnumAlloy.values()));
        } else {
            // Basic metals (including vanilla)
            metals.addAll(Arrays.asList(EnumMetal.values()));
            metals.addAll(Arrays.asList(EnumVanillaMetal.values()));
            // Extended vanilla
            for (EnumVanillaExtended extended : EnumVanillaExtended.values())
                if ((isGear && extended.allowGear) || (isPlate && extended.allowPlate))
                    metals.add(extended);
            return metals;
        }

        return metals;
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        if (isGear) {
            for (IMetal metal : getMetals(this)) {
                ItemStack gear = metal.getGear();
                if (gear != null && !FunOres.registry.isItemDisabled(gear)) {
                    for (String metalName : metal.getMetalNames()) {
                        String mat = metal instanceof EnumVanillaExtended ? ((EnumVanillaExtended) metal).getMaterialOreDictKey() : "ingot" + metalName;
                        recipes.addShapedOre("gear_" + metalName, gear, " m ", "mim", " m ", 'm', mat, 'i',
                                metal == EnumVanillaExtended.WOOD || metal == EnumVanillaExtended.STONE ? "stickWood" : "ingotIron");
                    }
                }
            }
        }
    }

    @Override
    public void addOreDict() {
        super.addOreDict();

        if (!isAlloy)
            OreDictionary.registerOre(oreDictPrefix + "Aluminum", new ItemStack(this, 1, EnumMetal.ALUMINIUM.meta));
    }

    @Override
    public String getNameForStack(ItemStack stack) {
        String metalName = null;
        int meta = stack.getItemDamage();
        List<IMetal> metals = getMetals(this);
        if (meta >= 0 && meta < metals.size() && metals.get(meta) != null) {
            metalName = metals.get(meta).getMetalName();
        } else {
            for (IMetal metal : Lists.reverse(metals)) {
                if (metal.getMeta() == meta) {
                    metalName = metal.getMetalName();
                }
            }
        }
        return craftingItemName + metalName;
    }

    @Override
    public void clAddInformation(ItemStack stack, World world, List list, boolean advanced) {
        // TODO
    }
}
