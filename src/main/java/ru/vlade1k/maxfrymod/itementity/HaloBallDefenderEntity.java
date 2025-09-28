package ru.vlade1k.maxfrymod.itementity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker.Builder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import ru.vlade1k.maxfrymod.item.ItemRegistryManager;

public class HaloBallDefenderEntity extends Entity implements FlyingItemEntity {
  private World world;
  private PlayerEntity owner;
  private static final double RADIUS = 0.7;
  private static final double THETA_DELTA = 0.05;
  private double xCurrCoord;
  private double yCurrCoord;
  private double zCurrCoord;
  private double theta;
  private boolean isAttackMod;
  private boolean isInitStage;

  private static final int ALIVE_TIME = 600;
  private int currentTick = 0;

  public HaloBallDefenderEntity(EntityType<?> type, World world) {
    super(type, world);
  }

  public HaloBallDefenderEntity(World world, PlayerEntity owner) {
    super(ItemEntityRegistryManager.ENTITY_TYPE_HALO_BALL, world);
    this.world = world;
    this.owner = owner;
    this.isAttackMod = false;
    this.isInitStage = true;
    this.theta = 0;
  }

  @Override
  public void tick() {
    super.tick();

    if (!this.getWorld().isClient()) {
      this.currentTick += 1;

      if (!isAttackMod) {
        this.yCurrCoord = owner.getY() + 2;
        if (isInitStage) {
          this.xCurrCoord = owner.getX() + RADIUS;
          this.zCurrCoord = owner.getZ() + RADIUS;
          this.isInitStage = false;
        } else {
          this.xCurrCoord = owner.getX() + RADIUS * Math.cos(theta);
          this.zCurrCoord = owner.getZ() + RADIUS * Math.sin(theta);
          this.theta += THETA_DELTA;
        }

        setPos(this.xCurrCoord, this.yCurrCoord, this.zCurrCoord);

        if (currentTick == ALIVE_TIME) {
          discard();
        }
      }
    }
  }

  @Override
  public ItemStack getStack() {
    return new ItemStack(ItemRegistryManager.HALLO_BALL_DEFENDER);
  }

  @Override
  protected void initDataTracker(Builder builder) {

  }

  @Override
  public boolean damage(ServerWorld world, DamageSource source, float amount) {
    return false;
  }

  @Override
  protected void readCustomDataFromNbt(NbtCompound nbt) {
    this.xCurrCoord = nbt.getDouble("x_cord");
    this.yCurrCoord = nbt.getDouble("y_cord");
    this.zCurrCoord = nbt.getDouble("z_cord");
    this.isAttackMod = nbt.getBoolean("is_attack_mod");
    this.isInitStage = nbt.getBoolean("is_init_stage");
  }

  @Override
  protected void writeCustomDataToNbt(NbtCompound nbt) {
    nbt.putDouble("x_cord", xCurrCoord);
    nbt.putDouble("y_cord", yCurrCoord);
    nbt.putDouble("z_cord", zCurrCoord);
    nbt.putBoolean("is_attack_mod", isAttackMod);
    nbt.putBoolean("is_init_stage", isInitStage);
  }
}
