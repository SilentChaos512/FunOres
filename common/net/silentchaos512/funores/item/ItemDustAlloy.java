package net.silentchaos512.funores.item;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.registry.FunOresRegistry;

public class ItemDustAlloy extends ItemBaseMetal {

  public ItemDustAlloy() {

    super(Names.ALLOY_DUST, "Dust", "dust");
  }
  
  @Override
  public List<IMetal> getMetals(Item item) {

    return Arrays.asList(EnumAlloy.values());
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
}
