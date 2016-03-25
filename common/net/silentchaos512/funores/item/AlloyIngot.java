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
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.RecipeHelper;

public class AlloyIngot extends ItemSL {

  public AlloyIngot() {

    super(EnumAlloy.count(), FunOres.MOD_ID, Names.ALLOY_INGOT);
  }

  @Override
  public void addRecipes() {

    for (int i = 0; i < EnumAlloy.count(); ++i) {
      EnumAlloy alloy = EnumAlloy.byMetadata(i);
      // Ingots <--> Blocks
      RecipeHelper.addCompressionRecipe(alloy.getIngot(), alloy.getBlock(), 9);
      // Nuggets <--> Ingots
      RecipeHelper.addCompressionRecipe(alloy.getNugget(), alloy.getIngot(), 9);
    }
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy alloy : EnumAlloy.values()) {
      String name = "ingot" + alloy.getMetalName();
      int meta = alloy.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumAlloy metal : EnumAlloy.values()) {
      models.add(new ModelResourceLocation(FunOres.MOD_ID + ":Ingot" + metal.getMetalName(), "inventory"));
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
