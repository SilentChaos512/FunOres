package net.silentchaos512.funores.tile;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.silentchaos512.funores.api.recipe.dryingrack.DryingRackRecipe;
import net.silentchaos512.funores.block.machine.BlockMachine;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.EnumMachineState;
import net.silentchaos512.lib.tile.IInventorySL;
import net.silentchaos512.lib.util.StackHelper;

public class TileDryingRack extends TileEntity implements ITickable, IInventorySL {

  public static final int BASE_DRY_SPEED = 1;

  private @Nonnull ItemStack stack = StackHelper.empty();
  private int dryTime = 0;
  private int totalDryTime = 0;
  private float xp = 0;

  public List<String> getDebugLines() {

    String sep = "-----------------------";
    List<String> list = Lists.newArrayList();
    list.add("DEBUG INFO:");
    list.add(sep);
    // variables
    list.add("dryTime = " + dryTime);
    list.add("totalDryTime = " + totalDryTime);
    list.add("drySpeed = " + getDrySpeed());
    list.add("xp = " + xp);
    ItemStack output = getOutput();
    list.add("stack = " + (StackHelper.isEmpty(stack) ? "null" : stack.getDisplayName()));
    list.add("output = " + (StackHelper.isEmpty(output) ? "null" : output.getDisplayName()));
    list.add(sep);
    // constants
    return list;
  }

  @Override
  public void update() {

    DryingRackRecipe recipe = DryingRackRecipe.getMatchingRecipe(stack);
    ItemStack output = recipe == null ? StackHelper.empty() : recipe.getOutput();
    if (recipe != null && output != null) {
      dryTime += getDrySpeed();
      if (dryTime >= totalDryTime) {
        stack = output;
        dryTime = 0;
        xp = recipe.getExperience();
        totalDryTime = getTotalDryTime();
      }
    }
  }

  public boolean interact(EntityPlayer player, EnumHand hand, ItemStack heldItem) {

    if (player.world.isRemote)
      return true;

    boolean markForUpdate = false;

    if (StackHelper.isEmpty(stack) && StackHelper.isValid(heldItem)) {
      // Add to rack.
      stack = StackHelper.safeCopy(heldItem);
      StackHelper.setCount(stack, 1);
      StackHelper.shrink(heldItem, 1);

      dryTime = 0;
      totalDryTime = getTotalDryTime();
      markForUpdate = true;
    } else if (StackHelper.isValid(stack)) {
      // Remove from rack.
      if (!player.world.isRemote) {
        Vec3d v = new Vec3d(player.posX, player.posY + 1.1, player.posZ);
        Vec3d lookVec = player.getLookVec();
        v = v.add(lookVec);
        StackHelper.setCount(stack, 1); // Not sure why this is necessary...
        EntityItem entityItem = new EntityItem(player.world, v.x, v.y, v.z,
            stack);
        // LogHelper.list(entityItem, entityItem.getEntityItem().stackSize);
        player.world.spawnEntity(entityItem);
      }
      givePlayerXp(player);
      stack = StackHelper.empty();
      dryTime = 0;
      totalDryTime = getTotalDryTime();
      markForUpdate = true;
    }

    if (markForUpdate) {
      IBlockState state = world.getBlockState(pos);
      world.notifyBlockUpdate(pos, state, state, 2);
    }

    return true;
  }

  protected void givePlayerXp(EntityPlayer player) {

    if (world.isRemote) {
      xp = 0;
      return;
    }

    int amount = (int) xp;
    xp -= amount;
    if (xp > 0.0f) {
      amount = player.world.rand.nextInt((int) (1.0f / xp)) == 0 ? 1 : 0;
    }

    if (amount > 0) {
      player.addExperience(amount);
      world.playSound(null, player.posX, player.posY, player.posZ,
          SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F,
          0.5F * ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.8F));
    }

