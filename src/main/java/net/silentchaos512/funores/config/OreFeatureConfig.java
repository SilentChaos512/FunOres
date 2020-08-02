package net.silentchaos512.funores.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.lib.util.NameUtils;

import javax.annotation.Nullable;
import java.util.*;

public class OreFeatureConfig implements IFeatureConfig, IPlacementConfig {
    public static final Codec<OreFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("config_id").forGetter(o -> o.configId),
                    WeightedBlockStateProvider.field_236811_b_.fieldOf("blocks").forGetter(o -> o.blocks),
                    TagMatchRuleTest.field_237161_a_.fieldOf("replaces").forGetter(o -> o.replacesBlock),
                    Codec.list(ResourceLocation.RESOURCE_LOCATION_CODEC).fieldOf("dimensions").forGetter(o -> o.dimensionAllowed),
                    OreDistribution.CODEC.fieldOf("distribution").forGetter(o -> o.distribution)
            ).apply(instance, OreFeatureConfig::new));

    private final String configId;
    private WeightedBlockStateProvider blocks;
    private TagMatchRuleTest replacesBlock;
    private List<ResourceLocation> dimensionAllowed;
    private OreDistribution distribution;

    public OreFeatureConfig(String configId) {
        this.configId = configId;
    }

    public OreFeatureConfig(String configId, WeightedBlockStateProvider blocks, TagMatchRuleTest target, List<ResourceLocation> dimensions, OreDistribution distribution) {
        this.configId = configId;
        this.blocks = blocks;
        this.replacesBlock = target;
        this.dimensionAllowed = new ArrayList<>(dimensions);
        this.distribution = distribution;
    }

    public String getConfigId() {
        return configId;
    }

    public BlockState getBlock(Random random, BlockPos pos) {
        return this.blocks.getBlockState(random, pos);
    }

    public boolean canReplace(BlockState state, Random random) {
        return this.replacesBlock.test(state, random);
    }

    public boolean canSpawnIn(Biome biome) {
        // TODO
        return true;
    }

    public boolean canSpawnIn(IWorld world) {
        if (this.dimensionAllowed.isEmpty()) {
            return true;
        }

        RegistryKey<World> type = world.getWorld().func_234923_W_();
        return this.dimensionAllowed.stream().anyMatch(key -> key.equals(type.getRegistryName()));
    }

    public int getVeinCount() {
        return this.distribution.getVeinCount();
    }

    public int getVeinSize() {
        return this.distribution.getVeinSize();
    }

    public int getMinHeight() {
        return this.distribution.getMinHeight();
    }

    public int getMaxHeight() {
        return this.distribution.getMaxHeight();
    }

    public static OreFeatureConfig deserialize(String id, JsonObject json) throws JsonSyntaxException {
        OreFeatureConfig result = new OreFeatureConfig(id);
        result.blocks = parseBlocksElement(json.get("blocks"));
        result.replacesBlock = parseReplacesElement(json);
        result.distribution = OreDistribution.deserialize(json);
        result.dimensionAllowed = parseDimensionsElement(json.get("dimensions"));
        // TODO: biomes
        return result;
    }

    private static WeightedBlockStateProvider parseBlocksElement(JsonElement json) {
        if (json.isJsonArray()) {
            WeightedBlockStateProvider ret = new WeightedBlockStateProvider();
            JsonArray array = json.getAsJsonArray();
            for (JsonElement je : array) {
                if (je.isJsonObject()) {
                    JsonObject jo = je.getAsJsonObject();
                    BlockState block = deserializeBlock(jo);
                    int weight = JSONUtils.getInt(jo, "weight", 1);
                    ret.addWeightedBlockstate(block, weight);
                } else if (je.isJsonPrimitive() && je.getAsJsonPrimitive().isString()) {
                    BlockState block = deserializeBlock(je);
                    ret.addWeightedBlockstate(block, 1);
                } else {
                    throw new JsonSyntaxException("Expected blocks array element to be object or string");
                }
            }
            return ret;
        }
        throw new JsonSyntaxException("Expected 'blocks' to be array");
    }

    private static BlockState deserializeBlock(JsonElement json) {
        String str = json.isJsonObject()
                ? JSONUtils.getString(json.getAsJsonObject(), "block", NameUtils.from(Blocks.AIR).toString())
                : json.getAsString();
        ResourceLocation blockId = new ResourceLocation(str);
        Block block = ForgeRegistries.BLOCKS.getValue(blockId);
        if (block == null) {
            throw new JsonSyntaxException("Unknown block: " + blockId);
        }
        return block.getDefaultState();
    }

    private static TagMatchRuleTest parseReplacesElement(JsonObject json) {
        return new TagMatchRuleTest(BlockTags.makeWrapperTag(JSONUtils.getString(json, "replaces")));
    }

    private static List<ResourceLocation> parseDimensionsElement(@Nullable JsonElement json) {
        if (json == null) {
            return ImmutableList.of();
        }

        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            List<ResourceLocation> list = new ArrayList<>();
            array.forEach(je -> list.add(parseDimensionElementSingle(je)));
            return list;
        }
        if (json.isJsonPrimitive()) {
            return Collections.singletonList(parseDimensionElementSingle(json));
        }
        throw new JsonSyntaxException("Expected 'dimensions' to be array, string, or number");
    }

    private static ResourceLocation parseDimensionElementSingle(JsonElement json) {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            return new ResourceLocation(json.getAsString());
        }
        throw new JsonSyntaxException("Expected 'dimensions' array element to be string");
    }

    private static ResourceLocation parseId(JsonObject json, String key) {
        String str = JSONUtils.getString(json, key);
        ResourceLocation id = ResourceLocation.tryCreate(str);
        if (id == null) {
            throw new JsonSyntaxException("Invalid ID: " + str);
        }
        return id;
    }

    public static JsonObject createDefault(String blockId, ITag.INamedTag<Block> replaces, double chance, int count, int size, int minHeight, int maxHeight, RegistryKey<World> dimension) {
        Map<String, Integer> blocks = new HashMap<>();
        blocks.put(blockId, 1);
        return createDefault(blocks, replaces, chance, count, size, minHeight, maxHeight, dimension);
    }

    public static JsonObject createDefault(Map<String, Integer> blocks, ITag.INamedTag<Block> replaces, double chance, int count, int size, int minHeight, int maxHeight, RegistryKey<World> dimension) {
        // Would love to just dump the files into data folder, but seems we can't read files from
        // the jar in 1.13. So, let's do this the hard way...
        JsonObject json = new JsonObject();
        JsonArray blocksArray = new JsonArray();
        //noinspection OverlyLongLambda
        blocks.forEach((blockId, weight) -> {
            JsonObject blocksElement = new JsonObject();
            if (!blockId.isEmpty()) {
                blocksElement.addProperty("block", blockId);
            }
            blocksElement.addProperty("weight", weight);
            blocksArray.add(blocksElement);
        });
        json.add("blocks", blocksArray);
        json.addProperty("replaces", replaces.getName().toString());
        json.addProperty("chance", chance);
        json.addProperty("count", 1);
        json.addProperty("size", size);
        json.addProperty("min_height", minHeight);
        json.addProperty("max_height", maxHeight);
        JsonArray dimensionsArray = new JsonArray();
        dimensionsArray.add(dimension.getRegistryName().toString());
        json.add("dimensions", dimensionsArray);
        JsonArray biomesArray = new JsonArray();
        json.add("biomes", biomesArray);
        return json;
    }
}
