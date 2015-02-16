package silent.funores.configuration;

import net.minecraftforge.common.config.Configuration;

public class ConfigOptionString extends ConfigOption {

  public String value;
  public final String defaultValue;

  public ConfigOptionString(String name, String defaultValue) {

    this.name = name;
    value = "";
    this.defaultValue = defaultValue;
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category) {

    value = c.get(category, name, defaultValue).getString();
    return this;
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    value = c.get(category, name, defaultValue, comment).getString();
    return this;
  }

  @Override
  public ConfigOption validate() {

    return this;
  }

}
