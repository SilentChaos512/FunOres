package net.silentchaos512.funores.api.recipe.dryingrack;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.silentchaos512.lib.util.StackHelper;

import java.util.List;

public class DryingRackRecipeObject {
    private List<ItemStack> stacks = Lists.newArrayList();

    public DryingRackRecipeObject(String oreName) {
        stacks.addAll(StackHelper.getOres(oreName));
    }

    public DryingRackRecipeObject(ItemStack stack) {
        stacks.add(stack);
    }

    public boolean matches(ItemStack inputStack) {
        if (inputStack.isEmpty()) return false;

        for (ItemStack stack : stacks)
            if (stack.isItemEqual(inputStack))
                return true;
        return false;
    }

    public List<ItemStack> getStacks() {
        return stacks;
    }
}
