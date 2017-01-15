package net.silentchaos512.funores.init;

import java.text.DecimalFormatSymbols;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockAlloy;
import net.silentchaos512.funores.block.BlockOreMeat;
import net.silentchaos512.funores.block.BlockMetal;
import net.silentchaos512.funores.block.BlockOreMetal;
import net.silentchaos512.funores.block.BlockOreMob;
import net.silentchaos512.funores.block.machine.BlockAlloySmelter;
import net.silentchaos512.funores.block.machine.BlockDryingRack;
import net.silentchaos512.funores.block.machine.BlockMetalFurnace;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.item.block.ItemBlockOre;
import net.silentchaos512.funores.item.block.ItemBlockOreDrops;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks {

  public static BlockOreMetal metalOre = new BlockOreMetal();
  public static BlockOreMeat meatOre = new BlockOreMeat();
  public static BlockOreMob mobOre = new BlockOreMob();
  public static BlockMetal metalBlock = new BlockMetal();
  public static BlockAlloy alloyBlock = new BlockAlloy();
  public static BlockMetalFurnace metalFurnace = new BlockMetalFurnace();
  public static BlockAlloySmelter alloySmelter = new BlockAlloySmelter();
  public static BlockDryingRack dryingRack = new BlockDryingRack();

  public static void init() {

    SRegistry reg = FunOres.instance.registry;
    reg.registerBlock(metalOre, new ItemBlockOre(metalOre));
    reg.registerBlock(meatOre, new ItemBlockOreDrops(meatOre));
    reg.registerBlock(mobOre, new ItemBlockOreDrops(mobOre));
    reg.registerBlock(metalBlock);
    reg.registerBlock(alloyBlock);
    reg.registerBlock(metalFurnace, Names.METAL_FURNACE);
    reg.registerBlock(alloySmelter, Names.ALLOY_SMELTER);
    reg.registerBlock(dryingRack, Names.DRYING_RACK);
  }

  public static List<String> getWitInfoForOre(ConfigOptionOreGen config, IBlockState state,
      BlockPos pos, EntityPlayer player) {

    if (config == null) {
      return null;
    }

    // Show average veins per chunk (cluster count divided by rarity)
    Biome biome = FunOresGenerator.getBiomeForPos(player.world, pos);
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
