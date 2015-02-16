package silent.funores.configuration;

import net.minecraftforge.common.config.Configuration;

public class ConfigOptionDouble extends ConfigOption {

  public double value;
  public final double defaultValue;

  public ConfigOptionDouble(String name, double defaultValue) {

    this.name = name;
    value = 0.0;
    this.defaultValue = defaultValue;
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category) {

    value = c.get(category, name, defaultValue).getDouble(defaultValue);
    return this;
  }

  @Override
  public ConfigOption loadValue(Configuration c, String category, String comment) {

    value = c.get(category, name, defaultValue, comment).getDouble(defaultValue);
    return this;
  }

  @Override
  public ConfigOption validate() {

    return this;
  }

}
