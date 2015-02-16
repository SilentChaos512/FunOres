package silent.funores.configuration;

import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.config.Configuration;

public class ConfigOptionOreGen extends ConfigOption {

  public static final String COMMENT_ENABLED = "Spawns the ore if set to true, disables it if false.";
  public static final String COMMENT_CLUSTER_COUNT = "The number of veins the game will attempt to spawn per chunk.";
  public static final String COMMENT_CLUSTER_SIZE = "The size of veins that are spawned.";
  public static final String COMMENT_MIN_Y = "The lowest level veins can spawn at.";
  public static final String COMMENT_MAX_Y = "The highest level veins can spawn at.";
  public static final String COMMENT_RARITY = "Only one in this many veins will spawn. Set to 1 for no chance of failure.";

  public boolean enabled = true;
  public int clusterCount = 8;
  public int clusterSize = 8;
  public int minY = 0;
  public int maxY = 128;
  public int rarity = 1;
  public final String oreName;
  public IStringSerializable ore;

  public ConfigOptionOreGen(IStringSerializable ore) {

    this.ore = ore;
    this.oreName = ore.getName();
  }
  
  public ConfigOptionOreGen(IStringSerializable ore, boolean isExample) {
    
    this.ore = ore;
    this.oreName = "example";
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category) {

    return loadValue(c, category + "." + oreName, "World generation for " + oreName + " Ore");
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    enabled = c.getBoolean("Enabled", category, true, COMMENT_ENABLED);
    clusterCount = c.getInt("ClusterCount", category, clusterCount, 0, 1000, COMMENT_CLUSTER_COUNT);
    clusterSize = c.getInt("ClusterSize", category, clusterSize, 0, 1000, COMMENT_CLUSTER_SIZE);
    minY = c.getInt("MinY", category, minY, 0, 255, COMMENT_MIN_Y);
    maxY = c.getInt("MaxY", category, maxY, 0, 255, COMMENT_MAX_Y);
    rarity = c.getInt("Rarity", category, rarity, 0, 1000, COMMENT_RARITY);
    

    return this;
  }

  @Override
  public ConfigOption validate() {

    return this;
  }

}
