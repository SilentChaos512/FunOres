package net.silentchaos512.funores.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class LootDropOreWithSpawn extends LootDropOre {
    public LootDropOreWithSpawn(Function<World, LivingEntity> entityFactory) {
        super();
    }

    @Nullable
    public abstract LivingEntity getBreakSpawn(BlockState state, World world);

    @Override
    public void spawnAdditionalDrops(BlockState state, World world, BlockPos pos, ItemStack stack) {
        super.spawnAdditionalDrops(state, world, pos, stack);

        LivingEntity entity = getBreakSpawn(state, world);
        if (entity != null) {
            entity.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
            world.addEntity(entity);

            if (entity instanceof CreatureEntity) {
                ((CreatureEntity) entity).spawnExplosionParticle();
            }
        }
    }
}
