package ru.vlade1k.maxfrymod.extensiondata.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public record PlayerMemoryCrystalData(
    List<Vec3d> positions
) {
  public static final Codec<PlayerMemoryCrystalData>
      MEMORY_CRYSTAL_CODEC = RecordCodecBuilder.create(
          instance ->
            instance.group(
              Vec3d.CODEC.listOf().fieldOf("positions").forGetter(PlayerMemoryCrystalData::positions)
            ).apply(instance, PlayerMemoryCrystalData::new)
      );

  public static final PacketCodec<ByteBuf, PlayerMemoryCrystalData>
      PACKET_CODEC = PacketCodec.of(
                       (value, buf) -> {
                         buf.writeInt(value.positions.size());
                         for (var position : value.positions) {
                           buf.writeDouble(position.getX());
                           buf.writeDouble(position.getY());
                           buf.writeDouble(position.getZ());
                         }
                       },
                       buf -> {
                         List<Vec3d> positions = new ArrayList<>();

                         int positionCount = buf.readInt();
                         for (int i = 0; i < positionCount; i++) {
                           var xPosition = buf.readDouble();
                           var yPosition = buf.readDouble();
                           var zPosition = buf.readDouble();

                           Vec3d position = new Vec3d(xPosition, yPosition, zPosition);
                           positions.add(position);
                         }

                         return new PlayerMemoryCrystalData(positions);
                       }
      );
}
