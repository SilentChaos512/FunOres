package net.silentchaos512.funores.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.lib.item.ItemBlockSL;

public class ItemBlockOre extends ItemBlockSL {

  public ItemBlockOre(Block block) {

    super(block);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    ConfigOptionOreGen config = getOreConfig(stack);

    if (!isOreEnabled(config)) {
      list.add(FunOres.instance.localizationHelper.getMiscText("Disabled"));
    }
  }

  protected ConfigOptionOreGen getOreConfig(ItemStack stack) {

    if (this.block == ModBlocks.meatOre) {
      return EnumMeat.byMetadata(stack.getItemDamage()).getConfig();
    } else if (this.block == ModBlocks.mobOre) {
      return EnumMob.byMetadata(stack.getItemDamage()).getConfig();
    } else if (block == ModBlocks.metalOre) {
      return EnumMetal.byMetadata(stack.getItemDamage()).getConfig();
    } else {
      return null;
    }
  }

  protected boolean isOreEnabled(ConfigOptionOreGen config) {

    if (block == ModBlocks.metalOre && Config.disableMetalOres) {
      return false;
    }
    if (block == ModBlocks.meatOre && Config.disableMeatOres) {
      return false;
    }
    if (block == ModBlocks.mobOre && Config.disableMobOres) {
      return false;
    }

    return config.enabled;
  }
}
