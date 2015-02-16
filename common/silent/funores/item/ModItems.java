package silent.funores.item;

import silent.funores.core.registry.SRegistry;
import silent.funores.lib.Names;

public class ModItems {
  
  public static MetalIngot metalIngot;
  public static MetalNugget metalNugget;
  public static Shard shard;

  public static void init() {
    
    metalIngot = (MetalIngot) SRegistry.registerItem(MetalIngot.class, Names.METAL_INGOT);
    metalNugget = (MetalNugget) SRegistry.registerItem(MetalNugget.class, Names.METAL_NUGGET);
    shard = (Shard) SRegistry.registerItem(Shard.class, Names.SHARD);
  }
}
