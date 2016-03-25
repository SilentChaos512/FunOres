package net.silentchaos512.funores.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemSL;

public class ItemHammer extends ItemSL {

  public ItemHammer() {

    super(1, FunOres.MOD_ID, Names.HAMMER);
  }

  @Override
  public void addRecipes() {

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
    CraftingItem item = ModItems.plateBasic;
    for (IMetal metal : item.getMetals()) {
      plate = new ItemStack(item, 1, metal.getMeta());
      ingot = "ingot" + metal.getMetalName();
      GameRegistry.addRecipe(new ShapedOreRecipe(plate, "h", "i", "i", 'h', this, 'i', ingot));
    }

    // Alloy plates
    item = ModItems.plateAlloy;
    for (IMetal metal : item.getMetals()) {
      plate = new ItemStack(item, 1, metal.getMeta());
      ingot = "ingot" + metal.getMetalName();
      GameRegistry.addRecipe(new ShapedOreRecipe(plate, "h", "i", "i", 'h', this, 'i', ingot));
    }
  }
}
