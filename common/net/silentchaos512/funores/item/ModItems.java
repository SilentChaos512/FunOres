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

    metalIngot = (MetalIngot) SRegistry.registerItem(MetalIngot.class, Names.METAL_INGOT);
    metalNugget = (MetalNugget) SRegistry.registerItem(MetalNugget.class, Names.METAL_NUGGET);
    metalDust = (MetalDust) SRegistry.registerItem(MetalDust.class, Names.METAL_DUST);
    alloyIngot = (AlloyIngot) SRegistry.registerItem(AlloyIngot.class, Names.ALLOY_INGOT);
    alloyNugget = (AlloyNugget) SRegistry.registerItem(AlloyNugget.class, Names.ALLOY_NUGGET);
    alloyDust = (AlloyDust) SRegistry.registerItem(AlloyDust.class, Names.ALLOY_DUST);
    plateBasic = (CraftingItem) SRegistry.registerItem(CraftingItem.class, "Metal" + Names.PLATE,
        Names.PLATE, false);
    plateAlloy = (CraftingItem) SRegistry.registerItem(CraftingItem.class, "Alloy" + Names.PLATE,
        Names.PLATE, true);
    gearBasic = (CraftingItem) SRegistry.registerItem(CraftingItem.class, "Metal" + Names.GEAR,
        Names.GEAR, false);
    gearAlloy = (CraftingItem) SRegistry.registerItem(CraftingItem.class, "Alloy" + Names.GEAR,
        Names.GEAR, true);
    shard = (Shard) SRegistry.registerItem(Shard.class, Names.SHARD);
    driedItem = (ItemDried) SRegistry.registerItem(ItemDried.class, Names.DRIED_ITEM);
    hammer = (ItemHammer) SRegistry.registerItem(ItemHammer.class, Names.HAMMER);

    SRegistry.registerItem(Sword.class, "SwordBronze", ModMaterials.toolBronze);
    SRegistry.registerItem(Pickaxe.class, "PickaxeBronze", ModMaterials.toolBronze);
    SRegistry.registerItem(Shovel.class, "ShovelBronze", ModMaterials.toolBronze);
    SRegistry.registerItem(Axe.class, "AxeBronze", ModMaterials.toolBronze);
    SRegistry.registerItem(Hoe.class, "HoeBronze", ModMaterials.toolBronze);

    SRegistry.registerItem(Sword.class, "SwordSteel", ModMaterials.toolSteel);
    SRegistry.registerItem(Pickaxe.class, "PickaxeSteel", ModMaterials.toolSteel);
    SRegistry.registerItem(Shovel.class, "ShovelSteel", ModMaterials.toolSteel);
    SRegistry.registerItem(Axe.class, "AxeSteel", ModMaterials.toolSteel);
    SRegistry.registerItem(Hoe.class, "HoeSteel", ModMaterials.toolSteel);
  }
}
