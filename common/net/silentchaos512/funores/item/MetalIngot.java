package net.silentchaos512.funores.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;
import net.silentchaos512.lib.util.RecipeHelper;

public class MetalIngot extends ItemSL {

  public MetalIngot() {

    super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_INGOT);
  }

  @Override
  public void addRecipes() {

    for (int i = 0; i < EnumMetal.count(); ++i) {
      ItemStack nugget = new ItemStack(ModItems.metalNugget, 1, i);
      ItemStack ingot = new ItemStack(this, 1, i);
      ItemStack block = new ItemStack(ModBlocks.metalBlock, 1, i);
      // Ingots <--> Blocks
      RecipeHelper.addCompressionRecipe(ingot, block, 9);
      // Nuggets <--> Ingots
      RecipeHelper.addCompressionRecipe(nugget, ingot, 9);
    }
    
    // Iron
    ItemStack nugget = new ItemStack(ModItems.metalNugget, 1, MetalNugget.META_IRON);
    ItemStack ingot = new ItemStack(Items.iron_ingot);
    RecipeHelper.addCompressionRecipe(nugget, ingot, 9);
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      String name = "ingot" + metal.getMetalName();
      int meta = metal.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }
  
  @Override
  public List<ModelResourceLocation> getVariants() {
    
    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMetal metal : EnumMetal.values()) {
      models.add(new ModelResourceLocation(FunOres.MOD_ID + ":Ingot" + metal.getMetalName(), "inventory"));
    }
    return models;
  }
  
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }
  
  @Override
  public boolean isBeaconPayment(ItemStack stack) {
    
    return true;
  }
}
