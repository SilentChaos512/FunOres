/*
 * Fun Ores -- FunOresRegistry
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

package net.silentchaos512.funores.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockFunOre;
import net.silentchaos512.funores.compat.jei.FunOresPlugin;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.lib.registry.SRegistry;
import net.silentchaos512.lib.util.LogHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Modified SRegistry that automatically loads configs for disableable (IDisableable) items.
 *
 * @author Silent
 */
public class FunOresRegistry extends SRegistry {
    public FunOresRegistry(String modId, LogHelper logHelper) {
        super(modId, logHelper);
    }

    /**
     * Set of disabled items (contains values returned by getStackKey)
     */
    private Set<String> disabledItems = new HashSet<>();

    private List<ItemStack> getSubItems(IDisableable disableable, Item item) {
        return disableable.getSubItems(item);
    }

    /**
     * Check that the item is disabled in the config file.
     */
    public boolean isItemDisabled(ItemStack stack) {
        return disabledItems.contains(getStackKey(stack));
    }

    /**
     * Gets a String to use for the disabledItems set.
     */
    private String getStackKey(ItemStack stack) {
        return stack.isEmpty() ? "null" : stack.getTranslationKey() + ":" + stack.getItemDamage();
    }

    @Override
    public <T extends Block> T registerBlock(T block, String key, ItemBlock itemBlock) {
        super.registerBlock(block, key, itemBlock);

        if (block instanceof IDisableable) {
            // General disableable blocks.
            List<ItemStack> list = getSubItems((IDisableable) block, itemBlock);
            for (ItemStack stack : list) {
                if (!Config.isItemDisabled(stack)) {
                    block.setCreativeTab(FunOres.tabFunOres);
                } else {
                    disabledItems.add(getStackKey(stack));
                    if (Loader.isModLoaded("jei"))
                        FunOresPlugin.disabledItems.add(stack);
                }
            }
        } else if (block instanceof BlockFunOre) {
            // Ores are not IDisableable, so we check the ore configs.
            BlockFunOre ore = (BlockFunOre) block;
            for (int i = 0; i < ore.maxMeta; ++i) {
                if (!ore.isEnabled(i)) {
                    ItemStack stack = new ItemStack(itemBlock, 1, i);
                    disabledItems.add(getStackKey(stack));
                    if (Loader.isModLoaded("jei"))
                        FunOresPlugin.disabledItems.add(stack);
                } else {
                    block.setCreativeTab(FunOres.tabFunOres);
                }
            }
        }

        return block;
    }

    @Override
    public <T extends Item> T registerItem(T item, String key) {
        if (item instanceof IDisableable) {
            List<ItemStack> list = getSubItems((IDisableable) item, item);
            for (ItemStack stack : list) {
                if (!Config.isItemDisabled(stack)) {
                    item.setCreativeTab(FunOres.tabFunOres);
                } else {
                    disabledItems.add(getStackKey(stack));
                    if (Loader.isModLoaded("jei"))
                        FunOresPlugin.disabledItems.add(stack);
                }
            }
        }

        return super.registerItem(item, key);
    }
}
