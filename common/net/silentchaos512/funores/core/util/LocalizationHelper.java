package net.silentchaos512.funores.core.util;

import java.text.DecimalFormatSymbols;

import net.minecraft.util.StatCollector;

public class LocalizationHelper {

    public static final String BLOCKS_PREFIX = "tile.funores:";
    public static final String MISC_PREFIX = "misc.funores:";
    public static final String ITEM_PREFIX = "item.funores:";
    public static final String TOOL_PREFIX = "tool.funores:";
    
    public static String getBlockDescription(String blockName, int index) {
        
        return getLocalizedString(getBlockDescriptionKey(blockName, index));
    }
    
    public static String getBlockDescriptionKey(String blockName, int index) {
        
        return BLOCKS_PREFIX + blockName + ".desc" + (index > 0 ? index : "");
    }
    
    public static String getItemDescription(String itemName, int index) {
        
        return getLocalizedString(getItemDescriptionKey(itemName, index));
    }
    
    public static String getItemDescriptionKey(String itemName, int index) {
        
        return ITEM_PREFIX + itemName + ".desc" + (index > 0 ? index : "");
    }
    
    public static String getLocalizedString(String key) {

        return StatCollector.translateToLocal(key).trim();
    }

    public static String getMiscText(String key) {
        
        return getLocalizedString(MISC_PREFIX + key);
    }
    
    public static String getOtherBlockKey(String blockName, String key) {
        
        return getLocalizedString(BLOCKS_PREFIX + blockName + "." + key);
    }
    
    public static String getOtherItemKey(String itemName, String key) {
        
        return getLocalizedString(ITEM_PREFIX + itemName + "." + key);
    }
}
