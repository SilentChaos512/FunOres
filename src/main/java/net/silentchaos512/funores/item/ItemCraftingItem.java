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
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.*;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.Arrays;
import java.util.List;

public class ItemCraftingItem extends ItemBaseMetal {
    private final String craftingItemName;
    private final boolean isAlloy;
    private final boolean isGear;
    private final boolean isPlate;

    public ItemCraftingItem(String type, boolean isAlloy) {
        super(type, type);
        this.craftingItemName = type;
        this.isAlloy = isAlloy;
        this.isGear = "gear".equals(type);
        this.isPlate = "plate".equals(type);
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
    public String getTranslationKey(ItemStack stack) {
        for (IMetal metal : getMetals(this))
            if (metal.getMeta() == stack.getItemDamage())
                return super.getTranslationKey().replaceFirst("metal|alloy", "") + metal.getName();
        return super.getTranslationKey(stack);
    }

    //    @Override
//    public String getNameForStack(ItemStack stack) {
//        String metalName = null;
//        int meta = stack.getItemDamage();
//        List<IMetal> metals = getMetals(this);
//        if (meta >= 0 && meta < metals.size() && metals.get(meta) != null) {
//            metalName = metals.get(meta).getMetalName();
//        } else {
//            for (IMetal metal : Lists.reverse(metals)) {
//                if (metal.getMeta() == meta) {
//                    metalName = metal.getMetalName();
//                }
//            }
//        }
//        return craftingItemName + metalName;
//    }
}
