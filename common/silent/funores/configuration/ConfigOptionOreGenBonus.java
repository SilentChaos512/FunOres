package silent.funores.configuration;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Configuration;
import silent.funores.core.util.LogHelper;

public class ConfigOptionOreGenBonus extends ConfigOptionOreGen {

  private static final ArrayList<ConfigOptionOreGenBonus> LOADED_CONFIGS = new ArrayList<ConfigOptionOreGenBonus>();

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
      LogHelper.severe("The ore " + oreName
          + " has no drops assigned! You will get poisonous potatoes!");
      dropKeys.add("minecraft:poisonous_potato, 1, 0, 1.0, 0.0, 0.0");
    }

    String[] keys = c.get(category, "Drops", dropKeys.toArray(new String[] {})).getStringList();
    dropKeys.clear();
    for (String key : keys) {
      dropKeys.add(key);
    }

    pick = c.get(category, "Pick", pick).getInt();

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
