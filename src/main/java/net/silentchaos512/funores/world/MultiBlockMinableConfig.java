package net.silentchaos512.funores.world;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.silentchaos512.funores.config.OreFeatureConfig;

public class MultiBlockMinableConfig implements IFeatureConfig {
    public final OreFeatureConfig config;

    public MultiBlockMinableConfig(OreFeatureConfig config) {
        this.config = config;
    }
}
