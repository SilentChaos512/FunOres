package net.silentchaos512.funores.world;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
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
        Block oreBlock = config.getBlock();
        if (oreBlock == null) {
            FunOres.LOGGER.error(MARKER, "Tried to add ore {} to biome {}, but the ore block is null!", config.getConfigId(), biome.getRegistryName());
            return;
        }

        FunOres.LOGGER.info(MARKER, "Add ore {} to biome {}", config.getConfigId(), biome.getRegistryName());
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(
                Feature.MINABLE,
                new MinableConfig(
                        config::canReplace,
                        oreBlock.getDefaultState(),
                        config.getVeinSize()
                ),
                OrePlacement.INSTANCE,
                config
        ));
    }
}
