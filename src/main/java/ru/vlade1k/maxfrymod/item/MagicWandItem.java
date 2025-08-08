package ru.vlade1k.maxfrymod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.util.PlayerUtility;
import ru.vlade1k.maxfrymod.util.WorldUtil;

public class MagicWandItem extends Item {

  public MagicWandItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    if (world instanceof ServerWorld serverWorld) {
      var blockDist = PlayerUtility.getBlockHitResultWithDist(serverWorld, user, 100D);
      if (blockDist == null) {
        return ActionResult.FAIL;
      }
      var blockPos = blockDist.getBlockPos();
      var lightBoltEntity = WorldUtil.spawnLightningBolt(serverWorld, blockPos);
      WorldUtil.damageEntitiesByLightningBoltWithSpecialDamage(serverWorld,
                                                               world.getOtherEntities(lightBoltEntity, new Box(blockPos).expand(2)),
                                                               40f);
      return ActionResult.SUCCESS;
    }
    return ActionResult.PASS;
  }
}
