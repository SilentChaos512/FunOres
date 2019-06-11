package net.silentchaos512.funores.block;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class LootDropOre extends OreBlock {
    private final Function<World, LivingEntity> entityConstructor;

    public LootDropOre(Function<World, LivingEntity> entityConstructor) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10f));
        this.entityConstructor = entityConstructor;
    }

    @Nullable
    public LivingEntity getEntityLiving(BlockState state, World world) {
        return entityConstructor.apply(world);
    }

    public ResourceLocation getLootTable(BlockState state, @Nullable LivingEntity entity) {
        return this.getLootTable();
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ServerWorld world = builder.func_216018_a();
        LivingEntity entity = getEntityLiving(state, world);

        FakePlayer fakePlayer = FakePlayerFactory.get(world, new GameProfile(null, "FakePlayerFunOres"));
        ItemStack harvestTool = builder.get(LootParameters.field_216289_i);
        int fortune = harvestTool != null ? EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, harvestTool) : 0;
        ItemStack fakeSword = getFakeSword(fortune);
        fakePlayer.setHeldItem(Hand.MAIN_HAND, fakeSword);

        LootContext context;
        if (entity != null) {
            // parameter set "entity": afc-deb
            DamageSource source = DamageSource.causePlayerDamage(fakePlayer);
            context = new LootContext.Builder(world)
                    .withParameter(LootParameters.field_216281_a, entity)
                    .withParameter(LootParameters.field_216283_c, source)
                    .withParameter(LootParameters.field_216284_d, fakePlayer)
                    .withParameter(LootParameters.field_216282_b, fakePlayer)
                    .withNullableParameter(LootParameters.field_216285_e, source.getImmediateSource())
                    .withNullableParameter(LootParameters.field_216286_f, builder.get(LootParameters.field_216286_f))
                    .build(LootParameterSets.field_216263_d);
        } else {
            // parameter set "block": gfi-ahj
            context = builder
                    .withParameter(LootParameters.field_216287_g, state)
                    .withParameter(LootParameters.field_216289_i, fakeSword)
                    .withParameter(LootParameters.field_216281_a, fakePlayer)
                    .build(LootParameterSets.field_216267_h);
        }
        ResourceLocation lootTableId = getLootTable(state, entity);
        LootTable lootTable = world.getServer().getLootTableManager().getLootTableFromLocation(lootTableId);
        return lootTable.func_216113_a(context);
    }

    private static final ItemStack[] FAKE_SWORDS = new ItemStack[4];

    private static ItemStack getFakeSword(int lootingLevel) {
        int index = MathHelper.clamp(lootingLevel, 0, 3);
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
}
