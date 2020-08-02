package net.silentchaos512.funores.config;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.loading.FMLPaths;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Ores;
import net.silentchaos512.utils.config.BooleanValue;
import net.silentchaos512.utils.config.ConfigSpecWrapper;
import net.silentchaos512.utils.config.DoubleValue;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Config {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    private static final ConfigSpecWrapper WRAPPER = ConfigSpecWrapper.create(resolveAndCreate("funores/common.toml"));

    public static final List<OreFeatureConfig> ORES = new ArrayList<>();

    public static final Common COMMON = new Common(WRAPPER);

    public static class Common {
        public final DoubleValue endermiteSpawnChance;
        public final BooleanValue logOreSpawns;

        Common(ConfigSpecWrapper wrapper) {
            endermiteSpawnChance = wrapper
                    .builder("ores.enderman.endermiteSpawnChance")
                    .comment("The chance that mining enderman ore will spawn an endermite. Range: [0, 1]")
                    .defineInRange(0.25, 0, 1);
            logOreSpawns = wrapper
                    .builder("logging.oreSpawns")
                    .comment("If true, all ore spawns are logged. This could be useful for validating your configs,",
                            "but will dump large amounts of text to your logs, slowing down the game.",
                            "This should be set to 'false' most of the time!")
                    .define(false);
        }
    }

    private Config() {}

    public static void init() {
        // Create needed directories, copy default configs if missing
        File directory = FMLPaths.CONFIGDIR.get().resolve("funores/ore_generation/").toFile();
        validateOreConfigFolder(directory);

        WRAPPER.validate();
        WRAPPER.validate();

        loadOres(directory);
    }

    private static Path resolveAndCreate(String path) {
        Path result = FMLPaths.CONFIGDIR.get().resolve(path);
        //noinspection ResultOfMethodCallIgnored
        result.toFile().getParentFile().mkdirs();
        return result;
    }

    private static void loadOres(File directory) {
        if (!directory.exists()) {
            FunOres.LOGGER.fatal("Ore generation directory has vanished! Fun Ores will NOT load ore configs.");
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            FunOres.LOGGER.fatal("'{}' is not a directory?", directory.getPath());
            return;
        }

        ORES.clear();

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                //noinspection DynamicRegexReplaceableByCompiledPattern
                String id = file.getName().replace(".json", "");
                JsonObject json = GSON.fromJson(reader, JsonObject.class);
                OreFeatureConfig config = OreFeatureConfig.deserialize(id, json);
                FunOres.LOGGER.info("Read ore config '{}'", file.getPath());
                ORES.add(config);
            } catch (JsonSyntaxException | NullPointerException ex) {
                FunOres.LOGGER.error("Error reading ore config file: '{}'", file.getPath());
                FunOres.LOGGER.catching(ex);
            } catch (FileNotFoundException ex) {
                FunOres.LOGGER.error("File not found: '{}'", file.getPath());
                FunOres.LOGGER.catching(ex);
            } catch (IOException ex) {
                FunOres.LOGGER.error("Could not read file: '{}'", file.getPath());
                FunOres.LOGGER.catching(ex);
            }
        }
    }

    private static void validateOreConfigFolder(File directory) {
        if (!directory.exists()) {
            FunOres.LOGGER.info("Ore config directory '{}' does not exist, creating it now", directory.getPath());
            if (!directory.mkdirs()) {
                FunOres.LOGGER.fatal("Could not create directory {}", directory.getPath());
                return;
            }
        }

        File[] files = directory.listFiles();
        if (files == null) {
            FunOres.LOGGER.fatal("'{}' is not a directory?", directory.getPath());
            return;
        }

        if (files.length == 0) {
            createDefaultOreFiles(directory);
        }
    }

    private static void createDefaultOreFiles(File directory) {
        for (Ores ore : Ores.values()) {
            createDefaultFile(directory, ore);
        }
        writeDefaultFile(directory, "extra_diamonds", OreFeatureConfig.createDefault(
                "minecraft:diamond_ore",
                Tags.Blocks.STONE,
                0.5, 1,
                6,
                0, 16,
                World.field_234918_g_
        ));
        writeDefaultFile(directory, "extra_emeralds", OreFeatureConfig.createDefault(
                "minecraft:emerald_ore",
                Tags.Blocks.STONE,
                0.1, 1,
                1,
                16, 48,
                World.field_234918_g_
        ));
        writeDefaultFile(directory, "extra_gold", OreFeatureConfig.createDefault(
                "minecraft:gold_ore",
                Tags.Blocks.STONE,
                0.7, 2,
                8,
                8, 32,
                World.field_234918_g_
        ));
    }

    private static void createDefaultFile(File directory, Ores ore) {
        JsonObject json = OreFeatureConfig.createDefault(
                ImmutableMap.of(
                        "funores:" + ore.getBlockName(), 10,
                        "", 3
                ),
                ore.getReplacesBlock(),
                0.05, 1,
                22,
                32, 84,
                ore.getDimensionType()
        );
        writeDefaultFile(directory, ore.getName(), json);
    }

    private static void writeDefaultFile(File directory, String oreName, JsonObject json) {
        String fileName = oreName + ".json";
        File file = new File(directory.getPath(), fileName);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.write(GSON.toJson(json));
        } catch (IOException ex) {
            FunOres.LOGGER.error("Could not write to file: '{}'", file.getPath());
            FunOres.LOGGER.catching(ex);
        }
    }
}
