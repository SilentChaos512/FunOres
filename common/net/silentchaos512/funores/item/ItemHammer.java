package net.silentchaos512.funores.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.init.ModItems;
import net.silentchaos512.funores.lib.EnumVanillaExtended;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;

public class ItemHammer extends ItemSL implements IDisableable {

  public ItemHammer() {

    super(1, FunOres.MOD_ID, Names.HAMMER);
  }

  @Override
  public void addRecipes() {

    if (FunOres.registry.isItemDisabled(new ItemStack(this)))
      return;

    // Crafting the hammer.
    String wood = "plankWood";

    for (String ingot : new String[] { "ingotAluminium", "ingotAluminum" }) {
      GameRegistry.addRecipe(new ShapedOreRecipe(this, "ii ", " pi", " p ", 'i', ingot, 'p', wood));
    }

    // Hammer doesn't get consumed.
    setContainerItem(this);

    ItemStack plate;
    String ingot;

    // Basic plates
    ItemCraftingItem item = ModItems.plateBasic;
    for (IMetal metal : item.getMetals(item)) {
      plate = metal.getPlate();
      ingot = metal instanceof EnumVanillaExtended
          ? ((EnumVanillaExtended) metal).getMaterialOreDictKey() : "ingot" + metal.getMetalName();
      if (plate != null && !FunOres.registry.isItemDisabled(plate))
        GameRegistry.addRecipe(new ShapedOreRecipe(plate, "h", "i", "i", 'h', this, 'i', ingot));
    }

    // Alloy plates
    item = ModItems.plateAlloy;
    for (IMetal metal : item.getMetals(item)) {
      plate = new ItemStack(item, 1, metal.getMeta());
      ingot = "ingot" + metal.getMetalName();
      if (!FunOres.registry.isItemDisabled(plate))
        GameRegistry.addRecipe(new ShapedOreRecipe(plate, "h", "i", "i", 'h', this, 'i', ingot));
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    if (!FunOres.registry.isItemDisabled(new ItemStack(this)))
      return super.getVariants();
    return Lists.newArrayList();
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    return Lists.newArrayList(new ItemStack(this));
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    ItemStack hammer = new ItemStack(this);
    if (!FunOres.registry.isItemDisabled(hammer))
      list.add(hammer);
  }
}
