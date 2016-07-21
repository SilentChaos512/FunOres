package net.silentchaos512.funores.lib;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.init.ModItems;

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
  public String getMetalName() {

    return name;
  }

  @Override
  public boolean isAlloy() {

    return false;
  }

  @Override
  public ItemStack getBlock() {

    Block block = this == IRON ? Blocks.IRON_BLOCK : Blocks.GOLD_BLOCK;
    return new ItemStack(block);
  }

  @Override
  public ItemStack getIngot() {

    Item item = this == IRON ? Items.IRON_INGOT : Items.GOLD_INGOT;
    return new ItemStack(item);
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
  public ItemStack getPlate() {

    return new ItemStack(ModItems.plateBasic, 1, meta);
  }

  @Override
  public ItemStack getGear() {

    return new ItemStack(ModItems.gearBasic, 1, meta);
  }
}
