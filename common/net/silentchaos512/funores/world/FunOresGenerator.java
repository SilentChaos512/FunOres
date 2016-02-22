package net.silentchaos512.funores.world;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenReplace;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.lib.EnumVanillaOre;
import net.silentchaos512.funores.lib.IHasOre;

public class FunOresGenerator implements IWorldGenerator {

  private double debugMinTime = 1000000d;
  private double debugMaxTime = 0d;
  private double debugTotalTime = 0d;
  private int debugChunkGenCount = 0;
  private final String DEBUG_LINE = "Chunk (%d, %d) took %f ms to generate (min = %f, max = %f, avg = %f)";

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
      IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

    int dimension = world.provider.getDimensionId();
    int x = 16 * chunkX;
    int z = 16 * chunkZ;

    long timeStart = System.nanoTime();
    generateForDimension(dimension, world, random, x, z);
    if (Config.printWorldGenTime) {
      double timeTaken = (double) (System.nanoTime() - timeStart) / 1000000;
      debugMinTime = timeTaken < debugMinTime ? timeTaken : debugMinTime;
      debugMaxTime = timeTaken > debugMaxTime ? timeTaken : debugMaxTime;
      debugTotalTime += timeTaken;
      ++debugChunkGenCount;
      double avgTime = debugTotalTime / debugChunkGenCount;
      LogHelper.info(String.format(DEBUG_LINE, chunkX, chunkZ, timeTaken, debugMinTime,
          debugMaxTime, avgTime));
    }
  }

  private void generateForDimension(final int dim, World world, Random random, int posX, int posZ) {

    Predicate predicate;
    switch (dim) {
      case -1:
        predicate = BlockHelper.forBlock(Blocks.netherrack);
        break;
      case 1:
        predicate = BlockHelper.forBlock(Blocks.end_stone);
        break;
      default:
        predicate = BlockHelper.forBlock(Blocks.stone);
    }

    // Vanilla
    for (EnumVanillaOre vanilla : EnumVanillaOre.values()) {
      if (vanilla.dimension == dim) {
        generateOre(vanilla.getConfig(), world, random, posX, posZ, predicate);
      }
    }
    // Metal
    if (!Config.disableMetalOres) {
      for (EnumMetal metal : EnumMetal.values()) {
        if (metal.dimension == dim) {
          generateOre(metal.getConfig(), world, random, posX, posZ, predicate);
        }
      }
    }
    // Meat
    if (!Config.disableMeatOres) {
      for (EnumMeat meat : EnumMeat.values()) {
        if (meat.dimension == dim) {
          generateOre(meat.getConfig(), world, random, posX, posZ, predicate);
        }
      }
    }
    // Mob
    if (!Config.disableMobOres) {
      for (EnumMob mob : EnumMob.values()) {
        if (mob.dimension == dim) {
          generateOre(mob.getConfig(), world, random, posX, posZ, predicate);
        }
      }
    }
  }

  public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ,
      Predicate predicate) {

    if (!ore.enabled) {
      return;
    }

    if (!(ore.ore instanceof IHasOre)) {
      LogHelper.debug(ore.oreName + " is not an ore?");
      return;
    }

    if (ore instanceof ConfigOptionOreGenReplace) {
      ConfigOptionOreGenReplace oreGenReplace = (ConfigOptionOreGenReplace) ore;
      if (oreGenReplace.replaceExisting) {
        removeExistingOres(oreGenReplace, world, posX, posZ);
      }
    }

    BiomeGenBase biome = getBiomeForPos(world, new BlockPos(posX, 64, posZ));
    if (ore.canSpawnInBiome(biome)) {
      // Debug
      // if (ore == EnumMetal.COPPER.getConfig()) {
      // LogHelper.list(biome.biomeName, ore.oreName, ore.getClusterCountForBiome(biome));
      // }
      float trueClusterCount = ore.getClusterCountForBiome(biome);
      int clusterCount = (int) trueClusterCount;
      float bonusClusterChance = trueClusterCount - clusterCount;
      if (random.nextFloat() < bonusClusterChance) {
        ++clusterCount;
      }

      int x, y, z;
      for (int i = 0; i < clusterCount; ++i) {
        if (random.nextInt(ore.rarity) == 0) {
          x = posX + random.nextInt(16);
          y = ore.minY + random.nextInt(ore.maxY - ore.minY + 1);
          z = posZ + random.nextInt(16);

          BlockPos pos = new BlockPos(x, y, z);
          IBlockState state = ((IHasOre) ore.ore).getOre();

          new WorldGenMinable(state, ore.clusterSize, predicate).generate(world, random, pos);
        }
      }
    }
  }

  private void removeExistingOres(ConfigOptionOreGenReplace ore, World world, int posX, int posZ) {

    IBlockState state = ((IHasOre) ore.ore).getOre();
    IBlockState stone = Blocks.stone.getDefaultState();
    BlockPos pos;

    int maxHeight = 256;
    if (ore.ore instanceof EnumVanillaOre) {
      EnumVanillaOre vanilla = (EnumVanillaOre) ore.ore;
      maxHeight = vanilla.dimension == -1 ? 128 : maxHeight;
    }

    for (int y = 0; y < maxHeight; ++y) {
      for (int z = 0; z < 16; ++z) {
        for (int x = 0; x < 16; ++x) {
          pos = new BlockPos(posX + x, y, posZ + z);
          if (world.getBlockState(pos) == state) {
            world.setBlockState(pos, stone);
            // LogHelper.debug(pos + ", " + state);
          }
        }
      }
    }
  }

  public static BiomeGenBase getBiomeForPos(World world, BlockPos pos) {

    // Get biome at center of chunk
    int posX = (pos.getX() & 0xFFFFFFF0) + 8;
    int posZ = (pos.getZ() & 0xFFFFFFF0) + 8;
    BlockPos center = new BlockPos(posX, 64, posZ);
    // LogHelper.debug(pos + " -> " + center);
    return world.getBiomeGenForCoords(center);
  }
}
