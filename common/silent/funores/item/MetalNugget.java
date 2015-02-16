package silent.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import silent.funores.FunOres;
import silent.funores.lib.EnumMetal;
import silent.funores.lib.Names;

public class MetalNugget extends ItemSG {

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
  }
  
  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[EnumMetal.count()];
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Nugget" + EnumMetal.values()[i].getName();
    }
    
    return result;
  }
  
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }
}
