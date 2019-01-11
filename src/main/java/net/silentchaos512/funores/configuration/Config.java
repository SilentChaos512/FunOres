/*
 * Fun Ores -- Config
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

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.init.ModItems;
import net.silentchaos512.funores.lib.*;

import javax.annotation.Nonnull;
import java.io.File;

public class Config {
    private static Configuration c;

    public static final String CATEGORY_METAL_ORE = "MetalOre";
    public static final String CATEGORY_MEAT_ORE = "MeatOre";
    public static final String CATEGORY_MOB_ORE = "MobOre";
    public static final String CATEGORY_VANILLA_ORE = "vanilla_ore";
    public static final String CATEGORY_RECIPE = "Recipe";
    public static final String CATEGORY_ITEM_DISABLE = "item_disable";
    public static final String CATEGORY_MISC = "Misc";
    public static final String CATEGORY_DEBUG = CATEGORY_MISC + c.CATEGORY_SPLITTER + "Debug";
    public static final String CATEGORY_RECIPE_ALLOY_SMELTER = "recipe_alloy_smelter";
    public static final String CATEGORY_QUICK_TWEAKS = "_quick_tweaks";

    public static final String COMMENT_EXAMPLE = "An example ore with comments on each individual setting.";
    public static final String COMMENT_METAL_ORE = "The metal ores like copper and titanium.";
    public static final String COMMENT_MEAT_ORE = "The meat (passive mob) ores.";
    public static final String COMMENT_MOB_ORE = "The mob (hostile mob) ores.";
    public static final String COMMENT_VANILLA_ORE = "Fun Ores can optionally add to or replace vanilla ores. By default, these are all disabled. Defaults if\n"
            + "enabled are set to add to vanilla generation, rather than replace it.";
    public static final String COMMENT_ITEM_DISABLE = "\"Disables\" specific items. Disabled items have their crafting recipes and ore dictionary entries removed\n"
            + "and are hidden from JEI and creative tabs, but the items still exists.";
    public static final String COMMENT_RECIPE_ALLOY_SMELTER = "You can disable alloy smelter recipes here. Set to false to disable the recipe.";
    public static final String COMMENT_QUICK_TWEAKS = "Some settings to quickly make large changes to the mod.";
    public static final String COMMENT_MISC = "Random settings that don't fit anywhere else!";

    public static void init(File file) {
        c = new Configuration(file);
        try {
            c.load();
//            ExtraRecipes.config = c;
//
//            /*
//             * Quick tweaks
//             */
//
//            c.setCategoryComment(CATEGORY_QUICK_TWEAKS, COMMENT_QUICK_TWEAKS);
//
//            disableMetalOres = c.getBoolean("DisableMetalOres", CATEGORY_QUICK_TWEAKS, disableMetalOres,
//                    "Disable all metal ores (copper, tin, etc.)");
//            disableMeatOres = c.getBoolean("DisableMeatOres", CATEGORY_QUICK_TWEAKS, disableMeatOres,
//                    "Disable all meat (passive mob) ores.");
//            disableMobOres = c.getBoolean("DisableMobOres", CATEGORY_QUICK_TWEAKS, disableMobOres,
//                    "Disable all mob (hostile mob) ores.");
//
//            disableMachines = c.getBoolean("Disable Machines", CATEGORY_QUICK_TWEAKS, disableMachines,
//                    "Disable all machine blocks.");
//
//            disableMetalBlocks = c.getBoolean("Disable Metal Blocks", CATEGORY_QUICK_TWEAKS,
//                    disableMetalBlocks, "Disables all metal (non-alloy) blocks.");
//            disableMetalIngots = c.getBoolean("Disable Metal Ingots", CATEGORY_QUICK_TWEAKS,
//                    disableMetalIngots, "Disables all metal (non-alloy) ingots.");
//            disableMetalNuggets = c.getBoolean("Disable Metal Nuggets", CATEGORY_QUICK_TWEAKS,
//                    disableMetalNuggets, "Disable all metal (non-alloy) nuggets.");
//            disableMetalDusts = c.getBoolean("Disable Metal Dusts", CATEGORY_QUICK_TWEAKS,
//                    disableMetalDusts, "Disable all metal (non-alloy) dusts.");
//            disableMetalPlates = c.getBoolean("Disable Metal Plates", CATEGORY_QUICK_TWEAKS,
//                    disableMetalPlates, "Disable all metal (non-alloy) plates.");
//            disableMetalGears = c.getBoolean("Disable Metal Gears", CATEGORY_QUICK_TWEAKS,
//                    disableMetalGears, "Disable all metal (non-alloy) gears.");
//
//            disableAlloyBlocks = c.getBoolean("Disable Alloy Blocks", CATEGORY_QUICK_TWEAKS,
//                    disableAlloyBlocks, "Disables all alloy blocks.");
//            disableAlloyIngots = c.getBoolean("Disable Alloy Ingots", CATEGORY_QUICK_TWEAKS,
//                    disableAlloyIngots, "Disables all alloy ingots.");
//            disableAlloyNuggets = c.getBoolean("Disable Alloy Nuggets", CATEGORY_QUICK_TWEAKS,
//                    disableAlloyNuggets, "Disable all alloy nuggets.");
//            disableAlloyDusts = c.getBoolean("Disable Alloy Dusts", CATEGORY_QUICK_TWEAKS,
//                    disableAlloyDusts, "Disable all alloy dusts.");
//            disableAlloyPlates = c.getBoolean("Disable Alloy Plates", CATEGORY_QUICK_TWEAKS,
//                    disableAlloyPlates, "Disable all alloy plates.");
//            disableAlloyGears = c.getBoolean("Disable Alloy Gears", CATEGORY_QUICK_TWEAKS,
//                    disableAlloyGears, "Disable all alloy gears.");
//
//            disableFoods = c.getBoolean("Disable Foods", CATEGORY_QUICK_TWEAKS, disableFoods,
//                    "Disable all foods.");
//            disableShards = c.getBoolean("Disable Shards", CATEGORY_QUICK_TWEAKS, disableShards,
//                    "Disable all shards.");
//
//            /*
//             * Misc configs
//             */
//
//            c.setCategoryComment(CATEGORY_MISC, COMMENT_MISC);
//
//            machinesCanBurn = c.getBoolean("MachinesCanBurn", CATEGORY_MISC, machinesCanBurn,
//                    "If true, active machines can damage entities that step on top of them. Ouch.");
//            oneIngotPlates = c.getBoolean("One Ingot Per Plate", CATEGORY_MISC, oneIngotPlates,
//                    "Change plate recipes to require only one ingot instead of two.");
//            oreGenBiomeFavorsMultiplier = c.getFloat("OreGenFavorsBiomeMultiplier", CATEGORY_MISC,
//                    oreGenBiomeFavorsMultiplier, 0.01f, 100f,
//                    "When ores favor certain biomes the number of clusters (veins) is multiplied by this, or divided by this if it avoids the biome.");
//
//            // Spawn chances
//            spawnEndermiteChance = c.getFloat("EndermiteSpawnChance",
//                    CATEGORY_MOB_ORE + Configuration.CATEGORY_SPLITTER + "enderman", spawnEndermiteChance, 0f,
//                    1f, "The chance that enderman ore will spawn an endermite when mined.");
//            spawnBatChance = c.getFloat("BatSpawnChance",
//                    CATEGORY_MEAT_ORE + Configuration.CATEGORY_SPLITTER + "bat", spawnBatChance, 0f, 1f,
//                    "The chance that bat ore will spawn a bat when mined.");
//            // Fish filter config
//            fishOreDropFishOnly = c.getBoolean("DropFishOnly", CATEGORY_MEAT_ORE + Configuration.CATEGORY_SPLITTER + "fish",
//                    false, "Fish ore will attempt to drop only fish (results with some mods or customized loot table may vary)");
//
//            // Debug
//            printWorldGenTime = c.getBoolean("PrintWorldGenTime", CATEGORY_DEBUG, printWorldGenTime,
//                    "Logs the time the Fun Ores world generator takes to do its generation in each chunk. Also tracks min, max, and average times.");
//            logOrePlacement = c.getBoolean("LogOrePlacement", CATEGORY_DEBUG, logOrePlacement,
//                    "Logs where ores are spawning. This will probably slow down your game!");
//
//            /*
//             * Example Ore
//             */
//
//            c.setCategoryComment(ConfigOptionOreGen.CATEGORY_EXAMPLE, COMMENT_EXAMPLE);
//
//            (new ConfigOptionOreGenBonus(true)).loadValue(c, "");
//
//            /*
//             * Metal Ores
//             */
//
//            c.setCategoryComment(CATEGORY_METAL_ORE, COMMENT_METAL_ORE);
//
//            copper.clusterCount = 10;
//            copper.clusterSize = 8;
//            copper.minY = 40;
//            copper.maxY = 75;
//            copper.rarity = 1;
//            copper.loadValue(c, CATEGORY_METAL_ORE);
//
//            tin.clusterCount = 10;
//            tin.clusterSize = 8;
//            tin.minY = 20;
//            tin.maxY = 55;
//            tin.rarity = 1;
//            tin.loadValue(c, CATEGORY_METAL_ORE);
//
//            silver.clusterCount = 6;
//            silver.clusterSize = 8;
//            silver.minY = 5;
//            silver.maxY = 30;
//            silver.rarity = 1;
//            silver.loadValue(c, CATEGORY_METAL_ORE);
//
//            lead.clusterCount = 4;
//            lead.clusterSize = 8;
//            lead.minY = 10;
//            lead.maxY = 35;
//            lead.rarity = 1;
//            lead.loadValue(c, CATEGORY_METAL_ORE);
//
//            nickel.clusterCount = 2;
//            nickel.clusterSize = 4;
//            nickel.minY = 5;
//            nickel.maxY = 20;
//            nickel.rarity = 1;
//            nickel.loadValue(c, CATEGORY_METAL_ORE);
//
//            platinum.clusterCount = 4;
//            platinum.clusterSize = 6;
//            platinum.minY = 5;
//            platinum.maxY = 15;
//            platinum.rarity = 10;
//            platinum.loadValue(c, CATEGORY_METAL_ORE);
//
//            aluminium.clusterCount = 6;
//            aluminium.clusterSize = 7;
//            aluminium.minY = 10;
//            aluminium.maxY = 50;
//            aluminium.rarity = 1;
//            aluminium.loadValue(c, CATEGORY_METAL_ORE);
//
//            zinc.clusterCount = 4;
//            zinc.clusterSize = 8;
//            zinc.minY = 20;
//            zinc.maxY = 70;
//            zinc.rarity = 1;
//            zinc.loadValue(c, CATEGORY_METAL_ORE);
//
//            titanium.clusterCount = 3;
//            titanium.clusterSize = 6;
//            titanium.minY = 5;
//            titanium.maxY = 20;
//            titanium.rarity = 10;
//            titanium.loadValue(c, CATEGORY_METAL_ORE);
//
//            osmium.clusterCount = 7;
//            osmium.clusterSize = 8;
//            osmium.minY = 8;
//            osmium.maxY = 64;
//            osmium.loadValue(c, CATEGORY_METAL_ORE);
//
//            /*
//             * Meat Ores
//             */
//
//            c.setCategoryComment(CATEGORY_MEAT_ORE, COMMENT_MEAT_ORE);
//
//            int meatClusterCount = 1;
//            int meatClusterSize = 15;
//            int meatMinY = 32;
//            int meatMaxY = 96;
//            int meatRarity = 14;
//
//            pig.clusterCount = meatClusterCount;
//            pig.clusterSize = meatClusterSize;
//            pig.minY = meatMinY;
//            pig.maxY = meatMaxY;
//            pig.rarity = meatRarity;
//            pig.addBonusDrop(ConfigItemDrop.getKey("minecraft:saddle", 1, 0, 0.025f, 0.01f, 0.0f));
//            pig.loadValue(c, CATEGORY_MEAT_ORE);
//
//            fish.clusterCount = meatClusterCount;
//            fish.clusterSize = meatClusterSize;
//            fish.minY = meatMinY;
//            fish.maxY = meatMaxY;
//            fish.rarity = meatRarity;
//            fish.loadValue(c, CATEGORY_MEAT_ORE);
//
//            cow.clusterCount = meatClusterCount;
//            cow.clusterSize = meatClusterSize;
//            cow.minY = meatMinY;
//            cow.maxY = meatMaxY;
//            cow.rarity = meatRarity;
//            cow.loadValue(c, CATEGORY_MEAT_ORE);
//
//            chicken.clusterCount = meatClusterCount;
//            chicken.clusterSize = meatClusterSize;
//            chicken.minY = meatMinY;
//            chicken.maxY = meatMaxY;
//            chicken.rarity = meatRarity;
//            chicken.addBonusDrop(ConfigItemDrop.getKey("minecraft:egg", 2, 0, 0.15f, 0.02f, 0.7f));
//            chicken.loadValue(c, CATEGORY_MEAT_ORE);
//
//            rabbit.clusterCount = meatClusterCount;
//            rabbit.clusterSize = meatClusterSize;
//            rabbit.minY = meatMinY;
//            rabbit.maxY = meatMaxY;
//            rabbit.rarity = meatRarity;
//            rabbit.loadValue(c, CATEGORY_MEAT_ORE);
//
//            sheep.clusterCount = meatClusterCount;
//            sheep.clusterSize = meatClusterSize;
//            sheep.minY = meatMinY;
//            sheep.maxY = meatMaxY;
//            sheep.rarity = meatRarity;
//            sheep.loadValue(c, CATEGORY_MEAT_ORE);
//
//            squid.clusterCount = meatClusterCount;
//            squid.clusterSize = meatClusterSize;
//            squid.minY = meatMinY;
//            squid.maxY = meatMaxY;
//            squid.rarity = meatRarity;
//            squid.loadValue(c, CATEGORY_MEAT_ORE);
//
//            bat.clusterCount = meatClusterCount;
//            bat.clusterSize = meatClusterSize;
//            bat.minY = meatMinY;
//            bat.maxY = meatMaxY;
//            bat.rarity = meatRarity;
//            bat.addBonusDrop(ConfigItemDrop.getKey("minecraft:wool", 1, EnumDyeColor.BROWN.getMetadata(),
//                    1f, 0f, 0.5f));
//            bat.loadValue(c, CATEGORY_MEAT_ORE);
//
//            /*
//             * Mob Ores
//             */
//
//            c.setCategoryComment(CATEGORY_MOB_ORE, COMMENT_MOB_ORE);
//
//            int mobClusterCount = 1;
//            int mobClusterSize = 17;
//            int mobMinY = 24;
//            int mobMaxY = 86;
//            int mobRarity = 20;
//
//            // Overworld
//
//            zombie.clusterCount = mobClusterCount;
//            zombie.clusterSize = mobClusterSize;
//            zombie.minY = mobMinY;
//            zombie.maxY = mobMaxY;
//            zombie.rarity = mobRarity;
//            zombie.loadValue(c, CATEGORY_MOB_ORE);
//
//            skeleton.clusterCount = mobClusterCount;
//            skeleton.clusterSize = mobClusterSize;
//            skeleton.minY = mobMinY;
//            skeleton.maxY = mobMaxY;
//            skeleton.rarity = mobRarity;
//            skeleton.loadValue(c, CATEGORY_MOB_ORE);
//
//            creeper.clusterCount = mobClusterCount;
//            creeper.clusterSize = mobClusterSize;
//            creeper.minY = mobMinY;
//            creeper.maxY = mobMaxY;
//            creeper.rarity = mobRarity;
//            creeper.loadValue(c, CATEGORY_MOB_ORE);
//
//            spider.clusterCount = mobClusterCount;
//            spider.clusterSize = mobClusterSize;
//            spider.minY = mobMinY;
//            spider.maxY = mobMaxY;
//            spider.rarity = mobRarity;
//            spider.loadValue(c, CATEGORY_MOB_ORE);
//
//            enderman.clusterCount = mobClusterCount;
//            enderman.clusterSize = mobClusterSize;
//            enderman.minY = mobMinY;
//            enderman.maxY = mobMaxY;
//            enderman.rarity = mobRarity;
//            enderman.removeStandardDrop("minecraft:ender_pearl 1 0");
//            enderman.addBonusDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 0, 1.0f, 0.0f, 0.7f));
//            enderman.addBonusDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 0, 0.10f, 0.0f, 0.4f));
//            enderman.loadValue(c, CATEGORY_MOB_ORE);
//
//            slime.clusterCount = mobClusterCount;
//            slime.clusterSize = mobClusterSize;
//            slime.minY = mobMinY;
//            slime.maxY = mobMaxY;
//            slime.rarity = mobRarity;
//            slime.loadValue(c, CATEGORY_MOB_ORE);
//
//            witch.clusterCount = mobClusterCount;
//            witch.clusterSize = mobClusterSize;
//            witch.minY = mobMinY;
//            witch.maxY = mobMaxY;
//            witch.rarity = mobRarity;
//            witch.pick = 2;
//            witch.loadValue(c, CATEGORY_MOB_ORE);
//
//            guardian.clusterCount = mobClusterCount;
//            guardian.clusterSize = mobClusterSize;
//            guardian.minY = mobMinY;
//            guardian.maxY = mobMaxY;
//            guardian.rarity = mobRarity;
//            guardian.addBonusDrop(ConfigItemDrop.getKey("minecraft:sponge", 1, 0, 0.005f, 0.0005f, 0f));
//            guardian.loadValue(c, CATEGORY_MOB_ORE);
//
//            // Nether
//
//            mobClusterCount = 2;
//            mobClusterSize = 17;
//            mobMinY = 48;
//            mobMaxY = 116;
//            mobRarity = 24;
//
//            pigman.clusterCount = mobClusterCount;
//            pigman.clusterSize = mobClusterSize;
//            pigman.minY = mobMinY;
//            pigman.maxY = mobMaxY;
//            pigman.rarity = mobRarity;
//            pigman.loadValue(c, CATEGORY_MOB_ORE);
//
//            ghast.clusterCount = mobClusterCount;
//            ghast.clusterSize = mobClusterSize;
//            ghast.minY = mobMinY;
//            ghast.maxY = mobMaxY;
//            ghast.rarity = mobRarity;
//            ghast.removeStandardDrop("minecraft:ghast_tear 1 0");
//            ghast.addBonusDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 2, 1.0f, 0.00f, 0.7f));
//            ghast.addBonusDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 2, 0.25f, 0.0f, 0.4f));
//            ghast.loadValue(c, CATEGORY_MOB_ORE);
//
//            magmaCube.clusterCount = mobClusterCount;
//            magmaCube.clusterSize = mobClusterSize;
//            magmaCube.minY = mobMinY;
//            magmaCube.maxY = mobMaxY;
//            magmaCube.rarity = mobRarity;
//            magmaCube.loadValue(c, CATEGORY_MOB_ORE);
//
//            wither.clusterCount = mobClusterCount;
//            wither.clusterSize = mobClusterSize;
//            wither.minY = mobMinY;
//            wither.maxY = mobMaxY;
//            wither.rarity = mobRarity;
//            wither.loadValue(c, CATEGORY_MOB_ORE);
//
//            blaze.clusterCount = mobClusterCount;
//            blaze.clusterSize = mobClusterSize;
//            blaze.minY = mobMinY;
//            blaze.maxY = mobMaxY;
//            blaze.rarity = mobRarity;
//            blaze.removeStandardDrop("minecraft:blaze_rod 1 0");
//            blaze.addBonusDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 1, 1.0f, 0.0f, 0.7f));
//            blaze.addBonusDrop(ConfigItemDrop.getKey("FunOres:Shard", 1, 1, 0.10f, 0.0f, 0.4f));
//            blaze.loadValue(c, CATEGORY_MOB_ORE);
//
//            // Vanilla Ores
//            // Default settings are set so that bonus ores will spawn if enabled, not replacements.
//
//            c.setCategoryComment(CATEGORY_VANILLA_ORE, COMMENT_VANILLA_ORE);
//
//            iron.clusterCount = 4;
//            iron.maxY = 64;
//            iron.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            gold.clusterCount = 4;
//            gold.maxY = 32;
//            gold.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            diamond.clusterCount = 1;
//            diamond.maxY = 16;
//            diamond.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            emerald.clusterCount = 2;
//            emerald.clusterSize = 1;
//            emerald.minY = 8;
//            emerald.maxY = 32;
//            emerald.rarity = 24;
//            emerald.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            coal.clusterCount = 0;
//            coal.clusterSize = 16;
//            coal.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            redstone.clusterCount = 2;
//            redstone.maxY = 16;
//            redstone.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            lapis.clusterCount = 0;
//            lapis.maxY = 16;
//            lapis.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            quartz.clusterCount = 0;
//            quartz.clusterSize = 13;
//            quartz.loadValue(c, CATEGORY_VANILLA_ORE);
//
//            c.setCategoryComment(CATEGORY_ITEM_DISABLE, COMMENT_ITEM_DISABLE);

        } catch (Exception e) {
//            FunOres.logHelper.error("Oh noes!!! Couldn't load configuration file properly!");
//            FunOres.logHelper.catching(e);
        }
    }

    public static void save() {
        if (c.hasChanged()) {
            c.save();
        }
    }

    public static Configuration getConfiguration() {
        return c;
    }
}
