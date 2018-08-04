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
import net.silentchaos512.funores.tile.TileAlloySmelter;
import net.silentchaos512.lib.registry.IAddRecipes;
import net.silentchaos512.lib.registry.RecipeMaker;

public class BlockAlloySmelter extends BlockMachine implements IAddRecipes {

    public BlockAlloySmelter() {
        super(Material.IRON);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAlloySmelter();
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        ItemStack result = new ItemStack(this);
        if (!FunOres.registry.isItemDisabled(result)) {
            recipes.addShapedOre("alloy_smelter_1", result, "iii", "a a", "bab", 'i', "plateIron", 'a', "plateAluminum", 'b', Blocks.BRICK_BLOCK);
            recipes.addShapedOre("alloy_smelter_2", result, "iii", "a a", "bab", 'i', "plateIron", 'a', "plateAluminium", 'b', Blocks.BRICK_BLOCK);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY) {
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
