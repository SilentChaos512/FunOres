package net.silentchaos512.funores.lib;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public enum EnumMachineState implements IStringSerializable {

  NORTH_OFF(2, "north_off", false),
  SOUTH_OFF(3, "south_off", false),
  WEST_OFF(4, "west_off", false),
  EAST_OFF(5, "east_off", false),
  NORTH_ON(10, "north_on", true),
  SOUTH_ON(11, "south_on", true),
  WEST_ON(12, "west_on", true),
  EAST_ON(13, "east_on", true);

  public final int meta;
  public final String name;
  public final boolean isOn;

  private EnumMachineState(int meta, String name, boolean isOn) {

    this.meta = meta;
    this.name = name;
    this.isOn = isOn;
  }

  @Override
  public String getName() {

    return name;
  }

  public static EnumMachineState fromEnumFacing(EnumFacing facing) {

    switch (facing) {
      case EAST:
        return EAST_OFF;
      case NORTH:
        return NORTH_OFF;
      case SOUTH:
        return SOUTH_OFF;
      case WEST:
        return WEST_OFF;
      default:
        return null;
      
    }
  }

  public static EnumMachineState fromMeta(int meta) {

    for (EnumMachineState state : values()) {
      if (state.meta == meta) {
        return state;
      }
    }
    return null;
  }
}
