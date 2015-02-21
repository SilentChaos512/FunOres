package silent.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import silent.funores.FunOres;
import silent.funores.lib.EnumAlloy;
import silent.funores.lib.Names;


public class AlloyNugget extends ItemSG {

  public AlloyNugget() {

    super(EnumAlloy.count());
    
    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.ALLOY_NUGGET);
  }

  @Override
  public void addOreDict() {
    
    for (EnumAlloy alloy : EnumAlloy.values()) {
      String name = "nugget" + alloy.getName();
      int meta = alloy.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }
  
  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[EnumAlloy.count()];
    
    for (int i = 0; i < EnumAlloy.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Nugget" + EnumAlloy.values()[i].getName();
    }
    
    return result;
  }
  
  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumAlloy.count(); ++i) {
      list.add(new ItemStack(this, 1, i));
    }
  }
}
