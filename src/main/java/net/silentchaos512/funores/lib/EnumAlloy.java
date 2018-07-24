/*
 * Fun Ores -- EnumAlloy
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

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;

public enum EnumAlloy implements IStringSerializable, IMetal {
    BRONZE(0, "Bronze"),
    BRASS(1, "Brass"),
    STEEL(2, "Steel"),
    INVAR(3, "Invar"),
    ELECTRUM(4, "Electrum"),
    ENDERIUM(5, "Enderium"),
    PRISMARIINIUM(6, "Prismarinium");

    private final int meta;
    private final String name;

    EnumAlloy(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    public int getMeta() {
        return meta;
    }

    @Override
    public String getMetalName() {
        return name;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    public static EnumAlloy byMetadata(int meta) {
        if (meta < 0 || meta >= values().length) {
            meta = 0;
        }
        return values()[meta];
    }

    public ItemStack getBlock() {
        return new ItemStack(ModBlocks.alloyBlock, 1, meta);
    }

    public ItemStack getIngot() {
        return new ItemStack(ModItems.alloyIngot, 1, meta);
    }

    public ItemStack getNugget() {
        return new ItemStack(ModItems.alloyNugget, 1, meta);
    }

    public ItemStack getDust() {
        return new ItemStack(ModItems.alloyDust, 1, meta);
    }

    @Override
    public boolean isAlloy() {
        return true;
    }

    @Override
    public ItemStack getPlate() {
        return new ItemStack(ModItems.plateAlloy, 1, meta);
    }

    @Override
    public ItemStack getGear() {
        return new ItemStack(ModItems.gearAlloy, 1, meta);
    }
}
