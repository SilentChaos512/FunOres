/*
 * Fun Ores -- ExtraRecipes
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

package net.silentchaos512.funores.lib;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.FunOres;

@Deprecated
public class ExtraRecipes {
    public static final String CATEGORY = "recipe_extra";

    public static Configuration config;

    public static void init() {
        config.setCategoryComment(CATEGORY, "Various recipes for vanilla things using other ingots.");
        String comment;
        ItemStack result;

        // Chests
        comment = "Eight logs to four chests.";
        result = new ItemStack(Blocks.CHEST, 4);
        add("ChestsFromLogs", comment, result, "lll", "l l", "lll", 'l', "logWood");
        // Pistons
        comment = "Extra piston recipe using an ingot other than iron.";
        result = new ItemStack(Blocks.PISTON);
        add("Piston.Aluminium", comment, result, "ppp", "cic", "crc", 'p', "plankWood", 'c',
                "cobblestone", 'i', "ingotAluminium", 'r', "dustRedstone");
        add("Piston.Aluminum", comment, result, "ppp", "cic", "crc", 'p', "plankWood", 'c',
                "cobblestone", 'i', "ingotAluminum", 'r', "dustRedstone");
        add("Piston.Bronze", comment, result, "ppp", "cic", "crc", 'p', "plankWood", 'c', "cobblestone",
                'i', "ingotBronze", 'r', "dustRedstone");
        // Hoppers
        comment = "Hopper recipe using an ingot other than iron.";
        result = new ItemStack(Blocks.HOPPER);
        add("Hopper.Aluminium", comment, result, "a a", "aca", " a ", 'a', "ingotAluminium", 'c', "chestWood");
        add("Hopper.Aluminum", comment, result, "a a", "aca", " a ", 'a', "ingotAluminum", 'c', "chestWood");
        // Shears
        comment = "Shears recipe using an ingot other than iron.";
        result = new ItemStack(Items.SHEARS);
        add("Shears.Aluminium", comment, result, " a", "a ", 'a', "ingotAluminium");
        add("Shears.Aluminum", comment, result, " a", "a ", 'a', "ingotAluminum");
    }

    static int lastIndex = -1;

    public static void add(String configName, String comment, ItemStack result, Object... params) {
        boolean enabled = config.getBoolean(configName, CATEGORY, true, comment);
        if (enabled) {
            FunOres.registry.getRecipeMaker().addShapedOre("extra" + (++lastIndex), result, params);
        }
    }
}
