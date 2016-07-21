package net.silentchaos512.funores.item;

import java.rmi.registry.Registry;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.registry.FunOresRegistry;
import net.silentchaos512.lib.item.ItemSL;

public class AlloyDust extends ItemSL implements IDisableable {

  public AlloyDust() {

    super(EnumAlloy.count(), FunOres.MOD_ID, Names.ALLOY_DUST);
  }

  @Override
  public void addRecipes() {

    // Crafting alloy dust from other dust
    String copper = "dustCopper";
    String tin = "dustTin";
    String zinc = "dustZinc";
    String iron = "dustIron";
    String coal = "dustCoal";
    String nickel = "dustNickel";
    String gold = "dustGold";
    String silver = "dustSilver";

    ItemStack bronze = EnumAlloy.BRONZE.getDust();
    ItemStack brass = EnumAlloy.BRASS.getDust();
    ItemStack steel = EnumAlloy.STEEL.getDust();
    ItemStack invar = EnumAlloy.INVAR.getDust();
    ItemStack electrum = EnumAlloy.ELECTRUM.getDust();

    FunOresRegistry reg = FunOres.registry;

    if (!reg.isItemDisabled(bronze))
      GameRegistry.addRecipe(new ShapelessOreRecipe(bronze, copper, copper, copper, tin));
    if (!reg.isItemDisabled(brass))
      GameRegistry.addRecipe(new ShapelessOreRecipe(brass, copper, copper, copper, zinc));
    if (!reg.isItemDisabled(steel))
      GameRegistry.addRecipe(new ShapelessOreRecipe(steel, iron, coal, coal, coal));
    if (!reg.isItemDisabled(invar))
      GameRegistry.addRecipe(new ShapelessOreRecipe(invar, iron, iron, nickel));
    if (!reg.isItemDisabled(electrum))
      GameRegistry.addRecipe(new ShapelessOreRecipe(electrum, gold, silver));

    // Smelting dust to ingots
    for (EnumAlloy metal : EnumAlloy.values()) {
      ItemStack dust = metal.getDust();
      ItemStack ingot = metal.getIngot();
      if (!reg.isItemDisabled(dust) && !reg.isItemDisabled(ingot))
        GameRegistry.addSmelting(dust, ingot, 0.6f);
    }
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy metal : EnumAlloy.values()) {
      ItemStack dust = metal.getDust();
      if (!FunOres.registry.isItemDisabled(dust)) {
        String name = "dust" + metal.getMetalName();
        OreDictionary.registerOre(name, dust);
      }
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumAlloy metal : EnumAlloy.values()) {
      models.add(
          new ModelResourceLocation(FunOres.MOD_ID + ":Dust" + metal.getMetalName(), "inventory"));
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
