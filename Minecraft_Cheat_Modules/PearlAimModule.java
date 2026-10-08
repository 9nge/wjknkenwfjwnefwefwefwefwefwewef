package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PlayerMovePacket;
import catlavan.util.BiConsumerImpl;
import catlavan.util.InventoryUtil;
import catlavan.util.McContextHolder;
import catlavan.util.MovementUtil;
import catlavan.util.SoundUtil;
import catlavan.util.Timer;
import java.util.List;
import sg.Vec3d;
import sg.ClickType;
import sg.SgEnum014;
import sg.MathHelper;
import sg.Entity;
import sg.ClientTickEvent;
import sg.ItemStack;
import sg.SgClass451;
import sg.Items;
import sg.LivingEntity;
import sg.Hand;
import sg.Item;
import sg.BlockPos;
import sg.HeldItemChangeC2SPacket;
import sg.PlayerTryUseItemC2SPacket;
import sg.SgClass583;
import sg.SgClass596;
import sg.MinecraftClient;

public class PearlAimModule extends Module implements McContextHolder {
   long field_J;
   static long targetUpdateInterval = 70L;
   Vec3d targetPos;
   SgClass583 currentTarget;
   static MinecraftClient mc;
   boolean isAiming;
   SgClass596 aimAngles;
   long field_J2;
   Timer rotationManager;
   Entity targetEntity;
   long targetLockTime;
   float yawOffset;
   double heightThreshold;
   float pitchHighUp;
   float pitchHighNormal;
   double distanceFarThreshold;
   float pitchFarMin;
   float pitchNearMin;
   float pitchUpMin;
   double distanceFarThreshold2;
   float stepFar;
   float stepNear;
   double distanceFarThreshold3;
   float yawRangeFar;
   float yawRangeNear;
   float pitchUpHigh;
   double distThresholdSweep;
   float yawStepCoarseUp;
   float yawStepCoarseNormal;
   double distThresholdCoarse;
   double maxDistCoarse;
   double maxDistNear;
   double maxDistUp;
   float clampMinA;
   float clampMaxA;
   float clampMinB;
   float clampMaxB;
   float pitchSweepStart;
   float pitchSweepEnd;
   double maxDistMultiplier;
   float clampMinC;
   float clampMaxC;
   float yawStepFinal;
   float pitchFinalMin;
   double maxDistFinalUp;
   double maxDistFinalNormal;
   float clampMinD;
   float clampMaxD;
   static double simDragFactor = 130.0;
   static double simGravity = Double.MAX_VALUE;
   FloatSliderSetting minDistanceSetting;
   BooleanSetting onlyWithTargetSetting;
   long throwCooldown;
   double maxThrowDistance;
   double closeLandDistThreshold;
   double landDistNear;
   double landDistFar;
   double pearlVelocity;
   static String MODE_RU = "Только за таргетом";
   static String MODE_ONLY_WITH_TARGET = "Only with target";
   static String LABEL_MIN_DIST = "Мин дистанция";
   static float minDistDefault = 10.0F;
   static float minDistMin = SgClass057.0F;
   static float minDistMax = SgClass025.0F;
   long lastThrowTime;
   float yawOffset2;
   double heightThreshold2;
   float pitchStartUp;
   float pitchStartNormal;
   double distThreshold2Far;
   float pitchMinFar2;
   float pitchMinNear2;
   float pitchMinUp2;
   double distThreshold2Far2;
   float yawStep2;
   float yawStepNormal2;
   float yawStepUp2;
   int bestTicksMin;
   double bestDistMin;
   double maxDistUp2;
   double maxDistNormal2;
   int bestTicksThreshold;
   float pitchClampMin;
   float pitchClampMax;
   double simDragFactor2;
   double simGravity2;
   double simDragFactor3;
   double simStep;
   double simDragFactor4;
   double simGravity3;
   static long throwCooldownMs = 2000L;
   double heightThresholdBlock;
   double distFarThresholdBlock;
   double maxDistClose;
   double maxDistCloseFar;
   double maxDistCloseUp;
   double maxDistBlockedFar;
   double maxDistBlockedClose;
   String FUNTIME_MODE;
   String SPOOKY_MODE;
   double pearlDrag;
   double pearlGravity;
   double simDrag2;
   double simGrav2;

