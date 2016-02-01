package net.silentchaos512.funores.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.wit.api.IWitHudInfo;

public class MeatOre extends BlockSG implements IWitHudInfo {

  public static final PropertyEnum MEAT = PropertyEnum.create("meat", EnumMeat.class);

  public MeatOre() {

    super(EnumMeat.count(), Material.rock);

    setHardness(1.5f);
    setResistance(10.0f);
    setStepSound(Block.soundTypeStone);
    setHarvestLevel("pickaxe", 0);

    setHasSubtypes(true);
    setUnlocalizedName(Names.MEAT_ORE);
  }

  @Override
  public void addOreDict() {

    for (EnumMeat meat : EnumMeat.values()) {
      OreDictionary.registerOre("ore" + meat.getName(), new ItemStack(this, 1, meat.getMeta()));
    }
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!player.isSneaking()) {
      return null;
    }

    EnumMeat meat = EnumMeat.byMetadata(state.getBlock().getMetaFromState(state));
    ConfigOptionOreGen config = meat.getConfig();
    return ModBlocks.getWitInfoForOre(config, state, pos, player);
  }

  @Override
  public String[] getVariantNames() {

    String[] result = new String[EnumMeat.count()];

    for (int i = 0; i < EnumMeat.count(); ++i) {
      result[i] = FunOres.MOD_ID + ":Ore" + EnumMeat.values()[i].getName();
    }

    return result;
  }

  @Override
  public int damageDropped(IBlockState state) {

    return ((EnumMeat) state.getValue(MEAT)).getMeta();
  }

  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    for (int i = 0; i < EnumMeat.count(); ++i) {
      list.add(new ItemStack(item, 1, EnumMeat.values()[i].getMeta()));
    }
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {

    return this.getDefaultState().withProperty(MEAT, EnumMeat.byMetadata(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {

    return ((EnumMeat) state.getValue(MEAT)).getMeta();
  }

  @Override
  protected BlockState createBlockState() {

    return new BlockState(this, new IProperty[] { MEAT });
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
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    List<ItemStack> ret = new ArrayList<ItemStack>();

    Random rand = world instanceof World ? ((World) world).rand : RANDOM;

    ConfigOptionOreGenBonus config = ((EnumMeat) state.getValue(MEAT)).getConfig();

    ConfigItemDrop[] dropsToTry;
    // Pick a certain number from the list, or try them all?
    if (config.pick != 0) {
      dropsToTry = new ConfigItemDrop[config.pick];
      for (int i = 0; i < config.pick; ++i) {
        dropsToTry[i] = config.drops.get(rand.nextInt(config.drops.size()));
      }
    } else {
      dropsToTry = config.drops.toArray(new ConfigItemDrop[] {});
    }

    for (ConfigItemDrop drop : dropsToTry) {
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

  private ItemStack getBonusItemDropped(IBlockState state, Random rand, int fortune) {

    int r;
    switch ((EnumMeat) state.getValue(MEAT)) {
      case PIG:
        if (rand.nextInt(100) < 2 + 1 * fortune) {
          return new ItemStack(Items.saddle);
        } else {
          return null;
        }
      case FISH:
        r = rand.nextInt(100);
        int meta = 0;
        if (r < 50) {
          meta = 1;
        } else if (r < 65) {
          meta = 2;
        } else if (r < 75) {
          meta = 3;
        }
        if (meta > 0) {
          return new ItemStack(Items.fish, 1 + rand.nextInt(fortune + 1), meta);
        } else {
          return null;
        }
      case COW:
        if (rand.nextInt(100) < 50 + 7 * fortune) {
          return new ItemStack(Items.leather, 1 + rand.nextInt(fortune + 1));
        } else {
          return null;
        }
      case CHICKEN:
        if (rand.nextInt(100) < 15) {
          return new ItemStack(Items.egg, 2 + rand.nextInt(fortune + 1));
        } else if (rand.nextInt(100) < 50 + 8 * fortune) {
          return new ItemStack(Items.feather, 2 + rand.nextInt(fortune + 1));
        } else {
          return null;
        }
      case RABBIT:
        r = rand.nextInt(100);
        if (r < 60 + 5 * fortune) {
          return new ItemStack(Items.rabbit_hide, 1 + rand.nextInt(fortune + 1));
        } else if (r < 65 + 5 * fortune) {
          return new ItemStack(Items.rabbit_foot, 1 + rand.nextInt(fortune / 2 + 1));
        } else {
          return null;
        }
      case SHEEP:
        if (rand.nextInt(100) < 45 + 6 * fortune) {
          return new ItemStack(Blocks.wool, 1 + rand.nextInt(fortune + 1));
        } else {
          return null;
        }
      default:
        return null;
    }
  }

  // @Override
  // public Item getItemDropped(IBlockState state, Random rand, int fortune) {
  //
  // switch ((EnumMeat) state.getValue(MEAT)) {
  // case PIG:
  // return Items.porkchop;
  // case FISH:
  // return Items.fish;
  // case COW:
  // return Items.beef;
  // case CHICKEN:
  // return Items.chicken;
  // case RABBIT:
  // return Items.rabbit;
  // case SHEEP:
  // return Items.mutton;
  // default:
  // return Items.rotten_flesh;
  // }
  // }

  // @Override
  // public int quantityDropped(Random random) {
  //
  // return 1;
  // }

  @Override
  public int quantityDroppedWithBonus(int fortune, Random random) {

    if (fortune > 0) {
      int j = random.nextInt(fortune + 2) - 1;

      if (j < 0) {
        j = 0;
      }

      return quantityDropped(random) * (j + 1);
    } else {
      return quantityDropped(random);
    }
  }
}
