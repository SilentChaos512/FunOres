/*
 * Fun Ores -- ModBlocks
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

package net.silentchaos512.funores.init;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.block.BlockLootDropOre;
import net.silentchaos512.funores.util.ModelGenerator;

import javax.annotation.Nullable;

public final class ModBlocks {
    private ModBlocks() {}

    public static void registerAll(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        register(registry, "bat_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_BAT,
                (state, world) -> new EntityBat(world)));

        register(registry, "blaze_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_BLAZE,
                (state, world) -> new EntityBlaze(world)));

        register(registry, "chicken_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_CHICKEN,
                (state, world) -> new EntityChicken(world)));

        register(registry, "cow_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_COW,
                (state, world) -> new EntityCow(world)));

        register(registry, "creeper_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_CREEPER,
                (state, world) -> new EntityCreeper(world)));

        // TODO: Enderman ore should spawn endermites occasionally
        register(registry, "enderman_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_ENDERMAN,
                (state, world) -> new EntityEnderman(world)));

        // TODO: Fish ore? Should it be split and associated with new fish entities?

        register(registry, "ghast_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_GHAST,
                (state, world) -> new EntityGhast(world)));

        register(registry, "guardian_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_GUARDIAN,
                (state, world) -> new EntityGuardian(world)));

        register(registry, "magma_cube_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_MAGMA_CUBE,
                (state, world) -> new EntityMagmaCube(world)));

        register(registry, "pig_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_PIG,
                (state, world) -> new EntityPig(world)));

        register(registry, "rabbit_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_RABBIT,
                (state, world) -> new EntityRabbit(world)));

        register(registry, "sheep_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_SHEEP,
                (state, world) -> new EntitySheep(world)) {
            @Nullable
            @Override
            public ResourceLocation getLootTable(IBlockState state, @Nullable EntityLivingBase entity) {
                if (!(entity instanceof EntitySheep)) return null;
                return getSheepLootTable((EntitySheep) entity);
            }
        });

        register(registry, "skeleton_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_SKELETON,
                (state, world) -> new EntitySkeleton(world)));

        register(registry, "slime_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_SLIME,
                (state, world) -> new EntitySlime(world)));

        register(registry, "spider_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_SPIDER,
                (state, world) -> new EntitySpider(world)));

        register(registry, "squid_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_SQUID,
                (state, world) -> new EntitySquid(world)));

        register(registry, "witch_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_WITCH,
                (state, world) -> new EntityWitch(world)));

        register(registry, "wither_skeleton_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_WITHER_SKELETON,
                (state, world) -> new EntityWitherSkeleton(world)));

        register(registry, "zombie_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_ZOMBIE,
                (state, world) -> new EntityZombie(world)));

        register(registry, "zombie_pigman_ore", new BlockLootDropOre(
                LootTableList.ENTITIES_ZOMBIE_PIGMAN,
                (state, world) -> new EntityPigZombie(world)));
    }

    private static void register(IForgeRegistry<Block> registry, String name, Block block) {
        ResourceLocation registryName = new ResourceLocation(FunOres.MOD_ID, name);
        block.setRegistryName(registryName);
        registry.register(block);

        ItemBlock itemBlock = new ItemBlock(block, new Item.Builder().group(ItemGroup.BUILDING_BLOCKS));
        itemBlock.setRegistryName(registryName);
        ForgeRegistries.ITEMS.register(itemBlock);

//        ModelGenerator.createFor(block);
    }

    private static ResourceLocation getSheepLootTable(EntitySheep sheep) {
        if (sheep.getSheared()) {
            return LootTableList.ENTITIES_SHEEP;
        } else {
            switch (sheep.getFleeceColor()) {
                case WHITE:
                default:
                    return LootTableList.ENTITIES_SHEEP_WHITE;
                case ORANGE:
                    return LootTableList.ENTITIES_SHEEP_ORANGE;
                case MAGENTA:
                    return LootTableList.ENTITIES_SHEEP_MAGENTA;
                case LIGHT_BLUE:
                    return LootTableList.ENTITIES_SHEEP_LIGHT_BLUE;
                case YELLOW:
                    return LootTableList.ENTITIES_SHEEP_YELLOW;
                case LIME:
                    return LootTableList.ENTITIES_SHEEP_LIME;
                case PINK:
                    return LootTableList.ENTITIES_SHEEP_PINK;
                case GRAY:
                    return LootTableList.ENTITIES_SHEEP_GRAY;
                case LIGHT_GRAY:
                    return LootTableList.ENTITIES_SHEEP_LIGHT_GRAY;
                case CYAN:
                    return LootTableList.ENTITIES_SHEEP_CYAN;
                case PURPLE:
                    return LootTableList.ENTITIES_SHEEP_PURPLE;
                case BLUE:
                    return LootTableList.ENTITIES_SHEEP_BLUE;
                case BROWN:
                    return LootTableList.ENTITIES_SHEEP_BROWN;
                case GREEN:
                    return LootTableList.ENTITIES_SHEEP_GREEN;
                case RED:
                    return LootTableList.ENTITIES_SHEEP_RED;
                case BLACK:
                    return LootTableList.ENTITIES_SHEEP_BLACK;
            }
        }
    }
}
