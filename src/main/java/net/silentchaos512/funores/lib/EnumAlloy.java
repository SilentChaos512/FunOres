package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;

public enum EnumAlloy implements IStringSerializable, IMetal {

  BRONZE(0, "Bronze"),
  BRASS(1, "Brass"),
  STEEL(2, "Steel"),
  INVAR(3, "Invar"),
  ELECTRUM(4, "Electrum"),
  ENDERIUM(5, "Enderium"),
  PRISMARIINIUM(6, "Prismarinium");

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
  public String getMetalName() {

    return name;
  }

  @Override
  public String getName() {

    return name.toLowerCase();
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

  public ItemStack getDust() {

    return new ItemStack(ModItems.alloyDust, 1, meta);
  }

  @Override
  public boolean isAlloy() {

    return true;
  }

  @Override
  public ItemStack getPlate() {

    return new ItemStack(ModItems.plateAlloy, 1, meta);
  }

  @Override
  public ItemStack getGear() {

    return new ItemStack(ModItems.gearAlloy, 1, meta);
  }
}
