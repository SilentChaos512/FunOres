package net.silentchaos512.funores.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.silentchaos512.funores.init.ModLoot;
import net.silentchaos512.funores.lib.Ores;
import net.silentchaos512.lib.util.NameUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class MobLootEntry extends StandaloneLootEntry {
    private final WeightedList<EntityType<?>> typeList = new WeightedList<>();
    // Weighted list seems to have no way to get the weights, so we can't serialize it...
    private final Map<EntityType<?>, Integer> typeMap = new LinkedHashMap<>();

    private MobLootEntry(Map<EntityType<?>, Integer> weightedTypesIn, int weightIn, int qualityIn, ILootCondition[] conditionsIn, ILootFunction[] functionsIn) {
        super(weightIn, qualityIn, conditionsIn, functionsIn);
        weightedTypesIn.forEach(this.typeList::func_226313_a_);
        this.typeMap.putAll(weightedTypesIn);
    }

    public static StandaloneLootEntry.Builder<?> builder(EntityType<?> entityType) {
        return builder((weightIn, qualityIn, conditionsIn, functionsIn) ->
                new MobLootEntry(Collections.singletonMap(entityType, 1), weightIn, qualityIn, conditionsIn, functionsIn));
    }

    public static StandaloneLootEntry.Builder<?> builder(EntityType<?> type1, int weight1, EntityType<?> type2, int weight2) {
        Map<EntityType<?>, Integer> map = ImmutableMap.<EntityType<?>, Integer>builder()
                .put(type1, weight1)
                .put(type2, weight2)
                .build();
        return builder((weightIn, qualityIn, conditionsIn, functionsIn) ->
                new MobLootEntry(map, weightIn, qualityIn, conditionsIn, functionsIn));
    }

    public static StandaloneLootEntry.Builder<?> builder(EntityType<?> type1, int weight1, EntityType<?> type2, int weight2, EntityType<?> type3, int weight3) {
        Map<EntityType<?>, Integer> map = ImmutableMap.<EntityType<?>, Integer>builder()
                .put(type1, weight1)
                .put(type2, weight2)
                .put(type3, weight3)
                .build();
        return builder((weightIn, qualityIn, conditionsIn, functionsIn) ->
                new MobLootEntry(map, weightIn, qualityIn, conditionsIn, functionsIn));
    }

    public static StandaloneLootEntry.Builder<?> builder(Map<EntityType<?>, Integer> typesIn) {
        return builder((weightIn, qualityIn, conditionsIn, functionsIn) -> {
            return new MobLootEntry(typesIn, weightIn, qualityIn, conditionsIn, functionsIn);
        });
    }

    @Override
    protected void func_216154_a(Consumer<ItemStack> consumer, LootContext context) {
        ItemStack tool = context.get(LootParameters.TOOL);
        int fortune = tool != null ? EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool) : 0;

        FakePlayer fakePlayer = FakePlayerFactory.get(context.getWorld(), new GameProfile(null, "FakePlayerFunOres"));
        ItemStack fakeSword = getFakeSword(fortune);
        fakePlayer.setHeldItem(Hand.MAIN_HAND, fakeSword);

        EntityType<?> entityType = this.typeList.func_226318_b_(context.getRandom());
        Entity entity = entityType.create(context.getWorld());
        if (entity != null) {
            DamageSource source = DamageSource.causePlayerDamage(fakePlayer);
            LootContext fakeContext = new LootContext.Builder(context.getWorld())
                    .withParameter(LootParameters.THIS_ENTITY, entity)
                    .withParameter(LootParameters.DAMAGE_SOURCE, source)
                    .withParameter(LootParameters.KILLER_ENTITY, fakePlayer)
                    .withParameter(LootParameters.LAST_DAMAGE_PLAYER, fakePlayer)
                    .withNullableParameter(LootParameters.DIRECT_KILLER_ENTITY, source.getImmediateSource())
                    .withNullableParameter(LootParameters.POSITION, context.get(LootParameters.POSITION))
                    .build(LootParameterSets.ENTITY);
            ResourceLocation lootTableId = getLootTable(entity);
            LootTable lootTable = context.getLootTable(lootTableId);
            lootTable.recursiveGenerate(fakeContext, consumer);
        }
    }

    private static ResourceLocation getLootTable(Entity entity) {
        // Sheep are a special case... No wool drops from the default loot table
        if (entity.getType() == EntityType.SHEEP)
            return ((SheepEntity) entity).getLootTable();
        return entity.getType().getLootTable();
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return ModLoot.MOB_LOOT;
    }

    private static final int MAX_LOOTING_LEVEL = 10;
    private static final ItemStack[] FAKE_SWORDS = new ItemStack[MAX_LOOTING_LEVEL + 1];

    private static ItemStack getFakeSword(int lootingLevel) {
        int index = MathHelper.clamp(lootingLevel, 0, MAX_LOOTING_LEVEL);
        ItemStack stack = FAKE_SWORDS[index];
        if (stack == null) {
            stack = new ItemStack(Items.DIAMOND_SWORD);
            if (lootingLevel > 0) {
                stack.addEnchantment(Enchantments.LOOTING, lootingLevel);
            }
            FAKE_SWORDS[index] = stack;
        }
        return stack;
    }

    public static class Serializer extends StandaloneLootEntry.Serializer<MobLootEntry> {
        @Override
        public void func_230422_a_(JsonObject json, MobLootEntry entry, JsonSerializationContext context) {
            super.func_230422_a_(json, entry, context);

            JsonArray typesArray = new JsonArray();
            entry.typeMap.forEach((type, weight) -> {
                JsonObject jo = new JsonObject();
                jo.addProperty("entity", NameUtils.from(type).toString());
                jo.addProperty("weight", weight);
                typesArray.add(jo);
            });
            json.add("entity_types", typesArray);
        }

        @Override
        protected MobLootEntry func_212829_b_(JsonObject json, JsonDeserializationContext context, int weightIn, int qualityIn, ILootCondition[] conditionsIn, ILootFunction[] functionsIn) {
            Map<EntityType<?>, Integer> typeMap = new LinkedHashMap<>();

            JsonArray typesArray = JSONUtils.getJsonArray(json, "entity_types");
            typesArray.forEach(je -> {
                ResourceLocation typeId = new ResourceLocation(JSONUtils.getString(je.getAsJsonObject(), "entity"));
                EntityType<?> type = ForgeRegistries.ENTITIES.getValue(typeId);
                int weight = JSONUtils.getInt(je.getAsJsonObject(), "weight", 1);
                typeMap.put(type, weight);
            });

            return new MobLootEntry(typeMap, weightIn, qualityIn, conditionsIn, functionsIn);
        }
    }
}