   private void updateTargetPos() {
      long i = System.currentTimeMillis();
      if (i - this.field_J >= targetUpdateInterval) {
         this.field_J = i;
         this.targetPos = null;
         this.currentTarget = this.method6592();
         if (this.currentTarget != null && this.currentTarget.isAlive()) {
            this.targetPos = this.method600(this.currentTarget);
            if (this.targetPos != null) {
               BlockPos blockPos = new BlockPos(this.targetPos);
               if (mc.world.getBlockState(blockPos.down()).getCollisionShape(mc.world, blockPos.down()).isEmpty()
                  && mc.world.getBlockState(blockPos.down(2)).getCollisionShape(mc.world, blockPos.down(2)).isEmpty()) {
                  this.targetPos = null;
               }
            }
         }
      }
   }

   @Override
   public void onDisable() {
      this.isAiming = false;
      this.currentTarget = null;
      this.targetPos = null;
      this.aimAngles = null;
      this.field_J2 = 0L;
      this.rotationManager.reset();
      this.targetEntity = null;
      this.targetLockTime = 0L;
      super.onDisable();
   }

   private float[] calcAimAnglesCoarse(Vec3d vec3d) {
      Vec3d дыx = mc.player.getEyePosition(1.0F);
      double d0 = дыxxx.x - дыx.x;
      double d1 = дыxxx.z - дыx.z;
      float f = (float)Math.toDegrees(Math.atan2(d1, d0)) - yawOffset;
      double d2 = дыx.distanceTo(дыxxx);
      double d3 = дыxxx.y - дыx.y;
      boolean flag = d3 > heightThreshold;
      float f1 = flag ? pitchHighUp : pitchHighNormal;
      float f2 = d2 > distanceFarThreshold ? pitchFarMin : pitchNearMin;
      if (flag) {
         f2 = pitchUpMin;
      }

      float f3 = d2 > distanceFarThreshold2 ? stepFar : stepNear;
      if (flag) {
         f3 = 1.0F;
      }

      float f4 = d2 > distanceFarThreshold3 ? yawRangeFar : yawRangeNear;
      if (flag) {
         f4 = pitchUpHigh;
      }

      float f5 = d2 > distThresholdSweep ? yawStepCoarseUp : yawStepCoarseNormal;
      if (flag) {
         f5 = 2.0F;
      }

      double d4 = d2 > distThresholdCoarse ? maxDistCoarse : maxDistNear;
      if (flag) {
         d4 = maxDistUp;
      }

      for (float f6 = f1; f6 >= f2; f6 -= f3) {
         Vec3d дыxx = this.method805(f, f6);
         if (дыxx != null) {
            double d5 = дыxxx.distanceTo(дыxx);
            if (d5 <= d4) {
               return new float[]{f, MathHelper.clamp(f6, clampMinA, clampMaxA)};
            }
         }
      }

      for (float f7 = -f4; f7 <= f4; f7 += f5) {
         if (f7 != 0.0F) {
            float f10 = f + f7;

            for (float f13 = f1; f13 >= f2; f13 -= f3) {
               Vec3d дыxx = this.method805(f10, f13);
               if (дыxx != null) {
                  double d6 = дыxxx.distanceTo(дыxx);
                  if (d6 <= d4) {
                     return new float[]{f10, MathHelper.clamp(f13, clampMinB, clampMaxB)};
                  }
               }
            }
         }
      }

      if (flag) {
         for (float f8 = pitchSweepStart; f8 >= pitchSweepEnd; f8 -= yawStepFinal) {
            for (float f11 = -f4; f11 <= f4; f11 += f5) {
               float f14 = f + f11;
               Vec3d дыxx = this.method805(f14, f8);
               if (дыxx != null) {
                  double d8 = дыxxx.distanceTo(дыxx);
                  if (d8 <= d4 * maxDistMultiplier) {
                     return new float[]{f14, MathHelper.clamp(f8, clampMinC, clampMaxC)};
                  }
               }
            }
         }
      }

      for (float f9 = f1; f9 >= pitchFinalMin; f9 -= 2.0F) {
         for (float f12 = -f4; f12 <= f4; f12 += f5 * 2.0F) {
            float f15 = f + f12;
            Vec3d дыxx = this.method805(f15, f9);
            if (дыxx != null) {
               double d9 = дыxxx.distanceTo(дыxx);
               double d7 = flag ? maxDistFinalUp : maxDistFinalNormal;
               if (d9 <= d7) {
                  return new float[]{f15, MathHelper.clamp(f9, clampMinD, clampMaxD)};
               }
            }
         }
      }

      return null;
   }

