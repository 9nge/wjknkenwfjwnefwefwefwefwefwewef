package catlavan.module.combat;

import catlavan.util.BiConsumerImpl;
import catlavan.util.McContextHolder;
import catlavan.util.Rotation;
import catlavan.util.RotationHelper;
import java.security.SecureRandom;
import sg.Vec3d;
import sg.MathHelper;
import sg.LivingEntity;
import sg.Box;
import sg.MinecraftClient;

public final class AimAssistRotationUtil implements McContextHolder {
   static SecureRandom random;
   static Vec3d field_Lsg;
   static Vec3d originPos;
   static long lastAimUpdateTime = 1781446688707L;
   static MinecraftClient mc;
   static float yawRangeMin = 15.0F;
   static float yawRangeMax = 17.0F;
   static double oscillationPeriod = 60.0;
   static boolean invertOscillation = false;
   static float snapTicksLow = 5.0F;
   static float snapTicksHigh = 9.0F;
   static float snapTicksMax = 14.0F;
   static float snapOffsetValue = -1.0F;
   static double baseYawOffset = 90.0;
   static float currentYaw = 15.785516F;
   static float currentPitch = Float.NaN;
   static float tickModEven = 4.0F;
   float yawOffsetA;
   float yawOffsetB;
   static float yawOffsetC = 10.0F;
   static float yawOffsetD = 13.0F;
   static float tickModOdd1 = 6.0F;
   static float tickModOdd2 = SgClass057.0F;
   static float yawOffsetE = 17.0F;
   static float yawOffsetF = SgClass006.0F;
   static float yawOffsetG = 6.0F;
   static float yawOffsetH = 7.0F;
   static boolean invertActive = false;
   float lerpAlphaIntersectA;
   static float lerpAlphaIntersectB = 0.7F;
   float lerpAlphaIntersectC;
   static float lerpAlphaIntersectD = 0.91F;
   static float smoothX = 0.9067745F;
   static float lerpSpeedIntersectX1 = 0.35F;
   static float lerpSpeedIntersectX2 = 0.65F;
   static float lerpSpeedNormalX1 = 0.85F;
   static float lerpSpeedNormalX2 = 0.95F;
   float lerpAlphaZX;
   static float lerpAlphaZX2 = 0.7F;
   float lerpAlphaZX3;
   static float lerpAlphaZX4 = 0.91F;
   static float smoothZ = 0.8687472F;
   static float lerpSpeedIntersectZ1 = 0.35F;
   static float lerpSpeedIntersectZ2 = 0.65F;
   static float lerpSpeedNormalZ1 = 0.85F;
   static float lerpSpeedNormalZ2 = 0.95F;
   static float aimTimerLow = 160.0F;
   static float aimTimerHigh = 190.0F;
   static double heightScaleFactor = 0.5;
   static float heightDivisor = 0.0F;
   static float lerpAlphaYLow = 0.29F;
   static float lerpAlphaYHigh = 0.45F;
   static float lerpAlphaY2 = 0.69F;
   static float yTimerLow = 55.0F;
   static float yTimerHigh = 75.0F;
   static float lerpAlphaOriginLow = 0.15F;
   static float lerpAlphaOriginHigh = 0.35F;
   float panelWidth;
   float panelHeight;
   float smoothXDefault;
   float smoothXMax;
   float smoothZDefault;
   float smoothZMax;
   String utilityClassError;
   static double gaussianSigma = 0.02F;
   static double skewExponent = 0.7;
   static double skewScale = 0.SgClass057;
   static double skewOffset = 0.1;
   static double logScale = 3.0;
   static double logMult = 0.5;
   static double logBase = 0.5;

   public static void init() {
      random = new SecureRandom();
      field_Lsg = Vec3d.ZERO;
      originPos = Vec3d.ZERO;
      lastAimUpdateTime = 0L;
   }

