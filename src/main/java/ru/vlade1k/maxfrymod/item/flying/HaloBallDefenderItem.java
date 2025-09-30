package ru.vlade1k.maxfrymod.item.flying;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.itementity.HaloBallDefenderEntity;

public class HaloBallDefenderItem extends Item {
  public HaloBallDefenderItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    if (world instanceof ServerWorld serverWorld) {
      var entity = new HaloBallDefenderEntity(world, user);
      serverWorld.spawnEntity(entity);
      return ActionResult.SUCCESS;
    }

    return ActionResult.PASS;
  }

  @Override
  public int getMaxUseTime(ItemStack stack, LivingEntity user) {
    return 0;
  }
}
