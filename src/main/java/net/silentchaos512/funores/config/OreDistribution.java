package net.silentchaos512.funores.config;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.JSONUtils;

import java.util.Random;

public final class OreDistribution {
    public static final Codec<OreDistribution> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("rarity").forGetter(o -> o.rarity),
                    Codec.INT.fieldOf("count").forGetter(o -> o.count),
                    Codec.INT.fieldOf("size").forGetter(o -> o.size),
                    Codec.INT.fieldOf("min_height").forGetter(o -> o.minHeight),
                    Codec.INT.fieldOf("max_height").forGetter(o -> o.maxHeight)
            ).apply(instance, OreDistribution::new));

    private static final Random RANDOM = new Random();

    private final int rarity;
    private final int count;
    private final int size;
    private final int minHeight;
    private final int maxHeight;

    private OreDistribution(int rarity, int count, int size, int minHeight, int maxHeight) {
        this.rarity = rarity;
        this.count = count;
        this.size = size;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public int getRarity() {
        return rarity;
    }

    public int getCount() {
        return this.count;
    }

    public int getSize() {
        return this.size;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public static OreDistribution deserialize(JsonObject json) {
        int rarity = JSONUtils.getAsInt(json, "rarity", 1);
        int count = JSONUtils.getAsInt(json, "count", 1);
        int size = JSONUtils.getAsInt(json, "size");
        int minHeight = JSONUtils.getAsInt(json, "min_height");
        int maxHeight = JSONUtils.getAsInt(json, "max_height");
        return new OreDistribution(rarity, count, size, minHeight, maxHeight);
    }
}