    xp = 0;
  }

  protected int getDrySpeed() {

    boolean canSeeSky = world.canBlockSeeSky(getPos());
    boolean daytime = world.isDaytime(); // Always returns true?
    boolean raining = world.isRaining();

    if (canSeeSky) {
      if (daytime && !raining) {
        return 2 * BASE_DRY_SPEED;
      } else if (raining) {
        return (int) (0.5 * BASE_DRY_SPEED);
      }
    }
    return BASE_DRY_SPEED;
  }

  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {

    return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
  }

  @Override
  public NBTTagCompound getUpdateTag() {

    NBTTagCompound tags = super.getUpdateTag();
    tags.setInteger("DryTime", dryTime);
    tags.setInteger("TotalDryTime", totalDryTime);
    tags.setFloat("XP", xp);
    if (StackHelper.isValid(stack))
      tags.setTag("ItemStack", stack.writeToNBT(new NBTTagCompound()));
    return tags;
  }

  @Override
  public void onDataPacket(NetworkManager network, SPacketUpdateTileEntity packet) {

    NBTTagCompound tags = packet.getNbtCompound();
    dryTime = tags.getInteger("DryTime");
    totalDryTime = tags.getInteger("TotalDryTime");
    xp = tags.getFloat("XP");
    if (tags.hasKey("ItemStack")) {
      stack = StackHelper.loadFromNBT(tags.getCompoundTag("ItemStack"));
    } else {
      stack = StackHelper.empty();
    }

    if (getWorld().isRemote) {
      // FIXME
//      getWorld().markBlockForUpdate(getPos());
    }
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {

    super.readFromNBT(compound);
    stack = StackHelper.loadFromNBT(compound.getCompoundTag("ItemStack"));
    dryTime = compound.getShort("DryTime");
    totalDryTime = getTotalDryTime();
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {

    super.writeToNBT(compound);
    compound.setShort("DryTime", (short) dryTime);
    NBTTagCompound tagCompound = new NBTTagCompound();
    if (StackHelper.isValid(stack)) {
      stack.writeToNBT(tagCompound);
    }
    compound.setTag("ItemStack", tagCompound);
    return compound;
  }

  public @Nonnull ItemStack getOutput() {

    DryingRackRecipe recipe = DryingRackRecipe.getMatchingRecipe(stack);
    if (recipe != null) {
      return recipe.getOutput();
    }
    return StackHelper.empty();
  }

  public @Nonnull ItemStack getStack() {

    return stack;
  }

  public void setStack(@Nonnull ItemStack stack) {

    this.stack = stack;
  }

  public EnumMachineState getMachineState() {

    IBlockState state = world.getBlockState(pos);
    if (state != null && state.getBlock() == ModBlocks.dryingRack)
      return (EnumMachineState) world.getBlockState(pos).getValue(BlockMachine.FACING);
    return EnumMachineState.NORTH_OFF;
  }

  public int getTotalDryTime() {

    DryingRackRecipe recipe = DryingRackRecipe.getMatchingRecipe(stack);
    if (recipe != null) {
      return recipe.getDryTime();
    }
    return 0;
  }

  @Override
  public String getName() {

    return "container.funores:dryingrack.name";
  }

  @Override
  public boolean hasCustomName() {

    return false;
  }

  @Override
  public ITextComponent getDisplayName() {

    return null;
  }

  @Override
  public int getSizeInventory() {

    return 1;
  }

  @Override
  public ItemStack getStackInSlot(int index) {

    return stack;
  }

  @Override
  public ItemStack decrStackSize(int index, int count) {

    return removeStackFromSlot(index);
  }

  @Override
  public ItemStack removeStackFromSlot(int index) {

    ItemStack copy = stack;
    stack = StackHelper.empty();
    return copy;
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {

    setStack(stack);
  }

  @Override
  public int getInventoryStackLimit() {

    return 1;
  }

  @Override
  public boolean isUsable(EntityPlayer player) {

    return this.world.getTileEntity(this.pos) != this ? false
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

    return true;
  }

  @Override
  public int getField(int id) {

    switch (id) {
      case 0:
        return dryTime;
      case 1:
        return totalDryTime;
      default:
        return 0;
    }
  }

  @Override
  public void setField(int id, int value) {

    switch (id) {
      case 0:
        dryTime = value;
        break;
      case 1:
        totalDryTime = value;
        break;
    }
  }

  @Override
  public int getFieldCount() {

    return 2;
  }

  @Override
  public void clear() {

    stack = StackHelper.empty();
  }

  @Override
  public boolean isEmpty() {

    return StackHelper.isEmpty(stack);
  }
}
