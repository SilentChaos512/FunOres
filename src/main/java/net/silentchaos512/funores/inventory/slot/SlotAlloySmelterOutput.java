package net.silentchaos512.funores.inventory.slot;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.lib.util.StackHelper;

public class SlotAlloySmelterOutput extends SlotFurnaceOutput {

  private EntityPlayer thePlayer;
  private int removeCount;

  public SlotAlloySmelterOutput(EntityPlayer player, IInventory inventoryIn, int slotIndex,
      int xPosition, int yPosition) {

    super(player, inventoryIn, slotIndex, xPosition, yPosition);
    this.thePlayer = player;
  }

  public ItemStack decrStackSize(int amount) {

    if (this.getHasStack()) {
      this.removeCount += Math.min(amount, StackHelper.getCount(getStack()));
    }

    return super.decrStackSize(amount);
  }

  public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {

    this.onCrafting(stack);
    super.onTake(playerIn, stack);
  }

  /**
   * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
   * internal count then calls onCrafting(item).
   */
  protected void onCrafting(ItemStack stack, int amount) {

    this.removeCount += amount;
    this.onCrafting(stack);
  }

  @Override
  protected void onCrafting(ItemStack stack) {

    stack.onCrafting(this.thePlayer.world, this.thePlayer, this.removeCount);

    if (!this.thePlayer.world.isRemote) {
      int i = this.removeCount;

      AlloySmelterRecipe recipe = AlloySmelterRecipe.getRecipeByOutput(stack);
      float f = recipe == null ? 0 : recipe.getExperience();

      if (f == 0.0F) {
        i = 0;
      } else if (f < 1.0F) {
        int j = MathHelper.floor((float) i * f);

        if (j < MathHelper.ceil((float) i * f)
            && Math.random() < (double) ((float) i * f - (float) j)) {
          ++j;
        }

        i = j;
      }

      while (i > 0) {
        int k = EntityXPOrb.getXPSplit(i);
        i -= k;
        this.thePlayer.world.spawnEntity(new EntityXPOrb(this.thePlayer.world,
            this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, k));
      }
    }

    this.removeCount = 0;
  }
}
