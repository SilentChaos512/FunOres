package net.silentchaos512.funores.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.item.ModItems;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.util.ModRecipeHelper;
import net.silentchaos512.lib.block.BlockSL;
import net.silentchaos512.wit.api.IWitHudInfo;

public class MetalOre extends BlockSL implements IWitHudInfo {

  public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

  public MetalOre() {

    super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_ORE, Material.ROCK);

    setHardness(3.0f);
    setResistance(15.0f);
    setSoundType(SoundType.STONE);

    for (EnumMetal metal : EnumMetal.values()) {
      if (metal == EnumMetal.COPPER || metal == EnumMetal.TIN || metal == EnumMetal.ALUMINIUM
          || metal == EnumMetal.ZINC) {
        setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(METAL, metal));
      } else {
        setHarvestLevel("pickaxe", 2, getDefaultState().withProperty(METAL, metal));
      }
    }

    // setHasSubtypes(true);
    setUnlocalizedName(Names.METAL_ORE);
  }

  @Override
  public void addRecipes() {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack ore = new ItemStack(this, 1, metal.meta);
      ItemStack ingot = metal.getIngot();
      GameRegistry.addSmelting(ore, ingot, 0.5f);

      ItemStack dust = metal.getDust();
      ItemStack bonus = metal.getBonus();
      ModRecipeHelper.addSagMillRecipe(metal.getMetalName(), ore, dust, bonus, "cobblestone", 3000);
    }
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack stack = new ItemStack(this, 1, metal.getMeta());
      OreDictionary.registerOre("ore" + metal.getMetalName(), stack);
    }

    // Alternative spelling of aluminium
    OreDictionary.registerOre("oreAluminum", new ItemStack(this, 1, EnumMetal.ALUMINIUM.meta));
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!player.isSneaking() && !advanced) {
      return null;
    }

    EnumMetal metal = EnumMetal.byMetadata(state.getBlock().getMetaFromState(state));
    ConfigOptionOreGen config = metal.getConfig();
    return ModBlocks.getWitInfoForOre(config, state, pos, player);
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMetal metal : EnumMetal.values()) {
      models.add(
          new ModelResourceLocation(FunOres.MOD_ID + ":Ore" + metal.getMetalName(), "inventory"));
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
}
