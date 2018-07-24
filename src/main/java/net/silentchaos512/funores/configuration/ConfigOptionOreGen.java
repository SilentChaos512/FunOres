/*
 * Fun Ores -- ConfigOptionOreGen
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

package net.silentchaos512.funores.configuration;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.lib.IHasOre;
import net.silentchaos512.lib.util.BiomeHelper;

import java.util.List;

public class ConfigOptionOreGen extends ConfigOption {
    public enum BiomeListType {

        BLACKLIST, WHITELIST, FAVORS, AVOIDS;

        public static String[] getValidValues() {

            // What I wouldn't do for list comprehensions in Java...
            String[] result = new String[values().length];
            for (int i = 0; i < result.length; ++i)
                result[i] = values()[i].name();
            return result;
        }
    }

    ;

    /*
     * Config comments
     */
    public static final String COMMENT_ENABLED = "Spawns the ore if set to true, disables it if false.";
    public static final String COMMENT_CLUSTER_COUNT = "The number of veins the game will attempt to spawn per chunk.";
    public static final String COMMENT_CLUSTER_SIZE = "The size of veins that are spawned.";
    public static final String COMMENT_MIN_Y = "The lowest level veins can spawn at.";
    public static final String COMMENT_MAX_Y = "The highest level veins can spawn at.";
    public static final String COMMENT_RARITY = "Only one in this many veins will spawn. Set to 1 for no chance of failure.";
    public static final String COMMENT_BIOME_LIST = "List of biomes to consider. The effects of the list depends on the list type.";
    public static final String COMMENT_BIOME_LIST_TYPE = "Biome list type.\n"
            + "BLACKLIST: Do not spawn in biome types in the list. An empty blacklist means all biomes are OK.\n"
            + "WHITELIST: Spawn only in biome types in the list. An empty whitelist will fail to spawn the ore!\n"
            + "FAVORS: Spawns more often in the biomes in the list. Spawns in all other biomes less often.\n"
            + "AVOIDS: Spawns less often in the biomes in the list. Spawns in all other biomes more often.\n"
            + "See: https://github.com/MinecraftForge/MinecraftForge/blob/master/src/main/java/net/minecraftforge/common/BiomeDictionary.java";

    /*
     * Ore spawn predicates (what block the ore replaces)
     */
    public static final Predicate PREDICATE_STONE = BlockMatcher.forBlock(Blocks.STONE);
    public static final Predicate PREDICATE_NETHERRACK = BlockMatcher.forBlock(Blocks.NETHERRACK);
    public static final Predicate PREDICATE_END_STONE = BlockMatcher.forBlock(Blocks.END_STONE);

    /*
     * Min/max values for various properties.
     */
    public static final int CLUSTER_COUNT_MIN = 0;
    public static final int CLUSTER_COUNT_MAX = 1000;
    public static final int CLUSTER_SIZE_MIN = 0;
    public static final int CLUSTER_SIZE_MAX = 1000;
    public static final int Y_MIN = 0;
    public static final int Y_MAX = 255;
    public static final int RARITY_MIN = 1;
    public static final int RARITY_MAX = 1000;
    public static final String[] BIOMES_LIST_TYPE_VALID_VALUES = BiomeListType.getValidValues();
    protected static String CATEGORY_EXAMPLE = "an_example";

    /*
     * Ore spawn properties
     */

    /**
     * Legacy support. Setting cluster count to 0 would have been better, but too late now.
     */
    @Deprecated
    protected boolean enabled = true;
    /**
     * The number of veins to spawn per chunk
     */
    public int clusterCount = 8;
    /**
     * The size of veins
     */
    public int clusterSize = 8;
    /**
     * The lowest Y-level the ore can be found at.
     */
    public int minY = 0;
    /**
     * The highest Y-level the ore can be found at.
     */
    public int maxY = 128;
    /**
     * The ore has a 1/rarity chance of spawning for each vein the world generator attempts to
     * place.
     */
    public int rarity = 1;
    /**
     * How do we use the biome list?
     */
    public BiomeListType biomeListType = BiomeListType.BLACKLIST;
    /**
     * The biome list. How this is used depends on biomeListType
     */
    public List<String> biomes = Lists.newArrayList();
    /**
     * The name of the ore. Used in the config file and a couple other places.
     */
    public final String oreName;
    /**  */
    public IHasOre ore;
    public final Predicate predicate;
    protected boolean isExample = false;

    public ConfigOptionOreGen(IHasOre ore) {
        this.ore = ore;
        this.oreName = ore.getName();

        switch (ore.getDimension()) {
            case -1:
                predicate = PREDICATE_NETHERRACK;
                break;
            case 1:
                predicate = PREDICATE_END_STONE;
                break;
            default:
                predicate = PREDICATE_STONE;
        }
    }

    public ConfigOptionOreGen(boolean example) {
        this.ore = null;
        this.oreName = "example";
        this.isExample = example;
        predicate = PREDICATE_STONE;
    }

    @Override
    public ConfigOption loadValue(Configuration c, String category) {
        return loadValue(c, category + "." + oreName, "World generation for " + oreName + " Ore");
    }

    @Override
    public ConfigOption loadValue(Configuration c, String category, String comment) {
        if (isExample) {
            return loadExample(c);
        }

        enabled = c.get(category, "Enabled", enabled).getBoolean();

        clusterCount = c.get(category, "ClusterCount", clusterCount).getInt();
        clusterSize = c.get(category, "ClusterSize", clusterSize).getInt();
        minY = c.get(category, "MinY", minY).getInt();
        maxY = c.get(category, "MaxY", maxY).getInt();
        rarity = c.get(category, "Rarity", rarity).getInt();

        if (clusterCount <= 0) {
            enabled = false;
        }

        // Biome list type
        String listType = c.get(category, "BiomeListType", "BLACKLIST").getString().toUpperCase();
        // Add missing S?
        if (listType.equals("FAVOR") || listType.equals("AVOID")) {
            listType += "S";
        }
        // Get the enum...
        for (BiomeListType type : BiomeListType.values()) {
            if (type.name().equals(listType)) {
                biomeListType = type;
            }
        }

        // Get biome list
        String[] biomeList = c.get(category, "Biomes", new String[0]).getStringList();
        for (String str : biomeList) {
            // if (!str.matches("[a-z_]+:.+"))
            // str = "minecraft:" + str;
            biomes.add(str.trim().replaceFirst(",$", ""));
        }
        if (biomes.isEmpty()) {
            c.renameProperty(category, "BiomeTypes", "Biomes");
        }

        return this.validate();
    }

    protected ConfigOption loadExample(Configuration c) {
        enabled = c.getBoolean("Enabled", CATEGORY_EXAMPLE, enabled, COMMENT_ENABLED);
        clusterCount = c.getInt("ClusterCount", CATEGORY_EXAMPLE, clusterCount, CLUSTER_COUNT_MIN,
                CLUSTER_COUNT_MAX, COMMENT_CLUSTER_COUNT);
        clusterSize = c.getInt("ClusterSize", CATEGORY_EXAMPLE, clusterSize, CLUSTER_SIZE_MIN,
                CLUSTER_SIZE_MAX, COMMENT_CLUSTER_SIZE);
        minY = c.getInt("MinY", CATEGORY_EXAMPLE, minY, Y_MIN, Y_MAX, COMMENT_MIN_Y);
        maxY = c.getInt("MaxY", CATEGORY_EXAMPLE, maxY, Y_MIN, Y_MAX, COMMENT_MAX_Y);
        rarity = c.getInt("Rarity", CATEGORY_EXAMPLE, rarity, RARITY_MIN, RARITY_MAX, COMMENT_RARITY);
        String biomeListType = c.getString("BiomeListType", CATEGORY_EXAMPLE, "BLACKLIST",
                COMMENT_BIOME_LIST_TYPE, BIOMES_LIST_TYPE_VALID_VALUES).toUpperCase();
        // isBiomeBlacklist = !biomeListType.equals("WHITELIST");
        String[] biomeList = c.get(CATEGORY_EXAMPLE, "Biomes", new String[]{"dry", "minecraft:forest"}).getStringList();
        // for (String str : biomeList) {
        // for (BiomeDictionary.Type type : BiomeDictionary.Type.values()) {
        // if (type.name().toUpperCase().equals(str.toUpperCase())) {
        // biomes.add(type);
        // }
        // }
        // }

        return this;
    }

    @Override
    public ConfigOption validate() {
        clusterCount = MathHelper.clamp(clusterCount, CLUSTER_COUNT_MIN, CLUSTER_COUNT_MAX);
        clusterSize = MathHelper.clamp(clusterSize, CLUSTER_SIZE_MIN, CLUSTER_SIZE_MAX);
        minY = MathHelper.clamp(minY, Y_MIN, Y_MAX);
        maxY = MathHelper.clamp(maxY, Y_MIN, Y_MAX);
        rarity = MathHelper.clamp(rarity, RARITY_MIN, RARITY_MAX);

        return this;
    }

    public boolean isEnabled() {
        return enabled && clusterCount > 0;
    }

    public boolean isBiomeInList(Biome biome) {
        for (String str : biomes) {
            // Biome type match?
            for (BiomeDictionary.Type type : BiomeHelper.getTypes(biome))
                if (BiomeHelper.getTypeName(type).equalsIgnoreCase(str))
                    return true;

            // Is listed biome an exact match?
            Biome b = Biome.REGISTRY.getObject(new ResourceLocation(str));
            if (b != null && b.equals(biome))
                return true;
            // Trying adding minecraft resource prefix
            b = Biome.REGISTRY.getObject(new ResourceLocation("minecraft:" + str));
            if (b != null && b.equals(biome))
                return true;
        }

        return false;
    }

    public boolean canSpawnInBiome(Biome biome) {
        return isBiomeInList(biome) ? biomeListType != BiomeListType.BLACKLIST : biomeListType != BiomeListType.WHITELIST;
    }

    public float getClusterCountForBiome(Biome biome) {
        float count = 0f;
        float countForBiome;
        boolean foundMatch = false;

        if (isBiomeInList(biome)) {
            foundMatch = true;
            switch (biomeListType) {
                case AVOIDS:
                    countForBiome = clusterCount / Config.oreGenBiomeFavorsMultiplier;
                    break;
                case BLACKLIST:
                    countForBiome = 0f;
                    break;
                case FAVORS:
                    countForBiome = clusterCount * Config.oreGenBiomeFavorsMultiplier;
                    break;
                case WHITELIST:
                    countForBiome = clusterCount;
                    break;
                default:
                    countForBiome = 0f;
            }
            count = Math.max(count, countForBiome);
        }

        if (!foundMatch) {
            switch (biomeListType) {
                // Favoring a non-avoided biome?
                case AVOIDS:
                    count = clusterCount * Config.oreGenBiomeFavorsMultiplier;
                    break;
                // Avoiding a non-favored biome?
                case FAVORS:
                    count = clusterCount / Config.oreGenBiomeFavorsMultiplier;
                    break;
                // Empty whitelist?
                case WHITELIST:
                    count = 0;
                default:
                    break;
            }
        }

        return count == 0 ? clusterCount : count;
    }
}
