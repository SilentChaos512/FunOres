package net.silentchaos512.funores.lib;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.LootTableList;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.LootDropOre;
import net.silentchaos512.funores.block.LootDropOreWithSpawn;
import net.silentchaos512.utils.Lazy;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Supplier;

public enum Ores implements IItemProvider {
    // Peaceful
    BAT(() -> new LootDropOre(dropsTable("bat"), EntityBat::new)),
    CHICKEN(() -> new LootDropOre(dropsTable("chicken"), EntityChicken::new)),
    COW(() -> new LootDropOre(dropsTable("cow"), EntityCow::new)),
    PIG(() -> new LootDropOre(dropsTable("pig"), EntityPig::new)),
    RABBIT(() -> new LootDropOre(dropsTable("rabbit"), EntityRabbit::new)),
    SHEEP(() -> new LootDropOre(LootTableList.ENTITIES_SHEEP, EntitySheep::new) {
        @Nullable
        @Override
        public ResourceLocation getLootTable(IBlockState state, @Nullable EntityLivingBase entity) {
            return getSheepLootTable(entity);
        }
    }),
    SQUID(() -> new LootDropOre(dropsTable("squid"), EntitySquid::new)),
    // Fish
    COD(() -> new LootDropOre(dropsTable("cod"), EntityCod::new)),
    SALMON(() -> new LootDropOre(dropsTable("salmon"), EntitySalmon::new)),
    PUFFERFISH(() -> new LootDropOre(dropsTable("pufferfish"), EntityPufferFish::new)),
    // Hostile
    BLAZE(() -> new LootDropOre(dropsTable("blaze"), EntityBlaze::new),
            DimensionType.NETHER),
    CREEPER(() -> new LootDropOre(dropsTable("creeper"), EntityCreeper::new)),
    ENDERMAN(() -> new LootDropOreWithSpawn(dropsTable("enderman"), EntityEnderman::new) {
        @Nullable
        @Override
        public EntityLivingBase getBreakSpawn(IBlockState state, World world) {
            if (FunOres.RANDOM.nextFloat() < 0.25f) // TODO: config
                return new EntityEndermite(world);
            return null;
        }
    }),
    GHAST(() -> new LootDropOre(dropsTable("ghast"), EntityGhast::new),
            DimensionType.NETHER),
    GUARDIAN(() -> new LootDropOre(dropsTable("guardian"), EntityGuardian::new)),
    MAGMA_CUBE(() -> new LootDropOre(dropsTable("magma_cube"), EntityMagmaCube::new),
            DimensionType.NETHER),
    SKELETON(() -> new LootDropOre(dropsTable("skeleton"), EntitySkeleton::new)),
    SLIME(() -> new LootDropOre(dropsTable("slime"), EntitySlime::new)),
    SPIDER(() -> new LootDropOre(dropsTable("spider"), EntitySpider::new)),
    WITCH(() -> new LootDropOre(dropsTable("witch"), EntityWitch::new)),
    WITHER_SKELETON(() -> new LootDropOre(dropsTable("wither_skeleton"), EntityWitherSkeleton::new),
            DimensionType.NETHER),
    ZOMBIE(() -> new LootDropOre(dropsTable("zombie"), EntityZombie::new)),
    ZOMBIE_PIGMAN(() -> new LootDropOre(dropsTable("zombie_pigman"), EntityPigZombie::new),
            DimensionType.NETHER);

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

    public Block asBlock() {
        return block.get();
    }

    @Override
    public Item asItem() {
        return asBlock().asItem();
    }

    private static ResourceLocation dropsTable(String name) {
        return new ResourceLocation(FunOres.MOD_ID, "ores/" + name);
    }

    @SuppressWarnings({"OverlyComplexMethod", "OverlyLongMethod"})
    private static ResourceLocation getSheepLootTable(EntityLivingBase entity) {
        if (!(entity instanceof EntitySheep)) return LootTableList.ENTITIES_SHEEP;
        EntitySheep sheep = (EntitySheep) entity;

        if (sheep.getSheared()) {
            return LootTableList.ENTITIES_SHEEP;
        } else {
            switch (sheep.getFleeceColor()) {
                case WHITE:
                default:
                    return LootTableList.ENTITIES_SHEEP_WHITE;
                case ORANGE:
                    return LootTableList.ENTITIES_SHEEP_ORANGE;
                case MAGENTA:
                    return LootTableList.ENTITIES_SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTableList.ENTITIES_SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTableList.ENTITIES_SHEEP_YELLOW;
                case LIME:
                    return LootTableList.ENTITIES_SHEEP_LIME;
                case PINK:
                    return LootTableList.ENTITIES_SHEEP_PINK;
                case GRAY:
                    return LootTableList.ENTITIES_SHEEP_GRAY;
                case LIGHT_GRAY:
                    return LootTableList.ENTITIES_SHEEP_LIGHT_GRAY;
                case CYAN:
                    return LootTableList.ENTITIES_SHEEP_CYAN;
                case PURPLE:
                    return LootTableList.ENTITIES_SHEEP_PURPLE;
                case BLUE:
                    return LootTableList.ENTITIES_SHEEP_BLUE;
                case BROWN:
                    return LootTableList.ENTITIES_SHEEP_BROWN;
                case GREEN:
                    return LootTableList.ENTITIES_SHEEP_GREEN;
                case RED:
                    return LootTableList.ENTITIES_SHEEP_RED;
                case BLACK:
                    return LootTableList.ENTITIES_SHEEP_BLACK;
            }
        }
    }
}
