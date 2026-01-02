package ru.vlade1k.maxfrymod.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WallRealisticTorchBlock extends WallTorchBlock {

  public static final BooleanProperty WALL_TORCH_LIT = BooleanProperty.of("wall_torch_lit");

  public WallRealisticTorchBlock(Settings settings) {
    super(ParticleTypes.DRAGON_BREATH, settings);
    setDefaultState(getDefaultState().with(WALL_TORCH_LIT, true));
  }

  @Override
  protected void appendProperties(Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(WALL_TORCH_LIT);
  }

  @Override
  protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
    super.onBlockAdded(state, world, pos, oldState, notify);
    world.scheduleBlockTick(pos, this, 1);
  }

  @Override
  protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    if (world.isSkyVisible(pos) && world.isRaining() && state.get(WALL_TORCH_LIT)) {
      world.setBlockState(pos, state.with(WALL_TORCH_LIT, false));
    }
    world.scheduleBlockTick(pos, this, 1);
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (world.isClient()) {
      return ActionResult.PASS;
    }

    var handItemStack = player.getStackInHand(player.getActiveHand());

    if (handItemStack.getItem().equals(Items.FLINT_AND_STEEL)) {
      if (handItemStack.getMaxDamage() - handItemStack.getDamage() > 0) {
        handItemStack.setDamage(handItemStack.getDamage() + 1);
        world.setBlockState(pos, state.with(WALL_TORCH_LIT, true));
        return ActionResult.SUCCESS;
      }
    }

    return ActionResult.FAIL;
  }
}
