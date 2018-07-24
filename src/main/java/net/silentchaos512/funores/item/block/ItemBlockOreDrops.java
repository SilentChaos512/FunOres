package net.silentchaos512.funores.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockOreDrops extends ItemBlockOre {

  public ItemBlockOreDrops(Block block) {

    super(block);
  }

//  @Override
//  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
//
//    super.addInformation(stack, player, list, advanced);
//
//    ConfigOptionOreGen config1 = getOreConfig(stack);
//    if (config1 == null || !(config1 instanceof ConfigOptionOreGenBonus)) {
//      return;
//    }
//    ConfigOptionOreGenBonus config = (ConfigOptionOreGenBonus) config1;
//
//    boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
//        || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
//    if (shifted) {
//      list.add(FunOres.instance.localizationHelper.getMiscText("PossibleDrops"));
//
//      for (ConfigItemDrop drop : config.bonusDrops) {
//        String str = drop.stack.getUnlocalizedName() + ".name";
//        str = I18n.translateToLocal(str);
//        TextFormatting format = this.getRarityColor(drop);
//        list.add(format + str);
//      }
//    } else {
//      String str = FunOres.instance.localizationHelper.getMiscText("PressShift");
//      list.add(TextFormatting.ITALIC + str);
//    }
//  }

//  private TextFormatting getRarityColor(ConfigItemDrop drop) {
//
//    if (drop.baseChance <= 0.01) {
//      return EnumRarity.EPIC.rarityColor;
//    } else if (drop.baseChance < 0.06) {
//      return EnumRarity.RARE.rarityColor;
//    } else if (drop.baseChance < 0.33) {
//      return EnumRarity.UNCOMMON.rarityColor;
//    } else {
//      return EnumRarity.COMMON.rarityColor;
//    }
//  }
}
