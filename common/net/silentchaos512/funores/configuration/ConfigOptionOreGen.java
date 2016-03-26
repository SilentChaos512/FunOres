package net.silentchaos512.funores.configuration;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IHasOre;

public class ConfigOptionOreGen extends ConfigOption {

  public static enum BiomeListType {

    BLACKLIST, WHITELIST, FAVORS, AVOIDS;

    public static String[] getValidValues() {

      // What I wouldn't do for list comprehensions in Java...
      String[] result = new String[values().length];
      for (int i = 0; i < result.length; ++i)
        result[i] = values()[i].name();
      return result;
    }
  };

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
      + "FAVORS: Spawns more often in the biome types in the list. Spawns in all other biomes less often.\n"
      + "AVOIDS: Spawns less often in the biome types in the list. Spawns in all other biomes more often.\n"
      + "See: https://github.com/MinecraftForge/MinecraftForge/blob/master/src/main/java/net/minecraftforge/common/BiomeDictionary.java";

  public static final Predicate PREDICATE_STONE = BlockMatcher.forBlock(Blocks.stone);
  public static final Predicate PREDICATE_NETHERRACK = BlockMatcher.forBlock(Blocks.netherrack);
  public static final Predicate PREDICATE_END_STONE = BlockMatcher.forBlock(Blocks.end_stone);

  // Cluster count multiplied/divide by this for favored/avoided biomes.
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

  public boolean enabled = true;
  public int clusterCount = 8;
  public int clusterSize = 8;
  public int minY = 0;
  public int maxY = 128;
  public int rarity = 1;
  // public boolean isBiomeBlacklist = true;
  public BiomeListType biomeListType = BiomeListType.BLACKLIST;
  public List<BiomeDictionary.Type> biomes = Lists.newArrayList();
//  private Dictionary<BiomeDictionary.Type, Float> clusterCountByBiomeType = new Hashtable<BiomeDictionary.Type, Float>();
  public final String oreName;
  public IStringSerializable ore;
  public final Predicate predicate;
  protected boolean isExample = false;

  public ConfigOptionOreGen(IStringSerializable ore) {

    this.ore = ore;
    this.oreName = ore.getName();

    if (ore instanceof IHasOre) {
      IHasOre hasOre = (IHasOre) ore;
      if (hasOre.getDimension() == -1)
        predicate = PREDICATE_NETHERRACK;
      else if (hasOre.getDimension() == 1)
        predicate = PREDICATE_END_STONE;
      else
        predicate = PREDICATE_STONE;
    } else {
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
    if (enabled) {
      clusterCount = c.get(category, "ClusterCount", clusterCount).getInt();
      clusterSize = c.get(category, "ClusterSize", clusterSize).getInt();
      minY = c.get(category, "MinY", minY).getInt();
      maxY = c.get(category, "MaxY", maxY).getInt();
      rarity = c.get(category, "Rarity", rarity).getInt();

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
      // isBiomeBlacklist = !biomeListType.equals("WHITELIST");
      String[] biomeList = c.get(category, "BiomeTypes", new String[] {}).getStringList();
      for (String str : biomeList) {
        boolean foundMatch = false;
        for (BiomeDictionary.Type type : BiomeDictionary.Type.values()) {
          boolean isInList = type.name().toUpperCase().equals(str.toUpperCase());
          foundMatch = isInList ? true : foundMatch;
          if (isInList) {
            biomes.add(type);
          }
//          setClusterCountForBiomeType(type, isInList);
        }
        if (!foundMatch) {
          FunOres.instance.logHelper.warning("Unknown biome type: " + str);
        }
      }
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
    String[] biomeList = c.get(CATEGORY_EXAMPLE, "Biomes", new String[] {}).getStringList();
    // for (String str : biomeList) {
    // for (BiomeDictionary.Type type : BiomeDictionary.Type.values()) {
    // if (type.name().toUpperCase().equals(str.toUpperCase())) {
    // biomes.add(type);
    // }
    // }
    // }

    return this;
  }

//  private void setClusterCountForBiomeType(BiomeDictionary.Type type, boolean isInList) {
//
//    final float multi = Config.oreGenBiomeFavorsMultiplier;
//    float count;
//    switch (biomeListType) {
//      case AVOIDS:
//        count = isInList ? clusterCount / multi : clusterCount * multi;
//        break;
//      case BLACKLIST:
//        count = isInList ? 0 : clusterCount;
//        break;
//      case FAVORS:
//        count = isInList ? clusterCount * multi : clusterCount / multi;
//        break;
//      case WHITELIST:
//        count = isInList ? clusterCount : 0;
//        break;
//      default:
//        count = clusterCount;
//        LogHelper.warning(
//            "ConfigOptionOreGen.setClusterCountForBiomeType - unknown list type: " + biomeListType);
//        break;
//    }
//    clusterCountByBiomeType.put(type, count);
//  }

  @Override
  public ConfigOption validate() {

    clusterCount = MathHelper.clamp_int(clusterCount, CLUSTER_COUNT_MIN, CLUSTER_COUNT_MAX);
    clusterSize = MathHelper.clamp_int(clusterSize, CLUSTER_SIZE_MIN, CLUSTER_SIZE_MAX);
    minY = MathHelper.clamp_int(minY, Y_MIN, Y_MAX);
    maxY = MathHelper.clamp_int(maxY, Y_MIN, Y_MAX);
    rarity = MathHelper.clamp_int(rarity, RARITY_MIN, RARITY_MAX);

    return this;
  }

  public boolean canSpawnInBiome(BiomeGenBase biome) {

    for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome)) {
      for (BiomeDictionary.Type typeInList : biomes) {
        if (type == typeInList) {
          return biomeListType != BiomeListType.BLACKLIST;
        }
      }
    }
    return biomeListType != BiomeListType.WHITELIST;
  }

  public float getClusterCountForBiome(BiomeGenBase biome) {

    // LogHelper.debug(clusterCountByBiomeType);
    float count = 0f;
    float countForBiome;

    boolean foundMatch = false;
    for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome)) {
      for (BiomeDictionary.Type typeInList : biomes) {
        if (type == typeInList) {
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
      }
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

    // for (BiomeDictionary.Type type1 : biomes) {
    // for (BiomeDictionary.Type type2 : BiomeDictionary.getTypesForBiome(biome)) {
    // if (type1 == type2) {
    // // Biome is in list.
    // if (biomeListType == BiomeListType.FAVORS) {
    // return (int) (clusterCount * BIOME_FAVOR_COUNT_MULTI);
    // } else if (biomeListType == BiomeListType.AVOIDS) {
    // return (int) (clusterCount / BIOME_FAVOR_COUNT_MULTI);
    // } else {
    // return clusterCount;
    // }
    // }
    // }
    // }
    // // Empty biome list or biome is not in list.
    // if (biomeListType == BiomeListType.FAVORS) {
    // return (int) (clusterCount / BIOME_FAVOR_COUNT_MULTI);
    // } else if (biomeListType == BiomeListType.AVOIDS) {
    // return (int) (clusterCount * BIOME_FAVOR_COUNT_MULTI);
    // }
    // return clusterCount;
  }
}
