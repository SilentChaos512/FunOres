package net.silentchaos512.funores.block;

import net.silentchaos512.funores.core.registry.SRegistry;
import net.silentchaos512.funores.item.block.ItemBlockOreDrops;
import net.silentchaos512.funores.lib.Names;

public class ModBlocks {

  public static MetalOre metalOre;
  public static MeatOre meatOre;
  public static MobOre mobOre;
  public static MetalBlock metalBlock;
  public static AlloyBlock alloyBlock;
  public static MetalFurnace metalFurnace;

  public static void init() {

    metalOre = (MetalOre) SRegistry.registerBlock(MetalOre.class, Names.METAL_ORE);
    meatOre = (MeatOre) SRegistry.registerBlock(MeatOre.class, Names.MEAT_ORE, ItemBlockOreDrops.class);
    mobOre = (MobOre) SRegistry.registerBlock(MobOre.class, Names.MOB_ORE, ItemBlockOreDrops.class);
    metalBlock = (MetalBlock) SRegistry.registerBlock(MetalBlock.class, Names.METAL_BLOCK);
    alloyBlock = (AlloyBlock) SRegistry.registerBlock(AlloyBlock.class, Names.ALLOY_BLOCK);
    metalFurnace = (MetalFurnace) SRegistry.registerBlock(MetalFurnace.class, Names.METAL_FURNACE);
  }
}
