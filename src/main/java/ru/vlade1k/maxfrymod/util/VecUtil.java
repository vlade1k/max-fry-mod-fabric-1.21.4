package ru.vlade1k.maxfrymod.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class VecUtil {

  public static Vec3d getNextCoordinatesByStep(Vec3d from, Vec3d to, double step) {
    Vec3d direction = to.subtract(from);
    return from.add(direction.normalize().multiply(step));
  }

  public static Vec3d getEntityPositionVector(Entity entity) {
    return new Vec3d(entity.getX(), entity.getY(), entity.getZ());
  }

}
