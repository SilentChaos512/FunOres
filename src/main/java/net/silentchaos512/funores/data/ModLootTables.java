package net.silentchaos512.funores.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
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
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(Blocks::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((p_218436_2_, p_218436_3_) -> LootTableManager.validateLootTable(validationtracker, p_218436_2_, p_218436_3_));
    }

    private static final class Blocks extends BlockLootTables {
        private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

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

        private void mobOre(Ores ore, StandaloneLootEntry.Builder<?> mobLoot) {
            mobOre(ore, mobLoot, null);
        }

        private void mobOre(Ores ore, IItemProvider bonusShards) {
            mobOre(ore, MobLootEntry.builder(ore.getEntityType()), bonusShards);
        }

        @SuppressWarnings("TypeMayBeWeakened")
        private void mobOre(Ores ore, StandaloneLootEntry.Builder<?> mobLoot, @Nullable IItemProvider bonusShards) {
            LootTable.Builder builder = LootTable.builder()
                    .addLootPool(LootPool.builder()
                            .addEntry(AlternativesLootEntry.builder(
                                    ItemLootEntry.builder(ore.asBlock())
                                            .acceptCondition(SILK_TOUCH),
                                    mobLoot.acceptFunction(ReplaceWithShardsFunction::new)
                            ))
                    );
            if (bonusShards != null) {
                builder.addLootPool(LootPool.builder()
                        .addEntry(AlternativesLootEntry.builder(
                                EmptyLootEntry.func_216167_a()
                                        .acceptCondition(SILK_TOUCH),
                                ItemLootEntry.builder(bonusShards)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0, 1)))
                                        .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5f, 1))
                        ))
                );
            }
            this.registerLootTable(ore.asBlock(), builder);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                    .filter(block -> FunOres.MOD_ID.equals(NameUtils.from(block).getNamespace()))
                    .collect(Collectors.toSet());
        }
    }
}
