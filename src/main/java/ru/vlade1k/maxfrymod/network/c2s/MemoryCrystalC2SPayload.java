package ru.vlade1k.maxfrymod.network.c2s;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import ru.vlade1k.maxfrymod.MaxFryMod;
import ru.vlade1k.maxfrymod.network.data.PlayerTeleportationData;

public record MemoryCrystalC2SPayload(
  PlayerTeleportationData teleportationData
) implements CustomPayload {

  public static final Identifier TELEPORT_INFO_PAYLOAD_ID = Identifier.of(MaxFryMod.MOD_ID, "teleport_data_payload");
  public static CustomPayload.Id<MemoryCrystalC2SPayload> ID = new CustomPayload.Id<>(TELEPORT_INFO_PAYLOAD_ID);

  public static PacketCodec<ByteBuf, MemoryCrystalC2SPayload>
    CODEC = PacketCodec.tuple(
      PlayerTeleportationData.PACKET_CODEC,
      MemoryCrystalC2SPayload::teleportationData,
      MemoryCrystalC2SPayload::new
    );

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
