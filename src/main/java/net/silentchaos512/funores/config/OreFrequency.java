package net.silentchaos512.funores.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JSONUtils;

import java.util.Random;

public final class OreFrequency {
    private static final Random RANDOM = new Random();

    private final double chance;
    private final int count;

    private OreFrequency(double chance, int count) {
        this.chance = chance;
        this.count = count;
    }

    public int getVeinCount() {
        double d = RANDOM.nextDouble();
        boolean b = d < this.chance;
        return b ? this.count : 0;
    }

    public static OreFrequency deserialize(JsonElement json) {
        if (json.isJsonObject()) {
            JsonObject obj = json.getAsJsonObject();
            if (obj.has("chance") && obj.has("count"))
                return new OreFrequency(JSONUtils.getFloat(obj, "chance"), JSONUtils.getInt(obj, "count"));
            if (obj.has("chance"))
                return new OreFrequency(JSONUtils.getFloat(obj, "chance"), 1);
            if (obj.has("count"))
                return new OreFrequency(1, JSONUtils.getInt(obj, "count"));
            throw new JsonSyntaxException("Expected 'frequency' to contains either 'chance' or 'count'");
        }
        throw new JsonSyntaxException("Expected 'frequency' to be object");
    }
}
