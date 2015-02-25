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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import silent.funores.FunOres;
import silent.funores.item.ModItems;
import silent.funores.lib.EnumMetal;
import silent.funores.lib.Names;


public class MetalOre extends BlockSG {
  
  public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

  public MetalOre() {
    
    super(EnumMetal.count(), Material.rock);
    
    setHardness(3.0f);
    setResistance(15.0f);
    setStepSound(Block.soundTypeStone);
    
    for (EnumMetal metal : EnumMetal.values()) {
      if (metal == EnumMetal.COPPER || metal == EnumMetal.TIN
          || metal == EnumMetal.ALUMINIUM || metal == EnumMetal.ZINC) {
        setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(METAL, metal));
      } else {
        setHarvestLevel("pickaxe", 2, getDefaultState().withProperty(METAL, metal));
      }
    }
    
    setHasSubtypes(true);
    setUnlocalizedName(Names.METAL_ORE);
  }
  
  @Override
  public void addRecipes() {
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      ItemStack ore = new ItemStack(this, 1, i);
      ItemStack ingot = new ItemStack(ModItems.metalIngot, 1, i);
      GameRegistry.addSmelting(ore, ingot, 0.5f);
    }
  }
  
  @Override
  public void addOreDict() {
    
    for (EnumMetal metal : EnumMetal.values()) {
      String name = "ore" + metal.getName();
      int meta = metal.getMeta();
      OreDictionary.registerOre(name, new ItemStack(this, 1, meta));
    }
  }
  
  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[EnumMetal.count()];
    
    for (int i = 0; i < EnumMetal.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Ore" + EnumMetal.values()[i].getName();
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
