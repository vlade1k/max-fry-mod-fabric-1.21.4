package ru.vlade1k.maxfrymod.register;

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
import ru.vlade1k.maxfrymod.item.InvisibilityCapeItem;
import ru.vlade1k.maxfrymod.item.MagicWandItem;
import ru.vlade1k.maxfrymod.item.HaloBallDefenderItem;
import ru.vlade1k.maxfrymod.item.MemoryCrystalItem;

import java.util.function.Function;

public class ItemRegistryManager {
  public static final Item MAGIC_WAND = register("magic_wand", MagicWandItem::new, new Item.Settings().useCooldown(1));
  public static final Item INVISIBILITY_CAPE = register("invisibility_cape", InvisibilityCapeItem::new, new Item.Settings());
  public static final Item HALLO_BALL_DEFENDER = register("halo_ball", HaloBallDefenderItem::new, new Item.Settings());
  public static final Item MEMORY_CRYSTAL = register("memory_crystal", MemoryCrystalItem::new, new Item.Settings());

  public static Item register(String name, Function<Settings, Item> itemFactory, Item.Settings settings) {
    RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MaxFryMod.MOD_ID, name));
    Item item = itemFactory.apply(settings.registryKey(itemKey));
    Registry.register(Registries.ITEM, itemKey, item);
    return item;
  }

  public static void register() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(MAGIC_WAND));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> itemGroup.add(INVISIBILITY_CAPE));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> itemGroup.add(HALLO_BALL_DEFENDER));
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register((itemGroup) -> itemGroup.add(MEMORY_CRYSTAL));
  }
}
