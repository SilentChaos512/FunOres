package net.silentchaos512.funores.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.inventory.ContainerAlloySmelter;
import net.silentchaos512.funores.inventory.ContainerMetalFurnace;
import net.silentchaos512.funores.tile.TileAlloySmelter;
import net.silentchaos512.funores.tile.TileMetalFurnace;


public class GuiHandlerFunOres implements IGuiHandler {

  public static final int ID_METAL_FURNACE = 0;
  public static final int ID_ALLOY_SMELTER = 1;

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

    BlockPos pos = new BlockPos(x, y, z);
    TileEntity tile = world.getTileEntity(pos);
    
    switch (ID) {
      case ID_METAL_FURNACE:
        if (tile instanceof TileMetalFurnace) {
          TileMetalFurnace tileFurnace = (TileMetalFurnace) tile;
          return new ContainerMetalFurnace(player.inventory, tileFurnace);
        }
        return null;
      case ID_ALLOY_SMELTER:
        if (tile instanceof TileAlloySmelter) {
          TileAlloySmelter tileSmelter = (TileAlloySmelter) tile;
          return new ContainerAlloySmelter(player.inventory, tileSmelter);
        }
        return null;
      default:
        LogHelper.warning("No GUI with ID " + ID + "!");
        return null;
    }
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

    BlockPos pos = new BlockPos(x, y, z);
    TileEntity tile = world.getTileEntity(pos);
    
    switch (ID) {
      case ID_METAL_FURNACE:
        if (tile instanceof TileMetalFurnace) {
          TileMetalFurnace tileFurnace = (TileMetalFurnace) tile;
          return new GuiMetalFurnace(player.inventory, tileFurnace);
        }
        return null;
      case ID_ALLOY_SMELTER:
        if (tile instanceof TileAlloySmelter) {
          TileAlloySmelter tileSmelter = (TileAlloySmelter) tile;
          return new GuiAlloySmelter(player.inventory, tileSmelter);
        }
        return null;
      default:
        LogHelper.warning("No GUI with ID " + ID + "!");
        return null;
    }
  }

}
