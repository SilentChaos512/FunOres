/*
 * Fun Ores -- FunOresWitEvents
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.event;

import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.funores.configuration.Config;
import net.silentchaos512.funores.configuration.ConfigOptionOreGen;
import net.silentchaos512.funores.init.ModBlocks;
import net.silentchaos512.funores.lib.EnumMeat;
import net.silentchaos512.funores.lib.EnumMetal;
import net.silentchaos512.funores.lib.EnumMob;
import net.silentchaos512.funores.world.FunOresGenerator;
import net.silentchaos512.lib.util.BiomeHelper;
import net.silentchaos512.wit.api.WitBlockInfoEvent;

public class FunOresWitEvents {

    public static final FunOresWitEvents INSTANCE = new FunOresWitEvents();

    Map<IBlockState, ConfigOptionOreGen> ores = Maps.newHashMap();

    FunOresWitEvents() {

        // Vanilla
        ores.put(Blocks.COAL_ORE.getDefaultState(), Config.coal);
        ores.put(Blocks.DIAMOND_ORE.getDefaultState(), Config.diamond);
        ores.put(Blocks.EMERALD_ORE.getDefaultState(), Config.emerald);
        ores.put(Blocks.GOLD_ORE.getDefaultState(), Config.gold);
        ores.put(Blocks.IRON_ORE.getDefaultState(), Config.iron);
        ores.put(Blocks.LAPIS_ORE.getDefaultState(), Config.lapis);
        ores.put(Blocks.QUARTZ_ORE.getDefaultState(), Config.quartz);
        ores.put(Blocks.LIT_REDSTONE_ORE.getDefaultState(), Config.redstone);
        ores.put(Blocks.REDSTONE_ORE.getDefaultState(), Config.redstone);

        // Metals
        for (EnumMetal metal : EnumMetal.values())
            ores.put(metal.getOre(), metal.getConfig());

        // Meats
        for (EnumMeat meat : EnumMeat.values())
            ores.put(meat.getOre(), meat.getConfig());

        // Mobs
        for (EnumMob mob : EnumMob.values())
            ores.put(mob.getOre(), mob.getConfig());
    }

    @SubscribeEvent
    public void onBlockInfo(WitBlockInfoEvent event) {

        if (event.player.isSneaking() || event.advanced) {
            ConfigOptionOreGen config = ores.get(event.blockState);
            if (config == null)
                return;

            // Show average veins per chunk (cluster count divided by rarity)
            Biome biome = FunOresGenerator.getBiomeForPos(event.player.world, event.pos);
            float veinsPerChunk = (float) config.getClusterCountForBiome(biome) / config.rarity;
            String veinCountString = String.format("%.3f", veinsPerChunk);

            char c = DecimalFormatSymbols.getInstance().getDecimalSeparator();
            String decimalSep = c == '.' ? "\\." : Character.toString(c);
            String regex = decimalSep + "?0+$";
            veinCountString = veinCountString.replaceFirst(regex, "");

            String line = String.format("%s veins per chunk (%s)", veinCountString, biome.getBiomeName());
            line = line.replaceFirst(regex, "");
            event.lines.add(line);

            // List biome types
            line = "";
            for (BiomeDictionary.Type biomeType : BiomeHelper.getTypes(biome))
                line += biomeType.toString() + ", ";
            event.lines.add(line.replaceFirst(", $", ""));
        }
    }
}
