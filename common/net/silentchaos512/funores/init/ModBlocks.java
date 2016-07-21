package net.silentchaos512.funores.init;

import java.text.DecimalFormatSymbols;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.AlloyBlock;
import net.silentchaos512.funores.block.AlloySmelter;
import net.silentchaos512.funores.block.BlockDryingRack;
import net.silentchaos512.funores.block.MeatOre;
import net.silentchaos512.funores.block.MetalBlock;
import net.silentchaos512.funores.block.MetalFurnace;
import net.silentchaos512.funores.block.MetalOre;
import net.silentchaos512.funores.block.MobOre;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.item.block.ItemBlockOre;
import net.silentchaos512.funores.item.block.ItemBlockOreDrops;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks {

  public static MetalOre metalOre = new MetalOre();
  public static MeatOre meatOre = new MeatOre();
  public static MobOre mobOre = new MobOre();
  public static MetalBlock metalBlock = new MetalBlock();
  public static AlloyBlock alloyBlock = new AlloyBlock();
  public static MetalFurnace metalFurnace = new MetalFurnace();
  public static AlloySmelter alloySmelter = new AlloySmelter();
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
