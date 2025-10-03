package ru.vlade1k.maxfrymod.extensiondata;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.Nullable;
import ru.vlade1k.maxfrymod.MaxFryMod;

import java.util.HashMap;
import java.util.UUID;

public class ExtensionDataSaver extends PersistentState {
  private static final Type<ExtensionDataSaver> TYPE = new Type<>(
      ExtensionDataSaver::new,
      ExtensionDataSaver::createFromNbt,
      null
  );

  private final HashMap<UUID, PlayerMemoryCrystallData> extensionData = new HashMap<>();

  @Override
  public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup registries) {
    var playersNbt = new NbtCompound();

    extensionData.forEach((uuid, extensionPlayerData) -> {
      NbtCompound playerNbt = new NbtCompound();
      playerNbt.putDouble("x", extensionPlayerData.x());
      playerNbt.putDouble("y", extensionPlayerData.y());
      playerNbt.putDouble("z", extensionPlayerData.z());
      playersNbt.put(uuid.toString(), playerNbt);
    });

    nbt.put("players", playersNbt);
    return nbt;
  }

  public static ExtensionDataSaver createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
    var state = new ExtensionDataSaver();

    var playersNbt = tag.getCompound("players");
    playersNbt.getKeys().forEach(key -> {
      NbtCompound playerData = playersNbt.getCompound(key);
      double x = playerData.getDouble("x");
      double y = playerData.getDouble("y");
      double z = playerData.getDouble("z");

      PlayerMemoryCrystallData data = new PlayerMemoryCrystallData(x, y, z);
      state.extensionData.put(UUID.fromString(key), data);
    });

    return state;
  }

  @Nullable
  public static PlayerMemoryCrystallData getUserData(PlayerEntity player, ServerWorld world) {
    var extensionHolder = getServerState(world);

    return extensionHolder.extensionData.get(player.getUuid());
  }

  public static void addUserData(PlayerEntity player, ServerWorld world) {
    var extensionHolder = getServerState(world);
    extensionHolder.extensionData.put(player.getUuid(), new PlayerMemoryCrystallData(
        player.getX(),
        player.getY(),
        player.getZ()
    ));
  }

  private static ExtensionDataSaver getServerState(ServerWorld server) {
    var persistentStateManager = server.getPersistentStateManager();
    var state = persistentStateManager.getOrCreate(TYPE, MaxFryMod.MOD_ID);
    state.markDirty();
    return state;
  }
}
