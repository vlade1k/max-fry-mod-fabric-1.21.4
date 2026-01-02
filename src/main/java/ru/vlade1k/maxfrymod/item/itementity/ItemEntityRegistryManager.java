package ru.vlade1k.maxfrymod.item.itementity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import ru.vlade1k.maxfrymod.MaxFryMod;

public class ItemEntityRegistryManager {

  public static final EntityType<? extends HaloBallDefenderEntity>
      ENTITY_TYPE_HALO_BALL = register("hallo_ball_type_entity", HaloBallDefenderEntity::new, SpawnGroup.MISC);


  public static <T extends Entity> EntityType.Builder<T> createTypeBuilder(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
    return EntityType.Builder.<T>create(factory, spawnGroup);
  }

  public static <T extends Entity> EntityType<T> register(String id, EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
    var identifier = Identifier.of(MaxFryMod.MOD_ID, id);
    var registryKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier);
    var typeBuilder = createTypeBuilder(factory, spawnGroup).build(registryKey);
    return Registry.register(Registries.ENTITY_TYPE, identifier, typeBuilder);
  }
}
