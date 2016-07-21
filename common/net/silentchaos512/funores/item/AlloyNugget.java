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
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;

public class AlloyNugget extends ItemSL implements IDisableable {

  public AlloyNugget() {

    super(EnumAlloy.count(), FunOres.MOD_ID, Names.ALLOY_NUGGET);
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy metal : EnumAlloy.values()) {
      ItemStack nugget = metal.getNugget();
      if (!FunOres.registry.isItemDisabled(nugget)) {
        String name = "nugget" + metal.getMetalName();
        OreDictionary.registerOre(name, nugget);
      }
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumAlloy metal : EnumAlloy.values()) {
      models.add(new ModelResourceLocation(FunOres.MOD_ID + ":Nugget" + metal.getMetalName(),
          "inventory"));
    }
    return models;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (EnumAlloy metal : EnumAlloy.values()) {
      list.add(new ItemStack(item, 1, metal.getMeta()));
    }
  }
}
