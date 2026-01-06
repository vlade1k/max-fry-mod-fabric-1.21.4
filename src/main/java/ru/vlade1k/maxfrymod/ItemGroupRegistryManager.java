package ru.vlade1k.maxfrymod;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupRegistryManager {
  public static final RegistryKey<ItemGroup>
      COMMON_GROUP_KEY = RegistryKey.of(
                                Registries.ITEM_GROUP.getKey(),
                                Identifier.of(
                                    MaxFryMod.MOD_ID,
                                    "common_item_group"
                                )
      );

  public static final ItemGroup
      COMMON_GROUP = FabricItemGroup.builder()
                                    .displayName(Text.translatable("itemGroup.commonMFModGroup"))
                                    .icon(() -> new ItemStack(Items.ENDER_EYE))
                                    .build();

  public static void register() {
    Registry.register(Registries.ITEM_GROUP, COMMON_GROUP_KEY, COMMON_GROUP);
  }
}
