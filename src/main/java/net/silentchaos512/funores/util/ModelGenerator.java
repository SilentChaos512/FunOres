package net.silentchaos512.funores.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.silentchaos512.funores.FunOres;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class ModelGenerator {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void createFor(Block block) {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();
        writeFile(createBlockstate(name), "blockstates", name);
        writeFile(createBlockModel(name), "models/block", name);
        writeFile(createBlockItemModel(name), "models/item", name);
    }

    public static void createFor(Item item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        writeFile(createItemModel(name), "models/item", name);
    }

    private static JsonObject createBlockstate(String name) {
        JsonObject json = new JsonObject();
        JsonObject variants = new JsonObject();
        JsonObject model = new JsonObject();

        model.addProperty("model", FunOres.RESOURCE_PREFIX + "block/" + name);
        variants.add("", model);
        json.add("variants", variants);

        return json;
    }

    private static JsonObject createBlockModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "block/cube_all");

        JsonObject textures = new JsonObject();
        textures.addProperty("all", FunOres.RESOURCE_PREFIX + "blocks/" + name);

        json.add("textures", textures);
        return json;
    }

    private static JsonObject createBlockItemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", FunOres.RESOURCE_PREFIX + "block/" + name);
        return json;
    }

    private static JsonObject createItemModel(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "item/generated");

        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", FunOres.RESOURCE_PREFIX + "items/" + name);

        json.add("textures", textures);
        return json;
    }

    private static void writeFile(JsonObject json, String subdir, String fileName) {
        fileName = fileName.replace(':', '_');
        final String dirPath = "output/" + FunOres.MOD_ID + "/" + subdir;
        final File directory = new File(dirPath);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                FunOres.LOGGER.error("Could not create directory: {}", dirPath);
                return;
            }
        }

        final File output = new File(directory, fileName + ".json");

        try (FileWriter writer = new FileWriter(output)) {
            GSON.toJson(json, writer);
            FunOres.LOGGER.info("Successfully wrote to {}", output.getAbsolutePath());
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }
}
