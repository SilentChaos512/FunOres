package net.silentchaos512.funores.configuration;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.util.LogHelper;

public class ConfigItemDrop {

  public static List<String> errorList = Lists.newArrayList();

  public final ItemStack stack;
  public final float fortuneCountBonus;
  public final float baseChance;
  public final float fortuneChanceBonus;

  public ConfigItemDrop(Item item, int count, int meta, float baseChance, float fortuneChanceBonus,
      float fortuneCountBonus) {

    this.baseChance = baseChance;
    this.fortuneChanceBonus = fortuneChanceBonus;
    this.fortuneCountBonus = fortuneCountBonus;
    this.stack = new ItemStack(item, count, meta);
  }

  public float getDropChance(int fortuneLevel) {

    return baseChance + fortuneLevel * fortuneChanceBonus;
  }

  public int getDropCount(int fortuneLevel, Random random) {

    int bonus = (int) (fortuneLevel * fortuneCountBonus);
    if (bonus > 0) {
      bonus = random.nextInt(bonus + 1);
    }
    return stack.stackSize + bonus;
  }

  public ItemStack getStack() {

    return stack;
  }

  public static String getKey(String itemName, int count, int meta, float baseChance,
      float fortuneChanceBonus, float fortuneCountBonus) {

    // LogHelper.debug(Item.getByNameOrId(itemName).getUnlocalizedName());
    return String.format("%s, %d, %d, %.3f, %.3f, %.3f", itemName, count, meta, baseChance,
        fortuneChanceBonus, fortuneCountBonus);
  }

  public static final String ERROR_WARNING_MESSAGE = "You have %d malformed item drop keys in your config file! They will not work unless fixed!";

  public static void listErrorsInLog() {

    if (errorList.isEmpty()) {
      return;
    }

    LogHelper.warning(String.format(ERROR_WARNING_MESSAGE, errorList.size()));

    for (String error : errorList) {
      LogHelper.warning(error);
    }
  }

  public static void listErrorsInChat(EntityPlayer player) {

    if (errorList.isEmpty()) {
      return;
    }

    String prefix = String.format("[%s] ", FunOres.MOD_ID);
    String str = String.format(ERROR_WARNING_MESSAGE, errorList.size());
    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + prefix + str));

    for (String error : errorList) {
      player.addChatMessage(new ChatComponentText(prefix + error));
    }
  }
}
