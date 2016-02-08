package net.silentchaos512.funores.block;

import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.core.registry.SRegistry;
import net.silentchaos512.funores.core.util.LogHelper;
import net.silentchaos512.funores.item.block.ItemBlockOreDrops;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.funores.world.FunOresGenerator;

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

    metalOre = (MetalOre) SRegistry.registerBlock(MetalOre.class, Names.METAL_ORE);
    meatOre = (MeatOre) SRegistry.registerBlock(MeatOre.class, Names.MEAT_ORE,
        ItemBlockOreDrops.class);
    mobOre = (MobOre) SRegistry.registerBlock(MobOre.class, Names.MOB_ORE, ItemBlockOreDrops.class);
    metalBlock = (MetalBlock) SRegistry.registerBlock(MetalBlock.class, Names.METAL_BLOCK);
    alloyBlock = (AlloyBlock) SRegistry.registerBlock(AlloyBlock.class, Names.ALLOY_BLOCK);
    metalFurnace = (MetalFurnace) SRegistry.registerBlock(MetalFurnace.class, Names.METAL_FURNACE);
    alloySmelter = (AlloySmelter) SRegistry.registerBlock(AlloySmelter.class, Names.ALLOY_SMELTER);
    dryingRack = (BlockDryingRack) SRegistry.registerBlock(BlockDryingRack.class, Names.DRYING_RACK);
  }

  public static List<String> getWitInfoForOre(ConfigOptionOreGen config, IBlockState state, BlockPos pos,
      EntityPlayer player) {

    if (config == null) {
      return null;
    }

    // Show average veins per chunk (cluster count divided by rarity)
    BiomeGenBase biome = FunOresGenerator.getBiomeForPos(player.worldObj, pos);
    float veinsPerChunk = (float) config.getClusterCountForBiome(biome) / config.rarity;
    String veinCountString = String.format("%.3f", veinsPerChunk);

    char c = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    String decimalSep = c == '.' ? "\\." : Character.toString(c);
    String regex = decimalSep + "?0+$";
    veinCountString = veinCountString.replaceFirst(regex, "");

    String line = String.format("%s veins per chunk (%s)", veinCountString, biome.biomeName);
    line = line.replaceFirst(regex, "");

    return Lists.newArrayList(line);
  }
}
