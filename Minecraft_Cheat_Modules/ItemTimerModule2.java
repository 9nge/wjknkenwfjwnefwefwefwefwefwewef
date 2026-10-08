package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.config.MultiBoxSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.module.player.ThrowableItemTypeEnum;
import catlavan.util.AnimatedRunnable;
import catlavan.util.McContextHolder;
import catlavan.util.SoundUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import sg.Item;

public class ItemTimerModule2 extends Module implements McContextHolder {
   static MultiBoxSetting itemTypeConfig;
   HashMap cooldownMap;
   float COOLDOWN_SCALE;
   static String NAME_RU = "Только в PVP";
   static String NAME_EN = "Only in pvp";
   BooleanSetting pvpOnlyToggle = new BooleanSetting(NAME_RU, false, NAME_EN);
   static FloatSliderSetting gAppleSetting;
   static FloatSliderSetting pearlSetting;
   static FloatSliderSetting enchAppleSetting;
   static FloatSliderSetting chorusSetting;
   String CATEGORY_ITEMS_RU;
   String CATEGORY_ITEMS_EN;
   String GAPPLE_NAME_RU;
   String GAPPLE_NAME_EN;
   String PEARL_NAME_EN;
   String ENCH_APPLE_NAME_EN;
   String CHORUS_NAME_EN;
   String GAPPLE_LABEL_RU;
   String GAPPLE_TIME_LABEL;
   String PEARL_LABEL_RU;
   String PEARL_TIME_LABEL;
   float GAPPLE_DEFAULT_CD;
   float GAPPLE_MIN_CD;
   float GAPPLE_MAX_CD;
   float GAPPLE_STEP;
   String PEARL_LABEL_EN;
   String ENCH_APPLE_LABEL_RU;
   float PEARL_DEFAULT_CD;
   float PEARL_MAX_CD;
   float PEARL_STEP;
   String PEARL_TIME_LABEL_2;
   String ENCH_APPLE_LABEL_EN;
   float ENCH_APPLE_DEFAULT_CD;
   float ENCH_APPLE_MAX_CD;
   float ENCH_APPLE_STEP;
   String ENCH_APPLE_TIME_LABEL;
   String CHORUS_LABEL_RU;
   float CHORUS_DEFAULT_CD;
   float CHORUS_MAX_CD;
   float CHORUS_STEP;
   String CHORUS_TIME_LABEL;

   public boolean checkItemEnabled(ThrowableItemTypeEnum throwableitemtypeenum) {
      return !throwableitemtypeenum.к.get()
         ? false
         : (Boolean)throwableitemtypeenum.к.get()
            && Arrays.stream(ThrowableItemTypeEnum.values()).anyMatch(throwableitemtypeenum1 -> throwableitemtypeenum1 == throwableitemtypeenum);
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof AnimatedRunnable) {
         AnimatedRunnable animatedrunnable = (AnimatedRunnable)event;
         ArrayList arraylist = new ArrayList();

         for (Entry entry : this.cooldownMap.entrySet()) {
            ThrowableItemTypeEnum throwableitemtypeenum = ThrowableItemTypeEnum.method874(((Item)entry.getKey()).getDefaultInstance());
            if (throwableitemtypeenum != null
               && animatedrunnable.target == throwableitemtypeenum.к
               && (Boolean)throwableitemtypeenum.к.get()
               && !this.isPvpOnlyAndNotInPvp()) {
               long i = System.currentTimeMillis() - (Long)entry.getValue();
               float f = (Float)throwableitemtypeenum.Hand.get() * COOLDOWN_SCALE;
               if ((float)i < f && (Boolean)throwableitemtypeenum.к.get()) {
                  animatedrunnable.method7784((float)i / f);
               } else {
                  arraylist.add(throwableitemtypeenum.к);
               }
            }
         }

         arraylist.forEach(this.cooldownMap::remove);
      }
   }

   public ItemTimerModule2() {
      this.cooldownMap = new HashMap();
      this.addSettings(new AbstractModuleSetting[]{itemTypeConfig, gAppleSetting, pearlSetting, enchAppleSetting, chorusSetting, this.pvpOnlyToggle});
   }

   public static void method8021() {
      itemTypeConfig = new MultiBoxSetting(
         CATEGORY_ITEMS_RU,
         CATEGORY_ITEMS_EN,
         new BooleanSetting(GAPPLE_NAME_RU, true, GAPPLE_NAME_EN),
         new BooleanSetting(PEARL_NAME_EN, true, ENCH_APPLE_NAME_EN),
         new BooleanSetting(CHORUS_NAME_EN, true, GAPPLE_LABEL_RU),
         new BooleanSetting(GAPPLE_TIME_LABEL, true, PEARL_LABEL_RU)
      );
      gAppleSetting = new FloatSliderSetting(PEARL_TIME_LABEL, GAPPLE_DEFAULT_CD, GAPPLE_MIN_CD, GAPPLE_MAX_CD, GAPPLE_STEP, PEARL_LABEL_EN)
         .withSupplier(() -> itemTypeConfig.isCheckedByIndex(0));
      pearlSetting = new FloatSliderSetting(ENCH_APPLE_LABEL_RU, PEARL_DEFAULT_CD, 2.0F, PEARL_MAX_CD, PEARL_STEP, PEARL_TIME_LABEL_2)
         .withSupplier(() -> itemTypeConfig.isCheckedByIndex(1));
      enchAppleSetting = new FloatSliderSetting(ENCH_APPLE_LABEL_EN, ENCH_APPLE_DEFAULT_CD, 2.0F, ENCH_APPLE_MAX_CD, ENCH_APPLE_STEP, ENCH_APPLE_TIME_LABEL)
         .withSupplier(() -> itemTypeConfig.isCheckedByIndex(2));
      chorusSetting = new FloatSliderSetting(CHORUS_LABEL_RU, CHORUS_DEFAULT_CD, 2.0F, CHORUS_MAX_CD, CHORUS_STEP, CHORUS_TIME_LABEL)
         .withSupplier(() -> itemTypeConfig.isCheckedByIndex(3));
   }

   public boolean isPvpOnlyAndNotInPvp() {
      return this.pvpOnlyToggle.getValue() && !SoundUtil.isPvpActive();
   }
}
