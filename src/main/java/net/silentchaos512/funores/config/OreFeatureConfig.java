package net.silentchaos512.funores.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class OreFeatureConfig implements IPlacementConfig {
    private final String configId;
    // TODO: Maybe allow multiple blocks with varying probabilities?
    //  Should probably load configs AFTER registry events in that case (load files in common setup)
    private ResourceLocation blockId;
    private Predicate<Block> replacesBlock;
    private List<Predicate<DimensionType>> dimensionAllowed;
    private OreFrequency frequency;
    private int veinSize;
    private int minHeight;
    private int maxHeight;

    public OreFeatureConfig(String configId) {
        this.configId = configId;
    }

    public String getConfigId() {
        return configId;
    }

    @Nullable
    public Block getBlock() {
        return ForgeRegistries.BLOCKS.getValue(this.blockId);
    }

    public boolean canReplace(IBlockState state) {
        return this.replacesBlock.test(state.getBlock());
    }

    public boolean canSpawnIn(Biome biome) {
        // TODO
        return true;
    }

    public boolean canSpawnIn(IWorldReaderBase world) {
        if (this.dimensionAllowed.isEmpty()) {
            return true;
        }

        DimensionType type = world.getDimension().getType();
        for (Predicate<DimensionType> predicate : this.dimensionAllowed) {
            if (predicate.test(type)) {
                return true;
            }
        }

        return false;
    }

    public int getVeinCount() {
        return this.frequency.getVeinCount();
    }

    public int getVeinSize() {
        return this.veinSize;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public static OreFeatureConfig deserialize(String id, JsonObject json) throws JsonSyntaxException {
        OreFeatureConfig result = new OreFeatureConfig(id);
        result.blockId = parseId(json, "block");
        result.replacesBlock = parseReplacesElement(json.get("replaces"));
        result.frequency = OreFrequency.deserialize(json.get("frequency"));
        result.veinSize = JsonUtils.getInt(json, "size");
        result.minHeight = JsonUtils.getInt(json, "min_height");
        result.maxHeight = JsonUtils.getInt(json, "max_height");
        result.dimensionAllowed = parseDimensionsElement(json.get("dimensions"));
        // TODO: biomes
        return result;
    }

    private static Predicate<Block> parseReplacesElement(JsonElement json) {
        if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            if (obj.has("item")) {
                final ResourceLocation id = parseId(obj, "item");
                return b -> b == ForgeRegistries.BLOCKS.getValue(id);
            } else if (obj.has("tag")) {
                final ResourceLocation tagId = parseId(obj, "tag");
                final Tag<Block> tag = new BlockTags.Wrapper(tagId);
                return b -> b.isIn(tag);
            }
        }
        throw new JsonSyntaxException("Expected 'replaces' to be object");
    }

    private static List<Predicate<DimensionType>> parseDimensionsElement(@Nullable JsonElement json) {
        if (json == null) {
            return ImmutableList.of();
        }

        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            List<Predicate<DimensionType>> list = new ArrayList<>();
            array.forEach(je -> list.add(parseDimensionElementSingle(je)));
            return list;
        }
        if (json.isJsonPrimitive()) {
            return Collections.singletonList(parseDimensionElementSingle(json));
        }
        throw new JsonSyntaxException("Expected 'dimensions' to be array, string, or number");
    }

    private static Predicate<DimensionType> parseDimensionElementSingle(JsonElement json) {
        if (json.isJsonPrimitive()) {
            if (json.getAsJsonPrimitive().isString()) {
                final String str = json.getAsString();
                return d -> testDimension(str, d);
            } else if (json.getAsJsonPrimitive().isNumber()) {
                final int k = json.getAsInt();
                return d -> testDimension(k, d);
            }
        }
        throw new JsonSyntaxException("Expected 'dimensions' array element to be string or number");
    }

    private static boolean testDimension(String str, DimensionType dimensionType) {
        if ("overworld".equalsIgnoreCase(str) && dimensionType != DimensionType.NETHER && dimensionType != DimensionType.THE_END) {
            return true;
        }
        return str.equalsIgnoreCase(Objects.requireNonNull(dimensionType.getRegistryName()).toString());
    }

    private static boolean testDimension(int id, DimensionType dimensionType) {
        if (id == 0 && dimensionType != DimensionType.NETHER && dimensionType != DimensionType.THE_END) {
            return true;
        }
        return id == dimensionType.getId();
    }

    private static ResourceLocation parseId(JsonObject json, String key) {
        String str = JsonUtils.getString(json, key);
        ResourceLocation id = ResourceLocation.tryCreate(str);
        if (id == null) {
            throw new JsonSyntaxException("Invalid ID: " + str);
        }
        return id;
    }

    public static JsonObject createDefault(String blockId, String replacesType, String replaces, double chance, int count, int size, int minHeight, int maxHeight, int dimension) {
        // Would love to just dump the files into data folder, but seems we can't read files from
        // the jar in 1.13. So, let's do this the hard way...
        JsonObject json = new JsonObject();
        json.addProperty("block", blockId);
        JsonObject replacesObj = new JsonObject();
        replacesObj.addProperty(replacesType, replaces);
        json.add("replaces", replacesObj);
        JsonObject frequency = new JsonObject();
        frequency.addProperty("chance", chance);
        frequency.addProperty("count", 1);
        json.add("frequency", frequency);
        json.addProperty("size", size);
        json.addProperty("min_height", minHeight);
        json.addProperty("max_height", maxHeight);
        JsonArray dimensionsArray = new JsonArray();
        dimensionsArray.add(dimension);
        json.add("dimensions", dimensionsArray);
        JsonArray biomesArray = new JsonArray();
        json.add("biomes", biomesArray);
        return json;
    }
}
