/*
 * Fun Ores -- ModItems
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

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.silentchaos512.funores.FunOres;

import java.util.ArrayList;
import java.util.List;

public final class ModItems {
    static List<ItemBlock> itemBlocks = new ArrayList<>();

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        final Item.Builder properties = new Item.Builder().group(ItemGroup.MATERIALS);
        register(registry, "ender_shard", new Item(properties));
        register(registry, "blaze_shard", new Item(properties));
        register(registry, "ghast_shard", new Item(properties));
    }

    private static void register(IForgeRegistry<Item> registry, String name, Item item) {
        item.setRegistryName(new ResourceLocation(FunOres.MOD_ID, name));
        registry.register(item);
    }
}
