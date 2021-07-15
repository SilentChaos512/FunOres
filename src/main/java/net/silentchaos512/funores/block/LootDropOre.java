package net.silentchaos512.funores.block;

import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;

@Deprecated
public class LootDropOre extends OreBlock {
    public LootDropOre() {
        super(Properties.of(Material.STONE).strength(1.5f, 10f));
    }

}
