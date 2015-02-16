package silent.funores.lib;

import silent.funores.block.MeatOre;
import silent.funores.block.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.IStringSerializable;

public enum EnumMeat implements IStringSerializable, IHasOre {
  
  PIG(0, "Pig"),
  FISH(1, "Fish"),
  COW(2, "Cow"),
  CHICKEN(3, "Chicken"),
  RABBIT(4, "Rabbit"),
  SHEEP(5, "Sheep");
  
  private final int meta;
  private final String name;
  
  private EnumMeat(int meta, String name) {
    
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

  public static EnumMeat byMetadata(int meta) {
    
    if (meta < 0 || meta >= values().length) {
      meta = 0;
    }
    return values()[meta];
  }
  
  public static int count() {
    
    return values().length;
  }

  @Override
  public IBlockState getOre() {

    return ModBlocks.meatOre.getDefaultState().withProperty(MeatOre.MEAT, this);
  }
}
