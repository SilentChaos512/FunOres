package net.silentchaos512.funores.lib;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BreakSpawnBlock;
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
    ENDERMAN(EntityType.ENDERMAN, Level.OVERWORLD, () -> new BreakSpawnBlock() {
        @Nullable
        @Override
        public LivingEntity getBreakSpawn(BlockState state, Level world) {
            if (FunOres.RANDOM.nextFloat() < Config.COMMON.endermiteSpawnChance.get())
                return new Endermite(EntityType.ENDERMITE, world);
            return null;
        }
    }),
    GUARDIAN(EntityType.GUARDIAN),
    PHANTOM(EntityType.PHANTOM),
    SKELETON(EntityType.SKELETON, Level.OVERWORLD),
    SLIME(EntityType.SLIME),
    SPIDER(EntityType.SPIDER),
    WITCH(EntityType.WITCH),
    ZOMBIE(EntityType.ZOMBIE, Level.OVERWORLD),
    // Hostile (Nether)
    BLAZE(EntityType.BLAZE, Level.NETHER),
    GHAST(EntityType.GHAST, Level.NETHER),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, Level.NETHER),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, Level.NETHER),
    PIGLIN(EntityType.PIGLIN, Level.NETHER),
    HOGLIN(EntityType.HOGLIN, Level.NETHER);

    private final EntityType<? extends Mob> entityType;
    private final Lazy<Block> block;
    private final ResourceKey<Level> dimensionType;
    private final Tag.Named<Block> replacesBlock;

    Ores(EntityType<? extends Mob> entityType) {
        this(entityType, Level.OVERWORLD);
    }

    Ores(EntityType<? extends Mob> entityType, ResourceKey<Level> dim) {
        this(entityType, dim, () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 10f)));
    }

    Ores(EntityType<? extends Mob> entityType, ResourceKey<Level> dim, Supplier<Block> blockFactory) {
        this.block = Lazy.of(blockFactory);
        this.dimensionType = dim;
        this.replacesBlock = this.dimensionType == Level.NETHER ? Tags.Blocks.NETHERRACK : Tags.Blocks.STONE;
        this.entityType = entityType;
    }

    public EntityType<? extends Mob> getEntityType() {
        return entityType;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String getBlockName() {
        return getName() + "_ore";
    }

    public ResourceKey<Level> getDimensionType() {
        return dimensionType;
    }

    public Tag.Named<Block> getReplacesBlock() {
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
