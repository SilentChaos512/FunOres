package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockOreMeat;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.init.ModBlocks;

public enum EnumMeat implements IStringSerializable, IHasOre, ILootTableDrops {

  PIG(0, "Pig"),
  FISH(1, "Fish"),
  COW(2, "Cow"),
  CHICKEN(3, "Chicken"),
  RABBIT(4, "Rabbit"),
  SHEEP(5, "Sheep"),
  SQUID(6, "Squid"),
  BAT(7, "Bat");

  public final int meta;
  public final String name;
  public final int dimension;

  private EnumMeat(int meta, String name) {

    this(meta, name, 0);
  }

  private EnumMeat(int meta, String name, int dimension) {

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

  public String getUnmodifiedName() {

    return name;
  }

  @Override
  public IBlockState getOre() {

    return ModBlocks.meatOre.getDefaultState().withProperty(BlockOreMeat.MEAT, this);
  }

  @Override
  public int getDimension() {

    return dimension;
  }

  public ConfigOptionOreGenBonus getConfig() {

    switch (this) {
      //@formatter:off
      case PIG:     return Config.pig;
      case FISH:    return Config.fish;
      case COW:     return Config.cow;
      case CHICKEN: return Config.chicken;
      case RABBIT:  return Config.rabbit;
      case SHEEP:   return Config.sheep;
      case SQUID:   return Config.squid;
      case BAT:     return Config.bat;
      //@formatter:on
      default:
        FunOres.logHelper.severe("Don't know config for ore " + name + "!");
        return null;
    }
  }

  @Override
  public EntityLivingBase getEntityLiving(World worldIn) {

    switch (this) {
      //@formatter:off
      case PIG:     return new EntityPig(worldIn);
      case FISH:    return null;
      case COW:     return new EntityCow(worldIn);
      case CHICKEN: return new EntityChicken(worldIn);
      case RABBIT:  return new EntityRabbit(worldIn);
      case SHEEP:
        EntitySheep sheep = new EntitySheep(worldIn);
        sheep.setFleeceColor(sheep.getRandomSheepColor(FunOres.random));
        return sheep;
      case SQUID:   return new EntitySquid(worldIn);
      case BAT:     return new EntityBat(worldIn);
      //@formatter:on
      default:
        FunOres.logHelper.severe("Don't know entity for ore " + name + "!");
        return null;
    }
  }

  @Override
  public ResourceLocation getLootTable(EntityLivingBase entityLiving) {

    switch (this) {
      //@formatter:off
      case PIG:     return LootTableList.ENTITIES_PIG;
      case FISH:    return LootTableList.GAMEPLAY_FISHING;
      case COW:     return LootTableList.ENTITIES_COW;
      case CHICKEN: return LootTableList.ENTITIES_CHICKEN;
      case RABBIT:  return LootTableList.ENTITIES_RABBIT;
      case SHEEP:
        if (entityLiving instanceof EntitySheep) {
          EntitySheep sheep = (EntitySheep) entityLiving;
          switch (sheep.getFleeceColor()) {
            case WHITE:       return LootTableList.ENTITIES_SHEEP_WHITE;
            case ORANGE:      return LootTableList.ENTITIES_SHEEP_ORANGE;
            case MAGENTA:     return LootTableList.ENTITIES_SHEEP_MAGENTA;
            case LIGHT_BLUE:  return LootTableList.ENTITIES_SHEEP_LIGHT_BLUE;
            case YELLOW:      return LootTableList.ENTITIES_SHEEP_YELLOW;
            case LIME:        return LootTableList.ENTITIES_SHEEP_LIME;
            case PINK:        return LootTableList.ENTITIES_SHEEP_PINK;
            case GRAY:        return LootTableList.ENTITIES_SHEEP_GRAY;
            case SILVER:      return LootTableList.ENTITIES_SHEEP_SILVER;
            case CYAN:        return LootTableList.ENTITIES_SHEEP_CYAN;
            case PURPLE:      return LootTableList.ENTITIES_SHEEP_PURPLE;
            case BLUE:        return LootTableList.ENTITIES_SHEEP_BLUE;
            case BROWN:       return LootTableList.ENTITIES_SHEEP_BROWN;
            case GREEN:       return LootTableList.ENTITIES_SHEEP_GREEN;
            case RED:         return LootTableList.ENTITIES_SHEEP_RED;
            case BLACK:       return LootTableList.ENTITIES_SHEEP_BLACK;
          }
        }
        return LootTableList.ENTITIES_SHEEP_WHITE;
      case SQUID:   return LootTableList.ENTITIES_SQUID;
      case BAT:     return LootTableList.ENTITIES_BAT;
      //@formatter:on
      default:
        FunOres.logHelper.severe("Don't know loot table for ore " + name + "!");
        return null;
    }
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
}
