package net.silentchaos512.funores.world;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.silentchaos512.funores.config.OreFeatureConfig;

public class MultiBlockMinableConfig implements IFeatureConfig {
    public final OreFeatureConfig config;

    public MultiBlockMinableConfig(OreFeatureConfig config) {
        this.config = config;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> p_214634_1_) {
        return null;
    }
}
