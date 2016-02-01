package net.silentchaos512.funores.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.item.ModItems;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.wit.api.IWitHudInfo;

public class MetalOre extends BlockSG implements IWitHudInfo {

  public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

  public MetalOre() {

    super(EnumMetal.count(), Material.rock);

    setHardness(3.0f);
    setResistance(15.0f);
    setStepSound(Block.soundTypeStone);

    for (EnumMetal metal : EnumMetal.values()) {
      if (metal == EnumMetal.COPPER || metal == EnumMetal.TIN || metal == EnumMetal.ALUMINIUM
          || metal == EnumMetal.ZINC) {
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
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!player.isSneaking()) {
      return null;
    }

    EnumMetal metal = EnumMetal.byMetadata(state.getBlock().getMetaFromState(state));
    ConfigOptionOreGen config = metal.getConfig();
    return ModBlocks.getWitInfoForOre(config, state, pos, player);
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
