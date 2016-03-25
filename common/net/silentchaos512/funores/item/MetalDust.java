package net.silentchaos512.funores.item;

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
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;

public class MetalDust extends ItemSL {

  public MetalDust() {

    super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_DUST);
  }

  @Override
  public void addRecipes() {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack dust = metal.getDust();
      ItemStack ingot = metal.getIngot();
      GameRegistry.addSmelting(dust, ingot, 0.6f);
    }
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      String name = "dust" + metal.getMetalName();
      int meta = metal.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }
  
  @Override
  public List<ModelResourceLocation> getVariants() {
    
    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMetal metal : EnumMetal.values()) {
      models.add(new ModelResourceLocation(FunOres.MOD_ID + ":Dust" + metal.getMetalName(), "inventory"));
    }
    return models;
  }
  
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }
}
