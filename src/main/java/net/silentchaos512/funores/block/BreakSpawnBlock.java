package net.silentchaos512.funores.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nullable;

public abstract class BreakSpawnBlock extends Block {
    public BreakSpawnBlock() {
        super(Properties.of(Material.STONE).strength(1.5f, 10f));
    }

    @Nullable
    public abstract LivingEntity getBreakSpawn(BlockState state, Level world);

    @SuppressWarnings("deprecation")
    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack stack) {
        super.spawnAfterBreak(state, world, pos, stack);

        LivingEntity entity = getBreakSpawn(state, world);
        if (entity != null) {
            entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
            world.addFreshEntity(entity);

            if (entity instanceof PathfinderMob) {
                ((PathfinderMob) entity).spawnAnim();
            }
        }
    }
}
