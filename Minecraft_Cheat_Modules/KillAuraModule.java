package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.config.ModeSetting;
import catlavan.config.MultiBoxSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.module.player.AntiCheatBypassModule;
import catlavan.module.player.BotDetectorModule;
import catlavan.module.player.FreecamController;
import catlavan.network.PlayerMovePacket;
import catlavan.util.AimUtil;
import catlavan.util.BiConsumerImpl;
import catlavan.util.InventoryUtil;
import catlavan.util.McContextHolder;
import catlavan.util.MouseSensitivityUtil;
import catlavan.util.Rotation;
import catlavan.util.RotationHelper;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import sg.SgClass003;
import sg.SgClass040;
import sg.BlockRayTraceResult;
import sg.SgClass090;
import sg.Vec3d;
import sg.SgClass179;
import sg.PlayerEntity;
import sg.Enchantments;
import sg.Blocks;
import sg.SgClass224;
import sg.SgClass241;
import sg.MathHelper;
import sg.Entity;
import sg.SgClass343;
import sg.EnchantmentHelper;
import sg.SgClass361;
import sg.SgInterface023;
import sg.SgClass402;
import sg.ClientTickEvent;
import sg.Effects;
import sg.ItemStack;
import sg.Items;
import sg.ClientPlayerEntity;
import sg.LivingEntity;
import sg.Hand;
import sg.Item;
import sg.BlockPos;
import sg.RayTraceResultType;
import sg.HeldItemChangeC2SPacket;
import sg.Box;
import sg.Direction;
import sg.SgClass584;
import sg.SgClass596;
import sg.MinecraftClient;
import sg.SgClass617;

public class KillAuraModule extends Module implements McContextHolder {
   static double YAW_OFFSET = 90.0;
   static MinecraftClient mc;
   static float SNAP_YAW_SPEED = 255.0F;
   static float SNAP_PITCH_SPEED = 255.0F;
   MultiBoxSetting targetsGroup;
   static LivingEntity target;
   long attackTimer;
   BooleanSetting rayCastOption;
   ModeSetting bypassMode;
   static String TRIGGER_BOT_MODE_STR = "Trigger Bot";
   FloatSliderSetting attackRangeSlider;
   double ARMOR_WEIGHT_DIVISOR;
   BooleanSetting noHitWhileEatingOption;
   BooleanSetting noHitInBlocksOption;
   boolean sprintResetFlag;
   BooleanSetting hitInWallsBypassOption;
   BlockPos field_Lsg;
   int lastBreakTick;
   static long ATTACK_DELAY_MS = 460L;
   SecureRandom secureRandom;
   static float RANDOM_MIN = 0.88F;
   static float RANDOM_MAX = 0.94F;
   float critRandomFactor;
   BooleanSetting shieldBreakerOption;
   static String STR_BYPASS_LABEL_RU = "Обход";
   static String STR_BYPASS = "Bypass";
   static String STR_REALLY_WORLD = "ReallyWorld";
   static String STR_ATTACK_RANGE_LABEL_RU;
   static String STR_ATTACK_RANGE_EXTRA1;
   static String STR_ATTACK_RANGE_EXTRA2;
   static String STR_ATTACK_RANGE_EXTRA3;
   static String STR_ATTACK_RANGE_EXTRA4;
   static String STR_ATTACK_RANGE_EXTRA5;
   static String STR_ATTACK_RANGE_LABEL2_RU = "Дистания аттаки";
   static float ATTACK_RANGE_MIN = 3.0F;
   static float ATTACK_RANGE_DEFAULT = 3.0F;
   static float ATTACK_RANGE_MAX = 5.0F;
   static float ATTACK_RANGE_STEP = 0.1F;
   static String STR_ATTACK_RANGE = "Attack range";
   static String STR_ROTATE_DIST_LABEL_RU = "Дистанция ротации";
   static float ROTATE_DIST_MIN = 1.5F;
   static float ROTATE_DIST_MAX = 5.0F;
   static float ROTATE_DIST_STEP = 0.05F;
   static String STR_ROTATE_DISTANCE = "Rotate distance";
   FloatSliderSetting rotateDistSlider;
   static String STR_ELYTRA_ROTATE_LABEL_RU = "Элитра ротация";
   static float ELYTRA_ROTATE_MIN = 30.0F;
   static float ELYTRA_ROTATE_MAX = 30.0F;
   static float ELYTRA_ROTATE_STEP = 0.05F;
   static String STR_ELYTRA_ROTATE_DISTANCE = "Elytra rotate distance";
   FloatSliderSetting elytraRotateDistSlider;
   static String STR_RAYCAST_LABEL_RU = "Проверять точную наводку";
   static String STR_RAYCAST = "RayCast";
   static String STR_TARGETS_LABEL_RU = "Цели";
   static String STR_TARGETS = "Targets";
   static String STR_PLAYERS;
   static String STR_NAKEDS = "Players";
   static String STR_MOBS;
   static String STR_FRIENDS = "Nakeds";
   static String STR_SETTINGS_LABEL_RU;
   static String STR_SETTINGS = "Mobs";
   static String STR_ONLY_CRITS;
   static String STR_CORRECTOR_MOVE = "Friends";
   MultiBoxSetting settingsGroup;
   static String STR_ONLY_SPACE_CRITS_LABEL_RU = "Настройка";
   static String STR_ONLY_SPACE_CRITS = "Settings";
   static String STR_ONLY_CRITS_LABEL_RU;
   static String STR_ONLY_CRITS_KEY = "Only crits";
   static String STR_SHIELD_BREAKER_LABEL_RU;
   static String STR_SHIELD_BREAKER = "Corrector move";
   static String STR_ONLY_HOTBAR_LABEL_RU = "Только с пробелом";
   static String STR_ONLY_HOTBAR = "Only space crits";
   BooleanSetting onlyCritsOption;
   static String STR_HIT_IN_WALLS_LABEL_RU = "Ломать щит";
   static String STR_HIT_IN_WALLS_BYPASS = "Shield Breaker";
   static String STR_NO_HIT_IN_BLOCKS_LABEL_RU = "Только с хотбара";
   static String STR_NO_HIT_IN_BLOCKS = "Only in hotbar";
   BooleanSetting onlyHotbarOption;
   static String STR_NO_HIT_EATING_LABEL_RU = "Обход бить через стены (RW)";
   static String STR_NO_HIT_EATING = "Hit in Walls bypass (ReallyWorld)";
   static String STR_TPS_SYNC_LABEL_RU = "Не бить через блоки";
   static String STR_TPS_SYNC = "No hit in bloks";
   static String STR_REALLY_WORLD_BYPASS_KEY = "Не бить если ешь";
   static String STR_REALLY_WORLD_BYPASS_KEY2 = "No hit in eats";
   static String STR_TPS_SYNC_KEY = "Синхрон с тпс";
   static String STR_TPS_SYNC_VALUE = "TPS Sync";
   BooleanSetting tpsSyncOption;
   static float CRIT_RANDOM_INIT = 0.95F;
   Vec3d field_Lsg2;
   static int TICK_UNSET_VALUE = Integer.MIN_VALUE;
   boolean isDecelerating;
   float smoothRotationProgress;
   float smoothRotationYaw;
   float smoothRotationPitch;
   static double SMOOTH_YAW_OFFSET = 90.0;
   static float SMOOTH_PITCH_MIN = -40.0F;
   static float SMOOTH_PITCH_MAX = 40.0F;
   static double SMOOTH_RAY_REACH = 999.0;
   static double AABB_SHRINK_AMOUNT = 0.5;
   static float DECEL_PROGRESS_MIN = -0.01F;
   static float DECEL_LARGE_ANGLE_THRESHOLD = 80.0F;
   float DECEL_FAST_STEP;
   static float DECEL_SLOW_STEP = 0.0055F;
   static float DECEL_PROGRESS_THRESHOLD = -0.01F;
   static float ACCEL_STEP = 0.0034F;
   static float ACCEL_THRESHOLD = 0.36F;
   static float SMOOTH_YAW_FACTOR = 1.3F;
   static float SMOOTH_PITCH_FACTOR = 1.7F;
   static float SMOOTH_CLAMP_MIN_PITCH = -40.0F;
   static float SMOOTH_CLAMP_MAX_PITCH = 40.0F;
   static float SMOOTH_ROTATION_SPEED = 45.0F;
   static float SMOOTH_ROTATION_SPEED_STEP = 15.0F;
   double elytraYawOffset;
   float elytraAngleThreshold;
   float ELYTRA_MAX_YAW_DELTA;
   float ELYTRA_CLAMP_YAW_MIN;
   float ELYTRA_CLAMP_YAW_MAX;
   float ELYTRA_CLAMP_PITCH_MIN;
   float ELYTRA_CLAMP_PITCH_MAX;
   float ELYTRA_PITCH_MIN;
   float ELYTRA_PITCH_MAX;
   float ELYTRA_BASE_ROTATE_SPEED;
   float ELYTRA_TPS_ROTATE_SPEED;
   static double CRIT_FALL_CHECK_OFFSET_Y = SgClass017.0;
   static float CRIT_MIN_COOLDOWN = 0.5F;
   static float CRIT_SPRINT_COOLDOWN = 0.1F;
   double CRIT_Y_VELOCITY_THRESHOLD;
   double CRIT_Y_FALLING_THRESHOLD;
   double RAYCAST_EXTRA_REACH;
   double RAYCAST_FALLBACK_OFFSET;
   static String STR_FUNTIME_MODE = "FunTime";
   static String STR_HOLYWORLD_MODE = "HolyWorld";
   double PROTECTION_ENCHANT_WEIGHT;
   double BLAST_PROTECTION_WEIGHT;
   double FIRE_PROTECTION_WEIGHT;
   double PROJ_PROTECTION_WEIGHT;
   static double ATTACK_SPEED_TICKS = 40.0;
   static String STR_HOLYWORLD_BYPASS = "HolyWorld";
   String STR_REALLY_WORLD_MODE1;
   String STR_REALLY_WORLD_MODE2;
   String STR_FRIENDS_FILTER;
   String STR_PLAYERS_FILTER;
   String STR_NAKEDS_FILTER;
   static String STR_MOBS_FILTER = "Мобы";
   static double GROUND_CHECK_EXPAND_Y = 0.1F;
   Direction blockFace;
   static int TICK_RESET_VALUE = Integer.MIN_VALUE;
   static String STR_REALLY_WORLD_CHECK = "ReallyWorld";
   String STR_SNAP_MODE;
   static String STR_FUNTIME_ROTATION = "FunTime";
   String STR_SPOOKY_MODE;
   String STR_HOLYWORLD_ROTATION;
   String STR_TRIGGER_BOT;
   float SMOOTH_MAX_YAW_RANGE;
   float SMOOTH_MAX_PITCH_RANGE;
   double RAYCAST_MIN_DIST_SQ;
   float SMOOTH_INIT_PITCH_MIN;
   float SMOOTH_INIT_PITCH_MAX;

