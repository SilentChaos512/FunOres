package net.silentchaos512.funores.core.util;

import net.silentchaos512.funores.FunOres;

public class LogHelper {

  public static void severe(Object object) {

    FunOres.logger.error(object);
  }

  public static void debug(Object object) {

    FunOres.logger.debug(object);
    System.out.println(object);
  }

  public static void warning(Object object) {

    FunOres.logger.warn(object);
  }

  public static void info(Object object) {

    FunOres.logger.info(object);
  }

  /**
   * Prints a derp message to the console.
   */
  public static void derp() {

    debug("Derp!");
  }

  public static void derp(String message) {

    debug("Derp! " + message);
  }

  public static void derpRand() {

    String s = "";
    for (int i = 0; i < FunOres.instance.random.nextInt(6); ++i) {
      s += " ";
    }
    debug(s + "Derp!");
  }

  public static void yay() {

    debug("Yay!");
  }

  public static void list(Object... objects) {

    String s = "";
    for (int i = 0; i < objects.length; ++i) {
      if (i != 0) {
        s += ", ";
      }
      s += objects[i];
    }
    debug(s);
  }
}
