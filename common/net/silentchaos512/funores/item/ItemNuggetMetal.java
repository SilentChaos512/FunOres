package net.silentchaos512.funores.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;

public class ItemNuggetMetal extends ItemSL implements IDisableable {

  public static final int META_IRON = 16;

  public ItemNuggetMetal() {

    super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_NUGGET);
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack nugget = metal.getNugget();
      if (!FunOres.registry.isItemDisabled(nugget)) {
        String name = "nugget" + metal.getMetalName();
        OreDictionary.registerOre(name, nugget);
      }
    }

    if (!FunOres.registry.isItemDisabled(EnumVanillaMetal.IRON.getNugget()))
      OreDictionary.registerOre("nuggetIron", new ItemStack(this, 1, META_IRON));
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    String prefix = FunOres.MOD_ID + ":Nugget";
    List<ModelResourceLocation> models = Lists.newArrayList();
    int i = 0;
    for (; i < EnumMetal.count(); ++i) {
      models.add(new ModelResourceLocation(prefix + EnumMetal.values()[i].getMetalName(), "inventory"));
    }
    for (; i < 16; ++i) {
      models.add(null);
    }
    models.add(new ModelResourceLocation(prefix + "Iron", "inventory"));
    return models;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    list.addAll(getSubItems(item));
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    List<ItemStack> ret = Lists.newArrayList();
    for (IMetal metal : EnumMetal.values())
      ret.add(new ItemStack(item, 1, metal.getMeta()));
    return ret;
  }
}
