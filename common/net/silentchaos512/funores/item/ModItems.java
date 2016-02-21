package net.silentchaos512.funores.item;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.silentchaos512.funores.core.registry.SRegistry;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.material.ModMaterials;

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

    metalIngot = (MetalIngot) SRegistry.registerItem(new MetalIngot(), Names.METAL_INGOT);
    metalNugget = (MetalNugget) SRegistry.registerItem(new MetalNugget(), Names.METAL_NUGGET);
    metalDust = (MetalDust) SRegistry.registerItem(new MetalDust(), Names.METAL_DUST);
    alloyIngot = (AlloyIngot) SRegistry.registerItem(new AlloyIngot(), Names.ALLOY_INGOT);
    alloyNugget = (AlloyNugget) SRegistry.registerItem(new AlloyNugget(), Names.ALLOY_NUGGET);
    alloyDust = (AlloyDust) SRegistry.registerItem(new AlloyDust(), Names.ALLOY_DUST);
    plateBasic = (CraftingItem) SRegistry.registerItem(new CraftingItem(Names.PLATE, false), "Metal" + Names.PLATE);
    plateAlloy = (CraftingItem) SRegistry.registerItem(new CraftingItem(Names.PLATE, true), "Alloy" + Names.PLATE);
    gearBasic = (CraftingItem) SRegistry.registerItem(new CraftingItem(Names.GEAR, false), "Metal" + Names.GEAR);
    gearAlloy = (CraftingItem) SRegistry.registerItem(new CraftingItem(Names.GEAR, true), "Alloy" + Names.GEAR);
    shard = (Shard) SRegistry.registerItem(new Shard(), Names.SHARD);
    driedItem = (ItemDried) SRegistry.registerItem(new ItemDried(), Names.DRIED_ITEM);
    hammer = (ItemHammer) SRegistry.registerItem(new ItemHammer(), Names.HAMMER);

    SRegistry.registerItem(new Sword(ModMaterials.toolBronze), "SwordBronze");
    SRegistry.registerItem(new Pickaxe(ModMaterials.toolBronze), "PickaxeBronze");
    SRegistry.registerItem(new Shovel(ModMaterials.toolBronze), "ShovelBronze");
    SRegistry.registerItem(new Axe(ModMaterials.toolBronze), "AxeBronze");
    SRegistry.registerItem(new Hoe(ModMaterials.toolBronze), "HoeBronze");

    SRegistry.registerItem(new Sword(ModMaterials.toolSteel), "SwordSteel");
    SRegistry.registerItem(new Pickaxe(ModMaterials.toolSteel), "PickaxeSteel");
    SRegistry.registerItem(new Shovel(ModMaterials.toolSteel), "ShovelSteel");
    SRegistry.registerItem(new Axe(ModMaterials.toolSteel), "AxeSteel");
    SRegistry.registerItem(new Hoe(ModMaterials.toolSteel), "HoeSteel");
  }
}
