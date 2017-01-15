package net.silentchaos512.funores.configuration;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.lib.IHasOre;

public class ConfigOptionOreGenReplace extends ConfigOptionOreGen {

  public ConfigOptionOreGenReplace(IHasOre ore) {

    super(ore);
  }

  public boolean replaceExisting = false;

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    if (isExample) {
      return loadExample(c);
    }

    super.loadValue(c, category, comment);
    if (enabled) {
      replaceExisting = c.get(category, "ReplaceExisting", replaceExisting).getBoolean();
    }

    return this.validate();
  }
}
