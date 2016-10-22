package net.silentchaos512.funores.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.Item;
import net.silentchaos512.funores.lib.EnumAlloy;
import net.silentchaos512.funores.lib.IMetal;
import net.silentchaos512.funores.lib.Names;

public class ItemNuggetAlloy extends ItemBaseMetal {

  public ItemNuggetAlloy() {

    super(Names.ALLOY_NUGGET, "Nugget", "nugget");
  }

  @Override
  public List<IMetal> getMetals(Item item) {

    List<IMetal> list = new ArrayList<IMetal>(Arrays.asList(EnumAlloy.values()));
    return list; // Build fails if not assigned to a variable?
  }
}
