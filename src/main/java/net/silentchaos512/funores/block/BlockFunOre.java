/*
 * Fun Ores -- BlockFunOre
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

import net.minecraft.block.material.Material;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.lib.block.BlockSL;

public abstract class BlockFunOre extends BlockSL {

    public final int maxMeta;

    public BlockFunOre(int subBlockCount, String name) {
        super(subBlockCount, FunOres.MOD_ID, name, Material.ROCK);
        this.maxMeta = subBlockCount;
    }

    public abstract ConfigOptionOreGen getConfig(int meta);

    public abstract boolean isEnabled(int meta);
}
