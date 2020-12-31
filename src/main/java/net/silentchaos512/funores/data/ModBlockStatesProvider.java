package net.silentchaos512.funores.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Ores;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FunOres.MOD_ID, exFileHelper);
    }

    @Override
    public String getName() {
        return "Fun Ores - Block States/Models";
    }

    @Override
    protected void registerStatesAndModels() {
        for (Ores ore : Ores.values()) {
            simpleBlock(ore.asBlock());
        }
    }
}
