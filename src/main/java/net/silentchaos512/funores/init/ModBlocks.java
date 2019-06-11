/*
 * Fun Ores -- ModBlocks
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

package net.silentchaos512.funores.init;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Ores;
import net.silentchaos512.funores.util.ModelGenerator;

public final class ModBlocks {
    private ModBlocks() {}

    public static void registerAll(RegistryEvent.Register<Block> event) {
        if (!event.getName().equals(ForgeRegistries.BLOCKS.getRegistryName())) return;

        for (Ores ore : Ores.values()) {
            register(ore.getBlockName(), ore.asBlock());
        }
    }

    private static void register(String name, Block block) {
        ResourceLocation registryName = new ResourceLocation(FunOres.MOD_ID, name);
        block.setRegistryName(registryName);
        ForgeRegistries.BLOCKS.register(block);

        BlockItem item = new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
        item.setRegistryName(registryName);
        ModItems.blockItems.add(item);

        if (FunOres.RUN_GENERATORS && FunOres.isDevBuild()) {
            ModelGenerator.createFor(block);
        }
    }
}
