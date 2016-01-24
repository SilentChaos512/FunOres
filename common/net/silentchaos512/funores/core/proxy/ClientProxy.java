package net.silentchaos512.funores.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.silentchaos512.funores.core.registry.SRegistry;

public class ClientProxy extends CommonProxy {

  @Override
  public void preInit() {

    super.preInit();
    SRegistry.clientPreInit();
  }

  @Override
  public void init() {

    super.init();
    SRegistry.clientInit();
  }

  @Override
  public void postInit() {

    super.postInit();
  }

  @Override
  public EntityPlayer getClientPlayer() {

    return Minecraft.getMinecraft().thePlayer;
  }
}
