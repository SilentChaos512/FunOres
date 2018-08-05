/*
 * Fun Ores -- ItemNuggetAlloy
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

package net.silentchaos512.funores.item;

import net.minecraft.item.Item;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IMetal;

import java.util.Arrays;
import java.util.List;

public class ItemNuggetAlloy extends ItemBaseMetal {

    public ItemNuggetAlloy() {
        super("nugget", "nugget");
    }

    @Override
    public List<IMetal> getMetals(Item item) {
//        List<IMetal> list = new ArrayList<>(Arrays.asList(EnumAlloy.values()));
//        return list; // Build fails if not assigned to a variable?
        return Arrays.asList(EnumAlloy.values());
    }
}
