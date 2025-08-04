package ru.vlade1k.maxfrymod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class WorldUtil {

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
}
