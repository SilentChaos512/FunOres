package net.silentchaos512.funores.init;

import net.minecraft.loot.LootFunctionType;
import net.minecraft.util.registry.Registry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.loot.function.ReplaceWithShardsFunction;

public final class ModLoot {
    public static final LootFunctionType REPLACE_WITH_SHARDS = new LootFunctionType(new ReplaceWithShardsFunction.Serializer());

    private ModLoot() {}

    public static void init() {
        Registry.register(Registry.field_239694_aZ_, FunOres.getId("replace_with_shards"), REPLACE_WITH_SHARDS);
    }
}
