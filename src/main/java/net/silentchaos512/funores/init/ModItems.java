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
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModItems implements IRegistrationHandler<Item> {
    public static ItemIngotMetal metalIngot = new ItemIngotMetal();
    public static ItemNuggetMetal metalNugget = new ItemNuggetMetal();
    public static ItemDustMetal metalDust = new ItemDustMetal();
    public static ItemIngotAlloy alloyIngot = new ItemIngotAlloy();
    public static ItemNuggetAlloy alloyNugget = new ItemNuggetAlloy();
    public static ItemDustAlloy alloyDust = new ItemDustAlloy();
    public static ItemCraftingItem plateBasic = new ItemCraftingItem("plate", false);
    public static ItemCraftingItem plateAlloy = new ItemCraftingItem("plate", true);
    public static ItemCraftingItem gearBasic = new ItemCraftingItem("gear", false);
    public static ItemCraftingItem gearAlloy = new ItemCraftingItem("gear", true);
    public static ItemShard shard = new ItemShard();
    public static ItemDried driedItem = new ItemDried();
    public static ItemHammer hammer = new ItemHammer();

    @Override
    public void registerAll(SRegistry reg) {
        reg.registerItem(metalIngot, "metalingot");
        reg.registerItem(metalNugget, "metalnugget");
        reg.registerItem(metalDust, "metaldust");
        reg.registerItem(alloyIngot, "alloyingot");
        reg.registerItem(alloyNugget, "alloynugget");
        reg.registerItem(alloyDust, "alloydust");
        reg.registerItem(plateBasic, "metalplate");
        reg.registerItem(plateAlloy, "alloyplate");
        reg.registerItem(gearBasic, "metalgear");
        reg.registerItem(gearAlloy, "alloygear");
        reg.registerItem(shard, "shard");
        reg.registerItem(driedItem, "drieditem");
        reg.registerItem(hammer, "hammer");
    }
}
