package net.silentchaos512.funores.init;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockMoltenFluid;

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

    // if (Loader.isModLoaded("tconstruct")) {
    fluidPlatinum = newFluid("platinum", 2000, 10000, 800, 10, 0x81A3F0);
    fluidTitanium = newFluid("titanium", 2000, 10000, 800, 10, 0x4845B4);

    fluidBlockPlatinum = registerFluidBlock(fluidPlatinum, new BlockMoltenFluid(fluidPlatinum),
        "platinum");
    fluidBlockTitanium = registerFluidBlock(fluidTitanium, new BlockMoltenFluid(fluidTitanium),
        "titanium");
    // }
  }

  private static Fluid newFluid(String name, int density, int viscosity, int temperature,
      int luminosity, int tintColor) {

    Fluid fluid = new Fluid(name, new ResourceLocation(FunOres.MOD_ID + ":blocks/molten_metal"),
        new ResourceLocation(FunOres.MOD_ID + ":blocks/molten_metal_flow")) {

      @Override
      public int getColor() {

        return tintColor;
      }

      @Override
      public String getLocalizedName(FluidStack stack) {

        return FunOres.localizationHelper.getLocalizedString(unlocalizedName);
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

  private static BlockFluidClassic registerFluidBlock(Fluid fluid, BlockFluidClassic block,
      String name) {

    FunOres.registry.registerBlock(block, name);
    block.setUnlocalizedName(FunOres.RESOURCE_PREFIX + "Molten"
        + Character.toUpperCase(name.charAt(0)) + name.substring(1));
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
      ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {

          return fluidModelLocation;
        }
      });
      ModelLoader.setCustomStateMapper(block, new StateMapperBase() {

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {

          return fluidModelLocation;
        }
      });
    }
  }
}
