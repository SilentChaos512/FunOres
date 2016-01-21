package net.silentchaos512.funores.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.silentchaos512.funores.FunOres;

public class ModDamageSources {

  public static final String DEATH_PREFIX = "death." + FunOres.RESOURCE_PREFIX;
  public static final String DEATH_HOT_MACHINE = DEATH_PREFIX + "HotMachine";

  public static final DamageSource hotMachine = new DamageSource("HotMachine") {

    @Override
    public IChatComponent getDeathMessage(EntityLivingBase entity) {

      EntityLivingBase entitylivingbase = entity.func_94060_bK();
      String s = DEATH_HOT_MACHINE;
      String s1 = s + ".player";
      return entitylivingbase != null && StatCollector.canTranslate(s1)
          ? new ChatComponentTranslation(s1, entity.getDisplayName(),
              entitylivingbase.getDisplayName())
          : new ChatComponentTranslation(s, entity.getDisplayName());
    }
  };

  public static void init() {

    hotMachine.setDamageBypassesArmor();
  }
}
