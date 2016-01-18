package net.silentchaos512.funores.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.inventory.ContainerMetalFurnace;

public class TileMetalFurnace extends TileEntity implements ITickable, ISidedInventory {

  public static final int COOK_TIME_NO_SECONDARY = 133;
  public static final int COOK_TIME_WITH_SECONDARY = 266;
  public static final int BONUS_NUGGETS_MIN = 2;
  public static final int BONUS_NUGGETS_MAX = 6;

  public static final int SLOT_INPUT = 0;
  public static final int SLOT_FUEL = 1;
  public static final int SLOT_OUTPUT_1 = 2;
  public static final int SLOT_OUTPUT_2 = 3;

  private static final int[] SLOTS_TOP = new int[] { SLOT_INPUT };
  private static final int[] SLOTS_BOTTOM = new int[] { SLOT_FUEL, SLOT_OUTPUT_1, SLOT_OUTPUT_2 };
  private static final int[] SLOTS_SIDES = new int[] { SLOT_FUEL };

  private ItemStack[] stacks = new ItemStack[4];
  private int furnaceBurnTime;
  private int currentItemBurnTime;
  private int cookTime;
  private int totalCookTime;

  public int getCookTime() {

    return getSecondaryOutput() == null ? COOK_TIME_NO_SECONDARY : COOK_TIME_WITH_SECONDARY;
  }

  @Override
  public int getSizeInventory() {

    return stacks.length;
  }

  @Override
  public ItemStack getStackInSlot(int index) {

    return stacks[index];
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {

    if (this.stacks[index] != null) {
      ItemStack stack;

      if (this.stacks[index].stackSize <= count) {
        stack = this.stacks[index];
        this.stacks[index] = null;
        return stack;
      } else {
        stack = this.stacks[index].splitStack(count);

        if (this.stacks[index].stackSize == 0) {
          this.stacks[index] = null;
        }

        return stack;
      }
    } else {
      return null;
    }
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {

    boolean flag = stack != null && stack.isItemEqual(this.stacks[index])
        && ItemStack.areItemStacksEqual(stack, this.stacks[index]);
    this.stacks[index] = stack;

    if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
      stack.stackSize = this.getInventoryStackLimit();
    }

    if (index == 0 && !flag) {
      this.totalCookTime = this.getCookTime();
      this.cookTime = 0;
      this.markDirty();
    }
  }

  @Override
  public int getInventoryStackLimit() {

    return 64;
  }

  @Override
  public boolean isUseableByPlayer(EntityPlayer player) {

    return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq(
        (double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
        (double) this.pos.getZ() + 0.5D) <= 64.0D;
  }

  @Override
  public void openInventory(EntityPlayer player) {

  }

  @Override
  public void closeInventory(EntityPlayer player) {

  }

  @Override
  public boolean isItemValidForSlot(int index, ItemStack stack) {

    if (index >= SLOT_OUTPUT_1) {
      return false;
    } else if (index == SLOT_FUEL) {
      return isItemFuel(stack);
    } else {
      return true;
    }
  }

  @Override
  public int getField(int id) {

    switch (id) {
      case 0:
        return this.furnaceBurnTime;
      case 1:
        return this.currentItemBurnTime;
      case 2:
        return this.cookTime;
      case 3:
        return this.totalCookTime;
      default:
        return 0;
    }
  }

  @Override
  public void setField(int id, int value) {

    switch (id) {
      case 0:
        this.furnaceBurnTime = value;
        break;
      case 1:
        this.currentItemBurnTime = value;
        break;
      case 2:
        this.cookTime = value;
        break;
      case 3:
        this.totalCookTime = value;
    }
  }

  @Override
  public int getFieldCount() {

    return 4;
  }

  @Override
  public void clear() {

    for (int i = 0; i < this.stacks.length; ++i) {
      this.stacks[i] = null;
    }
  }

  @Override
  public boolean hasCustomName() {

    return false;
  }

  @Override
  public IChatComponent getDisplayName() {

    return null;
  }

  @Override
  public int[] getSlotsForFace(EnumFacing side) {

    return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP
        : SLOTS_SIDES);
  }

  @Override
  public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

    return this.isItemValidForSlot(index, itemStackIn);
  }