   private SgClass583 method6592() {
      List list = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getBoundingBox().grow(simDragFactor));
      SgClass583 弟诶x = null;
      double d0 = simGravity;
      double d1 = this.minDistanceSetting.get().doubleValue();
      Object object = null;
      if (this.onlyWithTargetSetting.getValue()) {
         KillAuraModule killauramodule = BiConsumerImpl.й.module0;
         LivingEntity LivingEntity = killauramodule != null ? KillAuraModule.getCurrentTarget() : null;
         long i = System.currentTimeMillis();
         if (LivingEntity != null && LivingEntity.isAlive()) {
            this.targetEntity = LivingEntity;
            this.targetLockTime = i;
            object = LivingEntity;
         } else if (this.targetEntity != null) {
            if (this.targetEntity.isAlive() && !this.targetEntity.removed) {
               long j = i - this.targetLockTime;
               if (j < throwCooldown) {
                  object = this.targetEntity;
               } else {
                  this.targetEntity = null;
                  this.targetLockTime = 0L;
               }
            } else {
               this.targetEntity = null;
               this.targetLockTime = 0L;
            }
         }
      }

      for (Entity entity : list) {
         if (entity instanceof SgClass583 && entity.isAlive()) {
            SgClass583 弟诶x = (SgClass583)entity;
            if (!this.onlyWithTargetSetting.getValue() || object != null && 弟诶x.func_234616_v_() != null && 弟诶x.func_234616_v_().equals(object)) {
               Vec3d vec3d = this.method600(弟诶x);
               if (vec3d != null) {
                  double d2 = mc.player.getPositionVec().distanceTo(vec3d);
                  if (d2 >= d1 && d2 <= maxThrowDistance && d2 < d0) {
                     弟诶x = 弟诶x;
                     d0 = d2;
                  }
               }
            }
         }
      }

