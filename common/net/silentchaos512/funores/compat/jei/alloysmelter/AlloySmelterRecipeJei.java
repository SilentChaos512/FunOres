package net.silentchaos512.funores.compat.jei.alloysmelter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class AlloySmelterRecipeJei extends BlankRecipeWrapper {

  @Nonnull
  private final List inputs;

  @Nonnull
  private final ItemStack output;

  public AlloySmelterRecipeJei(@Nonnull List inputs, @Nonnull ItemStack output) {

    this.inputs = inputs;
    this.output = output;
  }

  @Override
  public List getInputs() {

    return inputs;
  }

  @Override
  public List getOutputs() {

    return Collections.singletonList(output);
  }
}
