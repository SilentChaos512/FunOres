/*
 * Fun Ores -- ClientProxy
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

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.silentchaos512.funores.client.render.TileDryingRackRender;
import net.silentchaos512.funores.init.ModFluids;
import net.silentchaos512.funores.tile.TileDryingRack;
import net.silentchaos512.lib.registry.SRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(SRegistry reg) {
        super.preInit(reg);
        reg.clientPreInit();
        ModFluids.bakeModels();
    }

    @Override
    public void init(SRegistry reg) {
        super.init(reg);
        reg.clientInit();
    }

    @Override
    public void postInit(SRegistry reg) {
        super.postInit(reg);
        reg.clientPostInit();
    }

    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileDryingRack.class, new TileDryingRackRender());
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
}
