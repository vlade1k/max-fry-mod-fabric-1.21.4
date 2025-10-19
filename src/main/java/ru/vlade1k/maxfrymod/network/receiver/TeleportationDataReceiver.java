package ru.vlade1k.maxfrymod.network.receiver;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import ru.vlade1k.maxfrymod.network.c2s.MemoryCrystalC2SPayload;

public class TeleportationDataReceiver {

  public static void receive() {
    ServerPlayNetworking.registerGlobalReceiver(
        MemoryCrystalC2SPayload.ID,
        (payload, context) -> {
          var position = payload.teleportationData().positionToTeleport();
          var player = context.player().getWorld().getPlayerByUuid(payload.teleportationData().playerId());
          if (player != null) {
            player.teleport(position.getX(), position.getY(), position.getZ(), true);
          }
        }
    );
  }
}
