/*
 * Fun Ores -- BlockMetal
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

package net.silentchaos512.funores.block;

import com.google.common.collect.Lists;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.lib.block.BlockMetaSubtypes;
import net.silentchaos512.lib.registry.IAddRecipes;

import java.util.List;

public class BlockMetal extends BlockMetaSubtypes implements IDisableable, IAddRecipes {
    private static final PropertyEnum<EnumMetal> METAL = PropertyEnum.create("metal", EnumMetal.class);

    public BlockMetal() {
        super(Material.IRON, EnumMetal.values().length);
        setHardness(3.0f);
        setResistance(30.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void addOreDict() {
        for (EnumMetal metal : EnumMetal.values())
            if (!FunOres.registry.isItemDisabled(metal.getBlock()))
                OreDictionary.registerOre("block" + metal.getMetalName(), metal.getBlock());

        // Alternative spelling of aluminium
        if (!FunOres.registry.isItemDisabled(EnumMetal.ALUMINIUM.getBlock()))
            OreDictionary.registerOre("blockAluminum", EnumMetal.ALUMINIUM.getBlock());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(METAL).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (ItemStack stack : getSubItems(Item.getItemFromBlock(this)))
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
    }

    @Override
    public List<ItemStack> getSubItems(Item item) {
        List<ItemStack> ret = Lists.newArrayList();
        for (IMetal metal : EnumMetal.values())
            ret.add(new ItemStack(item, 1, metal.getMeta()));
        return ret;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(METAL, EnumMetal.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(METAL).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, METAL);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        return true;
    }
}
