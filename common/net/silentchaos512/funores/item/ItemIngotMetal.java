package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.util.RecipeHelper;

public class ItemIngotMetal extends ItemBaseMetal {

  public ItemIngotMetal() {

    super(Names.METAL_INGOT, "Ingot", "ingot");
  }

  @Override
  public List<IMetal> getMetals(Item item) {

    List<IMetal> list = new ArrayList(Arrays.asList(EnumMetal.values())); // No vanilla metals for ingots!
    return list; // Build fails if not assigned to a variable?
  }

  @Override
  public void addRecipes() {

    for (EnumMetal metal : EnumMetal.values()) {
      boolean disabledNugget = FunOres.registry.isItemDisabled(metal.getNugget());
      boolean disabledIngot = FunOres.registry.isItemDisabled(metal.getIngot());
      boolean disabledBlock = FunOres.registry.isItemDisabled(metal.getBlock());

      // Ingots <--> Blocks
      if (!disabledIngot && !disabledBlock)
        RecipeHelper.addCompressionRecipe(metal.getIngot(), metal.getBlock(), 9);
      // Nuggets <--> Ingots
      if (!disabledNugget && !disabledIngot)
        RecipeHelper.addCompressionRecipe(metal.getNugget(), metal.getIngot(), 9);
    }

    // Iron
    if (!FunOres.registry.isItemDisabled(EnumVanillaMetal.IRON.getNugget())) {
      ItemStack nugget = EnumVanillaMetal.IRON.getNugget();
      ItemStack ingot = EnumVanillaMetal.IRON.getIngot();
      RecipeHelper.addCompressionRecipe(nugget, ingot, 9);
    }
  }

  @Override
  public void addOreDict() {

    super.addOreDict();

    // Alternative spelling of aluminium
    if (!FunOres.registry.isItemDisabled(EnumMetal.ALUMINIUM.getIngot()))
      OreDictionary.registerOre("ingotAluminum", EnumMetal.ALUMINIUM.getIngot());
  }
}
