package net.silentchaos512.funores.world;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.funores.config.OreFeatureConfig;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OrePlacement extends SimplePlacement<OreFeatureConfig> {
    public OrePlacement(Codec<OreFeatureConfig> p_i232095_1_) {
        super(p_i232095_1_);
    }

    @Override
    protected <FC extends IFeatureConfig, F extends Feature<FC>> boolean func_236963_a_(ISeedReader worldIn, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, BlockPos pos, OreFeatureConfig config, ConfiguredFeature<FC, F> feature) {
        if (!config.canSpawnIn(worldIn)) return false;

        int count = config.getVeinCount();
        for (int i = 0; i < count; ++i) {
            int x = random.nextInt(16);
            int y = random.nextInt(config.getMaxHeight() - config.getMinHeight()) + config.getMinHeight();
            int z = random.nextInt(16);
            BlockPos pos1 = pos.add(x, y, z);
            if (Config.COMMON.logOreSpawns.get()) {
                FunOres.LOGGER.info("Placing ore '{}' at {}, chunk {}", config.getConfigId(), pos1, new ChunkPos(pos1));
            }
            feature.func_236265_a_(worldIn, structureManager, chunkGenerator, random, pos1);
        }

        return true;
    }

    @Override
    protected Stream<BlockPos> getPositions(Random random, OreFeatureConfig config, BlockPos pos) {
        return IntStream.range(0, config.getVeinSize()).mapToObj(k ->
                pos.add(random.nextInt(16),
                        random.nextInt(config.getMaxHeight()) + config.getMinHeight(),
                        random.nextInt(16)));
    }
}
