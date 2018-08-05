/*
 * Fun Ores -- EnumMetal
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

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockOreMetal;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;

import java.util.Locale;

public enum EnumMetal implements IHasOre, IMetal {
    COPPER(0, "Copper"),
    TIN(1, "Tin"),
    SILVER(2, "Silver"),
    LEAD(3, "Lead"),
    NICKEL(4, "Nickel"),
    PLATINUM(5, "Platinum"),
    ALUMINIUM(6, "Aluminium"),
    ZINC(7, "Zinc"),
    TITANIUM(8, "Titanium"),
    OSMIUM(9, "Osmium");

    public final int meta;
    public final int dimension;
    public final String name;

    EnumMetal(int meta, String name) {
        this(meta, name, 0);
    }

    EnumMetal(int meta, String name, int dimension) {
        this.meta = meta;
        this.name = name;
        this.dimension = dimension;
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
    public String[] getMetalNames() {
        if (this == ALUMINIUM)
            return new String[]{"Aluminium", "Aluminum"};
        return new String[]{getMetalName()};
    }

    @Override
    public String getName() {
        return name.toLowerCase(Locale.ROOT);
    }

    public static EnumMetal byMetadata(int meta) {
        if (meta < 0 || meta >= values().length) {
            meta = 0;
        }
        return values()[meta];
    }

    @Override
    public IBlockState getOre() {
        return ModBlocks.metalOre.getDefaultState().withProperty(BlockOreMetal.METAL, this);
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    @Override
    public ItemStack getBlock() {
        return new ItemStack(ModBlocks.metalBlock, 1, meta);
    }

    @Override
    public ItemStack getIngot() {
        return new ItemStack(ModItems.metalIngot, 1, meta);
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
    public boolean isAlloy() {
        return false;
    }

    @Override
    public ItemStack getPlate() {
        return new ItemStack(ModItems.plateBasic, 1, meta);
    }

    @Override
    public ItemStack getGear() {
        return new ItemStack(ModItems.gearBasic, 1, meta);
    }

    public ConfigOptionOreGen getConfig() {
        switch (meta) {
            case 0:
                return Config.copper;
            case 1:
                return Config.tin;
            case 2:
                return Config.silver;
            case 3:
                return Config.lead;
            case 4:
                return Config.nickel;
            case 5:
                return Config.platinum;
            case 6:
                return Config.aluminium;
            case 7:
                return Config.zinc;
            case 8:
                return Config.titanium;
            case 9:
                return Config.osmium;
            default:
                FunOres.instance.logHelper.debug("EnumMetal: Don't know config for ore with meta " + meta);
                return null;
        }
    }

    public ItemStack getBonus() {
        switch (this) {
            case ALUMINIUM:
                return ALUMINIUM.getDust();
            case COPPER:
                return EnumVanillaMetal.GOLD.getDust();
            case LEAD:
                return SILVER.getDust();
            case NICKEL:
                return PLATINUM.getDust();
            case PLATINUM:
                return TITANIUM.getDust();
            case SILVER:
                return LEAD.getDust();
            case TIN:
                return EnumVanillaMetal.IRON.getDust();
            case TITANIUM:
                return ZINC.getDust();
            case ZINC:
                return TIN.getDust();
            case OSMIUM:
                return EnumVanillaMetal.IRON.getDust();
            default:
                return null;
        }
    }
}
