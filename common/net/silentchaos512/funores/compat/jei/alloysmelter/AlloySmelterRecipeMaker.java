package net.silentchaos512.funores.compat.jei.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.lib.AlloySmelterRecipe;
import net.silentchaos512.funores.lib.EnumMetal;

public class AlloySmelterRecipeMaker {

  @Nonnull
  public static List<AlloySmelterRecipeJei> getRecipes() {

    ArrayList<AlloySmelterRecipeJei> recipes = new ArrayList<AlloySmelterRecipeJei>();

    for (AlloySmelterRecipe smelterRecipe : AlloySmelterRecipe.allRecipes) {
      List inputs = Lists.newArrayList();

      for (Object obj : smelterRecipe.getInputs()) {
        inputs.add(obj);
//        ItemStack stack = convertInputToStack(obj);
//        if (stack != null) {
//          inputs.add(stack);
//        }
      }

      ItemStack output = smelterRecipe.getOutput();
      AlloySmelterRecipeJei jeiRecipe = new AlloySmelterRecipeJei(inputs, output);
      recipes.add(jeiRecipe);
    }

    return recipes;
  }

//  private static ItemStack convertInputToStack(Object input) {
//
//    if (input instanceof String) {
//      String str = (String) input;
//      String[] parts = str.split("\\*");
//
//      if (parts.length < 2) {
//        return null;
//      }
//
//      String oreName = parts[0];
//      int stackSize = AlloySmelterRecipe.getRecipeObjectStackSize(input);
//
//      ItemStack result = OreDictionary.getOres(oreName).get(0).copy(); // FIXME?
//      result.stackSize = stackSize;
//      LogHelper.list(result, result.getItem(), result.getItemDamage(), result.stackSize);
//      return result;
//    } else if (input instanceof ItemStack) {
//      return (ItemStack) input;
//    }
//    return null;
//  }
}
