package net.silentchaos512.funores.item;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.registry.IAddRecipe;
import net.silentchaos512.funores.core.registry.IHasVariants;
import net.silentchaos512.funores.material.ModMaterials;

public class Pickaxe extends ItemPickaxe implements IAddRecipe, IHasVariants {
  
  private String name;
  private String ingot = "";

  public Pickaxe(ToolMaterial material) {

    super(material);
    setCreativeTab(FunOres.tabFunOres);

    name = "Pickaxe";
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

    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this), true, "iii", " s ", " s ", 'i',
        ingot, 's', "stickWood"));
  }
  
  @Override
  public String getUnlocalizedName(ItemStack stack) {

    return "item.funores:" + name;
  }
}
