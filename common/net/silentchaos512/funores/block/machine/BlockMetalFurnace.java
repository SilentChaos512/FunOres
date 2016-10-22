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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileMetalFurnace;

public class BlockMetalFurnace extends BlockMachine {

  public BlockMetalFurnace() {

    super(Material.IRON, Names.METAL_FURNACE);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return new TileMetalFurnace();
  }

  @Override
  public void addRecipes() {

    if (!FunOres.registry.isItemDisabled(new ItemStack(this)))
      for (String alloy : new String[] { "plateBronze", "plateBrass" })
        GameRegistry.addRecipe(new ShapedOreRecipe(this, "aaa", "afa", "bab", 'a', alloy, 'b',
            Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE));
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
}
