package net.silentchaos512.funores.lib;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Marks a block/item that the user can disable in the config file.
 */
public interface IDisableable {

  /**
   * Get a list of all possible items, including disabled ones! The proper getSubBlocks and getSubItems methods
   * inherited from Block/Item should reference this, but filter out disabled items.
   */
  public List<ItemStack> getSubItems(Item item);
}
