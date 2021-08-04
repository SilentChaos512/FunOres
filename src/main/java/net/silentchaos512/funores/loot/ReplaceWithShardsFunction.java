package net.silentchaos512.funores.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.silentchaos512.funores.init.ModLoot;
import net.silentchaos512.funores.item.ShardItems;

public class ReplaceWithShardsFunction implements LootItemFunction {
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
    public LootItemFunctionType getType() {
        return ModLoot.REPLACE_WITH_SHARDS;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ReplaceWithShardsFunction> {
        @Override
        public void serialize(JsonObject p_230424_1_, ReplaceWithShardsFunction p_230424_2_, JsonSerializationContext p_230424_3_) {
        }

        @Override
        public ReplaceWithShardsFunction deserialize(JsonObject p_230423_1_, JsonDeserializationContext p_230423_2_) {
            return new ReplaceWithShardsFunction();
        }
    }
}
