/*
 * Fun Ores -- CommonProxy
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

package net.silentchaos512.funores.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileAlloySmelter;
import net.silentchaos512.funores.tile.TileDryingRack;
import net.silentchaos512.funores.tile.TileMetalFurnace;
import net.silentchaos512.lib.registry.SRegistry;

public class CommonProxy extends net.silentchaos512.lib.proxy.CommonProxy {

    @Override
    public void init(SRegistry reg) {
        super.init(reg);
        registerTileEntities(reg);
        registerRenderers();
    }

    public void registerTileEntities(SRegistry reg) {
        reg.registerTileEntity(TileMetalFurnace.class, Names.METAL_FURNACE);
        reg.registerTileEntity(TileAlloySmelter.class, Names.ALLOY_SMELTER);
        reg.registerTileEntity(TileDryingRack.class, Names.DRYING_RACK);
    }

    public void registerRenderers() {
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }
}
