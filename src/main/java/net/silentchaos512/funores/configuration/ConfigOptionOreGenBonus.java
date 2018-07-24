/*
 * Fun Ores -- ConfigOptionOreGenBonus
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

package net.silentchaos512.funores.configuration;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.lib.IHasOre;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfigOptionOreGenBonus extends ConfigOptionOreGen {
    private static final ArrayList<ConfigOptionOreGenBonus> LOADED_CONFIGS = new ArrayList<>();

    public static final String SPLITTER = "\\s+";
    public static final String COMMENT_DROP = "Additional items dropped by this ore. The parameters are "
            + "itemName, count, meta, baseChance, fortuneChanceBonus, fortuneCountBonus.";
    public static final String COMMENT_PICK = "If greater than 0, try this many drops from the "
            + "list when mining the ore. If 0, try them all.";

    public static final int PICK_MIN = 0;
    public static final int PICK_MAX = 9001;

    static final String KEY_DROPS_BONUS = "DropsBonus";
    static final String KEY_DROPS_REMOVED = "DropsRemoved";
    static final String KEY_PICK = "Pick";

    /**
     * The number of drops from the bonus drops list to attempt to drop when mining the ore.
     */
    public int pick = 0;
    /**
     * The list of standard loot table drops to remove.
     */
    public List<ItemStack> removedDrops = Lists.newArrayList();
    /**
     * The unparsed stack keys for removed drops.
     */
    private List<String> removedDropKeys = Lists.newArrayList();
    /**
     * The bonus drops that the player can get from the ore.
     */
    public List<ConfigItemDrop> bonusDrops = Lists.newArrayList();
    /**
     * The unparsed drops read from the config file.
     */
    private List<String> bonusDropKeys = Lists.newArrayList();

    public ConfigOptionOreGenBonus(IHasOre ore) {
        super(ore);
    }

    public ConfigOptionOreGenBonus(boolean example) {
        super(example);
    }

    /**
     * Creates the primaryDrop and bonusDrops stacks. I don't think these should be created in
     * pre-init, because that would not allow mod items to be used. Should probably be called in
     * post-init.
     */
    public static void initItemKeys() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

        for (ConfigOptionOreGenBonus config : LOADED_CONFIGS) {
            config.bonusDrops.clear();
            // Parse bonus drop keys.
            for (String dropKey : config.bonusDropKeys) {
                ConfigItemDrop drop = config.parseItemDrop(dropKey, numberFormat);
                if (drop != null)
                    config.bonusDrops.add(drop);
            }

            // Parse the removed standard drop keys.
            config.removedDrops.clear();
            for (String stackKey : config.removedDropKeys) {
                ItemStack stack = config.parseStack(stackKey, numberFormat);
                if (stack != null)
                    config.removedDrops.add(stack);
            }
        }
    }

    /**
     * Used to add bonus drops.
     *
     * @param key
     */
    public void addBonusDrop(String key) {
        bonusDropKeys.add(key);
    }

    public void removeStandardDrop(String key) {
        removedDropKeys.add(key);
    }

    public boolean shouldRemoveDrop(ItemStack drop) {
        for (ItemStack stack : removedDrops)
            if (stack.isItemEqual(drop))
                return true;
        return false;
    }

    @Override
    public ConfigOption loadValue(Configuration c, String category, String comment) {
        if (isExample) {
            return loadExample(c);
        }

        super.loadValue(c, category, comment);

        //@formatter:on
        String[] keys;
        // Bonus drops
        keys = c.get(category, KEY_DROPS_BONUS, bonusDropKeys.toArray(new String[]{})).getStringList();
        bonusDropKeys.clear();
        for (String key : keys) bonusDropKeys.add(key);

        // Removed standard drops
        keys = c.get(category, KEY_DROPS_REMOVED, removedDropKeys.toArray(new String[]{})).getStringList();
        removedDropKeys.clear();
        for (String key : keys) removedDropKeys.add(key);

        // Bonus drop pick count
        pick = c.get(category, KEY_PICK, pick).getInt();
        //@formatter:on

        LOADED_CONFIGS.add(this);
        return this.validate();
    }

    @Override
    protected ConfigOption loadExample(Configuration c) {
        addBonusDrop(ConfigItemDrop.getKey("minecraft:emerald", 1, 0, 1.0f, 0.0f, 1.0f));
        addBonusDrop(ConfigItemDrop.getKey("FunOres:AlloyIngot", 1, 2, 0.15f, 0.05f, 0.7f));

        c.getStringList(KEY_DROPS_BONUS, CATEGORY_EXAMPLE, bonusDropKeys.toArray(new String[]{}),
                COMMENT_DROP);
        pick = c.getInt(KEY_PICK, CATEGORY_EXAMPLE, 0, PICK_MIN, PICK_MAX, COMMENT_PICK);

        super.loadExample(c);

        return this;
    }

    @Override
    public ConfigOption validate() {
        pick = MathHelper.clamp(pick, PICK_MIN, PICK_MAX);
        return super.validate();
    }

    /**
     * Parses the String read from the config file into something useable.
     *
     * @param input
     * @return
     */
    private ConfigItemDrop parseItemDrop(String input, NumberFormat numberFormat) {
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

        // Get stack size and meta
        int count = -1;
        int meta = -1;
        int currentIndex = 0;
        try {
            count = parseInt(values[currentIndex = 1].trim(), numberFormat);
            meta = parseInt(values[currentIndex = 2].trim(), numberFormat);
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
            chance = parseFloat(values[currentIndex = 3].trim(), numberFormat);
            chanceBonus = parseFloat(values[currentIndex = 4].trim(), numberFormat);
            countBonus = parseFloat(values[currentIndex = 5].trim(), numberFormat);
        } catch (NumberFormatException ex) {
            String error = "Could not parse \"%s\" as float: %s";
            error = String.format(error, values[currentIndex], input);
            ConfigItemDrop.errorList.add(error);
            return null;
        }

        //FunOres.logHelper.debug(item.getUnlocalizedName(), count, meta, chance, chanceBonus, countBonus);

        // Finally, create ConfigItemDrop
        return new ConfigItemDrop(item, count, meta, chance, chanceBonus, countBonus);
    }

    private ItemStack parseStack(String input, NumberFormat numberFormat) {
        if (input.trim().toLowerCase().startsWith("null"))
            return null;

        String original = input;
        input = input.trim();
        String[] values = input.split(SPLITTER);
        for (int i = 0; i < values.length; ++i)
            values[i] = values[i].replaceFirst(",$", "");

        // Check for correctly formed key.
        if (values.length != 3) {
            String error = "Removed drop key must have 3 elements (has %d): %s";
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

        // Get stack size and meta
        int count = -1;
        int meta = -1;
        int currentIndex = 0;
        try {
            count = parseInt(values[currentIndex = 1].trim(), numberFormat);
            meta = parseInt(values[currentIndex = 2].trim(), numberFormat);
        } catch (NumberFormatException ex) {
            String error = "Could not parse \"%s\" as integer: %s";
            error = String.format(error, values[currentIndex], input);
            ConfigItemDrop.errorList.add(error);
            return null;
        }

        return new ItemStack(item, count, meta);
    }

    public int parseInt(String input, NumberFormat numberFormat) {
        ParsePosition parsePosition = new ParsePosition(0);
        Number number = numberFormat.parse(input, parsePosition);

        if (parsePosition.getIndex() != input.length()) {
            throw new NumberFormatException();
        }

        return number.intValue();
    }

    public float parseFloat(String input, NumberFormat numberFormat) {
        ParsePosition parsePosition = new ParsePosition(0);
        Number number = numberFormat.parse(input.replaceFirst(",", "."), parsePosition);

        if (parsePosition.getIndex() != input.length()) {
            throw new NumberFormatException();
        }

        return number.floatValue();
    }
}
