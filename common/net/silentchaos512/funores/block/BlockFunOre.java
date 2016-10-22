package net.silentchaos512.funores.block;

import net.minecraft.block.material.Material;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.lib.block.BlockSL;

public abstract class BlockFunOre extends BlockSL {

  public final int maxMeta;

  public BlockFunOre(int subBlockCount, String name) {

    super(subBlockCount, FunOres.MOD_ID, name, Material.ROCK);
    this.maxMeta = subBlockCount;
  }

  public abstract ConfigOptionOreGen getConfig(int meta);

  public abstract boolean isEnabled(int meta);
}
