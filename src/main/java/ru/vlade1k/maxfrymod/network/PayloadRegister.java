package ru.vlade1k.maxfrymod.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import ru.vlade1k.maxfrymod.network.c2s.MemoryCrystalC2SPayload;
import ru.vlade1k.maxfrymod.network.s2c.MemoryCrystalS2CPayload;

public class PayloadRegister {

  public static void registerS2C() {
    PayloadTypeRegistry.playS2C().register(MemoryCrystalS2CPayload.ID, MemoryCrystalS2CPayload.CODEC);
  }

  public static void registerC2S() {
    PayloadTypeRegistry.playC2S().register(MemoryCrystalC2SPayload.ID, MemoryCrystalC2SPayload.CODEC);
  }
}
