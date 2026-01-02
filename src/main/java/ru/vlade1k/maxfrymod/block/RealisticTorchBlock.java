package ru.vlade1k.maxfrymod.block;

import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleTypes;

public class RealisticTorchBlock extends TorchBlock {

  public RealisticTorchBlock(Settings settings) {
    super(ParticleTypes.DRAGON_BREATH, settings);
  }
}
