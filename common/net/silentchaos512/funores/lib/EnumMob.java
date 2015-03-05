package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.block.MobOre;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;


public enum EnumMob implements IStringSerializable, IHasOre {
  
  ZOMBIE(0, "Zombie"),
  SKELETON(1, "Skeleton"),
  CREEPER(2, "Creeper"),
  SPIDER(3, "Spider"),
  ENDERMAN(4, "Enderman"),
  SLIME(5, "Slime"),
  WITCH(6, "Witch"),
  PIGMAN(7, "Pigman"),
  GHAST(8, "Ghast"),
  MAGMA_CUBE(9, "MagmaCube"),
  WITHER(10, "Wither");
  
  private final int meta;
  private final String name;
  
  private EnumMob(int meta, String name) {
    
    this.meta = meta;
    this.name = name;
  }
  
  public int getMeta() {
    
    return meta;
  }

  @Override
  public String getName() {

    return name;
  }
  
  @Override
  public IBlockState getOre() {

    return ModBlocks.mobOre.getDefaultState().withProperty(MobOre.MOB, this);
  }
  
  public ConfigOptionOreGenBonus getConfig() {
    
    switch (meta) {
      case 0: return Config.zombie;
      case 1: return Config.skeleton;
      case 2: return Config.creeper;
      case 3: return Config.spider;
      case 4: return Config.enderman;
      case 5: return Config.slime;
      case 6: return Config.witch;
      case 7: return Config.pigman;
      case 8: return Config.ghast;
      case 9: return Config.magmaCube;
      case 10: return Config.wither;
      default: return null;
    }
  }

  public static EnumMob byMetadata(int meta) {
    
    if (meta <= 0 || meta >= values().length) {
      meta = 0;
    }
    return values()[meta];
  }
  
  public static int count() {
    
    return values().length;
  }
}
