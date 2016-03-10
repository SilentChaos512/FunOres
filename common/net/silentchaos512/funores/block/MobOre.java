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
import net.minecraft.entity.monster.EntityEndermite;
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
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigItemDrop;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.item.ModItems;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.wit.api.IWitHudInfo;

public class MobOre extends BlockSG implements IWitHudInfo {

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
  public void addOreDict() {

    for (EnumMob mob : EnumMob.values()) {
      OreDictionary.registerOre("ore" + mob.getName(), new ItemStack(this, 1, mob.getMeta()));
    }
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!player.isSneaking() && !advanced) {
      return null;
    }

    EnumMob mob= EnumMob.byMetadata(state.getBlock().getMetaFromState(state));
    ConfigOptionOreGen config = mob.getConfig();
    return ModBlocks.getWitInfoForOre(config, state, pos, player);
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
    if ((EnumMob) state.getValue(MOB) == EnumMob.ENDERMAN
        && FunOres.instance.random.nextFloat() < Config.spawnEndermiteChance) {
      if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
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
}
