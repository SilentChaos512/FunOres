package silent.funores.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import silent.funores.FunOres;
import silent.funores.core.registry.IAddRecipe;
import silent.funores.core.registry.IHasVariants;
import silent.funores.lib.EnumAlloy;
import silent.funores.lib.EnumMetal;
import silent.funores.lib.Names;
import silent.funores.tile.TileMetalFurnace;

public class MetalFurnace extends BlockContainer implements IAddRecipe, IHasVariants {

  public static final PropertyDirection FACING = PropertyDirection.create("facing",
      EnumFacing.Plane.HORIZONTAL);
  // private final boolean isBurning;
  private static boolean keepInventory;

  public MetalFurnace() {

    super(Material.iron);
    setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    setCreativeTab(FunOres.tabFunOres);

    setHardness(4.0f);
    setResistance(6000.0f);
    setStepSound(Block.soundTypeMetal);
    setHarvestLevel("pickaxe", 1);

    // setHasSubtypes(true);
    setUnlocalizedName(Names.METAL_FURNACE);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return new TileMetalFurnace();
  }

  @Override
  public String[] getVariantNames() {

    return new String[] { getFullName() };
  }

  @Override
  public String getName() {

    return Names.METAL_FURNACE;
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

    // TODO: Fix highly temporary recipes!
    GameRegistry.addRecipe(new ShapedOreRecipe(this, true, "bib", "i i", "rrr", 'i',
        EnumAlloy.BRONZE.getIngot(), 'b', EnumAlloy.BRONZE.getBlock(), 'r', Blocks.brick_block));
    GameRegistry.addRecipe(new ShapedOreRecipe(this, true, "bib", "i i", "rrr", 'i',
        EnumAlloy.BRASS.getIngot(), 'b', EnumAlloy.BRASS.getBlock(), 'r', Blocks.brick_block));
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
      EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

      if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
        enumfacing = EnumFacing.SOUTH;
      } else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
        enumfacing = EnumFacing.NORTH;
      } else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
        enumfacing = EnumFacing.EAST;
      } else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
        enumfacing = EnumFacing.WEST;
      }

      worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
    }
  }

  // @Override
  // public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
  //
  // if (this.isBurning) {
  // EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
  // double d0 = (double) pos.getX() + 0.5D;
  // double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
  // double d2 = (double) pos.getZ() + 0.5D;
  // double d3 = 0.52D;
  // double d4 = rand.nextDouble() * 0.6D - 0.3D;
  //
  // switch (BlockFurnace.SwitchEnumFacing.FACING_LOOKUP[enumfacing.ordinal()]) {
  // case 1:
  // worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D,
  // 0.0D, new int[0]);
  // worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D,
  // new int[0]);
  // break;
  // case 2:
  // worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D,
  // 0.0D, new int[0]);
  // worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D,
  // new int[0]);
  // break;
  // case 3:
  // worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D,
  // 0.0D, new int[0]);
  // worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D,
  // new int[0]);
  // break;
  // case 4:
  // worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D,
  // 0.0D, new int[0]);
  // worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D,
  // new int[0]);
  // }
  // }
  // }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
      EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {

    if (world.isRemote) {
      return true;
    } else {
      TileEntity tile = world.getTileEntity(pos);

      if (tile instanceof TileMetalFurnace) {
        player.openGui(FunOres.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
      }

      return true;
    }
  }

  // public static void setState(boolean active, World worldIn, BlockPos pos) {
  //
  // IBlockState iblockstate = worldIn.getBlockState(pos);
  // TileEntity tileentity = worldIn.getTileEntity(pos);
  // keepInventory = true;
  //
  // if (active) {
  // worldIn.setBlockState(pos,
  // Blocks.lit_furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)),
  // 3);
  // worldIn.setBlockState(pos,
  // Blocks.lit_furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)),
  // 3);
  // } else {
  // worldIn.setBlockState(pos,
  // Blocks.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
  // worldIn.setBlockState(pos,
  // Blocks.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
  // }
  //
  // keepInventory = false;
  //
  // if (tileentity != null) {
  // tileentity.validate();
  // worldIn.setTileEntity(pos, tileentity);
  // }
  // }

  @Override
  public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX,
      float hitY, float hitZ, int meta, EntityLivingBase placer) {

    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
      EntityLivingBase placer, ItemStack stack) {

    world.setBlockState(pos,
        state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

    if (stack.hasDisplayName()) {
      TileEntity tileentity = world.getTileEntity(pos);

      if (tileentity instanceof TileMetalFurnace) {
        // ((TileMetalFurnace) tileentity).setCustomInventoryName(stack.getDisplayName());
      }
    }
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state) {

    if (!keepInventory) {
      TileEntity tile = world.getTileEntity(pos);

      if (tile instanceof TileMetalFurnace) {
        InventoryHelper.dropInventoryItems(world, pos, (TileMetalFurnace) tile);
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

    return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
  }

  public IBlockState getStateFromMeta(int meta) {

    EnumFacing enumFacing = EnumFacing.getFront(meta);

    if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
      enumFacing = EnumFacing.NORTH;
    }

    return this.getDefaultState().withProperty(FACING, enumFacing);
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((EnumFacing) state.getValue(FACING)).getIndex();
  }

  @Override
  protected BlockState createBlockState() {

    return new BlockState(this, new IProperty[] { FACING });
  }
}
