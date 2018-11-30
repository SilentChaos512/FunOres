# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Fixed
- Hot machines not hurting anything

Below is the old changelog format (prior to 2018-11-30):

1.6.1
Fixed: Server crash (#43)

1.6.0
No longer compatible with older Minecraft versions, 1.12.2 only!
Updated for latest Silent Lib 2.3.15 (major code cleanup)
Changed: Item disable configs now use translation keys instead of translated names (you may need to update your config file)
Fixed: Updated JEI plugin, removed deprecated method calls
Fixed: Drying rack item model

1.5.6
Fixed: [1.12] Container GUIs not rendering tooltips.
Fixed: [1.10.2] Crash when removing items from the alloy smelter's output slot.

1.5.2b
Fixed: Vanilla ores not respecting the "ReplaceExisting" config.
Fixed: Vanilla ores not reporting vein counts in WIT.

1.5.2
Changed: Additional vanilla ore generation is now enabled by default (existing config files will not changed). It adds to some vanilla ores, but does not replace them.
Fixed: The "ReplaceExisting" option for vanilla ores not being included on first config generation.
Fixed: [1.10.2] Crash when trying to use creative tab.

1.5.1
Fixed: A crash when using biome lists in ore gen configs in 1.10.2 (requires Silent Lib 2.0.2)

1.5.0
Updated for Silent Lib 2. Cross compatible between Minecraft 1.10.2, 1.11, and 1.11.2.
Changed: Dried Flesh to Leather now requires just two Dried Flesh.

1.4.2
Fixed: Crash when blocks/items are disabled and JEI is not installed.

1.4.1
Added: Config to change plate recipes to require one ingot.
Fixed: Biome lists for ore generation not working in some cases. The old "BiomeTypes" list will be removed from the config.

1.4.0 (Minecraft 1.11)
Changed: Biome list config for ores now work better. You can list specific biomes now. Try "minecraft:forest", "minecraft:extreme_hills", etc. No quotes, of course. You can still list biome types (hot, cold, wet, dry, etc.)
Fixed: The mod not being detected. This was likely caused by changes to biome types in recent Forge versions.
Fixed: Mod ID is now lowercase... Shouldn't cause any issues.

1.3.2
Significantly improved block/item disabling.
    - Disabled blocks/items should no longer appear in creative tabs (let me know if any still show up).
    - Machines can now be disabled.
    - Disabled ores are now hidden.
    - Less copy-pasted code should make behavior more consistent.
    - The models for disabled blocks/items are no longer loaded. Hurray for SLIGHTLY less memory usage...?
Removed: Drop list for meat/mob ores, as it no longer works.
Fixed: A crash when an ore has no bonus drops assigned, but pick count is set higher than zero.
Fixed: A crash with drying racks involving Minecraft Comes Alive.
Fixed: Iron nugget and iron/gold dust not appearing in JEI.

1.3.1
Added: Config options for meat/mob ores to remove specific loot table drops and add bonus drops. This should allow full customization of ore drops. By default, ender pearls, blaze rods, and ghast tears are replaced by their respective shards once again. All missing drops are back, including eggs from chicken ore. The old "S:Drops" config is no longer used and may be removed.
Added: Wood/stone/obsidian/diamond/emerald gears and obsidian/diamond/emerald plates. (Yes, I know the wood/stone gear textures are horrible at the moment. Contributors?)
Added: Chinese localization file (courtesy of Zero_Exact)

1.3.0
Added: Config options to disable many blocks and items.
Added: TCon smeltery compatibility (eww, Tinkers!)
Changed: Meat and Mob ores now use the loot tables of their respective mobs. Custom drops added in the config will not work for now.
Changed: 1.9.4 source should now be 1.10.x compatible (less work for me, yay!)

1.2.7
Fixed: Drying Racks on servers.

1.2.6
Updated for Silent Lib 1.0.9
Added: Sag Mill recipes (Ender IO) for metal ores.

1.2.3
Added: Iron and gold dust. Fixes some warnings from JEI in some cases.
Changed: Metal Furnace now gives a bonus for ores that smelt into gems (example: Chaos Ore from Silent's Gems)
Fixed: Meat and Mob ores not rendering correctly in inventories/JEI for some players (jriwanek)
Fixed: Most item models.

1.2.2
Updated for Forge 1833.

1.2.1
Updated for Forge 1805+.
Added: Disabled tooltip for metal ores.
Fixed: Shard localizations.

1.2.0
Ported to 1.9

1.1.3
Fixed: Ores not spawning in mod dimensions.

1.1.2
Added: Bat ore. It often spawns bats when mined.
Fixed: Drying rack desync on item removal. Probably.

1.1.1b
Maybe fixed an issue with high-rarity ores not being affecting by favors/avoids biome settings.

1.1.1
Added: Drying Rack recipe handler for JEI
Added (API): Drying Rack recipe adding method
Added (API): Plate recipe adding method
Changed: Plate textures
Maybe fixed Drying Rack desync on servers?
API version 3

1.1.0-beta6
Added: The Drying Rack. It dries things. Like meat and sponges. JEI recipe handler not implemented yet.
Fixed: Machines duplicating when broken in some cases. How I fixed it, I am not sure.
Known Issues:
- Drying racks don't sync up properly with other players.

1.1.0-beta5
Added: Config to print world gen time for each chunk. This was in the previous version, but there was no config. Disabled by default.
Fixed: Item drop keys being internationally ignorant. You should not need to change your config file, but try deleting it if you have trouble.

1.1.0-beta4
Fixed: World generator crash

1.1.0-beta3
Added: Ores can now be blacklisted/whitelisted for certain biome types, or set to favor/avoid them.
Added: WIT info for ores. Shows an estimated veins/chunk for the biome the ore is in.
Added: Missing textures and models for prismarinium.
Added: Update checker.
Changed: Improved(?) gear textures.
Changed: Alloy smelter XP given for all recipes.
Fixed: Hoppers often not working on alloy smelters, shift-clicking not working in some cases.
Fixed: Incorrect XP from alloy smelter.
API version 2

1.1.0-beta2
Fixed: Nether ores not spawning.

1.1.0-beta1
Added: The Alloy Smelter. It smelts alloys. All recipes can be disabled in the config, as always.
Added: Alloy Smelter JEI handler. View the recipes in JEI!
Added: New alloys: invar, electrum, enderium, and prismarinium (name and recipes may change).
Added: Alloy dust. Not sure if it's good for anything, but who knows?
Added: Gears and Plates. They are registered in the ore dictionary, of course. Textures need some work.
Added: A hammer. Currently just used to make plates, might have other uses later.
Added: Machines may be hot! Careful where you step! (configurable)
Added: The mod will now yell at you if your config contains mistakes (malformed item keys) in the ore drop settings.
Added: Configs to quickly disable all ores from one category (under _quick_tweaks at top of file)
Added: JEI descriptions for Metal Furnace and Alloy Smelter.
Added: New textures for lit metal furnace. Finally. Also animated!
Added: An API. First time doing something like this, feel free to yell (politely) if I'm doing it wrong.
Added: (API) Ability to add alloy smelter recipes.
Removed: Alloy ingot recipes (use the alloy smelter instead)
Changed: Metal furnace recipes
Fixed: Metal furnace trying and failing to smelt if secondary slot is full.
Fixed: Metal furnace failing to smelt when one output slot is emptied, but not the other.
API version 1

1.0.4
Fixed: Metal/alloy blocks not being registered in the ore dictionary.