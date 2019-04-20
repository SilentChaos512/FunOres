package net.silentchaos512.funores.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.config.OreFeatureConfig;

import java.util.Random;

public class OrePlacement extends BasePlacement<OreFeatureConfig> {
    public static final OrePlacement INSTANCE = new OrePlacement();

    @Override
    public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, OreFeatureConfig placementConfig, Feature<C> featureIn, C featureConfig) {
        if (!placementConfig.canSpawnIn(worldIn)) return false;

        int count = placementConfig.getVeinCount();
        for (int i = 0; i < count; ++i) {
            int x = random.nextInt(16);
            int y = random.nextInt(placementConfig.getMaxHeight() - placementConfig.getMinHeight()) + placementConfig.getMinHeight();
            int z = random.nextInt(16);
            BlockPos pos1 = pos.add(x, y, z);
            if (FunOres.LOGGER.isDebugEnabled()) {
                FunOres.LOGGER.debug("Placing ore '{}' at {}", placementConfig.getConfigId(), pos1);
            }
            featureIn.place(worldIn, chunkGenerator, random, pos1, featureConfig);
        }

        return true;
    }
}
