package net.silentchaos512.funores.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenReplace;
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
      IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

    int dimension = world.provider.getDimension();
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
      FunOres.instance.logHelper.info(String.format(DEBUG_LINE, chunkX, chunkZ, timeTaken,
          debugMinTime, debugMaxTime, avgTime));
    }
  }

  private void generateForDimension(final int dim, World world, Random random, int posX, int posZ) {

    // Vanilla
    for (EnumVanillaOre vanilla : EnumVanillaOre.values()) {
      if (vanilla.dimension == dim || vanilla.dimension == 0) {
        generateOre(vanilla.getConfig(), world, random, posX, posZ);
      }
    }
    // Metal
    if (!Config.disableMetalOres) {
      for (EnumMetal metal : EnumMetal.values()) {
        if (metal.dimension == dim || metal.dimension == 0) {
          generateOre(metal.getConfig(), world, random, posX, posZ);
        }
      }
    }
    // Meat
    if (!Config.disableMeatOres) {
      for (EnumMeat meat : EnumMeat.values()) {
        if (meat.dimension == dim || meat.dimension == 0) {
          generateOre(meat.getConfig(), world, random, posX, posZ);
        }
      }
    }
    // Mob
    if (!Config.disableMobOres) {
      for (EnumMob mob : EnumMob.values()) {
        if (mob.dimension == dim || mob.dimension == 0) {
          generateOre(mob.getConfig(), world, random, posX, posZ);
        }
      }
    }
  }

  public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ) {

    if (!ore.enabled) {
      return;
    }

    if (!(ore.ore instanceof IHasOre)) {
      FunOres.instance.logHelper.debug(ore.oreName + " is not an ore?");
      return;
    }

    if (ore instanceof ConfigOptionOreGenReplace) {
      ConfigOptionOreGenReplace oreGenReplace = (ConfigOptionOreGenReplace) ore;
      if (oreGenReplace.replaceExisting) {
        removeExistingOres(oreGenReplace, world, posX, posZ);
      }
    }

    Biome biome = getBiomeForPos(world, new BlockPos(posX, 64, posZ));
    if (ore.canSpawnInBiome(biome)) {
      // Debug
      // if (ore == EnumMetal.COPPER.getConfig()) {
      // LogHelper.list(biome.biomeName, ore.oreName, ore.getClusterCountForBiome(biome));
      // }
      float trueClusterCount = ore.getClusterCountForBiome(biome);
      int clusterCount = trueClusterCount > 0 && trueClusterCount < 1 ? 1 : (int) trueClusterCount;
      float bonusClusterChance = trueClusterCount - clusterCount;
      if (random.nextFloat() < bonusClusterChance) {
        ++clusterCount;
      }

      int numSpawned = 0;

      int x, y, z;
      for (int i = 0; i < clusterCount; ++i) {
        if (random.nextInt(ore.rarity) == 0) {
          x = posX + random.nextInt(16);
          y = ore.minY + random.nextInt(ore.maxY - ore.minY + 1);
          z = posZ + random.nextInt(16);

          BlockPos pos = new BlockPos(x, y, z);
          IBlockState state = ((IHasOre) ore.ore).getOre();
          IBlockState targetState = world.getBlockState(pos);

          new WorldGenMinable(state, ore.clusterSize, ore.predicate).generate(world, random, pos);

          // Log placement?
          if (Config.logOrePlacement && ore.predicate.apply(targetState)) {
            if (numSpawned == 0) {
              String str = "Trying to spawn %d veins of %s Ore in chunk (%d, %d)";
              str = String.format(str, clusterCount, ore.oreName, posX / 16, posZ / 16);
              FunOres.instance.logHelper.info(str);
            }
            String str = "%s %d %d %d";
            str = String.format(str, ore.oreName, pos.getX(), pos.getY(), pos.getZ());
            FunOres.instance.logHelper.info(str);
          }

          ++numSpawned;
        }
      }
    }
  }

  private void removeExistingOres(ConfigOptionOreGenReplace ore, World world, int posX, int posZ) {

    IBlockState state = ((IHasOre) ore.ore).getOre();
    IBlockState stone = Blocks.STONE.getDefaultState();
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

  private void canOreSpawnInDimension(int dim, int oreDim) {

  }

  public static Biome getBiomeForPos(World world, BlockPos pos) {

    // Get biome at center of chunk
    int posX = (pos.getX() & 0xFFFFFFF0) + 8;
    int posZ = (pos.getZ() & 0xFFFFFFF0) + 8;
    BlockPos center = new BlockPos(posX, 64, posZ);
    // LogHelper.debug(pos + " -> " + center);
    return world.getBiomeGenForCoords(center);
  }
}
