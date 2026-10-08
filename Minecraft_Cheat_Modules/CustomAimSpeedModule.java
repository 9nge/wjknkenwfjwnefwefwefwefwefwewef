package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.config.ModeSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;
import catlavan.util.MutableVec2f;
import java.util.Arrays;
import sg.MathHelper;
import sg.MinecraftClient;

public class CustomAimSpeedModule extends Module implements McContextHolder {
   ModeSetting modeSettings;
   static String modeNameRu = "Custom";
   static String modeNameEn = "Custom";
   static String customDefault = "Custom";
   String currentMode = customDefault;
   static String modeLabelRu = "Мод";
   static String modeCustomRu = "Custom";
   static String modeCustomRu2 = "Custom";
   static String modeCustomRu3;
   static String modeCustomRu4;
   static String yaw0_5Label;
   static String yaw5_10Label = "Yaw 0-5";
   static float yaw0_5DefaultValue = 1.52F;
   static float yaw0_5Min = 1.5F;
   static float yaw0_5Max = 3.0F;
   static float yaw0_5Step = 0.01F;
   FloatSliderSetting yaw0_5Slider;
   static String yaw5_10SliderLabel = "Yaw 5-10";
   static float yaw5_10DefaultValue = 1.53F;
   static float yaw5_10Min = 1.5F;
   static float yaw5_10Max = 3.0F;
   static float yaw5_10Step = 0.01F;
   FloatSliderSetting yaw5_10Slider;
   static String yaw10_15SliderLabel = "Yaw 10-15";
   static float yaw10_15DefaultValue = 1.54F;
   static float yaw10_15Min = 1.5F;
   static float yaw10_15Max = 3.0F;
   static float yaw10_15Step = 0.01F;
   FloatSliderSetting yaw10_15Slider;
   static String yaw15_20SliderLabel = "Yaw 15-SgClass017";
   static float yaw15_20DefaultValue = 1.55F;
   static float yaw15_20Min = 1.5F;
   static float yaw15_20Max = 3.0F;
   static float yaw15_20Step = 0.01F;
   FloatSliderSetting field_Lsgl;
   static String yaw20_25SliderLabel = "Yaw SgClass017-25";
   static float yaw20_25DefaultValue = 1.56F;
   static float yaw20_25Min = 1.5F;
   static float yaw20_25Max = 3.0F;
   static float yaw20_25Step = 0.01F;
   FloatSliderSetting yaw20_25Slider;
   static String yaw25_30SliderLabel = "Yaw 25-30";
   static float yaw25_30DefaultValue = 1.57F;
   static float yaw25_30Min = 1.5F;
   static float yaw25_30Max = 3.0F;
   static float yaw25_30Step = 0.01F;
   FloatSliderSetting field_Lsgl2;
   static String yaw30_35SliderLabel = "Yaw 30-35";
   static float yaw30_35DefaultValue = 1.58F;
   static float yaw30_35Min = 1.5F;
   static float yaw30_35Max = 3.0F;
   static float yaw30_35Step = 0.01F;
   FloatSliderSetting yaw30_35Slider;
   static String yaw35_40SliderLabel = "Yaw 35-40";
   static float yaw35_40DefaultValue = 1.59F;
   static float yaw35_40Min = 1.5F;
   static float yaw35_40Max = 3.0F;
   static float yaw35_40Step = 0.01F;
   FloatSliderSetting field_Lsgl3;
   static String yaw40_45SliderLabel = "Yaw 40-45";
   static float yaw40_45DefaultValue = 1.6F;
   static float yaw40_45Min = 1.5F;
   static float yaw40_45Max = 3.0F;
   static float yaw40_45Step = 0.01F;
   FloatSliderSetting yaw40_45Slider;
   static String pitch0_5SliderLabel = "Pitch 0-5";
   static float pitch0_5DefaultValue = 1.51F;
   static float pitch0_5Min = 1.5F;
   static float pitch0_5Max = 3.0F;
   static float pitch0_5Step = 0.01F;
   FloatSliderSetting pitch0_5Slider;
   static String pitch5_10SliderLabel = "Pitch 5-10";
   static float pitch5_10DefaultValue = 1.52F;
   static float pitch5_10Min = 1.5F;
   static float pitch5_10Max = 3.0F;
   static float pitch5_10Step = 0.01F;
   FloatSliderSetting pitch5_10Slider;
   static String pitch10_15SliderLabel = "Pitch 10-15";
   static float pitch10_15DefaultValue = 1.53F;
   static float pitch10_15Min = 1.5F;
   static float pitch10_15Max = 3.0F;
   static float pitch10_15Step = 0.01F;
   FloatSliderSetting field_Lsgl4;
   static String pitch15_20SliderLabel = "Pitch 15-SgClass017";
   static float pitch15_20DefaultValue = 1.54F;
   static float pitch15_20Min = 1.5F;
   static float pitch15_20Max = 3.0F;
   static float pitch15_20Step = 0.01F;
   FloatSliderSetting pitch15_20Slider;
   static String pitch20_25SliderLabel = "Pitch SgClass017-25";
   static float pitch20_25DefaultValue = 1.55F;
   static float pitch20_25Min = 1.5F;
   static float pitch20_25Max = 3.0F;
   static float pitch20_25Step = 0.01F;
   FloatSliderSetting pitch20_25Slider;
   static String pitch25_30SliderLabel = "Pitch 25-30";
   static float pitch25_30DefaultValue = 1.56F;
   static float pitch25_30Min = 1.5F;
   static float pitch25_30Max = 3.0F;
   static float pitch25_30Step = 0.01F;
   FloatSliderSetting pitch25_30Slider;
   static String pitch30_35SliderLabel = "Pitch 30-35";
   static float pitch30_35DefaultValue = 1.57F;
   static float pitch30_35Min = 1.5F;
   static float pitch30_35Max = 3.0F;
   static float pitch30_35Step = 0.01F;
   FloatSliderSetting pitch30_35Slider;
   static String pitch35_40SliderLabel = "Pitch 35-40";
   static float pitch35_40DefaultValue = 1.58F;
   static float pitch35_40Min = 1.5F;
   static float pitch35_40Max = 3.0F;
   static float pitch35_40Step = 0.01F;
   FloatSliderSetting pitch35_40Slider;
   static String pitch40_45SliderLabel = "Pitch 40-45";
   static float pitch40_45DefaultValue = 1.59F;
   static float pitch40_45Min = 1.5F;
   static float pitch40_45Max = 3.0F;
   static float pitch40_45Step = 0.01F;
   FloatSliderSetting field_Lsgl5;
   FloatSliderSetting[] yawSliders;
   FloatSliderSetting[] pitchSliders;
   static String modeReallyWorld = "Custom";
   static String modeCustom = "Custom";
   static String modeBravoHVH = "Custom";
   float yawBracketStep;
   static String modeCustomLabel2 = "Custom";
   static String modeCustomLabel3 = "Custom";
   static String modeCustomLabel4 = "Custom";
   float pitchBracketStep;
   float yawSpeedTable0;
   float yawSpeedTable1;
   float yawSpeedTable2;
   float yawSpeedTable3;
   float yawSpeedTable4;
   float yawSpeedTable5;
   float yawSpeedTable6;
   float yawSpeedTable7;
   float yawSpeedTable8;
   static String yawMode0Label = "Custom";
   static String yawMode1Label = "Custom";
   static String yawMode2Label = "Custom";
   float pitchBracketStepAlt;
   static String yawMode3Label = "Custom";
   static String yawMode4Label = "Custom";
   static String yawMode5Label = "Custom";
   static String yawMode6Label = "Custom";
   float pitchStepForPitchTable;
   float pitchSpeedTable0;
   float pitchSpeedTable1;
   float pitchSpeedTable2;
   float pitchSpeedTable3;
   float pitchSpeedTable4;
   float pitchSpeedTable5;
   float pitchSpeedTable6;
   float pitchSpeedTable7;
   float pitchSpeedTable8;
   static String pitchMode0Label = "Custom";
   float angleNormThreshold1;
   float angleNormThreshold2;
   float angleNormThreshold3;
   float angleNormMirrorBase;
   float thirdSpeedTable0;
   float thirdSpeedTable1;
   float thirdSpeedTable2;
   float thirdSpeedTable3;
   float thirdSpeedTable4;
   float thirdSpeedTable5;
   float thirdSpeedTable6;
   float thirdSpeedTable7;
   float thirdSpeedTable8;
   float thirdBracketStep;
   MinecraftClient mc;
   String modeReallyWorldLabel;
   String modeCustomAltLabel;
   String modeBravoHVHLabel;
   static String staticModeLabel = "Custom";
   float pitchStepAlt;
   float altSpeedTable0;
   float altSpeedTable1;
   float altSpeedTable2;
   float altSpeedTable3;
   float altSpeedTable4;
   float altSpeedTable5;
   float altSpeedTable6;
   float altSpeedTable7;
   float altSpeedTable8;
   static String pitchMode8Label = "Custom";

