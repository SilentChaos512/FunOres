package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.item.ModItems;

public enum EnumAlloy implements IStringSerializable {

  BRONZE(0, "Bronze"),
  BRASS(1, "Brass"),
  STEEL(2, "Steel");

  private final int meta;
  private final String name;

  private EnumAlloy(int meta, String name) {

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

  public static EnumAlloy byMetadata(int meta) {

    if (meta < 0 || meta >= values().length) {
      meta = 0;
    }
    return values()[meta];
  }

  public static int count() {

    return values().length;
  }
  
  public ItemStack getBlock() {
    
    return new ItemStack(ModBlocks.alloyBlock, 1, meta);
  }
  
  public ItemStack getIngot() {
    
    return new ItemStack(ModItems.alloyIngot, 1, meta);
  }
  
  public ItemStack getNugget() {
    
    return new ItemStack(ModItems.alloyNugget, 1, meta);
  }
}
