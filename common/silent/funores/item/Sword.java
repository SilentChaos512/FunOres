package silent.funores.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import silent.funores.FunOres;
import silent.funores.core.registry.IAddRecipe;
import silent.funores.core.registry.IHasVariants;
import silent.funores.material.ModMaterials;

public class Sword extends ItemSword implements IAddRecipe, IHasVariants {

  private String name;
  private String ingot = "";

  public Sword(ToolMaterial material) {

    super(material);
    setCreativeTab(FunOres.tabFunOres);

    name = "Sword";
    if (material.equals(ModMaterials.toolBronze)) {
      name += "Bronze";
      ingot = "ingotCopperAlloy";
    } else if (material.equals(ModMaterials.toolSteel)) {
      name += "Steel";
      ingot = "ingotSteel";
    } else {
      name += "Null";
    }
  }

  @Override
  public String[] getVariantNames() {

    return new String[] { getFullName() };
  }

  @Override
  public String getName() {

    return name;
  }

  @Override
  public String getFullName() {

    return FunOres.MOD_ID + ":" + getName();
  }

  @Override
  public void addOreDict() {

  }

  @Override
  public void addRecipes() {

    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), true, "i", "i", "s", 'i',
        ingot, 's', "stickWood"));
  }
  
  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return "item.funores:" + name;
  }
}
