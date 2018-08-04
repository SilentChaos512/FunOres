/*
 * Fun Ores -- BlockMetalFurnace
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import net.silentchaos512.funores.tile.TileMetalFurnace;
import net.silentchaos512.lib.registry.IAddRecipes;
import net.silentchaos512.lib.registry.RecipeMaker;

public class BlockMetalFurnace extends BlockMachine implements IAddRecipes {

    public BlockMetalFurnace() {
        super(Material.IRON);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileMetalFurnace();
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        ItemStack result = new ItemStack(this);
        if (!FunOres.registry.isItemDisabled(result)) {
            recipes.addShapedOre("metal_furnace_bronze", result, "aaa", "afa", "bab", 'a', "plateBronze", 'b', Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE);
            recipes.addShapedOre("metal_furnace_brass", result, "aaa", "afa", "bab", 'a', "plateBrass", 'b', Blocks.BRICK_BLOCK, 'f', Blocks.FURNACE);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float side, float hitX, float hitY) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileMetalFurnace) {
                player.openGui(FunOres.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}
