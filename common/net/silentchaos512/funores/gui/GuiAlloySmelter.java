package net.silentchaos512.funores.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.inventory.ContainerAlloySmelter;
import net.silentchaos512.funores.tile.TileAlloySmelter;

public class GuiAlloySmelter extends GuiContainer {

  private static final ResourceLocation guiTextures = new ResourceLocation(FunOres.MOD_ID,
      "textures/gui/alloysmelter.png");
  private final InventoryPlayer playerInventory;
  private IInventory tileAlloySmelter;

  public GuiAlloySmelter(InventoryPlayer playerInventory, IInventory smelterInventory) {

    super(new ContainerAlloySmelter(playerInventory, smelterInventory));
    this.playerInventory = playerInventory;
    this.tileAlloySmelter = smelterInventory;
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(guiTextures);
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    int i1;

    if (TileEntityFurnace.isBurning(this.tileAlloySmelter)) {
      i1 = this.getBurnLeftScaled(13);
      this.drawTexturedModalRect(k + 20, l + 27 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
    }

    i1 = this.getCookProgressScaled(24);
    this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);

    if (FunOres.DEBUG_MODE)
      drawDebugInfo();
  }

  private int getCookProgressScaled(int pixels) {

    int j = this.tileAlloySmelter.getField(2);
    int k = this.tileAlloySmelter.getField(3);
    return k != 0 && j != 0 ? j * pixels / k : 0;
  }

  private int getBurnLeftScaled(int pixels) {

    int j = this.tileAlloySmelter.getField(1);

    if (j == 0) {
      j = 200;
    }

    return this.tileAlloySmelter.getField(0) * pixels / j;
  }

  private void drawDebugInfo() {

    if (!(tileAlloySmelter instanceof TileAlloySmelter)) {
      return;
    }

    TileAlloySmelter tile = (TileAlloySmelter) tileAlloySmelter;
    FontRenderer fontRender = mc.fontRendererObj;
    int x = 5;
    int y = 5;
    int yIncrement = 10;
    int color = 0xFFFFFF;

    GL11.glPushMatrix();
    float scale = 0.75f;
    GL11.glScalef(scale, scale, 1f);
    for (String str : tile.getDebugLines()) {
      fontRender.drawStringWithShadow(str, x, y, color);
      y += yIncrement;
    }
    GL11.glPopMatrix();
  }
}
