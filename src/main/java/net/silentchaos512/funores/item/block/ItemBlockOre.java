/*
 * Fun Ores -- ItemBlockOre
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

package net.silentchaos512.funores.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.lib.item.ItemBlockSL;

import java.util.List;

public class ItemBlockOre extends ItemBlockSL {

    public ItemBlockOre(Block block) {
        super(block);
    }

    @Override
    public void clAddInformation(ItemStack stack, World world, List list, boolean advanced) {
        ConfigOptionOreGen config = getOreConfig(stack);

        if (!isOreEnabled(config)) {
            list.add(FunOres.instance.localizationHelper.getMiscText("Disabled"));
        }
    }

    protected ConfigOptionOreGen getOreConfig(ItemStack stack) {
        if (this.block == ModBlocks.meatOre) {
            return EnumMeat.byMetadata(stack.getItemDamage()).getConfig();
        } else if (this.block == ModBlocks.mobOre) {
            return EnumMob.byMetadata(stack.getItemDamage()).getConfig();
        } else if (block == ModBlocks.metalOre) {
            return EnumMetal.byMetadata(stack.getItemDamage()).getConfig();
        } else {
            return null;
        }
    }

    protected boolean isOreEnabled(ConfigOptionOreGen config) {
        if (block == ModBlocks.metalOre && Config.disableMetalOres) {
            return false;
        }
        if (block == ModBlocks.meatOre && Config.disableMeatOres) {
            return false;
        }
        if (block == ModBlocks.mobOre && Config.disableMobOres) {
            return false;
        }

        return config.isEnabled();
    }
}
