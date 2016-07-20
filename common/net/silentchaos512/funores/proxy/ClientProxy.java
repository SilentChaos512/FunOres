package net.silentchaos512.funores.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.client.render.TileDryingRackRender;
import net.silentchaos512.funores.init.ModFluids;
import net.silentchaos512.funores.tile.TileDryingRack;
import net.silentchaos512.lib.registry.SRegistry;

public class ClientProxy extends CommonProxy {

  @Override
  public void preInit(SRegistry reg) {

    super.preInit(reg);
    reg.clientPreInit();
    ModFluids.bakeModels();
  }

  @Override
  public void init(SRegistry reg) {

    super.init(reg);
    reg.clientInit();
  }

  @Override
  public void postInit(SRegistry reg) {

    super.postInit(reg);
  }

  @Override
  public void registerRenderers() {

    ClientRegistry.bindTileEntitySpecialRenderer(TileDryingRack.class, new TileDryingRackRender());
  }

  @Override
  public EntityPlayer getClientPlayer() {

    return Minecraft.getMinecraft().thePlayer;
  }
}
