/*
 * Fun Ores -- EnumVanillaExtended
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

import org.apache.commons.lang3.NotImplementedException;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.init.ModItems;

public enum EnumVanillaExtended implements IMetal {
    WOOD(18, true, false, "Wood"),
    STONE(19, true, false, "Stone"),
    OBSIDIAN(20, true, true, "Obsidian"),
    DIAMOND(21, true, true, "Diamond"),
    EMERALD(22, true, true, "Emerald");

    public final int meta;
    public final String name;
    public final boolean allowGear;
    public final boolean allowPlate;

    EnumVanillaExtended(int meta, boolean allowGear, boolean allowPlate, String name) {

        this.meta = meta;
        this.name = name;
        this.allowGear = allowGear;
        this.allowPlate = allowPlate;
    }

    public String getMaterialOreDictKey() {
        switch (this) {
            case WOOD:
                return "plankWood";
            case STONE:
                return "cobblestone";
            case OBSIDIAN:
                return "obsidian";
            case DIAMOND:
                return "gemDiamond";
            case EMERALD:
                return "gemEmerald";
            default:
                throw new NotImplementedException("EnumVanillaExtended: Don't know oredict key for " + name);
        }
    }

    @Override
    public int getMeta() {
        return meta;
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
        return null;
    }

    @Override
    public ItemStack getIngot() {
        return null;
    }

    @Override
    public ItemStack getNugget() {
        return null;
    }

    @Override
    public ItemStack getDust() {
        return null;
    }

    @Override
    public ItemStack getPlate() {
        if (!allowPlate)
            return null;
        return new ItemStack(ModItems.plateBasic, 1, meta);
    }

    @Override
    public ItemStack getGear() {
        if (!allowGear)
            return null;
        return new ItemStack(ModItems.gearBasic, 1, meta);
    }
}
