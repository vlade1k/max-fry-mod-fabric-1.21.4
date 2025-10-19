package ru.vlade1k.maxfrymod.item;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.extensiondata.MemoryCrystalExtensionDataHandler;
import ru.vlade1k.maxfrymod.network.s2c.MemoryCrystalS2CPayload;
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

        ServerPlayNetworking.send(
            (ServerPlayerEntity) player,
            new MemoryCrystalS2CPayload(memoryCrystalData)
        );

        return ActionResult.SUCCESS;
      } else {
        MemoryCrystalExtensionDataHandler.addUserData(player, serverWorld);
        return ActionResult.SUCCESS;
      }
    }

    return ActionResult.PASS;
  }
}
