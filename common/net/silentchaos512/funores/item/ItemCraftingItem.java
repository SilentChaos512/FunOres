package net.silentchaos512.funores.item;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaExtended;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.RecipeMaker;

public class ItemCraftingItem extends ItemBaseMetal {

  public static final int BASE_METALS_COUNT = 18;

  public final String craftingItemName;
  public final boolean isAlloy;
  public final boolean isGear;
  public final boolean isPlate;

  public ItemCraftingItem(String name, boolean isAlloy) {

    super(Names.CRAFTING_ITEM, name, name.toLowerCase());
    this.craftingItemName = name;
    this.isAlloy = isAlloy;
    this.isGear = craftingItemName.equals(Names.GEAR);
    this.isPlate = craftingItemName.equals(Names.PLATE);
  }

  @Override
  public List<IMetal> getMetals(Item item) {

    List<IMetal> metals = Lists.newArrayList();

    if (isAlloy) {
      // Alloys
      metals.addAll(Arrays.asList(EnumAlloy.values()));
    } else {
      // Basic metals (including vanilla)
      metals.addAll(Arrays.asList(EnumMetal.values()));
      metals.addAll(Arrays.asList(EnumVanillaMetal.values()));
      // Extended vanilla
      for (EnumVanillaExtended extended : EnumVanillaExtended.values())
        if ((isGear && extended.allowGear) || (isPlate && extended.allowPlate))
          metals.add(extended);
      return metals;
    }

    return metals;
  }

  @Override
  public void addRecipes(RecipeMaker recipes) {

    if (isGear) {
      for (IMetal metal : getMetals(this)) {
        ItemStack gear = metal.getGear();
        if (gear != null && !FunOres.registry.isItemDisabled(gear)) {
          String mat = metal instanceof EnumVanillaExtended
              ? ((EnumVanillaExtended) metal).getMaterialOreDictKey()
              : "ingot" + metal.getMetalName();
          recipes.addShapedOre("gear_" + metal.getMetalName(), gear, " m ", "mim", " m ", 'm', mat,
              'i', metal == EnumVanillaExtended.WOOD || metal == EnumVanillaExtended.STONE
                  ? "stickWood" : "ingotIron");
        }
      }
    }
  }

  @Override
  public void addOreDict() {

    super.addOreDict();

    if (!isAlloy)
      OreDictionary.registerOre(oreDictPrefix + "Aluminum",
          new ItemStack(this, 1, EnumMetal.ALUMINIUM.meta));
  }

  @Override
  public String getNameForStack(ItemStack stack) {

    String metalName = null;
    int meta = stack.getItemDamage();
    List<IMetal> metals = getMetals(this);
    if (meta >= 0 && meta < metals.size() && metals.get(meta) != null) {
      metalName = metals.get(meta).getMetalName();
    } else {
      for (IMetal metal : Lists.reverse(metals)) {
        if (metal.getMeta() == meta) {
          metalName = metal.getMetalName();
        }
      }
    }
    return craftingItemName + metalName;
  }

  @Override
  public void clAddInformation(ItemStack stack, World world, List list, boolean advanced) {

    // TODO
  }
}
