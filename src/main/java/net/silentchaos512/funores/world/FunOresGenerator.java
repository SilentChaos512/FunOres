package net.silentchaos512.funores.world;

public class FunOresGenerator /*extends WorldGeneratorSL*/ {
//
//    private double debugMinTime = 1000000d;
//    private double debugMaxTime = 0d;
//    private double debugTotalTime = 0d;
//    private int debugChunkGenCount = 0;
//
//    public FunOresGenerator() {
//        super(true, FunOres.MOD_ID + "_retrogen");
//    }
//
//    private void printDebugInfo(int chunkX, int chunkZ, long timeStart) {
//        if (Config.printWorldGenTime) {
//            double timeTaken = (double) (System.nanoTime() - timeStart) / 1000000;
//            debugMinTime = timeTaken < debugMinTime ? timeTaken : debugMinTime;
//            debugMaxTime = timeTaken > debugMaxTime ? timeTaken : debugMaxTime;
//            debugTotalTime += timeTaken;
//            ++debugChunkGenCount;
//            double avgTime = debugTotalTime / debugChunkGenCount;
//            FunOres.logHelper.info("Chunk ({}, {}) took {} ms to generate (min = {}, max = {}, avg = {})",
//                    chunkX, chunkZ, timeTaken, debugMinTime, debugMaxTime, avgTime);
//        }
//    }
//
//    @Override
//    protected boolean generateForDimension(final int dim, World world, Random random, int posX, int posZ) {
//        long timeStart = System.nanoTime();
//
//        // Vanilla
//        for (EnumVanillaOre vanilla : EnumVanillaOre.values()) {
//            if (vanilla.dimension == dim || vanilla.dimension == 0) {
//                generateOre(vanilla.getConfig(), world, random, posX, posZ);
//            }
//        }
//        // Metal
//        if (!Config.disableMetalOres) {
//            for (EnumMetal metal : EnumMetal.values()) {
//                if (metal.dimension == dim || metal.dimension == 0) {
//                    generateOre(metal.getConfig(), world, random, posX, posZ);
//                }
//            }
//        }
//        // Meat
//        if (!Config.disableMeatOres) {
//            for (EnumMeat meat : EnumMeat.values()) {
//                if (meat.dimension == dim || meat.dimension == 0) {
//                    generateOre(meat.getConfig(), world, random, posX, posZ);
//                }
//            }
//        }
//        // Mob
//        if (!Config.disableMobOres) {
//            for (EnumMob mob : EnumMob.values()) {
//                if (mob.dimension == dim || mob.dimension == 0) {
//                    generateOre(mob.getConfig(), world, random, posX, posZ);
//                }
//            }
//        }
//
//        printDebugInfo(posX / 16, posZ / 16, timeStart);
//
//        return true;
//    }
//
//    public void generateOre(ConfigOptionOreGen ore, World world, Random random, int posX, int posZ) {
//        if (!ore.isEnabled()) {
//            return;
//        }
//
//        Biome biome = getBiomeForPos(world, new BlockPos(posX, 64, posZ));
//        if (ore.canSpawnInBiome(biome)) {
//            float trueClusterCount = ore.getClusterCountForBiome(biome);
//            int clusterCount = trueClusterCount > 0 && trueClusterCount < 1 ? 1 : (int) trueClusterCount;
//            float bonusClusterChance = trueClusterCount - clusterCount;
//            if (random.nextFloat() < bonusClusterChance) {
//                ++clusterCount;
//            }
//
//            int numSpawned = 0;
//
//            int x, y, z;
//            for (int i = 0; i < clusterCount; ++i) {
//                if (random.nextInt(ore.rarity) == 0) {
//                    x = posX + random.nextInt(16);
//                    y = ore.minY + random.nextInt(ore.maxY - ore.minY + 1);
//                    z = posZ + random.nextInt(16);
//
//                    BlockPos pos = new BlockPos(x, y, z);
//                    IBlockState state = ore.ore.getOre();
//                    IBlockState targetState = world.getBlockState(pos);
//
//                    new WorldGenMinable(state, ore.clusterSize, ore.predicate).generate(world, random, pos);
//
//                    // Log placement?
//                    if (Config.logOrePlacement && ore.predicate.apply(targetState)) {
//                        if (numSpawned == 0) {
//                            String str = "Trying to spawn %d veins of %s Ore in chunk (%d, %d)";
//                            str = String.format(str, clusterCount, ore.oreName, posX / 16, posZ / 16);
//                            FunOres.logHelper.info(str);
//                        }
//                        String str = "%s %d %d %d";
//                        str = String.format(str, ore.oreName, pos.getX(), pos.getY(), pos.getZ());
//                        FunOres.logHelper.info(str);
//                    }
//
//                    ++numSpawned;
//                }
//            }
//        }
//    }
//
//    public static Biome getBiomeForPos(World world, BlockPos pos) {
//        // Get biome at center of chunk
//        int posX = (pos.getX() & 0xFFFFFFF0) + 8;
//        int posZ = (pos.getZ() & 0xFFFFFFF0) + 8;
//        BlockPos center = new BlockPos(posX, 64, posZ);
//        return world.getBiome(center);
//    }
//
//    @SubscribeEvent
//    public void onGenerateMinable(OreGenEvent.GenerateMinable event) {
//        if ((event.getType() == EventType.COAL && Config.coal.replaceExisting)
//                || (event.getType() == EventType.DIAMOND && Config.diamond.replaceExisting)
//                || (event.getType() == EventType.EMERALD && Config.emerald.replaceExisting)
//                || (event.getType() == EventType.GOLD && Config.gold.replaceExisting)
//                || (event.getType() == EventType.IRON && Config.iron.replaceExisting)
//                || (event.getType() == EventType.LAPIS && Config.lapis.replaceExisting)
//                || (event.getType() == EventType.QUARTZ && Config.quartz.replaceExisting)
//                || (event.getType() == EventType.REDSTONE && Config.redstone.replaceExisting))
//            event.setResult(Result.DENY);
//
//    }
}
