/*
 * Fun Ores -- EnumVanillaMetal
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

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.init.ModItems;

import java.util.Locale;

public enum EnumVanillaMetal implements IMetal {
    IRON(16, "Iron"),
    GOLD(17, "Gold");

    public final int meta;
    public final String name;

    EnumVanillaMetal(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    @Override
    public int getMeta() {
        return meta;
    }

    @Override
    public String getName() {
        return name.toLowerCase(Locale.ROOT);
    }

    @Override
    public String getMetalName() {
        return name;
    }

    @Override
    public boolean isAlloy() {
        return false;
    }

    @Override
    public ItemStack getBlock() {
        Block block = this == IRON ? Blocks.IRON_BLOCK : Blocks.GOLD_BLOCK;
        return new ItemStack(block);
    }

    @Override
    public ItemStack getIngot() {
        Item item = this == IRON ? Items.IRON_INGOT : Items.GOLD_INGOT;
        return new ItemStack(item);
    }

    @Override
    public ItemStack getNugget() {
        return new ItemStack(ModItems.metalNugget, 1, meta);
    }

    @Override
    public ItemStack getDust() {
        return new ItemStack(ModItems.metalDust, 1, meta);
    }

    @Override
    public ItemStack getPlate() {
        return new ItemStack(ModItems.plateBasic, 1, meta);
    }

    @Override
    public ItemStack getGear() {
        return new ItemStack(ModItems.gearBasic, 1, meta);
    }
}
