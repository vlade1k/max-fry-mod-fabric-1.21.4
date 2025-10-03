package ru.vlade1k.maxfrymod.item;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.server.network.ServerPlayerEntity;
import ru.vlade1k.maxfrymod.util.WorldUtil;

public class InvisibilityCapeItem extends ArmorItem {

  static {
    ServerTickEvents.START_WORLD_TICK.register(world -> {
      for (var entity : world.iterateEntities()) {
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
  }

  public InvisibilityCapeItem(Settings settings) {
    super(ArmorMaterials.TURTLE_SCUTE, EquipmentType.CHESTPLATE, settings);
  }
}
