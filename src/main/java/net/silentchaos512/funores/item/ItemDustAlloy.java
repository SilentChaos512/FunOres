/*
 * Fun Ores -- ItemDustAlloy
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
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.registry.FunOresRegistry;
import net.silentchaos512.lib.registry.RecipeMaker;

import java.util.Arrays;
import java.util.List;

public class ItemDustAlloy extends ItemBaseMetal {

    public ItemDustAlloy() {
        super("dust", "dust");
    }

    @Override
    public List<IMetal> getMetals(Item item) {
//        List<IMetal> list = new ArrayList<>(Arrays.asList(EnumAlloy.values()));
//        return list; // Build fails if not assigned to a variable?
        return Arrays.asList(EnumAlloy.values());
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        // Crafting alloy dust from other dust
        String copper = "dustCopper";
        String tin = "dustTin";
        String zinc = "dustZinc";
        String iron = "dustIron";
        String coal = "dustCoal";
        String nickel = "dustNickel";
        String gold = "dustGold";
        String silver = "dustSilver";

        ItemStack bronze = EnumAlloy.BRONZE.getDust();
        ItemStack brass = EnumAlloy.BRASS.getDust();
        ItemStack steel = EnumAlloy.STEEL.getDust();
        ItemStack invar = EnumAlloy.INVAR.getDust();
        ItemStack electrum = EnumAlloy.ELECTRUM.getDust();

        FunOresRegistry reg = FunOres.registry;

        if (!reg.isItemDisabled(bronze))
            recipes.addShapelessOre("bronze_dust", bronze, copper, copper, copper, tin);
        if (!reg.isItemDisabled(brass))
            recipes.addShapelessOre("brass_dust", brass, copper, copper, copper, zinc);
        if (!reg.isItemDisabled(steel))
            recipes.addShapelessOre("steel_dust", steel, iron, coal, coal, coal);
        if (!reg.isItemDisabled(invar))
            recipes.addShapelessOre("invar_dust", invar, iron, iron, nickel);
        if (!reg.isItemDisabled(electrum))
            recipes.addShapelessOre("electrum_dust", electrum, gold, silver);

        // Smelting dust to ingots
        for (EnumAlloy metal : EnumAlloy.values()) {
            ItemStack dust = metal.getDust();
            ItemStack ingot = metal.getIngot();
            if (!reg.isItemDisabled(dust) && !reg.isItemDisabled(ingot))
                recipes.addSmelting(dust, ingot, 0.6f);
        }
    }
}