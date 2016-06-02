package net.silentchaos512.funores.block;

import java.text.DecimalFormatSymbols;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.item.block.ItemBlockOre;
import net.silentchaos512.funores.item.block.ItemBlockOreDrops;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks {

  public static MetalOre metalOre;
  public static MeatOre meatOre;
  public static MobOre mobOre;
  public static MetalBlock metalBlock;
  public static AlloyBlock alloyBlock;
  public static MetalFurnace metalFurnace;
  public static AlloySmelter alloySmelter;
  public static BlockDryingRack dryingRack;

  public static void init() {

    SRegistry reg = FunOres.instance.registry;
    metalOre = new MetalOre();
    metalOre = (MetalOre) reg.registerBlock(metalOre, new ItemBlockOre(metalOre));
    meatOre = new MeatOre();
    meatOre = (MeatOre) reg.registerBlock(meatOre, new ItemBlockOreDrops(meatOre));
    mobOre = new MobOre();
    mobOre = (MobOre) reg.registerBlock(mobOre, new ItemBlockOreDrops(mobOre));
    metalBlock = (MetalBlock) reg.registerBlock(new MetalBlock());
    alloyBlock = (AlloyBlock) reg.registerBlock(new AlloyBlock());
    metalFurnace = (MetalFurnace) reg.registerBlock(new MetalFurnace(), Names.METAL_FURNACE);
    alloySmelter = (AlloySmelter) reg.registerBlock(new AlloySmelter(), Names.ALLOY_SMELTER);
    dryingRack = (BlockDryingRack) reg.registerBlock(new BlockDryingRack(), Names.DRYING_RACK);
  }

  public static List<String> getWitInfoForOre(ConfigOptionOreGen config, IBlockState state,
      BlockPos pos, EntityPlayer player) {

    if (config == null) {
      return null;
    }

    // Show average veins per chunk (cluster count divided by rarity)
    Biome biome = FunOresGenerator.getBiomeForPos(player.worldObj, pos);
    float veinsPerChunk = (float) config.getClusterCountForBiome(biome) / config.rarity;
    String veinCountString = String.format("%.3f", veinsPerChunk);

    char c = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    String decimalSep = c == '.' ? "\\." : Character.toString(c);
    String regex = decimalSep + "?0+$";
    veinCountString = veinCountString.replaceFirst(regex, "");

    String line = String.format("%s veins per chunk (%s)", veinCountString, biome.getBiomeName());
    line = line.replaceFirst(regex, "");

    return Lists.newArrayList(line);
  }
}
