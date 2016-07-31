package net.silentchaos512.funores.lib;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IDisableable {

  public List<ItemStack> getSubItems(Item item);
}
