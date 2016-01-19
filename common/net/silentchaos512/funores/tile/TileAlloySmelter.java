package net.silentchaos512.funores.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.silentchaos512.funores.inventory.ContainerAlloySmelter;
import net.silentchaos512.funores.lib.AlloySmelterRecipe;
import net.silentchaos512.funores.lib.EnumMachineState;

public class TileAlloySmelter extends TileEntity implements ITickable, ISidedInventory {

  public static final float BURN_TIME_MULTI = 0.5f;

  // Slot ids
  public static final int[] SLOTS_INPUT = { 0, 1, 2, 3 };
  public static final int SLOT_FUEL = 4;
  public static final int SLOT_OUTPUT = 5;
  public static final int NUMBER_OF_SLOTS = 6;

  // Slot to side mappings
  public static final int[] SLOTS_TOP = SLOTS_INPUT;
  public static final int[] SLOTS_BOTTOM = { SLOT_OUTPUT, SLOT_FUEL };
  public static final int[] SLOTS_SIDES = { SLOT_FUEL };

  // Block's inventory
  private ItemStack[] stacks = new ItemStack[NUMBER_OF_SLOTS];

  private int furnaceBurnTime;
  private int currentItemBurnTime;
  private int cookTime;
  private int totalCookTime;

  public int getCookTime() {

    AlloySmelterRecipe recipe = AlloySmelterRecipe.getMatchingRecipe(this);
    if (recipe != null) {
      return recipe.getCookTime();
    }
    return 0;
  }

  public static int getItemBurnTime(ItemStack stack) {

    return (int) (TileEntityFurnace.getItemBurnTime(stack) * BURN_TIME_MULTI);
  }

  public static boolean isItemFuel(ItemStack stack) {

    return getItemBurnTime(stack) > 0;
  }

  public boolean isBurning() {

    return this.furnaceBurnTime > 0;
  }

  public boolean isBurning(IInventory inventory) {

    return inventory.getField(0) > 0;
  }

  public boolean canSmelt() {

    ItemStack result = getSmeltingResult();
    if (result == null) {
      return false;
    }

    ItemStack outputSlot = stacks[SLOT_OUTPUT];
    if (outputSlot == null) {
      return true; // Output slot free.
    }

    // Output slot not free. Same item?
    if (!result.isItemEqual(outputSlot)) {
      return false;
    }
    // Enough room?
    int newOutputSize = outputSlot.stackSize + result.stackSize;
    if (newOutputSize > getInventoryStackLimit() || newOutputSize > result.getMaxStackSize()) {
      return false;
    }

    return true;
  }

  public void smeltItem() {

    if (canSmelt()) {
      ItemStack output = getSmeltingResult();

      // Set output
      if (stacks[SLOT_OUTPUT] == null) {
        stacks[SLOT_OUTPUT] = output; // No need to copy, since output is a copy, right?
      } else if (stacks[SLOT_OUTPUT].isItemEqual(output)) {
        stacks[SLOT_OUTPUT].stackSize += output.stackSize;
      }

      // Set inputs
      AlloySmelterRecipe recipe = AlloySmelterRecipe.getMatchingRecipe(this);
      Object[] inputList = recipe.getInputs();
      for (int i = 0; i < SLOTS_INPUT.length; ++i) {
        if (stacks[i] != null) {
          for (Object recipeInputObject : inputList) {
            if (recipe.itemStackMatchesForInput(stacks[i], recipeInputObject)) {
              stacks[i].stackSize -= recipe.getRecipeObjectStackSize(recipeInputObject);
              if (stacks[i].stackSize <= 0) {
                // stacks[i] = null;
                stacks[i] = stacks[i].getItem().getContainerItem(stacks[i]);
              }
              break;
            }
          }
        }
      }
    }
  }

  /*
   * Alloy smelting functions.
   */

  public ItemStack getSmeltingResult() {

    AlloySmelterRecipe recipe = AlloySmelterRecipe.getMatchingRecipe(this);
    if (recipe != null) {
      return recipe.getOutput();
    }

    return null;
  }

  @Override
  public int getSizeInventory() {

    return stacks.length;
  }

  @Override
  public ItemStack getStackInSlot(int index) {

    return index >= 0 && index < stacks.length ? stacks[index] : null;
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
  public void setInventorySlotContents(int index, ItemStack stack) {

    boolean flag = stack != null && stack.isItemEqual(this.stacks[index])
        && ItemStack.areItemStacksEqual(stack, this.stacks[index]);
    this.stacks[index] = stack;

    if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
      stack.stackSize = this.getInventoryStackLimit();
    }

    if (index < SLOTS_INPUT.length && !flag) {
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
  public void markDirty() {

    super.markDirty();
  }

  @Override
  public boolean isUseableByPlayer(EntityPlayer player) {

    return this.worldObj.getTileEntity(this.pos) != this ? false
        : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
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

    if (index == SLOT_FUEL) {
      return isItemFuel(stack);
    } else if (index < SLOTS_INPUT.length) {
      // TODO
    }

    return true;
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

    for (int i = 0; i < stacks.length; ++i) {
      stacks[i] = null;
    }
  }

  @Override
  public String getName() {

    return "container.funores.alloysmelter.name";
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

    return side == EnumFacing.DOWN ? SLOTS_BOTTOM
        : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
  }

  @Override
  public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

    return isItemValidForSlot(index, itemStackIn);
  }

  @Override
  public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {

    if (direction == EnumFacing.DOWN && index == SLOT_FUEL) {
      Item item = stack.getItem();

      if (item != Items.water_bucket && item != Items.bucket) {
        return false;
      }
    }

    return true;
  }

  public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {

    return new ContainerAlloySmelter(playerInventory, this);
  }

  @Override
  public void update() {

    boolean flag = isBurning();
    boolean flag1 = false;

    if (isBurning()) {
      --furnaceBurnTime;
    }

    if (!worldObj.isRemote) {
      if (!isBurning() && (stacks[SLOT_FUEL] == null /* || stacks[SLOT_INPUT] == null */)) {
        if (!isBurning() && cookTime > 0) {
          cookTime = MathHelper.clamp_int(cookTime - 2, 0, totalCookTime);
        }
      } else {
        if (!isBurning() && canSmelt()) {
          currentItemBurnTime = furnaceBurnTime = getItemBurnTime(stacks[SLOT_FUEL]);

          if (isBurning()) {
            flag1 = true;

            if (stacks[SLOT_FUEL] != null) {
              --stacks[SLOT_FUEL].stackSize;

              if (stacks[SLOT_FUEL].stackSize == 0) {
                stacks[SLOT_FUEL] = stacks[SLOT_FUEL].getItem().getContainerItem(stacks[SLOT_FUEL]);
              }
            }
          }
        }

        if (isBurning() && canSmelt()) {
          ++cookTime;
          if (cookTime == totalCookTime) {
            cookTime = 0;
            totalCookTime = getCookTime();
            if (totalCookTime > 0) {
              smeltItem();
              flag1 = true;
            }
          }
        } else {
          cookTime = 0;
        }
      }

      if (flag != isBurning()) {
        flag1 = true;
        // Set off/on state.
        IBlockState state = worldObj.getBlockState(pos);
        int meta = state.getBlock().getMetaFromState(state);
        meta = isBurning() ? meta | 8 : meta & 7;
        worldObj.setBlockState(pos, state.getBlock().getStateFromMeta(meta));
      }
    }

    if (flag1) {
      markDirty();
    }
  }

  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState,
      IBlockState newState) {

    return oldState.getBlock() != newState.getBlock();
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
}
