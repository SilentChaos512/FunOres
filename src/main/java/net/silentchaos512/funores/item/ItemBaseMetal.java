package net.silentchaos512.funores.item;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.IMetal;

import java.util.List;
import java.util.Map;

public abstract class ItemBaseMetal extends ItemBaseDisableable {
    String modelName;
    String oreDictPrefix;

    public ItemBaseMetal(String name, String modelName, String oreDictPrefix) {
        super(32, name); // Exact value of subItemCount doesn't matter, just needs to be > 1.
        this.modelName = modelName;
        this.oreDictPrefix = oreDictPrefix;
    }

    /**
     * Get a list of IMetals that represent the sub-items. For basic metals, this is everything in
     * EnumMetal and, in most cases, EnumVanillaMetal. For alloys, it's everything in EnumAlloy.
     */
    public abstract List<IMetal> getMetals(Item item);

    @Override
    public List<ItemStack> getSubItems(Item item) {
        List<ItemStack> list = Lists.newArrayList();
        for (IMetal metal : getMetals(item))
            list.add(new ItemStack(item, 1, metal.getMeta()));
        return list;
    }

    @Override
    public void getModels(Map<Integer, ModelResourceLocation> models) {
        String prefix = FunOres.MOD_ID + ":" + modelName;

        for (IMetal metal : getMetals(this)) {
            // Add the model, if it's not disabled.
            if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, metal.getMeta())))
                models.put(metal.getMeta(), new ModelResourceLocation((prefix + metal.getMetalName()).toLowerCase(), "inventory"));
        }
    }

    @Override
    public void addOreDict() {
        for (IMetal metal : getMetals(this)) {
            ItemStack stack = new ItemStack(this, 1, metal.getMeta());
            if (!FunOres.registry.isItemDisabled(stack)) {
                String name = oreDictPrefix + metal.getMetalName();
                OreDictionary.registerOre(name, stack);
            }
        }
    }
}
