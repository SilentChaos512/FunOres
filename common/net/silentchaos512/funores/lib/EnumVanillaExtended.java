package net.silentchaos512.funores.lib;

import org.apache.commons.lang3.NotImplementedException;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.init.ModItems;

public enum EnumVanillaExtended implements IMetal {

  WOOD(18, true, false, "Wood"),
  STONE(19, true, false, "Stone"),
  OBSIDIAN(20, true, true, "Obsidian"),
  DIAMOND(21, true, true, "Diamond"),
  EMERALD(22, true, true, "Emerald");

  public final int meta;
  public final String name;
  public final boolean allowGear;
  public final boolean allowPlate;

  private EnumVanillaExtended(int meta, boolean allowGear, boolean allowPlate, String name) {

    this.meta = meta;
    this.name = name;
    this.allowGear = allowGear;
    this.allowPlate = allowPlate;
  }

  public String getMaterialOreDictKey() {

    //@formatter:off
    switch (this) {
      case WOOD:      return "plankWood";
      case STONE:     return "cobblestone";
      case OBSIDIAN:  return "obsidian";
      case DIAMOND:   return "gemDiamond";
      case EMERALD:   return "gemEmerald";
      default: throw new NotImplementedException("EnumVanillaExtended: Don't know oredict key for " + name);
    }
    //@formatter:on
  }

  @Override
  public int getMeta() {

    return meta;
  }

  @Override
  public String getMetalName() {

    return name;
  }

  @Override
  public boolean isAlloy() {

    return false;
  }

  @Override
  public ItemStack getBlock() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemStack getIngot() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemStack getNugget() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemStack getDust() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemStack getPlate() {

    if (!allowPlate)
      return null;
    return new ItemStack(ModItems.plateBasic, 1, meta);
  }

  @Override
  public ItemStack getGear() {

    if (!allowGear)
      return null;
    return new ItemStack(ModItems.gearBasic, 1, meta);
  }
}
