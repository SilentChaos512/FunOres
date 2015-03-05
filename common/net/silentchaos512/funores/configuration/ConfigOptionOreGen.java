package net.silentchaos512.funores.configuration;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;

public class ConfigOptionOreGen extends ConfigOption {

  public static final String COMMENT_ENABLED = "Spawns the ore if set to true, disables it if false.";
  public static final String COMMENT_CLUSTER_COUNT = "The number of veins the game will attempt to spawn per chunk.";
  public static final String COMMENT_CLUSTER_SIZE = "The size of veins that are spawned.";
  public static final String COMMENT_MIN_Y = "The lowest level veins can spawn at.";
  public static final String COMMENT_MAX_Y = "The highest level veins can spawn at.";
  public static final String COMMENT_RARITY = "Only one in this many veins will spawn. Set to 1 for no chance of failure.";

  public static final int CLUSTER_COUNT_MIN = 0;
  public static final int CLUSTER_COUNT_MAX = 1000;
  public static final int CLUSTER_SIZE_MIN = 0;
  public static final int CLUSTER_SIZE_MAX = 1000;
  public static final int Y_MIN = 0;
  public static final int Y_MAX = 255;
  public static final int RARITY_MIN = 1;
  public static final int RARITY_MAX = 1000;

  protected static String CATEGORY_EXAMPLE = "an_example";

  public boolean enabled = true;
  public int clusterCount = 8;
  public int clusterSize = 8;
  public int minY = 0;
  public int maxY = 128;
  public int rarity = 1;
  public final String oreName;
  public IStringSerializable ore;
  protected boolean isExample = false;

  public ConfigOptionOreGen(IStringSerializable ore) {

    this.ore = ore;
    this.oreName = ore.getName();
  }

  public ConfigOptionOreGen(boolean example) {

    this.ore = null;
    this.oreName = "example";
    this.isExample = example;
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

    return this;
  }

  @Override
  public ConfigOption validate() {

    clusterCount = MathHelper.clamp_int(clusterCount, CLUSTER_COUNT_MIN, CLUSTER_COUNT_MAX);
    clusterSize = MathHelper.clamp_int(clusterSize, CLUSTER_SIZE_MIN, CLUSTER_SIZE_MAX);
    minY = MathHelper.clamp_int(minY, Y_MIN, Y_MAX);
    maxY = MathHelper.clamp_int(maxY, Y_MIN, Y_MAX);
    rarity = MathHelper.clamp_int(rarity, RARITY_MIN, RARITY_MAX);

    return this;
  }

}
