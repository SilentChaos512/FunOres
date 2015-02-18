package silent.funores.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import silent.funores.FunOres;
import silent.funores.lib.EnumMetal;
import silent.funores.lib.Names;

public class MetalBlock extends BlockSG {
  
  public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

  public MetalBlock() {

    super(EnumMetal.count(), Material.iron);

    setHardness(3.0f);
    setResistance(30.0f);
    setStepSound(Block.soundTypeMetal);
    setHarvestLevel("pickaxe", 1);
    
    setHasSubtypes(true);
    setUnlocalizedName(Names.METAL_BLOCK);
  }

  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[EnumMetal.count()];
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Block" + EnumMetal.values()[i].getName();
    }
    
    return result;
  }
  
  @Override
  public int damageDropped(IBlockState state) {
    
    return ((EnumMetal) state.getValue(METAL)).getMeta();
  }
  
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      list.add(new ItemStack(item, 1, EnumMetal.values()[i].getMeta()));
    }
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta) {
    
    return this.getDefaultState().withProperty(METAL, EnumMetal.byMetadata(meta));
  }
  
  @Override
  public int getMetaFromState(IBlockState state) {
    
    return ((EnumMetal) state.getValue(METAL)).getMeta();
  }
  
  @Override
  protected BlockState createBlockState() {
    
    return new BlockState(this, new IProperty[] { METAL });
  }
}
