package net.silentchaos512.funores.item.block;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.lib.item.ItemBlockSL;

public class ItemBlockOreDrops extends ItemBlockSL {

  public ItemBlockOreDrops(Block block) {

    super(block);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    ConfigOptionOreGenBonus config;

    if (this.block == ModBlocks.meatOre) {
      config = EnumMeat.values()[stack.getItemDamage()].getConfig();
    } else if (this.block == ModBlocks.mobOre) {
      config = EnumMob.values()[stack.getItemDamage()].getConfig();
    } else {
      list.add("Wrong ItemBlock class?");
      return;
    }

    if (!isOreEnabled(config)) {
      list.add(
          TextFormatting.DARK_BLUE + FunOres.instance.localizationHelper.getMiscText("Disabled"));
      return;
    }

    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    if (shifted) {
      list.add(TextFormatting.DARK_BLUE
          + FunOres.instance.localizationHelper.getMiscText("PossibleDrops"));

      for (ConfigItemDrop drop : config.drops) {
        String str = drop.stack.getUnlocalizedName() + ".name";
        str = I18n.translateToLocal(str);
        TextFormatting format = this.getRarityColor(drop);
        list.add(format + str);
      }
    } else {
      list.add(
          TextFormatting.ITALIC + FunOres.instance.localizationHelper.getMiscText("PressShift"));
    }
  }

  private TextFormatting getRarityColor(ConfigItemDrop drop) {

    if (drop.baseChance <= 0.01) {
      return EnumRarity.EPIC.rarityColor;
    } else if (drop.baseChance < 0.06) {
      return EnumRarity.RARE.rarityColor;
    } else if (drop.baseChance < 0.33) {
      return EnumRarity.UNCOMMON.rarityColor;
    } else {
      return EnumRarity.COMMON.rarityColor;
    }
  }

  private boolean isOreEnabled(ConfigOptionOreGenBonus config) {

    if (block == ModBlocks.meatOre && Config.disableMeatOres) {
      return false;
    }
    if (block == ModBlocks.mobOre && Config.disableMobOres) {
      return false;
    }

    return config.enabled;
  }
}
