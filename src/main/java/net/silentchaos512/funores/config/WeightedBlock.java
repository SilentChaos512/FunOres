package net.silentchaos512.funores.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class WeightedBlock extends WeightedRandom.Item {
    @Nullable private final Block block;

    public WeightedBlock(@Nullable Block block, int itemWeightIn) {
        super(itemWeightIn);
        this.block = block;
    }

    @Nullable
    public Block getBlock() {
        return block;
    }

    public static WeightedBlock deserialize(JsonElement json) {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            int weight = JSONUtils.getInt(jsonObject, "weight", 1);

            // No block, which means no replacement is done. This is valid.
            if (!jsonObject.has("block")) {
                return new WeightedBlock(null, weight);
            }

            String blockId = JSONUtils.getString(jsonObject, "block");
            ResourceLocation key = ResourceLocation.tryCreate(blockId);
            if (key == null) {
                throw new JsonSyntaxException("Invalid block ID: " + blockId);
            }
            Block block = ForgeRegistries.BLOCKS.getValue(key);
            return new WeightedBlock(block, weight);
        } else if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            String blockId = json.getAsString();
            ResourceLocation key = ResourceLocation.tryCreate(blockId);
            if (key == null) {
                throw new JsonSyntaxException("Invalid block ID: " + blockId);
            }
            Block block = ForgeRegistries.BLOCKS.getValue(key);
            return new WeightedBlock(block, 1);
        }
        throw new JsonSyntaxException("Expected blocks array element to be object or string");
    }
}
