/*
 * Fun Ores -- GuiMetalFurnace
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.inventory.ContainerMetalFurnace;
import net.silentchaos512.funores.tile.TileMetalFurnace;
import net.silentchaos512.lib.SilentLib;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMetalFurnace extends GuiContainer {

    private static final ResourceLocation guiTextures = new ResourceLocation(FunOres.MOD_ID, "textures/gui/metalfurnace.png");
    private IInventory tileFurnace;

    public GuiMetalFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {
        super(new ContainerMetalFurnace(playerInventory, furnaceInventory));
        this.tileFurnace = furnaceInventory;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (SilentLib.getMCVersion() < 12) {
            super.drawScreen(mouseX, mouseY, partialTicks);
        } else {
            this.drawDefaultBackground();
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.renderHoveredToolTip(mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (TileEntityFurnace.isBurning(this.tileFurnace)) {
            i1 = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
        }

        i1 = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);

        if (FunOres.DEBUG_MODE)
            drawDebugInfo();
    }

    private int getCookProgressScaled(int pixels) {
        int j = this.tileFurnace.getField(2);
        int k = this.tileFurnace.getField(3);
        return k != 0 && j != 0 ? j * pixels / k : 0;
    }

    private int getBurnLeftScaled(int pixels) {
        int j = this.tileFurnace.getField(1);

        if (j == 0) {
            j = 200;
        }

        return this.tileFurnace.getField(0) * pixels / j;
    }

    private void drawDebugInfo() {
        if (!(tileFurnace instanceof TileMetalFurnace)) {
            return;
        }

        TileMetalFurnace tile = (TileMetalFurnace) tileFurnace;
        FontRenderer fontRender = mc.fontRenderer;
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
