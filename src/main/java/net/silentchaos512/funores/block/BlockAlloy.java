/*
 * Fun Ores -- BlockAlloy
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

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.lib.block.BlockMetaSubtypes;
import net.silentchaos512.lib.registry.IAddRecipes;
import net.silentchaos512.lib.registry.ICustomModel;

import java.util.ArrayList;
import java.util.List;

public class BlockAlloy extends BlockMetaSubtypes implements IDisableable, IAddRecipes, ICustomModel {
    private static final PropertyEnum<EnumAlloy> ALLOY = PropertyEnum.create("alloy", EnumAlloy.class);

    public BlockAlloy() {
        super(Material.IRON, EnumAlloy.values().length);
        setHardness(3.0f);
        setResistance(30.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public void addOreDict() {
        for (EnumAlloy alloy : EnumAlloy.values())
            if (!FunOres.registry.isItemDisabled(alloy.getBlock()))
                OreDictionary.registerOre("block" + alloy.getMetalName(), alloy.getBlock());
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(ALLOY).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (ItemStack stack : getSubItems(Item.getItemFromBlock(this)))
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
    }

    @Override
    public List<ItemStack> getSubItems(Item item) {
        List<ItemStack> ret = new ArrayList<>();
        for (IMetal metal : EnumAlloy.values())
            ret.add(new ItemStack(item, 1, metal.getMeta()));
        return ret;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(ALLOY, EnumAlloy.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ALLOY).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ALLOY);
    }

    @Override
    public void registerModels() {
        Item item = Item.getItemFromBlock(this);
        for (EnumAlloy alloy : EnumAlloy.values()) {
            ModelResourceLocation model = new ModelResourceLocation(FunOres.RESOURCE_PREFIX + "alloyblock", "alloy=" + alloy.getName());
            ModelLoader.setCustomModelResourceLocation(item, alloy.getMeta(), model);
        }
    }
}
