package net.silentchaos512.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;

public class MetalDust extends ItemSG {

  public MetalDust() {

    super(EnumMetal.count());
    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.METAL_DUST);
  }

  @Override
  public void addRecipes() {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack dust = metal.getDust();
      ItemStack ingot = metal.getIngot();
      GameRegistry.addSmelting(dust, ingot, 0.0f);
    }
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      String name = "dust" + metal.getName();
      int meta = metal.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }
  
  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[EnumMetal.count()];
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Dust" + EnumMetal.values()[i].getName();
    }
    
    return result;
  }
  
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }
}
