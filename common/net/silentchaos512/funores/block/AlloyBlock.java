package net.silentchaos512.funores.block;

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
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.Names;

public class AlloyBlock extends BlockSG {
  
  public static final PropertyEnum ALLOY = PropertyEnum.create("alloy", EnumAlloy.class);

  public AlloyBlock() {

    super(EnumAlloy.count(), Material.iron);

    setHardness(3.0f);
    setResistance(30.0f);
    setStepSound(Block.soundTypeMetal);
    setHarvestLevel("pickaxe", 1);

    setHasSubtypes(true);
    setUnlocalizedName(Names.ALLOY_BLOCK);
  }

  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[EnumAlloy.count()];
    
    for (int i = 0; i < EnumAlloy.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Block" + EnumAlloy.values()[i].getName();
    }
    
    return result;
  }
  
  @Override
  public int damageDropped(IBlockState state) {
    
    return ((EnumAlloy) state.getValue(ALLOY)).getMeta();
  }
  
  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    
    for (int i = 0; i < EnumAlloy.count(); ++i) {
      list.add(new ItemStack(item, 1, EnumAlloy.values()[i].getMeta()));
    }
  }
  
  @Override
  public IBlockState getStateFromMeta(int meta) {
    
    return this.getDefaultState().withProperty(ALLOY, EnumAlloy.byMetadata(meta));
  }
  
  @Override
  public int getMetaFromState(IBlockState state) {
    
    return ((EnumAlloy) state.getValue(ALLOY)).getMeta();
  }
  
  @Override
  protected BlockState createBlockState() {
    
    return new BlockState(this, new IProperty[] { ALLOY });
  }
}
