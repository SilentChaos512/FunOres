package silent.funores.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import silent.funores.FunOres;
import silent.funores.lib.Names;

public class Shard extends ItemSG {

  public Shard() {

    super(1);
    
    setMaxStackSize(64);
    setHasSubtypes(true);
    setMaxDamage(0);
    setUnlocalizedName(Names.SHARD);
  }

  @Override
  public void addRecipes() {
    
    ItemStack shard = new ItemStack(this, 1, 0);
    ItemStack enderPearl = new ItemStack(Items.ender_pearl);
    GameRegistry.addShapedRecipe(enderPearl, "ss", "ss", 's', shard);
  }
  
  @Override
  public String[] getVariantNames() {
    
    return new String[] { FunOres.MOD_ID + ":EnderShard" };
  }
}
