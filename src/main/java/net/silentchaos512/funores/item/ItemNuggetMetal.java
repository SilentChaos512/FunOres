package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumVanillaMetal;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;

public class ItemNuggetMetal extends ItemBaseMetal {

  public ItemNuggetMetal() {

    super(Names.METAL_NUGGET, "Nugget", "nugget");
  }
  
  @Override
  public List<IMetal> getMetals(Item item) {

    List<IMetal> list = new ArrayList(Arrays.asList(EnumMetal.values()));
    for (IMetal metal : EnumVanillaMetal.values())
      if (metal != EnumVanillaMetal.GOLD) // Vanilla has gold nuggets already!
        list.add(metal);
    return list;
  }
}
