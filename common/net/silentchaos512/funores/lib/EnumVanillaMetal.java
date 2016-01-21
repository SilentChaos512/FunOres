package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.item.ModItems;

public enum EnumVanillaMetal implements IMetal {

  IRON(16, "Iron"),
  GOLD(17, "Gold");

  public final int meta;
  public final String name;

  private EnumVanillaMetal(int meta, String name) {

    this.meta = meta;
    this.name = name;
  }

  @Override
  public int getMeta() {

    return meta;
  }

  @Override
  public String getName() {

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

    return new ItemStack(ModItems.metalNugget, 1, meta);
  }

  @Override
  public ItemStack getDust() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ItemStack getPlate() {

    return new ItemStack(ModItems.plateBasic, 1, meta);
  }

  @Override
  public ItemStack getGear() {

    return new ItemStack(ModItems.gearBasic, 1, meta);
  }
}
