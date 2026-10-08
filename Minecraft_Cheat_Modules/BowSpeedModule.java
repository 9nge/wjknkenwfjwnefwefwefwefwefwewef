package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;
import sg.ClientTickEvent;
import sg.Hand;
import sg.SgClass536;
import sg.PlayerTryUseItemC2SPacket;
import sg.MinecraftClient;

public class BowSpeedModule extends Module implements McContextHolder {
   static MinecraftClient mc;
   FloatSliderSetting speedSetting;
   static String settingNameRu = "Скорость";
   static float settingMin = 3.0F;
   static float settingStep = 0.05F;
   static String settingNameEn = "Speed";

   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent) {
         ClientTickEvent ClientTickEvent = (ClientTickEvent)event;
         if (mc.player.inventory.getCurrentItem().getItem() instanceof SgClass536
            && mc.player.isHandActive()
            && mc.player.getItemInUseMaxCount() > this.speedSetting.get().floatValue()) {
            mc.playerController.onStoppedUsingItem(mc.player);
            mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.MAIN_HAND));
         }
      }
   }

   public BowSpeedModule() {
      this.speedSetting = new FloatSliderSetting(settingNameRu, 2.0F, 2.0F, settingMin, settingStep, settingNameEn);
      this.addSettings(new AbstractModuleSetting[]{this.speedSetting});
   }
}
