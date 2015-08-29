package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.block.MetalOre;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.item.ModItems;

public enum EnumMetal implements IStringSerializable, IHasOre {
  
  COPPER(0, "Copper"),
  TIN(1, "Tin"),
  SILVER(2, "Silver"),
  LEAD(3, "Lead"),
  NICKEL(4, "Nickel"),
  PLATINUM(5, "Platinum"),
  ALUMINIUM(6, "Aluminium"),
  ZINC(7, "Zinc");
  
  private final int meta;
  private final String name;
  
  private EnumMetal(int meta, String name) {
    
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

  public static EnumMetal byMetadata(int meta) {
    
    if (meta < 0 || meta >= values().length) {
      meta = 0;
    }
    return values()[meta];
  }
  
  public static int count() {
    
    return values().length;
  }

  @Override
  public IBlockState getOre() {

    return ModBlocks.metalOre.getDefaultState().withProperty(MetalOre.METAL, this);
  }
  
  public ItemStack getBlock() {
    
    return new ItemStack(ModBlocks.metalBlock, 1, meta);
  }
  
  public ItemStack getIngot() {
    
    return new ItemStack(ModItems.metalIngot, 1, meta);
  }
  
  public ItemStack getNugget() {
    
    return new ItemStack(ModItems.metalNugget, 1, meta);
  }
  
  public ItemStack getDust() {
    
    return new ItemStack(ModItems.metalDust, 1, meta);
  }
}
