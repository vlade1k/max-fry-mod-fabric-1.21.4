package ru.vlade1k.maxfrymod.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.register.BlockEntityRegistryManager;
import ru.vlade1k.maxfrymod.util.WorldUtil;

public class LightningBoltDefenderEntity extends BlockEntity {
  private int tickCount = 0;

  public LightningBoltDefenderEntity(BlockPos pos, BlockState state) {
    super(BlockEntityRegistryManager.BLOCK_DEFENDER_ENTITY, pos, state);
  }

  public void incrementTick() {
    tickCount++;
    if (tickCount == 20) {
      tickCount = 0;
    }
  }

  public int getTick() {
    return tickCount;
  }

  public static void tick(World world, BlockPos blockPos, BlockState blockState, LightningBoltDefenderEntity blockEntity) {
    blockEntity.incrementTick();

    if (blockEntity.getTick() != 0) {
        return;
    }

    if (world instanceof ServerWorld serverWorld) {
      for(var entity : serverWorld.iterateEntities()) {
        if (entity instanceof Monster && WorldUtil.entityIsIntersectsWithBox(entity, new Box(blockPos).expand(20))) {
          WorldUtil.spawnLightningBolt(serverWorld, entity.getBlockPos());
          WorldUtil.damageEntityWithSpecialDamage(serverWorld, DamageTypes.LIGHTNING_BOLT, entity, 40f);
        }
      }
    }
  }
}