  @Override
  public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {

    if (direction == EnumFacing.DOWN && index == 1) {
      Item item = stack.getItem();

      if (item != Items.water_bucket && item != Items.bucket) {
        return false;
      }
    }

    return true;
  }

  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {

    return new ContainerMetalFurnace(playerInventory, this);
  }

  @Override
  public void update() {

    boolean flag = this.isBurning();
    boolean flag1 = false;

    if (this.isBurning()) {
      --this.furnaceBurnTime;
    }

    if (!this.worldObj.isRemote) {
      if (!this.isBurning() && (this.stacks[SLOT_FUEL] == null || this.stacks[SLOT_INPUT] == null)) {
        if (!this.isBurning() && this.cookTime > 0) {
          this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
        }
      } else {
        if (!this.isBurning() && this.canSmelt()) {
          this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.stacks[SLOT_FUEL]);

          if (this.isBurning()) {
            flag1 = true;

            if (this.stacks[SLOT_FUEL] != null) {
              --this.stacks[SLOT_FUEL].stackSize;

              if (this.stacks[SLOT_FUEL].stackSize == 0) {
                this.stacks[SLOT_FUEL] = stacks[SLOT_FUEL].getItem().getContainerItem(
                    stacks[SLOT_FUEL]);
              }
            }
          }
        }

        if (this.isBurning() && this.canSmelt()) {
          ++this.cookTime;

          if (this.cookTime == this.totalCookTime) {
            this.cookTime = 0;
            this.totalCookTime = this.getCookTime();
            this.smeltItem();
            flag1 = true;
          }
        } else {
          this.cookTime = 0;
        }
      }

      if (flag != this.isBurning()) {
        flag1 = true;
        // TODO: Fixit!
        // MetalFurnace.setState(this.isBurning(), this.worldObj, this.pos);
      }
    }

    if (flag1) {
      this.markDirty();
    }
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    NBTTagList tagList = compound.getTagList("Items", 10);
    this.stacks = new ItemStack[this.getSizeInventory()];

    for (int i = 0; i < tagList.tagCount(); ++i) {
      NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
      byte b0 = tagCompound.getByte("Slot");

      if (b0 >= 0 && b0 <= this.stacks.length) {
        this.stacks[b0] = ItemStack.loadItemStackFromNBT(tagCompound);
      }
    }

