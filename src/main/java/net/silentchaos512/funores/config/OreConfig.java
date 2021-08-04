package net.silentchaos512.funores.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.world.FunOresWorldFeatures;
import net.silentchaos512.lib.util.Lazy;
import net.silentchaos512.lib.util.NameUtils;

import javax.annotation.Nullable;
import java.util.*;

public class OreConfig implements FeatureConfiguration, DecoratorConfiguration {
    public static final Codec<OreConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("config_id").forGetter(o -> o.configId),
                    WeightedStateProvider.CODEC.fieldOf("blocks").forGetter(o -> o.blocks),
                    TagMatchTest.CODEC.fieldOf("replaces").forGetter(o -> o.replacesBlock),
                    Codec.list(ResourceLocation.CODEC).fieldOf("dimensions").forGetter(o -> o.dimensionAllowed),
                    OreDistribution.CODEC.fieldOf("distribution").forGetter(o -> o.distribution)
            ).apply(instance, OreConfig::new));

    private final String configId;
    private WeightedStateProvider blocks;
    private TagMatchTest replacesBlock;
    private List<ResourceLocation> dimensionAllowed;
    private OreDistribution distribution;

    private Lazy<ConfiguredFeature<?, ?>> configuredFeature = Lazy.of(() -> {
                int bottom = this.distribution.getMinHeight();
                return FunOresWorldFeatures.MULTI_BLOCK_ORE.get()
                        .configured(this)
                        .decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(bottom, bottom, this.distribution.getMaxHeight())))
                        .decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(this.distribution.getRarity())))
                        .squared()
                        .count(this.distribution.getCount());
            }
    );

    public OreConfig(String configId) {
        this.configId = configId;
    }

    public OreConfig(String configId, WeightedStateProvider blocks, TagMatchTest target, List<ResourceLocation> dimensions, OreDistribution distribution) {
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
        return this.blocks.getState(random, pos);
    }

    public boolean canReplace(BlockState state, Random random) {
        return this.replacesBlock.test(state, random);
    }

    public boolean canSpawnIn(BiomeLoadingEvent biome) {
        // TODO
        return true;
    }

    public boolean canSpawnIn(Level world) {
        if (this.dimensionAllowed.isEmpty()) {
            return true;
        }

        ResourceKey<Level> type = world.dimension();
        return this.dimensionAllowed.stream().anyMatch(key -> key.equals(type.getRegistryName()));
    }

    public int getSize() {
        return this.distribution.getSize();
    }

    public ConfiguredFeature<?, ?> getConfiguredFeature() {
        return configuredFeature.get();
    }

    public static OreConfig deserialize(String id, JsonObject json) throws JsonSyntaxException {
        OreConfig result = new OreConfig(id);
        result.blocks = parseBlocksElement(json.get("blocks"));
        result.replacesBlock = parseReplacesElement(json);
        result.distribution = OreDistribution.deserialize(json);
        result.dimensionAllowed = parseDimensionsElement(json.get("dimensions"));
        // TODO: biomes
        return result;
    }

    private static WeightedStateProvider parseBlocksElement(JsonElement json) {
        if (json.isJsonArray()) {
            WeightedStateProvider ret = new WeightedStateProvider();
            JsonArray array = json.getAsJsonArray();
            for (JsonElement je : array) {
                if (je.isJsonObject()) {
                    JsonObject jo = je.getAsJsonObject();
                    BlockState block = deserializeBlock(jo);
                    int weight = GsonHelper.getAsInt(jo, "weight", 1);
                    ret.add(block, weight);
                } else if (je.isJsonPrimitive() && je.getAsJsonPrimitive().isString()) {
                    BlockState block = deserializeBlock(je);
                    ret.add(block, 1);
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
                ? GsonHelper.getAsString(json.getAsJsonObject(), "block", NameUtils.from(Blocks.AIR).toString())
                : json.getAsString();
        ResourceLocation blockId = new ResourceLocation(str);
        Block block = ForgeRegistries.BLOCKS.getValue(blockId);
        if (block == null) {
            throw new JsonSyntaxException("Unknown block: " + blockId);
        }
        return block.defaultBlockState();
    }

    private static TagMatchTest parseReplacesElement(JsonObject json) {
        return new TagMatchTest(BlockTags.bind(GsonHelper.getAsString(json, "replaces")));
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
        String str = GsonHelper.getAsString(json, key);
        ResourceLocation id = ResourceLocation.tryParse(str);
        if (id == null) {
            throw new JsonSyntaxException("Invalid ID: " + str);
        }
        return id;
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    public static JsonObject createDefault(String blockId, Tag.Named<Block> replaces, int rarity, int count, int size, int minHeight, int maxHeight, ResourceKey<Level> dimension) {
        Map<String, Integer> blocks = new HashMap<>();
        blocks.put(blockId, 1);
        return createDefault(blocks, replaces, rarity, count, size, minHeight, maxHeight, dimension);
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    public static JsonObject createDefault(Map<String, Integer> blocks, Tag.Named<Block> replaces, int rarity, int count, int size, int minHeight, int maxHeight, ResourceKey<Level> dimension) {
        // Would love to just dump the files into data folder, but seems we can't read files from
        // the jar in 1.13. So, let's do this the hard way...
        JsonObject json = new JsonObject();
        JsonArray blocksArray = new JsonArray();
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
        json.addProperty("rarity", rarity);
        json.addProperty("count", 1);
        json.addProperty("size", size);
        json.addProperty("min_height", minHeight);
        json.addProperty("max_height", maxHeight);
        JsonArray dimensionsArray = new JsonArray();
        dimensionsArray.add(dimension.location().toString());
        json.add("dimensions", dimensionsArray);
        JsonArray biomesArray = new JsonArray();
        json.add("biomes", biomesArray);
        return json;
    }
}
