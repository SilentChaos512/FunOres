package net.silentchaos512.funores.lib;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.silentchaos512.funores.init.ModItems;

import java.util.Locale;

public enum EnumDriedItem implements IStringSerializable {
    DRIED_FLESH(0, "DriedFlesh", 4, 0.2f, "jerkyflesh"),
    BEEF_JERKY(1, "BeefJerky", 8, 0.8f, "jerkybeef"),
    CHICKEN_JERKY(2, "ChickenJerky", 6, 0.6f, "jerkychicken"),
    PORK_JERKY(3, "PorkJerky", 8, 0.8f, "jerkypork"),
    MUTTON_JERKY(4, "MuttonJerky", 6, 0.8f, "jerkymutton"),
    RABBIT_JERKY(5, "RabbitJerky", 5, 0.6f, "jerkyrabbit"),
    COD_JERKY(6, "CodJerky", 5, 0.6f, "jerkycod"),
    SALMON_JERKY(7, "SalmonJerky", 6, 0.8f, "jerkysalmon");

    public final int meta;
    public final String name;
    public final int foodValue;
    public final float saturationValue;
    public final int useDuration;
    public final String textureName;

    EnumDriedItem(int meta, String name, int food, float saturation, String textureName) {
        this.meta = meta;
        this.name = name;
        this.foodValue = food;
        this.saturationValue = saturation;
        this.useDuration = 32;
        this.textureName = textureName;
    }

    public ItemStack getItem() {
        return new ItemStack(ModItems.driedItem, 1, meta);
    }

    @Override
    public String getName() {
        return name.toLowerCase(Locale.ROOT);
    }
}
