package net.silentchaos512.funores.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.registry.IAddRecipe;
import net.silentchaos512.funores.core.registry.IHasVariants;
import net.silentchaos512.funores.lib.EnumMachineState;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileAlloySmelter;

public class AlloySmelter extends BlockContainer implements IAddRecipe, IHasVariants {

  public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumMachineState.class);
  private static boolean keepInventory; // TODO: What's this?

  public AlloySmelter() {

    super(Material.iron);
    setDefaultState(
        this.blockState.getBaseState().withProperty(FACING, EnumMachineState.NORTH_OFF));
    setCreativeTab(FunOres.tabFunOres);

    setHardness(4.0f);
    setResistance(6000.0f);
    setStepSound(Block.soundTypeMetal);
    setHarvestLevel("pickaxe", 1);

    setUnlocalizedName(Names.ALLOY_SMELTER);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return new TileAlloySmelter();
  }

  @Override
  public String[] getVariantNames() {

    return new String[] { getFullName() };
  }

  @Override
  public String getName() {

    return Names.ALLOY_SMELTER;
  }

  @Override
  public String getFullName() {

    return FunOres.MOD_ID + ":" + getName();
  }

  @Override
  public void addOreDict() {

  }

  @Override
  public void addRecipes() {

    // TODO Auto-generated method stub

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

      if (machineState == EnumMachineState.NORTH_OFF && block.isFullBlock()
          && !block1.isFullBlock()) {
        machineState = EnumMachineState.SOUTH_OFF;
      } else if (machineState == EnumMachineState.SOUTH_OFF && block1.isFullBlock()
          && !block.isFullBlock()) {
        machineState = EnumMachineState.NORTH_OFF;
      } else if (machineState == EnumMachineState.WEST_OFF && block2.isFullBlock()
          && !block3.isFullBlock()) {
        machineState = EnumMachineState.EAST_OFF;
      } else if (machineState == EnumMachineState.EAST_OFF && block3.isFullBlock()
          && !block2.isFullBlock()) {
        machineState = EnumMachineState.WEST_OFF;
      }

      worldIn.setBlockState(pos, state.withProperty(FACING, machineState), 2);
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
      EnumFacing side, float hitX, float hitY, float hitZ) {

    if (world.isRemote) {
      return true;
    } else {
      TileEntity tile = world.getTileEntity(pos);

      if (tile instanceof TileAlloySmelter) {
        player.openGui(FunOres.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
      }

      return true;
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

    EnumMachineState machineState = EnumMachineState
        .fromEnumFacing(placer.getHorizontalFacing().getOpposite());
    world.setBlockState(pos, state.withProperty(FACING, machineState), 2);

    if (stack.hasDisplayName()) {
      TileEntity tileentity = world.getTileEntity(pos);

      if (tileentity instanceof TileAlloySmelter) {
        // ((TileAlloySmelter) tileentity).setCustomInventoryName(stack.getDisplayName());
      }
    }
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {

    if (!keepInventory) {
      TileEntity tile = world.getTileEntity(pos);

      if (tile instanceof TileAlloySmelter) {
        InventoryHelper.dropInventoryItems(world, pos, (TileAlloySmelter) tile);
        world.updateComparatorOutputLevel(pos, this);
      }
    }

    super.breakBlock(world, pos, state);
  }

  @Override
  public boolean hasComparatorInputOverride() {

    return true;
  }

  @Override
  public int getComparatorInputOverride(World world, BlockPos pos) {

    return Container.calcRedstone(world.getTileEntity(pos));
  }

  @Override
  public Item getItem(World world, BlockPos pos) {

    return Item.getItemFromBlock(this);
  }

  @Override
  public int getRenderType() {

    return 3;
  }

  @Override
  public IBlockState getStateForEntityRender(IBlockState state) {

    return this.getDefaultState().withProperty(FACING, EnumMachineState.SOUTH_OFF);
  }

  public IBlockState getStateFromMeta(int meta) {

    EnumMachineState machineState = EnumMachineState.fromMeta(meta);
    return getDefaultState().withProperty(FACING, machineState);
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((EnumMachineState) state.getValue(FACING)).meta;
  }

  @Override
  protected BlockState createBlockState() {

    return new BlockState(this, new IProperty[] { FACING });
  }
}
