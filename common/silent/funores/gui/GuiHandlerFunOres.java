package silent.funores.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import silent.funores.core.util.LogHelper;
import silent.funores.inventory.ContainerMetalFurnace;
import silent.funores.tile.TileMetalFurnace;


public class GuiHandlerFunOres implements IGuiHandler {

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

    BlockPos pos = new BlockPos(x, y, z);
    TileEntity tile = world.getTileEntity(pos);
    
    switch (ID) {
      case 0:
        if (tile instanceof TileMetalFurnace) {
          TileMetalFurnace tileFurnace = (TileMetalFurnace) tile;
          return new ContainerMetalFurnace(player.inventory, tileFurnace);
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
      case 0:
        if (tile instanceof TileMetalFurnace) {
          TileMetalFurnace tileFurnace = (TileMetalFurnace) tile;
          return new GuiMetalFurnace(player.inventory, tileFurnace);
        }
        return null;
      default:
        LogHelper.warning("No GUI with ID " + ID + "!");
        return null;
    }
  }

}
