package catlavan.module.combat;

import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PlayerMovePacket;
import catlavan.util.BiConsumerImpl;
import catlavan.util.MathUtil;
import catlavan.util.McContextHolder;
import catlavan.util.MouseSensitivityUtil;
import catlavan.util.MovementUtil;
import catlavan.util.SmoothValueAnimation;
import catlavan.util.Timer;
import java.util.ArrayList;
import java.util.Comparator;
import sg.SgClass125;
import sg.Vec3d;
import sg.SgClass145;
import sg.SgClass154;
import sg.MathHelper;
import sg.Entity;
import sg.ClientTickEvent;
import sg.ItemEntity;
import sg.Items;
import sg.LivingEntity;
import sg.Hand;
import sg.SgClass596;
import sg.MinecraftClient;

public class KillAuraModule2 extends Module implements McContextHolder {
   static MinecraftClient context;
   static float cooldownPartialTicks = 1.5F;
   static float minCooldown = 0.95F;
   static Entity targetEntity;
   static boolean shouldRotate = false;
   static Timer attackTimer;
   static long ATTACK_TIMER_PERIOD = 1200L;
   float baseYaw;
   float yawJitterMin;
   float yawJitterMax;
   static SgClass596 aimRotation;
   double attackRangeSq;
   double yawOffsetDeg;
   float pitchMin;
   float pitchMax;
   static long jitterPeriod = 300L;
   static long jitterHalfPeriod = 150L;
   static float field_F = 15.0F;
   static float jitterYawAmp = 5.0F;
   static float jitterPitchAmp = 15.0F;
   static float jitterPosYawAmp = 5.0F;
   static float jitterPosPitchAmp = 1.5F;
   static float smoothDivisorX = 1.2F;
   static float pitchClampMin1 = -90.0F;
   static float pitchClampMax1 = 90.0F;
   static float smoothDivisorY = 1.8F;
   static float smoothDivisorY2 = 1.8F;
   static float pitchClampMin2 = -90.0F;
   static float pitchClampMax2 = 90.0F;
   float DEFAULT_RANGE;
   float currentRange;
   float field_F2;
   float itemEntityRange;

   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent) {
         targetEntity = null;
         shouldRotate = false;
         this.method3707();
         this.method9048();
         if (targetEntity != null) {
            context.gameSettings.keyBindForward.setPressed(true);
            context.gameSettings.keyBindSprint.setPressed(true);
         } else {
            context.gameSettings.keyBindForward.setPressed(false);
         }

         if (this.method4566()) {
            this.updateAimRotation();
         } else {
            shouldRotate = true;
         }

         if (!attackTimer.hasElapsed(ATTACK_TIMER_PERIOD) && !(targetEntity instanceof ItemEntity)) {
            shouldRotate = true;
         }
      }

      if (event instanceof PlayerMovePacket) {
         PlayerMovePacket playermovepacket = (PlayerMovePacket)event;
         if (shouldRotate && !(targetEntity instanceof ItemEntity)) {
            float f = baseYaw;
            if (context.player.collidedHorizontally) {
               f += MathUtil.method1659(yawJitterMin, yawJitterMax);
            }

            MovementUtil.setMovementInput(playermovepacket, aimRotation.x - f);
         }
      }

      if (event instanceof SmoothValueAnimation) {
         SmoothValueAnimation smoothvalueanimation = (SmoothValueAnimation)event;
         smoothvalueanimation.setCurrentValue(aimRotation.x);
         smoothvalueanimation.setStartValue(aimRotation.y);
      }
   }

   public boolean method4566() {
      float f = context.player.getCooledAttackStrength(cooldownPartialTicks);
      return !(f < minCooldown);
   }

   private void method9048() {
      float f = 0.0F;
      float f1 = 0.0F;
      if (targetEntity != null) {
         Object object = null;
         if (targetEntity instanceof ItemEntity) {
            object = targetEntity.getPositionVec()
               .add(0.0, MathHelper.clamp(context.player.getPosYEye() - targetEntity.getPosY(), 0.0, (double)targetEntity.getHeight()), 0.0)
               .subtract(context.player.getEyePosition(1.0F));
         } else {
            object = targetEntity.getPositionVec()
               .add(
                  0.0,
                  MathHelper.clamp(
                     context.player.getPosYEye() - targetEntity.getPosY(),
                     0.0,
                     targetEntity.getHeight() * (context.player.getDistanceEyePos((LivingEntity)targetEntity) / attackRangeSq)
                  ),
                  0.0
               )
               .subtract(context.player.getEyePosition(1.0F));
         }

         float f2 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(((Vec3d)object).z, ((Vec3d)object).x)) - yawOffsetDeg);
         float f3 = (float)(-Math.toDegrees(Math.atan2(((Vec3d)object).y, Math.hypot(((Vec3d)object).x, ((Vec3d)object).z))));
         float f4 = MathHelper.wrapDegrees(f2 - aimRotation.x);
         float f5 = MathHelper.wrapDegrees(f3 - aimRotation.y);
         int i = (int)f4;
         float f6 = aimRotation.x + i;
         float f7 = MathHelper.clamp(aimRotation.y + f5, pitchMin, pitchMax);
         float f8 = MouseSensitivityUtil.getSensitivityStep();
         f6 -= (f6 - aimRotation.x) % f8;
         f7 -= (f7 - aimRotation.y) % f8;
         f = f6;
         f1 = f7;
      }

      if (targetEntity == null) {
         f = context.player.rotationYaw;
         f1 = context.player.rotationPitch;
      }

      float f9;
      float f10;
      if (System.currentTimeMillis() % jitterPeriod > jitterHalfPeriod) {
         f9 = f - field_F;
         f10 = f1 - jitterYawAmp;
      } else {
         f9 = f + jitterPitchAmp;
         f10 = f1 + jitterPosYawAmp;
      }

      float f11 = aimRotation.x / jitterPosPitchAmp;
      float f12 = MathHelper.clamp(aimRotation.y / smoothDivisorX, pitchClampMin1, pitchClampMax1);
      f11 = aimRotation.x + (f9 - aimRotation.x) / smoothDivisorY;
      f12 = MathHelper.clamp(aimRotation.y + (f10 - aimRotation.y) / smoothDivisorY2, pitchClampMin2, pitchClampMax2);
      float f13 = MouseSensitivityUtil.getSensitivityStep();
      f11 -= (f11 - aimRotation.x) % f13;
      f12 -= (f12 - aimRotation.y) % f13;
      aimRotation = new SgClass596(f11, f12);
   }

   public static void resetState() {
      targetEntity = null;
      aimRotation = new SgClass596(0.0F, 0.0F);
      shouldRotate = false;
      currentRange = DEFAULT_RANGE;
      attackTimer = new Timer();
   }

   private void method3707() {
      boolean flag = false;
      ArrayList arraylist = new ArrayList();

      for (Entity ъюx : context.world.getAllEntities()) {
         if (ъюx instanceof SgClass145 && context.player.getDistance(ъюx) < field_F2 && ъюx.isAlive()) {
            arraylist.add(ъюx);
            flag = true;
         }
      }

      if (!flag) {
         for (Entity ъюx : context.world.getAllEntities()) {
            if (ъюx instanceof ItemEntity && ((ItemEntity)ъюx).getItem().getItem() == Items.GUNPOWDER && context.player.getDistance(ъюx) < itemEntityRange) {
               arraylist.add(ъюx);
            }
         }
      }

      arraylist.sort(Comparator.comparingDouble(BiConsumerImpl.й.module96::getTargetHitboxLength));
      if (!arraylist.isEmpty() && arraylist.get(0) != null) {
         targetEntity = (Entity)arraylist.get(0);
      }
   }

   private void updateAimRotation() {
      if (targetEntity != null && !(targetEntity instanceof ItemEntity)) {
         Entity entity = SgClass125.5(targetEntity, aimRotation.x, aimRotation.y, currentRange);
         if (entity != null && this.getTargetHitboxLength(targetEntity) <= currentRange) {
            context.playerController.attackEntity(context.player, targetEntity);
            context.player.swingArm(Hand.MAIN_HAND);
            attackTimer.reset();
         }
      }
   }

   private double getTargetHitboxLength(Entity entity) {
      return SgClass154.I((LivingEntity)entity).length();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      aimRotation = new SgClass596(context.player.rotationYaw, context.player.rotationPitch);
   }
}
