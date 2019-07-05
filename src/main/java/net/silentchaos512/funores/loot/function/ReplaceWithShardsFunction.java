package net.silentchaos512.funores.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.functions.ILootFunction;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.ShardItems;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ReplaceWithShardsFunction implements ILootFunction {
    public static final Serializer SERIALIZER = new Serializer();

    @Override
    public ItemStack apply(ItemStack itemStack, LootContext lootContext) {
        for (ShardItems shard : ShardItems.values()) {
            if (shard.getFullItem() == itemStack.getItem()) {
                return new ItemStack(shard);
            }
        }
        return itemStack;
    }

    static class Serializer extends ILootFunction.Serializer<ReplaceWithShardsFunction> {
        Serializer() {
            super(FunOres.getId("replace_with_shards"), ReplaceWithShardsFunction.class);
        }

        @Override
        public void serialize(JsonObject object, ReplaceWithShardsFunction functionClazz, JsonSerializationContext serializationContext) {
        }

        @Override
        public ReplaceWithShardsFunction deserialize(JsonObject p_212870_1_, JsonDeserializationContext p_212870_2_) {
            return new ReplaceWithShardsFunction();
        }
    }
}
