package net.silentchaos512.funores.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.function.Function;

@Deprecated
public class LootDropOre extends OreBlock {
    public LootDropOre() {
        super(Properties.create(Material.ROCK).hardnessAndResistance(1.5f, 10f));
    }

}
