package net.silentchaos512.funores.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.LootDropOre;
import net.silentchaos512.funores.block.LootDropOreWithSpawn;
import net.silentchaos512.funores.config.Config;
import net.silentchaos512.lib.block.IBlockProvider;
import net.silentchaos512.utils.Lazy;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Supplier;

public enum Ores implements IBlockProvider {
    // Peaceful
    BAT(EntityType.BAT),
    CHICKEN(EntityType.CHICKEN),
    COW(EntityType.COW),
    PIG(EntityType.PIG),
    RABBIT(EntityType.RABBIT),
    SHEEP(EntityType.SHEEP),
    SQUID(EntityType.SQUID),
    // Fish
    COD(EntityType.COD),
    SALMON(EntityType.SALMON),
    PUFFERFISH(EntityType.PUFFERFISH),
    // Hostile
    CREEPER(EntityType.CREEPER),
    ENDERMAN(EntityType.ENDERMAN, World.OVERWORLD, () -> new LootDropOreWithSpawn(EntityType.ENDERMAN::create) {
        @Nullable
        @Override
        public LivingEntity getBreakSpawn(BlockState state, World world) {
            if (FunOres.RANDOM.nextFloat() < Config.COMMON.endermiteSpawnChance.get())
                return new EndermiteEntity(EntityType.ENDERMITE, world);
            return null;
        }
    }),
    GUARDIAN(EntityType.GUARDIAN),
    PHANTOM(EntityType.PHANTOM),
    SKELETON(EntityType.SKELETON, World.OVERWORLD),
    SLIME(EntityType.SLIME),
    SPIDER(EntityType.SPIDER),
    WITCH(EntityType.WITCH),
    ZOMBIE(EntityType.ZOMBIE, World.OVERWORLD),
    // Hostile (Nether)
    BLAZE(EntityType.BLAZE, World.NETHER),
    GHAST(EntityType.GHAST, World.NETHER),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, World.NETHER),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, World.NETHER),
    PIGLIN(EntityType.PIGLIN, World.NETHER),
    HOGLIN(EntityType.HOGLIN, World.NETHER);

    private final EntityType<? extends MobEntity> entityType;
    private final Lazy<LootDropOre> block;
    private final RegistryKey<World> dimensionType;
    private final ITag.INamedTag<Block> replacesBlock;

    Ores(EntityType<? extends MobEntity> entityType) {
        this(entityType, World.OVERWORLD, LootDropOre::new);
    }

    Ores(EntityType<? extends MobEntity> entityType, RegistryKey<World> dim) {
        this(entityType, dim, LootDropOre::new);
    }

    Ores(EntityType<? extends MobEntity> entityType, RegistryKey<World> dim, Supplier<LootDropOre> blockFactory) {
        this.block = Lazy.of(blockFactory);
        this.dimensionType = dim;
        this.replacesBlock = this.dimensionType == World.NETHER ? Tags.Blocks.NETHERRACK : Tags.Blocks.STONE;
        this.entityType = entityType;
    }

    public EntityType<? extends MobEntity> getEntityType() {
        return entityType;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String getBlockName() {
        return getName() + "_ore";
    }

    public RegistryKey<World> getDimensionType() {
        return dimensionType;
    }

    public ITag.INamedTag<Block> getReplacesBlock() {
        return replacesBlock;
    }

    @Override
    public Block asBlock() {
        return block.get();
    }

    @Override
    public Item asItem() {
        return asBlock().asItem();
    }
}
