package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;

public interface IHasOre extends IStringSerializable {

  public IBlockState getOre();

  public int getDimension();
}
