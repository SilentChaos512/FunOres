package net.silentchaos512.funores.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.biome.TheEndBiome;
import net.minecraft.world.gen.feature.MinableConfig;

import java.util.function.Predicate;

public enum OreDimensionType {
    OVERWORLD(MinableConfig.IS_ROCK),
    NETHER(state -> state.getBlock() == Blocks.NETHERRACK),
    END(state -> state.getBlock() == Blocks.END_STONE);

    private final Predicate<IBlockState> predicate;

    OreDimensionType(Predicate<IBlockState> predicate) {
        this.predicate = predicate;
    }

    public Predicate<IBlockState> blockToReplace() {
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
