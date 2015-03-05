package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.block.MeatOre;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;

public enum EnumMeat implements IStringSerializable, IHasOre {
  
  PIG(0, "Pig"),
  FISH(1, "Fish"),
  COW(2, "Cow"),
  CHICKEN(3, "Chicken"),
  RABBIT(4, "Rabbit"),
  SHEEP(5, "Sheep");
  
  private final int meta;
  private final String name;
  
  private EnumMeat(int meta, String name) {
    
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

    return ModBlocks.meatOre.getDefaultState().withProperty(MeatOre.MEAT, this);
  }
  
  public ConfigOptionOreGenBonus getConfig() {
    
    switch (meta) {
      case 0: return Config.pig;
      case 1: return Config.fish;
      case 2: return Config.cow;
      case 3: return Config.chicken;
      case 4: return Config.rabbit;
      case 5: return Config.sheep;
      default: return null;
    }
  }

  public static EnumMeat byMetadata(int meta) {
    
    if (meta < 0 || meta >= values().length) {
      meta = 0;
    }
    return values()[meta];
  }
  
  public static int count() {
    
    return values().length;
  }
}
