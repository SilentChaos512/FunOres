package net.silentchaos512.funores.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Ores;

import java.util.Arrays;

public final class ModBlocks {
    private ModBlocks() {}

    public static void registerAll(RegistryEvent.Register<Block> event) {
        Arrays.stream(Ores.values()).forEach(ore -> register(ore.getBlockName(), ore.asBlock()));
    }

    private static void register(String name, Block block) {
        ResourceLocation registryName = new ResourceLocation(FunOres.MOD_ID, name);
        block.setRegistryName(registryName);
        ForgeRegistries.BLOCKS.register(block);

        BlockItem item = new BlockItem(block, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
        item.setRegistryName(registryName);
        ModItems.blockItems.add(item);
    }
}
