package ru.vlade1k.maxfrymod.extensiondata;

import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
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
  private static final Codec<List<Vec3d>> POSITIONS_CODEC = Vec3d.CODEC.listOf().xmap(ArrayList::new, ArrayList::new);

  @Override
  public NbtCompound writeNbt(NbtCompound nbt, WrapperLookup registries) {
    var playersNbt = new NbtCompound();

    extensionData.forEach((uuid, extensionPlayerData) -> {
      var positions = POSITIONS_CODEC.encodeStart(NbtOps.INSTANCE, extensionPlayerData.positions())
                                     .getOrThrow();
      playersNbt.put(uuid.toString(), positions);
    });

    nbt.put("players", playersNbt);
    return nbt;
  }

  public static MemoryCrystalExtensionDataHandler createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
    var state = new MemoryCrystalExtensionDataHandler();
    var playersNbt = tag.getCompound("players");

    playersNbt.getKeys().forEach(key -> {
      var playerUuid = UUID.fromString(key);
      var playerNbt = playersNbt.get(key);

      var positions = POSITIONS_CODEC.parse(NbtOps.INSTANCE, playerNbt).getOrThrow();
      var memoryData = new PlayerMemoryCrystalData(positions);

      state.extensionData.put(playerUuid, memoryData);
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