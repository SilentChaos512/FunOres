package net.silentchaos512.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;

public class MetalNugget extends ItemSG {

  public static final int META_IRON = 16;

  public MetalNugget() {

    super(EnumMetal.count());

    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.METAL_NUGGET);
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      String name = "nugget" + metal.getName();
      int meta = metal.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }

    OreDictionary.registerOre("nuggetIron", new ItemStack(this, 1, META_IRON));
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[17];

    int i = 0;
    for (; i < EnumMetal.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Nugget" + EnumMetal.values()[i].getName();
    }
    for (; i < 16; ++i)
      ;
    result[i] = FunOres.MOD_ID + ":NuggetIron";

    return result;
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < EnumMetal.count(); ++i) {
      list.add(new ItemStack(this, 1, i));
    }
    list.add(new ItemStack(this, 1, META_IRON));
  }
}
