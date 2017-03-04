package net.silentchaos512.funores.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemNamedSubtypes;

public class ItemShard extends ItemNamedSubtypes implements IDisableable {

  public static final String[] NAMES = { "ShardEnder", "ShardBlaze", "ShardGhast" };

  public ItemShard() {

    super(NAMES, FunOres.MOD_ID, Names.SHARD);
  }

  @Override
  public void addRecipes() {

    ItemStack enderShard = new ItemStack(this, 1, 0);
    if (!FunOres.registry.isItemDisabled(enderShard)) {
      ItemStack enderPearl = new ItemStack(Items.ENDER_PEARL);
      GameRegistry.addShapedRecipe(enderPearl, "ss", "ss", 's', enderShard);
    }

    ItemStack blazeShard = new ItemStack(this, 1, 1);
    if (!FunOres.registry.isItemDisabled(blazeShard)) {
      ItemStack blazeRod = new ItemStack(Items.BLAZE_ROD);
      GameRegistry.addShapedRecipe(blazeRod, "ss", "ss", 's', blazeShard);
    }

    ItemStack ghastShard = new ItemStack(this, 1, 2);
    if (!FunOres.registry.isItemDisabled(ghastShard)) {
      ItemStack ghastTear = new ItemStack(Items.GHAST_TEAR);
      GameRegistry.addShapedRecipe(ghastTear, "ss", "ss", 's', ghastShard);
    }
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    List<ItemStack> ret = Lists.newArrayList();
    for (int i = 0; i < subItemCount; ++i)
      ret.add(new ItemStack(item, 1, i));
    return ret;
  }

  @Override
  protected void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {

    for (ItemStack stack : getSubItems(item))
      if (!FunOres.registry.isItemDisabled(stack))
        list.add(stack);
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (int i = 0; i < NAMES.length; ++i) {
      if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, i)))
        models.add(new ModelResourceLocation((modId + ":" + NAMES[i]).toLowerCase(), "inventory"));
      else
        models.add(null);
    }
    return models;
  }
}
