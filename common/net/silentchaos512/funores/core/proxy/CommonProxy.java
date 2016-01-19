package net.silentchaos512.funores.core.proxy;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileAlloySmelter;
import net.silentchaos512.funores.tile.TileMetalFurnace;


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
    GameRegistry.registerTileEntity(TileAlloySmelter.class, prefix + Names.ALLOY_SMELTER);
  }
}
