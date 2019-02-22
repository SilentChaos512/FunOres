package net.silentchaos512.funores.block;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import javax.annotation.Nullable;
import java.util.function.Function;

public class LootDropOre extends BlockOre {
    private final ResourceLocation lootTableName;
    private final Function<World, EntityLivingBase> entityConstructor;

    public LootDropOre(ResourceLocation lootTableName, Function<World, EntityLivingBase> entityConstructor) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10f));
        this.lootTableName = lootTableName;
        this.entityConstructor = entityConstructor;
    }

    @Nullable
    public EntityLivingBase getEntityLiving(IBlockState state, World world) {
        return entityConstructor.apply(world);
    }

    @Nullable
    public ResourceLocation getLootTable(IBlockState state, @Nullable EntityLivingBase entity) {
        return this.lootTableName;
    }

    public int getLootTryCount(IBlockState state, @Nullable EntityLivingBase entity) {
        return 1;
    }

    @Override
    public void getDrops(IBlockState state, NonNullList<ItemStack> drops, World world, BlockPos pos, int fortune) {
        if (world.isRemote() || !(world instanceof WorldServer)) return;

        EntityLivingBase entity = getEntityLiving(state, world);

        ResourceLocation resource = getLootTable(state, entity);
        if (resource == null) return;

        MinecraftServer server = world.getServer();
        if (server == null) return;

        LootTable lootTable = server.getLootTableManager().getLootTableFromLocation(resource);

        WorldServer worldServer = (WorldServer) world;
        FakePlayer fakePlayer = FakePlayerFactory.get(worldServer, new GameProfile(null, "FakePlayerFunOres"));
        ItemStack fakeSword = new ItemStack(Items.DIAMOND_SWORD);
        if (fortune > 0) {
            fakeSword.addEnchantment(Enchantments.LOOTING, fortune);
        }
        fakePlayer.setHeldItem(EnumHand.MAIN_HAND, fakeSword);

        LootContext.Builder lootContextBuilder = new LootContext.Builder(worldServer).withPlayer(fakePlayer);
        if (entity != null) {
            lootContextBuilder.withLootedEntity(entity);
            lootContextBuilder.withDamageSource(DamageSource.causePlayerDamage(fakePlayer));
        }

        final int tryCount = getLootTryCount(state, entity);
        for (int i = 0; i < tryCount; ++i) {
            drops.addAll(lootTable.generateLootForPools(RANDOM, lootContextBuilder.build()));
        }
    }
}
