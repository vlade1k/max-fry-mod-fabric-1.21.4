package ru.vlade1k.maxfrymod.callback;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.StartWorldTick;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.vlade1k.maxfrymod.util.WorldUtil;

public class EventRegistry {

  private final static StartWorldTick INVISIBILITY_ITEM_EVENT = (world -> {
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
  });

  public static void register() {
    ServerTickEvents.START_WORLD_TICK.register(INVISIBILITY_ITEM_EVENT);
  }
}
