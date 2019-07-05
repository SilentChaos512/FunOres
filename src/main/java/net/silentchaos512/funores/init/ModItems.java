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

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.ShardItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class ModItems {
    static Collection<BlockItem> blockItems = new ArrayList<>();

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        blockItems.forEach(ForgeRegistries.ITEMS::register);

        Arrays.stream(ShardItems.values()).forEach(shard -> register(shard.getName(), shard.asItem()));
    }

    private static void register(String name, Item item) {
        item.setRegistryName(FunOres.getId(name));
        ForgeRegistries.ITEMS.register(item);
    }
}
