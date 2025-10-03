package ru.vlade1k.maxfrymod.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.extensiondata.ExtensionDataSaver;
import ru.vlade1k.maxfrymod.util.PlayerUtility;

public class MemoryCrystallItem extends Item {

  public MemoryCrystallItem(Settings settings) {
    super(settings);
  }

  @Override
  public ActionResult use(World world, PlayerEntity player, Hand hand) {
    if (world instanceof ServerWorld serverWorld) {
      var playerViewBlockPos = PlayerUtility.getBlockHitResultWithDist(serverWorld, player, 2.5d).getBlockPos();
      var block = serverWorld.getBlockState(playerViewBlockPos).getBlock();
      System.out.println(block);
      if (Blocks.AIR.equals(block)) {
        var memoryCrystallData = ExtensionDataSaver.getUserData(player, serverWorld);
        if (memoryCrystallData == null) {
          return ActionResult.PASS;
        }
        player.teleport(memoryCrystallData.x(), memoryCrystallData.y(), memoryCrystallData.z(), true);
        return ActionResult.SUCCESS;
      } else {
        ExtensionDataSaver.addUserData(player, serverWorld);
        return ActionResult.SUCCESS;
      }
    }

    return ActionResult.PASS;
  }
}
