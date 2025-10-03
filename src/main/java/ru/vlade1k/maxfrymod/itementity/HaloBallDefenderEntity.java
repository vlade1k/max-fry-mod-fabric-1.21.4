package ru.vlade1k.maxfrymod.itementity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker.Builder;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;
import ru.vlade1k.maxfrymod.register.ItemRegistryManager;
import ru.vlade1k.maxfrymod.util.VecUtil;

import java.util.UUID;

public class HaloBallDefenderEntity extends Entity implements FlyingItemEntity {
  private static final int ALIVE_TIME = 30 * 20;
  private static final double RADIUS = 0.7;
  private static final double THETA_DELTA = 0.15;
  private static final double ATTACK_SPEED = 0.2;

  private PlayerEntity owner;
  private UUID ownerUuid;
  private Entity enemy;
  private UUID enemyUuid;
  private double theta;
  private boolean isAttackMod;
  private boolean isInitStage;


  private int currentTick = 0;

  public HaloBallDefenderEntity(EntityType<?> type, World world) {
    super(type, world);
  }

  public HaloBallDefenderEntity(World world, PlayerEntity owner) {
    super(ItemEntityRegistryManager.ENTITY_TYPE_HALO_BALL, world);
    this.owner = owner;
    this.ownerUuid = owner.getUuid();
    this.isAttackMod = false;
    this.isInitStage = true;
    this.theta = 0;
  }

  @Override
  public void tick() {
    super.tick();

    if (!this.getWorld().isClient()) {
      if (currentTick == ALIVE_TIME) {
        discard();
      }

      if (owner == null && !isOwnerInit()) {
        return;
      }

      if (!isAttackMod) {
        double y = owner.getY() + 2;
        double x = 0;
        double z = 0;

        if (isInitStage) {
          x = owner.getX() + RADIUS;
          z = owner.getZ() + RADIUS;
          isInitStage = false;
        } else {
          x = owner.getX() + RADIUS * Math.cos(theta);
          z = owner.getZ() + RADIUS * Math.sin(theta);
          this.theta += THETA_DELTA;
        }

        this.setPos(x, y, z);

        var badEntityBox = Box.from(Vec3d.of(this.owner.getBlockPos())).expand(10, 0, 10);
        var badEntity = this.getWorld().getOtherEntities(this.owner, badEntityBox)
                                       .stream()
                                       .filter(entity -> entity instanceof Monster)
                                       .findFirst();

        if (badEntity.isPresent()) {
          this.enemy = badEntity.get();
          this.enemyUuid = enemy.getUuid();
          this.isAttackMod = true;
        }

        this.currentTick += 1;
      } else {
        if (enemy == null) {
          if (!isRecoveredInAttackMod()) {
            return;
          }
        }

        var currentPos = VecUtil.getEntityPositionVector(this);
        var enemyPos = VecUtil.getEntityPositionVector(enemy);
        currentPos = VecUtil.getNextCoordinatesByStep(currentPos, enemyPos, ATTACK_SPEED);

        this.setPos(currentPos.getX(), currentPos.getY(), currentPos.getZ());

        if (enemy.getBoundingBox().intersects(currentPos, currentPos.add(3))) {
          this.getWorld().createExplosion(
              owner,
              enemy.getX() + 0.1,
              enemy.getEyeY() + 0.1,
              enemy.getZ() + 0.1,
              10,
              ExplosionSourceType.TRIGGER
          );
          this.discard();
        }
      }
    }
  }


  /**
   * При запуске сервера, игрок загружается после инициализации мира.
   * Соответственно, нужно смотреть, загрузился ли игрок прежде чем присваивать его
   * Entity
   *
   * @return true, если пользователь загрузился и присвоился
   */
  private boolean isOwnerInit() {
    owner = getWorld().getPlayerByUuid(ownerUuid);
    return owner != null;
  }

  /**
   * Поскольку при запуске сервера enemy может быть не инициализирован
   * при isAttackMod = true необходимо смотреть, загружена ли enemyEntity
   * в мир, а уже потом присваивать её этой Entity
   *
   * @return true, если Entity загрузилась и присвоилась полю enemy
   */
  public boolean isRecoveredInAttackMod() {
    enemy = ((ServerWorld) getWorld()).getEntity(enemyUuid);

    return enemy != null;
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
    readNbt(nbt);
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    double xCurrCoord = nbt.getDouble("x_cord");
    double yCurrCoord = nbt.getDouble("y_cord");
    double zCurrCoord = nbt.getDouble("z_cord");
    this.setPos(xCurrCoord, yCurrCoord, zCurrCoord);

    this.isAttackMod = nbt.getBoolean("is_attack_mod");
    this.isInitStage = nbt.getBoolean("is_init_stage");
    this.ownerUuid = nbt.getUuid("owner_uuid");
    this.currentTick = nbt.getInt("current_tick");

    if (nbt.containsUuid("enemy_uuid")) {
      this.enemyUuid = nbt.getUuid("enemy_uuid");
    }
  }

  @Override
  protected void writeCustomDataToNbt(NbtCompound nbt) {
    this.writeNbt(nbt);
  }

  @Override
  public NbtCompound writeNbt(NbtCompound nbt) {
    nbt.putDouble("x_cord", getX());
    nbt.putDouble("y_cord", getY());
    nbt.putDouble("z_cord", getZ());
    nbt.putBoolean("is_attack_mod", isAttackMod);
    nbt.putBoolean("is_init_stage", isInitStage);
    nbt.putUuid("owner_uuid", ownerUuid);
    nbt.putInt("current_tick", currentTick);

    if (enemy != null) {
      nbt.putUuid("enemy_uuid", enemyUuid);
    }

    return nbt;
  }
}
