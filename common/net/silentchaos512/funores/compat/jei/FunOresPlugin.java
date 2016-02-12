package net.silentchaos512.funores.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeCategory;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeHandler;
import net.silentchaos512.funores.compat.jei.alloysmelter.AlloySmelterRecipeMaker;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeCategory;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeHandler;
import net.silentchaos512.funores.compat.jei.dryingrack.DryingRackRecipeMaker;
import net.silentchaos512.funores.gui.GuiAlloySmelter;
import net.silentchaos512.funores.lib.Names;

@JEIPlugin
public class FunOresPlugin implements IModPlugin {

  public static IJeiHelpers jeiHelper;

  @Override
  public void register(IModRegistry reg) {

    IGuiHelper guiHelper = jeiHelper.getGuiHelper();

    reg.addRecipeCategories(new AlloySmelterRecipeCategory(guiHelper));
    reg.addRecipeCategories(new DryingRackRecipeCategory(guiHelper));

    reg.addRecipeHandlers(new AlloySmelterRecipeHandler());
    reg.addRecipeHandlers(new DryingRackRecipeHandler());

    reg.addRecipes(AlloySmelterRecipeMaker.getRecipes());
    reg.addRecipes(DryingRackRecipeMaker.getRecipes());

    reg.addRecipeClickArea(GuiAlloySmelter.class, 80, 34, 25, 16,
        AlloySmelterRecipeCategory.CATEGORY);

    String descPrefix = "jei.funores.desc.";
    reg.addDescription(new ItemStack(ModBlocks.metalFurnace), descPrefix + Names.METAL_FURNACE);
    reg.addDescription(new ItemStack(ModBlocks.alloySmelter), descPrefix + Names.ALLOY_SMELTER);
    reg.addDescription(new ItemStack(ModBlocks.dryingRack), descPrefix + Names.DRYING_RACK);
  }

  @Override
  public void onJeiHelpersAvailable(IJeiHelpers arg0) {

    jeiHelper = arg0;
  }

  @Override
  public void onItemRegistryAvailable(IItemRegistry arg0) {

  }

  @Override
  public void onRecipeRegistryAvailable(IRecipeRegistry arg0) {

  }
}
