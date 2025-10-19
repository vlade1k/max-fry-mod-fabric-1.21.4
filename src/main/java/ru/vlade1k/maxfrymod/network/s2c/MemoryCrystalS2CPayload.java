package ru.vlade1k.maxfrymod.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import ru.vlade1k.maxfrymod.MaxFryMod;
import ru.vlade1k.maxfrymod.extensiondata.data.PlayerMemoryCrystalData;

public record MemoryCrystalS2CPayload(PlayerMemoryCrystalData memoryCrystalData) implements CustomPayload {
  public static final Identifier
    OPEN_MEMORY_CRYSTAL_GUI_ID = Identifier.of(MaxFryMod.MOD_ID, "memory_crystal_payload");

  public static final CustomPayload.Id<MemoryCrystalS2CPayload>
    ID = new CustomPayload.Id<>(OPEN_MEMORY_CRYSTAL_GUI_ID);

  public static final PacketCodec<ByteBuf, MemoryCrystalS2CPayload>
    CODEC = PacketCodec.tuple(
      PlayerMemoryCrystalData.PACKET_CODEC,
      MemoryCrystalS2CPayload::memoryCrystalData,
      MemoryCrystalS2CPayload::new
    );

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
