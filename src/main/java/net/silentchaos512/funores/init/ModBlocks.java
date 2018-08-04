/*
 * Fun Ores -- ModBlocks
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

import net.minecraft.block.Block;
import net.silentchaos512.funores.block.*;
import net.silentchaos512.funores.block.machine.BlockAlloySmelter;
import net.silentchaos512.funores.block.machine.BlockDryingRack;
import net.silentchaos512.funores.block.machine.BlockMetalFurnace;
import net.silentchaos512.funores.item.block.ItemBlockOre;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks implements IRegistrationHandler<Block> {
    public static BlockOreMetal metalOre = new BlockOreMetal();
    public static BlockOreMeat meatOre = new BlockOreMeat();
    public static BlockOreMob mobOre = new BlockOreMob();
    public static BlockMetal metalBlock = new BlockMetal();
    public static BlockAlloy alloyBlock = new BlockAlloy();
    public static BlockMetalFurnace metalFurnace = new BlockMetalFurnace();
    public static BlockAlloySmelter alloySmelter = new BlockAlloySmelter();
    public static BlockDryingRack dryingRack = new BlockDryingRack();

    @Override
    public void registerAll(SRegistry reg) {
        reg.registerBlock(metalOre, "metalore", new ItemBlockOre(metalOre));
        reg.registerBlock(meatOre, "meatore", new ItemBlockOre(meatOre));
        reg.registerBlock(mobOre, "mobore", new ItemBlockOre(mobOre));
        reg.registerBlock(metalBlock, "metalblock");
        reg.registerBlock(alloyBlock, "alloyblock");
        reg.registerBlock(metalFurnace, "metalfurnace");
        reg.registerBlock(alloySmelter, "alloysmelter");
        reg.registerBlock(dryingRack, "dryingrack");
    }
}
