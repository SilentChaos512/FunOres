package net.silentchaos512.funores.block.machine;

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
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileMetalFurnace;
import net.silentchaos512.lib.registry.RecipeMaker;

public class BlockMetalFurnace extends BlockMachine {

  public BlockMetalFurnace() {

    super(Material.IRON, Names.METAL_FURNACE);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return new TileMetalFurnace();
  }

  @Override
  public void addRecipes(RecipeMaker recipes) {

    ItemStack result = new ItemStack(this);
    if (!FunOres.registry.isItemDisabled(result)) {
      recipes.addShapedOre(getName() + 0, result, "aaa", "afa", "bab", 'a', "plateBronze", 'b', Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE);
      recipes.addShapedOre(getName() + 1, result, "aaa", "afa", "bab", 'a', "plateBrass", 'b', Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE);
    }
  }

  @Override
  protected boolean clOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
      EnumHand hand, EnumFacing facing, float side, float hitX, float hitY) {

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
}