   public CustomAimSpeedModule() {
      this.modeSettings = new ModeSetting(modeLabelRu, modeCustomRu, modeCustomRu2, modeCustomRu3, modeCustomRu4, yaw0_5Label);
      this.yaw0_5Slider = new FloatSliderSetting(yaw5_10Label, yaw0_5DefaultValue, yaw0_5Min, yaw0_5Max, yaw0_5Step)
         .withSupplier(() -> this.modeSettings.is(yawMode0Label));
      this.yaw5_10Slider = new FloatSliderSetting(yaw5_10SliderLabel, yaw5_10DefaultValue, yaw5_10Min, yaw5_10Max, yaw5_10Step)
         .withSupplier(() -> this.modeSettings.is(staticModeLabel));
      this.yaw10_15Slider = new FloatSliderSetting(yaw10_15SliderLabel, yaw10_15DefaultValue, yaw10_15Min, yaw10_15Max, yaw10_15Step)
         .withSupplier(() -> this.modeSettings.is(modeCustomLabel2));
      this.field_Lsgl = new FloatSliderSetting(yaw15_20SliderLabel, yaw15_20DefaultValue, yaw15_20Min, yaw15_20Max, yaw15_20Step)
         .withSupplier(() -> this.modeSettings.is(yawMode3Label));
      this.yaw20_25Slider = new FloatSliderSetting(yaw20_25SliderLabel, yaw20_25DefaultValue, yaw20_25Min, yaw20_25Max, yaw20_25Step)
         .withSupplier(() -> this.modeSettings.is(modeCustomLabel3));
      this.field_Lsgl2 = new FloatSliderSetting(yaw25_30SliderLabel, yaw25_30DefaultValue, yaw25_30Min, yaw25_30Max, yaw25_30Step)
         .withSupplier(() -> this.modeSettings.is(modeReallyWorld));
      this.yaw30_35Slider = new FloatSliderSetting(yaw30_35SliderLabel, yaw30_35DefaultValue, yaw30_35Min, yaw30_35Max, yaw30_35Step)
         .withSupplier(() -> this.modeSettings.is(modeBravoHVH));
      this.field_Lsgl3 = new FloatSliderSetting(yaw35_40SliderLabel, yaw35_40DefaultValue, yaw35_40Min, yaw35_40Max, yaw35_40Step)
         .withSupplier(() -> this.modeSettings.is(yawMode5Label));
      this.yaw40_45Slider = new FloatSliderSetting(yaw40_45SliderLabel, yaw40_45DefaultValue, yaw40_45Min, yaw40_45Max, yaw40_45Step)
         .withSupplier(() -> this.modeSettings.is(modeCustomLabel4));
      this.pitch0_5Slider = new FloatSliderSetting(pitch0_5SliderLabel, pitch0_5DefaultValue, pitch0_5Min, pitch0_5Max, pitch0_5Step)
         .withSupplier(() -> this.modeSettings.is(pitchMode8Label));
      this.pitch5_10Slider = new FloatSliderSetting(pitch5_10SliderLabel, pitch5_10DefaultValue, pitch5_10Min, pitch5_10Max, pitch5_10Step)
         .withSupplier(() -> this.modeSettings.is(yawMode6Label));
      this.field_Lsgl4 = new FloatSliderSetting(pitch10_15SliderLabel, pitch10_15DefaultValue, pitch10_15Min, pitch10_15Max, pitch10_15Step)
         .withSupplier(() -> this.modeSettings.is(pitchMode0Label));
      this.pitch15_20Slider = new FloatSliderSetting(pitch15_20SliderLabel, pitch15_20DefaultValue, pitch15_20Min, pitch15_20Max, pitch15_20Step)
         .withSupplier(() -> this.modeSettings.is(modeNameEn));
      this.pitch20_25Slider = new FloatSliderSetting(pitch20_25SliderLabel, pitch20_25DefaultValue, pitch20_25Min, pitch20_25Max, pitch20_25Step)
         .withSupplier(() -> this.modeSettings.is(yawMode2Label));
      this.pitch25_30Slider = new FloatSliderSetting(pitch25_30SliderLabel, pitch25_30DefaultValue, pitch25_30Min, pitch25_30Max, pitch25_30Step)
         .withSupplier(() -> this.modeSettings.is(modeCustom));
      this.pitch30_35Slider = new FloatSliderSetting(pitch30_35SliderLabel, pitch30_35DefaultValue, pitch30_35Min, pitch30_35Max, pitch30_35Step)
         .withSupplier(() -> this.modeSettings.is(modeNameRu));
      this.pitch35_40Slider = new FloatSliderSetting(pitch35_40SliderLabel, pitch35_40DefaultValue, pitch35_40Min, pitch35_40Max, pitch35_40Step)
         .withSupplier(() -> this.modeSettings.is(yawMode4Label));
      this.field_Lsgl5 = new FloatSliderSetting(pitch40_45SliderLabel, pitch40_45DefaultValue, pitch40_45Min, pitch40_45Max, pitch40_45Step)
         .withSupplier(() -> this.modeSettings.is(yawMode1Label));
      this.yawSliders = new FloatSliderSetting[]{
         this.yaw0_5Slider,
         this.yaw5_10Slider,
         this.yaw10_15Slider,
         this.field_Lsgl,
         this.yaw20_25Slider,
         this.field_Lsgl2,
         this.yaw30_35Slider,
         this.field_Lsgl3,
         this.yaw40_45Slider
      };
      this.pitchSliders = new FloatSliderSetting[]{
         this.pitch0_5Slider,
         this.pitch5_10Slider,
         this.field_Lsgl4,
         this.pitch15_20Slider,
         this.pitch20_25Slider,
         this.pitch25_30Slider,
         this.pitch30_35Slider,
         this.pitch35_40Slider,
         this.field_Lsgl5
      };
      this.addSettings(new AbstractModuleSetting[]{this.modeSettings});
      Arrays.stream(this.yawSliders).toList().forEach(abstractmodulesetting -> this.addSettings(new AbstractModuleSetting[]{abstractmodulesetting}));
      Arrays.stream(this.pitchSliders).toList().forEach(abstractmodulesetting -> this.addSettings(new AbstractModuleSetting[]{abstractmodulesetting}));
   }

