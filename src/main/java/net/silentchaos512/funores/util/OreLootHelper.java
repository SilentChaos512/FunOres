package net.silentchaos512.funores.util;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
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
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.lib.ILootTableDrops;
import net.silentchaos512.lib.util.StackHelper;

public class OreLootHelper {

  public static List<ItemStack> getDrops(WorldServer world, int fortune, ILootTableDrops ore,
      int tryCount, ConfigOptionOreGenBonus config) {

    List<ItemStack> ret = Lists.newArrayList();
    Random rand = FunOres.random;

    EntityLivingBase entityLiving = ore.getEntityLiving(world);

    // Get the loot table.
    ResourceLocation resource = ore.getLootTable(entityLiving);
    LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(resource);

    // Create a fake player, wielding a sword with looting level equivalent to pickaxe's fortune.
    WeakReference<FakePlayer> fakePlayer = new WeakReference<>(
        FakePlayerFactory.get((WorldServer) world, new GameProfile(null, "FakePlayerFO")));
    ItemStack fakeSword = new ItemStack(Items.DIAMOND_SWORD);
    if (fortune > 0)
      fakeSword.addEnchantment(Enchantments.LOOTING, fortune);
    fakePlayer.get().setHeldItem(EnumHand.MAIN_HAND, fakeSword);

    LootContext.Builder lootContextBuilder = (new LootContext.Builder(world))
        .withLootedEntity(entityLiving).withPlayer(fakePlayer.get())
        .withDamageSource(DamageSource.causePlayerDamage(fakePlayer.get()));

    // Add drops from mob loot table
    for (int i = 0; i < tryCount; ++i)
      for (ItemStack stack : lootTable.generateLootForPools(rand, lootContextBuilder.build()))
        if (!config.shouldRemoveDrop(stack))
          ret.add(stack);

    addBonusDrops(ret, fortune, config);

    return ret;
  }

  private static void addBonusDrops(List<ItemStack> list, int fortune,
      ConfigOptionOreGenBonus config) {

    Random rand = FunOres.random;

    ConfigItemDrop[] dropsToTry;
    // Pick a certain number from the list, or try them all?
    if (config.pick != 0 && config.bonusDrops.size() > 0) {
      dropsToTry = new ConfigItemDrop[config.pick];
      for (int i = 0; i < config.pick; ++i) {
        dropsToTry[i] = config.bonusDrops.get(rand.nextInt(config.bonusDrops.size()));
      }
    } else {
      dropsToTry = config.bonusDrops.toArray(new ConfigItemDrop[] {});
    }

    for (ConfigItemDrop drop : dropsToTry) {
      // Make sure drop config isn't null.
      if (drop != null) {
        // Should we do the drop?
        if (rand.nextFloat() < drop.getDropChance(fortune)) {
          // How many to drop?
          ItemStack stack = StackHelper.safeCopy(drop.getStack());
          StackHelper.setCount(stack, drop.getDropCount(fortune, rand));
          // Drop stuff.
          for (int i = 0; i < StackHelper.getCount(stack); ++i) {
            list.add(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
          }
        }
      }
    }
  }
}
