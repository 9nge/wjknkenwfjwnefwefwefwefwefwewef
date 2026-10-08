package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.config.MultiBoxSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PlayerMovePacket;
import catlavan.util.BiConsumerImpl;
import catlavan.util.McContextHolder;
import catlavan.util.MouseSensitivityUtil;
import catlavan.util.MovementUtil;
import catlavan.util.SmoothValueAnimation;
import java.util.ArrayList;
import java.util.Comparator;
import sg.BlockRayTraceResult;
import sg.Vec3d;
import sg.SgClass179;
import sg.PlayerEntity;
import sg.SgClass241;
import sg.MathHelper;
import sg.SgClass402;
import sg.ClientTickEvent;
import sg.ItemStack;
import sg.Items;
import sg.LivingEntity;
import sg.RayTraceResultType;
import sg.SgClass596;
import sg.MinecraftClient;

public class ProjectileAimBotModule extends Module implements McContextHolder {
   static MinecraftClient mc;
   float yawOffset;
   static LivingEntity currentTarget;
   static boolean isAiming = false;
   SgClass596 aimRotation;
   static MultiBoxSetting field_Lsg;
   static FloatSliderSetting distanceSlider;
   float fovAngle;
   float pitchFov;
   double yawOffsetDeg;
   float pitchMin;
   float pitchMax;
   long jitterPeriodMs;
   long jitterHalfPeriodMs;
   float yawSmoothDivisor;
   float pitchSmoothDivisor;
   float pitchClampMin;
   float pitchClampMax;
   float yawSmoothFactor;
   float pitchSmoothFactor;
   float pitchClampMin2;
   float pitchClampMax2;
   String moduleNameRu;
   String moduleNameEn;
   String optionTridentName;
   String optionCrossbowName;
   String optionBowName;
   String unusedStrE;
   String unusedStrU;
   String unusedStrW;
   String distanceSliderName;
   float distanceDefault;
   float distanceMin;
   float distanceMax;
   String distanceDisplayName;

   private float[] calcYawPitchToEntity(LivingEntity LivingEntity) {
      double d0 = LivingEntity.getPosX() - mc.player.getPosX();
      double d1 = LivingEntity.getPosY() + LivingEntity.getEyeHeight() - (mc.player.getPosY() + mc.player.getEyeHeight());
      double d2 = LivingEntity.getPosZ() - mc.player.getPosZ();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = (float)Math.toDegrees(Math.atan2(d2, d0)) - yawOffset;
      float f1 = (float)(-Math.toDegrees(Math.atan2(d1, d3)));
      return new float[]{f, f1};
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof PlayerMovePacket) {
         PlayerMovePacket playermovepacket = (PlayerMovePacket)event;
         if (currentTarget != null && isAiming) {
            MovementUtil.setMovementInput(playermovepacket, aimRotation.x);
         }
      }

      if (event instanceof ClientTickEvent) {
         isAiming = false;
         ItemStack itemStack = mc.player.getHeldItemMainhand();
         if (!BiConsumerImpl.й.module0.enabled && (mc.player.isHandActive() || itemStack.getItem() == Items.CROSSBOW)) {
            if (field_Lsg.isCheckedByIndex(0) && itemStack.getItem() == Items.TRIDENT) {
               isAiming = true;
            }

            if (field_Lsg.isCheckedByIndex(1) && itemStack.getItem() == Items.CROSSBOW) {
               isAiming = true;
            }

            if (field_Lsg.isCheckedByIndex(2) && itemStack.getItem() == Items.BOW) {
               isAiming = true;
            }
         }

         if (currentTarget != null && isAiming) {
            this.updateAimRotation();
         } else if (currentTarget != null) {
            currentTarget = null;
         }
      }

      if (event instanceof ClientTickEvent) {
         ArrayList arraylist = new ArrayList();

         for (PlayerEntity м3 : mc.world.getPlayers()) {
            boolean flag = true;
            if (!this.hasLineOfSight(м3)) {
               flag = false;
            }

            if (mc.player.getDistance(м3) > distanceSlider.get().intValue()) {
               flag = false;
            }

            if (м3 != mc.player && !BiConsumerImpl.й.contains(м3.getName().getString()) && flag) {
               float f = fovAngle;
               float[] afloat = this.calcYawPitchToEntity(м3);
               float f1 = MathHelper.wrapDegrees(afloat[0] - mc.player.rotationYaw);
               float f2 = Math.abs(afloat[1] - mc.player.rotationPitch);
               if (!(Math.abs(f1) > f / 2.0F) && !(f2 > pitchFov)) {
                  arraylist.add(м3);
               } else if (м3 == currentTarget) {
                  currentTarget = null;
               }
            }

            if (currentTarget != null && м3 == currentTarget && !flag) {
               currentTarget = null;
            }
         }

         if (currentTarget == null) {
            if (!arraylist.isEmpty()) {
               arraylist.sort(Comparator.comparingDouble(м3 -> mc.player.getDistanceSq(м3x.getPosX(), м3x.getPosY(), м3x.getPosZ())));
               currentTarget = (LivingEntity)arraylist.get(0);
            } else {
               currentTarget = null;
            }
         }
      }

