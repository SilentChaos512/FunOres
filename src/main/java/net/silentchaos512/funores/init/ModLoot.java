package net.silentchaos512.funores.init;

import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.core.Registry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.loot.MobLootEntry;
import net.silentchaos512.funores.loot.ReplaceWithShardsFunction;

public final class ModLoot {
    public static final LootPoolEntryType MOB_LOOT = new LootPoolEntryType(new MobLootEntry.Serializer());
    public static final LootItemFunctionType REPLACE_WITH_SHARDS = new LootItemFunctionType(new ReplaceWithShardsFunction.Serializer());

    private ModLoot() {}

    public static void init() {
        Registry.register(Registry.LOOT_POOL_ENTRY_TYPE, FunOres.getId("loot_mob"), MOB_LOOT);
        Registry.register(Registry.LOOT_FUNCTION_TYPE, FunOres.getId("replace_with_shards"), REPLACE_WITH_SHARDS);
    }
}
