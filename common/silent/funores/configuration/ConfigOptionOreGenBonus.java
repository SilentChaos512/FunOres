package silent.funores.configuration;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.config.Configuration;
import silent.funores.core.util.LogHelper;

public class ConfigOptionOreGenBonus extends ConfigOptionOreGen {

  private static final ArrayList<ConfigOptionOreGenBonus> LOADED_CONFIGS = new ArrayList<ConfigOptionOreGenBonus>();

  public static final String COMMENT_DROP = "The items drop by this ore. The parameters are "
      + "itemName, count, meta, baseChance, fortuneChanceBonus, fortuneCountBonus.";

  public ArrayList<ConfigItemDrop> drops = new ArrayList<ConfigItemDrop>();

  private ArrayList<String> dropKeys = new ArrayList<String>();

  public ConfigOptionOreGenBonus(IStringSerializable ore) {

    super(ore);
  }

  /**
   * Creates the primaryDrop and bonusDrops stacks. I don't think these should be created in pre-init, because that
   * would not allow mod items to be used. Should probably be called in post-init.
   */
  public static void initItemKeys() {

    for (ConfigOptionOreGenBonus config : LOADED_CONFIGS) {
      config.drops.clear();
      for (String dropKey : config.dropKeys) {
        // LogHelper.debug(dropKey);
        ConfigItemDrop drop = config.parseItem(dropKey);
        if (drop != null) {
          config.drops.add(drop);
        }
      }
    }
  }

  public void addDrop(String key) {

    dropKeys.add(key);
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    super.loadValue(c, category, comment);

    // Did I forget to add default drops?
    if (dropKeys.isEmpty()) {
      LogHelper.severe("The ore " + oreName
          + " has no drops assigned! You will get poisonous potatoes!");
      dropKeys.add("minecraft:poisonous_potato, 1, 0, 1.0, 0.0, 0.0");
    }

    String[] keys = c.getStringList("Drops", category, dropKeys.toArray(new String[] {}),
        COMMENT_DROP);
    dropKeys.clear();
    for (String key : keys) {
      dropKeys.add(key);
    }

    // int i = 0;
    // // Get default values.
    // for (; i < dropKeys.size(); ++i) {
    // dropKeys.set(i, c.getString("Drop" + i, category, dropKeys.get(i), COMMENT_DROP));
    // }
    // // Get additional values.
    // while (c.hasKey(category, "Drop" + i) && i < MAX_DROPS_COUNT) {
    // dropKeys.set(i, c.getString("Drop" + i, category, dropKeys.get(i), COMMENT_DROP));
    // LogHelper.derp(dropKeys.get(i));
    // ++i;
    // }
    //
    // // Did user add more than max items?
    // if (i >= MAX_DROPS_COUNT && c.hasKey(category, "Drop" + i)) {
    // LogHelper.warning("You may only enter " + MAX_DROPS_COUNT
    // + " drops for each ore! You will not see some drops you entered!");
    // }

    LOADED_CONFIGS.add(this);
    return this;
  }

  private ConfigItemDrop parseItem(String input) {

    // minecraft:stick, count, meta, chance, fortuneChanceBonus, fortuneCountBonus

    // Skip null keys.
    if (input.trim().toLowerCase().startsWith("null")) {
      return null;
    }

    String original = input;
    input = input.trim();
    String[] values = input.split(",");

    // Check for correctly formed key.
    if (values.length != 6) {
      LogHelper.warning("Malformed item key (must be a 6-tuple): " + original);
      return null;
    }

    // Get item
    Item item = Item.getByNameOrId(values[0]);
    if (item == null) {
      LogHelper.warning("Malformed item key: item " + values[0] + " could not be found!");
      return null;
    }
    // Get stack size and meta
    int count = -1;
    int meta = -1;
    String s = "";
    try {
      s = values[1].trim();
      count = Integer.parseInt(s);
      s = values[2].trim();
      meta = Integer.parseInt(s);
    } catch (NumberFormatException ex) {
      LogHelper.warning("Malformed item key: could not parse " + s + " as an integer!");
      return null;
    }

    // Get drop chances and fortune bonuses.
    float countBonus;
    float chance;
    float chanceBonus;
    s = "";
    try {
      s = values[3].trim();
      chance = Float.parseFloat(s);
      s = values[4].trim();
      chanceBonus = Float.parseFloat(s);
      s = values[5].trim();
      countBonus = Float.parseFloat(s);
    } catch (NumberFormatException ex) {
      LogHelper.warning("Malformed item key: could not parse " + s + " as a float!");
      return null;
    }

    // Finally, create ConfigItemDrop
    return new ConfigItemDrop(item, count, meta, chance, chanceBonus, countBonus);
  }
}
