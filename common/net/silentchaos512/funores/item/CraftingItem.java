package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.util.LocalizationHelper;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import scala.actors.threadpool.Arrays;

public class CraftingItem extends ItemSG {

  public static final int BASE_METALS_COUNT = 18;

  public final String craftingItemName;
  public final boolean isAlloy;

  public CraftingItem(String name, boolean isAlloy) {

    super(1); // I don't think the value matters?
    this.craftingItemName = name;
    this.isAlloy = isAlloy;

    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.CRAFTING_ITEM);
  }

  public List<IMetal> getMetals() {

    if (isAlloy) {
      // Alloys
      return Arrays.asList(EnumAlloy.values());
    } else {
      // Basic metals (including vanilla)
      List<IMetal> metals = new ArrayList<IMetal>(Arrays.asList(EnumMetal.values()));
      metals.addAll(Arrays.asList(EnumVanillaMetal.values()));
      return metals;
    }
  }

  @Override
  public void addRecipes() {

    if (craftingItemName.equals(Names.GEAR)) {
      String iron = "ingotIron";

      for (IMetal metal : getMetals()) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this, 1, metal.getMeta()), " m ",
            "mim", " m ", 'm', "ingot" + metal.getName(), 'i', iron));
      }
    } else if (craftingItemName.equals(Names.PLATE)) {
      // TODO
    }
  }

  @Override
  public void addOreDict() {

    ItemStack stack;

    if (craftingItemName.equals(Names.GEAR)) {
      for (IMetal metal : getMetals()) {
        stack = new ItemStack(this, 1, metal.getMeta());
        OreDictionary.registerOre("gear" + metal.getName(), stack);
        if (metal == EnumMetal.ALUMINIUM) {
          OreDictionary.registerOre("gearAluminum", stack);
        }
      }
    } else if (craftingItemName.equals(Names.PLATE)) {
      for (IMetal metal : getMetals()) {
        stack = new ItemStack(this, 1, metal.getMeta());
        OreDictionary.registerOre("plate" + metal.getName(), stack);
        if (metal == EnumMetal.ALUMINIUM) {
          OreDictionary.registerOre("plateAluminum", stack);
        }
      }
    } else {
      LogHelper.warning("CraftingItem.addOreDict - Unknown item type: " + craftingItemName);
    }
  }

  @Override
  public String[] getVariantNames() {

    String prefix = FunOres.MOD_ID + ":" + craftingItemName;
    String[] result = new String[isAlloy ? EnumAlloy.count() : BASE_METALS_COUNT];

    for (IMetal metal : getMetals()) {
      result[metal.getMeta()] = prefix + metal.getName();
    }

    return result;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (IMetal metal : getMetals()) {
      list.add(new ItemStack(item, 1, metal.getMeta()));
    }
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    String metalName = null;
    int meta = stack.getItemDamage();
    List<IMetal> metals = getMetals();
    if (meta >= 0 && meta < metals.size() && metals.get(meta) != null) {
      metalName = metals.get(meta).getName();
    } else {
      for (IMetal metal : Lists.reverse(metals)) {
        if (metal.getMeta() == meta) {
          metalName = metal.getName();
        }
      }
    }
    return LocalizationHelper.ITEM_PREFIX + craftingItemName + metalName;
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    // TODO
  }
}
