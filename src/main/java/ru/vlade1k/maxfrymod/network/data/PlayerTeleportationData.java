package ru.vlade1k.maxfrymod.network.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public record PlayerTeleportationData(
    UUID playerId,
    Vec3d positionToTeleport
) {

  public static final PacketCodec<ByteBuf, PlayerTeleportationData>
      PACKET_CODEC = PacketCodec.of(
        (value, buf) -> {
          var bytesUUID = value.playerId().toString().getBytes();

          buf.writeInt(bytesUUID.length);
          for (byte b : bytesUUID) {
            buf.writeByte(b);
          }

          var positionToTeleport = value.positionToTeleport;

          buf.writeDouble(positionToTeleport.getX());
          buf.writeDouble(positionToTeleport.getY());
          buf.writeDouble(positionToTeleport.getZ());
        },
        (buf) -> {
          int byteLength = buf.readInt();
          byte[] bytesUUID = new byte[byteLength];
          for (int i = 0; i < byteLength; i++) {
            bytesUUID[i] = buf.readByte();
          }

          UUID playerId = UUID.fromString(new String(bytesUUID));

          double x = buf.readDouble();
          double y = buf.readDouble();
          double z = buf.readDouble();

          Vec3d position = new Vec3d(x, y, z);

          return new PlayerTeleportationData(playerId, position);
        }
  );
}
