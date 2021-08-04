package net.silentchaos512.funores.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.ShardItems;
import net.silentchaos512.funores.lib.Ores;
import net.silentchaos512.funores.loot.MobLootEntry;
import net.silentchaos512.funores.loot.ReplaceWithShardsFunction;
import net.silentchaos512.lib.util.NameUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTables extends LootTableProvider {
    public ModLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public String getName() {
        return "Fun Ores - Loot Tables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(Blocks::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> LootTables.validate(validationtracker, p_218436_2_, p_218436_3_));
    }

    private static final class Blocks extends BlockLoot {
        private static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));

        @Override
        protected void addTables() {
            mobOre(Ores.BAT);
            mobOre(Ores.CHICKEN);
            mobOre(Ores.COW);
            mobOre(Ores.PIG);
            mobOre(Ores.RABBIT);
            mobOre(Ores.SHEEP);
            mobOre(Ores.SQUID);
            mobOre(Ores.COD);
            mobOre(Ores.SALMON);
            mobOre(Ores.PUFFERFISH);
            mobOre(Ores.CREEPER);
            mobOre(Ores.ENDERMAN, ShardItems.ENDER);
            mobOre(Ores.GUARDIAN, MobLootEntry.builder(
                    EntityType.GUARDIAN, 99,
                    EntityType.ELDER_GUARDIAN, 1
            ));
            mobOre(Ores.PHANTOM, ShardItems.PHANTOM_MEMBRANE);
            mobOre(Ores.SKELETON, MobLootEntry.builder(
                    EntityType.SKELETON, 9,
                    EntityType.STRAY, 1
            ));
            mobOre(Ores.SLIME);
            mobOre(Ores.SPIDER, MobLootEntry.builder(
                    EntityType.SPIDER, 1,
                    EntityType.CAVE_SPIDER, 1
            ));
            mobOre(Ores.WITCH);
            mobOre(Ores.ZOMBIE, MobLootEntry.builder(
                    EntityType.ZOMBIE, 8,
                    EntityType.HUSK, 1,
                    EntityType.DROWNED, 1
            ));
            mobOre(Ores.BLAZE, ShardItems.BLAZE);
            mobOre(Ores.GHAST, ShardItems.GHAST);
            mobOre(Ores.MAGMA_CUBE);
            mobOre(Ores.WITHER_SKELETON, ShardItems.WITHER_SKULL);
            mobOre(Ores.PIGLIN, MobLootEntry.builder(
                    EntityType.PIGLIN, 8,
                    EntityType.ZOMBIFIED_PIGLIN, 2
            ));
            mobOre(Ores.HOGLIN);
        }

        private void mobOre(Ores ore) {
            mobOre(ore, MobLootEntry.builder(ore.getEntityType()));
        }

        private void mobOre(Ores ore, LootPoolSingletonContainer.Builder<?> mobLoot) {
            mobOre(ore, mobLoot, null);
        }

        private void mobOre(Ores ore, ItemLike bonusShards) {
            mobOre(ore, MobLootEntry.builder(ore.getEntityType()), bonusShards);
        }

        @SuppressWarnings("TypeMayBeWeakened")
        private void mobOre(Ores ore, LootPoolSingletonContainer.Builder<?> mobLoot, @Nullable ItemLike bonusShards) {
            LootTable.Builder builder = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(AlternativesEntry.alternatives(
                                    LootItem.lootTableItem(ore.asBlock())
                                            .when(SILK_TOUCH),
                                    mobLoot.apply(ReplaceWithShardsFunction::new)
                            ))
                    );
            if (bonusShards != null) {
                builder.withPool(LootPool.lootPool()
                        .add(AlternativesEntry.alternatives(
                                EmptyLootItem.emptyItem()
                                        .when(SILK_TOUCH),
                                LootItem.lootTableItem(bonusShards)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5f, 1))
                        ))
                );
            }
            this.add(ore.asBlock(), builder);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(block -> FunOres.MOD_ID.equals(NameUtils.from(block).getNamespace()))
                    .collect(Collectors.toSet());
        }
    }
}
