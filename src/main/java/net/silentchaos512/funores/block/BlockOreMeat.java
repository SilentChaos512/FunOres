/*
 * Fun Ores -- BlockOreMeat
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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.util.OreLootHelper;

import java.util.Random;

public class BlockOreMeat extends BlockFunOre {

    public static final PropertyEnum<EnumMeat> MEAT = PropertyEnum.create("meat", EnumMeat.class);

    public BlockOreMeat() {
        super(EnumMeat.values().length);
        setHardness(1.5f);
        setResistance(10.0f);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public ConfigOptionOreGen getConfig(int meta) {
        if (meta < 0 || meta >= EnumMeat.values().length)
            return null;
        return EnumMeat.byMetadata(meta).getConfig();
    }

    @Override
    public boolean isEnabled(int meta) {
        if (Config.disableMeatOres)
            return false;

        ConfigOptionOreGen config = getConfig(meta);
        return config != null && config.isEnabled();
    }

    @Override
    public void addOreDict() {

        for (EnumMeat meat : EnumMeat.values()) {
            ItemStack stack = new ItemStack(this, 1, meat.meta);
            if (!FunOres.registry.isItemDisabled(stack))
                OreDictionary.registerOre("ore" + meat.getName(), stack);
        }
    }

    @Override
    public int damageDropped(IBlockState state) {

        return state.getValue(MEAT).getMeta();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        Item item = Item.getItemFromBlock(this);
        for (EnumMeat meat : EnumMeat.values()) {
            ItemStack stack = new ItemStack(item, 1, meat.meta);
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(MEAT, EnumMeat.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MEAT).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MEAT);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Item drop = this.getItemDropped(world.getBlockState(pos), RANDOM, fortune);
        if (drop != Item.getItemFromBlock(this)) {
            return 1 + RANDOM.nextInt(3);
        }
        return 0;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

        // Spawn bats?
        if (state.getValue(MEAT) == EnumMeat.BAT && FunOres.random.nextFloat() < Config.spawnBatChance) {
            if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
                EntityBat entity = new EntityBat(world);
                entity.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY(),
                        (double) pos.getZ() + 0.5, 0.0f, 0.0f);
                world.spawnEntity(entity);
                entity.spawnExplosionParticle();
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (world instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) world;
            EnumMeat meat = state.getValue(MEAT);
            int tryCount = meat == EnumMeat.FISH ? 2 + FunOres.random.nextInt(3) : 1;
            ConfigOptionOreGenBonus config = state.getValue(MEAT).getConfig();
            drops.addAll(OreLootHelper.getDrops(worldServer, fortune, meat, tryCount, config));
        }
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0) {
            int j = random.nextInt(fortune + 2) - 1;

            if (j < 0) {
                j = 0;
            }

            return quantityDropped(random) * (j + 1);
        } else {
            return quantityDropped(random);
        }
    }
}
