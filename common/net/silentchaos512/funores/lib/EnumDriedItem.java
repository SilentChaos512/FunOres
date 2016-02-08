package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.item.ModItems;

public enum EnumDriedItem {

  DRIED_FLESH(0, "DriedFlesh", 4, 0.2f, "JerkyFlesh"),
  BEEF_JERKY(1, "BeefJerky", 8, 0.8f, "JerkyBeef"),
  CHICKEN_JERKY(2, "ChickenJerky", 6, 0.6f, "JerkyChicken"),
  PORK_JERKY(3, "PorkJerky", 8, 0.8f, "JerkyPork"),
  MUTTON_JERKY(4, "MuttonJerky", 6, 0.8f, "JerkyMutton"),
  RABBIT_JERKY(5, "RabbitJerky", 5, 0.6f, "JerkyRabbit"),
  COD_JERKY(6, "CodJerky", 5, 0.6f, "JerkyCod"),
  SALMON_JERKY(7, "SalmonJerky", 6, 0.8f, "JerkySalmon");

  public final int meta;
  public final String name;
  public final int foodValue;
  public final float saturationValue;
  public final int useDuration;
  public final String textureName;

  private EnumDriedItem(int meta, String name, int food, float saturation, String textureName) {

    this.meta = meta;
    this.name = name;
    this.foodValue = food;
    this.saturationValue = saturation;
    this.useDuration = 32;
    this.textureName = textureName;
  }

  public ItemStack getItem() {

    return new ItemStack(ModItems.driedItem, 1, meta);
  }
}
