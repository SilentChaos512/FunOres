package net.silentchaos512.funores.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SharedConstants;
import net.silentchaos512.utils.Lazy;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum ShardItems implements IItemProvider {
    ENDER(Items.ENDER_PEARL),
    BLAZE(Items.BLAZE_ROD),
    GHAST(Items.GHAST_TEAR),
    PHANTOM_MEMBRANE(Items.PHANTOM_MEMBRANE, "scrap"),
    WITHER_SKULL(Items.WITHER_SKELETON_SKULL);

    // The shard item
    private final Lazy<Item> item;
    // The item the shards craft into
    private final IItemProvider fullItem;
    private final String suffix;

    ShardItems(IItemProvider fullItem) {
        this(fullItem, "shard");
    }

    ShardItems(IItemProvider fullItem, String suffix) {
        this.item = Lazy.of(() -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
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
     * Gets the item these shards craft into. This is used in {@link net.silentchaos512.funores.loot.function.ReplaceWithShardsFunction}
     * to replace a mob's drops without reconstructing their entire loot table.
     *
     * @return The item the shards craft into
     */
    public IItemProvider getFullItem() {
        return fullItem;
    }
}
