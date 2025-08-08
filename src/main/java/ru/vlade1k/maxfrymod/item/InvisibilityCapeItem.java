package ru.vlade1k.maxfrymod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;

public class InvisibilityCapeItem extends ArmorItem {
  public InvisibilityCapeItem(Settings settings) {
    super(ArmorMaterials.TURTLE_SCUTE, EquipmentType.CHESTPLATE, settings);
  }
}
