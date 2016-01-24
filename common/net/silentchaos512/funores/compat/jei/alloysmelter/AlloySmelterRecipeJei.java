package net.silentchaos512.funores.compat.jei.alloysmelter;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;

public class AlloySmelterRecipeJei extends BlankRecipeWrapper {

  @Nonnull
  private final List<AlloySmelterRecipeObject> inputs;

  @Nonnull
  private final ItemStack output;

  public AlloySmelterRecipeJei(@Nonnull List<AlloySmelterRecipeObject> inputs, @Nonnull ItemStack output) {

    this.inputs = inputs;
    this.output = output;
  }

  public List<AlloySmelterRecipeObject> getInputObjects() {

    return inputs;
  }

  @Override
  public List getInputs() {

    // Test
    List<ItemStack> list = Lists.newArrayList();
    for (AlloySmelterRecipeObject recipeObject : inputs) {
      list.addAll(recipeObject.getPossibleItemStacks());
    }
    return list;
  }

  @Override
  public List getOutputs() {

    return Collections.singletonList(output);
  }
}
