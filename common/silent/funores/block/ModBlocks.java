package silent.funores.block;

import silent.funores.core.registry.SRegistry;
import silent.funores.lib.Names;


public class ModBlocks {
  
  public static MeatOre meatOre;
  public static MetalBlock metalBlock;
  public static MetalOre metalOre;
  public static MobOre mobOre;

  public static void init() {
    
    meatOre = (MeatOre) SRegistry.registerBlock(MeatOre.class, Names.MEAT_ORE);
    metalOre = (MetalOre) SRegistry.registerBlock(MetalOre.class, Names.METAL_ORE);
    metalBlock = (MetalBlock) SRegistry.registerBlock(MetalBlock.class, Names.METAL_BLOCK);
    mobOre = (MobOre) SRegistry.registerBlock(MobOre.class, Names.MOB_ORE);
  }
}
