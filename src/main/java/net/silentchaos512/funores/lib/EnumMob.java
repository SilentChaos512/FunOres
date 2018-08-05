/*
 * Fun Ores -- EnumMob
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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockOreMob;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.init.ModBlocks;

import java.util.Locale;

public enum EnumMob implements IHasOre, ILootTableDrops {
    ZOMBIE(0, "Zombie"),
    SKELETON(1, "Skeleton"),
    CREEPER(2, "Creeper"),
    SPIDER(3, "Spider"),
    ENDERMAN(4, "Enderman"),
    SLIME(5, "Slime"),
    WITCH(6, "Witch"),
    PIGMAN(7, "Pigman", -1),
    GHAST(8, "Ghast", -1),
    MAGMA_CUBE(9, "MagmaCube", -1),
    WITHER(10, "Wither", -1),
    BLAZE(11, "Blaze", -1),
    GUARDIAN(12, "Guardian");

    public final int meta;
    public final String name;
    public final int dimension;

    EnumMob(int meta, String name) {
        this(meta, name, 0);
    }

    EnumMob(int meta, String name, int dimension) {
        this.meta = meta;
        this.name = name;
        this.dimension = dimension;
    }

    public int getMeta() {
        return meta;
    }

    @Override
    public String getName() {
        return name.toLowerCase(Locale.ROOT);
    }

    @Override
    public IBlockState getOre() {
        return ModBlocks.mobOre.getDefaultState().withProperty(BlockOreMob.MOB, this);
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    public ConfigOptionOreGenBonus getConfig() {
        switch (this) {
            case ZOMBIE:
                return Config.zombie;
            case SKELETON:
                return Config.skeleton;
            case CREEPER:
                return Config.creeper;
            case SPIDER:
                return Config.spider;
            case ENDERMAN:
                return Config.enderman;
            case SLIME:
                return Config.slime;
            case WITCH:
                return Config.witch;
            case PIGMAN:
                return Config.pigman;
            case GHAST:
                return Config.ghast;
            case MAGMA_CUBE:
                return Config.magmaCube;
            case WITHER:
                return Config.wither;
            case BLAZE:
                return Config.blaze;
            case GUARDIAN:
                return Config.guardian;
            default:
                FunOres.logHelper.error("Don't know config for ore " + name + "!");
                return null;
        }
    }

    @Override
    public EntityLivingBase getEntityLiving(World worldIn) {
        switch (this) {
            case ZOMBIE:
                return new EntityZombie(worldIn); // TODO: Husk variation?
            case SKELETON:
                return new EntitySkeleton(worldIn); // TODO: Stray variation?
            case CREEPER:
                return new EntityCreeper(worldIn);
            case SPIDER:
                return new EntitySpider(worldIn);
            case ENDERMAN:
                return new EntityEnderman(worldIn);
            case SLIME:
                return new EntitySlime(worldIn);
            case WITCH:
                return new EntityWitch(worldIn);
            case PIGMAN:
                return new EntityPigZombie(worldIn);
            case GHAST:
                return new EntityGhast(worldIn);
            case MAGMA_CUBE:
                return new EntityMagmaCube(worldIn);
            case WITHER:
                return new EntityWither(worldIn);
            case BLAZE:
                return new EntityBlaze(worldIn);
            case GUARDIAN:
                return new EntityGuardian(worldIn);
            //@formatter:on
            default:
                FunOres.logHelper.error("Don't know config for ore " + name + "!");
                return null;
        }
    }

    @Override
    public ResourceLocation getLootTable(EntityLivingBase entityLiving) {
        switch (this) {
            case ZOMBIE:
                return LootTableList.ENTITIES_ZOMBIE;
            case SKELETON:
                return LootTableList.ENTITIES_SKELETON;
            case CREEPER:
                return LootTableList.ENTITIES_CREEPER;
            case SPIDER:
                if (FunOres.random.nextFloat() < 0.25f)
                    return LootTableList.ENTITIES_CAVE_SPIDER;
                else
                    return LootTableList.ENTITIES_SPIDER;
            case ENDERMAN:
                return LootTableList.ENTITIES_ENDERMAN;
            case SLIME:
                return LootTableList.ENTITIES_SLIME;
            case WITCH:
                return LootTableList.ENTITIES_WITCH;
            case PIGMAN:
                return LootTableList.ENTITIES_ZOMBIE_PIGMAN;
            case GHAST:
                return LootTableList.ENTITIES_GHAST;
            case MAGMA_CUBE:
                return LootTableList.ENTITIES_MAGMA_CUBE;
            case WITHER:
                return LootTableList.ENTITIES_WITHER_SKELETON;
            case BLAZE:
                return LootTableList.ENTITIES_BLAZE;
            case GUARDIAN:
                return LootTableList.ENTITIES_GUARDIAN;
            default:
                FunOres.logHelper.error("Don't know config for ore " + name + "!");
                return null;
        }
    }

    public static EnumMob byMetadata(int meta) {
        if (meta <= 0 || meta >= values().length) {
            meta = 0;
        }
        return values()[meta];
    }
}
