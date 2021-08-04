package net.silentchaos512.funores.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public final class DataGenerators {
    private DataGenerators() {}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModLootTables(gen));

        gen.addProvider(new ModBlockStatesProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelsProvider(gen, existingFileHelper));
    }
}
