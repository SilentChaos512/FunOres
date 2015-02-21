package silent.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import silent.funores.FunOres;
import silent.funores.core.util.RecipeHelper;
import silent.funores.lib.EnumAlloy;
import silent.funores.lib.EnumMetal;
import silent.funores.lib.Names;

public class AlloyIngot extends ItemSG {

  public AlloyIngot() {

    super(EnumAlloy.count());

    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.ALLOY_INGOT);
  }

  @Override
  public void addRecipes() {

    for (int i = 0; i < EnumAlloy.count(); ++i) {
      EnumAlloy alloy = EnumAlloy.byMetadata(i);
      // Ingots <--> Blocks
      RecipeHelper.addCompressionRecipe(alloy.getIngot(), alloy.getBlock(), 9);
      // Nuggets <--> Ingots
      RecipeHelper.addCompressionRecipe(alloy.getNugget(), alloy.getIngot(), 9);
    }

    // TODO: Temporary recipes?

    ItemStack copperIngot = EnumMetal.COPPER.getIngot();
    ItemStack tinIngot = EnumMetal.TIN.getIngot();
    ItemStack zincIngot = EnumMetal.ZINC.getIngot();

    ItemStack bronzeIngot = EnumAlloy.BRONZE.getIngot();
    bronzeIngot.stackSize = 4;
    ItemStack brassIngot = EnumAlloy.BRASS.getIngot();
    brassIngot.stackSize = 4;

    GameRegistry.addShapelessRecipe(bronzeIngot, copperIngot, copperIngot, copperIngot, tinIngot);
    GameRegistry.addShapelessRecipe(brassIngot, copperIngot, copperIngot, copperIngot, zincIngot);
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy alloy : EnumAlloy.values()) {
      String name = "ingot" + alloy.getName();
      int meta = alloy.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[EnumAlloy.count()];

    for (int i = 0; i < EnumAlloy.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Ingot" + EnumAlloy.values()[i].getName();
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
