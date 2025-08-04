package ru.vlade1k.maxfrymod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import ru.vlade1k.maxfrymod.MaxFryMod;

import java.util.function.Function;

public class ItemRegistryManager {

  public static final Item MAGIC_WAND = register("magic_wand", MagicWandItem::new, new Item.Settings().useCooldown(1));

  public static Item register(String name, Function<Settings, Item> itemFactory, Item.Settings settings) {
    RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MaxFryMod.MOD_ID, name));
    Item item = itemFactory.apply(settings.registryKey(itemKey));
    Registry.register(Registries.ITEM, itemKey, item);
    return item;
  }

  public static void clinit() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(MAGIC_WAND));
  }
}
