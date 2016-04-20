package net.silentchaos512.funores.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.block.BlockSL;

public class MetalBlock extends BlockSL {
  
  public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

  public MetalBlock() {

    super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_BLOCK, Material.IRON);

    setHardness(3.0f);
    setResistance(30.0f);
    setSoundType(SoundType.METAL);
    setHarvestLevel("pickaxe", 1);
    
//    setHasSubtypes(true);
    setUnlocalizedName(Names.METAL_BLOCK);
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      OreDictionary.registerOre("block" + metal.getMetalName(), metal.getBlock());
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMetal metal : EnumMetal.values()) {
      models.add(new ModelResourceLocation(FunOres.MOD_ID + ":Block" + metal.getMetalName(), "inventory"));
    }
    return models;
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
  protected BlockStateContainer createBlockState() {
    
    return new BlockStateContainer(this, new IProperty[] { METAL });
  }
  
  @Override
  public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
    
    return true;
  }
}
