package ru.vlade1k.maxfrymod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.World;

public class PlayerUtility {

  public static BlockHitResult getBlockHitResultWithDist(World world, PlayerEntity player, double dist) {
    Vec3d vec3d = player.getEyePos();
    Vec3d vec3d2 = vec3d.add(player.getRotationVector(player.getPitch(), player.getYaw()).multiply(dist));
    return world.raycast(new RaycastContext(vec3d, vec3d2, ShapeType.OUTLINE, FluidHandling.ANY, player));
  }
}
