package ru.vlade1k.maxfrymod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import ru.vlade1k.maxfrymod.item.InvisibilityCapeItem;

import java.util.Set;

public class WorldUtil {
  private static final Set<Class<? extends Item>> INVISIBILITY_ITEMS = Set.of(
      InvisibilityCapeItem.class
  );


  public static void damageEntitiesByLightningBoltWithSpecialDamage(ServerWorld serverWorld, Iterable<Entity> entities, float damage) {
    for (var entity : entities) {
      damageEntityByLightningBoltWithSpecialDamage(serverWorld, entity, damage);
    }
  }

  public static void damageEntityByLightningBoltWithSpecialDamage(ServerWorld serverWorld, Entity entity, float damage) {
    entity.damage(serverWorld,
                  new DamageSource(serverWorld.getRegistryManager()
                      .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                      .getEntry(DamageTypes.LIGHTNING_BOLT.getValue())
                      .get()),
                  damage);
  }

  public static boolean entityIsIntersectsWithBox(Entity entity, Box box) {
    return box.intersects(entity.getBoundingBox());
  }

  public static LightningEntity spawnLightningBolt(ServerWorld serverWorld, BlockPos blockPos) {
    var lightBoltEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, serverWorld);
    lightBoltEntity.setPos(blockPos.getX(),
                           blockPos.getY(),
                           blockPos.getZ());
    serverWorld.spawnEntity(lightBoltEntity);

    return lightBoltEntity;
  }

  public static boolean isInvisibilityItem(Class<? extends Item> item) {
    return INVISIBILITY_ITEMS.contains(item);
  }
}
