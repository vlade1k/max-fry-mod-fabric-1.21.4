package ru.vlade1k.maxfrymod.client.receiver;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import ru.vlade1k.maxfrymod.client.item.memorycrystal.gui.MemoryCrystalGui;
import ru.vlade1k.maxfrymod.network.s2c.MemoryCrystalS2CPayload;

public class MemoryCrystalPackageReceiver {

  public static void receive() {
    ClientPlayNetworking.registerGlobalReceiver(
        MemoryCrystalS2CPayload.ID,
        (payload, context) -> {
          MinecraftClient.getInstance().setScreen(
              new MemoryCrystalGui(
                  Text.literal("MemoryCrystalGui"),
                  payload.memoryCrystalData()
              )
          );
        }
    );
  }

}
