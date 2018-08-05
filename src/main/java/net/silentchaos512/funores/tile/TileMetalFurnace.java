/*
 * Fun Ores -- TileMetalFurnace
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.inventory.ContainerMetalFurnace;
import net.silentchaos512.lib.tile.TileSidedInventorySL;
import net.silentchaos512.lib.util.StackHelper;

import java.util.List;
import java.util.regex.Pattern;

public class TileMetalFurnace extends TileSidedInventorySL implements ITickable {
    public static final int COOK_TIME_NO_SECONDARY = 133;
    public static final int COOK_TIME_WITH_SECONDARY = 266;
    public static final int BONUS_NUGGETS_MIN = 2;
    public static final int BONUS_NUGGETS_MAX = 6;

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_FUEL = 1;
    public static final int SLOT_OUTPUT_1 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int SIZE_INVENTORY = 4;

    public static final Pattern REGEX_ITEM_MATCH = Pattern.compile("^(ingot|gem)");

    private static final int[] SLOTS_TOP = new int[]{SLOT_INPUT};
    private static final int[] SLOTS_BOTTOM = new int[]{SLOT_FUEL, SLOT_OUTPUT_1, SLOT_OUTPUT_2};
    private static final int[] SLOTS_SIDES = new int[]{SLOT_FUEL};

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
        ItemStack output1 = getPrimaryOutput();
        ItemStack output2 = getSecondaryOutput();
        list.add("output1 = " + (StackHelper.isEmpty(output1) ? "null" : output1.getDisplayName()));
        list.add("output2 = " + (StackHelper.isEmpty(output2) ? "null" : output2.getDisplayName()));
        list.add("canSmelt = " + canSmelt());
        list.add(sep);
        list.add("COOK_TIME_NO_SECONDARY = " + COOK_TIME_NO_SECONDARY);
        list.add("COOK_TIME_WITH_SECONDARY = " + COOK_TIME_WITH_SECONDARY);
        list.add("BONUS_NUGGETS_MIN = " + BONUS_NUGGETS_MIN);
        list.add("BONUS_NUGGETS_MAX = " + BONUS_NUGGETS_MAX);
        return list;
    }

    public int getCookTime() {
        return getSecondaryOutput().isEmpty() ? COOK_TIME_NO_SECONDARY : COOK_TIME_WITH_SECONDARY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack current = getStackInSlot(index);
        boolean flag = !current.isEmpty() && !stack.isEmpty()
                && stack.isItemEqual(current) && ItemStack.areItemStacksEqual(stack, current);

        super.setInventorySlotContents(index, stack);

        if (index == SLOT_INPUT && !flag) {
            this.totalCookTime = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index >= SLOT_OUTPUT_1)
            return false;
        else if (index == SLOT_FUEL)
            return isItemFuel(stack);
        else
            return true;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return furnaceBurnTime;
            case 1:
                return currentItemBurnTime;
            case 2:
                return cookTime;
            case 3:
                return totalCookTime;
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
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();
            return item == Items.WATER_BUCKET || item == Items.BUCKET;
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

        if (isBurning()) {
            --furnaceBurnTime;
        }

        if (!world.isRemote) {
            ItemStack fuel = getStackInSlot(SLOT_FUEL);
            ItemStack input = getStackInSlot(SLOT_INPUT);
            if (!isBurning() && (fuel.isEmpty() || input.isEmpty())) {
                if (!isBurning() && cookTime > 0) {
                    cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
                }
            } else {
                if (!isBurning() && canSmelt()) {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(fuel);

                    if (isBurning()) {
                        flag1 = true;

                        if (!fuel.isEmpty()) {
                            fuel.shrink(1);

                            if (fuel.isEmpty()) {
                                fuel = fuel.getItem().getContainerItem(fuel);
                            }
                        }
                    }
                }

                if (isBurning() && canSmelt()) {
                    ++cookTime;

                    if (cookTime == totalCookTime) {
                        cookTime = 0;
                        totalCookTime = getCookTime();
                        smeltItem();
                        flag1 = true;
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

    public boolean isBurning() {
        return furnaceBurnTime > 0;
    }

    public boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    public ItemStack getPrimaryOutput() {
        ItemStack inputStack = getStackInSlot(SLOT_INPUT);
        if (inputStack.isEmpty())
            return ItemStack.EMPTY;
        return FurnaceRecipes.instance().getSmeltingResult(inputStack);
    }

    public ItemStack getSecondaryOutput() {
        ItemStack inputStack = getStackInSlot(SLOT_INPUT);
        if (inputStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack outputPrimary = FurnaceRecipes.instance().getSmeltingResult(inputStack);
        if (outputPrimary.isEmpty()) {
            return ItemStack.EMPTY;
        }

        String inputName, outputName;
        for (int inputId : OreDictionary.getOreIDs(inputStack)) {
            inputName = OreDictionary.getOreName(inputId);
            for (int outputId : OreDictionary.getOreIDs(outputPrimary)) {
                outputName = OreDictionary.getOreName(outputId);
                // Input must be registered as oreSomething and output as ingotSomething
                if (inputName.startsWith("ore") && REGEX_ITEM_MATCH.matcher(outputName).find()) {
                    inputName = inputName.replaceFirst("ore", "");
                    outputName = outputName.replaceFirst(REGEX_ITEM_MATCH.pattern(), "");
                    if (inputName.equals(outputName)) {
                        // Same ore, can we find a nugget?
                        List<ItemStack> nuggets = StackHelper.getOres("nugget" + inputName);
                        if (!nuggets.isEmpty()) {
                            ItemStack result = nuggets.get(0).copy();
                            result.setCount(BONUS_NUGGETS_MIN + FunOres.random.nextInt(BONUS_NUGGETS_MAX - BONUS_NUGGETS_MIN + 1));
                            return result;
                        } else {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
        }

        return ItemStack.EMPTY;
    }

    private boolean canSmelt() {
        ItemStack inputStack = getStackInSlot(SLOT_INPUT);
        if (inputStack.isEmpty()) {
            return false;
        } else {
            ItemStack outputPrimary = getPrimaryOutput();
            ItemStack outputSecondary = getSecondaryOutput();
            if (!outputSecondary.isEmpty()) {
                outputSecondary.setCount(BONUS_NUGGETS_MAX);
            }
            ItemStack outputSlot1 = getStackInSlot(SLOT_OUTPUT_1);
            ItemStack outputSlot2 = getStackInSlot(SLOT_OUTPUT_2);

            if (outputPrimary.isEmpty()) {
                return false;
            }

            if (outputSlot1.isEmpty() && outputSlot2.isEmpty()) {
                return true;
            }

            boolean output1ClearOrSame = outputSlot1.isEmpty() || outputSlot1.isItemEqual(outputPrimary);
            boolean output2ClearOrSame = outputSecondary.isEmpty() || outputSlot2.isEmpty() || outputSlot2.isItemEqual(outputSecondary);
            if (!output1ClearOrSame || !output2ClearOrSame) {
                return false;
            }

            int newSize1 = outputSlot1.isEmpty() ? 0 : outputSlot1.getCount() + outputPrimary.getCount();
            int maxSize1 = outputPrimary.getMaxStackSize();
            int newSize2 = 0;
            int maxSize2 = outputSecondary.isEmpty() ? 64 : outputSecondary.getMaxStackSize();
            if (!outputSecondary.isEmpty()) {
                newSize2 = (outputSlot2.isEmpty() ? 0 : outputSlot2.getCount()) + outputSecondary.getCount();
            }
            boolean flag1 = newSize1 <= getInventoryStackLimit() && newSize1 <= maxSize1;
            boolean flag2 = newSize2 <= getInventoryStackLimit() && newSize2 <= maxSize2;
            return flag1 && flag2;
        }
    }

    public void smeltItem() {
        if (canSmelt()) {
            ItemStack outputPrimary = getPrimaryOutput();
            ItemStack outputSecondary = getSecondaryOutput();
            ItemStack output1 = getStackInSlot(SLOT_OUTPUT_1);
            ItemStack output2 = getStackInSlot(SLOT_OUTPUT_2);

            if (output1.isEmpty()) {
                output1 = outputPrimary.copy();
            } else if (output1.getItem() == outputPrimary.getItem()) {
                output1.grow(outputPrimary.getCount());
            }
            setInventorySlotContents(SLOT_OUTPUT_1, output1);

            if (!outputSecondary.isEmpty()) {
                if (output2.isEmpty()) {
                    output2 = outputSecondary.copy();
                } else if (output2.getItem() == outputSecondary.getItem()) {
                    output2.grow(outputSecondary.getCount());
                }
                setInventorySlotContents(SLOT_OUTPUT_2, output2);
            }

            ItemStack input = getStackInSlot(SLOT_INPUT);
            ItemStack fuel = getStackInSlot(SLOT_FUEL);
            if (input.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && input.getMetadata() == 1
                    && fuel.getItem() == Items.BUCKET) {
                setInventorySlotContents(SLOT_FUEL, new ItemStack(Items.WATER_BUCKET));
            }

            input.shrink(1);
            setInventorySlotContents(SLOT_INPUT, input);
        }
    }

    public static int getItemBurnTime(ItemStack stack) {
        return TileEntityFurnace.getItemBurnTime(stack);
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    @Override
    public String getName() {
        return "container.funores.metalfurnace.name";
    }

    @Override
    public int getSizeInventory() {
        return SIZE_INVENTORY;
    }
}
