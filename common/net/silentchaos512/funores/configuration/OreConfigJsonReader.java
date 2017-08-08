package net.silentchaos512.funores.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.lib.util.LogHelper;
import net.silentchaos512.lib.util.StateUtil;

public class OreConfigJsonReader {

  public static Map<String, ConfigOreGenJson> oregens = new HashMap<>();

  static LogHelper log;

  public static void readOreEntries() {

    log = FunOres.logHelper;

    File directory = new File("config/funores");
    JsonParser parser = new JsonParser();

    if (!directory.exists()) {
      directory.mkdirs();
      return;
    }

    if (!directory.isDirectory()) {
      return;
    }

    File[] files = directory.listFiles();

    if (files == null) {
      return;
    }

    Arrays.stream(files).filter(file -> file.getName().endsWith(".json")).forEach(file -> {
      log.info("Reading " + file.getName());
      readFile(file);
    });
  }

  public static void readFile(File file) {

    JsonParser parser = new JsonParser();
    try {
      JsonElement elements = parser.parse(new FileReader(file));
      JsonObject jsonObjects = elements.getAsJsonObject();

      for (Entry<String, JsonElement> entry : jsonObjects.entrySet()) {
        tryParseOreConfig(entry.getKey(), entry.getValue());
      }

    } catch (JsonIOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonSyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void tryParseOreConfig(String oreName, JsonElement element) {

    if (element.isJsonObject()) {
      ConfigOreGenJson config = new ConfigOreGenJson(oreName);
      Block block = null;
      String stateString = null;

      for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
        try {
          String key = entry.getKey();
          JsonElement value = entry.getValue();
          switch (key.toLowerCase()) {
            case "block":
              String itemName = value.getAsString();
              if (!itemName.matches("[^:]+:[^:]+")) {
                String original = itemName;
                itemName = "minecraft:" + itemName;
                log.info(String.format("Assuming block \"%s\" is \"%s\"", original, itemName));
              }
              Item item = Item.getByNameOrId(itemName);
              block = Block.getBlockFromItem(item);
              break;
            case "state":
              stateString = value.getAsString();
              break;
            case "vein_count":
              config.veinCount = value.getAsFloat();
              break;
            case "vein_size":
              config.veinSize = value.getAsInt();
              break;
            case "vein_type":
              // TODO
              break;
            case "height_min":
              config.minY = MathHelper.clamp(value.getAsInt(), 0, 255);
              break;
            case "height_max":
              config.maxY = MathHelper.clamp(value.getAsInt(), 0, 255);
              break;
            case "height_favor":
              // TODO
              break;
            case "predicate":
              // TODO
              break;
            case "biomes":
              if (value.isJsonArray()) {
                value.getAsJsonArray().forEach(elem -> {
                  if (elem.isJsonObject()) {
                    JsonObject obj = elem.getAsJsonObject();
                    // FIXME
                  }
                });
              }
              break;
            default:
              log.warning("Unknown JSON element in ore config: " + key);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      if (block != null) {
        IBlockState state = block.getDefaultState();

        if (stateString != null) {
          state = StateUtil.deserializeState(block, stateString);
          if (state == null) {
            throw new RuntimeException("Invalid state " + stateString + " for block " + block.getRegistryName());
          }
        }

        config.block = state;

        oregens.put(oreName, config);
      } else {
        log.warning("Ore config \"" + oreName + "\": no block specified!");
      }
    }
  }
}
