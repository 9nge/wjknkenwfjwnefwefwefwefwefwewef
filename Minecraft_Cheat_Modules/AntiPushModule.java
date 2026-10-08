package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.MultiBoxSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;
import sg.ClientTickEvent;

public class AntiPushModule extends Module implements McContextHolder {
   static MultiBoxSetting targetConfig;
   String MODULE_NAME_RU;
   String MODULE_NAME;
   String OPT_PLAYERS;
   String OPT_BLOCKS;
   String OPT_NAME3;
   String OPT_NAME4;
   String OPT_NAME5;

   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent) {
      }
   }

   public AntiPushModule() {
      this.addSettings(new AbstractModuleSetting[]{targetConfig});
   }

   public static void initConfig() {
      targetConfig = new MultiBoxSetting(
         MODULE_NAME_RU,
         MODULE_NAME,
         new BooleanSetting(OPT_PLAYERS, true, OPT_BLOCKS),
         new BooleanSetting(OPT_NAME3, true, OPT_NAME4),
         new BooleanSetting(OPT_NAME5, true, "")
      );
   }
}
