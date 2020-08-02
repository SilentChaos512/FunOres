package net.silentchaos512.funores.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.silentchaos512.funores.init.ModLoot;
import net.silentchaos512.funores.item.ShardItems;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
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
    public LootFunctionType func_230425_b_() {
        return ModLoot.REPLACE_WITH_SHARDS;
    }

    public static class Serializer implements ILootSerializer<ReplaceWithShardsFunction> {
        @Override
        public void func_230424_a_(JsonObject json, ReplaceWithShardsFunction function, JsonSerializationContext context) {
        }

        @Override
        public ReplaceWithShardsFunction func_230423_a_(JsonObject json, JsonDeserializationContext context) {
            return new ReplaceWithShardsFunction();
        }
    }
}
