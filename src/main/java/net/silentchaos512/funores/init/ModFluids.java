/*
 * Fun Ores -- ModFluids
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.init;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockMoltenFluid;

import java.util.HashMap;
import java.util.Map;

public class ModFluids {

    static {
        FluidRegistry.enableUniversalBucket();
    }

    // public static Fluid fluidCopper;
    // public static BlockFluidBase fluidBlockCopper;
    // public static Fluid fluidTin;
    // public static BlockFluidBase fluidBlockTin;
    // public static Fluid fluidSilver;
    // public static BlockFluidBase fluidBlockSilver;
    // public static Fluid fluidLead;
    // public static BlockFluidBase fluidBlockLead;
    // public static Fluid fluidNickel;
    // public static BlockFluidBase fluidBlockNickel;
    public static Fluid fluidPlatinum;
    public static BlockFluidBase fluidBlockPlatinum;
    // public static Fluid fluidAluminium;
    // public static BlockFluidBase fluidBlockAluminium;
    // public static Fluid fluidZinc;
    // public static BlockFluidBase fluidBlockZinc;
    public static Fluid fluidTitanium;
    public static BlockFluidBase fluidBlockTitanium;

    private static final Map<Fluid, BlockFluidBase> fluidBlocks = new HashMap<>();
    private static final Map<BlockFluidBase, String> fluidBlockNames = new HashMap<>();

    public static void init() {
        if (Loader.isModLoaded("tconstruct")) {
            fluidPlatinum = newFluid("platinum", 2000, 10000, 800, 10, 0xFF81A3F0);
            fluidTitanium = newFluid("titanium", 2000, 10000, 800, 10, 0xFF4845B4);

            fluidBlockPlatinum = registerFluidBlock(fluidPlatinum, new BlockMoltenFluid(fluidPlatinum), "platinum");
            fluidBlockTitanium = registerFluidBlock(fluidTitanium, new BlockMoltenFluid(fluidTitanium), "titanium");

            registerFluidWithTCon(fluidPlatinum, true);
            registerFluidWithTCon(fluidTitanium, true);
        }
    }

    private static Fluid newFluid(String name, int density, int viscosity, int temperature, int luminosity, final int tintColor) {
        Fluid fluid = new Fluid(name, new ResourceLocation(FunOres.MOD_ID + ":blocks/molten_metal"), new ResourceLocation(FunOres.MOD_ID + ":blocks/molten_metal_flow")) {
            @Override
            public int getColor() {
                return tintColor;
            }

            @Override
            public String getLocalizedName(FluidStack stack) {
                return FunOres.i18n.translate(unlocalizedName);
            }
        };

        fluid.setDensity(density);
        fluid.setViscosity(viscosity);
        fluid.setTemperature(temperature);
        fluid.setLuminosity(luminosity);
        fluid.setUnlocalizedName(FunOres.MOD_ID + "." + name);
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
        return fluid;
    }

    private static void registerFluidWithTCon(Fluid fluid, boolean toolForge) {
        if (Loader.isModLoaded("tconstruct")) {
            NBTTagCompound message = new NBTTagCompound();
            String name = fluid.getName();
            message.setString("fluid", name);
            message.setString("ore", Character.toUpperCase(name.charAt(0)) + name.substring(1));
            message.setBoolean("toolforge", toolForge);

            // send the NBT to TCon
            FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", message);
        }
    }

    private static BlockFluidClassic registerFluidBlock(Fluid fluid, BlockFluidClassic block, String name) {
        String blockName = "Molten" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
        FunOres.registry.registerBlock(block, blockName);
        block.setTranslationKey(FunOres.RESOURCE_PREFIX + blockName);
        fluidBlocks.put(fluid, block);
        fluidBlockNames.put(block, name);
        return block;
    }

    @SideOnly(Side.CLIENT)
    public static void bakeModels() {
        for (Fluid fluid : fluidBlocks.keySet()) {
            BlockFluidBase block = fluidBlocks.get(fluid);
            Item item = Item.getItemFromBlock(block);
            String name = fluidBlockNames.get(block);
            name = "Molten" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            final ModelResourceLocation fluidModelLocation = new ModelResourceLocation(
                    FunOres.RESOURCE_PREFIX + name, "fluid");
            ModelBakery.registerItemVariants(item);
            ModelLoader.setCustomMeshDefinition(item, stack -> fluidModelLocation);
            ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

                    return fluidModelLocation;
                }
            });
        }
    }
}
