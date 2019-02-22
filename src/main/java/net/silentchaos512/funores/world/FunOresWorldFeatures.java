package net.silentchaos512.funores.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MinableConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Ores;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class FunOresWorldFeatures {
    private static final Marker MARKER = MarkerManager.getMarker("WorldFeatures");

    private FunOresWorldFeatures() {}

    public static void addFeaturesToBiomes() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            for (Ores ore : Ores.values()) {
                if (ore.canSpawnIn(biome)) {
                    addOreToBiome(biome, ore);
                }
            }
        }
    }

    private static void addOreToBiome(Biome biome, Ores ore) {
        FunOres.LOGGER.debug(MARKER, "Add ore {} to biome {}", ore, biome.getRegistryName());
        // TODO: Ore feature configs
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createCompositeFeature(
                Feature.MINABLE,
                new MinableConfig(
                        ore.getBlockToReplace(),
                        ore.asBlock().getDefaultState(),
                        20
                ),
                Biome.CHANCE_RANGE,
                new ChanceRangeConfig(0.075f, 24, 0, 80)
        ));
    }
}
