package net.silentchaos512.funores.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.compat.jei.FunOresPlugin;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.lib.registry.SRegistry;

/**
 * Modified SRegistry that automatically loads configs for disableable (IDisableable) items.
 * @author Silent
 *
 */
public class FunOresRegistry extends SRegistry {

  public FunOresRegistry(String modId) {

    super(modId);
  }

  /**
   * Set of disabled items (contains values returned by getStackKey)
   */
  Set<String> disabledItems = new HashSet<>();

  private List<ItemStack> getSubItems(Item item) {

    List<ItemStack> list = Lists.newArrayList();
    item.getSubItems(item, FunOres.tabFunOres, list);
    return list;
  }

  /**
   * Check that the item is disabled in the config file.
   */
  public boolean isItemDisabled(ItemStack stack) {

    return disabledItems.contains(getStackKey(stack));
  }

  /**
   * Gets a String to use for the disabledItems set.
   */
  private String getStackKey(ItemStack stack) {

    return stack.getUnlocalizedName() + ":" + stack.getItemDamage();
  }

  @Override
  public Block registerBlock(Block block, String key, ItemBlock itemBlock) {

    if (block instanceof IDisableable) {
      List<ItemStack> list = getSubItems(itemBlock);
      for (ItemStack stack : list) {
        if (!Config.isItemDisabled(stack)) {
          block.setCreativeTab(FunOres.tabFunOres);
        } else {
          disabledItems.add(getStackKey(stack));
          FunOresPlugin.disabledItems.add(stack);
        }
      }
    }

    return super.registerBlock(block, key, itemBlock);
  }

  @Override
  public Item registerItem(Item item, String key) {

    if (item instanceof IDisableable) {
      List<ItemStack> list = getSubItems(item);
      for (ItemStack stack : list) {
        if (!Config.isItemDisabled(stack)) {
          item.setCreativeTab(FunOres.tabFunOres);
        } else {
          disabledItems.add(getStackKey(stack));
          FunOresPlugin.disabledItems.add(stack);
        }
      }
    }

    return super.registerItem(item, key);
  }
}
