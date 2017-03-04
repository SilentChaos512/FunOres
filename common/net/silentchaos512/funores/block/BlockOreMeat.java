package net.silentchaos512.funores.block;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.util.OreLootHelper;
import net.silentchaos512.wit.api.IWitHudInfo;

public class BlockOreMeat extends BlockFunOre implements IWitHudInfo {

  public static final PropertyEnum MEAT = PropertyEnum.create("meat", EnumMeat.class);

  public BlockOreMeat() {

    super(EnumMeat.count(), Names.MEAT_ORE);

    setHardness(1.5f);
    setResistance(10.0f);
    setSoundType(SoundType.STONE);
    setHarvestLevel("pickaxe", 0);

    setUnlocalizedName(Names.MEAT_ORE);
  }

  @Override
  public ConfigOptionOreGen getConfig(int meta) {

    if (meta < 0 || meta >= EnumMeat.values().length)
      return null;
    return EnumMeat.byMetadata(meta).getConfig();
  }

  @Override
  public boolean isEnabled(int meta) {

    if (Config.disableMeatOres)
      return false;

    ConfigOptionOreGen config = getConfig(meta);
    return config == null ? false : config.enabled;
  }

  @Override
  public void addOreDict() {

    for (EnumMeat meat : EnumMeat.values()) {
      ItemStack stack = new ItemStack(this, 1, meat.meta);
      if (!FunOres.registry.isItemDisabled(stack))
        OreDictionary.registerOre("ore" + meat.getName(), stack);
    }
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!player.isSneaking() && !advanced) {
      return null;
    }

    EnumMeat meat = EnumMeat.byMetadata(state.getBlock().getMetaFromState(state));
    ConfigOptionOreGen config = meat.getConfig();
    return ModBlocks.getWitInfoForOre(config, state, pos, player);
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMeat meat : EnumMeat.values()) {
      if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, meat.meta))) {
        String name = FunOres.MOD_ID + ":Ore" + meat.getUnmodifiedName();
        models.add(new ModelResourceLocation(name.toLowerCase(), "inventory"));
      } else {
        models.add(null);
      }
    }
    return models;
  }

  @Override
  public int damageDropped(IBlockState state) {

    return ((EnumMeat) state.getValue(MEAT)).getMeta();
  }

  @Override
  public void clGetSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {

    for (EnumMeat meat : EnumMeat.values()) {
      ItemStack stack = new ItemStack(item, 1, meat.meta);
      if (!FunOres.registry.isItemDisabled(stack))
        list.add(stack);
    }
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState().withProperty(MEAT, EnumMeat.byMetadata(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((EnumMeat) state.getValue(MEAT)).getMeta();
  }

  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, new IProperty[] { MEAT });
  }

  @Override
  public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {

    Item drop = this.getItemDropped(world.getBlockState(pos), RANDOM, fortune);
    if (drop != Item.getItemFromBlock(this)) {
      return 1 + RANDOM.nextInt(3);
    }
    return 0;
  }

  @Override
  public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance,
      int fortune) {

    super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

    // Spawn bats?
    if ((EnumMeat) state.getValue(MEAT) == EnumMeat.BAT
        && FunOres.instance.random.nextFloat() < Config.spawnBatChance) {
      if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
        EntityBat entity = new EntityBat(world);
        entity.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY(),
            (double) pos.getZ() + 0.5, 0.0f, 0.0f);
        world.spawnEntity(entity);
        entity.spawnExplosionParticle();
      }
    }
  }

  @Override
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state,
      int fortune) {

    Random rand = FunOres.random;

    if (world instanceof WorldServer) {
      WorldServer worldServer = (WorldServer) world;
      EnumMeat meat = ((EnumMeat) state.getValue(MEAT));
      EntityLivingBase entityLiving = meat.getEntityLiving(worldServer);
      int tryCount = meat == EnumMeat.FISH ? 2 + rand.nextInt(3) : 1;
      ConfigOptionOreGenBonus config = ((EnumMeat) state.getValue(MEAT)).getConfig();
      return OreLootHelper.getDrops(worldServer, fortune, meat, tryCount, config);
    }

    return Lists.newArrayList();
  }

  @Override
  public int quantityDroppedWithBonus(int fortune, Random random) {

    if (fortune > 0) {
      int j = random.nextInt(fortune + 2) - 1;

      if (j < 0) {
        j = 0;
      }

      return quantityDropped(random) * (j + 1);
    } else {
      return quantityDropped(random);
    }
  }
}
