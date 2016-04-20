package net.silentchaos512.funores.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileMetalFurnace;

public class MetalFurnace extends BlockMachine {

  public MetalFurnace() {

    super(Material.IRON, Names.METAL_FURNACE);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return new TileMetalFurnace();
  }

  @Override
  public void addRecipes() {

    for (String alloy : new String[] { "plateBronze", "plateBrass" }) {
      GameRegistry.addRecipe(new ShapedOreRecipe(this, "aaa", "afa", "bab", 'a', alloy, 'b',
          Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE));
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
      EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {

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
}
