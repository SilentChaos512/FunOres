package net.silentchaos512.funores.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.EnumDriedItem;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.IRegistryObject;

public class ItemDried extends ItemFood implements IRegistryObject, IDisableable {

  public ItemDried() {

    super(EnumDriedItem.values().length, true);
    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.DRIED_ITEM);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);

    // Display flavor text when shift is held.
    if (shifted)
      for (String line : FunOres.localizationHelper.getItemDescriptionLines(getEnum(stack).name))
        list.add(TextFormatting.ITALIC + line);
  }

  @Override
  public void addRecipes() {

    if (!FunOres.registry.isItemDisabled(getStack(EnumDriedItem.DRIED_FLESH)))
      GameRegistry.addShapedRecipe(new ItemStack(Items.LEATHER), "ff", "ff", 'f',
          getStack(EnumDriedItem.DRIED_FLESH));
  }

  @Override
  public void addOreDict() {

  }

  @Override
  public int getHealAmount(ItemStack stack) {

    EnumDriedItem e = getEnum(stack);
    if (e != null)
      return e.foodValue;
    return super.getHealAmount(stack);
  }

  @Override
  public float getSaturationModifier(ItemStack stack) {

    EnumDriedItem e = getEnum(stack);
    if (e != null)
      return e.saturationValue;
    return super.getSaturationModifier(stack);
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {

    // Add only non-disabled items for display!
    for (ItemStack stack : getSubItems(item))
      if (!FunOres.registry.isItemDisabled(stack))
        list.add(stack);
  }

  @Override
  public List<ItemStack> getSubItems(Item item) {

    // Make a list of all possible items, including disabled ones.
    List<ItemStack> ret = Lists.newArrayList();
    for (EnumDriedItem e : EnumDriedItem.values())
      ret.add(new ItemStack(item, 1, e.meta));
    return ret;
  }

  public ItemStack getStack(EnumDriedItem e) {

    return new ItemStack(this, 1, e.meta);
  }

  public EnumDriedItem getEnum(ItemStack stack) {

    for (EnumDriedItem e : EnumDriedItem.values())
      if (e.meta == stack.getItemDamage())
        return e;

    return null;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    EnumDriedItem e = getEnum(stack);
    return "item.funores:" + (e == null ? Names.DRIED_ITEM : e.name);
  }

  @Override
  public String getName() {

    return Names.DRIED_ITEM;
  }

  @Override
  public String getFullName() {

    return FunOres.MOD_ID + ":" + getName();
  }

  @Override
  public String getModId() {

    return FunOres.MOD_ID;
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumDriedItem item : EnumDriedItem.values()) {
      if (!FunOres.registry.isItemDisabled(item.getItem())) // Don't load disabled item models.
        models.add(new ModelResourceLocation(FunOres.MOD_ID + ":" + item.textureName, "inventory"));
      else
        models.add(null);
    }
    return models;
  }

  @Override
  public boolean registerModels() {

    return false; // Use standard model registration.
  }
}
