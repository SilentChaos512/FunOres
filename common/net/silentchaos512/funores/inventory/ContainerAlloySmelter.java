package net.silentchaos512.funores.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.inventory.slot.SlotAlloySmelterOutput;
import net.silentchaos512.funores.tile.TileAlloySmelter;
import net.silentchaos512.lib.util.StackHelper;

public class ContainerAlloySmelter extends Container {

  private final IInventory tileAlloySmelter;
  private int cookTime;
  private int totalCookTime;
  private int furnaceBurnTime;
  private int currentItemBurnTime;

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
    addSlotToContainer(new SlotAlloySmelterOutput(playerInventory.player, smelterInventory,
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

      if (this.cookTime != this.tileAlloySmelter.getField(2)) {
        icrafting.sendProgressBarUpdate(this, 2, this.tileAlloySmelter.getField(2));
      }

      if (this.furnaceBurnTime != this.tileAlloySmelter.getField(0)) {
        icrafting.sendProgressBarUpdate(this, 0, this.tileAlloySmelter.getField(0));
      }

      if (this.currentItemBurnTime != this.tileAlloySmelter.getField(1)) {
        icrafting.sendProgressBarUpdate(this, 1, this.tileAlloySmelter.getField(1));
      }

      if (this.totalCookTime != this.tileAlloySmelter.getField(3)) {
        icrafting.sendProgressBarUpdate(this, 3, this.tileAlloySmelter.getField(3));
      }
    }

    this.cookTime = this.tileAlloySmelter.getField(2);
    this.furnaceBurnTime = this.tileAlloySmelter.getField(0);
    this.currentItemBurnTime = this.tileAlloySmelter.getField(1);
    this.totalCookTime = this.tileAlloySmelter.getField(3);
  }

//  @Override
  public void updateProgressBar(int id, int data) {

    this.tileAlloySmelter.setField(id, data);
  }

  @Override
  public boolean canInteractWith(EntityPlayer playerIn) {

    return this.tileAlloySmelter.isUsableByPlayer(playerIn);
  }

  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

    //FunOres.instance.logHelper.debug("index = " + index);
    ItemStack itemstack = StackHelper.empty();
    Slot slot = (Slot) this.inventorySlots.get(index);

    if (slot != null && slot.getHasStack()) {
      final int slotFuel = TileAlloySmelter.SLOT_FUEL;                // 4
      final int inputSlotCount = TileAlloySmelter.SLOTS_INPUT.length; // 4
      final int slotCount = TileAlloySmelter.NUMBER_OF_SLOTS;         // 6

      final boolean test = true;

      ItemStack itemstack1 = slot.getStack();
      itemstack = StackHelper.safeCopy(itemstack1);

      if (index == TileAlloySmelter.SLOT_OUTPUT) {
        if (!this.mergeItemStack(itemstack1, slotCount, slotCount + 36, true)) {
          return StackHelper.empty();
        }

        slot.onSlotChange(itemstack1, itemstack);
      } else if (index != slotFuel && index >= inputSlotCount) {
        if (TileEntityFurnace.isItemFuel(itemstack1)) {
          // Insert fuel?
          if (!this.mergeItemStack(itemstack1, slotFuel, slotFuel + 1, false)) {
            return StackHelper.empty();
          }
        } else if (AlloySmelterRecipe.isValidIngredient(itemstack1)) {
          // Insert ingredients?
          if (!this.mergeItemStack(itemstack1, 0, inputSlotCount, false)) {
            return StackHelper.empty();
          }
        } else if (index >= 4 && index < 31) {
          if (!this.mergeItemStack(itemstack1, 31, 40, false)) {
            return StackHelper.empty();
          }
        } else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
          return StackHelper.empty();
        }
      } else if (!this.mergeItemStack(itemstack1, slotCount, slotCount + 36, false)) {
        return StackHelper.empty();
      }

      if (StackHelper.isValid(itemstack1)) {
        slot.putStack(StackHelper.empty());
      } else {
        slot.onSlotChanged();
      }

      if (StackHelper.getCount(itemstack1) == StackHelper.getCount(itemstack)) {
        return StackHelper.empty();
      }

      slot.onTake(playerIn, itemstack1);
    }

    return itemstack;
  }
}
