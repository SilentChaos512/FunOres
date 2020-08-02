package net.silentchaos512.funores.config;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.JSONUtils;

import java.util.Random;

public final class OreDistribution {
    public static final Codec<OreDistribution> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.fieldOf("chance").forGetter(o -> o.chance),
                    Codec.INT.fieldOf("count").forGetter(o -> o.count),
                    Codec.INT.fieldOf("size").forGetter(o -> o.size),
                    Codec.INT.fieldOf("min_height").forGetter(o -> o.minHeight),
                    Codec.INT.fieldOf("max_height").forGetter(o -> o.maxHeight)
            ).apply(instance, OreDistribution::new));

    private static final Random RANDOM = new Random();

    private final double chance;
    private final int count;
    private final int size;
    private final int minHeight;
    private final int maxHeight;

    private OreDistribution(double chance, int count, int size, int minHeight, int maxHeight) {
        this.chance = chance;
        this.count = count;
        this.size = size;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public int getVeinCount() {
        double d = RANDOM.nextDouble();
        boolean b = d < this.chance;
        return b ? this.count : 0;
    }

    public int getVeinSize() {
        return this.size;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public static OreDistribution deserialize(JsonObject json) {
        double chance = JSONUtils.getFloat(json, "chance", 1f);
        int count = JSONUtils.getInt(json, "count", 1);
        int size = JSONUtils.getInt(json, "size");
        int minHeight = JSONUtils.getInt(json, "min_height");
        int maxHeight = JSONUtils.getInt(json, "max_height");
        return new OreDistribution(chance, count, size, minHeight, maxHeight);
    }
}
