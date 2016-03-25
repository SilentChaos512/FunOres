package net.silentchaos512.funores.configuration;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.FunOres;

public class ConfigOptionOreGenBonus extends ConfigOptionOreGen {

  private static final ArrayList<ConfigOptionOreGenBonus> LOADED_CONFIGS = new ArrayList<ConfigOptionOreGenBonus>();

  public static final String SPLITTER = "\\s+";
  public static final String COMMENT_DROP = "The items drop by this ore. The parameters are "
      + "itemName, count, meta, baseChance, fortuneChanceBonus, fortuneCountBonus.";
  public static final String COMMENT_PICK = "If greater than 0, try this many drops from the "
      + "list when mining the ore. If 0, try them all.";

  public static final int PICK_MIN = 0;
  public static final int PICK_MAX = 9001;

  /**
   * The number of drops from the list to attempt to drop when mining the ore.
   */
  public int pick = 0;
  /**
   * The drops that the player can get from the ore.
   */
  public ArrayList<ConfigItemDrop> drops = new ArrayList<ConfigItemDrop>();
  /**
   * The unparsed drops read from the config file.
   */
  private ArrayList<String> dropKeys = new ArrayList<String>();

  public ConfigOptionOreGenBonus(IStringSerializable ore) {

    super(ore);
  }

  public ConfigOptionOreGenBonus(boolean example) {

    super(example);
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

  /**
   * Used to add default drops.
   * 
   * @param key
   */
  public void addDrop(String key) {

    dropKeys.add(key);
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    if (isExample) {
      return loadExample(c);
    }

    super.loadValue(c, category, comment);

    // Did I forget to add default drops?
    if (dropKeys.isEmpty()) {
      FunOres.instance.logHelper.warning(
          "The ore " + oreName + " has no drops assigned! You will get poisonous potatoes!");
      dropKeys.add("minecraft:poisonous_potato, 1, 0, 1.0, 0.0, 0.0");
    }

    if (enabled) {
      String[] keys = c.get(category, "Drops", dropKeys.toArray(new String[] {})).getStringList();
      dropKeys.clear();
      for (String key : keys) {
        dropKeys.add(key);
      }

      pick = c.get(category, "Pick", pick).getInt();
    }

    LOADED_CONFIGS.add(this);
    return this.validate();
  }

  @Override
  protected ConfigOption loadExample(Configuration c) {

    addDrop(ConfigItemDrop.getKey("minecraft:emerald", 1, 0, 1.0f, 0.0f, 1.0f));
    addDrop(ConfigItemDrop.getKey("FunOres:AlloyIngot", 1, 2, 0.15f, 0.05f, 0.7f));

    c.getStringList("Drops", CATEGORY_EXAMPLE, dropKeys.toArray(new String[] {}), COMMENT_DROP);
    pick = c.getInt("Pick", CATEGORY_EXAMPLE, 0, PICK_MIN, PICK_MAX, COMMENT_PICK);

    super.loadExample(c);

    return this;
  }

  @Override
  public ConfigOption validate() {

    pick = MathHelper.clamp_int(pick, PICK_MIN, PICK_MAX);

    return super.validate();
  }

  /**
   * Parses the String read from the config file into something useable.
   * 
   * @param input
   * @return
   */
  private ConfigItemDrop parseItem(String input) {

    // minecraft:stick, count, meta, chance, fortuneChanceBonus, fortuneCountBonus

    // Skip null keys.
    if (input.trim().toLowerCase().startsWith("null")) {
      return null;
    }

    String original = input;
    input = input.trim();
    String[] values = input.split(SPLITTER);
    for (int i = 0; i < values.length; ++i) {
      values[i] = values[i].replaceAll(",$", "");
    }

    // Check for correctly formed key.
    if (values.length != 6) {
      String error = "Item key must have 6 elements (has %d): %s";
      error = String.format(error, values.length, input);
      ConfigItemDrop.errorList.add(error);
      return null;
    }

    // Get item
    Item item = Item.getByNameOrId(values[0]);
    if (item == null) {
      String error = "Item \"%s\" not found: %s";
      error = String.format(error, values[0], input);
      ConfigItemDrop.errorList.add(error);
      return null;
    }

    NumberFormat format = NumberFormat.getInstance();

    // Get stack size and meta
    int count = -1;
    int meta = -1;
    int currentIndex = 0;
    try {
      count = parseInt(values[currentIndex = 1].trim());
      meta =  parseInt(values[currentIndex = 2].trim());
    } catch (NumberFormatException ex) {
      String error = "Could not parse \"%s\" as integer: %s";
      error = String.format(error, values[currentIndex], input);
      ConfigItemDrop.errorList.add(error);
      return null;
    }

    // Get drop chances and fortune bonuses.
    float countBonus;
    float chance;
    float chanceBonus;
    try {
      chance = parseFloat(values[currentIndex = 3].trim());
      chanceBonus = parseFloat(values[currentIndex = 4].trim());
      countBonus = parseFloat(values[currentIndex = 5].trim());
    } catch (NumberFormatException ex) {
      String error = "Could not parse \"%s\" as float: %s";
      error = String.format(error, values[currentIndex], input);
      ConfigItemDrop.errorList.add(error);
      return null;
    }

    // Finally, create ConfigItemDrop
    return new ConfigItemDrop(item, count, meta, chance, chanceBonus, countBonus);
  }

  public int parseInt(String input) {

    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    ParsePosition parsePosition = new ParsePosition(0);
    Number number = numberFormat.parse(input, parsePosition);

    if (parsePosition.getIndex() != input.length()) {
      throw new NumberFormatException();
    }

//    LogHelper.debug(input + " -> " + number.intValue());
    return number.intValue();
  }

  public float parseFloat(String input) {

    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    ParsePosition parsePosition = new ParsePosition(0);
    Number number = numberFormat.parse(input, parsePosition);

    if (parsePosition.getIndex() != input.length()) {
      throw new NumberFormatException();
    }

//    LogHelper.debug(input + " -> " + number.floatValue());
    return number.floatValue();
  }
}
