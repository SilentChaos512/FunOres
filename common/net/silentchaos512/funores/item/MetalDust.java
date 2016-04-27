package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;
import scala.actors.threadpool.Arrays;

public class MetalDust extends ItemSL {

  public MetalDust() {

    super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_DUST);
  }

  public static List<IMetal> getMetals() {

    List<IMetal> list = new ArrayList<IMetal>(Arrays.asList(EnumMetal.values()));
    list.addAll(Arrays.asList(EnumVanillaMetal.values()));
    return list;
  }

  @Override
  public void addRecipes() {

    for (IMetal metal : getMetals()) {
      ItemStack dust = metal.getDust();
      ItemStack ingot = metal.getIngot();
      GameRegistry.addSmelting(dust, ingot, 0.6f);
    }
  }

  @Override
  public void addOreDict() {

    for (IMetal metal : getMetals()) {
      String name = "dust" + metal.getMetalName();
      int meta = metal.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    String prefix = FunOres.MOD_ID + ":Dust";

    int i;
    // Mod metals
    for (i = 0; i < EnumMetal.values().length; ++i) {
      String metalName = EnumMetal.values()[i].getMetalName();
      models.add(new ModelResourceLocation(prefix + metalName, "inventory"));
    }
    final int metaIron = EnumVanillaMetal.IRON.meta;
    // Fill empty space
    for (; i < metaIron; ++i) {
      models.add(null);
    }
    // Vanilla metals
    for (i = 0; i < EnumVanillaMetal.values().length; ++i) {
      String metalName = EnumVanillaMetal.values()[i].getMetalName();
      models.add(new ModelResourceLocation(prefix + metalName, "inventory"));
    }

    return models;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (IMetal metal : getMetals()) {
      list.add(new ItemStack(this, 1, metal.getMeta()));
    }
  }
}
