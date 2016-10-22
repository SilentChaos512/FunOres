package net.silentchaos512.funores.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockFunOre;
import net.silentchaos512.funores.compat.jei.FunOresPlugin;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.lib.registry.SRegistry;

/**
 * Modified SRegistry that automatically loads configs for disableable (IDisableable) items.
 * 
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

  private List<ItemStack> getSubItems(IDisableable disableable, Item item) {

    return disableable.getSubItems(item);
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

    if (stack == null)
      return "null";
    return stack.getUnlocalizedName() + ":" + stack.getItemDamage();
  }

  @Override
  public Block registerBlock(Block block, String key, ItemBlock itemBlock) {

    if (block instanceof IDisableable) {
      // General disableable blocks.
      List<ItemStack> list = getSubItems((IDisableable) block, itemBlock);
      for (ItemStack stack : list) {
        if (!Config.isItemDisabled(stack)) {
          block.setCreativeTab(FunOres.tabFunOres);
        } else {
          disabledItems.add(getStackKey(stack));
          FunOresPlugin.disabledItems.add(stack);
        }
      }
    } else if (block instanceof BlockFunOre) {
      // Ores are not IDisableable, so we check the ore configs.
      BlockFunOre ore = (BlockFunOre) block;
      for (int i = 0; i < ore.maxMeta; ++i) {
        if (!ore.isEnabled(i)) {
          ItemStack stack = new ItemStack(itemBlock, 1, i);
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
      List<ItemStack> list = getSubItems((IDisableable) item, item);
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
