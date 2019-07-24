package net.silentchaos512.funores.world;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.SimplePlacement;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.funores.config.OreFeatureConfig;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OrePlacement extends SimplePlacement<OreFeatureConfig> {
    public static final OrePlacement INSTANCE = new OrePlacement(dynamic -> new OreFeatureConfig(""));

    public OrePlacement(Function<Dynamic<?>, ? extends OreFeatureConfig> p_i51371_1_) {
        super(p_i51371_1_);
    }

    @Override
    public <C extends IFeatureConfig> boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, BlockPos pos, OreFeatureConfig placementConfig, ConfiguredFeature<C> config) {
        if (!placementConfig.canSpawnIn(worldIn)) return false;

        int count = placementConfig.getVeinCount();
        for (int i = 0; i < count; ++i) {
            int x = random.nextInt(16);
            int y = random.nextInt(placementConfig.getMaxHeight() - placementConfig.getMinHeight()) + placementConfig.getMinHeight();
            int z = random.nextInt(16);
            BlockPos pos1 = pos.add(x, y, z);
            if (Config.COMMON.logOreSpawns.get()) {
                FunOres.LOGGER.info("Placing ore '{}' at {}, chunk {}", placementConfig.getConfigId(), pos1, new ChunkPos(pos1));
            }
            config.place(worldIn, chunkGenerator, random, pos1);
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