   private float getSpeedByYaw(float f) {
      int i = (int)(f / yawBracketStep);
      if (i >= this.yawSliders.length) {
         i = this.yawSliders.length - 1;
      }

      if (i < 0) {
         i = 0;
      }

      return this.yawSliders[i].get().floatValue();
   }

   private void applyAttackSpeed(MutableVec2f mutablevec2f, float f, float f1) {
      float f2 = this.normalizeAngle(MathHelper.wrapDegrees(f));
      float f3 = this.normalizeAngle(Math.abs(f1));
      float f4 = this.getSpeedByYaw(f2);
      float f5 = this.getSpeedByPitch(f3);
      if (f5 > f4) {
         f4 = f5;
      }

      mutablevec2f.setX(f4);
      mutablevec2f.setY(f5);
   }

   private float getPitchTableValue(float f) {
      float f1 = this.normalizeAngle(f);
      int i = (int)(f1 / pitchBracketStep);
      float[] afloat = new float[]{
         yawSpeedTable0, yawSpeedTable1, yawSpeedTable2, yawSpeedTable3, yawSpeedTable4, yawSpeedTable5, yawSpeedTable6, yawSpeedTable7, yawSpeedTable8
      };
      if (i >= afloat.length) {
         i = afloat.length - 1;
      }

      if (i < 0) {
         i = 0;
      }

      return afloat[i];
   }

