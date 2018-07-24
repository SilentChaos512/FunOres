package net.silentchaos512.funores.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.silentchaos512.lib.registry.IRegistryObject;

public class ModRecipeHelper {

//@formatter:off
  private static final String SAG_MILL_MSG =
      "<recipeGroup name=\"FunOres\">" + 
        "<recipe name=\"%s\" energyCost=\"%d\">" +
          "<input>" +
            "<itemStack modID=\"FunOres\" itemName=\"%s\" itemMeta=\"%d\" />" +
          "</input>" +
          "<output>" +
            "<itemStack modID=\"FunOres\" itemName=\"%s\" itemMeta=\"%d\" number=\"2\" />" +
            "<itemStack modID=\"FunOres\" itemName=\"%s\" itemMeta=\"%d\" number=\"1\" chance=\"0.1\" />" +    
            "<itemStack modID=\"minecraft\" itemName=\"%s\" chance=\"0.15\"/>" +
          "</output>" +
        "</recipe>" + 
      "</recipeGroup>";
  //@formatter:on

  public static void addSagMillRecipe(String key, ItemStack input, ItemStack outputPrimary,
      ItemStack outputSecondary, String stoneName, int energy) {

    String inputName = input.getItem() instanceof IRegistryObject
        ? ((IRegistryObject) input.getItem()).getName()
        : input.getItem().getUnlocalizedName().replaceFirst("(item\\.FunOres:|tile\\.)", "");
    String outputName = outputPrimary.getItem() instanceof IRegistryObject
        ? ((IRegistryObject) outputPrimary.getItem()).getName()
        : outputPrimary.getItem().getUnlocalizedName().replaceFirst("(item\\.FunOres:|tile\\.)", "");
    String extraName = outputSecondary.getItem() instanceof IRegistryObject
        ? ((IRegistryObject) outputSecondary.getItem()).getName()
        : outputSecondary.getItem().getUnlocalizedName().replaceFirst("(item\\.FunOres:|tile\\.)", "");

    int inputMeta = input.getItemDamage();
    int outputMeta = outputPrimary.getItemDamage();
    int extraMeta = outputSecondary.getItemDamage();

    String str = String.format(SAG_MILL_MSG, key, energy, inputName, inputMeta, outputName,
        outputMeta, extraName, extraMeta, stoneName);
    // FunOres.instance.logHelper.debug(str);
    FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", str);
  }
}
