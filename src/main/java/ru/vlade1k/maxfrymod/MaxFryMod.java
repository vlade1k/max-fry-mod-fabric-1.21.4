package ru.vlade1k.maxfrymod;

import net.fabricmc.api.ModInitializer;
import ru.vlade1k.maxfrymod.block.BlockRegistryManager;
import ru.vlade1k.maxfrymod.blockentity.BlockEntityRegistryManager;
import ru.vlade1k.maxfrymod.item.ItemRegistryManager;

public class MaxFryMod implements ModInitializer {
  public static final String MOD_ID = "maxfrymod";

  @Override
  public void onInitialize() {
    ItemRegistryManager.clinit();
    BlockRegistryManager.clinit();
    BlockEntityRegistryManager.clinit();
  }
}
