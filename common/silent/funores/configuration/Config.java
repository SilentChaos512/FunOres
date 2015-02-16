package silent.funores.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import silent.funores.core.util.LogHelper;
import silent.funores.lib.EnumMeat;
import silent.funores.lib.EnumMetal;
import silent.funores.lib.EnumMob;


public class Config {
  
  // Metal ores
  public static ConfigOptionOreGen copper = new ConfigOptionOreGen(EnumMetal.COPPER);
  public static ConfigOptionOreGen tin = new ConfigOptionOreGen(EnumMetal.TIN);
  public static ConfigOptionOreGen silver = new ConfigOptionOreGen(EnumMetal.SILVER);
  public static ConfigOptionOreGen lead = new ConfigOptionOreGen(EnumMetal.LEAD);
  public static ConfigOptionOreGen nickel = new ConfigOptionOreGen(EnumMetal.NICKEL);
  public static ConfigOptionOreGen platinum = new ConfigOptionOreGen(EnumMetal.PLATINUM);
  public static ConfigOptionOreGen aluminium = new ConfigOptionOreGen(EnumMetal.ALUMINIUM);
  public static ConfigOptionOreGen zinc = new ConfigOptionOreGen(EnumMetal.ZINC);
  
  // Meat ores
  public static ConfigOptionOreGen pig = new ConfigOptionOreGen(EnumMeat.PIG);
  public static ConfigOptionOreGen fish = new ConfigOptionOreGen(EnumMeat.FISH);
  public static ConfigOptionOreGen cow = new ConfigOptionOreGen(EnumMeat.COW);
  public static ConfigOptionOreGen chicken = new ConfigOptionOreGen(EnumMeat.CHICKEN);
  public static ConfigOptionOreGen rabbit = new ConfigOptionOreGen(EnumMeat.RABBIT);
  public static ConfigOptionOreGen sheep = new ConfigOptionOreGen(EnumMeat.SHEEP);
  
  // Mob ores
  public static ConfigOptionOreGen zombie = new ConfigOptionOreGen(EnumMob.ZOMBIE);
  public static ConfigOptionOreGen skeleton = new ConfigOptionOreGen(EnumMob.SKELETON);
  public static ConfigOptionOreGen creeper = new ConfigOptionOreGen(EnumMob.CREEPER);
  public static ConfigOptionOreGen spider = new ConfigOptionOreGen(EnumMob.SPIDER);
  public static ConfigOptionOreGen enderman = new ConfigOptionOreGen(EnumMob.ENDERMAN);
  public static ConfigOptionOreGen slime = new ConfigOptionOreGen(EnumMob.SLIME);
  public static ConfigOptionOreGen witch = new ConfigOptionOreGen(EnumMob.WITCH);
  public static ConfigOptionOreGen pigman = new ConfigOptionOreGen(EnumMob.PIGMAN);
  public static ConfigOptionOreGen ghast = new ConfigOptionOreGen(EnumMob.GHAST);
  public static ConfigOptionOreGen magmaCube = new ConfigOptionOreGen(EnumMob.MAGMA_CUBE);
  public static ConfigOptionOreGen wither = new ConfigOptionOreGen(EnumMob.WITHER);
  
  private static Configuration c;

  public static final String CATEGORY_METAL_ORE = "WorldGen.MetalOre";
  public static final String CATEGORY_MEAT_ORE = "WorldGen.MeatOre";
  public static final String CATEGORY_MOB_ORE = "WorldGen.MobOre";
  
