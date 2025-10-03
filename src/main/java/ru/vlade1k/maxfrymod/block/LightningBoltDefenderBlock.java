package ru.vlade1k.maxfrymod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import ru.vlade1k.maxfrymod.register.BlockEntityRegistryManager;
import ru.vlade1k.maxfrymod.blockentity.LightningBoltDefenderEntity;

public class LightningBoltDefenderBlock extends BlockWithEntity {

  public LightningBoltDefenderBlock(Settings settings) {
    super(settings);
  }

  @Nullable
  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new LightningBoltDefenderEntity(pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return validateTicker(type, BlockEntityRegistryManager.BLOCK_DEFENDER_ENTITY, LightningBoltDefenderEntity::tick);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> GameEventListener getGameEventListener(ServerWorld world, T blockEntity) {
    return super.getGameEventListener(world, blockEntity);
  }

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return createCodec(LightningBoltDefenderBlock::new);
  }
}
