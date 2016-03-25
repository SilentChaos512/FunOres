package net.silentchaos512.funores.item;

import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.material.ModMaterials;
import net.silentchaos512.lib.registry.SRegistry;

public class ModItems {

  public static MetalIngot metalIngot;
  public static MetalNugget metalNugget;
  public static MetalDust metalDust;
  public static AlloyIngot alloyIngot;
  public static AlloyNugget alloyNugget;
  public static AlloyDust alloyDust;
  public static CraftingItem plateBasic;
  public static CraftingItem plateAlloy;
  public static CraftingItem gearBasic;
  public static CraftingItem gearAlloy;
  public static Shard shard;
  public static ItemDried driedItem;
  public static ItemHammer hammer;

  public static void init() {

    SRegistry reg = FunOres.instance.registry;
    metalIngot = (MetalIngot) reg.registerItem(new MetalIngot());
    metalNugget = (MetalNugget) reg.registerItem(new MetalNugget());
    metalDust = (MetalDust) reg.registerItem(new MetalDust());
    alloyIngot = (AlloyIngot) reg.registerItem(new AlloyIngot());
    alloyNugget = (AlloyNugget) reg.registerItem(new AlloyNugget());
    alloyDust = (AlloyDust) reg.registerItem(new AlloyDust());
    plateBasic = (CraftingItem) reg.registerItem(new CraftingItem(Names.PLATE, false), "Metal" + Names.PLATE);
    plateAlloy = (CraftingItem) reg.registerItem(new CraftingItem(Names.PLATE, true), "Alloy" + Names.PLATE);
    gearBasic = (CraftingItem) reg.registerItem(new CraftingItem(Names.GEAR, false), "Metal" + Names.GEAR);
    gearAlloy = (CraftingItem) reg.registerItem(new CraftingItem(Names.GEAR, true), "Alloy" + Names.GEAR);
    shard = (Shard) reg.registerItem(new Shard());
    driedItem = (ItemDried) reg.registerItem(new ItemDried(), Names.DRIED_ITEM);
    hammer = (ItemHammer) reg.registerItem(new ItemHammer());

//    SRegistry.registerItem(new Sword(ModMaterials.toolBronze), "SwordBronze");
//    SRegistry.registerItem(new Pickaxe(ModMaterials.toolBronze), "PickaxeBronze");
//    SRegistry.registerItem(new Shovel(ModMaterials.toolBronze), "ShovelBronze");
//    SRegistry.registerItem(new Axe(ModMaterials.toolBronze), "AxeBronze");
//    SRegistry.registerItem(new Hoe(ModMaterials.toolBronze), "HoeBronze");
//
//    SRegistry.registerItem(new Sword(ModMaterials.toolSteel), "SwordSteel");
//    SRegistry.registerItem(new Pickaxe(ModMaterials.toolSteel), "PickaxeSteel");
//    SRegistry.registerItem(new Shovel(ModMaterials.toolSteel), "ShovelSteel");
//    SRegistry.registerItem(new Axe(ModMaterials.toolSteel), "AxeSteel");
//    SRegistry.registerItem(new Hoe(ModMaterials.toolSteel), "HoeSteel");
  }
}
