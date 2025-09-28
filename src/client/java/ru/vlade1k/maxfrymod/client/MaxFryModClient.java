package ru.vlade1k.maxfrymod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import ru.vlade1k.maxfrymod.itementity.ItemEntityRegistryManager;

public class MaxFryModClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    EntityRendererRegistry.register(ItemEntityRegistryManager.ENTITY_TYPE_HALO_BALL, FlyingItemEntityRenderer::new);
  }
}
