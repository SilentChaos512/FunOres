package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;

public interface IMetal {

  public int getMeta();
  public String getName();
  public boolean isAlloy();

  public ItemStack getBlock();
  public ItemStack getIngot();
  public ItemStack getNugget();
  public ItemStack getDust();
  public ItemStack getPlate();
  public ItemStack getGear();

  public static IMetal getByMeta(int meta) {

    return null;
  }
}