   private void snapRotateTo(Vec3d vec3d) {
      float f2 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - YAW_OFFSET);
      float f3 = (float)(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))));
      float f;
      float f1;
      if (mc.playerController.snapTicks < 2) {
         f = f2;
         f1 = f3;
      } else {
         f = FreecamController.getYaw();
         f1 = FreecamController.getPitch();
      }

      RotationHelper.aimAtSimple(new Rotation(f, f1), SNAP_YAW_SPEED, SNAP_PITCH_SPEED, 1, 6);
   }

   private void attackTick() {
      if (target != null) {
         if (this.shouldAttack() && this.attackTimer <= System.currentTimeMillis()) {
            if (!this.rayCastOption.getValue() && !this.bypassMode.is(TRIGGER_BOT_MODE_STR)) {
               this.doAttack();
            } else if (isEntityInRayCast(mc.player.rotationYaw, mc.player.rotationPitch, this.attackRangeSlider.get().floatValue(), target)) {
               this.doAttack();
            }
         }
      } else {
         this.attackTimer = System.currentTimeMillis();
      }
   }

   private double getEntityPriority(LivingEntity LivingEntity) {
      double d0 = LivingEntity.getHealth() + LivingEntity.getAbsorptionAmount();
      if (LivingEntity instanceof PlayerEntity) {
         PlayerEntity м3 = (PlayerEntity)LivingEntity;
         double d1 = this.getEntityTotalArmorWeight(м3);
         return d0 * (1.0 + d1 / ARMOR_WEIGHT_DIVISOR);
      } else {
         return d0;
      }
   }

   private void doAttack() {
      if (mc.player != null && mc.world != null && mc.playerController != null) {
         if (!this.noHitWhileEatingOption.getValue() || !mc.player.isHandActive() || mc.player.getHeldItemOffhand().getItem().equals(Items.SHIELD)) {
            if (!this.noHitInBlocksOption.getValue() || mc.player.canEntityBeSeen(target)) {
               if (mc.player.getDistanceEyePos(target) <= this.attackRangeSlider.get().floatValue()) {
                  boolean flag = mc.player.isInWater() || mc.player.isInLava() || mc.player.isSwimming();
                  if (!flag && mc.player.isSprinting()) {
                     this.sprintResetFlag = true;
                     if (ClientPlayerEntity.serverSprintState) {
                        return;
                     }
                  }

                  if (mc.player.isBlocking()) {
                     mc.player.stopActiveHand();
                  }

                  if (this.hitInWallsBypassOption.getValue()) {
                     if (this.field_Lsg != null && this.lastBreakTick == mc.player.ticksExisted) {
                        return;
                     }

                     this.startBlockDig();
                  }

                  this.attackTimer = System.currentTimeMillis() + ATTACK_DELAY_MS;
                  mc.playerController.attackEntity(mc.player, target);
                  mc.player.swingArm(Hand.MAIN_HAND);
                  this.critRandomFactor = this.secureRandom.nextFloat(RANDOM_MIN, RANDOM_MAX);
                  if (target instanceof PlayerEntity && this.shieldBreakerOption.getValue()) {
                     this.updateSmoothRotation();
                  }
               }
            }
         }
      }
   }

   public KillAuraModule() {
      this.bypassMode = new ModeSetting(
         STR_BYPASS_LABEL_RU,
         STR_BYPASS,
         STR_REALLY_WORLD,
         STR_ATTACK_RANGE_LABEL_RU,
         STR_ATTACK_RANGE_EXTRA1,
         STR_ATTACK_RANGE_EXTRA2,
         STR_ATTACK_RANGE_EXTRA3,
         STR_ATTACK_RANGE_EXTRA4,
         STR_ATTACK_RANGE_EXTRA5
      );
      this.attackRangeSlider = new FloatSliderSetting(
         STR_ATTACK_RANGE_LABEL2_RU, ATTACK_RANGE_MIN, ATTACK_RANGE_DEFAULT, ATTACK_RANGE_MAX, ATTACK_RANGE_STEP, STR_ATTACK_RANGE
      );
      this.rotateDistSlider = new FloatSliderSetting(STR_ROTATE_DIST_LABEL_RU, ROTATE_DIST_MIN, 0.0F, ROTATE_DIST_MAX, ROTATE_DIST_STEP, STR_ROTATE_DISTANCE);
      this.elytraRotateDistSlider = new FloatSliderSetting(
         STR_ELYTRA_ROTATE_LABEL_RU, ELYTRA_ROTATE_MIN, 0.0F, ELYTRA_ROTATE_MAX, ELYTRA_ROTATE_STEP, STR_ELYTRA_ROTATE_DISTANCE
      );
      this.rayCastOption = new BooleanSetting(STR_RAYCAST_LABEL_RU, false, STR_RAYCAST);
      this.settingsGroup = new MultiBoxSetting(
         STR_TARGETS_LABEL_RU,
         STR_TARGETS,
         new BooleanSetting(STR_PLAYERS, true, STR_NAKEDS),
         new BooleanSetting(STR_MOBS, true, STR_FRIENDS),
         new BooleanSetting(STR_SETTINGS_LABEL_RU, true, STR_SETTINGS),
         new BooleanSetting(STR_ONLY_CRITS, true, STR_CORRECTOR_MOVE)
      );
      this.targetsGroup = new MultiBoxSetting(
         STR_ONLY_SPACE_CRITS_LABEL_RU,
         STR_ONLY_SPACE_CRITS,
         new BooleanSetting(STR_ONLY_CRITS_LABEL_RU, true, STR_ONLY_CRITS_KEY),
         new BooleanSetting(STR_SHIELD_BREAKER_LABEL_RU, true, STR_SHIELD_BREAKER)
      );
      this.onlyCritsOption = new BooleanSetting(STR_ONLY_HOTBAR_LABEL_RU, true, STR_ONLY_HOTBAR).setSupplier(() -> this.targetsGroup.isCheckedByIndex(0));
      this.shieldBreakerOption = new BooleanSetting(STR_HIT_IN_WALLS_LABEL_RU, true, STR_HIT_IN_WALLS_BYPASS);
      this.onlyHotbarOption = new BooleanSetting(STR_NO_HIT_IN_BLOCKS_LABEL_RU, true, STR_NO_HIT_IN_BLOCKS).setSupplier(this.shieldBreakerOption::getValue);
      this.hitInWallsBypassOption = new BooleanSetting(STR_NO_HIT_EATING_LABEL_RU, false, STR_NO_HIT_EATING);
      this.noHitInBlocksOption = new BooleanSetting(STR_TPS_SYNC_LABEL_RU, false, STR_TPS_SYNC);
      this.noHitWhileEatingOption = new BooleanSetting(STR_REALLY_WORLD_BYPASS_KEY, false, STR_REALLY_WORLD_BYPASS_KEY2);
      this.tpsSyncOption = new BooleanSetting(STR_TPS_SYNC_KEY, false, STR_TPS_SYNC_VALUE);
      this.attackTimer = 0L;
      this.secureRandom = new SecureRandom();
      this.critRandomFactor = CRIT_RANDOM_INIT;
      this.field_Lsg2 = Vec3d.ZERO;
      this.lastBreakTick = TICK_UNSET_VALUE;
      this.isDecelerating = false;
      this.smoothRotationProgress = 0.0F;
      this.smoothRotationYaw = 0.0F;
      this.smoothRotationPitch = 0.0F;
      this.addSettings(
         new AbstractModuleSetting[]{
            this.bypassMode,
            this.attackRangeSlider,
            this.rotateDistSlider,
            this.elytraRotateDistSlider,
            this.rayCastOption,
            this.settingsGroup,
            this.targetsGroup,
            this.onlyCritsOption,
            this.shieldBreakerOption,
            this.onlyHotbarOption,
            this.hitInWallsBypassOption,
            this.noHitInBlocksOption,
            this.noHitWhileEatingOption
         }
      );
   }

   private void initSmoothRotation() {
      if (target != null) {
         Box элx = target.getBoundingBox();
         Vec3d дыxxxx = mc.player.getEyePosition(1.0F);
         Vec3d дыx = элx.getCenter();
         Vec3d дыxx = дыx.subtract(дыxxxx);
         float f = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(дыxx.z, дыxx.x)) - SMOOTH_YAW_OFFSET);
         float f1 = (float)(-Math.toDegrees(Math.atan2(дыxx.y, Math.hypot(дыxx.x, дыxx.z))));
         f1 = MathHelper.clamp(f1, SMOOTH_PITCH_MIN, SMOOTH_PITCH_MAX);
         Vec3d дыxxx = mc.player.getVectorForRotation(mc.player.rotationPitch, mc.player.rotationYaw);
         Vec3d дыxxxx = дыxxxx.add(дыxxx.scale(SMOOTH_RAY_REACH));
         Box элx = элx.shrink(AABB_SHRINK_AMOUNT);
         boolean flag = элx.rayTrace(дыxxxx, дыxxxx).isPresent();
         if (this.isDecelerating) {
            if (this.smoothRotationProgress >= DECEL_PROGRESS_MIN) {
               this.smoothRotationProgress = this.smoothRotationProgress
                  - (Math.abs(MathHelper.wrapDegrees(f - this.smoothRotationYaw)) > DECEL_LARGE_ANGLE_THRESHOLD ? DECEL_FAST_STEP : DECEL_SLOW_STEP);
            }

            if (this.smoothRotationProgress <= DECEL_PROGRESS_THRESHOLD) {
               this.isDecelerating = false;
            }
         } else {
            this.smoothRotationProgress = this.smoothRotationProgress + ACCEL_STEP;
            if (this.smoothRotationProgress >= ACCEL_THRESHOLD || flag) {
               this.isDecelerating = true;
            }
         }

         float f2 = MathHelper.wrapDegrees(f - this.smoothRotationYaw);
         float f3 = f1 - this.smoothRotationPitch;
         float f4 = Math.max(this.smoothRotationProgress, 0.0F);
         float f5 = this.smoothRotationYaw + f2 * MathHelper.clamp(f4 * SMOOTH_YAW_FACTOR, 0.0F, 1.0F);
         float f6 = this.smoothRotationPitch + f3 * MathHelper.clamp(f4 / SMOOTH_PITCH_FACTOR, 0.0F, 1.0F);
         float f7 = MouseSensitivityUtil.getSensitivityStep();
         f5 -= (f5 - this.smoothRotationYaw) % f7;
         f6 -= (f6 - this.smoothRotationPitch) % f7;
         f6 = MathHelper.clamp(f6, SMOOTH_CLAMP_MIN_PITCH, SMOOTH_CLAMP_MAX_PITCH);
         this.smoothRotationYaw = f5;
         this.smoothRotationPitch = f6;
         RotationHelper.aimAtSimple(new Rotation(f5, f6), SMOOTH_ROTATION_SPEED, SMOOTH_ROTATION_SPEED_STEP, 1, 1);
      }
   }

   private void stopBlockBreaker() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.StackOverflowError
      //   at java.base/java.util.concurrent.ConcurrentHashMap.computeIfAbsent(ConcurrentHashMap.java:1737)
      //   at org.jetbrains.java.decompiler.struct.StructContext.getClass(StructContext.java:78)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:318)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield catlavan/module/combat/KillAuraModule.field_Lsg2 Lsg/Vec3d;
      // SgClass001: astore 1
      // 05: aload 1
      // SgClass002: getfield sg/Vec3d.z D
      // 09: aload 1
      // 0a: getfield sg/Vec3d.x D
      // 0d: invokestatic java/lang/Math.atan2 (DD)D
      // 10: invokestatic java/lang/Math.toDegrees (D)D
      // 13: getstatic catlavan/module/combat/KillAuraModule.elytraYawOffset D
      // 16: dsub
      // 17: invokestatic sg/MathHelper.wrapDegrees (D)D
      // 1a: d2f
      // 1b: fstore 2
      // 1c: aload 1
      // 1d: getfield sg/Vec3d.y D
      // SgClass017: aload 1
      // 21: getfield sg/Vec3d.x D
      // 24: aload 1
      // 25: getfield sg/Vec3d.z D
      // 28: invokestatic java/lang/Math.hypot (DD)D
      // 2b: invokestatic java/lang/Math.atan2 (DD)D
      // 2e: dneg
      // 2f: invokestatic java/lang/Math.toDegrees (D)D
      // SgClass025: invokestatic sg/MathHelper.wrapDegrees (D)D
      // 35: d2f
      // 36: fstore 3
      // 37: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 3a: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 3d: getfield sg/ClientPlayerEntity.rotationYaw F
      // 40: fstore 4
      // 42: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 45: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 48: getfield sg/ClientPlayerEntity.rotationPitch F
      // 4b: fstore 5
      // 4d: fload 2
      // 4e: fload 4
      // 50: fsub
      // 51: invokestatic sg/MathHelper.wrapDegrees (F)F
      // SgEnum005: fstore 6
      // 56: fload 3
      // SgClass040: fload 5
      // 59: fsub
      // 5a: invokestatic sg/MathHelper.wrapDegrees (F)F
      // 5d: fstore 7
      // 5f: fload 6
      // 61: invokestatic java/lang/Math.abs (F)F
      // 64: getstatic catlavan/module/combat/KillAuraModule.elytraAngleThreshold F
      // 67: fcmpl
      // SgClass049: ifle 79
      // 6b: fload 6
      // 6d: fload 6
      // 6f: invokestatic java/lang/Math.signum (F)F
      // 72: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_MAX_YAW_DELTA F
      // 75: fmul
      // 76: fsub
      // 77: fstore 6
      // 79: fload 6
      // 7b: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_CLAMP_YAW_MIN F
      // 7e: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_CLAMP_YAW_MAX F
      // 81: invokestatic sg/MathHelper.clamp (FFF)F
      // 84: fstore SgClass057
      // 86: fload 7
      // SgClass059: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_CLAMP_PITCH_MIN F
      // 8b: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_CLAMP_PITCH_MAX F
      // 8e: invokestatic sg/MathHelper.clamp (FFF)F
      // 91: fstore 9
      // 93: fload 4
      // 95: fload SgClass057
      // 97: fadd
      // 98: fstore 10
      // 9a: fload 5
      // 9c: fload 9
      // 9e: fadd
      // 9f: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_PITCH_MIN F
      // a2: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_PITCH_MAX F
      // a5: invokestatic sg/MathHelper.clamp (FFF)F
      // a8: fstore 11
      // aa: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // ad: getfield sg/MinecraftClient.timer Lsg/УЫ;
      // b0: getfield sg/УЫ.timerSpeed F
      // b3: fstore 12
      // b5: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_BASE_ROTATE_SPEED F
      // b8: fstore 13
      // ba: getstatic catlavan/module/combat/AimAssistUtil.useElytraAim Z
      // bd: ifeq c5
      // c0: getstatic catlavan/module/combat/KillAuraModule.ELYTRA_TPS_ROTATE_SPEED F
      // c3: fstore 13
      // c5: fload 13
      // c7: fload 12
      // c9: fmul
      // ca: fstore 13
      // cc: new catlavan/util/Rotation
      // cf: dup
      // d0: fload 10
      // d2: fload 11
      // d4: invokespecial catlavan/util/Rotation.<init> (FF)V
      // d7: fload 13
      // d9: fload 13
      // db: bipush 1
      // dc: sipush 1000
      // df: invokestatic catlavan/util/RotationHelper.aimAtSimple (Lcatlavan/util/Rotation;FFII)V
      // e2: return
   }

   private boolean shouldAttack() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.StackOverflowError
      //   at java.base/java.util.concurrent.ConcurrentHashMap.computeIfAbsent(ConcurrentHashMap.java:1737)
      //   at org.jetbrains.java.decompiler.struct.StructContext.getClass(StructContext.java:78)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:318)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //
      // Bytecode:
      // 000: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 003: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 006: ifnull 012
      // 009: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 00c: getfield sg/MinecraftClient.world Lsg/ClientWorld;
      // 00f: ifnonnull 014
      // 012: bipush 0
      // 013: ireturn
      // 014: aload 0
      // 015: getfield catlavan/module/combat/KillAuraModule.targetsGroup Lcatlavan/config/MultiBoxSetting;
      // 018: bipush 0
      // 019: invokevirtual catlavan/config/MultiBoxSetting.isCheckedByIndex (I)Z
      // 01c: istore 1
      // 01d: iload 1
      // 01e: ifeq 028
      // 021: aload 0
      // 022: invokevirtual catlavan/module/combat/KillAuraModule.isInWeb ()Z
      // 025: ifne 02c
      // 028: bipush 1
      // 029: goto 02d
      // 02c: bipush 0
      // 02d: istore 2
      // 02e: aload 0
      // 02f: getfield catlavan/module/combat/KillAuraModule.onlyCritsOption Lcatlavan/config/BooleanSetting;
      // 032: invokevirtual catlavan/config/BooleanSetting.getValue ()Z
      // 035: ifeq 057
      // 038: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 03b: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 03e: getfield sg/ClientPlayerEntity.movementInput Lsg/сй;
      // 041: getfield sg/сй.jump Z
      // 044: ifne 057
      // 047: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 04a: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 04d: invokevirtual sg/ClientPlayerEntity.isOnGround ()Z
      // 050: ifeq 057
      // 053: bipush 1
      // 054: goto 058
      // 057: bipush 0
      // 058: istore 3
      // 059: getstatic sg/SgClass166.lastUpdatedSprint Z
      // 05c: ifeq 06f
      // 05f: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 062: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 065: invokevirtual sg/ClientPlayerEntity.isSprinting ()Z
      // 068: ifeq 06f
      // 06b: bipush 1
      // 06c: goto 070
      // 06f: bipush 0
      // 070: istore 4
      // 072: aload 0
      // 073: getfield catlavan/module/combat/KillAuraModule.tpsSyncOption Lcatlavan/config/BooleanSetting;
      // 076: invokevirtual catlavan/config/BooleanSetting.getValue ()Z
      // 079: ifeq 083
      // 07c: invokestatic catlavan/util/CpsTracker.getTargetCps ()F
      // 07f: f2d
      // 080: goto 086
      // 083: getstatic catlavan/module/combat/KillAuraModule.CRIT_FALL_CHECK_OFFSET_Y D
      // 086: dstore 5
      // 088: getstatic catlavan/module/combat/KillAuraModule.CRIT_MIN_COOLDOWN F
      // 08b: fstore 7
      // 08d: iload 4
      // 08f: ifeq 098
      // 092: getstatic catlavan/module/combat/KillAuraModule.CRIT_SPRINT_COOLDOWN F
      // 095: goto 09c
      // 098: aload 0
      // 099: getfield catlavan/module/combat/KillAuraModule.critRandomFactor F
      // 09c: fstore SgClass057
      // 09e: aload 0
      // 09f: fload 7
      // 0a1: dload 5
      // 0a3: invokevirtual catlavan/module/combat/KillAuraModule.getAttackCooldown (FD)F
      // 0a6: fload SgClass057
      // 0a8: fcmpg
      // 0a9: ifge 0ae
      // 0ac: bipush 0
      // 0ad: ireturn
      // 0ae: iload 3
      // 0af: ifne 0d0
      // 0b2: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0b5: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0b8: getfield sg/ClientPlayerEntity.prevOnGround Z
      // 0bb: ifne 0ca
      // 0be: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0c1: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0c4: invokevirtual sg/ClientPlayerEntity.isOnGround ()Z
      // 0c7: ifeq 0d0
      // 0ca: iload 1
      // 0cb: ifeq 0d0
      // 0ce: bipush 0
      // 0cf: ireturn
      // 0d0: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0d3: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0d6: getfield sg/ClientPlayerEntity.fallDistance F
      // 0d9: f2d
      // 0da: dstore 9
      // 0dc: dload 9
      // 0de: dconst_0
      // 0df: dcmpl
      // 0e0: ifle 0e7
      // 0e3: bipush 1
      // 0e4: goto 0e8
      // 0e7: bipush 0
      // 0e8: istore 11
      // 0ea: iload 1
      // 0eb: ifeq 145
      // 0ee: iload 3
      // 0ef: ifne 145
      // 0f2: iload 4
      // 0f4: ifeq 145
      // 0f7: iload 11
      // 0f9: ifne 13e
      // 0fc: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0ff: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 102: invokevirtual sg/ClientPlayerEntity.isOnGround ()Z
      // 105: ifeq 12b
      // 108: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 10b: getfield sg/MinecraftClient.world Lsg/ClientWorld;
      // 10e: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 111: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 114: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 117: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 11a: invokevirtual sg/ClientPlayerEntity.getBoundingBox ()Lsg/Box;
      // 11d: dconst_0
      // 11e: getstatic catlavan/module/combat/KillAuraModule.CRIT_Y_VELOCITY_THRESHOLD D
      // 121: dconst_0
      // 122: invokevirtual sg/Box.offset (DDD)Lsg/Box;
      // 125: invokevirtual sg/ClientWorld.hasNoCollisions (Lsg/Entity;Lsg/Box;)Z
      // 128: ifne 13e
      // 12b: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 12e: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 131: invokevirtual sg/ClientPlayerEntity.getMotion ()Lsg/Vec3d;
      // 134: getfield sg/Vec3d.y D
      // 137: getstatic catlavan/module/combat/KillAuraModule.CRIT_Y_FALLING_THRESHOLD D
      // 13a: dcmpg
      // 13b: ifge 142
      // 13e: bipush 1
      // 13f: goto 143
      // 142: bipush 0
      // 143: istore 11
      // 145: iload 2
      // 146: ifne 152
      // 149: iload 3
      // 14a: ifne 152
      // 14d: iload 11
      // 14f: ifeq 156
      // 152: bipush 1
      // 153: goto 157
      // 156: bipush 0
      // 157: ireturn
   }

   private Vec3d method3926(Vec3d vec3d) {
      Box Box = target.getBoundingBox().grow(target.getCollisionBorderSize());
      if (Box.contains(дыx)) {
         return дыx;
      } else {
         double d0 = Math.max(this.attackRangeSlider.get().doubleValue() + RAYCAST_EXTRA_REACH, mc.player.getDistanceEyePos(target) + RAYCAST_FALLBACK_OFFSET);
         Vec3d дыx = дыx.add(mc.player.getLook(1.0F).scale(d0));
         return Box.rayTrace(дыx, дыx).orElseGet(() -> clampVecToAABBEntity(дыx, target));
      }
   }

   public static Vec3d clampVecToAABBEntity(Vec3d vec3d, Entity entity) {
      return clampVecToAABBBounds(vec3d, entity.getBoundingBox());
   }

   @Override
   protected void onEnable() {
      super.onEnable();
      if (this.bypassMode.is(STR_FUNTIME_MODE)) {
         AimAssistRotationUtil.reset();
      }

      if (this.bypassMode.is(STR_HOLYWORLD_MODE)) {
         this.updateElytraRotation();
      }
   }

   private double getArmorProtection(ItemStack itemStack) {
      Item Item = itemStack.getItem();
      if (Item instanceof SgClass090) {
         SgClass090 _ь = (SgClass090)Item;
         double d0 = _ь.getDamageReduceAmount();
         if (itemStack.isEnchanted()) {
            double d1 = d0 + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack) * PROTECTION_ENCHANT_WEIGHT;
            double d2 = d1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, itemStack) * BLAST_PROTECTION_WEIGHT;
            double d3 = d2 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, itemStack) * FIRE_PROTECTION_WEIGHT;
            d0 = d3 + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROJECTILE_PROTECTION, itemStack) * PROJ_PROTECTION_WEIGHT;
         }

         return d0;
      } else {
         return 0.0;
      }
   }

   public static LivingEntity getCurrentTarget() {
      return target;
   }

   private double getEntityTotalArmorWeight(LivingEntity LivingEntity) {
      if (LivingEntity instanceof PlayerEntity) {
         PlayerEntity м3 = (PlayerEntity)LivingEntity;
         double d0 = 0.0;

         for (int i = 0; i < 4; i++) {
            ItemStack itemStack = (ItemStack)м3.inventory.armorInventory.get(i);
            if (itemStack.getItem() instanceof SgClass090) {
               d0 += this.getArmorProtection(itemStack);
            }
         }

         return d0;
      } else {
         return LivingEntity.getTotalArmorValue();
      }
   }

   public float getAttackCooldown(float f, double d0) {
      return mc.player != null && mc.world != null
         ? MathHelper.lerp((mc.player.ticksSinceLastSwing + f) / this.getAttackSpeedFactor(ATTACK_SPEED_TICKS - d0), 0.0F, 1.0F)
         : 0.0F;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.attackTimer = System.currentTimeMillis();
      this.handleShieldBreaker();
      target = null;
      if (this.bypassMode.is(STR_HOLYWORLD_BYPASS)) {
         this.isDecelerating = false;
         this.smoothRotationProgress = 0.0F;
         this.smoothRotationYaw = 0.0F;
         this.smoothRotationPitch = 0.0F;
      }
   }

   private void setRotateVec(Vec3d vec3d) {
      this.field_Lsg2 = vec3d;
   }

   public static boolean isEntityInRayCast(float f, float f1, double d0, Entity entity) {
      if (mc.player != null && mc.world != null && entity != null) {
         Vec3d дыxx = mc.player.getEyePosition(1.0F);
         Vec3d дыx = mc.player.getVectorForRotation(f1, f);
         Vec3d дыxx = дыxx.add(дыx.scale(d0));
         Box Box = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
         return Box.contains(дыxx) ? true : Box.rayTrace(дыxx, дыxx).filter(vec3d -> дыxx.squareDistanceTo(дыxxxx) <= d0 * d0).isPresent();
      } else {
         return false;
      }
   }

   private double method8991(LivingEntity LivingEntity) {
      return mc.player != null && mc.world != null ? LivingEntity.getDistanceEyePos(mc.player) : 0.0;
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof PlayerMovePacket) {
         PlayerMovePacket playermovepacket = (PlayerMovePacket)event;
         if (target != null && this.sprintResetFlag) {
            playermovepacket.setYaw(0.0F);
            playermovepacket.setPitch(0.0F);
            this.sprintResetFlag = false;
         }
      }

      if (event instanceof SgClass040 && target != null && BiConsumerImpl.й.module105.enabled) {
         this.setRotateVec(AimAssistUtil.computeAimDirection(mc.player, target, this.attackRangeSlider.get().floatValue()));
         PredictModule predictmodule = BiConsumerImpl.й.module19;
         if (predictmodule.isEnabled() && mc.player.isElytraFlying() && target.isElytraFlying()) {
            this.stopBlockBreaker();
         } else {
            this.selectTarget();
         }
      }

      if (event instanceof SgInterface023 && target != null && !BiConsumerImpl.й.module105.enabled) {
         this.setRotateVec(AimAssistUtil.computeAimDirection(mc.player, target, this.attackRangeSlider.get().floatValue()));
         PredictModule predictmodule1 = BiConsumerImpl.й.module19;
         if (predictmodule1.isEnabled() && mc.player.isElytraFlying() && target.isElytraFlying()) {
            this.stopBlockBreaker();
         } else {
            this.selectTarget();
         }
      }

      if (event instanceof ClientTickEvent) {
         if (mc.player == null || mc.world == null) {
            return;
         }

         if (target == null || !this.isValidTarget(target)) {
            this.updateTarget();
         }

         if (this.hitInWallsBypassOption.getValue()) {
            this.clearBlockBreaker();
         } else {
            this.handleShieldBreaker();
         }

         if (target != null) {
            AimAssistUtil.updateMovementState(target);
            AimAssistUtil.updateTargetOffset(target);
            if (!BiConsumerImpl.й.module4.enabled
               || !AntiCheatBypassModule.modeSetting.is(STR_REALLY_WORLD_MODE1)
               || !mc.player.isPotionActive(Effects.SLOW_FALLING)) {
               this.attackTick();
            }
         }
      }

      if (event instanceof SgClass224
         && target != null
         && BiConsumerImpl.й.module4.enabled
         && AntiCheatBypassModule.modeSetting.is(STR_REALLY_WORLD_MODE2)
         && mc.player.isPotionActive(Effects.SLOW_FALLING)
         && mc.player.fallDistance > 0.0F
         && mc.player.fallDistance < 1.0F) {
         this.attackTick();
      }
   }

   private boolean isValidTarget(LivingEntity LivingEntity) {
      if (LivingEntity instanceof ClientPlayerEntity) {
         return false;
      } else if (this.method8991(LivingEntity)
         >= this.attackRangeSlider.get().floatValue()
            + (
               this.rotateDistSlider.get().floatValue()
                  + (mc.player.isElytraFlying() && mc.player.isElytraFlying() ? this.elytraRotateDistSlider.get().floatValue() : 0.0F)
            )) {
         return false;
      } else {
         if (LivingEntity instanceof PlayerEntity) {
            PlayerEntity м3 = (PlayerEntity)LivingEntity;
            if (BotDetectorModule.isBot(LivingEntity)) {
               return false;
            }

            if (!this.settingsGroup.isCheckedByNameOnly(STR_FRIENDS_FILTER) && BiConsumerImpl.й.contains(м3.getName().getString())) {
               return false;
            }

            if (м3.getName().getString().equalsIgnoreCase(mc.player.getName().getString())) {
               return false;
            }
         }

         if (LivingEntity instanceof PlayerEntity && !this.settingsGroup.isCheckedByNameOnly(STR_PLAYERS_FILTER)) {
            return false;
         } else if (LivingEntity instanceof PlayerEntity && LivingEntity.getTotalArmorValue() == 0 && !this.settingsGroup.isCheckedByNameOnly(STR_NAKEDS_FILTER)) {
            return false;
         } else {
            return LivingEntity instanceof SgClass003 && !this.settingsGroup.isCheckedByNameOnly(STR_MOBS_FILTER)
               ? false
               : !LivingEntity.isInvulnerable() && LivingEntity.isAlive() && !(LivingEntity instanceof SgClass617);
         }
      }
   }

   public boolean isInWeb() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.StackOverflowError
      //   at java.base/java.util.concurrent.ConcurrentHashMap.computeIfAbsent(ConcurrentHashMap.java:1737)
      //   at org.jetbrains.java.decompiler.struct.StructContext.getClass(StructContext.java:78)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:318)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //   at org.jetbrains.java.decompiler.struct.StructClass.getAllGenerics(StructClass.java:320)
      //
      // Bytecode:
      // 000: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 003: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 006: ifnull 012
      // 009: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 00c: getfield sg/MinecraftClient.world Lsg/ClientWorld;
      // 00f: ifnonnull 014
      // 012: bipush 0
      // 013: ireturn
      // 014: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 017: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 01a: invokevirtual sg/ClientPlayerEntity.getBoundingBox ()Lsg/Box;
      // 01d: astore 1
      // 01e: bipush 0
      // 01f: istore 2
      // 020: aload 1
      // 021: getfield sg/Box.minX D
      // 024: invokestatic sg/MathHelper.floor (D)I
      // 027: aload 1
      // 028: getfield sg/Box.minY D
      // 02b: invokestatic sg/MathHelper.floor (D)I
      // 02e: aload 1
      // 02f: getfield sg/Box.minZ D
      // 032: invokestatic sg/MathHelper.floor (D)I
      // 035: aload 1
      // 036: getfield sg/Box.maxX D
      // 039: invokestatic sg/MathHelper.floor (D)I
      // 03c: aload 1
      // 03d: getfield sg/Box.maxY D
      // 040: invokestatic sg/MathHelper.floor (D)I
      // 043: aload 1
      // 044: getfield sg/Box.maxZ D
      // 047: invokestatic sg/MathHelper.floor (D)I
      // 04a: invokestatic sg/BlockPos.getAllInBoxMutable (IIIIII)Ljava/lang/Iterable;
      // 04d: invokeinterface java/lang/Iterable.iterator ()Ljava/util/Iterator; 1
      // 052: astore 3
      // 053: aload 3
      // 054: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 059: ifeq 086
      // 05c: aload 3
      // 05d: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 062: checkcast sg/BlockPos
      // 065: astore 4
      // 067: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 06a: getfield sg/MinecraftClient.world Lsg/ClientWorld;
      // 06d: aload 4
      // 06f: invokevirtual sg/ClientWorld.getBlockState (Lsg/BlockPos;)Lsg/BlockState;
      // 072: invokevirtual sg/BlockState.getBlock ()Lsg/SgEnum012;
      // 075: getstatic sg/Blocks.COBWEB Lsg/SgEnum012;
      // 078: invokevirtual java/lang/Object.equals (Ljava/lang/Object;)Z
      // 07b: ifeq 083
      // 07e: bipush 1
      // 07f: istore 2
      // 080: goto 086
      // 083: goto 053
      // 086: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 089: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 08c: invokevirtual sg/ClientPlayerEntity.isInLava ()Z
      // 08f: ifne 0db
      // 092: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 095: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 098: invokevirtual sg/ClientPlayerEntity.isRidingHorse ()Z
      // 09b: ifne 0db
      // 09e: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0a1: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0a4: invokevirtual sg/ClientPlayerEntity.isOnLadder ()Z
      // 0a7: ifne 0db
      // 0aa: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0ad: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0b0: getstatic sg/Effects.BLINDNESS Lsg/Effect;
      // 0b3: invokevirtual sg/ClientPlayerEntity.isPotionActive (Lsg/Effect;)Z
      // 0b6: ifne 0db
      // 0b9: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0bc: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0bf: getstatic sg/Effects.SLOW_FALLING Lsg/Effect;
      // 0c2: invokevirtual sg/ClientPlayerEntity.isPotionActive (Lsg/Effect;)Z
      // 0c5: ifne 0db
      // 0c8: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0cb: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0ce: getfield sg/ClientPlayerEntity.abilities Lsg/Юа;
      // 0d1: getfield sg/Юа.isFlying Z
      // 0d4: ifne 0db
      // 0d7: iload 2
      // 0d8: ifeq 0dd
      // 0db: bipush 0
      // 0dc: ireturn
      // 0dd: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0e0: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0e3: invokevirtual sg/ClientPlayerEntity.isOnGround ()Z
      // 0e6: ifeq 0fa
      // 0e9: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0ec: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 0ef: invokevirtual sg/ClientPlayerEntity.getPose ()Lsg/衣й;
      // 0f2: getstatic sg/衣й.FALL_FLYING Lsg/衣й;
      // 0f5: if_acmpne 0fa
      // 0f8: bipush 0
      // 0f9: ireturn
      // 0fa: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 0fd: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 100: invokevirtual sg/ClientPlayerEntity.isOnGround ()Z
      // 103: ifeq 12b
      // 106: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 109: getfield sg/MinecraftClient.world Lsg/ClientWorld;
      // 10c: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 10f: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 112: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 115: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 118: invokevirtual sg/ClientPlayerEntity.getBoundingBox ()Lsg/Box;
      // 11b: dconst_0
      // 11c: getstatic catlavan/module/combat/KillAuraModule.GROUND_CHECK_EXPAND_Y D
      // 11f: dconst_0
      // 120: invokevirtual sg/Box.expand (DDD)Lsg/Box;
      // 123: invokevirtual sg/ClientWorld.hasNoCollisions (Lsg/Entity;Lsg/Box;)Z
      // 126: ifne 12b
      // 129: bipush 0
      // 12a: ireturn
      // 12b: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 12e: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 131: invokevirtual sg/ClientPlayerEntity.isUnderWater ()Z
      // 134: ifne 153
      // 137: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 13a: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 13d: invokevirtual sg/ClientPlayerEntity.isInWater ()Z
      // 140: ifeq 14f
      // 143: getstatic catlavan/module/combat/KillAuraModule.mc Lsg/MinecraftClient;
      // 146: getfield sg/MinecraftClient.player Lsg/ClientPlayerEntity;
      // 149: invokevirtual sg/ClientPlayerEntity.isOnGround ()Z
      // 14c: ifne 153
      // 14f: bipush 1
      // 150: goto 154
      // 153: bipush 0
      // 154: ireturn
   }

   private void updateTarget() {
      if (mc.player != null && mc.world != null) {
         ArrayList arraylist = new ArrayList();

         for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof LivingEntity) {
               LivingEntity LivingEntity = (LivingEntity)entity;
               if (this.isValidTarget(LivingEntity)) {
                  arraylist.add(LivingEntity);
               }
            }
         }

         if (arraylist.isEmpty()) {
            target = null;
         } else if (arraylist.size() == 1) {
            target = (LivingEntity)arraylist.get(0);
         } else {
            arraylist.sort(Comparator.<Object>comparingDouble(object -> {
               if (object instanceof PlayerEntity) {
                  PlayerEntity м3 = (PlayerEntity)object;
                  return -this.getEntityTotalArmorWeight(м3);
               } else if (object instanceof LivingEntity) {
                  LivingEntity скx = (LivingEntity)object;
                  return -скx.getTotalArmorValue();
               } else {
                  return 0.0;
               }
            }).thenComparing((object, object1) -> {
               double d0 = this.getEntityPriority((LivingEntity)object);
               double d1 = this.getEntityPriority((LivingEntity)object1);
               return Double.compare(d0, d1);
            }).thenComparing((object, object1) -> {
               double d0 = mc.player.getDistanceEyePos((LivingEntity)object);
               double d1 = mc.player.getDistanceEyePos((LivingEntity)object1);
               return Double.compare(d0, d1);
            }));
            target = (LivingEntity)arraylist.get(0);
         }
      }
   }

   private void handleShieldBreaker() {
      if (mc.player != null && mc.player.connection != null && this.field_Lsg != null && this.blockFace != null) {
         mc.player.connection.sendPacket(new SgClass361(SgClass584.STOP_DESTROY_BLOCK, this.field_Lsg, this.blockFace));
      }

      this.field_Lsg = null;
      this.blockFace = null;
      this.lastBreakTick = TICK_RESET_VALUE;
   }

   private void selectTarget() {
      String s = this.bypassMode.getValue();
      switch (s) {
         case STR_REALLY_WORLD_CHECK:
            AimRotationUtil.aimAtEntity(target);
            break;
         case STR_SNAP_MODE:
            this.snapRotateTo(method3853(target));
            break;
         case STR_FUNTIME_ROTATION:
            AimAssistRotationUtil.updateRotation(target);
            break;
         case STR_SPOOKY_MODE:
            AimUtil.aimAt(target, new SgClass596(mc.player.rotationYaw, mc.player.rotationPitch), false);
            break;
         case STR_HOLYWORLD_ROTATION:
            this.initSmoothRotation();
            break;
         case STR_TRIGGER_BOT:
            Rotation rotation = new Rotation(FreecamController.getYaw(), FreecamController.getPitch());
            RotationHelper.aimAtSimple(rotation, SMOOTH_MAX_YAW_RANGE, SMOOTH_MAX_PITCH_RANGE, 1, 6);
      }
   }

   private void updateSmoothRotation() {
      if (target != null) {
         LivingEntity LivingEntity = target;
         if (LivingEntity instanceof PlayerEntity) {
            PlayerEntity м3 = (PlayerEntity)LivingEntity;
            if (!м3.isBlocking()) {
               return;
            }

            int j = InventoryUtil.findSwordSlot(true);
            if (j == -1) {
               if (this.onlyHotbarOption.getValue()) {
                  return;
               }

               j = InventoryUtil.findSwordSlot(false);
               if (j == -1) {
                  return;
               }
            }

            int i = mc.player.inventory.currentItem;
            if (j > SgClass057) {
               if (this.onlyHotbarOption.getValue()) {
                  return;
               }

               return;
            }

            mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(j));
            mc.playerController.attackEntity(mc.player, м3);
            mc.player.swingArm(Hand.MAIN_HAND);
            mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(i));
            return;
         }
      }
   }

   private void startBlockDig() {
      if (this.field_Lsg != null && this.blockFace != null && mc.player != null) {
         if (this.lastBreakTick != mc.player.ticksExisted) {
            this.handleShieldBreaker();
         }
      }
   }

   private BlockRayTraceResult getRaycastHitResult() {
      if (target == null) {
         return null;
      } else {
         Vec3d дыx = mc.player.getEyePosition(1.0F);
         Vec3d дыx = this.method3926(дыx);
         if (дыx != null && !(дыx.squareDistanceTo(дыx) < RAYCAST_MIN_DIST_SQ)) {
            BlockRayTraceResult BlockRayTraceResult = mc.world.rayTraceBlocks(new SgClass402(дыx, дыx, SgClass241.OUTLINE, SgClass179.NONE, mc.player));
            return BlockRayTraceResult.getType() == RayTraceResultType.BLOCK ? BlockRayTraceResult : null;
         } else {
            return null;
         }
      }
   }

   private void updateElytraRotation() {
      this.isDecelerating = false;
      this.smoothRotationProgress = 0.0F;
      if (mc.player != null) {
         this.smoothRotationYaw = mc.player.rotationYaw;
         this.smoothRotationPitch = MathHelper.clamp(mc.player.rotationPitch, SMOOTH_INIT_PITCH_MIN, SMOOTH_INIT_PITCH_MAX);
      } else {
         this.smoothRotationYaw = 0.0F;
         this.smoothRotationPitch = 0.0F;
      }
   }

   private void clearBlockBreaker() {
      if (mc.player != null && mc.world != null && mc.player.connection != null && target != null) {
         BlockRayTraceResult BlockRayTraceResult = this.getRaycastHitResult();
         if (BlockRayTraceResult != null && BlockRayTraceResult.getType() == RayTraceResultType.BLOCK) {
            BlockPos blockPos = BlockRayTraceResult.getPos().toImmutable();
            Direction Direction = BlockRayTraceResult.getFace();
            if (!blockPos.equals(this.field_Lsg) || Direction != this.blockFace) {
               this.handleShieldBreaker();
               mc.player.connection.sendPacket(new SgClass361(SgClass584.START_DESTROY_BLOCK, blockPos, Direction));
               this.field_Lsg = blockPos;
               this.blockFace = Direction;
               this.lastBreakTick = mc.player.ticksExisted;
            }
         } else {
            this.handleShieldBreaker();
         }
      } else {
         this.handleShieldBreaker();
      }
   }

   public float getAttackSpeedFactor(double d0) {
      return mc.player != null && mc.world != null ? (float)(1.0 / mc.player.getAttributeValue(SgClass343.ATTACK_SPEED) * d0) : 0.0F;
   }

   public static Vec3d clampVecToAABBBounds(Vec3d vec3d, Box Box) {
      return new Vec3d(MathHelper.clamp(vec3d.x, Box.minX, Box.maxX), MathHelper.clamp(vec3d.y, Box.minY, Box.maxY), MathHelper.clamp(vec3d.z, Box.minZ, Box.maxZ));
   }

   public static Vec3d method3853(Entity entity) {
      Vec3d vec3d = mc.player.getEyePosition(1.0F);
      return clampVecToAABBEntity(vec3d, entity).subtract(vec3d);
   }

   static {
      КЛ = 0.0F;
      тю = 0.0F;
      тй = 0.0F;
      SgClass496 = 0.0F;
      тя = 0.0F;
   }
}
