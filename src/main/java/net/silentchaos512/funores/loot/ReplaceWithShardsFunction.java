package net.silentchaos512.funores.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.silentchaos512.funores.init.ModLoot;
import net.silentchaos512.funores.item.ShardItems;

public class ReplaceWithShardsFunction implements ILootFunction {
    @Override
    public ItemStack apply(ItemStack itemStack, LootContext lootContext) {
        for (ShardItems shard : ShardItems.values()) {
            if (shard.getFullItem() == itemStack.getItem()) {
                return new ItemStack(shard);
            }
        }
        return itemStack;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return ModLoot.REPLACE_WITH_SHARDS;
    }

    public static class Serializer implements ILootSerializer<ReplaceWithShardsFunction> {
        @Override
        public void serialize(JsonObject p_230424_1_, ReplaceWithShardsFunction p_230424_2_, JsonSerializationContext p_230424_3_) {
        }

        @Override
        public ReplaceWithShardsFunction deserialize(JsonObject p_230423_1_, JsonDeserializationContext p_230423_2_) {
            return new ReplaceWithShardsFunction();
        }
    }
}
