package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.util.RecipeHelper;

public class ItemIngotAlloy extends ItemBaseMetal {

  public ItemIngotAlloy() {

    super(Names.ALLOY_INGOT, "Ingot", "ingot");
  }

  @Override
  public List<IMetal> getMetals(Item item) {

    List<IMetal> list = new ArrayList<IMetal>(Arrays.asList(EnumAlloy.values()));
    return list; // Build fails if not assigned to a variable?
  }

  @Override
  public void addRecipes() {

    for (EnumAlloy metal : EnumAlloy.values()) {
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
  }
}
