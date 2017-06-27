package net.silentchaos512.funores.item;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.lib.item.ItemSL;

public class ItemBaseDisableable extends ItemSL implements IDisableable {

  public ItemBaseDisableable(int subItemCount, String name) {

    super(subItemCount, FunOres.MOD_ID, name);
  }

  @Override
  public void getModels(Map<Integer, ModelResourceLocation> models) {

    String prefix = FunOres.MOD_ID + ":" + itemName;

    for (int i = 0; i < subItemCount; ++i) {
      if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, i)))
        models.put(i, new ModelResourceLocation((prefix + i).toLowerCase(), "inventory"));
    }
  }

  @Override
  protected void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {

    if (!isInCreativeTab(tab))
      return;

    for (ItemStack stack : getSubItems(item))
      if (!FunOres.registry.isItemDisabled(stack))
        list.add(stack);
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    List<ItemStack> list = Lists.newArrayList();
    for (int i = 0; i < subItemCount; ++i)
      list.add(new ItemStack(item, 1, i));
    return list;
  }
}
