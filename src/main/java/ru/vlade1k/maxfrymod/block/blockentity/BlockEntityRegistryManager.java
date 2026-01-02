package ru.vlade1k.maxfrymod.block.blockentity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.vlade1k.maxfrymod.MaxFryMod;
import ru.vlade1k.maxfrymod.block.BlockRegistryManager;

public class BlockEntityRegistryManager {

  public static final BlockEntityType<LightningBoltDefenderEntity>
      BLOCK_DEFENDER_ENTITY = register("lightning_bolt_defender", LightningBoltDefenderEntity::new, BlockRegistryManager.LIGHT_BOLT_DEFENDER_BLOCK);

  private static <T extends BlockEntity> BlockEntityType<T> register(
        String entityName,
        FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
        Block... blocks
  ) {
    return Registry.register(Registries.BLOCK_ENTITY_TYPE,
                             Identifier.of(MaxFryMod.MOD_ID, entityName),
                             FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
  }

  public static void clinit() { }
}
