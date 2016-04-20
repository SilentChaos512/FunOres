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
import net.silentchaos512.funores.tile.TileAlloySmelter;

public class AlloySmelter extends BlockMachine {

  public AlloySmelter() {

    super(Material.IRON, Names.ALLOY_SMELTER);
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {

    return new TileAlloySmelter();
  }

  @Override
  public void addRecipes() {

    for (String aluminium : new String[] { "plateAluminium", "plateAluminum" }) {
      GameRegistry.addRecipe(new ShapedOreRecipe(this, "iii", "a a", "bab", 'i', "plateIron", 'a',
          aluminium, 'b', Blocks.BRICK_BLOCK));
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
      EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

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
}
