package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenReplace;

public enum EnumVanillaOre implements IStringSerializable,IHasOre {

  IRON(0, "Iron", Blocks.IRON_ORE.getDefaultState()),
  GOLD(1, "Gold", Blocks.GOLD_ORE.getDefaultState()),
  DIAMOND(2, "Diamond", Blocks.DIAMOND_ORE.getDefaultState()),
  EMERALD(3, "Emerald", Blocks.EMERALD_ORE.getDefaultState()),
  COAL(4, "Coal", Blocks.COAL_ORE.getDefaultState()),
  REDSTONE(5, "Redstone", Blocks.REDSTONE_ORE.getDefaultState()),
  LAPIS(6, "Lapis", Blocks.LAPIS_ORE.getDefaultState()),
  QUARTZ(7, "Quartz", Blocks.QUARTZ_ORE.getDefaultState(), -1);

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