   private void applyAttackSpeedAlt(MutableVec2f mutablevec2f, float f, float f1) {
      float f2 = this.normalizeAngle(MathHelper.wrapDegrees(f));
      float f3 = this.normalizeAngle(Math.abs(f1));
      float f4 = this.getAltSpeedTableValue(f2);
      float f5 = this.getAltSpeedByPitch(f3);
      if (f5 > f4) {
         f4 = f5;
      }

      mutablevec2f.setX(f4);
      mutablevec2f.setY(f5);
   }

   private void applyThirdSpeedTable(MutableVec2f mutablevec2f, float f, float f1) {
      float f2 = this.normalizeAngle(MathHelper.wrapDegrees(f));
      float f3 = this.normalizeAngle(Math.abs(f1));
      float f4 = this.getThirdSpeedByYaw(f2);
      float f5 = this.getPitchTableValue(f3);
      if (f5 > f4) {
         f4 = f5;
      }

      mutablevec2f.setX(f4);
      mutablevec2f.setY(f5);
   }

   private float getSpeedByPitch(float f) {
      int i = (int)(f / pitchBracketStepAlt);
      if (i >= this.pitchSliders.length) {
         i = this.pitchSliders.length - 1;
      }

      if (i < 0) {
         i = 0;
      }

      return this.pitchSliders[i].get().floatValue();
   }

