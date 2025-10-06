package ru.vlade1k.maxfrymod.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.extensiondata.MemoryCrystalExtensionDataHandler;
import ru.vlade1k.maxfrymod.util.PlayerUtility;

public class MemoryCrystalItem extends Item {

  public MemoryCrystalItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity player, Hand hand) {
    if (world instanceof ServerWorld serverWorld) {
      var playerViewBlockPos = PlayerUtility.getBlockHitResultWithDist(serverWorld, player, 2.5d).getBlockPos();
      var block = serverWorld.getBlockState(playerViewBlockPos).getBlock();

      if (Blocks.AIR.equals(block)) {
        var memoryCrystalData = MemoryCrystalExtensionDataHandler.getUserData(player, serverWorld);

        if (memoryCrystalData == null) {
          return ActionResult.PASS;
        }

        if (!memoryCrystalData.positions().isEmpty()) {
          Vec3d teleportPosition = memoryCrystalData.positions().getLast();
          player.teleport(
              teleportPosition.getX(),
              teleportPosition.getY(),
              teleportPosition.getZ(),
              true
          );

          return ActionResult.SUCCESS;
        } else {
          return ActionResult.PASS;
        }
      } else {
        MemoryCrystalExtensionDataHandler.addUserData(player, serverWorld);
        return ActionResult.SUCCESS;
      }
    }

    return ActionResult.PASS;
  }
}
