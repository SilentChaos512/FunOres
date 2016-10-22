package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.registry.FunOresRegistry;

public class ItemDustMetal extends ItemBaseMetal {

  public ItemDustMetal() {

    super(Names.METAL_DUST, "Dust", "dust");
  }

  @Override
  public List<IMetal> getMetals(Item item) {

    List<IMetal> list = new ArrayList<IMetal>(Arrays.asList(EnumMetal.values()));
    list.addAll(Arrays.asList(EnumVanillaMetal.values()));
    return list;
  }

  @Override
  public void addRecipes() {

    FunOresRegistry reg = FunOres.registry;
    for (IMetal metal : getMetals(this)) {
      ItemStack dust = metal.getDust();
      ItemStack ingot = metal.getIngot();
      if (!reg.isItemDisabled(dust) && !reg.isItemDisabled(ingot))
        GameRegistry.addSmelting(dust, ingot, 0.6f);
    }
  }
}
