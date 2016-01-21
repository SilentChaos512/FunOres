package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.block.MetalOre;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.item.ModItems;

public enum EnumMetal implements IStringSerializable,IHasOre,IMetal {

  COPPER(0, "Copper"),
  TIN(1, "Tin"),
  SILVER(2, "Silver"),
  LEAD(3, "Lead"),
  NICKEL(4, "Nickel"),
  PLATINUM(5, "Platinum"),
  ALUMINIUM(6, "Aluminium"),
  ZINC(7, "Zinc"),
  TITANIUM(8, "Titanium");

  public final int meta;
  public final int dimension;
  public final String name;

  private EnumMetal(int meta, String name) {

    this(meta, name, 0);
  }

  private EnumMetal(int meta, String name, int dimension) {

    this.meta = meta;
    this.name = name;
    this.dimension = dimension;
  }

  @Override
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

  @Override
  public ItemStack getBlock() {

    return new ItemStack(ModBlocks.metalBlock, 1, meta);
  }

  @Override
  public ItemStack getIngot() {

    return new ItemStack(ModItems.metalIngot, 1, meta);
  }

  @Override
  public ItemStack getNugget() {

    return new ItemStack(ModItems.metalNugget, 1, meta);
  }

  @Override
  public ItemStack getDust() {

    return new ItemStack(ModItems.metalDust, 1, meta);
  }


  @Override
  public boolean isAlloy() {

    return false;
  }

  @Override
  public ItemStack getPlate() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemStack getGear() {

    // TODO Auto-generated method stub
    return null;
  }
  
  public ConfigOptionOreGen getConfig() {

    switch (meta) {
      case 0:
        return Config.copper;
      case 1:
        return Config.tin;
      case 2:
        return Config.silver;
      case 3:
        return Config.lead;
      case 4:
        return Config.nickel;
      case 5:
        return Config.platinum;
      case 6:
        return Config.aluminium;
      case 7:
        return Config.zinc;
      case 8:
        return Config.titanium;
      default:
        LogHelper.debug("EnumMetal: Don't know config for ore with meta " + meta);
        return null;
    }
  }
}
