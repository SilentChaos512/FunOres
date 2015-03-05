package net.silentchaos512.funores.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.registry.IAddRecipe;
import net.silentchaos512.funores.core.registry.IHasVariants;

public class BlockSG extends Block implements IAddRecipe, IHasVariants {

  protected int subBlockCount = 1;
  protected boolean hasSubtypes = false;
  protected String blockName = "null";

  public BlockSG(int subBlockCount, Material material) {

    super(material);
    this.subBlockCount = subBlockCount;

    setHardness(3.0f);
    setResistance(10.0f);
    setStepSound(Block.soundTypeStone);

    setCreativeTab(FunOres.tabFunOres);
  }
  
  @Override
  public void addRecipes() {
    
  }
  
  @Override
  public void addOreDict() {
    
  }
  
  @Override
  public String getName() {

    return blockName;
  }

  @Override
  public String getFullName() {

    return FunOres.MOD_ID + ":" + blockName;
  }

  @Override
  public String[] getVariantNames() {

    if (hasSubtypes) {
      String[] names = new String[subBlockCount];
      for (int i = 0; i < names.length; ++i) {
        names[i] = getFullName() + i;
      }
      return names;
    }
    return new String[] { getFullName() };
  }
  
  public boolean getHasSubtypes() {

    return hasSubtypes;
  }

  public Block setHasSubtypes(boolean value) {

    hasSubtypes = value;
    return this;
  }
  
  @Override
  public int damageDropped(IBlockState state) {

    return hasSubtypes ? this.getMetaFromState(state) : 0;
  }
  
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {

    if (hasSubtypes) {
      for (int i = 0; i < subBlockCount; ++i) {
        subItems.add(new ItemStack(this, 1, i));
      }
    } else {
      super.getSubBlocks(item, tab, subItems);
    }
  }
  
  @Override
  public String getUnlocalizedName() {

    return "tile." + this.blockName;
  }
  
  @Override
  public Block setUnlocalizedName(String blockName) {

    this.blockName = blockName;
    return this;
  }
}
