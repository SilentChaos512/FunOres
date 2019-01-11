/*
 * Fun Ores -- OreLootHelper
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.lib.ILootTableDrops;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OreLootHelper {

//    public static List<ItemStack> getDrops(WorldServer world, int fortune, ILootTableDrops ore, int tryCount, ConfigOptionOreGenBonus config) {
//        List<ItemStack> ret = new ArrayList<>();
//        Random rand = FunOres.random;
//
//        EntityLivingBase entityLiving = ore.getEntityLiving(world);
//
//        // Get the loot table.
//        ResourceLocation resource = ore.getLootTable(entityLiving);
//        if (resource == null) return ret;
//        LootTable lootTable = world.getServer().getLootTableManager().getLootTableFromLocation(resource);
//
//        // Create a fake player, wielding a sword with looting level equivalent to pickaxe's fortune.
//        WeakReference<FakePlayer> fakePlayer = new WeakReference<>(FakePlayerFactory.get(world, new GameProfile(null, "FakePlayerFO")));
//        ItemStack fakeSword = new ItemStack(Items.DIAMOND_SWORD);
//        if (fortune > 0) {
//            // Neither seem to do anything for fish drops
//            fakeSword.addEnchantment(Enchantments.LOOTING, fortune);
//            fakeSword.addEnchantment(Enchantments.LUCK_OF_THE_SEA, fortune);
//        }
//        fakePlayer.get().setHeldItem(EnumHand.MAIN_HAND, fakeSword);
//
//        LootContext.Builder lootContextBuilder = new LootContext.Builder(world).withPlayer(fakePlayer.get());
//        if (entityLiving != null) {
//            lootContextBuilder
//                    .withLootedEntity(entityLiving)
//                    .withDamageSource(DamageSource.causePlayerDamage(fakePlayer.get()));
//        }
//
//        // Add drops from mob loot table
//        for (int i = 0; i < tryCount; ++i) {
//            for (ItemStack stack : lootTable.generateLootForPools(rand, lootContextBuilder.build())) {
//                final boolean isFishOre = entityLiving == null; // FIXME: This is fine for now, but it's a potential time bomb.
//                final boolean isFish = isFish(stack);
//                final boolean mayDropNonFish = !isFishOre || isFish || !Config.fishOreDropFishOnly;
//
//                if (!config.shouldRemoveDrop(stack) && mayDropNonFish)
//                    ret.add(stack);
//            }
//        }
//
//        addBonusDrops(ret, fortune, config);
//
//        return ret;
//    }
//
//    private static void addBonusDrops(List<ItemStack> list, int fortune, ConfigOptionOreGenBonus config) {
//        Random rand = FunOres.random;
//
//        ConfigItemDrop[] dropsToTry;
//        // Pick a certain number from the list, or try them all?
//        if (config.pick != 0 && !config.bonusDrops.isEmpty()) {
//            dropsToTry = new ConfigItemDrop[config.pick];
//            for (int i = 0; i < config.pick; ++i) {
//                dropsToTry[i] = config.bonusDrops.get(rand.nextInt(config.bonusDrops.size()));
//            }
//        } else {
//            dropsToTry = config.bonusDrops.toArray(new ConfigItemDrop[]{});
//        }
//
//        for (ConfigItemDrop drop : dropsToTry) {
//            // Make sure drop config isn't null.
//            if (drop != null) {
//                // Should we do the drop?
//                if (rand.nextFloat() < drop.getDropChance(fortune)) {
//                    // How many to drop?
//                    ItemStack stack = drop.getStack().copy();
//                    stack.setCount(drop.getDropCount(fortune, rand));
//                    // Drop stuff.
//                    for (int i = 0; i < stack.getCount(); ++i) {
//                        list.add(new ItemStack(stack.getItem()));
//                    }
//                }
//            }
//        }
//    }
//
//    private static boolean isFish(ItemStack stack) {
//        String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getPath();
//        return stack.getItem() instanceof ItemFishFood
//                || (name.contains("fish") && !name.contains("fishing") && !name.contains("rod"));
//    }
}
