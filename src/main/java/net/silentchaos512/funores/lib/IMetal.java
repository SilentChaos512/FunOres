package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;

public interface IMetal {

  public int getMeta();

  public String getMetalName();

  public default String[] getMetalNames() {

    return new String[] { getMetalName() };
  }

  public boolean isAlloy();

  public ItemStack getBlock();

  public ItemStack getIngot();

  public ItemStack getNugget();

  public ItemStack getDust();

  public ItemStack getPlate();

  public ItemStack getGear();
}
