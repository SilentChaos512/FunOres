package silent.funores.configuration;

import java.util.Random;

import silent.funores.core.util.LogHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ConfigItemDrop {

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
    
//    LogHelper.debug(Item.getByNameOrId(itemName).getUnlocalizedName());
    return String.format("%s, %d, %d, %.3f, %.3f, %.3f", itemName, count, meta, baseChance,
        fortuneChanceBonus, fortuneCountBonus);
  }
}
