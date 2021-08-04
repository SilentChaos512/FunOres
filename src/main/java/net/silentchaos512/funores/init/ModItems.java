package net.silentchaos512.funores.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.ShardItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class ModItems {
    static Collection<BlockItem> blockItems = new ArrayList<>();

    private ModItems() {}

    public static void registerAll(RegistryEvent.Register<Item> event) {
        blockItems.forEach(ForgeRegistries.ITEMS::register);

        Arrays.stream(ShardItems.values()).forEach(shard -> register(shard.getName(), shard.asItem()));
    }

    private static void register(String name, Item item) {
        item.setRegistryName(FunOres.getId(name));
        ForgeRegistries.ITEMS.register(item);
    }
}
