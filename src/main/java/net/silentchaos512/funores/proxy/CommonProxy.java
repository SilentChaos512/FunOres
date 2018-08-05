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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.silentchaos512.lib.proxy.IProxy;
import net.silentchaos512.lib.registry.SRegistry;

public class CommonProxy implements IProxy {
    @Override
    public void preInit(SRegistry registry, FMLPreInitializationEvent event) {
        registry.preInit(event);
    }

    @Override
    public void init(SRegistry registry, FMLInitializationEvent event) {
        registry.init(event);
    }

    @Override
    public void postInit(SRegistry registry, FMLPostInitializationEvent event) {
        registry.postInit(event);
    }

    public EntityPlayer getClientPlayer() {
        return null;
    }

    @Override
    public int getParticleSettings() {
        return 0;
    }
}
