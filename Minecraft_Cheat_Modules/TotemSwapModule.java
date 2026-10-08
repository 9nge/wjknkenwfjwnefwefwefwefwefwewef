package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.config.MultiBoxSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.BlockPositionUtil;
import catlavan.util.InventoryUtil;
import catlavan.util.McContextHolder;
import catlavan.util.Timer;
import sg.SgClass105;
import sg.ClickType;
import sg.Blocks;
import sg.SgClass249;
import sg.SgClass290;
import sg.Entity;
import sg.SgClass380;
import sg.ClientTickEvent;
import sg.Effects;
import sg.ItemStack;
import sg.Items;
import sg.SgClass488;
import sg.MinecraftClient;

public class TotemSwapModule extends Module implements McContextHolder {
   static MultiBoxSetting triggerModeOption;
   BooleanSetting safeEnabled;
   static MinecraftClient healthSlider;
   static FloatSliderSetting tntDistSlider;
   static float MAX_FALL_DIST = 5.0F;
   static String HEALTH_LABEL_RU = "Здоровье";
   static float DEFAULT_HEALTH_MIN = 3.5F;
   static float DEFAULT_HEALTH_STEP = 3.0F;
   static float DEFAULT_HEALTH_MAX = SgClass017.0F;
   static float DEFAULT_HEALTH_TICK = 0.005F;
   static String HEALTH_LABEL_EN = "Health";
   FloatSliderSetting safeHealthSlider = new FloatSliderSetting(
      HEALTH_LABEL_RU, DEFAULT_HEALTH_MIN, DEFAULT_HEALTH_STEP, DEFAULT_HEALTH_MAX, DEFAULT_HEALTH_TICK, HEALTH_LABEL_EN
   );
   static String SWAP_BACK_LABEL_RU = "Свапать обратно";
   static String SWAP_BACK_LABEL_EN = "Swap back";
   BooleanSetting swapBackEnabled = new BooleanSetting(SWAP_BACK_LABEL_RU, true, SWAP_BACK_LABEL_EN);
   static String SAFE_LABEL_RU = "Безопасность";
   static String SAFE_LABEL_EN = "Save";
   BooleanSetting crystalEnabled = new BooleanSetting(SAFE_LABEL_RU, true, SAFE_LABEL_EN).setSupplier(this.swapBackEnabled::getValue);
   static String SAFE_HEALTH_LABEL_RU = "Безопасный уровень здоровья";
   static float DEFAULT_SAFE_HEALTH_MIN = 10.0F;
   static float DEFAULT_SAFE_HEALTH_STEP = 3.0F;
   static float DEFAULT_SAFE_HEALTH_MAX = SgClass017.0F;
   static float DEFAULT_SAFE_HEALTH_TICK = 0.005F;
   static String SAFE_HEALTH_LABEL_EN = "Save health";
   FloatSliderSetting crystalDistSlider = new FloatSliderSetting(
         SAFE_HEALTH_LABEL_RU, DEFAULT_SAFE_HEALTH_MIN, DEFAULT_SAFE_HEALTH_STEP, DEFAULT_SAFE_HEALTH_MAX, DEFAULT_SAFE_HEALTH_TICK, SAFE_HEALTH_LABEL_EN
      )
      .withSupplier(() -> this.swapBackEnabled.getValue() && this.crystalEnabled.getValue());
   static String NO_TAKE_BALL_LABEL_RU = "Не брать если шар";
   static String NO_TAKE_BALL_LABEL_EN = "Dont take if ball";
   static String CRYSTAL_DIST_LABEL_RU = "Расстояние Identifier кристалла";
   static float DEFAULT_CRYSTAL_DIST_MIN = 6.0F;
   static float DEFAULT_CRYSTAL_DIST_MAX = SgClass057.0F;
   static String CRYSTAL_DIST_LABEL_EN = "Distance before crystal";
   FloatSliderSetting obsidianDistSlider;
   static String OBSIDIAN_DIST_LABEL_RU = "Расстояние Identifier обсидиана";
   static float DEFAULT_OBSIDIAN_DIST_MIN = 6.0F;
   static float DEFAULT_OBSIDIAN_DIST_MAX = SgClass057.0F;
   static String OBSIDIAN_DIST_LABEL_EN = "Distance before obsidian";
   FloatSliderSetting fallDistSlider;
   static String FALL_DIST_LABEL_RU = "Расстояние падения";
   static float DEFAULT_FALL_DIST_MIN = SgClass017.0F;
   static float DEFAULT_FALL_DIST_STEP = 3.0F;
   static float DEFAULT_FALL_DIST_MAX = 50.0F;
   static float DEFAULT_FALL_DIST_TICK = 0.005F;
   static String FALL_DIST_LABEL_EN = "Distance fall";
   FloatSliderSetting elytraHealthSlider;
   static String ELYTRA_HEALTH_LABEL_RU = "Здоровье на элитрах";
   static float DEFAULT_ELYTRA_HEALTH_MIN = 6.0F;
   static float DEFAULT_ELYTRA_HEALTH_MAX = SgClass017.0F;
   static float DEFAULT_ELYTRA_HEALTH_TICK = 0.005F;
   static String ELYTRA_HEALTH_LABEL_EN = "Health in Elytra";
   FloatSliderSetting lowHpSlider;
   static String NO_TAKE_LOW_HP_LABEL_RU = "Не брать шар если мало хп";
   static String NO_TAKE_LOW_HP_LABEL_EN = "Take with ball if low HP";
   BooleanSetting takeBallLowHpEnabled;
   static String LOW_HP_TAKE_LABEL_RU = "При низком уровне HP:";
   static float DEFAULT_LOW_HP_MIN = 3.5F;
   static float DEFAULT_LOW_HP_STEP = 3.0F;
   static float DEFAULT_LOW_HP_MAX = SgClass017.0F;
   static float DEFAULT_LOW_HP_TICK = 0.005F;
   static String LOW_HP_LABEL_EN = "Take if HP low:";
   FloatSliderSetting lowHpTakeSlider;
   static String SAVE_ENCHANTED_LABEL_RU = "Сохранять зачар. тотемы";
   static String SAVE_ENCHANTED_LABEL_EN = "Save enchanted totems";
   BooleanSetting saveEnchantedEnabled;
   static String BYPASS_GRIM_LABEL_RU = "Обходить Grim";
   static String BYPASS_GRIM_LABEL_EN = "Bypass GrimAC";
   BooleanSetting bypassGrimEnabled;
   ItemStack stackBeforeSwap;
   Timer triggerModeSelector;
   boolean isSwapping;
   String TRIGGER_LABEL_RU;
   String TRIGGER_LABEL_EN;
   String ABSORPTION_OPTION;
   String CRYSTALS_OPTION;
   String OBSIDIAN_OPTION;
   String FALL_OPTION;
   String HP_ON_ELYTRA_OPTION;
   String TNT_OPTION;
   String TNT_DIST_LABEL_RU;
   String TNT_DIST_LABEL_EN;
   String HP_ELYTRA_LABEL_RU;
   String HP_ELYTRA_LABEL_EN;
   String CRYSTALS_LABEL_RU;
   String CRYSTALS_LABEL_EN;
   String OBSIDIAN_LABEL;
   float DEFAULT_TNT_DIST_MIN;
   float DEFAULT_TNT_DIST_STEP;
   float DEFAULT_TNT_DIST_MAX;
   String TNT_DIST_BEFORE_LABEL_EN;