    this.furnaceBurnTime = compound.getShort("BurnTime");
    this.cookTime = compound.getShort("CookTime");
    this.totalCookTime = compound.getShort("CookTimeTotal");
    this.currentItemBurnTime = getItemBurnTime(this.stacks[SLOT_FUEL]);
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);
    compound.setShort("BurnTime", (short) this.furnaceBurnTime);
    compound.setShort("CookTime", (short) this.cookTime);
    compound.setShort("CookTimeTotal", (short) this.totalCookTime);
    NBTTagList tagList = new NBTTagList();

    for (int i = 0; i < this.stacks.length; ++i) {
      if (this.stacks[i] != null) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setByte("Slot", (byte) i);
        this.stacks[i].writeToNBT(tagCompound);
        tagList.appendTag(tagCompound);
      }
    }

    compound.setTag("Items", tagList);
  }

  public boolean isBurning() {

    return this.furnaceBurnTime > 0;
  }

  public boolean isBurning(IInventory inventory) {

    return inventory.getField(0) > 0;
  }

  public ItemStack getSecondaryOutput() {

    ItemStack inputStack = this.stacks[SLOT_INPUT];
    if (inputStack == null) {
      return null;
    }

    ItemStack outputPrimary = FurnaceRecipes.instance().getSmeltingResult(inputStack);
    if (outputPrimary == null) {
      return null;
    }

    String inputName, outputName;
    for (int inputId : OreDictionary.getOreIDs(inputStack)) {
      inputName = OreDictionary.getOreName(inputId);
      for (int outputId : OreDictionary.getOreIDs(outputPrimary)) {
        outputName = OreDictionary.getOreName(outputId);
        // Input must be registered as oreSomething and output as ingotSomething
        if (inputName.startsWith("ore") && outputName.startsWith("ingot")) {
          inputName = inputName.replaceFirst("ore", "");
          outputName = outputName.replaceFirst("ingot", "");
          if (inputName.equals(outputName)) {
            // Same ore, can we find a nugget?
            List<ItemStack> nuggets = OreDictionary.getOres("nugget" + inputName);
            if (!nuggets.isEmpty()) {
              ItemStack result = nuggets.get(0).copy();
              result.stackSize = BONUS_NUGGETS_MIN
                  + FunOres.instance.random.nextInt(BONUS_NUGGETS_MAX - BONUS_NUGGETS_MIN + 1);
              return result;
            } else {
              return null;
            }
          }
        }
      }
    }

    return null;
  }

  private boolean canSmelt() {

    if (this.stacks[SLOT_INPUT] == null) {
      return false;
    } else {
      ItemStack inputStack = this.stacks[SLOT_INPUT];
      ItemStack outputPrimary = FurnaceRecipes.instance().getSmeltingResult(inputStack);
      ItemStack outputSecondary = this.getSecondaryOutput();
      ItemStack outputSlot1 = this.stacks[SLOT_OUTPUT_1];
      ItemStack outputSlot2 = this.stacks[SLOT_OUTPUT_2];

      if (outputPrimary == null) {
        return false;
      }

      if (outputSlot1 == null && outputSlot2 == null) {
        return true;
      }

      boolean output1ClearOrSame = outputSlot1 != null && outputSlot1.isItemEqual(outputPrimary);
      boolean output2ClearOrSame = outputSecondary == null
          || (outputSlot2 != null && outputSlot2.isItemEqual(outputSecondary));
      if (!output1ClearOrSame || !output2ClearOrSame) {
        return false;
      }

      int newSize1 = outputSlot1.stackSize + outputPrimary.stackSize;
      int newSize2 = 0;
      if (outputSecondary != null) {
        newSize2 = outputSlot2.stackSize + outputSecondary.stackSize;
      }
      boolean flag1 = newSize1 <= getInventoryStackLimit()
          && newSize1 <= outputSlot1.getMaxStackSize();
      boolean flag2;
      if (outputSlot2 == null) {
        flag2 = true;
      } else {
        flag2 = newSize2 <= getInventoryStackLimit() && newSize2 <= outputSlot2.getMaxStackSize();
      }
      return flag1 && flag2;
    }
  }

  public void smeltItem() {

    if (this.canSmelt()) {
      ItemStack outputPrimary = FurnaceRecipes.instance()
          .getSmeltingResult(this.stacks[SLOT_INPUT]);
      ItemStack outputSecondary = getSecondaryOutput();

      if (this.stacks[SLOT_OUTPUT_1] == null) {
        this.stacks[SLOT_OUTPUT_1] = outputPrimary.copy();
      } else if (this.stacks[SLOT_OUTPUT_1].getItem() == outputPrimary.getItem()) {
        this.stacks[SLOT_OUTPUT_1].stackSize += outputPrimary.stackSize; // Forge BugFix: Results may have multiple
                                                                         // items
      }

      if (outputSecondary != null) {
        if (this.stacks[SLOT_OUTPUT_2] == null) {
          this.stacks[SLOT_OUTPUT_2] = outputSecondary.copy();
        } else if (this.stacks[SLOT_OUTPUT_2].getItem() == outputSecondary.getItem()) {
          this.stacks[SLOT_OUTPUT_2].stackSize += outputSecondary.stackSize;
        }
      }

      if (this.stacks[SLOT_INPUT].getItem() == Item.getItemFromBlock(Blocks.sponge)
          && this.stacks[SLOT_INPUT].getMetadata() == 1 && this.stacks[SLOT_FUEL] != null
          && this.stacks[SLOT_FUEL].getItem() == Items.bucket) {
        this.stacks[SLOT_FUEL] = new ItemStack(Items.water_bucket);
      }

      --this.stacks[SLOT_INPUT].stackSize;

      if (this.stacks[SLOT_INPUT].stackSize <= 0) {
        this.stacks[SLOT_INPUT] = null;
      }
    }
  }

  public static int getItemBurnTime(ItemStack stack) {

    return TileEntityFurnace.getItemBurnTime(stack);
  }

  public static boolean isItemFuel(ItemStack stack) {

    return getItemBurnTime(stack) > 0;
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {

    if (stacks[index] != null) {
      ItemStack stack = stacks[index];
      stacks[index] = null;
      return stack;
    } else {
      return null;
    }
  }

  @Override
  public String getName() {

    return "container.funores.metalfurnace.name";
  }
}
