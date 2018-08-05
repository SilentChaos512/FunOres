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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.funores.init.ModFluids;
import net.silentchaos512.lib.registry.SRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(SRegistry reg, FMLPreInitializationEvent event) {
        super.preInit(reg, event);
        reg.clientPreInit(event);
        ModFluids.bakeModels();
    }

    @Override
    public void init(SRegistry reg, FMLInitializationEvent event) {
        super.init(reg, event);
        reg.clientInit(event);
    }

    @Override
    public void postInit(SRegistry reg, FMLPostInitializationEvent event) {
        super.postInit(reg, event);
        reg.clientPostInit(event);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    public int getParticleSettings() {
        return Minecraft.getMinecraft().gameSettings.particleSetting;
    }
}
