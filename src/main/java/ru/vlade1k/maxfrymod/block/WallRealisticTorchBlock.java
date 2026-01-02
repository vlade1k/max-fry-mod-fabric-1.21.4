package ru.vlade1k.maxfrymod.block;

import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;

public class WallRealisticTorchBlock extends WallTorchBlock {

  public WallRealisticTorchBlock(Settings settings) {
    super(ParticleTypes.DRAGON_BREATH, settings);
  }
}
