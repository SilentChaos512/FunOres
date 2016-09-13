package net.silentchaos512.funores.init;

import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.ItemDustAlloy;
import net.silentchaos512.funores.item.ItemIngotAlloy;
import net.silentchaos512.funores.item.ItemNuggetAlloy;
import net.silentchaos512.funores.item.ItemCraftingItem;
import net.silentchaos512.funores.item.ItemDried;
import net.silentchaos512.funores.item.ItemHammer;
import net.silentchaos512.funores.item.ItemDustMetal;
import net.silentchaos512.funores.item.ItemIngotMetal;
import net.silentchaos512.funores.item.ItemNuggetMetal;
import net.silentchaos512.funores.item.ItemShard;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.SRegistry;

public class ModItems {

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

  public static void init() {

    SRegistry reg = FunOres.instance.registry;
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
