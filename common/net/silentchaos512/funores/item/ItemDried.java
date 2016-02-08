package net.silentchaos512.funores.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.core.registry.IAddRecipe;
import net.silentchaos512.funores.core.registry.IHasVariants;
import net.silentchaos512.funores.core.util.LocalizationHelper;
import net.silentchaos512.funores.lib.EnumDriedItem;
import net.silentchaos512.funores.lib.Names;

public class ItemDried extends ItemFood implements IAddRecipe, IHasVariants {

  public ItemDried() {

    super(2, true);
    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.DRIED_ITEM);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    if (shifted) {
      list.add(EnumChatFormatting.ITALIC
          + LocalizationHelper.getItemDescription(getEnum(stack).name, 0));
    }
  }

  @Override
  public void addRecipes() {

    GameRegistry.addShapedRecipe(new ItemStack(Items.leather), "ff", "ff", 'f',
        getStack(EnumDriedItem.DRIED_FLESH));
  }

  @Override
  public void addOreDict() {

  }

  @Override
  public int getHealAmount(ItemStack stack) {

    EnumDriedItem e = getEnum(stack);
    if (e != null) {
      return e.foodValue;
    }
    return super.getHealAmount(stack);
  }

  @Override
  public float getSaturationModifier(ItemStack stack) {

    EnumDriedItem e = getEnum(stack);
    if (e != null) {
      return e.saturationValue;
    }
    return super.getSaturationModifier(stack);
  }

  @Override
  public void getSubItems(Item item, CreativeTabs tab, List list) {

    for (EnumDriedItem e : EnumDriedItem.values()) {
      list.add(new ItemStack(item, 1, e.meta));
    }
  }

  public ItemStack getStack(EnumDriedItem e) {

    return new ItemStack(this, 1, e.meta);
  }

  public EnumDriedItem getEnum(ItemStack stack) {

    for (EnumDriedItem e : EnumDriedItem.values()) {
      if (e.meta == stack.getItemDamage()) {
        return e;
      }
    }
    return null;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {

    EnumDriedItem e = getEnum(stack);
    return LocalizationHelper.ITEM_PREFIX + (e == null ? Names.DRIED_ITEM : e.name);
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[EnumDriedItem.values().length];
    for (int i = 0; i < result.length; ++i) {
      if (i >= EnumDriedItem.values().length) {
        result[i] = FunOres.MOD_ID + ":JerkyTemp";
      } else {
        result[i] = FunOres.MOD_ID + ":" + EnumDriedItem.values()[i].textureName;
      }
      // result[i] = FunOres.MOD_ID + ":JerkyTemp";
    }
    return result;
  }

  @Override
  public String getName() {

    return Names.DRIED_ITEM;
  }

  @Override
  public String getFullName() {

    return FunOres.MOD_ID + ":" + getName();
  }
}
