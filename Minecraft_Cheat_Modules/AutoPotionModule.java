package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.MultiBoxSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.BiConsumerImpl;
import catlavan.util.McContextHolder;
import catlavan.util.SmoothValueAnimation;
import catlavan.util.SoundUtil;
import catlavan.util.Timer;
import java.util.List;
import sg.Effect;
import sg.PlayerEntity;
import sg.ClickType;
import sg.SgClass334;
import sg.SgEnum021;
import sg.ClientTickEvent;
import sg.Effects;
import sg.ItemStack;
import sg.Items;
import sg.Hand;
import sg.SgClass518;
import sg.PlayerTryUseItemC2SPacket;
import sg.MinecraftClient;

public class AutoPotionModule extends Module implements McContextHolder {
   boolean needsThrow = false;
   static MinecraftClient mc;
   static BooleanSetting onlyInPvpOption;
   boolean isThrowing = false;
   Timer throwCooldownTimer;
   long THROW_DELAY_MS;
   float LOOK_PITCH;
   static BooleanSetting strengthOption;
   static BooleanSetting speedOption;
   static Timer scanCooldownTimer;
   static long SCAN_INTERVAL_MS = 1000L;
   static MultiBoxSetting settings;
   static BooleanSetting fireResistanceOption;
   String MODULE_NAME_RU;
   String MODULE_NAME_EN;
   String STRENGTH_OPTION_NAME;
   String SPEED_OPTION_NAME;
   String FIRE_RESISTANCE_OPTION_NAME;
   String ONLY_IN_PVP_OPTION_NAME;
   String ONLY_IN_PVP_DESC_RU;
   String ONLY_IN_PVP_DESC_EN;
   String NEAR_PROT4_OPTION_NAME_RU;
   String NEAR_PROT4_OPTION_NAME_EN;
   String AUTO_DISABLE_OPTION_NAME_RU;
   String AUTO_DISABLE_OPTION_NAME_EN;
   String ONLY_ON_GROUND_OPTION_NAME_RU;
   String ONLY_ON_GROUND_OPTION_NAME_EN;
   String NEAR_PROT4_DESC_RU;
   String NEAR_PROT4_DESC_EN;

