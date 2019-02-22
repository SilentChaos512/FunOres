package net.silentchaos512.funores.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class LootDropOreWithSpawn extends LootDropOre {
    public LootDropOreWithSpawn(ResourceLocation lootTableName, Function<World, EntityLivingBase> entityFactory) {
        super(lootTableName, entityFactory);
    }

    @Nullable
    public abstract EntityLivingBase getBreakSpawn(IBlockState state, World world);

    @Override
    public void dropBlockAsItemWithChance(IBlockState state, World worldIn, BlockPos pos, float chancePerItem, int fortune) {
        super.dropBlockAsItemWithChance(state, worldIn, pos, chancePerItem, fortune);

        EntityLivingBase entity = getBreakSpawn(state, worldIn);
        if (entity != null) {
            entity.setLocationAndAngles(
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    0.0f,
                    0.0f
            );
            worldIn.spawnEntity(entity);

            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).spawnExplosionParticle();
            }
        }
    }
}
