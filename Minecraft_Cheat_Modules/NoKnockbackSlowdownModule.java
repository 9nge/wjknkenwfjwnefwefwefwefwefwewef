package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;

public class NoKnockbackSlowdownModule extends Module implements McContextHolder {
   static String NAME_RU = "Не замедлять после удара";
   static String NAME_EN = "does not slow down when hit";
   BooleanSetting enabled = new BooleanSetting(NAME_RU, true, NAME_EN);

   public NoKnockbackSlowdownModule() {
      this.addSettings(new AbstractModuleSetting[]{this.enabled});
   }

   @Override
   public void onEvent(Event event) {
   }
}
