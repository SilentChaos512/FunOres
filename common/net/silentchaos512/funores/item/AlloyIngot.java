package net.silentchaos512.funores.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.RecipeHelper;

public class AlloyIngot extends ItemSL implements IDisableable {

  public AlloyIngot() {

    super(EnumAlloy.count(), FunOres.MOD_ID, Names.ALLOY_INGOT);
  }

  @Override
  public void addRecipes() {

    for (EnumAlloy metal : EnumAlloy.values()) {
      boolean disabledNugget = FunOres.registry.isItemDisabled(metal.getNugget());
      boolean disabledIngot = FunOres.registry.isItemDisabled(metal.getIngot());
      boolean disabledBlock = FunOres.registry.isItemDisabled(metal.getBlock());

      // Ingots <--> Blocks
      if (!disabledIngot && !disabledBlock)
        RecipeHelper.addCompressionRecipe(metal.getIngot(), metal.getBlock(), 9);
      // Nuggets <--> Ingots
      if (!disabledNugget && !disabledIngot)
        RecipeHelper.addCompressionRecipe(metal.getNugget(), metal.getIngot(), 9);
    }
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy metal : EnumAlloy.values()) {
      ItemStack ingot = metal.getIngot();
      if (!FunOres.registry.isItemDisabled(ingot)) {
        String name = "ingot" + metal.getMetalName();
        OreDictionary.registerOre(name, ingot);
      }
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumAlloy metal : EnumAlloy.values()) {
      models.add(
          new ModelResourceLocation(FunOres.MOD_ID + ":Ingot" + metal.getMetalName(), "inventory"));
    }
    return models;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    list.addAll(getSubItems(item));
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    List<ItemStack> ret = Lists.newArrayList();
    for (IMetal metal : EnumAlloy.values())
      ret.add(new ItemStack(item, 1, metal.getMeta()));
    return ret;
  }
}
