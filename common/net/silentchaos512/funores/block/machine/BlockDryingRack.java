package net.silentchaos512.funores.block.machine;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileDryingRack;

public class BlockDryingRack extends BlockMachine {

  public BlockDryingRack() {

    super(Material.WOOD, Names.DRYING_RACK);
    fullBlock = false;
    setHardness(1.5f);
    setResistance(3.0f);
    setSoundType(SoundType.WOOD);
    setUnlocalizedName(Names.DRYING_RACK);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int meta) {

    return new TileDryingRack();
  }

  @Override
  public void addRecipes() {

    ItemStack stack = new ItemStack(this, 2);
    if (!FunOres.registry.isItemDisabled(stack))
      GameRegistry
          .addRecipe(new ShapedOreRecipe(stack, "www", "sss", 'w', "slabWood", 's', "stickWood"));
  }

  @Override
  protected boolean clOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
      EnumHand hand, EnumFacing facing, float side, float hitX, float hitY) {

    TileEntity tile = world.getTileEntity(pos);
    if (tile != null && tile instanceof TileDryingRack) {
      TileDryingRack tileDryingRack = (TileDryingRack) tile;
      ItemStack heldItem = player.getHeldItem(hand);
      return tileDryingRack.interact(player, hand, heldItem);
    }
    return true;
  }

  @Override
  protected void clAddCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
      AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {

    state = state.getActualState(worldIn, pos);
    addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
  }

  // @Override
  // public AxisAlignedBB getCollisionBoundingBox(IBlockState worldIn, World pos, BlockPos state) {
  //
  // return worldIn.getSelectedBoundingBox(pos, state);
  // }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    final float f = 0.25f;
    final float minY = 1f - f;
    final float maxY = 1f;
    switch (getMachineState(state)) {
      case EAST_OFF:
      case EAST_ON:
        return new AxisAlignedBB(0f, minY, 0f, f, maxY, 1f);
      case NORTH_OFF:
      case NORTH_ON:
        return new AxisAlignedBB(0f, minY, 1f - f, 1f, maxY, 1f);
      case SOUTH_OFF:
      case SOUTH_ON:
        return new AxisAlignedBB(0f, minY, 0f, 1f, maxY, f);
      case WEST_OFF:
      case WEST_ON:
        return new AxisAlignedBB(1f - f, minY, 0f, 1f, maxY, 1f);
      default:
        return super.getBoundingBox(state, source, pos);
    }
  }

  // @Override
  // public void setBlockBoundsForItemRender() {
  //
  // // TODO
  // super.setBlockBoundsForItemRender();
  // }

  // @Override
  // public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
  //
  // final float f = 0.25f;
  // final float minY = 1f - f;
  // final float maxY = 1f;
  // switch ((EnumMachineState) worldIn.getBlockState(pos).getValue(FACING)) {
  // case EAST_OFF:
  // case EAST_ON:
  // setBlockBounds(0f, minY, 0f, f, maxY, 1f);
  // break;
  // case NORTH_OFF:
  // case NORTH_ON:
  // setBlockBounds(0f, minY, 1f - f, 1f, maxY, 1f);
  // break;
  // case SOUTH_OFF:
  // case SOUTH_ON:
  // setBlockBounds(0f, minY, 0f, 1f, maxY, f);
  // break;
  // case WEST_OFF:
  // case WEST_ON:
  // setBlockBounds(1f - f, minY, 0f, 1f, maxY, 1f);
  // break;
  // default:
  // setBlockBounds(1f, 1f, 1f, 1f, 1f, 1f);
  // }
  // }

  @Override
  public boolean isFullyOpaque(IBlockState state) {

    return false;
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {

    return false;
  }

  @Override
  public boolean isFullBlock(IBlockState state) {

    return false;
  }

  @Override
  public boolean isFullCube(IBlockState state) {

    return false;
  }

  @Override
  public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos,
      EnumFacing face) {

    return false;
  }
}
