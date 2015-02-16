package silent.funores.lib;

import silent.funores.block.MobOre;
import silent.funores.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;


public enum EnumMob implements IStringSerializable, IHasOre {
  
  ZOMBIE(0, "Zombie"),
  SKELETON(1, "Skeleton"),
  CREEPER(2, "Creeper"),
  SPIDER(3, "Spider"),
  ENDERMAN(4, "Enderman"),
  SLIME(5, "Slime"),
  WITCH(6, "Witch"),
  PIGMAN(7, "Pigman"),
  GHAST(8, "Ghast"),
  MAGMA_CUBE(9, "MagmaCube"),
  WITHER(10, "Wither");
  
  private final int meta;
  private final String name;
  
  private EnumMob(int meta, String name) {
    
    this.meta = meta;
    this.name = name;
  }
  
  public int getMeta() {
    
    return meta;
  }

  @Override
  public String getName() {

    return name;
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

  @Override
  public IBlockState getOre() {

    return ModBlocks.mobOre.getDefaultState().withProperty(MobOre.MOB, this);
  }
}
