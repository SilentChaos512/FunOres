package net.silentchaos512.funores.lib;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface ILootTableDrops {

  @Nullable
  public EntityLivingBase getEntityLiving(World worldIn);

  @Nullable
  public ResourceLocation getLootTable(EntityLivingBase entityLiving);
}
