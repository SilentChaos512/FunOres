package net.silentchaos512.funores.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.silentchaos512.funores.FunOres;


public class BlockMoltenFluid extends BlockFluidClassic {

  public BlockMoltenFluid(Fluid fluid) {

    super(fluid, Material.LAVA);
  }

  @Override
  public String getUnlocalizedName() {

    Fluid fluid = FluidRegistry.getFluid(fluidName);
    return fluid != null ? fluid.getUnlocalizedName() : super.getUnlocalizedName();
  }
}
