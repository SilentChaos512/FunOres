package net.silentchaos512.funores.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.fish.CodEntity;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
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
    BAT(() -> new LootDropOre(world -> new BatEntity(EntityType.BAT, world))),
    CHICKEN(() -> new LootDropOre(world -> new ChickenEntity(EntityType.CHICKEN, world))),
    COW(() -> new LootDropOre(world -> new CowEntity(EntityType.COW, world))),
    PIG(() -> new LootDropOre(world -> new PigEntity(EntityType.PIG, world))),
    RABBIT(() -> new LootDropOre(world -> new RabbitEntity(EntityType.RABBIT, world))),
    SHEEP(() -> new LootDropOre(world -> new SheepEntity(EntityType.SHEEP, world))),
    SQUID(() -> new LootDropOre(world -> new SquidEntity(EntityType.SQUID, world))),
    // Fish
    COD(() -> new LootDropOre(world -> new CodEntity(EntityType.COD, world))),
    SALMON(() -> new LootDropOre(world -> new SalmonEntity(EntityType.SALMON, world))),
    PUFFERFISH(() -> new LootDropOre(world -> new PufferfishEntity(EntityType.PUFFERFISH, world))),
    // Hostile
    CREEPER(() -> new LootDropOre(world -> new CreeperEntity(EntityType.CREEPER, world))),
    ENDERMAN(() -> new LootDropOreWithSpawn(world -> new EndermanEntity(EntityType.ENDERMAN, world)) {
        @Nullable
        @Override
        public LivingEntity getBreakSpawn(BlockState state, World world) {
            if (FunOres.RANDOM.nextFloat() < Config.COMMON.endermiteSpawnChance.get())
                return new EndermiteEntity(EntityType.ENDERMITE, world);
            return null;
        }
    }),
    GUARDIAN(() -> new LootDropOre(world -> {
        if (FunOres.RANDOM.nextFloat() < 0.01f)
            return new ElderGuardianEntity(EntityType.ELDER_GUARDIAN, world);
        return new GuardianEntity(EntityType.GUARDIAN, world);
    })),
    SKELETON(() -> new LootDropOre(world -> new SkeletonEntity(EntityType.SKELETON, world))),
    SLIME(() -> new LootDropOre(world -> new SlimeEntity(EntityType.SLIME, world))),
    SPIDER(() -> new LootDropOre(world -> {
        if (FunOres.RANDOM.nextBoolean())
            return new SpiderEntity(EntityType.SPIDER, world);
        return new CaveSpiderEntity(EntityType.CAVE_SPIDER, world);
    })),
    WITCH(() -> new LootDropOre(world -> new WitchEntity(EntityType.WITCH, world))),
    ZOMBIE(() -> new LootDropOre(Ores::selectZombie)),
    // Hostile (Nether)
    BLAZE(() -> new LootDropOre(world -> new BlazeEntity(EntityType.BLAZE, world)),
            DimensionType.THE_NETHER),
    GHAST(() -> new LootDropOre(world -> new GhastEntity(EntityType.GHAST, world)),
            DimensionType.THE_NETHER),
    MAGMA_CUBE(() -> new LootDropOre(world -> new MagmaCubeEntity(EntityType.MAGMA_CUBE, world)),
            DimensionType.THE_NETHER),
    WITHER_SKELETON(() -> new LootDropOre(world -> new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world)),
            DimensionType.THE_NETHER),
    ZOMBIE_PIGMAN(() -> new LootDropOre(world -> new ZombiePigmanEntity(EntityType.ZOMBIE_PIGMAN, world)),
            DimensionType.THE_NETHER);

    private final Lazy<LootDropOre> block;
    private final DimensionType dimensionType;

    Ores(Supplier<LootDropOre> blockFactory) {
        this(blockFactory, DimensionType.OVERWORLD);
    }

    Ores(Supplier<LootDropOre> blockFactory, DimensionType dim) {
        this.block = Lazy.of(blockFactory);
        this.dimensionType = dim;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public String getBlockName() {
        return getName() + "_ore";
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    @Override
    public Block asBlock() {
        return block.get();
    }

    @Override
    public Item asItem() {
        return asBlock().asItem();
    }

    private static ZombieEntity selectZombie(World world) {
        if (FunOres.RANDOM.nextFloat() < 0.2f)
            return new DrownedEntity(EntityType.DROWNED, world);
        if (FunOres.RANDOM.nextFloat() < 0.1f)
            return new HuskEntity(EntityType.HUSK, world);
        return new ZombieEntity(EntityType.ZOMBIE, world);
    }
}
