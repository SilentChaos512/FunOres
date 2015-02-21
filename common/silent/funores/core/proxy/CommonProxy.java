package silent.funores.core.proxy;

import net.minecraftforge.fml.common.registry.GameRegistry;
import silent.funores.lib.Names;
import silent.funores.tile.TileMetalFurnace;


public class CommonProxy {

  public CommonProxy() {
    
  }
  
  public void preInit() {
    
  }
  
  public void init() {
    
    registerTileEntities();
  }
  
  public void postInit() {
    
  }
  
  public void registerTileEntities() {

    String prefix = "tile.funores:";
    GameRegistry.registerTileEntity(TileMetalFurnace.class, prefix + Names.METAL_FURNACE);
  }
}
