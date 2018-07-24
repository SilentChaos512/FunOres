/*
 * Fun Ores -- IDisableable
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

package net.silentchaos512.funores.lib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Marks a block/item that the user can disable in the config file.
 */
public interface IDisableable {
    /**
     * Get a list of all possible items, including disabled ones! The proper getSubBlocks and
     * getSubItems methods inherited from Block/Item should reference this, but filter out disabled
     * items.
     */
    List<ItemStack> getSubItems(Item item);
}
