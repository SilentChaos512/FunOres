/*
 * Fun Ores -- FunOres
 * Copyright (C) 2018-2019 SilentChaos512
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

package net.silentchaos512.funores;

import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Random;

@Mod(FunOres.MOD_ID)
public class FunOres {
    public static final String MOD_ID = "funores";
    public static final String MOD_NAME = "Fun Ores";
    public static final String VERSION = "2.0.1";
    public static final boolean RUN_GENERATORS = true;
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase(Locale.ROOT) + ":";

    public static FunOres INSTANCE;
    private static SideProxy PROXY;

    public static Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static Random RANDOM = new Random();

//    public static CreativeTabs tabFunOres = registry.makeCreativeTab("tabFunOres", () ->
//            new ItemStack(ModBlocks.meatOre, 1, random.nextInt(EnumMeat.values().length)));

    public FunOres() {
        INSTANCE = this;
        PROXY = DistExecutor.runForDist(() -> () -> new SideProxy.Client(), () -> () -> new SideProxy.Server());
    }
}
