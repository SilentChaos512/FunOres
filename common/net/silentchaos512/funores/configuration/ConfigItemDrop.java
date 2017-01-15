package net.silentchaos512.funores.configuration;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.silentchaos512.funores.FunOres;

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
    return stack.getCount() + bonus;
  }

  public ItemStack getStack() {

    return stack;
  }

  public static String getKey(String itemName, int count, int meta, float baseChance,
      float fortuneChanceBonus, float fortuneCountBonus) {

    // LogHelper.debug(Item.getByNameOrId(itemName).getUnlocalizedName());
    return String.format(Locale.US, "%s %d %d %.3f %.3f %.3f", itemName, count, meta, baseChance,
        fortuneChanceBonus, fortuneCountBonus);
  }

  public static final String ERROR_WARNING_MESSAGE = "You have %d malformed item drop keys in your config file! They will not work unless fixed!";

  public static void listErrorsInLog() {

    if (errorList.isEmpty()) {
      return;
    }

    FunOres.instance.logHelper.warning(String.format(ERROR_WARNING_MESSAGE, errorList.size()));

    for (String error : errorList) {
      FunOres.instance.logHelper.warning(error);
    }
  }

  public static void listErrorsInChat(EntityPlayer player) {

    if (errorList.isEmpty()) {
      return;
    }

    String prefix = String.format("[%s] ", FunOres.MOD_ID);
    String str = String.format(ERROR_WARNING_MESSAGE, errorList.size());
    player.sendMessage(new TextComponentString(TextFormatting.DARK_RED + prefix + str));

    for (String error : errorList) {
      player.sendMessage(new TextComponentString(prefix + error));
    }
  }
}
