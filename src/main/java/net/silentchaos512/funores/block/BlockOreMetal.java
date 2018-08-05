/*
 * Fun Ores -- BlockOreMetal
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
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.registry.FunOresRegistry;
import net.silentchaos512.funores.util.ModRecipeHelper;
import net.silentchaos512.lib.registry.ICustomModel;
import net.silentchaos512.lib.registry.RecipeMaker;

public class BlockOreMetal extends BlockFunOre implements ICustomModel {
    public static final PropertyEnum<EnumMetal> METAL = PropertyEnum.create("metal", EnumMetal.class);

    public BlockOreMetal() {
        super(EnumMetal.values().length);
        setHardness(3.0f);
        setResistance(15.0f);
        setSoundType(SoundType.STONE);

        for (EnumMetal metal : EnumMetal.values()) {
            if (metal == EnumMetal.COPPER || metal == EnumMetal.TIN || metal == EnumMetal.ALUMINIUM || metal == EnumMetal.ZINC) {
                setHarvestLevel("pickaxe", 1, getDefaultState().withProperty(METAL, metal));
            } else {
                setHarvestLevel("pickaxe", 2, getDefaultState().withProperty(METAL, metal));
            }
        }
    }

    @Override
    public ConfigOptionOreGen getConfig(int meta) {
        if (meta < 0 || meta >= EnumMetal.values().length)
            return null;
        return EnumMetal.byMetadata(meta).getConfig();
    }

    @Override
    public boolean isEnabled(int meta) {
        if (Config.disableMetalOres) return false;
        ConfigOptionOreGen config = getConfig(meta);
        return config != null && config.isEnabled();
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        FunOresRegistry reg = FunOres.registry;
        for (EnumMetal metal : EnumMetal.values()) {
            ItemStack ore = new ItemStack(this, 1, metal.meta);
            // No recipes for disabled ores!
            if (!reg.isItemDisabled(ore)) {
                ItemStack ingot = metal.getIngot();

                // Vanilla smelting
                if (!reg.isItemDisabled(ingot))
                    recipes.addSmelting(ore, ingot, 0.5f);

                // Ender IO Sag Mill
                ItemStack dust = metal.getDust();
                ItemStack bonus = metal.getBonus();
                if (!reg.isItemDisabled(dust) && !reg.isItemDisabled(bonus))
                    ModRecipeHelper.addSagMillRecipe(metal.getMetalName(), ore, dust, bonus, "cobblestone",
                            3000);
            }
        }
    }

    @Override
    public void addOreDict() {
        for (EnumMetal metal : EnumMetal.values()) {
            ItemStack stack = new ItemStack(this, 1, metal.getMeta());
            if (!FunOres.registry.isItemDisabled(stack)) {
                OreDictionary.registerOre("ore" + metal.getMetalName(), stack);
                // Alternative spelling of aluminium
                if (metal == EnumMetal.ALUMINIUM)
                    OreDictionary.registerOre("oreAluminum", stack);
            }
        }
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(METAL).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        Item item = Item.getItemFromBlock(this);
        for (EnumMetal metal : EnumMetal.values()) {
            ItemStack stack = new ItemStack(item, 1, metal.meta);
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
        }
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
    public void registerModels() {
        Item item = Item.getItemFromBlock(this);
        for (EnumMetal metal : EnumMetal.values()) {
            ModelResourceLocation model = new ModelResourceLocation(FunOres.RESOURCE_PREFIX + "metalore", "metal=" + metal.getName());
            ModelLoader.setCustomModelResourceLocation(item, metal.getMeta(), model);
        }
    }
}
