package net.silentchaos512.funores.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.tile.TileAlloySmelter;
import net.silentchaos512.funores.tile.TileDryingRack;
import net.silentchaos512.funores.tile.TileMetalFurnace;
import net.silentchaos512.lib.registry.SRegistry;

public class CommonProxy extends net.silentchaos512.lib.proxy.CommonProxy {

  @Override
  public void init(SRegistry reg) {

    super.init(reg);
    registerTileEntities();
    registerRenderers();
  }

  public void registerTileEntities() {

    String prefix = "tile.funores:";
    GameRegistry.registerTileEntity(TileMetalFurnace.class, prefix + Names.METAL_FURNACE);
    GameRegistry.registerTileEntity(TileAlloySmelter.class, prefix + Names.ALLOY_SMELTER);
    GameRegistry.registerTileEntity(TileDryingRack.class, prefix + Names.DRYING_RACK);
  }

  public void registerRenderers() {

  }

  public EntityPlayer getClientPlayer() {

    return null;
  }
}
