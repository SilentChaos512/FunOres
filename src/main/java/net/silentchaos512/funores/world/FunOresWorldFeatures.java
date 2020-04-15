package net.silentchaos512.funores.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.funores.config.OreFeatureConfig;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public final class FunOresWorldFeatures {
    private static final Marker MARKER = MarkerManager.getMarker("WorldFeatures");

    private static Feature<MultiBlockMinableConfig> MULTI_BLOCK_ORE;

    private static Placement<OreFeatureConfig> ORE_PLACEMENT;

    private FunOresWorldFeatures() {}

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        MULTI_BLOCK_ORE = new MultiBlockMinableFeature(dynamic -> new MultiBlockMinableConfig(new OreFeatureConfig("")));
        MULTI_BLOCK_ORE.setRegistryName(FunOres.getId("multi_block_ore"));
        event.getRegistry().register(MULTI_BLOCK_ORE);
    }

    public static void registerPlacements(RegistryEvent.Register<Placement<?>> event) {
        ORE_PLACEMENT = new OrePlacement(dynamic -> new OreFeatureConfig(""));
        ORE_PLACEMENT.setRegistryName(FunOres.getId("ore_placement"));
        event.getRegistry().register(ORE_PLACEMENT);
    }

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
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MULTI_BLOCK_ORE
                .withConfiguration(new MultiBlockMinableConfig(config))
                .withPlacement(ORE_PLACEMENT.configure(config))
        );
    }
}
