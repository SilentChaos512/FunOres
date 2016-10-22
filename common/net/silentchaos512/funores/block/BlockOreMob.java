package net.silentchaos512.funores.block;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.oredict.OreDictionary;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.configuration.ConfigOptionOreGenBonus;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.util.OreLootHelper;
import net.silentchaos512.wit.api.IWitHudInfo;

public class BlockOreMob extends BlockFunOre implements IWitHudInfo {

  public static final PropertyEnum MOB = PropertyEnum.create("mob", EnumMob.class);

  public BlockOreMob() {

    super(EnumMob.count(), Names.MOB_ORE);

    setHardness(1.5f);
    setResistance(10.0f);
    setSoundType(SoundType.STONE);
    setHarvestLevel("pickaxe", 0);

    setUnlocalizedName(Names.MOB_ORE);
  }

  @Override
  public ConfigOptionOreGen getConfig(int meta) {

    if (meta < 0 || meta >= EnumMob.values().length)
      return null;
    return EnumMob.byMetadata(meta).getConfig();
  }

  @Override
  public boolean isEnabled(int meta) {

    if (Config.disableMobOres)
      return false;

    ConfigOptionOreGen config = getConfig(meta);
    return config == null ? false : config.enabled;
  }

  @Override
  public void addOreDict() {

    for (EnumMob mob : EnumMob.values()) {
      ItemStack stack = new ItemStack(this, 1, mob.getMeta());
      if (!FunOres.registry.isItemDisabled(stack))
        OreDictionary.registerOre("ore" + mob.getName(), stack);
    }
  }

  @Override
  public List<String> getWitLines(IBlockState state, BlockPos pos, EntityPlayer player,
      boolean advanced) {

    if (!player.isSneaking() && !advanced) {
      return null;
    }

    EnumMob mob = EnumMob.byMetadata(state.getBlock().getMetaFromState(state));
    ConfigOptionOreGen config = mob.getConfig();
    return ModBlocks.getWitInfoForOre(config, state, pos, player);
  }

  @Override
  public List<ModelResourceLocation> getVariants() {

    List<ModelResourceLocation> models = Lists.newArrayList();
    for (EnumMob mob : EnumMob.values()) {
      if (!FunOres.registry.isItemDisabled(new ItemStack(this, 1, mob.meta))) {
        String name = FunOres.MOD_ID + ":Ore" + mob.getUnmodifiedName();
        models.add(new ModelResourceLocation(name, "inventory"));
      } else {
        models.add(null);
      }
    }
    return models;
  }

  @Override
  public int damageDropped(IBlockState state) {

    return ((EnumMob) state.getValue(MOB)).getMeta();
  }

  @Override
  public void getSubBlocks(Item item, CreativeTabs tab, List list) {

    for (EnumMob mob : EnumMob.values()) {
      ItemStack stack = new ItemStack(item, 1, mob.meta);
      if (!FunOres.registry.isItemDisabled(stack))
        list.add(stack);
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
  protected BlockStateContainer createBlockState() {

    return new BlockStateContainer(this, new IProperty[] { MOB });
  }

  @Override
  public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {

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
  public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state,
      int fortune) {

    Random rand = world instanceof World ? ((World) world).rand : RANDOM;

    if (world instanceof WorldServer) {
      WorldServer worldServer = (WorldServer) world;
      EnumMob mob = ((EnumMob) state.getValue(MOB));
      EntityLivingBase entityLiving = mob.getEntityLiving(worldServer);
      int tryCount = 1;
      ConfigOptionOreGenBonus config = ((EnumMob) state.getValue(MOB)).getConfig();
      return OreLootHelper.getDrops(worldServer, fortune, mob, tryCount, config);
    }

    return Lists.newArrayList();
  }
}
