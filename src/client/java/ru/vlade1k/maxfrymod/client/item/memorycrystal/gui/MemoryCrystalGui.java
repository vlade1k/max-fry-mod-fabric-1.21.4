package ru.vlade1k.maxfrymod.client.item.memorycrystal.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import ru.vlade1k.maxfrymod.extensiondata.data.PlayerMemoryCrystalData;
import ru.vlade1k.maxfrymod.network.c2s.MemoryCrystalC2SPayload;
import ru.vlade1k.maxfrymod.network.data.PlayerTeleportationData;

public class MemoryCrystalGui extends Screen {
  private final static int BUTTON_WIDTH = 150;
  private final static int BUTTON_HEIGHT = 20;

  private final PlayerMemoryCrystalData crystalData;

  public MemoryCrystalGui(Text title, PlayerMemoryCrystalData data) {
    super(title);
    crystalData = data;
  }

  @Override
  protected void init() {
    super.init();

    double heightInPercent = this.height / 100d;

    double xPosition = this.width / 2d - BUTTON_WIDTH / 2d;
    double yPosition = this.height / 2d - BUTTON_HEIGHT / 2d - heightInPercent * 40;

    var positions = crystalData.positions();

    for (int i = 0; i < positions.size(); i++) {
      var currentPosition = positions.get(i);

      var buttonText = String.format(
          "x: %.2f, y: %.2f, z: %.2f",
          currentPosition.getX(),
          currentPosition.getY(),
          currentPosition.getZ()
      );

      var button = ButtonWidget.builder(
          Text.literal((i + 1) + ". " +  buttonText),
          click -> {
            if (this.client == null || this.client.player == null) {
              return;
            }
            var teleportData = new PlayerTeleportationData(
                this.client.player.getUuid(),
                currentPosition
            );
            var payload = new MemoryCrystalC2SPayload(teleportData);
            ClientPlayNetworking.send(payload);
          }
      ).dimensions((int) xPosition, (int) yPosition, BUTTON_WIDTH, BUTTON_HEIGHT).build();

      this.addDrawableChild(button);

      yPosition += BUTTON_HEIGHT + (heightInPercent * 5);
    }
  }
}
