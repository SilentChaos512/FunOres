package silent.funores.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import silent.funores.FunOres;
import silent.funores.configuration.ConfigItemDrop;
import silent.funores.configuration.ConfigOptionOreGenBonus;
import silent.funores.item.ModItems;
import silent.funores.lib.EnumMob;
import silent.funores.lib.Names;

public class MobOre extends BlockSG {

  public static final PropertyEnum MOB = PropertyEnum.create("mob", EnumMob.class);

  public MobOre() {

    super(EnumMob.count(), Material.rock);

    setHardness(1.5f);
    setResistance(10.0f);
    setStepSound(Block.soundTypeStone);
    setHarvestLevel("pickaxe", 0);

    setHasSubtypes(true);
    setUnlocalizedName(Names.MOB_ORE);
  }

  @Override
  public void addRecipes() {

    for (EnumMob mob : EnumMob.values()) {
      if (mob == EnumMob.WITCH) {
        addSmelting(mob.getMeta(), Items.glass_bottle);
      } else {
        IBlockState state = this.getDefaultState().withProperty(MOB, mob);
        addSmelting(mob.getMeta(), getItemDropped(state, RANDOM, 0));
      }
    }
  }

  private void addSmelting(int meta, Item result) {

    GameRegistry.addSmelting(new ItemStack(this, 1, meta), new ItemStack(result, 2), 0.2f);
  }

  @Override
  public void addOreDict() {

    // TODO
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[EnumMob.count()];

    for (int i = 0; i < EnumMob.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Ore" + EnumMob.values()[i].getName();
    }

    return result;
  }

  @Override
  public int damageDropped(IBlockState state) {

    return ((EnumMob) state.getValue(MOB)).getMeta();
  }

  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < EnumMob.count(); ++i) {
      list.add(new ItemStack(item, 1, EnumMob.values()[i].getMeta()));
    }
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState().withProperty(MOB, EnumMob.byMetadata(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((EnumMob) state.getValue(MOB)).getMeta();
  }

  @Override
  protected BlockState createBlockState() {

    return new BlockState(this, new IProperty[] { MOB });
  }

  @Override
  public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {

    Item drop = this.getItemDropped(world.getBlockState(pos), RANDOM, fortune);
    if (drop != Item.getItemFromBlock(this)) {
      return 1 + RANDOM.nextInt(3);
    }
    return 0;
  }

  @Override
  public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance,
      int fortune) {

    super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

    // Spawn Endermites?
    if ((EnumMob) state.getValue(MOB) == EnumMob.ENDERMAN && FunOres.instance.random.nextInt(100) < 15) {
      if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
        EntityEndermite entity = new EntityEndermite(world);
        entity.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY(),
            (double) pos.getZ() + 0.5, 0.0f, 0.0f);
        world.spawnEntityInWorld(entity);
        entity.spawnExplosionParticle();
      }
    }
  }

  @Override
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    List<ItemStack> ret = new ArrayList<ItemStack>();

    Random rand = world instanceof World ? ((World) world).rand : RANDOM;

    ConfigOptionOreGenBonus config = ((EnumMob) state.getValue(MOB)).getConfig();

    for (ConfigItemDrop drop : config.drops) {
      // Make sure drop config isn't null.
      if (drop != null) {
        // Should we do the drop?
        if (rand.nextFloat() < drop.getDropChance(fortune)) {
          // How many to drop?
          ItemStack stack = drop.getStack().copy();
          stack.stackSize = drop.getDropCount(fortune, rand);
          // Drop stuff.
          for (int i = 0; i < stack.stackSize; ++i) {
            ret.add(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
          }
        }
      }
    }

    return ret;
  }
}
