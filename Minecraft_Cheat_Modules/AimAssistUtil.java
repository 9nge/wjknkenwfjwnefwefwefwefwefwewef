package catlavan.module.combat;

import catlavan.util.BiConsumerImpl;
import catlavan.util.MathUtil;
import catlavan.util.McContextHolder;
import catlavan.util.Timer;
import sg.Vec3d;
import sg.PlayerEntity;
import sg.MathHelper;
import sg.LivingEntity;
import sg.MinecraftClient;

public final class AimAssistUtil implements McContextHolder {
   static Vec3d targetOffset;
   static Timer timer;
   static long UPDATE_INTERVAL = 50L;
   static double SPEED_MULTIPLIER = SgClass017.0;
   static double SPEED_THRESHOLD = SgClass017.0;
   static int boostTicksLeft = 2;
   static boolean wasMovingFast = false;
   static boolean isMovingFast = false;
   static MinecraftClient mc;
   static double aimOffsetScale = 2.0;
   double motionYThreshold;
   static boolean useElytraAim = false;
   static double aimDistance = 3.3558476F;
   String utilClassName;

   private static Vec3d getEntityPosition(LivingEntity LivingEntity, float f) {
      if (LivingEntity instanceof PlayerEntity) {
         PlayerEntity м3 = (PlayerEntity)LivingEntity;
         if (м3.isElytraFlying() && MathUtil.getEntitySpeed(м3) == 0.0 && !LivingEntity.getPositionVec().equals(м3.getResolvedPos())) {
            return м3.getResolvedPos();
         }
      }

      return LivingEntity.getPositionVec(f);
   }

   public static void reset() {
      targetOffset = Vec3d.ZERO;
      timer = new Timer();
   }

   public static void updateMovementState(LivingEntity LivingEntity) {
      if (timer.hasElapsed(UPDATE_INTERVAL)) {
         if (LivingEntity == null) {
            timer.reset();
         } else {
            double d0 = LivingEntity.getPosX() - LivingEntity.prevPosX;
            double d1 = LivingEntity.getPosY() - LivingEntity.prevPosY;
            double d2 = LivingEntity.getPosZ() - LivingEntity.prevPosZ;
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * SPEED_MULTIPLIER;
            if (d3 > SPEED_THRESHOLD) {
               boostTicksLeft = 0;
               wasMovingFast = false;
               isMovingFast = true;
            } else {
               boostTicksLeft++;
               if (isMovingFast) {
                  wasMovingFast = true;
               }

               if (boostTicksLeft >= 3) {
                  isMovingFast = false;
                  boostTicksLeft = 0;
                  wasMovingFast = false;
               } else {
                  isMovingFast = wasMovingFast;
               }
            }

            timer.reset();
         }
      }
   }

   public static Vec3d computeAimDirection(LivingEntity LivingEntity, LivingEntity LivingEntity, float f) {
      PredictModule predictmodule = BiConsumerImpl.й.module19;
      float f1 = mc.getRenderPartialTicks();
      Vec3d дыxxxxxx = LivingEntity.getPositionVec(f1);
      Vec3d дыx = LivingEntity.getEyePosition(f1);
      Vec3d дыxx = дыxxxxxx.add(0.0, LivingEntity.getEyeHeight(), 0.0);
      Vec3d дыxxx = скx.getResolvedForward().normalize();
      Vec3d дыxxxx = targetOffset.add(дыxxx.scale(aimOffsetScale)).subtract(дыxxxxxx);
      Vec3d дыxxxxx = getEntityPosition(скx, f1);
      boolean flag = predictmodule.isEnabled() && isMovingFast && LivingEntity.isElytraFlying() && скx.isElytraFlying();
      double d0 = MathHelper.clamp(дыx.y - скx.getPosY(), 0.0, скx.getHeight() * (LivingEntity.getDistanceEyePos(скx) / f));
      Vec3d дыxxxxxx = дыxxxxx.add(0.0, d0, 0.0).subtract(дыxx);
      if (LivingEntity.isElytraFlying()) {
         if (скx.getMotion().y < motionYThreshold) {
            дыxxxxxx = скx.getPositionVec(1.0F).subtract(дыx);
         } else {
            дыxxxxxx = скx.getPositionVec(1.0F).add(0.0, скx.getHeight() / 2.0F, 0.0).subtract(дыx);
         }
      }

      useElytraAim = flag;
      return flag ? дыxxxx.normalize() : дыxxxxxx.normalize();
   }

   public static void updateTargetOffset(LivingEntity LivingEntity) {
      if (LivingEntity != null && mc.player != null) {
         float f = PredictModule.setting.get().floatValue();
         Vec3d дыx = LivingEntity.getEyePosition(1.0F);
         Vec3d дыx = LivingEntity.getResolvedForward().normalize();
         targetOffset = дыx.add(дыx.scale(f));
         updateAimDistance(targetOffset, LivingEntity);
      }
   }

   private static void updateAimDistance(Vec3d vec3d, LivingEntity LivingEntity) {
      if (mc.player != null) {
         double d0 = vec3d.distanceTo(mc.player.getEyePosition(1.0F));
         double d1 = mc.player.getDistanceEyePos(LivingEntity);
         aimDistance = useElytraAim ? d0 : d1;
      }
   }

   private AimAssistUtil() {
      throw new UnsupportedOperationException(utilClassName);
   }
}
