package net.silentchaos512.funores.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.tile.TileAlloySmelter;

public class ContainerAlloySmelter extends Container {

  private final IInventory tileAlloySmelter;
  private int field_178152_f;
  private int field_178153_g;
  private int field_178154_h;
  private int field_178155_i;

  public ContainerAlloySmelter(InventoryPlayer playerInventory, IInventory smelterInventory) {

    this.tileAlloySmelter = smelterInventory;

    // Input slots
    addSlotToContainer(new Slot(smelterInventory, 0, 43, 26));
    addSlotToContainer(new Slot(smelterInventory, 1, 61, 26));
    addSlotToContainer(new Slot(smelterInventory, 2, 43, 44));
    addSlotToContainer(new Slot(smelterInventory, 3, 61, 44));

    // Fuel slot
    addSlotToContainer(new SlotFurnaceFuel(smelterInventory, TileAlloySmelter.SLOT_FUEL, 19, 44));

    // Output slot
    addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, smelterInventory,
        TileAlloySmelter.SLOT_OUTPUT, 116, 35));

    // Player inventory
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

  @Override
  public void onCraftGuiOpened(ICrafting listener) {

    super.onCraftGuiOpened(listener);
    listener.updateCraftingInventory(this, this.getInventory());
  }

  @Override
  public void detectAndSendChanges() {

    super.detectAndSendChanges();

    for (int i = 0; i < this.crafters.size(); ++i) {
      ICrafting icrafting = (ICrafting) this.crafters.get(i);

      if (this.field_178152_f != this.tileAlloySmelter.getField(2)) {
        icrafting.sendProgressBarUpdate(this, 2, this.tileAlloySmelter.getField(2));
      }

      if (this.field_178154_h != this.tileAlloySmelter.getField(0)) {
        icrafting.sendProgressBarUpdate(this, 0, this.tileAlloySmelter.getField(0));
      }

      if (this.field_178155_i != this.tileAlloySmelter.getField(1)) {
        icrafting.sendProgressBarUpdate(this, 1, this.tileAlloySmelter.getField(1));
      }

      if (this.field_178153_g != this.tileAlloySmelter.getField(3)) {
        icrafting.sendProgressBarUpdate(this, 3, this.tileAlloySmelter.getField(3));
      }
    }

    this.field_178152_f = this.tileAlloySmelter.getField(2);
    this.field_178154_h = this.tileAlloySmelter.getField(0);
    this.field_178155_i = this.tileAlloySmelter.getField(1);
    this.field_178153_g = this.tileAlloySmelter.getField(3);
  }

  @Override
  public void updateProgressBar(int id, int data) {

    this.tileAlloySmelter.setField(id, data);
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {

    return this.tileAlloySmelter.isUseableByPlayer(playerIn);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

    LogHelper.debug("index = " + index);
    ItemStack itemstack = null;
    Slot slot = (Slot) this.inventorySlots.get(index);

    if (slot != null && slot.getHasStack()) {
      final int slotFuel = TileAlloySmelter.SLOT_FUEL;                // 4
      final int inputSlotCount = TileAlloySmelter.SLOTS_INPUT.length; // 4
      final int slotCount = TileAlloySmelter.NUMBER_OF_SLOTS;         // 6

      final boolean test = true;

      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();

      if (index == TileAlloySmelter.SLOT_OUTPUT) {
        if (!this.mergeItemStack(itemstack1, slotCount, slotCount + 36, true)) {
          return null;
        }

        slot.onSlotChange(itemstack1, itemstack);
      } else if (index != slotFuel && index >= inputSlotCount) {
        if (TileEntityFurnace.isItemFuel(itemstack1)) {
          // Insert fuel?
          if (!this.mergeItemStack(itemstack1, slotFuel, slotFuel + 1, false)) {
            return null;
          }
        } else if (AlloySmelterRecipe.isValidIngredient(itemstack1)) {
          // Insert ingredients?
          if (!this.mergeItemStack(itemstack1, 0, inputSlotCount, false)) {
            return null;
          }
        }  else if (index >= 4 && index < 31) {
          if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
            return null;
          }
        } else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
          return null;
        }
      } else if (!this.mergeItemStack(itemstack1, slotCount, slotCount + 36, false)) {
        return null;
      }

      if (itemstack1.stackSize == 0) {
        slot.putStack((ItemStack) null);
      } else {
        slot.onSlotChanged();
      }

      if (itemstack1.stackSize == itemstack.stackSize) {
        return null;
      }

      slot.onPickupFromSlot(playerIn, itemstack1);
    }

    return itemstack;
  }
}
