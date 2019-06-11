package net.silentchaos512.funores.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.funores.config.OreFeatureConfig;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class FunOresWorldFeatures {
    private static final Marker MARKER = MarkerManager.getMarker("WorldFeatures");

    private FunOresWorldFeatures() {}

    public static void addFeaturesToBiomes() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            for (OreFeatureConfig config : Config.ORES) {
                if (config.canSpawnIn(biome)) {
                    addOreToBiome(biome, config);
                }
            }
        }
    }

    private static void addOreToBiome(Biome biome, OreFeatureConfig config) {
//        FunOres.LOGGER.info(MARKER, "Add ore {} to biome {}", config.getConfigId(), biome.getRegistryName());
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(
                MultiBlockMinableFeature.INSTANCE,
                new MultiBlockMinableConfig(config),
                OrePlacement.INSTANCE,
                config
        ));
    }
}