  public static void init(File file) {
    
    c = new Configuration(file);
    
    try {
      c.load();
      
      /*
       * Metal Ores
       */
      
      copper.enabled = true;
      copper.clusterCount = 10;
      copper.clusterSize = 8;
      copper.minY = 40;
      copper.maxY = 75;
      copper.rarity = 1;
      copper.loadValue(c, CATEGORY_METAL_ORE);
      
      tin.enabled = true;
      tin.clusterCount = 10;
      tin.clusterSize = 8;
      tin.minY = 20;
      tin.maxY = 55;
      tin.rarity = 1;
      tin.loadValue(c, CATEGORY_METAL_ORE);
      
      silver.enabled = true;
      silver.clusterCount = 6;
      silver.clusterSize = 8;
      silver.minY = 5;
      silver.maxY = 30;
      silver.rarity = 1;
      silver.loadValue(c, CATEGORY_METAL_ORE);
      
      lead.enabled = true;
      lead.clusterCount = 4;
      lead.clusterSize = 8;
      lead.minY = 10;
      lead.maxY = 35;
      lead.rarity = 1;
      lead.loadValue(c, CATEGORY_METAL_ORE);
      
      nickel.enabled = true;
      nickel.clusterCount = 2;
      nickel.clusterSize = 4;
      nickel.minY = 5;
      nickel.maxY = 20;
      nickel.rarity = 1;
      nickel.loadValue(c, CATEGORY_METAL_ORE);
      
      platinum.enabled = true;
      platinum.clusterCount = 4;
      platinum.clusterSize = 6;
      platinum.minY = 5;
      platinum.maxY = 15;
      platinum.rarity = 10;
      platinum.loadValue(c, CATEGORY_METAL_ORE);
      
      aluminium.enabled = true;
      aluminium.clusterCount = 6;
      aluminium.clusterSize = 7;
      aluminium.minY = 10;
      aluminium.maxY = 50;
      aluminium.rarity = 1;
      aluminium.loadValue(c, CATEGORY_METAL_ORE);
      
      zinc.enabled = true;
      zinc.clusterCount = 4;
      zinc.clusterSize = 8;
      zinc.minY = 20;
      zinc.maxY = 70;
      zinc.rarity = 1;
      zinc.loadValue(c, CATEGORY_METAL_ORE);
      
      /*
       * Meat Ores
       */
      
      int meatClusterCount = 1;
      int meatClusterSize = 28;
      int meatMinY = 32;
      int meatMaxY = 84;
      int meatRarity = 20;
      
      pig.enabled = true;
      pig.clusterCount = meatClusterCount;
      pig.clusterSize = meatClusterSize;
      pig.minY = meatMinY;
      pig.maxY = meatMaxY;
      pig.rarity = meatRarity;
      pig.loadValue(c, CATEGORY_MEAT_ORE);
      
      fish.enabled = true;
      fish.clusterCount = meatClusterCount;
      fish.clusterSize = meatClusterSize;
      fish.minY = meatMinY;
      fish.maxY = meatMaxY;
      fish.rarity = meatRarity;
      fish.loadValue(c, CATEGORY_MEAT_ORE);
      
      cow.enabled = true;
      cow.clusterCount = meatClusterCount;
      cow.clusterSize = meatClusterSize;
      cow.minY = meatMinY;
      cow.maxY = meatMaxY;
      cow.rarity = meatRarity;
      cow.loadValue(c, CATEGORY_MEAT_ORE);
      
      chicken.enabled = true;
      chicken.clusterCount = meatClusterCount;
      chicken.clusterSize = meatClusterSize;
      chicken.minY = meatMinY;
      chicken.maxY = meatMaxY;
      chicken.rarity = meatRarity;
      chicken.loadValue(c, CATEGORY_MEAT_ORE);
      
      rabbit.enabled = true;
      rabbit.clusterCount = meatClusterCount;
      rabbit.clusterSize = meatClusterSize;
      rabbit.minY = meatMinY;
      rabbit.maxY = meatMaxY;
      rabbit.rarity = meatRarity;
      rabbit.loadValue(c, CATEGORY_MEAT_ORE);
      
      sheep.enabled = true;
      sheep.clusterCount = meatClusterCount;
      sheep.clusterSize = meatClusterSize;
      sheep.minY = meatMinY;
      sheep.maxY = meatMaxY;
      sheep.rarity = meatRarity;
      sheep.loadValue(c, CATEGORY_MEAT_ORE);
      
      /*
       * Mob Ores
       */
      
      int mobClusterCount = 1;
      int mobClusterSize = 28;
      int mobMinY = 32;
      int mobMaxY = 84;
      int mobRarity = 30;
      
      zombie.enabled = true;
      zombie.clusterCount = mobClusterCount;
      zombie.clusterSize = mobClusterSize;
      zombie.minY = mobMinY;
      zombie.maxY = mobMaxY;
      zombie.rarity = mobRarity;
      zombie.loadValue(c, CATEGORY_MOB_ORE);
      
      skeleton.enabled = true;
      skeleton.clusterCount = mobClusterCount;
      skeleton.clusterSize = mobClusterSize;
      skeleton.minY = mobMinY;
      skeleton.maxY = mobMaxY;
      skeleton.rarity = mobRarity;
      skeleton.loadValue(c, CATEGORY_MOB_ORE);
      
      creeper.enabled = true;
      creeper.clusterCount = mobClusterCount;
      creeper.clusterSize = mobClusterSize;
      creeper.minY = mobMinY;
      creeper.maxY = mobMaxY;
      creeper.rarity = mobRarity;
      creeper.loadValue(c, CATEGORY_MOB_ORE);
      
      spider.enabled = true;
      spider.clusterCount = mobClusterCount;
      spider.clusterSize = mobClusterSize;
      spider.minY = mobMinY;
      spider.maxY = mobMaxY;
      spider.rarity = mobRarity;
      spider.loadValue(c, CATEGORY_MOB_ORE);
      
      enderman.enabled = true;
      enderman.clusterCount = mobClusterCount;
      enderman.clusterSize = mobClusterSize;
      enderman.minY = mobMinY;
      enderman.maxY = mobMaxY;
      enderman.rarity = mobRarity;
      enderman.loadValue(c, CATEGORY_MOB_ORE);
      
      slime.enabled = true;
      slime.clusterCount = mobClusterCount;
      slime.clusterSize = mobClusterSize;
      slime.minY = mobMinY;
      slime.maxY = mobMaxY;
      slime.rarity = mobRarity;
      slime.loadValue(c, CATEGORY_MOB_ORE);
      
      witch.enabled = true;
      witch.clusterCount = mobClusterCount;
      witch.clusterSize = mobClusterSize;
      witch.minY = mobMinY;
      witch.maxY = mobMaxY;
      witch.rarity = mobRarity;
      witch.loadValue(c, CATEGORY_MOB_ORE);
      
      pigman.enabled = true;
      pigman.clusterCount = mobClusterCount;
      pigman.clusterSize = mobClusterSize;
      pigman.minY = mobMinY;
      pigman.maxY = mobMaxY;
      pigman.rarity = mobRarity;
      pigman.loadValue(c, CATEGORY_MOB_ORE);
      
      ghast.enabled = true;
      ghast.clusterCount = mobClusterCount;
      ghast.clusterSize = mobClusterSize;
      ghast.minY = mobMinY;
      ghast.maxY = mobMaxY;
      ghast.rarity = mobRarity;
      ghast.loadValue(c, CATEGORY_MOB_ORE);
      
      magmaCube.enabled = true;
      magmaCube.clusterCount = mobClusterCount;
      magmaCube.clusterSize = mobClusterSize;
      magmaCube.minY = mobMinY;
      magmaCube.maxY = mobMaxY;
      magmaCube.rarity = mobRarity;
      magmaCube.loadValue(c, CATEGORY_MOB_ORE);
      
      wither.enabled = true;
      wither.clusterCount = mobClusterCount;
      wither.clusterSize = mobClusterSize;
      wither.minY = mobMinY;
      wither.maxY = mobMaxY;
      wither.rarity = mobRarity;
      wither.loadValue(c, CATEGORY_MOB_ORE);
      
    } catch (Exception e) {
      LogHelper.severe("Oh noes!!! Couldn't load configuration file properly!");
    } finally {
      c.save();
    }
  }
  
  public static void save() {
    
    c.save();
  }
}
