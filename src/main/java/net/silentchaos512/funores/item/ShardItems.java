package net.silentchaos512.funores.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.silentchaos512.funores.loot.ReplaceWithShardsFunction;
import net.silentchaos512.utils.Lazy;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum ShardItems implements ItemLike {
    ENDER(Items.ENDER_PEARL),
    BLAZE(Items.BLAZE_ROD),
    GHAST(Items.GHAST_TEAR),
    PHANTOM_MEMBRANE(Items.PHANTOM_MEMBRANE, "scrap"),
    WITHER_SKULL(Items.WITHER_SKELETON_SKULL);

    // The shard item
    private final Lazy<Item> item;
    // The item the shards craft into
    private final ItemLike fullItem;
    private final String suffix;

    ShardItems(ItemLike fullItem) {
        this(fullItem, "shard");
    }

    ShardItems(ItemLike fullItem, String suffix) {
        this.item = Lazy.of(() -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
        this.fullItem = fullItem;
        this.suffix = suffix;
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT) + "_" + suffix;
    }

    @Nonnull
    @Override
    public Item asItem() {
        return item.get();
    }

    /**
     * Gets the item these shards craft into. This is used in {@link ReplaceWithShardsFunction}
     * to replace a mob's drops without reconstructing their entire loot table.
     *
     * @return The item the shards craft into
     */
    public ItemLike getFullItem() {
        return fullItem;
    }
}