   private float getAltSpeedTableValue(float f) {
      int i = (int)(f / pitchStepForPitchTable);
      float[] afloat = new float[]{
         pitchSpeedTable0,
         pitchSpeedTable1,
         pitchSpeedTable2,
         pitchSpeedTable3,
         pitchSpeedTable4,
         pitchSpeedTable5,
         pitchSpeedTable6,
         pitchSpeedTable7,
         pitchSpeedTable8
      };
      if (i >= afloat.length) {
         i = afloat.length - 1;
      }

      if (i < 0) {
         i = 0;
      }

      return afloat[i];
   }

   private float normalizeAngle(float f) {
      float f1 = Math.abs(f);
      if (f1 > angleNormThreshold1) {
         f1 = angleNormThreshold2 - f1;
      }

      if (f1 > angleNormThreshold3) {
         f1 = angleNormMirrorBase - f1;
      }

      return f1;
   }

   private float getThirdSpeedByYaw(float f) {
      float f1 = this.normalizeAngle(f);
      int i = (int)(f1 / thirdSpeedTable0);
      float[] afloat = new float[]{
         thirdSpeedTable1,
         thirdSpeedTable2,
         thirdSpeedTable3,
         thirdSpeedTable4,
         thirdSpeedTable5,
         thirdSpeedTable6,
         thirdSpeedTable7,
         thirdSpeedTable8,
         thirdBracketStep
      };
      if (i >= afloat.length) {
         i = afloat.length - 1;
      }

      if (i < 0) {
         i = 0;
      }

      return afloat[i];
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof MutableVec2f) {
         MutableVec2f mutablevec2f = (MutableVec2f)event;
         float f = MathHelper.wrapDegrees(mc.player.rotationYaw);
         float f1 = mc.player.rotationPitch;
         String s = this.modeSettings.getValue();
         switch (s) {
            case modeReallyWorldLabel:
               this.applyAttackSpeedAlt(mutablevec2f, f, f1);
               break;
            case modeCustomAltLabel:
               this.applyAttackSpeed(mutablevec2f, f, f1);
               break;
            case modeBravoHVHLabel:
               this.applyThirdSpeedTable(mutablevec2f, f, f1);
         }
      }
   }

   private float getAltSpeedByPitch(float f) {
      int i = (int)(f / pitchStepAlt);
      float[] afloat = new float[]{
         altSpeedTable0, altSpeedTable1, altSpeedTable2, altSpeedTable3, altSpeedTable4, altSpeedTable5, altSpeedTable6, altSpeedTable7, altSpeedTable8
      };
      if (i >= afloat.length) {
         i = afloat.length - 1;
      }

      if (i < 0) {
         i = 0;
      }

      return afloat[i];
   }
}
