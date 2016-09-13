package net.silentchaos512.funores.block.machine;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.lib.EnumMachineState;
import net.silentchaos512.funores.lib.ModDamageSources;
import net.silentchaos512.lib.registry.IRegistryObject;
import net.silentchaos512.wit.api.IWitHudInfo;

public class BlockMachine extends BlockContainer implements IRegistryObject, IWitHudInfo {

  public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumMachineState.class);
  protected static boolean keepInventory;
  protected String machineBlockName;

  protected BlockMachine(Material materialIn, String name) {

    super(materialIn);
    this.machineBlockName = name;
    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumMachineState.NORTH_OFF));
    setCreativeTab(FunOres.tabFunOres);

    setHardness(4.0f);
    setResistance(6000.0f);
    setSoundType(SoundType.METAL);
    setHarvestLevel("pickaxe", 1);

    setUnlocalizedName(name);
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!advanced) {
      return Lists.newArrayList();
    }
    return Lists.newArrayList("State: " + ((EnumMachineState) state.getValue(FACING)));
  }

  public EnumMachineState getMachineState(World world, BlockPos pos) {

    return getMachineState(world.getBlockState(pos));
  }

  public EnumMachineState getMachineState(IBlockState state) {

    return (EnumMachineState) state.getValue(FACING);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return null;
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    return Lists.newArrayList(new ModelResourceLocation(getFullName()));
  }

  @Override
  public String getName() {

    return machineBlockName;
  }

  @Override
  public String getFullName() {

    return getModId() + ":" + getName();
  }

  @Override
  public String getModId() {

    return FunOres.MOD_ID.toLowerCase();
  }

  @Override
  public boolean registerModels() {

    return false;
  }

  @Override
  public void addOreDict() {

  }

  @Override
  public void addRecipes() {

  }

  @Override
  public int getLightValue(IBlockState state) {

    return getMachineState(state).isOn ? 15 : 0;
  }

  @Override
  public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {

    if (Config.machinesCanBurn && getMachineState(world, pos).isOn) {
      entity.attackEntityFrom(ModDamageSources.hotMachine, 0.5f);
    }
    super.onEntityCollidedWithBlock(world, pos, state, entity);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {

    return Item.getItemFromBlock(this);
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {

    this.setDefaultFacing(worldIn, pos, state);
  }

  private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {

    if (!worldIn.isRemote) {
      Block block = worldIn.getBlockState(pos.north()).getBlock();
      Block block1 = worldIn.getBlockState(pos.south()).getBlock();
      Block block2 = worldIn.getBlockState(pos.west()).getBlock();
      Block block3 = worldIn.getBlockState(pos.east()).getBlock();

      EnumMachineState machineState = (EnumMachineState) state.getValue(FACING);

      if (machineState == EnumMachineState.NORTH_OFF && block.isFullBlock(state)
          && !block1.isFullBlock(state)) {
        machineState = EnumMachineState.SOUTH_OFF;
      } else if (machineState == EnumMachineState.SOUTH_OFF && block1.isFullBlock(state)
          && !block.isFullBlock(state)) {
        machineState = EnumMachineState.NORTH_OFF;
      } else if (machineState == EnumMachineState.WEST_OFF && block2.isFullBlock(state)
          && !block3.isFullBlock(state)) {
        machineState = EnumMachineState.EAST_OFF;
      } else if (machineState == EnumMachineState.EAST_OFF && block3.isFullBlock(state)
          && !block2.isFullBlock(state)) {
        machineState = EnumMachineState.WEST_OFF;
      }

      worldIn.setBlockState(pos, state.withProperty(FACING, machineState), 2);
    }
  }

  @Override
  public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX,
      float hitY, float hitZ, int meta, EntityLivingBase placer) {

    EnumMachineState machineState = EnumMachineState
        .fromEnumFacing(placer.getHorizontalFacing().getOpposite());
    return this.getDefaultState().withProperty(FACING, machineState);
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
      ItemStack stack) {

    super.onBlockPlacedBy(world, pos, state, placer, stack);
    EnumMachineState machineState = EnumMachineState
        .fromEnumFacing(placer.getHorizontalFacing().getOpposite());
    world.setBlockState(pos, state.withProperty(FACING, machineState), 2);
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {

    if (!keepInventory) {
      TileEntity tile = world.getTileEntity(pos);

      if (tile instanceof IInventory) {
        InventoryHelper.dropInventoryItems(world, pos, (IInventory) tile);
        world.updateComparatorOutputLevel(pos, this);
      }
    }

    super.breakBlock(world, pos, state);
  }

  @Override
  public boolean hasComparatorInputOverride(IBlockState state) {

    return true;
  }

  @Override
  public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {

    return Container.calcRedstone(world.getTileEntity(pos));
  }

  @Override
  public ItemStack getItem(World world, BlockPos pos, IBlockState state) {

    return new ItemStack(Item.getItemFromBlock(this));
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {

    return EnumBlockRenderType.MODEL;
  }

  // TODO: What's this?
//  @Override
//  public IBlockState getStateForEntityRender(IBlockState state) {
//
//    return this.getDefaultState().withProperty(FACING, EnumMachineState.SOUTH_OFF);
//  }

  public IBlockState getStateFromMeta(int meta) {

    EnumMachineState machineState = EnumMachineState.fromMeta(meta);
    return getDefaultState().withProperty(FACING, machineState);
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((EnumMachineState) state.getValue(FACING)).meta;
  }

  @Override
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, new IProperty[] { FACING });
  }
}
