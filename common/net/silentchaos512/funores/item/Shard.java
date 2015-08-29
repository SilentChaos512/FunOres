package net.silentchaos512.funores.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.Names;

public class Shard extends ItemSG {
  
  public static final String[] NAMES = { "Ender", "Blaze" };

  public Shard() {

    super(NAMES.length);
    
    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.SHARD);
  }

  @Override
  public void addRecipes() {
    
    ItemStack enderShard = new ItemStack(this, 1, 0);
    ItemStack enderPearl = new ItemStack(Items.ender_pearl);
    GameRegistry.addShapedRecipe(enderPearl, "ss", "ss", 's', enderShard);
    
    ItemStack blazeShard = new ItemStack(this, 1, 1);
    ItemStack blazeRod = new ItemStack(Items.blaze_rod);
    GameRegistry.addShapedRecipe(blazeRod, "ss", "ss", 's', blazeShard);
  }
  
  @Override
  public String[] getVariantNames() {
    
    String[] result = new String[NAMES.length];
    
    for (int i = 0; i < NAMES.length; ++i) {
      result[i] = FunOres.MOD_ID + ":" + NAMES[i] + "Shard";
    }
    
    return result;
  }
}