   @Override
   public void onEvent(Event event) {
      if (event instanceof SmoothValueAnimation) {
         SmoothValueAnimation smoothvalueanimation = (SmoothValueAnimation)event;
         if (this.needsThrow) {
            if (!mc.player.isOnGround() && onlyInPvpOption.getValue()) {
               return;
            }

            this.isThrowing = true;
            throwCooldownTimer.reset();
            this.needsThrow = false;
         }

         if (this.isThrowing && !throwCooldownTimer.hasElapsed(THROW_DELAY_MS)) {
            smoothvalueanimation.setStartValue(LOOK_PITCH);
         } else {
            this.isThrowing = false;
         }
      }

      if (event instanceof ClientTickEvent) {
         this.needsThrow = false;
         boolean flag4 = false;
         boolean flag = false;
         boolean flag1 = false;
         if (!SoundUtil.isPvpActive() && strengthOption.getValue()) {
            boolean flag2 = true;
            if (speedOption.getValue()) {
               for (PlayerEntity м3 : mc.world.getPlayers()) {
                  if (м3 != mc.player && !BiConsumerImpl.й.contains(м3.getName().getString())) {
                     if (((ItemStack)м3.inventory.armorInventory.get(0)).getItem() == Items.NETHERITE_BOOTS) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(1)).getItem() == Items.NETHERITE_LEGGINGS) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(2)).getItem() == Items.NETHERITE_CHESTPLATE) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(3)).getItem() == Items.NETHERITE_HELMET) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(0)).getItem() == Items.DIAMOND_BOOTS) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(1)).getItem() == Items.DIAMOND_LEGGINGS) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(2)).getItem() == Items.DIAMOND_CHESTPLATE) {
                        flag2 = false;
                        break;
                     }

                     if (((ItemStack)м3.inventory.armorInventory.get(3)).getItem() == Items.DIAMOND_HELMET) {
                        flag2 = false;
                        break;
                     }
                  }
               }
            }

            if (flag2) {
               return;
            }
         }

         if (mc.player.isInWater() || mc.player.isElytraFlying()) {
            return;
         }

         if (scanCooldownTimer.hasElapsed(SCAN_INTERVAL_MS)) {
            for (int i = 0; i < 36; i++) {
               ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
               if (itemStack.getItem() instanceof SgEnum021) {
                  List list = SgClass334.getEffectsFromStack(itemStack);
                  boolean flag3 = false;

                  for (SgClass518 potionEffect : list) {
                     if (potionEffect.getPotion() == Effect.get(5) && !mc.player.isPotionActive(Effects.STRENGTH) && settings.isCheckedByIndex(0)) {
                        if (!flag4) {
                           flag3 = true;
                        }

                        if (this.isThrowing) {
                           flag4 = true;
                        }
                        break;
                     }

                     if (potionEffect.getPotion() == Effect.get(1) && !mc.player.isPotionActive(Effects.SPEED) && settings.isCheckedByIndex(1)) {
                        if (!flag) {
                           flag3 = true;
                        }

                        if (this.isThrowing) {
                           flag = true;
                        }
                        break;
                     }

                     if (potionEffect.getPotion() == Effect.get(12) && !mc.player.isPotionActive(Effects.FIRE_RESISTANCE) && settings.isCheckedByIndex(2)) {
                        if (!flag1) {
                           flag3 = true;
                        }

                        if (this.isThrowing) {
                           flag1 = true;
                        }
                        break;
                     }
                  }

                  if (flag3) {
                     this.needsThrow = true;
                     if (this.isThrowing) {
                        mc.playerController.windowClick(0, i < 9 ? i + 36 : i, 40, ClickType.SWAP, mc.player);
                        mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.OFF_HAND));
                        mc.playerController.windowClick(0, i < 9 ? i + 36 : i, 40, ClickType.SWAP, mc.player);
                     }
                  }
               }
            }

            if (this.isThrowing) {
               scanCooldownTimer.reset();
            }
         }

         if (fireResistanceOption.getValue() && this.isThrowing) {
            this.toggle();
         }
      }
   }

   public AutoPotionModule() {
      this.addSettings(new AbstractModuleSetting[]{settings, strengthOption, speedOption, fireResistanceOption, onlyInPvpOption});
   }

   public static void initOptions() {
      settings = new MultiBoxSetting(
         MODULE_NAME_RU,
         MODULE_NAME_EN,
         new BooleanSetting(STRENGTH_OPTION_NAME, true, SPEED_OPTION_NAME),
         new BooleanSetting(FIRE_RESISTANCE_OPTION_NAME, true, ONLY_IN_PVP_OPTION_NAME),
         new BooleanSetting(ONLY_IN_PVP_DESC_RU, true, ONLY_IN_PVP_DESC_EN)
      );
      strengthOption = new BooleanSetting(NEAR_PROT4_OPTION_NAME_RU, false, NEAR_PROT4_OPTION_NAME_EN);
      speedOption = new BooleanSetting(AUTO_DISABLE_OPTION_NAME_RU, false, AUTO_DISABLE_OPTION_NAME_EN).setSupplier(() -> strengthOption.getValue());
      fireResistanceOption = new BooleanSetting(ONLY_ON_GROUND_OPTION_NAME_RU, true, ONLY_ON_GROUND_OPTION_NAME_EN);
      onlyInPvpOption = new BooleanSetting(NEAR_PROT4_DESC_RU, true, NEAR_PROT4_DESC_EN);
      scanCooldownTimer = new Timer();
      throwCooldownTimer = new Timer();
   }

   public void onTick() {
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.needsThrow = false;
      this.isThrowing = false;
   }
}
