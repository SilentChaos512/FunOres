package net.silentchaos512.funores.block;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.EnumMachineState;
import net.silentchaos512.funores.lib.ModDamageSources;
import net.silentchaos512.wit.api.IWitHudInfo;

public class BlockSpikes extends BlockSG implements IWitHudInfo {

  public static final PropertyEnum FACING = PropertyEnum.create("facing", EnumFacing.class);

  public BlockSpikes() {

    super(1, Material.iron);
    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
    setCreativeTab(FunOres.tabFunOres);

    setHardness(6.0f);
    setResistance(2000.0f);
    setStepSound(Block.soundTypeMetal);
    setHarvestLevel("pickaxe", 1);

    setUnlocalizedName("Spikes"); // FIXME
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    return Lists.newArrayList("Facing: " + ((EnumFacing) state.getValue(FACING)));
  }

  @Override
  public void addRecipes() {

    GameRegistry.addRecipe(
        new ShapedOreRecipe(new ItemStack(this), " i ", "ipi", 'i', "ingotIron", 'p', "plateIron"));
  }

  @Override
  public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {

    entity.attackEntityFrom(ModDamageSources.hotMachine, 1.0f); // FIXME
    super.onEntityCollidedWithBlock(world, pos, entity);
  }

  // @Override
  // public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
  //
  // this.setDefaultFacing(worldIn, pos, state);
  // }

  @Override
  public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX,
      float hitY, float hitZ, int meta, EntityLivingBase placer) {

    LogHelper.list(facing, pos.getX(), pos.getY(), pos.getZ(), hitX, hitY, hitZ);
    return this.getDefaultState().withProperty(FACING, facing); // FIXME?
  }

  // @Override
  // public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
  // ItemStack stack) {
  //
  // super.onBlockPlacedBy(world, pos, state, placer, stack);
  // world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2); // FIXME?
  // }

  @Override
  public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state,
      AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {

    setBlockBoundsBasedOnState(worldIn, pos);
    super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
  }

  @Override
  public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {

    final float f = 0.15f; // 0.05f
    final float minX = f;
    final float maxX = 1f - f;
    final float height = 0.8f; // 0.4f
    switch ((EnumFacing) worldIn.getBlockState(pos).getValue(FACING)) {
      case DOWN:
        setBlockBounds(minX, 0f, minX, maxX, height, maxX);
        break;
      case EAST:
        setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        break;
      case NORTH:
        setBlockBounds(0f, 0f, 1f - height, 1f, 1f, 1f);
        break;
      case SOUTH:
        setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        break;
      case UP:
        setBlockBounds(minX, 0f, minX, maxX, height, maxX);
        break;
      case WEST:
        setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        break;
      default:
        setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
    }
  }

  public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {

    float f = 0.0625F;
    return new AxisAlignedBB((double) ((float) pos.getX() + f), (double) pos.getY(),
        (double) ((float) pos.getZ() + f), (double) ((float) (pos.getX() + 1) - f),
        (double) ((float) (pos.getY() + 1) - f), (double) ((float) (pos.getZ() + 1) - f));
  }

  @SideOnly(Side.CLIENT)
  public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {

    float f = 0.0625F;
    return new AxisAlignedBB((double) ((float) pos.getX() + f), (double) pos.getY(),
        (double) ((float) pos.getZ() + f), (double) ((float) (pos.getX() + 1) - f),
        (double) (pos.getY() + 1), (double) ((float) (pos.getZ() + 1) - f));
  }

  @Override
  public boolean isOpaqueCube() {

    return false;
  }

  @Override
  public boolean isFullCube() {

    return false;
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

    EnumFacing facing = meta >= 0 && meta < EnumFacing.values().length ? EnumFacing.values()[meta]
        : EnumFacing.UP;
    return getDefaultState().withProperty(FACING, facing);
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
