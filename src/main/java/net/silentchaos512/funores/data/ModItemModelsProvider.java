package net.silentchaos512.funores.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.item.ShardItems;
import net.silentchaos512.funores.lib.Ores;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, FunOres.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Fun Ores - Item Models";
    }

    @Override
    protected void registerModels() {
        for (Ores ore : Ores.values()) {
            withExistingParent(ore.getBlockName(), modLoc("block/" + ore.getBlockName()));
        }

        for (ShardItems item : ShardItems.values()) {
            getBuilder(item.getName())
                    .parent(getExistingFile(new ResourceLocation("item/generated")))
                    .texture("layer0", modLoc("item/" + item.getName()));
        }
    }
}
