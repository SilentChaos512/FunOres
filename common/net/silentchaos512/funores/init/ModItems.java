package net.silentchaos512.funores.init;

import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.AlloyDust;
import net.silentchaos512.funores.item.AlloyIngot;
import net.silentchaos512.funores.item.AlloyNugget;
import net.silentchaos512.funores.item.CraftingItem;
import net.silentchaos512.funores.item.ItemDried;
import net.silentchaos512.funores.item.ItemHammer;
import net.silentchaos512.funores.item.MetalDust;
import net.silentchaos512.funores.item.MetalIngot;
import net.silentchaos512.funores.item.MetalNugget;
import net.silentchaos512.funores.item.Shard;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.SRegistry;

public class ModItems {

  public static MetalIngot metalIngot = new MetalIngot();
  public static MetalNugget metalNugget = new MetalNugget();
  public static MetalDust metalDust = new MetalDust();
  public static AlloyIngot alloyIngot = new AlloyIngot();
  public static AlloyNugget alloyNugget = new AlloyNugget();
  public static AlloyDust alloyDust = new AlloyDust();
  public static CraftingItem plateBasic = new CraftingItem(Names.PLATE, false);
  public static CraftingItem plateAlloy = new CraftingItem(Names.PLATE, true);
  public static CraftingItem gearBasic = new CraftingItem(Names.GEAR, false);
  public static CraftingItem gearAlloy = new CraftingItem(Names.GEAR, true);
  public static Shard shard = new Shard();
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
