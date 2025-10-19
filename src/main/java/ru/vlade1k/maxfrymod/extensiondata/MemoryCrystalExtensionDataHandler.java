package ru.vlade1k.maxfrymod.extensiondata;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.Nullable;
import ru.vlade1k.maxfrymod.MaxFryMod;
import ru.vlade1k.maxfrymod.extensiondata.data.PlayerMemoryCrystalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MemoryCrystalExtensionDataHandler extends PersistentState {
  private static final Type<MemoryCrystalExtensionDataHandler> TYPE = new Type<>(
      MemoryCrystalExtensionDataHandler::new,
      MemoryCrystalExtensionDataHandler::createFromNbt,
      null
  );

  private final HashMap<UUID, PlayerMemoryCrystalData> extensionData = new HashMap<>();

  @Override
  public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup registries) {
    var playersNbt = new NbtCompound();

    extensionData.forEach((uuid, extensionPlayerData) -> {
      var playerNbt = new NbtCompound();
      var positions = new NbtCompound();

      extensionPlayerData.positions().forEach(position -> {
        var coords = new NbtCompound();
        coords.putDouble("x", position.getX());
        coords.putDouble("y", position.getY());
        coords.putDouble("z", position.getZ());
        positions.put("position", coords);
      });

      playerNbt.put("positions", positions);
      playersNbt.put(uuid.toString(), playerNbt);
    });

    nbt.put("players", playersNbt);
    return nbt;
  }

  public static MemoryCrystalExtensionDataHandler createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
    var state = new MemoryCrystalExtensionDataHandler();

    var playersNbt = tag.getCompound("players");
    playersNbt.getKeys().forEach(key -> {
      List<Vec3d> playerPositions = new ArrayList<>();

      var playerNbt = playersNbt.getCompound(key);

      var positionsNbt = playerNbt.getCompound("positions");

      positionsNbt.getKeys().forEach(positionKey -> {
          NbtCompound positionNbt = positionsNbt.getCompound(positionKey);
          Vec3d vecPosition = new Vec3d(
              positionNbt.getDouble("x"),
              positionNbt.getDouble("y"),
              positionNbt.getDouble("z")
          );
          playerPositions.add(vecPosition);
      });

      PlayerMemoryCrystalData data = new PlayerMemoryCrystalData(playerPositions);
      state.extensionData.put(UUID.fromString(key), data);
    });

    return state;
  }

  @Nullable
  public static PlayerMemoryCrystalData getUserData(PlayerEntity player, ServerWorld world) {
    var extensionHolder = getServerState(world);
    return extensionHolder.extensionData.get(player.getUuid());
  }

  public static void addUserData(PlayerEntity player, ServerWorld world) {
    var extensionHolder = getServerState(world);
    extensionHolder.extensionData.computeIfAbsent(
        player.getUuid(),
        k -> new PlayerMemoryCrystalData(new ArrayList<>())
    ).positions().add(new Vec3d(player.getX(), player.getY(), player.getZ()));
  }

  private static MemoryCrystalExtensionDataHandler getServerState(ServerWorld server) {
    var persistentStateManager = server.getPersistentStateManager();
    var state = persistentStateManager.getOrCreate(TYPE, MaxFryMod.MOD_ID);
    state.markDirty();
    return state;
  }
}