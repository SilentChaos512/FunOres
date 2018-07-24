/*
 * Fun Ores -- ModItems
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

import net.minecraft.item.Item;
import net.silentchaos512.funores.item.*;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModItems implements IRegistrationHandler<Item> {
    public static ItemIngotMetal metalIngot = new ItemIngotMetal();
    public static ItemNuggetMetal metalNugget = new ItemNuggetMetal();
    public static ItemDustMetal metalDust = new ItemDustMetal();
    public static ItemIngotAlloy alloyIngot = new ItemIngotAlloy();
    public static ItemNuggetAlloy alloyNugget = new ItemNuggetAlloy();
    public static ItemDustAlloy alloyDust = new ItemDustAlloy();
    public static ItemCraftingItem plateBasic = new ItemCraftingItem(Names.PLATE, false);
    public static ItemCraftingItem plateAlloy = new ItemCraftingItem(Names.PLATE, true);
    public static ItemCraftingItem gearBasic = new ItemCraftingItem(Names.GEAR, false);
    public static ItemCraftingItem gearAlloy = new ItemCraftingItem(Names.GEAR, true);
    public static ItemShard shard = new ItemShard();
    public static ItemDried driedItem = new ItemDried();
    public static ItemHammer hammer = new ItemHammer();

    @Override
    public void registerAll(SRegistry reg) {
        reg.registerItem(metalIngot);
        reg.registerItem(metalNugget);
        reg.registerItem(metalDust);
        reg.registerItem(alloyIngot);
        reg.registerItem(alloyNugget);
        reg.registerItem(alloyDust);
        reg.registerItem(plateBasic, "Metal" + Names.PLATE);
        reg.registerItem(plateAlloy, "Alloy" + Names.PLATE);
        reg.registerItem(gearBasic, "Metal" + Names.GEAR);
        reg.registerItem(gearAlloy, "Alloy" + Names.GEAR);
        reg.registerItem(shard);
        reg.registerItem(driedItem, Names.DRIED_ITEM);
        reg.registerItem(hammer);
    }
}
