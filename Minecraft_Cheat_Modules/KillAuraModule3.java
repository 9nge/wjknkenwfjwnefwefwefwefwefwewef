package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.config.FloatSliderSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PacketEntry;
import catlavan.util.BiConsumerImpl;
import catlavan.util.InventoryUtil;
import catlavan.util.McContextHolder;
import sg.SgClass066;
import sg.SgClass090;
import sg.Vec3d;
import sg.SgClass389;
import sg.ClientTickEvent;
import sg.EquipmentSlotType;
import sg.ItemStack;
import sg.Items;
import sg.Item;
import sg.MinecraftClient;

public class KillAuraModule3 extends Module implements McContextHolder {
   static MinecraftClient mc;
   BooleanSetting elytraChecker;
   int swapCooldownTicks;
   Vec3d field_Lsg;
   boolean armorSwapped;
   float savedAttackRange;
   FloatSliderSetting attackDistanceSetting;
   static String ATTACK_DISTANCE_LABEL_RU = "Дистанция аттаки";
   static float ATTACK_DISTANCE_DEFAULT = 3.0F;
   static float ATTACK_DISTANCE_MIN = 3.0F;
   static float ATTACK_DISTANCE_MAX = 6.0F;
   static float ATTACK_DISTANCE_STEP = 0.1F;
   static String ATTACK_DISTANCE_LABEL = "Attack distance";
   static String SWAP_CHESTPLATE_LABEL_RU = "Свапнуть на нагрудник";
   static String SWAP_CHESTPLATE_LABEL = "Swap chestplace";
   int MIN_ARMOR_POINTS;
   static double MAX_POSITION_DRIFT = 1.0E-4;

   private void equipBestChestplate() {
      Item Item = this.getBestChestplate();
      if (Item != null) {
         InventoryUtil.equipItem(Item);
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.applyAttackDistance();
      boolean flag = ((ItemStack)mc.player.inventory.armorInventory.get(2)).getItem() == Items.ELYTRA;
      if (flag && this.elytraChecker.getValue()) {
         this.equipBestChestplate();
         this.swapCooldownTicks = 1;
         this.field_Lsg = null;
      } else {
         this.swapCooldownTicks = 0;
         this.field_Lsg = mc.player.getPositionVec();
      }
   }

   private void restoreElytra() {
      KillAuraModule killauramodule = this.getTargetEntity();
      if (killauramodule != null && this.armorSwapped) {
         killauramodule.attackRangeSlider.setValue(this.savedAttackRange);
      }

      this.armorSwapped = false;
   }

   private void applyAttackDistance() {
      KillAuraModule killauramodule = this.getTargetEntity();
      if (killauramodule != null) {
         this.savedAttackRange = killauramodule.attackRangeSlider.get().floatValue();
         this.armorSwapped = true;
         killauramodule.attackRangeSlider.setValue(this.attackDistanceSetting.get().floatValue());
      }
   }

   public KillAuraModule3() {
      this.attackDistanceSetting = new FloatSliderSetting(
         ATTACK_DISTANCE_LABEL_RU, ATTACK_DISTANCE_DEFAULT, ATTACK_DISTANCE_MIN, ATTACK_DISTANCE_MAX, ATTACK_DISTANCE_STEP, ATTACK_DISTANCE_LABEL
      );
      this.elytraChecker = new BooleanSetting(SWAP_CHESTPLATE_LABEL_RU, false, SWAP_CHESTPLATE_LABEL);
      this.swapCooldownTicks = 0;
      this.addSettings(new AbstractModuleSetting[]{this.attackDistanceSetting, this.elytraChecker});
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.field_Lsg = null;
      this.swapCooldownTicks = 0;
      this.restoreElytra();
   }

   private Item getBestChestplate() {
      Item ф9x = null;
      int i = MIN_ARMOR_POINTS;
      float f = Float.NEGATIVE_INFINITY;

      for (int j = 0; j < 36; j++) {
         ItemStack itemStack = mc.player.inventory.getStackInSlot(j);
         Item ф9x = itemStack.getItem();
         if (ф9x instanceof SgClass090) {
            SgClass090 _ь = (SgClass090)ф9x;
            if (_ь.getEquipmentSlot() == EquipmentSlotType.CHEST && ф9x != Items.ELYTRA) {
               int k = _ь.getDamageReduceAmount();
               float f1 = _ь.func_234657_f_();
               if (k > i || k == i && f1 > f) {
                  i = k;
                  f = f1;
                  ф9x = ф9x;
               }
            }
         }
      }

      return ф9x;
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof PacketEntry) {
         PacketEntry packetentry = (PacketEntry)event;
         if (packetentry.method8538() && packetentry.getPacket() instanceof SgClass389 && this.field_Lsg != null) {
            packetentry.setCancelled(true);
         }
      }

      if (event instanceof ClientTickEvent) {
         this.tickSwapLogic();
         if (this.swapCooldownTicks > 0) {
            this.swapCooldownTicks--;
            if (this.swapCooldownTicks == 0 && this.field_Lsg == null) {
               this.field_Lsg = mc.player.getPositionVec();
            }
         }

         if (this.field_Lsg != null) {
            mc.player.setMotion(Vec3d.ZERO);
            if (mc.player.getPositionVec().squareDistanceTo(this.field_Lsg) > MAX_POSITION_DRIFT) {
               this.field_Lsg = mc.player.getPositionVec();
            }

            if (mc.player.ticksExisted % 2 == 0) {
               mc.player
                  .connection
                  .sendPacket(
                     new SgClass066(this.field_Lsg.x, this.field_Lsg.y, this.field_Lsg.z, mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround())
                  );
            }
         }
      }
   }

   private void tickSwapLogic() {
      KillAuraModule killauramodule = this.getTargetEntity();
      if (killauramodule != null) {
         if (!this.armorSwapped) {
            this.savedAttackRange = killauramodule.attackRangeSlider.get().floatValue();
            this.armorSwapped = true;
         }

         killauramodule.attackRangeSlider.setValue(this.attackDistanceSetting.get().floatValue());
      }
   }

   private KillAuraModule getTargetEntity() {
      return mc.player != null && BiConsumerImpl.й != null ? BiConsumerImpl.й.module0 : null;
   }
}
