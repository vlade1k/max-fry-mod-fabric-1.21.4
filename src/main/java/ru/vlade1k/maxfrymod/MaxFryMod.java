package ru.vlade1k.maxfrymod;

import net.fabricmc.api.ModInitializer;
import ru.vlade1k.maxfrymod.network.PayloadRegister;
import ru.vlade1k.maxfrymod.network.receiver.TeleportationDataReceiver;
import ru.vlade1k.maxfrymod.register.BlockEntityRegistryManager;
import ru.vlade1k.maxfrymod.register.BlockRegistryManager;
import ru.vlade1k.maxfrymod.register.ItemRegistryManager;

public class MaxFryMod implements ModInitializer {
  public static final String MOD_ID = "maxfrymod";

  @Override
  public void onInitialize() {
    ItemRegistryManager.register();
    BlockRegistryManager.register();

    BlockEntityRegistryManager.clinit();

    PayloadRegister.registerS2C();
    PayloadRegister.registerC2S();

    TeleportationDataReceiver.receive();
  }
}
