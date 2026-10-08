package catlavan.module.combat;

import catlavan.util.MathUtil;
import catlavan.util.McContextHolder;
import catlavan.util.Rotation;
import catlavan.util.RotationHelper;
import java.util.concurrent.ThreadLocalRandom;
import sg.Vec3d;
import sg.MathHelper;
import sg.Entity;
import sg.LivingEntity;
import sg.Box;
import sg.MinecraftClient;

public final class AimRotationUtil implements McContextHolder {
   String utilClassName;
   static MinecraftClient mc;
   static double CLAMP_MIN_Y = 0.15;
   static double CLOSE_RANGE_THRESHOLD_SQ = 1.0E-6;
   static double YAW_OFFSET = 90.0;
   static float RANDOM_SPREAD = 3.0F;
   static double OSCILLATION_PERIOD = 60.0;
   float SPREAD_MIN_1;
   float SPREAD_MAX_1;
   float SPREAD_MIN_2;
   float SPREAD_MAX_2;
   static float VISIBLE_SPREAD_MIN_1 = 155.0F;
   static float VISIBLE_SPREAD_MAX_1 = 180.0F;
   static float VISIBLE_SPREAD_MIN_2 = 155.0F;
   static float VISIBLE_SPREAD_MAX_2 = 180.0F;

   public static float method4507(LivingEntity LivingEntity) {
      Vec3d vec3d = getDirectionToEntity(LivingEntity);
      return (float)(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))));
   }

   private AimRotationUtil() {
      throw new UnsupportedOperationException(utilClassName);
   }

   private static Vec3d getDirectionToEntity(LivingEntity LivingEntity) {
      Vec3d дыxxx = mc.player.getEyePosition(mc.getRenderPartialTicks());
      double d0 = MathHelper.clamp(mc.player.getPosYEye() - LivingEntity.getPosY(), CLAMP_MIN_Y, (double)LivingEntity.getEyeHeight());
      Vec3d дыx = LivingEntity.getPositionVec().add(0.0, d0, 0.0);
      Vec3d дыxx = дыx.subtract(дыxxx);
      if (дыxx.lengthSquared() < CLOSE_RANGE_THRESHOLD_SQ) {
         Vec3d дыxxx = clampToBoundingBox(дыxxx, LivingEntity);
         дыxx = дыxxx.subtract(дыxxx);
      }

      return дыxx;
   }

   public static Vec3d clampToAABB(Vec3d vec3d, Box Box) {
      double d0 = MathHelper.clamp(vec3d.getX(), Box.minX, Box.maxX);
      double d1 = MathHelper.clamp(vec3d.getY(), Box.minY, Box.maxY);
      double d2 = MathHelper.clamp(vec3d.getZ(), Box.minZ, Box.maxZ);
      return new Vec3d(d0, d1, d2);
   }

   public static Vec3d getDirectionToAABB(Entity entity) {
      Vec3d дыx = mc.player.getEyePosition(mc.getRenderPartialTicks());
      Vec3d дыx = clampToBoundingBox(дыx, entity);
      return дыx.subtract(дыx);
   }

   public static Vec3d clampToBoundingBox(Vec3d vec3d, Entity entity) {
      return clampToAABB(vec3d, entity.getBoundingBox());
   }

   public static float getYawToEntity(LivingEntity LivingEntity) {
      Vec3d vec3d = getDirectionToEntity(LivingEntity);
      return (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - YAW_OFFSET);
   }

   public static void aimAtEntity(LivingEntity LivingEntity) {
      if (LivingEntity != null) {
         float f = getYawToEntity(LivingEntity);
         float f1 = method4507(LivingEntity);
         float f2 = MathUtil.method1659(0.0F, RANDOM_SPREAD);
         float f3 = MathUtil.method1659(0.0F, 2.0F);
         float f4 = MathUtil.method1659(-f2, f2);
         float f5 = MathUtil.method1659(-f3, f3);
         boolean flag = !mc.player.canEntityBeSeen(LivingEntity) && !mc.player.isElytraFlying();
         float f6 = (float)Math.ceil(ThreadLocalRandom.current().nextInt(-17, 6) * Math.cos(System.currentTimeMillis() / OSCILLATION_PERIOD));
         if (flag) {
            RotationHelper.aimAtSimple(
               new Rotation(f + f6, f1), MathUtil.method1659(SPREAD_MIN_1, SPREAD_MAX_1), MathUtil.method1659(SPREAD_MIN_2, SPREAD_MAX_2), 1, 6
            );
         } else {
            RotationHelper.aimAtSimple(
               new Rotation(f + f4, f1 + f5),
               MathUtil.method1659(VISIBLE_SPREAD_MIN_1, VISIBLE_SPREAD_MAX_1),
               MathUtil.method1659(VISIBLE_SPREAD_MIN_2, VISIBLE_SPREAD_MAX_2),
               1,
               6
            );
         }
      }
   }
}