      return 弟诶x;
   }

   private Vec3d calcPearlStartPos(float f, float f2) {
      float f1 = (float)Math.toRadians(f);
      double d0 = mc.player.getPosX() - MathHelper.cos(f1) * closeLandDistThreshold;
      double d1 = mc.player.getPosY() + mc.player.getEyeHeight() - landDistNear;
      double d2 = mc.player.getPosZ() - MathHelper.sin(f1) * landDistFar;
      return new Vec3d(d0, d1, d2);
   }

   private Vec3d method4814(float f, float f1) {
      double d0 = pearlVelocity;
      float f2 = (float)Math.toRadians(f);
      float f3 = (float)Math.toRadians(f1);
      double d1 = -MathHelper.sin(f2) * MathHelper.cos(f3) * d0;
      double d2 = -MathHelper.sin(f3) * d0;
      double d3 = MathHelper.cos(f2) * MathHelper.cos(f3) * d0;
      double d4 = mc.player.getMotion().y;
      d2 += d4;
      return new Vec3d(d1, d2, d3);
   }

   public boolean isThrowingPearl() {
      return this.isEnabled() && this.isAiming && this.aimAngles != null;
   }

   private void method7580(Hand Hand) {
      if (mc.getConnection() != null) {
         mc.getConnection().sendPacket(new PlayerTryUseItemC2SPacket(Hand));
         mc.player.swingArm(Hand);
      }
   }

   public PearlAimModule() {
      this.onlyWithTargetSetting = new BooleanSetting(MODE_RU, false, MODE_ONLY_WITH_TARGET);
      this.minDistanceSetting = new FloatSliderSetting(LABEL_MIN_DIST, minDistDefault, minDistMin, minDistMax, 1.0F);
      this.rotationManager = new Timer();
      this.currentTarget = null;
      this.targetPos = null;
      this.field_J = 0L;
      this.lastThrowTime = 0L;
      this.field_J2 = 0L;
      this.isAiming = false;
      this.aimAngles = null;
      this.targetEntity = null;
      this.targetLockTime = 0L;
      this.addSettings(new AbstractModuleSetting[]{this.minDistanceSetting});
   }

   private int method660(Item Item, boolean flag) {
      for (int i = flag ? 0 : 9; i < (flag ? 9 : 36); i++) {
         ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
         if (!itemStack.isEmpty() && itemStack.getItem() == Item) {
            return i;
         }
      }

      return -1;
   }

   private float[] method3753(Vec3d vec3d) {
      Vec3d дыx = mc.player.getEyePosition(1.0F);
      double d0 = дыx.x - дыx.x;
      double d1 = дыx.z - дыx.z;
      float f = (float)Math.toDegrees(Math.atan2(d1, d0)) - yawOffset2;
      double d2 = дыx.distanceTo(дыx);
      double d3 = дыx.y - дыx.y;
      boolean flag = d3 > heightThreshold2;
      float f1 = flag ? pitchStartUp : pitchStartNormal;
      float f2 = d2 > distThreshold2Far ? pitchMinFar2 : pitchMinNear2;
      if (flag) {
         f2 = pitchMinUp2;
      }

      float f3 = d2 > distThreshold2Far2 ? yawStep2 : yawStepNormal2;
      if (flag) {
         f3 = yawStepUp2;
      }

      float f4 = 0.0F;
      int i = bestTicksMin;
      double d4 = bestDistMin;
      double d5 = flag ? maxDistUp2 : maxDistNormal2;

      for (float f5 = f1; f5 >= f2; f5 -= f3) {
         SgEnum014 сщ = this.method5704(f, f5, дыx);
         if (сщ != null && сщ.юГ <= d5 && (сщ.КЛ < i || сщ.КЛ == i && сщ.юГ < d4)) {
            i = сщ.КЛ;
            f4 = f5;
            d4 = сщ.юГ;
         }
      }

      return i != bestTicksThreshold ? new float[]{f, MathHelper.clamp(f4, pitchClampMin, pitchClampMax)} : null;
   }

   private Vec3d method4874(Vec3d vec3d) {
      return new Vec3d(MathHelper.floor(vec3d.x) + simDragFactor2, MathHelper.floor(vec3d.y), MathHelper.floor(vec3d.z) + simGravity2);
   }

   private boolean hasObstacleBetween(Vec3d vec3d, Vec3d vec3d) {
      Vec3d дыx = дыxxx.subtract(дыxxxx);
      double d0 = дыx.length();
      Vec3d дыxx = дыx.normalize();
      int i = (int)(d0 / simDragFactor3) + 1;

      for (int j = 1; j < i; j++) {
         Vec3d дыxxx = дыxxxx.add(дыxx.scale(j * simStep));
         BlockPos blockPos = new BlockPos(дыxxx);
         if (!mc.world.getBlockState(blockPos).getCollisionShape(mc.world, blockPos).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private void method6319(float f, float f1, boolean flag) {
      if (mc.getConnection() != null) {
         mc.getConnection().sendPacket(new SgClass451(f, f1, flag));
      }
   }

   private Vec3d method805(float f, float f1) {
      Vec3d дыx = this.calcPearlStartPos(f, f1);
      Vec3d дыx = this.method4814(f, f1);

      for (int i = 0; i < 160; i++) {
         дыx = дыx.add(дыx);
         дыx = дыx.scale(simDragFactor4).subtract(0.0, simGravity3, 0.0);
         if (дыx.y <= 0.0) {
            return this.method4874(дыx);
         }

         BlockPos blockPos = new BlockPos(дыx);
         if (!mc.world.getBlockState(blockPos).getCollisionShape(mc.world, blockPos).isEmpty()) {
            return this.method4874(дыx);
         }
      }

      return null;
   }

   private void tryThrowPearl() {
      if (System.currentTimeMillis() - this.lastThrowTime >= throwCooldownMs) {
         if (this.isPearlReady()) {
            this.updateTargetPos();
            if (this.targetPos != null) {
               float[] afloat = this.method3753(this.targetPos);
               if (afloat == null) {
                  afloat = this.calcAimAnglesCoarse(this.targetPos);
                  if (afloat == null) {
                     return;
                  }
               }

               Vec3d дыx = this.method805(afloat[0], afloat[1]);
               if (дыx != null) {
                  double d0 = this.targetPos.distanceTo(дыx);
                  Vec3d дыx = mc.player.getEyePosition(1.0F);
                  double d1 = дыx.distanceTo(this.targetPos);
                  double d2 = this.targetPos.y - дыx.y;
                  boolean flag = d2 > heightThresholdBlock;
                  double d3 = d1 > distFarThresholdBlock ? maxDistClose : maxDistCloseFar;
                  if (flag) {
                     d3 = maxDistCloseUp;
                  }

                  if (this.hasObstacleBetween(дыx, this.targetPos)) {
                     d3 = flag ? maxDistBlockedFar : maxDistBlockedClose;
                  }

                  if (d0 > d3) {
                     afloat = this.calcAimAnglesCoarse(this.targetPos);
                     if (afloat == null) {
                        return;
                     }
                  }
               }

               this.isAiming = true;
               this.aimAngles = new SgClass596(afloat[0], afloat[1]);
               this.method6319(afloat[0], afloat[1], mc.player.isOnGround());
               if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL) && InventoryUtil.findItemSlotFull(Items.ENDER_PEARL) != -1) {
                  if (!SoundUtil.isOnServer(FUNTIME_MODE) && !SoundUtil.isOnServer(SPOOKY_MODE)) {
                     this.method8024(Items.ENDER_PEARL);
                  } else {
                     int j = this.method660(Items.ENDER_PEARL, true);
                     int i = this.method660(Items.ENDER_PEARL, false);
                     int k = mc.player.inventory.currentItem;
                     if (j != -1) {
                        mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(j));
                        mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.MAIN_HAND));
                        mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(k));
                     } else if (i != -1) {
                        mc.playerController.pickItem(i);
                        mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.MAIN_HAND));
                     }

                     this.method7580(Hand.MAIN_HAND);
                  }

                  this.rotationManager.reset();
                  this.lastThrowTime = System.currentTimeMillis();
               }

               this.isAiming = false;
               this.aimAngles = null;
               this.field_J2 = 0L;
            }
         }
      }
   }

   private void method8024(Item Item) {
      if (mc.player != null) {
         int i = InventoryUtil.method228(Item);
         if (i != -1) {
            int j = mc.player.inventory.currentItem;
            if (i < 9) {
               mc.player.inventory.currentItem = i;
               mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(i));
               mc.playerController.processRightClick(mc.player, mc.world, Hand.MAIN_HAND);
               mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(j));
               mc.player.inventory.currentItem = j;
            } else {
               mc.playerController.windowClick(mc.player.openContainer.windowId, i, j, ClickType.SWAP, mc.player);
               mc.playerController.processRightClick(mc.player, mc.world, Hand.MAIN_HAND);
               mc.playerController.windowClick(mc.player.openContainer.windowId, i, j, ClickType.SWAP, mc.player);
            }
         }
      }
   }

   private Vec3d method600(SgClass583 SgClass583) {
      Vec3d дыxx = SgClass583.getPositionVec();
      Vec3d дыx = SgClass583.getMotion();

      for (int i = 0; i < 160; i++) {
         Vec3d дыxx = дыxx.add(дыx);
         дыx = дыx.scale(pearlDrag).subtract(0.0, pearlGravity, 0.0);
         if (дыxx.y <= 0.0) {
            return this.method4874(дыxx);
         }

         BlockPos blockPos = new BlockPos(дыxx);
         if (!mc.world.getBlockState(blockPos).getCollisionShape(mc.world, blockPos).isEmpty()) {
            return this.method4874(дыxx);
         }

         дыxx = дыxx;
      }

      return null;
   }

   private boolean isPearlReady() {
      return !mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL);
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof PlayerMovePacket) {
         PlayerMovePacket playermovepacket = (PlayerMovePacket)event;
         if (mc.player == null) {
            return;
         }

         if (this.field_J2 > 0L && System.currentTimeMillis() >= this.field_J2) {
            this.isAiming = false;
            this.aimAngles = null;
            this.field_J2 = 0L;
         }

         if (this.isThrowingPearl()) {
            MovementUtil.setMovementInput(playermovepacket, this.aimAngles.x);
         }
      }

      if (event instanceof ClientTickEvent) {
         if (mc.player == null || mc.world == null || mc.player.isElytraFlying()) {
            return;
         }

         this.tryThrowPearl();
      }
   }

   private SgEnum014 method5704(float f, float f1, Vec3d vec3d) {
      Vec3d дыx = this.calcPearlStartPos(f, f1);
      Vec3d дыxx = this.method4814(f, f1);

      for (int i = 0; i < 160; i++) {
         дыx = дыx.add(дыxx);
         дыxx = дыxx.scale(simDrag2).subtract(0.0, simGrav2, 0.0);
         if (дыx.y <= 0.0) {
            return new SgEnum014(this.method4874(дыx).distanceTo(дыxx), i + 1);
         }

         BlockPos blockPos = new BlockPos(дыx);
         if (!mc.world.getBlockState(blockPos).getCollisionShape(mc.world, blockPos).isEmpty()) {
            return new SgEnum014(this.method4874(дыx).distanceTo(дыxx), i + 1);
         }
      }

      return null;
   }

   static {
      4 = 0L;
      юЦ = 0;
      юв = 0;
      юх = 0;
      3 = 0.0;
      衣 = 0.0;
   }
}
