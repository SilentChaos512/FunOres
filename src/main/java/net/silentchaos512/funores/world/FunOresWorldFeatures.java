package net.silentchaos512.funores.world;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.funores.config.OreConfig;
import net.silentchaos512.utils.Lazy;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod.EventBusSubscriber(modid = FunOres.MOD_ID)
public final class FunOresWorldFeatures {
    private static final Marker MARKER = MarkerManager.getMarker("WorldFeatures");

    public static final Lazy<Feature<OreConfig>> MULTI_BLOCK_ORE = Lazy.of(() ->
            new MultiBlockMinableFeature(OreConfig.CODEC));

//    private static Placement<OreFeatureConfig> ORE_PLACEMENT;

    private static boolean configuredFeaturesRegistered = false;

    private FunOresWorldFeatures() {}

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().register(MULTI_BLOCK_ORE.get().setRegistryName(FunOres.getId("multi_block_ore")));
    }

    public static void registerPlacements(RegistryEvent.Register<Placement<?>> event) {
//        ORE_PLACEMENT = new OrePlacement(OreFeatureConfig.CODEC);
//        ORE_PLACEMENT.setRegistryName(FunOres.getId("ore_placement"));
//        event.getRegistry().register(ORE_PLACEMENT);
    }

    private static void registerConfiguredFeature(String name, ConfiguredFeature<?, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, FunOres.getId(name), configuredFeature);
    }

    @SubscribeEvent
    public static void addFeaturesToBiomes(BiomeLoadingEvent biome) {
        registerConfiguredFeatures();

        for (OreConfig config : Config.ORES) {
            if (config.canSpawnIn(biome)) {
                addOreToBiome(biome, config);
            }
        }
    }

    private static void addOreToBiome(BiomeLoadingEvent biome, OreConfig config) {
//        FunOres.LOGGER.info(MARKER, "Add ore {} to biome {}", config.getConfigId(), biome.getRegistryName());
        biome.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, config.getConfiguredFeature());
    }

    private static void registerConfiguredFeatures() {
        if (configuredFeaturesRegistered) return;
        configuredFeaturesRegistered = true;

        Config.ORES.forEach(config -> registerConfiguredFeature(config.getConfigId(), config.getConfiguredFeature()));
    }
}
