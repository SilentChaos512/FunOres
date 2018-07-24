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

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.block.BlockSL;

public class BlockMetal extends BlockSL implements IDisableable {

    public static final PropertyEnum METAL = PropertyEnum.create("metal", EnumMetal.class);

    public BlockMetal() {

        super(EnumMetal.count(), FunOres.MOD_ID, Names.METAL_BLOCK, Material.IRON);

        setHardness(3.0f);
        setResistance(30.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 1);

        // setHasSubtypes(true);
        setUnlocalizedName(Names.METAL_BLOCK);
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
    public void getModels(Map<Integer, ModelResourceLocation> models) {

        for (EnumMetal metal : EnumMetal.values()) {
            if (!FunOres.registry.isItemDisabled(metal.getBlock())) {
                String name = FunOres.MOD_ID + ":Block" + metal.getMetalName();
                models.put(metal.ordinal(), new ModelResourceLocation(name.toLowerCase(), "inventory"));
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {

        return ((EnumMetal) state.getValue(METAL)).getMeta();
    }

    @Override
    public void clGetSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {

        for (ItemStack stack : getSubItems(item))
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

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState().withProperty(METAL, EnumMetal.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return ((EnumMetal) state.getValue(METAL)).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[]{METAL});
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {

        return true;
    }
}
