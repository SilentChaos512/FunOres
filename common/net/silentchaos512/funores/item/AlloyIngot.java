package net.silentchaos512.funores.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.util.RecipeHelper;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;

public class AlloyIngot extends ItemSG {
  
  public static final String ORE_DICT_COPPER_ALLOY = "ingotCopperAlloy";

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

    String copper = "ingotCopper";
    String tin = "ingotTin";
    String zinc = "ingotZinc";
    String iron = "ingotIron";
    ItemStack coal = new ItemStack(Items.coal);

    ItemStack bronzeIngot = EnumAlloy.BRONZE.getIngot();
    bronzeIngot.stackSize = 4;
    ItemStack brassIngot = EnumAlloy.BRASS.getIngot();
    brassIngot.stackSize = 4;
    ItemStack steelIngot = EnumAlloy.STEEL.getIngot();

    GameRegistry.addRecipe(new ShapelessOreRecipe(bronzeIngot, copper, copper, copper, tin));
    GameRegistry.addRecipe(new ShapelessOreRecipe(brassIngot, copper, copper, copper, zinc));
    GameRegistry.addRecipe(new ShapelessOreRecipe(steelIngot, iron, coal, coal, coal));
  }

  @Override
  public void addOreDict() {

    for (EnumAlloy alloy : EnumAlloy.values()) {
      String name = "ingot" + alloy.getName();
      int meta = alloy.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
    
    OreDictionary.registerOre(ORE_DICT_COPPER_ALLOY, EnumAlloy.BRONZE.getIngot());
    OreDictionary.registerOre(ORE_DICT_COPPER_ALLOY, EnumAlloy.BRASS.getIngot());
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