   private boolean method4665() {
      if (!triggerModeOption.isCheckedByIndex(5)) {
         return false;
      } else {
         for (Entity entity : healthSlider.world.getAllEntities()) {
            if (entity instanceof SgClass488 && healthSlider.player.getDistance(entity) < tntDistSlider.get().floatValue()) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean shouldSwapTotem() {
      return healthSlider.player.fallDistance > MAX_FALL_DIST
         ? false
         : this.safeEnabled.getValue() && healthSlider.player.getHeldItemOffhand().getItem() instanceof SgClass380;
   }

   public TotemSwapModule() {
      this.safeEnabled = new BooleanSetting(NO_TAKE_BALL_LABEL_RU, false, NO_TAKE_BALL_LABEL_EN);
      this.obsidianDistSlider = new FloatSliderSetting(
            CRYSTAL_DIST_LABEL_RU, DEFAULT_CRYSTAL_DIST_MIN, 1.0F, DEFAULT_CRYSTAL_DIST_MAX, 1.0F, CRYSTAL_DIST_LABEL_EN
         )
         .withSupplier(() -> triggerModeOption.isCheckedByIndex(1));
      this.fallDistSlider = new FloatSliderSetting(
            OBSIDIAN_DIST_LABEL_RU, DEFAULT_OBSIDIAN_DIST_MIN, 1.0F, DEFAULT_OBSIDIAN_DIST_MAX, 1.0F, OBSIDIAN_DIST_LABEL_EN
         )
         .withSupplier(() -> triggerModeOption.isCheckedByIndex(2));
      this.elytraHealthSlider = new FloatSliderSetting(
            FALL_DIST_LABEL_RU, DEFAULT_FALL_DIST_MIN, DEFAULT_FALL_DIST_STEP, DEFAULT_FALL_DIST_MAX, DEFAULT_FALL_DIST_TICK, FALL_DIST_LABEL_EN
         )
         .withSupplier(() -> triggerModeOption.isCheckedByIndex(3));
      this.lowHpSlider = new FloatSliderSetting(
            ELYTRA_HEALTH_LABEL_RU, DEFAULT_ELYTRA_HEALTH_MIN, 1.0F, DEFAULT_ELYTRA_HEALTH_MAX, DEFAULT_ELYTRA_HEALTH_TICK, ELYTRA_HEALTH_LABEL_EN
         )
         .withSupplier(() -> triggerModeOption.isCheckedByIndex(4));
      this.takeBallLowHpEnabled = new BooleanSetting(NO_TAKE_LOW_HP_LABEL_RU, false, NO_TAKE_LOW_HP_LABEL_EN).setSupplier(() -> this.safeEnabled.getValue());
      this.lowHpTakeSlider = new FloatSliderSetting(
            LOW_HP_TAKE_LABEL_RU, DEFAULT_LOW_HP_MIN, DEFAULT_LOW_HP_STEP, DEFAULT_LOW_HP_MAX, DEFAULT_LOW_HP_TICK, LOW_HP_LABEL_EN
         )
         .withSupplier(() -> this.takeBallLowHpEnabled.getValue() && this.safeEnabled.getValue());
      this.saveEnchantedEnabled = new BooleanSetting(SAVE_ENCHANTED_LABEL_RU, false, SAVE_ENCHANTED_LABEL_EN);
      this.bypassGrimEnabled = new BooleanSetting(BYPASS_GRIM_LABEL_RU, true, BYPASS_GRIM_LABEL_EN);
      this.stackBeforeSwap = null;
      this.triggerModeSelector = new Timer();
      this.isSwapping = false;
      this.addSettings(
         new AbstractModuleSetting[]{
            triggerModeOption,
            this.safeHealthSlider,
            this.obsidianDistSlider,
            this.lowHpSlider,
            this.elytraHealthSlider,
            this.swapBackEnabled,
            this.crystalEnabled,
            this.crystalDistSlider,
            this.safeEnabled,
            this.takeBallLowHpEnabled,
            this.lowHpTakeSlider,
            this.saveEnchantedEnabled,
            tntDistSlider
         }
      );
   }

   private boolean hasElytraEquipped() {
      return !triggerModeOption.isCheckedByIndex(2) ? false : BlockPositionUtil.findNearestBlock(this.fallDistSlider.get().floatValue(), Blocks.OBSIDIAN) != null;
   }

   public boolean isCrystalNearby() {
      float f = triggerModeOption.isCheckedByIndex(0) && healthSlider.player.isPotionActive(Effects.ABSORPTION) ? healthSlider.player.getAbsorptionAmount() : 0.0F;
      if (healthSlider.player.getHealth() + f <= this.safeHealthSlider.get().floatValue()) {
         return true;
      } else {
         if (!this.shouldSwapTotem() || healthSlider.player.getHealth() + f < this.lowHpTakeSlider.get().floatValue() && this.takeBallLowHpEnabled.getValue()) {
            if (this.isTntNearby()) {
               return true;
            }

            if (this.hasElytraEquipped()) {
               return true;
            }
         }

         if (this.shouldTriggerObsidian()) {
            return true;
         } else {
            return this.method4665() ? true : this.isFallingFar();
         }
      }
   }

   private boolean shouldTriggerObsidian() {
      return !triggerModeOption.isCheckedByIndex(4)
         ? false
         : ((ItemStack)healthSlider.player.inventory.armorInventory.get(2)).getItem() == Items.ELYTRA
            && healthSlider.player.getHealth() <= this.lowHpSlider.get().floatValue();
   }

   private boolean shouldSwapBack() {
      if (!this.swapBackEnabled.getValue()) {
         return false;
      } else if (!this.crystalEnabled.getValue()) {
         return true;
      } else {
         float f = triggerModeOption.isCheckedByIndex(0) && healthSlider.player.isPotionActive(Effects.ABSORPTION)
            ? healthSlider.player.getAbsorptionAmount()
            : 0.0F;
         float f1 = healthSlider.player.getHealth() + f;
         return f1 >= this.crystalDistSlider.get().floatValue();
      }
   }

   private boolean isTntNearby() {
      if (!triggerModeOption.isCheckedByIndex(1)) {
         return false;
      } else {
         for (Entity entity : healthSlider.world.getAllEntities()) {
            if (entity instanceof SgClass249 && healthSlider.player.getDistance(entity) <= this.obsidianDistSlider.get().floatValue()) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isFallingFar() {
      return !triggerModeOption.isCheckedByIndex(3) ? false : healthSlider.player.fallDistance > this.elytraHealthSlider.get().floatValue();
   }

   @Override
   protected void onEnable() {
      super.onEnable();
   }

   @Override
   protected void onDisable() {
      super.onDisable();
   }

   public void swapSlot(int i, int j) {
      healthSlider.playerController.windowClick(0, i, 40, ClickType.SWAP, healthSlider.player);
      if (this.bypassGrimEnabled.getValue()) {
         healthSlider.player.connection.sendPacket(new SgClass105(0));
      }
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent) {
         int i = InventoryUtil.method228(Items.TOTEM_OF_UNDYING);
         boolean flag = !(healthSlider.player.getHeldItemOffhand().getItem() instanceof SgClass290);
         boolean flag1 = healthSlider.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING
            || healthSlider.player.getHeldItemMainhand().getItem() == Items.TOTEM_OF_UNDYING;
         boolean flag2 = false;
         if (this.saveEnchantedEnabled.getValue() && i >= 0) {
            for (int k = 0; k < 36; k++) {
               if (healthSlider.player.inventory.getStackInSlot(k).getItem() == Items.TOTEM_OF_UNDYING
                  && !healthSlider.player.inventory.getStackInSlot(k).isEnchanted()) {
                  int l = k;
                  if (k < 9 && k != -1) {
                     l = k + 36;
                  }

                  i = l;
                  flag2 = true;
                  break;
               }
            }
         }

         if (this.isCrystalNearby()) {
            if (i >= 0 && !flag1 || flag2 && i >= 0 && healthSlider.player.inventory.getStackInSlot(40).isEnchanted() && this.saveEnchantedEnabled.getValue()) {
               if (flag && this.stackBeforeSwap == null) {
                  this.stackBeforeSwap = healthSlider.player.inventory.getStackInSlot(40);
               }

               this.swapSlot(i, 40);
            }
         } else if (this.stackBeforeSwap != null && this.shouldSwapBack()) {
            int j = -1;

            for (int i1 = 0; i1 < 36; i1++) {
               if (healthSlider.player.inventory.getStackInSlot(i1).getItem() == this.stackBeforeSwap.getItem()) {
                  int j1 = i1;
                  if (i1 < 9 && i1 != -1) {
                     j1 = i1 + 36;
                  }

                  j = j1;
                  break;
               }
            }

            if (j != -1 && healthSlider.player.inventory.getStackInSlot(40) != this.stackBeforeSwap && this.stackBeforeSwap != null) {
               this.swapSlot(j, 40);
               this.stackBeforeSwap = null;
            }
         }
      }
   }

   public static void initStaticOptions() {
      triggerModeOption = new MultiBoxSetting(
         TRIGGER_LABEL_RU,
         TRIGGER_LABEL_EN,
         new BooleanSetting(ABSORPTION_OPTION, true, CRYSTALS_OPTION),
         new BooleanSetting(OBSIDIAN_OPTION, true, FALL_OPTION),
         new BooleanSetting(HP_ON_ELYTRA_OPTION, false, TNT_OPTION),
         new BooleanSetting(TNT_DIST_LABEL_RU, true, TNT_DIST_LABEL_EN),
         new BooleanSetting(HP_ELYTRA_LABEL_RU, true, HP_ELYTRA_LABEL_EN),
         new BooleanSetting(CRYSTALS_LABEL_RU, false, CRYSTALS_LABEL_EN)
      );
      tntDistSlider = new FloatSliderSetting(OBSIDIAN_LABEL, DEFAULT_TNT_DIST_MIN, DEFAULT_TNT_DIST_STEP, DEFAULT_TNT_DIST_MAX, 1.0F, TNT_DIST_BEFORE_LABEL_EN)
         .withSupplier(() -> triggerModeOption.isCheckedByIndex(5));
   }
}
