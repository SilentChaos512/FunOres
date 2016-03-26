package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.MobOre;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.lib.util.LogHelper;

public enum EnumMob implements IStringSerializable,IHasOre {

  ZOMBIE(0, "Zombie"),
  SKELETON(1, "Skeleton"),
  CREEPER(2, "Creeper"),
  SPIDER(3, "Spider"),
  ENDERMAN(4, "Enderman"),
  SLIME(5, "Slime"),
  WITCH(6, "Witch"),
  PIGMAN(7, "Pigman", -1),
  GHAST(8, "Ghast", -1),
  MAGMA_CUBE(9, "MagmaCube", -1),
  WITHER(10, "Wither", -1),
  BLAZE(11, "Blaze", -1),
  GUARDIAN(12, "Guardian");

  public final int meta;
  public final String name;
  public final int dimension;

  private EnumMob(int meta, String name) {

    this(meta, name, 0);
  }

  private EnumMob(int meta, String name, int dimension) {

    this.meta = meta;
    this.name = name;
    this.dimension = dimension;
  }

  public int getMeta() {

    return meta;
  }

  @Override
  public String getName() {

    return name.toLowerCase();
  }

  @Override
  public IBlockState getOre() {

    return ModBlocks.mobOre.getDefaultState().withProperty(MobOre.MOB, this);
  }

  @Override
  public int getDimension() {

    return dimension;
  }

  public ConfigOptionOreGenBonus getConfig() {

    switch (meta) {
      //@formatter:off
      case 0: return Config.zombie;
      case 1: return Config.skeleton;
      case 2: return Config.creeper;
      case 3: return Config.spider;
      case 4: return Config.enderman;
      case 5: return Config.slime;
      case 6: return Config.witch;
      case 7: return Config.pigman;
      case 8: return Config.ghast;
      case 9: return Config.magmaCube;
      case 10: return Config.wither;
      case 11: return Config.blaze;
      case 12: return Config.guardian;
      //@formatter:on
      default:
        FunOres.instance.logHelper.severe("Don't know config for ore " + name + "!");
        return null;
    }
  }

  public static EnumMob byMetadata(int meta) {

    if (meta <= 0 || meta >= values().length) {
      meta = 0;
    }
    return values()[meta];
  }

  public static int count() {

    return values().length;
  }
}
