package catlavan.module.combat;

import sg.SgClass180;
import sg.SgClass188;
import sg.ItemStack;
import sg.Items;
import sg.SgInterface040;

public class TotemAttributeItemCreator implements SgInterface040 {
   static String ATTR_KEY_ATK;
   static String ATTR_ATK_DAMAGE = "minecraft:generic.attack_damage";
   static String ATTR_KEY_SLOT;
   static String ATTR_ATK_SLOT = "offhand";
   static String ATTR_MAX_HEALTH;
   static double ATTR_ATK_AMOUNT = 7.0;
   static String ATTR_KEY_OPERATION;
   static String ATTR_HEALTH_KEY;
   static String ATTR_HEALTH_VALUE = "minecraft:generic.max_health";
   static String ATTR_HEALTH_SLOT;
   static double ATTR_HEALTH_AMOUNT = -4.0;
   static String ATTR_HEALTH_UUID_KEY;
   static String ATTR_HEALTH_UUID_VAL = "offhand";
   static String ATTR_SPEED;
   static String ATTR_SPEED_KEY = "minecraft:generic.movement_speed";
   static String ATTR_SPEED_VALUE;
   static double ATTR_SPEED_AMOUNT = 0.1;
   static String ATTR_SPEED_OPERATION_KEY;
   static String ATTR_SPEED_SLOT_KEY;
   static String ATTR_SPEED_SLOT = "offhand";
   static String ATTR_LIST_KEY;

   public static ItemStack createTotemWithAttributes() {
      ItemStack itemStack = new ItemStack(Items.TOTEM_OF_UNDYING);
      SgClass180 ллxx = new SgClass180();
      ллxx.putString(ATTR_KEY_ATK, ATTR_ATK_DAMAGE);
      ллxx.putString(ATTR_KEY_SLOT, ATTR_ATK_SLOT);
      ллxx.putDouble(ATTR_MAX_HEALTH, ATTR_ATK_AMOUNT);
      ллxx.putInt(ATTR_KEY_OPERATION, 0);
      SgClass180 ллx = new SgClass180();
      ллx.putString(ATTR_HEALTH_KEY, ATTR_HEALTH_VALUE);
      ллx.putDouble(ATTR_HEALTH_SLOT, ATTR_HEALTH_AMOUNT);
      ллx.putString(ATTR_HEALTH_UUID_KEY, ATTR_HEALTH_UUID_VAL);
      SgClass180 ллxx = new SgClass180();
      ллxx.putString(ATTR_SPEED, ATTR_SPEED_KEY);
      ллxx.putDouble(ATTR_SPEED_VALUE, ATTR_SPEED_AMOUNT);
      ллxx.putInt(ATTR_SPEED_OPERATION_KEY, 1);
      ллxx.putString(ATTR_SPEED_SLOT_KEY, ATTR_SPEED_SLOT);
      SgClass188 мк = new SgClass188();
      мк.add(ллxx);
      мк.add(ллx);
      мк.add(ллxx);
      itemStack.getOrCreateTag().put(ATTR_LIST_KEY, мк);
      return itemStack;
   }
}
