package net.silentchaos512.funores.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.silentchaos512.funores.FunOres;

public class ModDamageSources {

  public static final String DEATH_PREFIX = "death." + FunOres.RESOURCE_PREFIX;
  public static final String DEATH_HOT_MACHINE = DEATH_PREFIX + "HotMachine";

  public static final DamageSource hotMachine = new DamageSource("HotMachine") {

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entity) {

      EntityLivingBase entitylivingbase = entity.getAttackingEntity();
      String s = DEATH_HOT_MACHINE;
      String s1 = s + ".player";
      return entitylivingbase != null && I18n.canTranslate(s1)
          ? new TextComponentTranslation(s1, entity.getDisplayName(),
              entitylivingbase.getDisplayName())
          : new TextComponentTranslation(s, entity.getDisplayName());
    }
  };

  public static void init() {

    hotMachine.setDamageBypassesArmor();
  }
}
