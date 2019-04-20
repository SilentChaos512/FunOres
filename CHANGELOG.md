# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
