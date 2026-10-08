package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;
import sg.PlayerEntity;
import sg.Entity;
import sg.ClientTickEvent;
import sg.Box;
import sg.MinecraftClient;

public class HitboxExpandModule extends Module implements McContextHolder {
   static MinecraftClient mc;
   FloatSliderSetting sizeSlider;
   static float EXPAND_MULTIPLIER = 2.5F;
   static String SETTING_NAME_RU = "Размер";
   static float DEFAULT_MIN = 0.15F;
   static float DEFAULT_VALUE = 0.15F;
   static float DEFAULT_STEP = 0.05F;
   static String SETTING_NAME_EN = "Size";

   private Box createExpandedBox(Entity entity, float f) {
      double d0 = entity.getPosX() - f;
      double d1 = entity.getBoundingBox().minY;
      double d2 = entity.getPosZ() - f;
      double d3 = entity.getPosX() + f;
      double d4 = entity.getBoundingBox().maxY;
      double d5 = entity.getPosZ() + f;
      return new Box(d0, d1, d2, d3, d4, d5);
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent) {
         if (mc.player == null) {
            return;
         }

         float f = this.sizeSlider.get().floatValue() * EXPAND_MULTIPLIER;

         for (PlayerEntity м3 : mc.world.getPlayers()) {
            if (!this.shouldSkipPlayer(м3)) {
               м3.setBoundingBox(this.createExpandedBox(м3, f));
            }
         }
      }
   }

   public HitboxExpandModule() {
      this.sizeSlider = new FloatSliderSetting(SETTING_NAME_RU, DEFAULT_MIN, DEFAULT_VALUE, 1.0F, DEFAULT_STEP, SETTING_NAME_EN);
      this.addSettings(new AbstractModuleSetting[]{this.sizeSlider});
   }

   private boolean shouldSkipPlayer(PlayerEntity м3) {
      return м3 == mc.player || !м3.isAlive();
   }
}
