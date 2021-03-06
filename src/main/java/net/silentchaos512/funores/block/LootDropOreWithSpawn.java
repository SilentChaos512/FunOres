package net.silentchaos512.funores.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.function.Function;

public abstract class LootDropOreWithSpawn extends LootDropOre {
    public LootDropOreWithSpawn(Function<World, LivingEntity> entityFactory) {
        super();
    }

    @Nullable
    public abstract LivingEntity getBreakSpawn(BlockState state, World world);

    @Override
    public void spawnAfterBreak(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.spawnAfterBreak(state, world, pos, stack);

        LivingEntity entity = getBreakSpawn(state, world);
        if (entity != null) {
            entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
            world.addFreshEntity(entity);

            if (entity instanceof CreatureEntity) {
                ((CreatureEntity) entity).spawnAnim();
            }
        }
    }
}
