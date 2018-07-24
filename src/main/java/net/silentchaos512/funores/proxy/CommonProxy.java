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
    registerTileEntities(reg);
    registerRenderers();
  }

  public void registerTileEntities(SRegistry reg) {

    reg.registerTileEntity(TileMetalFurnace.class, Names.METAL_FURNACE);
    reg.registerTileEntity(TileAlloySmelter.class, Names.ALLOY_SMELTER);
    reg.registerTileEntity(TileDryingRack.class, Names.DRYING_RACK);
  }

  public void registerRenderers() {

  }

  public EntityPlayer getClientPlayer() {

    return null;
  }
}
