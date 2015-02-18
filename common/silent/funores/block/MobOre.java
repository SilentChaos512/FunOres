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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import silent.funores.FunOres;
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
    
    GameRegistry.addSmelting(new ItemStack(this, 1, meta), new ItemStack(result, 3), 0.2f);
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
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {

    List<ItemStack> ret = new ArrayList<ItemStack>();

    Random rand = world instanceof World ? ((World) world).rand : RANDOM;

    Item mainDrop = getItemDropped(state, rand, fortune);
    ItemStack bonusDrop = getBonusItemDropped(state, rand, fortune);
    
    int count = quantityDropped(state, fortune, rand);
    for (int i = 0; i < count; ++i) {
      if (mainDrop != null) {
        ret.add(new ItemStack(mainDrop, 1, 0));
      }
    }
    if (bonusDrop != null) {
      for (int i = 0; i < bonusDrop.stackSize; ++i) {
        ret.add(new ItemStack(bonusDrop.getItem(), 1, bonusDrop.getItemDamage()));
      }
    }

    return ret;
  }

  private ItemStack getBonusItemDropped(IBlockState state, Random rand, int fortune) {

    int r;
    switch ((EnumMob) state.getValue(MOB)) {
      case ZOMBIE:
        // Zombie rares
        if (doRareDrop(rand, fortune)) {
          r = rand.nextInt(3);
          switch (r) {
            case 0: return new ItemStack(Items.iron_ingot);
            case 1: return new ItemStack(Items.carrot);
            case 2: return new ItemStack(Items.potato);
          }
        }
        // Zombie head
        if (doHeadDrop(rand, fortune)) {
          return new ItemStack(Blocks.skull, 1, 2);
        }
        return null;
      case SKELETON:
        // Skeleton head
        if (doHeadDrop(rand, fortune)) {
          return new ItemStack(Blocks.skull, 1, 0);
        }
        // Skeleton common
        if (rand.nextInt(100) < 75) {
          return new ItemStack(Items.arrow, 1 + rand.nextInt(fortune + 1));
        }
        return null;
      case CREEPER:
        // Creeper head
        if (doHeadDrop(rand, fortune)) {
          return new ItemStack(Blocks.skull, 1, 4);
        }
        return null;
      case SPIDER:
        // Spider uncommon
        if (rand.nextInt(100) < 50) {
          return new ItemStack(Items.spider_eye, 1 + rand.nextInt(fortune + 1));
        }
        return null;
      case ENDERMAN:
        // Ender shards (common)
        if (rand.nextInt(100) < 35) {
          return new ItemStack(ModItems.shard, 1 + rand.nextInt(fortune + 1));
        }
      case SLIME:
        // Slime common
        return new ItemStack(getItemDropped(state, rand, fortune));
      case WITCH:
        // Witch common
        return new ItemStack(getWitchDrop(rand, fortune));
      case PIGMAN:
        // Pigman rare
        if (doRareDrop(rand, fortune)) {
          return new ItemStack(Items.gold_ingot);
        }
        // Pigman common
        if (rand.nextInt(100) < 60) {
          return new ItemStack(Items.gold_nugget, 1 + rand.nextInt(fortune + 1));
        }
        return null;
      case GHAST:
        // Ghast common
        if (rand.nextInt(100) < 50) {
          return new ItemStack(Items.ghast_tear, 1 + rand.nextInt(fortune + 1));
        }
        return null;
      case MAGMA_CUBE:
        return null;
      case WITHER:
        // Wither skull
        if (doHeadDrop(rand, fortune)) {
          return new ItemStack(Blocks.skull, 1, 1);
        }
        // Wither skeleton common
        if (rand.nextInt(100) < 70) {
          return new ItemStack(Items.coal, 1 + rand.nextInt(fortune + 1));
        }
        return null;
      default:
        return null;
    }
  }
  
  private boolean doRareDrop(Random rand, int fortune) {
    
    return rand.nextInt(100) < 2 + 1 * fortune;
  }
  
  private boolean doHeadDrop(Random rand, int fortune) {
    
    return rand.nextInt(100) < 4 + 2 * fortune;
  }
  
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    
    switch ((EnumMob) state.getValue(MOB)) {
      case ZOMBIE:
        return Items.rotten_flesh;
      case SKELETON:
        return Items.bone;
      case CREEPER:
        return Items.gunpowder;
      case SPIDER:
        return Items.string;
      case ENDERMAN:
        return ModItems.shard;
      case SLIME:
        return Items.slime_ball;
      case WITCH:
        return getWitchDrop(rand, fortune);
      case PIGMAN:
        return Items.rotten_flesh;
      case GHAST:
        return Items.gunpowder;
      case MAGMA_CUBE:
        return Items.magma_cream;
      case WITHER:
        return Items.bone;
      default:
        return Items.poisonous_potato;
    }
  }
  
  private Item getWitchDrop(Random rand, int fortune) {
    
    int r = rand.nextInt(7);
    switch (r) {
      case 0:
        return Items.glass_bottle;
      case 1:
        return Items.glowstone_dust;
      case 2:
        return Items.gunpowder;
      case 3:
        return Items.glowstone_dust;
      case 4:
        return Items.spider_eye;
      case 5:
        return Items.stick;
      case 6:
        return Items.sugar;
      default:
        return Items.poisonous_potato;
    }
  }
  
  @Override
  public int quantityDropped(Random random) {

    return 1 + random.nextInt(2);
  }

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
