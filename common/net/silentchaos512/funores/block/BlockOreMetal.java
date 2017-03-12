package net.silentchaos512.funores.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.registry.FunOresRegistry;
import net.silentchaos512.funores.util.ModRecipeHelper;
import net.silentchaos512.wit.api.IWitHudInfo;

public class BlockOreMetal extends BlockFunOre {

  public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

  public BlockOreMetal() {

    super(EnumMetal.count(), Names.METAL_ORE);

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

    setUnlocalizedName(Names.METAL_ORE);
  }

  @Override
  public ConfigOptionOreGen getConfig(int meta) {

    if (meta < 0 || meta >= EnumMetal.values().length)
      return null;
    return EnumMetal.byMetadata(meta).getConfig();
  }

  @Override
  public boolean isEnabled(int meta) {

    if (Config.disableMetalOres)
      return false;

    ConfigOptionOreGen config = getConfig(meta);
    return config == null ? false : config.isEnabled();
  }

  @Override
  public void addRecipes() {

    FunOresRegistry reg = FunOres.registry;
    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack ore = new ItemStack(this, 1, metal.meta);
      // No recipes for disabled ores!
      if (!reg.isItemDisabled(ore)) {
        ItemStack ingot = metal.getIngot();

        // Vanilla smelting
        if (!reg.isItemDisabled(ingot))
          GameRegistry.addSmelting(ore, ingot, 0.5f);

        // Ender IO Sag Mill
        ItemStack dust = metal.getDust();
        ItemStack bonus = metal.getBonus();
        if (!reg.isItemDisabled(dust) && !reg.isItemDisabled(bonus))
          ModRecipeHelper.addSagMillRecipe(metal.getMetalName(), ore, dust, bonus, "cobblestone",
              3000);
      }
    }
  }

  @Override
  public void addOreDict() {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack stack = new ItemStack(this, 1, metal.getMeta());
      if (!FunOres.registry.isItemDisabled(stack)) {
        OreDictionary.registerOre("ore" + metal.getMetalName(), stack);
        // Alternative spelling of aluminium
        if (metal == EnumMetal.ALUMINIUM)
          OreDictionary.registerOre("oreAluminum", stack);
      }
    }
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMetal metal : EnumMetal.values()) {
      if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, metal.meta))) {
        String name = FunOres.MOD_ID + ":Ore" + metal.getMetalName();
        models.add(new ModelResourceLocation(name.toLowerCase(), "inventory"));
      } else {
        models.add(null);
      }
    }
    return models;
  }

  @Override
  public int damageDropped(IBlockState state) {

    return ((EnumMetal) state.getValue(METAL)).getMeta();
  }

  @Override
  public void clGetSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {

    for (EnumMetal metal : EnumMetal.values()) {
      ItemStack stack = new ItemStack(item, 1, metal.meta);
      if (!FunOres.registry.isItemDisabled(stack))
        list.add(stack);
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
