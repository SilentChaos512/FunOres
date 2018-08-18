/*
 * Fun Ores -- TileAlloySmelter
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.tile;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipe;
import net.silentchaos512.funores.api.recipe.alloysmelter.AlloySmelterRecipeObject;
import net.silentchaos512.funores.inventory.ContainerAlloySmelter;
import net.silentchaos512.lib.tile.TileSidedInventorySL;

import java.util.List;

public class TileAlloySmelter extends TileSidedInventorySL implements ITickable {
    public static final float BURN_TIME_MULTI = 0.5f;

    // Slot ids
    public static final int[] SLOTS_INPUT = {0, 1, 2, 3};
    public static final int SLOT_FUEL = 4;
    public static final int SLOT_OUTPUT = 5;
    public static final int NUMBER_OF_SLOTS = 6;

    // Slot to side mappings
    public static final int[] SLOTS_TOP = SLOTS_INPUT;
    public static final int[] SLOTS_BOTTOM = {SLOT_OUTPUT, SLOT_FUEL};
    public static final int[] SLOTS_SIDES = {SLOT_FUEL};

    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;

    public List<String> getDebugLines() {
        String sep = "-----------------------";
        List<String> list = Lists.newArrayList();
        list.add("DEBUG INFO:");
        list.add(sep);
        list.add("furnaceBurnTime = " + furnaceBurnTime);
        list.add("currentItemBurnTime = " + currentItemBurnTime);
        list.add("cookTime = " + cookTime);
        list.add("totalCookTime = " + totalCookTime);
        ItemStack result = getSmeltingResult();
        list.add("result = " + (result.isEmpty() ? "null" : result.getDisplayName()));
        list.add("canSmelt = " + canSmelt());
        list.add(sep);
        list.add("BURN_TIME_MULTI = " + BURN_TIME_MULTI);
        return list;
    }

    public int getCookTime() {
        AlloySmelterRecipe recipe = AlloySmelterRecipe.getMatchingRecipe(getInputStacks());
        return recipe == null ? 0 : recipe.getCookTime();
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
        if (result.isEmpty())
            return false;

        ItemStack outputSlot = getStackInSlot(SLOT_OUTPUT);
        if (outputSlot.isEmpty())
            return true; // Output slot free.

        // Output slot not free. Same item?
        if (!result.isItemEqual(outputSlot))
            return false;

        // Enough room?
        int newOutputSize = outputSlot.getCount() + result.getCount();
        return newOutputSize <= getInventoryStackLimit() && newOutputSize <= result.getMaxStackSize();
    }

    public void smeltItem() {
        if (canSmelt()) {
            ItemStack output = getSmeltingResult();

            ItemStack slotOutput = getStackInSlot(SLOT_OUTPUT);

            // Set output
            if (slotOutput.isEmpty())
                setInventorySlotContents(SLOT_OUTPUT, output); // No need to copy, since output is a copy, right?
            else if (slotOutput.isItemEqual(output))
                slotOutput.grow(output.getCount());

            // Consume ingredients
            AlloySmelterRecipe recipe = AlloySmelterRecipe.getMatchingRecipe(getInputStacks());
            if (recipe == null) return;
            AlloySmelterRecipeObject[] inputList = recipe.getInputs();
            for (AlloySmelterRecipeObject recipeObject : inputList) {
                for (int i = 0; i < SLOTS_INPUT.length; ++i) {
                    ItemStack stack = getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        if (recipeObject.matches(stack)) {
                            stack.shrink(recipeObject.getMatchingStack(stack).getCount());
                            if (stack.isEmpty())
                                stack = stack.getItem().getContainerItem(stack);
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
        AlloySmelterRecipe recipe = AlloySmelterRecipe.getMatchingRecipe(getInputStacks());
        return recipe == null ? ItemStack.EMPTY : recipe.getOutput();
    }

    public NonNullList<ItemStack> getInputStacks() {
        NonNullList<ItemStack> result = NonNullList.create();
        for (int i = 0; i < SLOTS_INPUT.length; ++i)
            result.add(getStackInSlot(i));
        return result;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        boolean flag = !stack.isEmpty() && stack.isItemEqual(getStackInSlot(index))
                && ItemStack.areItemStacksEqual(stack, getStackInSlot(index));

        super.setInventorySlotContents(index, stack);

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
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == SLOT_FUEL)
            return isItemFuel(stack);
        else if (index < SLOTS_INPUT.length)
            return AlloySmelterRecipe.isValidIngredient(stack);

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
    public String getName() {
        return "container.funores.alloysmelter.name";
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == SLOT_FUEL) {
            Item item = stack.getItem();

            return item == Items.WATER_BUCKET || item == Items.BUCKET;
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

        if (!world.isRemote) {
            ItemStack slotFuel = getStackInSlot(SLOT_FUEL);

            if (!isBurning() && slotFuel.isEmpty()) {
                if (!isBurning() && cookTime > 0) {
                    cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
                }
            } else {
                if (!isBurning() && canSmelt()) {
                    currentItemBurnTime = furnaceBurnTime = getItemBurnTime(slotFuel);

                    if (isBurning()) {
                        flag1 = true;

                        if (!slotFuel.isEmpty()) {
                            slotFuel.shrink(1);

                            if (slotFuel.isEmpty()) {
                                slotFuel = slotFuel.getItem().getContainerItem(slotFuel);
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
                IBlockState state = world.getBlockState(pos);
                int meta = state.getBlock().getMetaFromState(state);
                meta = isBurning() ? meta | 8 : meta & 7;
                world.setBlockState(pos, state.getBlock().getStateFromMeta(meta));
            }
        }

        if (flag1) {
            markDirty();
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.furnaceBurnTime = compound.getShort("BurnTime");
        this.cookTime = compound.getShort("CookTime");
        this.totalCookTime = compound.getShort("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(getStackInSlot(SLOT_FUEL));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setShort("BurnTime", (short) this.furnaceBurnTime);
        compound.setShort("CookTime", (short) this.cookTime);
        compound.setShort("CookTimeTotal", (short) this.totalCookTime);

        return compound;
    }

    @Override
    public int getSizeInventory() {
        return NUMBER_OF_SLOTS;
    }
}
