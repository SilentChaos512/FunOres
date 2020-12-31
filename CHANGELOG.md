# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.7.0] - 2020-12-30
Ported to Minecraft 1.16.3/4

## [2.6.0] - 2020-08-05
Ported to Minecraft 1.16.1
### Changed
- Loot tables redone. You can now specify different entity types to pull loot from. It's a weighted list.
### Fixed
- Silk touch not working on ores [#54]

## [2.5.1] - 2020-04-15
Fixed regression in ore textures, missing phantom ore

## [2.5.0] - 2020-04-15
Updated for Minecraft 1.15.2

## [2.4.1] - 2019-07-28
### Added
- Phantom ore and phantom membrane scraps
### Changed
- Updated ore textures (EnbyChu)

## [2.4.0] - 2019-07-23
- Updated to 1.14.4

## [2.3.1] - 2019-07-05
- Updated zh_cn.json (XuyuEre)
### Added
- Endermite spawn chance config
### Changed
- Many ore loot tables tweaked
    - All of them now reference at least one entity loot table
    - Sheep ore can give loot from any naturally-occurring fleece color
    - Added a loot function to convert certain items into shards
### Fixed
- Enderman ore not spawning endermites

## [2.3.0] - 2019-06-30
- Updated to 1.14.3

## [2.2.0] - 2019-06-11
- Updated to Minecraft 1.14.2
- Ore loot tables have moved, so you will need to update data packs if you customized them

## [2.1.1] - 2019-05-28
### Added
- Config to enable logging of ore spawns (for debugging purposes), off by default [#51]. Found in `config/funores/common.toml`.
### Changed
- Tweaked some default ore configs. These will regenerate if all ore config files are deleted.
### Fixed
- More localization file errors

## [2.1.0] - 2019-04-20
### Added
- Ore configs. This is a completely overhauled system. These are JSON files, located at `config/funores/ore_generation`. You can add as many files as you like or remove existing ones. The files will regenerate if the folder is empty. Each ore config can spawn any block you want, even multiple blocks per vein with varying weights. For example, you could add deposits of cobblestone to the world, but also make them have a few mossy cobblestone blocks sprinkled in.
### Fixed
- Block/item localizations

## [2.0.1] - 2019-03-06
### Fixed
- Shard recipes missing

## [2.0.0] - 2019-02-23
- Port to 1.13.2.
- Removes all machines and metal blocks and items. This leaves only the mob drop ores. If you want metals, you will need to look elsewhere.
- No config at the moment, I'm working on it.
- Ores now have loot tables to control their drops.
- Silent Lib is no longer required.
