package net.silentchaos512.funores.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;

public class ItemHammer extends ItemSG {

  public ItemHammer() {

    super(1);
    setMaxStackSize(1);
    setUnlocalizedName(Names.HAMMER);
  }

  @Override
  public void addRecipes() {

    // Crafting the hammer.
    String[] ingots = { "ingotAluminium", "ingotAluminum" };
    String wood = "plankWood";

    for (String ingot : ingots) {
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
      ingot = "ingot" + metal.getName();
      GameRegistry.addRecipe(new ShapedOreRecipe(plate, "h", "i", "i", 'h', this, 'i', ingot));
    }

    // Alloy plates
    item = ModItems.plateAlloy;
    for (IMetal metal : item.getMetals()) {
      plate = new ItemStack(item, 1, metal.getMeta());
      ingot = "ingot" + metal.getName();
      GameRegistry.addRecipe(new ShapedOreRecipe(plate, "h", "i", "i", 'h', this, 'i', ingot));
    }
  }
}
