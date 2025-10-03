package ru.vlade1k.maxfrymod.register;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import ru.vlade1k.maxfrymod.MaxFryMod;
import ru.vlade1k.maxfrymod.block.LightningBoltDefenderBlock;

import java.util.function.Function;

public class BlockRegistryManager {

  public static final Block
      LIGHT_BOLT_DEFENDER_BLOCK = register("lightning_bolt_defender",
                                           LightningBoltDefenderBlock::new,
                                           AbstractBlock.Settings.create().hardness(100),
                                           true);

  private static Block register(String blockName, Function<Settings, Block> blockCreator, Settings settings, boolean shouldBeRegistered) {
    RegistryKey<Block> blockKey = keyOfBlock(blockName);
    Block block = blockCreator.apply(settings.registryKey(blockKey));

    if (shouldBeRegistered) {
      RegistryKey<Item> blockItemKey = keyOfItem(blockName);
      BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(blockItemKey));
      Registry.register(Registries.ITEM, blockItemKey, blockItem);
    }

    return Registry.register(Registries.BLOCK, blockKey, block);
  }

  private static RegistryKey<Block> keyOfBlock(String name) {
    return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MaxFryMod.MOD_ID, name));
  }

  private static RegistryKey<Item> keyOfItem(String name) {
    return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MaxFryMod.MOD_ID, name));
  }

  public static void register() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> itemGroup.add(LIGHT_BOLT_DEFENDER_BLOCK));
  }
}
