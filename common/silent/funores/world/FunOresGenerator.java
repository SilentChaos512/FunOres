package silent.funores.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import silent.funores.configuration.Config;
import silent.funores.configuration.ConfigOptionOreGen;
import silent.funores.core.util.LogHelper;
import silent.funores.lib.IHasOre;

import com.google.common.base.Predicate;

public class FunOresGenerator implements IWorldGenerator {

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
      IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

    int dimension = world.provider.getDimensionId();
    int x = 16 * chunkX;
    int z = 16 * chunkZ;

    switch (dimension) {
      case -1:
        generateNether(world, random, x, z);
        break;
      case 0:
        generateSurface(world, random, x, z);
        break;
      case 1:
        generateEnd(world, random, x, z);
        break;
      default:
        generateSurface(world, random, x, z);;
    }
  }

  private void generateSurface(World world, Random random, int posX, int posZ) {

    generateOre(Config.copper, world, random, posX, posZ);
    generateOre(Config.tin, world, random, posX, posZ);
    generateOre(Config.silver, world, random, posX, posZ);
    generateOre(Config.lead, world, random, posX, posZ);
    generateOre(Config.nickel, world, random, posX, posZ);
    generateOre(Config.platinum, world, random, posX, posZ);
    generateOre(Config.aluminium, world, random, posX, posZ);
    generateOre(Config.zinc, world, random, posX, posZ);

    generateOre(Config.pig, world, random, posX, posZ);
    generateOre(Config.fish, world, random, posX, posZ);
    generateOre(Config.cow, world, random, posX, posZ);
    generateOre(Config.chicken, world, random, posX, posZ);
    generateOre(Config.rabbit, world, random, posX, posZ);
    generateOre(Config.sheep, world, random, posX, posZ);

    generateOre(Config.zombie, world, random, posX, posZ);
    generateOre(Config.skeleton, world, random, posX, posZ);
    generateOre(Config.creeper, world, random, posX, posZ);
    generateOre(Config.spider, world, random, posX, posZ);
    generateOre(Config.enderman, world, random, posX, posZ);
    generateOre(Config.slime, world, random, posX, posZ);
    generateOre(Config.witch, world, random, posX, posZ);
  }

  private void generateNether(World world, Random random, int posX, int posZ) {

    Predicate predicate = BlockHelper.forBlock(Blocks.netherrack);

    generateOre(Config.pigman, world, random, posX, posZ, predicate);
    generateOre(Config.ghast, world, random, posX, posZ, predicate);
    generateOre(Config.magmaCube, world, random, posX, posZ, predicate);
    generateOre(Config.wither, world, random, posX, posZ, predicate);
  }

  private void generateEnd(World world, Random random, int posX, int posZ) {

    // Nothing
  }

  public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ) {

    generateOre(ore, world, random, posX, posZ, null);
  }

  public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ,
      Predicate predicate) {

    if (!(ore.ore instanceof IHasOre)) {
      LogHelper.debug(ore.oreName + " is not an ore?");
      return;
    }

    for (int i = 0; i < ore.clusterCount; ++i) {
      if (random.nextInt(ore.rarity) == 0) {
        int x = posX + random.nextInt(16);
        int y = ore.minY + random.nextInt(ore.maxY - ore.minY + 1);
        int z = posZ + random.nextInt(16);

        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = ((IHasOre) ore.ore).getOre();

        if (predicate == null) {
          new WorldGenMinable(state, ore.clusterSize).generate(world, random, pos);
        } else {
          new WorldGenMinable(state, ore.clusterSize, predicate).generate(world, random, pos);
        }
      }
    }
  }
}
