package net.silentchaos512.funores.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class ConfigOreGenJson {

  public static Predicate<IBlockState> PREDICATE_STONE = state -> state != null && state.getBlock() == Blocks.STONE
      && ((BlockStone.EnumType) state.getValue(BlockStone.VARIANT)).isNatural();

  public final String configName;
  public IBlockState block;
  public float veinCount = 0;
  public int veinSize = 0;
  public int minY = 0;
  public int maxY = 128;
  public Predicate<IBlockState> predicate = PREDICATE_STONE;
  public Map<Biome, Float> biomeMultipliers = new HashMap<>();

  public ConfigOreGenJson(String configName) {

    this.configName = configName;
  }

  public boolean isEnabled() {

    return veinCount > 0 && veinSize > 0;
  }

  public int getVeinCount(Random random, Biome biome) {

    int count = (int) veinCount;
    float bonusChance = veinCount - count;
    count = random.nextFloat() < bonusChance ? count + 1 : count;
    if (biomeMultipliers.containsKey(biome)) {
      count = (int) (count * biomeMultipliers.get(biome));
    }
    return count;
  }

  public boolean canSpawnInBiome(Biome biome) {

    return biomeMultipliers.containsKey(biome) ? biomeMultipliers.get(biome) > 0f : true;
  }
}
