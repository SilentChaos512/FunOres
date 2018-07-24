package net.silentchaos512.funores.init;

import net.minecraft.block.Block;
import net.silentchaos512.funores.block.BlockAlloy;
import net.silentchaos512.funores.block.BlockMetal;
import net.silentchaos512.funores.block.BlockOreMeat;
import net.silentchaos512.funores.block.BlockOreMetal;
import net.silentchaos512.funores.block.BlockOreMob;
import net.silentchaos512.funores.block.machine.BlockAlloySmelter;
import net.silentchaos512.funores.block.machine.BlockDryingRack;
import net.silentchaos512.funores.block.machine.BlockMetalFurnace;
import net.silentchaos512.funores.item.block.ItemBlockOre;
import net.silentchaos512.funores.item.block.ItemBlockOreDrops;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.IRegistrationHandler;
import net.silentchaos512.lib.registry.SRegistry;

public class ModBlocks implements IRegistrationHandler<Block> {

  public static BlockOreMetal metalOre = new BlockOreMetal();
  public static BlockOreMeat meatOre = new BlockOreMeat();
  public static BlockOreMob mobOre = new BlockOreMob();
  public static BlockMetal metalBlock = new BlockMetal();
  public static BlockAlloy alloyBlock = new BlockAlloy();
  public static BlockMetalFurnace metalFurnace = new BlockMetalFurnace();
  public static BlockAlloySmelter alloySmelter = new BlockAlloySmelter();
  public static BlockDryingRack dryingRack = new BlockDryingRack();

  @Override
  public void registerAll(SRegistry reg) {

    reg.registerBlock(metalOre, new ItemBlockOre(metalOre));
    reg.registerBlock(meatOre, new ItemBlockOreDrops(meatOre));
    reg.registerBlock(mobOre, new ItemBlockOreDrops(mobOre));
    reg.registerBlock(metalBlock);
    reg.registerBlock(alloyBlock);
    reg.registerBlock(metalFurnace, Names.METAL_FURNACE);
    reg.registerBlock(alloySmelter, Names.ALLOY_SMELTER);
    reg.registerBlock(dryingRack, Names.DRYING_RACK);
  }
}
