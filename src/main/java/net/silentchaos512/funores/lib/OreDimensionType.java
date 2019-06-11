package net.silentchaos512.funores.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.biome.TheEndBiome;

import java.util.function.Predicate;

public enum OreDimensionType {
    OVERWORLD(state -> {
        Block block = state.getBlock();
        return block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.DIORITE || block == Blocks.ANDESITE;
    }),
    NETHER(state -> state.getBlock() == Blocks.NETHERRACK),
    END(state -> state.getBlock() == Blocks.END_STONE);

    private final Predicate<BlockState> predicate;

    OreDimensionType(Predicate<BlockState> predicate) {
        this.predicate = predicate;
    }

    public Predicate<BlockState> blockToReplace() {
        return predicate;
    }

    public boolean matches(Biome biome) {
        boolean isNetherBiome = biome instanceof NetherBiome;
        if (this == NETHER) return isNetherBiome;

        boolean isEndBiome = biome instanceof TheEndBiome;
        if (this == END) return isEndBiome;

        return !isEndBiome && !isNetherBiome;
    }
}
