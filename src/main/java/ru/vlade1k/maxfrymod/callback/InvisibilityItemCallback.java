package ru.vlade1k.maxfrymod.callback;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.vlade1k.maxfrymod.util.WorldUtil;

public class InvisibilityItemCallback {

  public static void registerEvent() {
    ServerTickEvents.START_WORLD_TICK.register((world -> {
      for (var entity:  world.iterateEntities()) {
        if (entity instanceof ServerPlayerEntity playerEntity) {
          var chestSlot = playerEntity.getInventory().getArmorStack(2);

          if (WorldUtil.isInvisibilityItem(chestSlot.getItem().getClass())) {
            if (!playerEntity.isInvisible()) {
              playerEntity.setInvisible(true);
            }
          } else {
            if (playerEntity.isInvisible()) {
              playerEntity.setInvisible(false);
            }
          }
        }
      }
    }));
  }
}
