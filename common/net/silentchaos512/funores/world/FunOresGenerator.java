package net.silentchaos512.funores.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOreGenJson;
import net.silentchaos512.funores.configuration.OreConfigJsonReader;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.lib.EnumVanillaOre;
import net.silentchaos512.lib.world.WorldGeneratorSL;

public class FunOresGenerator extends WorldGeneratorSL {

  private double debugMinTime = 1000000d;
  private double debugMaxTime = 0d;
  private double debugTotalTime = 0d;
  private int debugChunkGenCount = 0;
  private final String DEBUG_LINE = "Chunk (%d, %d) took %f ms to generate (min = %f, max = %f, avg = %f)";

  public FunOresGenerator() {

    super(true, FunOres.MOD_ID + "_retrogen");
  }

  private void printDebugInfo(int chunkX, int chunkZ, long timeStart) {

    if (Config.printWorldGenTime) {
      double timeTaken = (double) (System.nanoTime() - timeStart) / 1000000;
      debugMinTime = timeTaken < debugMinTime ? timeTaken : debugMinTime;
      debugMaxTime = timeTaken > debugMaxTime ? timeTaken : debugMaxTime;
      debugTotalTime += timeTaken;
      ++debugChunkGenCount;
      double avgTime = debugTotalTime / debugChunkGenCount;
      FunOres.instance.logHelper.info(String.format(DEBUG_LINE, chunkX, chunkZ, timeTaken, debugMinTime, debugMaxTime, avgTime));
    }
  }

  @Override
  protected boolean generateForDimension(final int dim, World world, Random random, int posX, int posZ) {

    long timeStart = System.nanoTime();

    OreConfigJsonReader.oregens.entrySet().forEach(entry -> {
      generateOre(entry.getValue(), world, random, posX, posZ);
    });

    // // Vanilla
    // for (EnumVanillaOre vanilla : EnumVanillaOre.values()) {
    // if (vanilla.dimension == dim || vanilla.dimension == 0) {
    // generateOre(vanilla.getConfig(), world, random, posX, posZ);
    // }
    // }
    // // Metal
    // if (!Config.disableMetalOres) {
    // for (EnumMetal metal : EnumMetal.values()) {
    // if (metal.dimension == dim || metal.dimension == 0) {
    // generateOre(metal.getConfig(), world, random, posX, posZ);
    // }
    // }
    // }
    // // Meat
    // if (!Config.disableMeatOres) {
    // for (EnumMeat meat : EnumMeat.values()) {
    // if (meat.dimension == dim || meat.dimension == 0) {
    // generateOre(meat.getConfig(), world, random, posX, posZ);
    // }
    // }
    // }
    // // Mob
    // if (!Config.disableMobOres) {
    // for (EnumMob mob : EnumMob.values()) {
    // if (mob.dimension == dim || mob.dimension == 0) {
    // generateOre(mob.getConfig(), world, random, posX, posZ);
    // }
    // }
    // }

    printDebugInfo(posX / 16, posZ / 16, timeStart);

    return true;
  }

  public void generateOre(ConfigOreGenJson ore, World world, Random random, int posX, int posZ) {

    if (!ore.isEnabled()) {
      return;
    }

    Biome biome = getBiomeForPos(world, new BlockPos(posX, 64, posZ));
    if (ore.canSpawnInBiome(biome)) {
      int clusterCount = ore.getVeinCount(random, biome);

      int numSpawned = 0;

      int x, y, z;
      for (int i = 0; i < clusterCount; ++i) {
        x = posX + random.nextInt(16);
        y = ore.minY + random.nextInt(ore.maxY - ore.minY + 1);
        z = posZ + random.nextInt(16);

        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = ore.block;
        IBlockState targetState = world.getBlockState(pos);

        new WorldGenMinable(state, ore.veinSize, ore.predicate).generate(world, random, pos);

        // Log placement?
        if (Config.logOrePlacement && ore.predicate.apply(targetState)) {
          if (numSpawned == 0) {
            String str = "Trying to spawn %d veins of %s Ore in chunk (%d, %d)";
            str = String.format(str, clusterCount, ore.configName, posX / 16, posZ / 16);
            FunOres.instance.logHelper.info(str);
          }
          String str = "%s %d %d %d";
          str = String.format(str, ore.configName, pos.getX(), pos.getY(), pos.getZ());
          FunOres.instance.logHelper.info(str);
        }

        ++numSpawned;
      }
    }
  }

  public static Biome getBiomeForPos(World world, BlockPos pos) {

    // Get biome at center of chunk
    int posX = (pos.getX() & 0xFFFFFFF0) + 8;
    int posZ = (pos.getZ() & 0xFFFFFFF0) + 8;
    BlockPos center = new BlockPos(posX, 64, posZ);
    return world.getBiome(center);
  }

  @SubscribeEvent
  public void onGenerateMinable(OreGenEvent.GenerateMinable event) {

    if ((event.getType() == EventType.COAL && Config.coal.replaceExisting) || (event.getType() == EventType.DIAMOND && Config.diamond.replaceExisting)
        || (event.getType() == EventType.EMERALD && Config.emerald.replaceExisting) || (event.getType() == EventType.GOLD && Config.gold.replaceExisting)
        || (event.getType() == EventType.IRON && Config.iron.replaceExisting) || (event.getType() == EventType.LAPIS && Config.lapis.replaceExisting)
        || (event.getType() == EventType.QUARTZ && Config.quartz.replaceExisting) || (event.getType() == EventType.REDSTONE && Config.redstone.replaceExisting))
      event.setResult(Result.DENY);

  }
}
