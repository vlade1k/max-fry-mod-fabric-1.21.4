package ru.vlade1k.maxfrymod;

import net.fabricmc.api.ModInitializer;
import ru.vlade1k.maxfrymod.register.BlockRegistryManager;
import ru.vlade1k.maxfrymod.register.BlockEntityRegistryManager;
import ru.vlade1k.maxfrymod.register.EventRegistry;
import ru.vlade1k.maxfrymod.register.ItemRegistryManager;

public class MaxFryMod implements ModInitializer {
  public static final String MOD_ID = "maxfrymod";

  @Override
  public void onInitialize() {
    BlockEntityRegistryManager.clinit();

    ItemRegistryManager.register();
    BlockRegistryManager.register();
    EventRegistry.register();
  }
}
