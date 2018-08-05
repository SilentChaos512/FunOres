/*
 * Fun Ores -- IMetal
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

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public interface IMetal extends IStringSerializable {

    int getMeta();

    String getMetalName();

    default String[] getMetalNames() {

        return new String[]{getMetalName()};
    }

    boolean isAlloy();

    ItemStack getBlock();

    ItemStack getIngot();

    ItemStack getNugget();

    ItemStack getDust();

    ItemStack getPlate();

    ItemStack getGear();
}
