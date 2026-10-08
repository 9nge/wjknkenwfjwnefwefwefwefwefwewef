package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.McContextHolder;
import sg.SgClass190;
import sg.Enchantments;
import sg.MathHelper;
import sg.SgClass261;
import sg.SgClass294;
import sg.Entity;
import sg.EnchantmentHelper;
import sg.SgClass380;
import sg.ClientTickEvent;
import sg.ItemStack;
import sg.ItemEntity;
import sg.Items;
import sg.MinecraftClient;

public class ItemSnapModule extends Module implements McContextHolder {
   String AURA_HUNTER_NAME;
   String AURA_STRENGTH_NAME;
   String AURA_TELEPORT_NAME;
   String AURA_RUNNER_NAME;
   String AURA_PROTECTION_FALL_NAME;
   String AURA_PROTECTION_CRYSTALS_NAME;
   MinecraftClient mc;
   static String BALL_DISPLAY_NAME = "Шар";
   static String SKULL_ITEM_TAG = "Skull";
   BooleanSetting skullOption = new BooleanSetting(BALL_DISPLAY_NAME, true, SKULL_ITEM_TAG);
   static String ELYTRA_DISPLAY_NAME = "Элитра";
   static String ELYTRA_ITEM_TAG = "Elytra";
   BooleanSetting elytraOption = new BooleanSetting(ELYTRA_DISPLAY_NAME, true, ELYTRA_ITEM_TAG);
   static String SHARD_DISPLAY_NAME = "Осколок";
   static String SHARD_ITEM_TAG = "Shard";
   BooleanSetting shardOption = new BooleanSetting(SHARD_DISPLAY_NAME, true, SHARD_ITEM_TAG);
   static String SHARPNESS_DISPLAY_NAME = "Острота VI";
   static String SHARPNESS_ITEM_TAG = "Sharpness VI";
   BooleanSetting sharpnessOption = new BooleanSetting(SHARPNESS_DISPLAY_NAME, false, SHARPNESS_ITEM_TAG);
   static String ANTI_FLIGHT_DISPLAY_NAME = "Анти полет";
   static String ANTI_FLIGHT_ITEM_TAG = "Anti flight";
   BooleanSetting antiFightOption = new BooleanSetting(ANTI_FLIGHT_DISPLAY_NAME, false, ANTI_FLIGHT_ITEM_TAG);
   static String AURA_DISPLAY_NAME = "Аура";
   static String AURA_ITEM_TAG = "Aura";
   BooleanSetting auraOption = new BooleanSetting(AURA_DISPLAY_NAME, true, AURA_ITEM_TAG);
   String shardFilterName;
   String antiFightFilterName;
   double YAW_TO_DEG;
   double YAW_OFFSET;
   double PITCH_TO_DEG;

   private boolean isAuraItem(ItemStack itemStack, String s) {
      return itemStack.getItem() == Items.SUNFLOWER && s.contains(AURA_HUNTER_NAME)
         || itemStack.getItem() == Items.CLAY_BALL && s.contains(AURA_STRENGTH_NAME)
         || itemStack.getItem() == Items.POPPED_CHORUS_FRUIT && s.contains(AURA_TELEPORT_NAME)
         || itemStack.getItem() == Items.GOLD_NUGGET && s.contains(AURA_RUNNER_NAME)
         || itemStack.getItem() == Items.WHITE_DYE && s.contains(AURA_PROTECTION_FALL_NAME)
         || itemStack.getItem() == Items.GHAST_TEAR && s.contains(AURA_PROTECTION_CRYSTALS_NAME);
   }

   private void snapRotationTo(Entity entity) {
      float[] afloat = this.calcAngles(entity);
      mc.player.rotationYaw = afloat[0];
      mc.player.rotationPitch = afloat[1];
   }

   public ItemSnapModule() {
      this.addSettings(
         new AbstractModuleSetting[]{this.skullOption, this.elytraOption, this.shardOption, this.sharpnessOption, this.antiFightOption, this.auraOption}
      );
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof ClientTickEvent && mc.player != null && mc.world != null) {
         for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof ItemEntity) {
               ItemEntity ItemEntity = (ItemEntity)entity;
               ItemStack itemStack = ItemEntity.getItem();
               String s = itemStack.getDisplayName().getString();
               if (this.skullOption.getValue() && itemStack.getItem() instanceof SgClass380) {
                  this.snapRotationTo(entity);
               } else if (this.elytraOption.getValue() && itemStack.getItem() instanceof SgClass261) {
                  this.snapRotationTo(entity);
               } else if (this.shardOption.getValue() && itemStack.getItem() == Items.GHAST_TEAR && s.contains(shardFilterName)) {
                  this.snapRotationTo(entity);
               } else if (this.sharpnessOption.getValue()
                  && (itemStack.getItem() instanceof SgClass190 || itemStack.getItem() instanceof SgClass294)
                  && EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemStack) >= 6) {
                  this.snapRotationTo(entity);
               } else if (this.antiFightOption.getValue() && itemStack.getItem() == Items.FIREWORK_STAR && s.contains(antiFightFilterName)) {
                  this.snapRotationTo(entity);
               } else if (this.auraOption.getValue() && this.isAuraItem(itemStack, s)) {
                  this.snapRotationTo(entity);
               }
            }
         }
      }
   }

   private float[] calcAngles(Entity entity) {
      double d0 = entity.getPosX() - mc.player.getPosX();
      double d1 = entity.getPosY() - mc.player.getPosY() - 1.0;
      double d2 = entity.getPosZ() - mc.player.getPosZ();
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(MathHelper.atan2(d2, d0) * YAW_TO_DEG - YAW_OFFSET);
      float f1 = (float)(-MathHelper.atan2(d1, d3) * PITCH_TO_DEG);
      return new float[]{f, f1};
   }
}