   public static void updateRotation(LivingEntity LivingEntity) {
      if (mc.player != null && mc.playerController != null) {
         KillAuraModule killauramodule = BiConsumerImpl.й.module0;
         Vec3d vec3d = computeTargetVector(LivingEntity);
         float f = yawRangeMin;
         float f1 = yawRangeMax;
         float f2 = (float)Math.ceil(randomRange(f, f1) * Math.cos(System.currentTimeMillis() / oscillationPeriod));
         if (invertOscillation) {
            f2 = -f2;
         }

         if (mc.playerController.snapTicks > randomRange(2.0F, snapTicksLow) && mc.playerController.snapTicks < randomRange(snapTicksHigh, snapTicksMax)) {
            f2 += randomRange(snapOffsetValue, 1.0F);
         }

         float f3 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - baseYawOffset) + f2;
         float f4 = (float)(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))));
         currentYaw = f3;
         currentPitch = f4;
         Box Box = LivingEntity.getBoundingBox();
         boolean flag = mc.player.getBoundingBox().intersects(Box);
         boolean flag1 = KillAuraModule.isEntityInRayCast(f3, f4, killauramodule.attackRangeSlider.get().floatValue(), LivingEntity);
         float f5;
         if (flag) {
            if (mc.player.ticksExisted % randomRange(2.0F, tickModEven) == 0.0F) {
               if (!flag1) {
                  f5 = randomRange(yawOffsetA, yawOffsetB);
               } else {
                  f5 = 0.0F;
               }
            } else {
               f5 = randomRange(yawOffsetC, yawOffsetD);
            }
         } else if (mc.player.ticksExisted % randomRange(tickModOdd1, tickModOdd2) == 0.0F) {
            if (!flag1) {
               f5 = randomRange(yawOffsetE, yawOffsetF);
            } else {
               f5 = 0.0F;
            }
         } else {
            f5 = randomRange(yawOffsetG, yawOffsetH);
         }

         if (!invertActive && mc.player.ticksExisted % 760 == 0) {
            invertActive = true;
         }

         RotationHelper.aimAtSimple(new Rotation(f3, f4), f5, f5 * f5, 1, 12);
      }
   }

   public static Vec3d computeTargetVector(LivingEntity LivingEntity) {
      boolean flag = mc.player.getBoundingBox().intersects(LivingEntity.getBoundingBox());
      smoothX = MathHelper.lerp(
         randomRange(invertOscillation ? lerpAlphaIntersectA : lerpAlphaIntersectB, invertOscillation ? lerpAlphaIntersectC : lerpAlphaIntersectD),
         smoothX,
         flag ? randomRange(lerpSpeedIntersectX1, lerpSpeedIntersectX2) : randomRange(lerpSpeedNormalX1, lerpSpeedNormalX2)
      );
      smoothZ = MathHelper.lerp(
         randomRange(invertOscillation ? lerpAlphaZX : lerpAlphaZX2, invertOscillation ? lerpAlphaZX3 : lerpAlphaZX4),
         smoothZ,
         flag ? randomRange(lerpSpeedIntersectZ1, lerpSpeedIntersectZ2) : randomRange(lerpSpeedNormalZ1, lerpSpeedNormalZ2)
      );
      if (originPos == Vec3d.ZERO) {
         originPos = LivingEntity.getEyePosition(1.0F);
      }

      double d0 = LivingEntity.getPosX();
      double d1 = LivingEntity.getPosZ();
      Vec3d vec3d = mc.player.getEyePosition(1.0F);
      if (field_Lsg == Vec3d.ZERO) {
         field_Lsg = vec3d;
      }

      double d2 = Math.abs(LivingEntity.getPosY() - mc.player.getPosY());
      boolean flag1 = d2 > 1.0;
      if ((float)(System.currentTimeMillis() - lastAimUpdateTime) > randomRange(aimTimerLow, aimTimerHigh)) {
         lastAimUpdateTime = System.currentTimeMillis();
         double d3 = LivingEntity.getPosY();
         double d4 = d3 + (flag1 ? LivingEntity.getHeight() * heightScaleFactor : LivingEntity.getHeight() / heightDivisor);
         originPos.y = MathHelper.lerp((double)randomRange(lerpAlphaYLow, flag1 ? lerpAlphaYHigh : lerpAlphaY2), originPos.y, d4);
      }

      if ((float)(System.currentTimeMillis() - lastAimUpdateTime) > randomRange(yTimerLow, yTimerHigh)) {
         field_Lsg.y = MathHelper.lerp((double)randomRange(lerpAlphaOriginLow, lerpAlphaOriginHigh), field_Lsg.y, vec3d.y);
      }

      field_Lsg.x = MathHelper.lerp((double)smoothX, field_Lsg.x, vec3d.x);
      field_Lsg.z = MathHelper.lerp((double)smoothZ, field_Lsg.z, vec3d.z);
      originPos.x = MathHelper.lerp((double)smoothX, originPos.x, d0);
      originPos.z = MathHelper.lerp((double)smoothZ, originPos.z, d1);
      return originPos.subtract(field_Lsg);
   }

   private static float randomSecureRange(float f, float f1) {
      return f + (f1 - f) * random.nextFloat();
   }

   private static float randomMathRange(float f, float f1) {
      return f + (f1 - f) * (float)Math.random();
   }

   public static void reset() {
      random = new SecureRandom();
      field_Lsg = Vec3d.ZERO;
      originPos = Vec3d.ZERO;
      heightDivisor = randomRange(panelWidth, panelHeight);
      smoothX = randomRange(smoothXDefault, smoothXMax);
      smoothZ = randomRange(smoothZDefault, smoothZMax);
      invertActive = false;
   }

   private AimAssistRotationUtil() {
      throw new UnsupportedOperationException(utilityClassError);
   }

   private static float randomBiasedRange(float f, float f1) {
      float f2 = random.nextFloat();
      float f3 = random.nextFloat();
      return f + (f1 - f) * ((f2 + f3) / 2.0F);
   }

   private static float randomGaussianRange(float f, float f1) {
      double d0 = random.nextDouble();
      double d1 = random.nextDouble();
      double d2 = random.nextGaussian() * gaussianSigma;
      double d3 = Math.pow(d0, 1.0 + random.nextDouble() * skewExponent);
      double d4 = (d1 * skewScale + skewOffset) * (Math.log1p(d0 * logScale) * logMult + logBase);
      return (float)(f + (f1 - f) * d3 * d4 + d2);
   }

   private static float randomRange(float f, float f1) {
      if (f1 < f) {
         float f2 = f;
         f = f1;
         f1 = f2;
      }

      if (f1 == f) {
         return f;
      } else {
         float f3;
         switch (random.nextInt(4)) {
            case 0:
               f3 = randomBiasedRange(f, f1);
               break;
            case 1:
               f3 = randomGaussianRange(f, f1);
               break;
            case 2:
               f3 = randomMathRange(f, f1);
               break;
            default:
               f3 = randomSecureRange(f, f1);
         }

         if (!Float.isFinite(f3)) {
            f3 = f;
         }

         return MathHelper.clamp(f3, f, f1);
      }
   }
}
