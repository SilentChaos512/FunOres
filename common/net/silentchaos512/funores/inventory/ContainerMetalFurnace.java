package net.silentchaos512.funores.inventory;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.silentchaos512.lib.inventory.ContainerSL;
import net.silentchaos512.lib.util.StackHelper;

public class ContainerMetalFurnace extends ContainerSL {

  private final IInventory tileFurnace;
  private int cookTime;
  private int totalCookTime;
  private int furnaceBurnTime;
  private int currentItemBurnTime;

  public ContainerMetalFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {

    super(playerInventory, furnaceInventory);
    this.tileFurnace = furnaceInventory;
    this.addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
    this.addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 56, 53));
    this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 116,
        17));
    this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 3, 116,
        52));

    int i;
    for (i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }

    for (i = 0; i < 9; ++i) {
      this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
    }
  }

//  @Override
//  public void addListener(IContainerListener listener) {
//
//    super.addListener(listener);
//    listener.updateCraftingInventory(this, this.getInventory());
//  }

  @Override
  public void detectAndSendChanges() {

    super.detectAndSendChanges();

    for (int i = 0; i < this.listeners.size(); ++i) {
      IContainerListener icrafting = (IContainerListener) this.listeners.get(i);

      if (this.cookTime != this.tileFurnace.getField(2)) {
        icrafting.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
      }

      if (this.furnaceBurnTime != this.tileFurnace.getField(0)) {
        icrafting.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
      }

      if (this.currentItemBurnTime != this.tileFurnace.getField(1)) {
        icrafting.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
      }

      if (this.totalCookTime != this.tileFurnace.getField(3)) {
        icrafting.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
      }
    }

    this.cookTime = this.tileFurnace.getField(2);
    this.furnaceBurnTime = this.tileFurnace.getField(0);
    this.currentItemBurnTime = this.tileFurnace.getField(1);
    this.totalCookTime = this.tileFurnace.getField(3);
  }

  @Override
  public void updateProgressBar(int id, int data) {

    this.tileFurnace.setField(id, data);
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {

    return this.tileFurnace.isUsableByPlayer(playerIn);
  }

  @Override
  public @Nonnull ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

    ItemStack itemstack = StackHelper.empty();
    Slot slot = (Slot) this.inventorySlots.get(index);

    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = StackHelper.safeCopy(itemstack1);

      if (index == 2 || index == 3) { // TODO: Does this need to be changed?
        if (!this.mergeItemStack(itemstack1, 4, 40, true)) {
          return StackHelper.empty();
        }

        slot.onSlotChange(itemstack1, itemstack);
      } else if (index != 1 && index != 0) {
        if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null) {
          if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
            return StackHelper.empty();
          }
        } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
          if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
            return StackHelper.empty();
          }
        } else if (index >= 4 && index < 31) {
          if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
            return StackHelper.empty();
          }
        } else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
          return StackHelper.empty();
        }
      } else if (!this.mergeItemStack(itemstack1, 4, 40, false)) {
        return StackHelper.empty();
      }

      if (StackHelper.isEmpty(itemstack1)) {
        slot.putStack(StackHelper.empty());
      } else {
        slot.onSlotChanged();
      }

      if (StackHelper.getCount(itemstack1) == StackHelper.getCount(itemstack)) {
        return StackHelper.empty();
      }

      ContainerSL.onTakeFromSlot(slot, playerIn, itemstack1);
    }

    return itemstack;
  }
}
