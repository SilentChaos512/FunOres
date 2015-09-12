package net.silentchaos512.funores.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.silentchaos512.funores.block.ModBlocks;
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.core.util.LocalizationHelper;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMob;

import org.lwjgl.input.Keyboard;

public class ItemBlockOreDrops extends ItemBlockSG {

  public ItemBlockOreDrops(Block block) {

    super(block);
  }

  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {

    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);

    if (shifted) {
      ConfigOptionOreGenBonus config;

      if (this.block == ModBlocks.meatOre) {
        config = EnumMeat.values()[stack.getItemDamage()].getConfig();
      } else if (this.block == ModBlocks.mobOre) {
        config = EnumMob.values()[stack.getItemDamage()].getConfig();
      } else {
        list.add("Wrong ItemBlock class?");
        return;
      }

      list.add(EnumChatFormatting.DARK_BLUE + LocalizationHelper.getMiscText("PossibleDrops"));

      for (ConfigItemDrop drop : config.drops) {
        String str = drop.stack.getUnlocalizedName() + ".name";
        str = StatCollector.translateToLocal(str);
        EnumChatFormatting format = this.getRarityColor(drop);
        list.add(format + str);
      }
    } else {
      list.add(EnumChatFormatting.ITALIC + LocalizationHelper.getMiscText("PressShift"));
    }
  }

  private EnumChatFormatting getRarityColor(ConfigItemDrop drop) {

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
}