      if (event instanceof SmoothValueAnimation) {
         SmoothValueAnimation smoothvalueanimation = (SmoothValueAnimation)event;
         if (currentTarget == null) {
            aimRotation = new SgClass596(smoothvalueanimation.getCurrentValue(), smoothvalueanimation.getStartValue());
            isAiming = false;
         }

         if (currentTarget != null && isAiming) {
            mc.player.rotationYawHead = aimRotation.x;
            mc.player.renderYawOffset = aimRotation.x;
            mc.player.rotationPitchHead = aimRotation.y;
            smoothvalueanimation.setCurrentValue(aimRotation.x);
            smoothvalueanimation.setStartValue(aimRotation.y);
         }
      }
   }

   public ProjectileAimBotModule() {
      this.addSettings(new AbstractModuleSetting[]{field_Lsg, distanceSlider});
   }

   @Override
   public void onDisable() {
      currentTarget = null;
   }

   private boolean hasLineOfSight(LivingEntity LivingEntity) {
      Vec3d дыxx = LivingEntity.getPositionVec().add(0.0, LivingEntity.getEyeHeight(), 0.0);
      Vec3d дыx = mc.player.getPositionVec();
      Vec3d дыxx = дыx.add(0.0, mc.player.getEyeHeight(), 0.0);
      BlockRayTraceResult BlockRayTraceResult = mc.world.rayTraceBlocks(new SgClass402(дыxx, дыxx, SgClass241.OUTLINE, SgClass179.NONE, mc.player));
      return BlockRayTraceResult.getType() == RayTraceResultType.MISS;
   }

   private void updateAimRotation() {
      Vec3d vec3d = currentTarget.getPositionVec().add(0.0, currentTarget.getHeight(), 0.0).subtract(mc.player.getEyePosition(1.0F));
      float f = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d.z, vec3d.x)) - yawOffsetDeg);
      float f1 = (float)(-Math.toDegrees(Math.atan2(vec3d.y, Math.hypot(vec3d.x, vec3d.z))));
      float f2 = MathHelper.wrapDegrees(f - aimRotation.x);
      float f3 = MathHelper.wrapDegrees(f1 - aimRotation.y);
      int i = (int)f2;
      float f4 = aimRotation.x + i;
      float f5 = MathHelper.clamp(aimRotation.y + f3, pitchMin, pitchMax);
      float f6 = MouseSensitivityUtil.getSensitivityStep();
      f4 -= (f4 - aimRotation.x) % f6;
      f5 -= (f5 - aimRotation.y) % f6;
      float f7;
      float f8;
      if (System.currentTimeMillis() % jitterPeriodMs > jitterHalfPeriodMs) {
         f7 = f4 - 0.0F;
         f8 = f5 + 0.0F;
      } else {
         f7 = f4 + 0.0F;
         f8 = f5 - 0.0F;
      }

      float f9 = aimRotation.x / yawSmoothDivisor;
      float f10 = MathHelper.clamp(aimRotation.y / pitchSmoothDivisor, pitchClampMin, pitchClampMax);
      f9 = aimRotation.x + (f7 - aimRotation.x) / yawSmoothFactor;
      f10 = MathHelper.clamp(aimRotation.y + (f8 - aimRotation.y) / pitchSmoothFactor, pitchClampMin2, pitchClampMax2);
      float f11 = MouseSensitivityUtil.getSensitivityStep();
      f9 -= (f9 - aimRotation.x) % f11;
      f10 -= (f10 - aimRotation.y) % f11;
      aimRotation = new SgClass596(f9, f10);
   }

   @Override
   public void onEnable() {
   }

   public static void initModule() {
      aimRotation = new SgClass596(0.0F, 0.0F);
      field_Lsg = new MultiBoxSetting(
         moduleNameRu,
         moduleNameEn,
         new BooleanSetting(optionTridentName, true, optionCrossbowName),
         new BooleanSetting(optionBowName, true, unusedStrE),
         new BooleanSetting(unusedStrU, true, unusedStrW)
      );
      distanceSlider = new FloatSliderSetting(distanceSliderName, distanceDefault, distanceMin, distanceMax, 1.0F, distanceDisplayName);
      isAiming = false;
   }
}
