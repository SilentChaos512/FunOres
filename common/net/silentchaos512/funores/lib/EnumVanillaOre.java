package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenReplace;

public enum EnumVanillaOre implements IStringSerializable,IHasOre {

  IRON(0, "Iron", Blocks.iron_ore.getDefaultState()),
  GOLD(1, "Gold", Blocks.gold_ore.getDefaultState()),
  DIAMOND(2, "Diamond", Blocks.diamond_ore.getDefaultState()),
  EMERALD(3, "Emerald", Blocks.emerald_ore.getDefaultState()),
  COAL(4, "Coal", Blocks.coal_ore.getDefaultState()),
  REDSTONE(5, "Redstone", Blocks.redstone_ore.getDefaultState()),
  LAPIS(6, "Lapis", Blocks.lapis_ore.getDefaultState()),
  QUARTZ(7, "Quartz", Blocks.quartz_ore.getDefaultState(), -1);

  public final int meta;
  public final String name;
  public final IBlockState blockState;
  public final int dimension;

  private EnumVanillaOre(int meta, String name, IBlockState blockState) {

    this(meta, name, blockState, 0);
  }

  private EnumVanillaOre(int meta, String name, IBlockState blockState, int dimension) {

    this.meta = meta;
    this.name = name;
    this.blockState = blockState;
    this.dimension = dimension;
  }

  @Override
  public String getName() {

    return name;
  }

  @Override
  public IBlockState getOre() {

    return blockState;
  }

  @Override
  public int getDimension() {

    return dimension;
  }

  public ConfigOptionOreGenReplace getConfig() {

    switch (this) {
      case COAL:
        return Config.coal;
      case DIAMOND:
        return Config.diamond;
      case EMERALD:
        return Config.emerald;
      case GOLD:
        return Config.gold;
      case IRON:
        return Config.iron;
      case LAPIS:
        return Config.lapis;
      case QUARTZ:
        return Config.quartz;
      case REDSTONE:
        return Config.redstone;
      default:
        FunOres.instance.logHelper.warning("Don't know config for vanilla ore: " + name);
        return null;
    }
  }
}
