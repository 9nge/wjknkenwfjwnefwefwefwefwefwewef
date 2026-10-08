package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;

public class PredictModule extends Module implements McContextHolder {
   static FloatSliderSetting setting;
   String name;
   float minValue;
   float maxValue;
   String label;

   @Override
   public void onEvent(Event event) {
   }

   public PredictModule() {
      this.addSettings(new AbstractModuleSetting[]{setting});
   }

   public static void initSetting() {
      setting = new FloatSliderSetting(name, minValue, 1.0F, maxValue, 1.0F, label);
   }
}
