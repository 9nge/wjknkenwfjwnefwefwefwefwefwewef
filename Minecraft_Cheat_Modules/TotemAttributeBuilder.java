package catlavan.module.combat;

import sg.SgClass180;
import sg.SgClass188;
import sg.ItemStack;
import sg.Items;

public class TotemAttributeBuilder {
   static String ATTR_ID_KEY;
   static String ATTR_ATTACK_DAMAGE = "minecraft:generic.attack_damage";
   static String ATTR_SLOT_KEY;
   static String ATTR_SLOT_OFFHAND = "offhand";
   static String ATTR_AMOUNT_KEY;
   static double ATTACK_DAMAGE_VALUE = 6.0;
   static String ATTR_OPERATION_KEY;
   static String ATTR_UUID_KEY;
   static int ATK_UUID_P3 = 33333;
   static int ATK_UUID_P4 = 44444;
   static String ATTR_NAME_KEY;
   static String ATTR_MAX_HEALTH = "minecraft:generic.max_health";
   static String HP_SLOT_KEY;
   static double MAX_HEALTH_VALUE = -4.0;
   static String HP_OPERATION_KEY;
   static String HP_SLOT_OFFHAND = "offhand";
   static String HP_UUID_KEY;
   static int HP_UUID_P1 = 55555;
   static int HP_UUID_P2 = 66666;
   static int HP_UUID_P3 = 77777;
   static int HP_UUID_P4 = 88888;
   static String ATTR_ARMOR_TOUGHNESS;
   static String TOUGH_AMOUNT_KEY = "minecraft:generic.armor_toughness";
   static String TOUGH_SLOT_OFFHAND;
   static double ARMOR_TOUGHNESS_VALUE = -2.0;
   static String TOUGH_OPERATION_KEY;
   static String TOUGH_SLOT_KEY = "offhand";
   static String TOUGH_UUID_KEY;
   static int TOUGH_UUID_P1 = 99999;
   static String ATTR_ARMOR;
   static String ARMOR_AMOUNT_KEY = "minecraft:generic.armor";
   static String ARMOR_SLOT_KEY;
   static double ARMOR_VALUE = -2.0;
   static String ARMOR_OPERATION_KEY;
   static String ARMOR_SLOT_OFFHAND = "offhand";
   static String ARMOR_UUID_KEY;
   static String ATTRIBUTES_NBT_KEY;

   public static ItemStack buildTotemStack() {
      ItemStack itemStack = new ItemStack(Items.TOTEM_OF_UNDYING);
      SgClass180 ллxxx = new SgClass180();
      ллxxx.putString(ATTR_ID_KEY, ATTR_ATTACK_DAMAGE);
      ллxxx.putString(ATTR_SLOT_KEY, ATTR_SLOT_OFFHAND);
      ллxxx.putDouble(ATTR_AMOUNT_KEY, ATTACK_DAMAGE_VALUE);
      ллxxx.putInt(ATTR_OPERATION_KEY, 0);
      ллxxx.putIntArray(ATTR_UUID_KEY, new int[]{11111, 22222, ATK_UUID_P3, ATK_UUID_P4});
      SgClass180 ллx = new SgClass180();
      ллx.putString(ATTR_NAME_KEY, ATTR_MAX_HEALTH);
      ллx.putDouble(HP_SLOT_KEY, MAX_HEALTH_VALUE);
      ллx.putString(HP_OPERATION_KEY, HP_SLOT_OFFHAND);
      ллx.putIntArray(HP_UUID_KEY, new int[]{HP_UUID_P1, HP_UUID_P2, HP_UUID_P3, HP_UUID_P4});
      SgClass180 ллxx = new SgClass180();
      ллxx.putString(ATTR_ARMOR_TOUGHNESS, TOUGH_AMOUNT_KEY);
      ллxx.putDouble(TOUGH_SLOT_OFFHAND, ARMOR_TOUGHNESS_VALUE);
      ллxx.putString(TOUGH_OPERATION_KEY, TOUGH_SLOT_KEY);
      ллxx.putIntArray(TOUGH_UUID_KEY, new int[]{TOUGH_UUID_P1, 10101, 12121, 13131});
      SgClass180 ллxxx = new SgClass180();
      ллxxx.putString(ATTR_ARMOR, ARMOR_AMOUNT_KEY);
      ллxxx.putDouble(ARMOR_SLOT_KEY, ARMOR_VALUE);
      ллxxx.putString(ARMOR_OPERATION_KEY, ARMOR_SLOT_OFFHAND);
      ллxxx.putIntArray(ARMOR_UUID_KEY, new int[]{14141, 15151, 16161, 17171});
      SgClass188 мк = new SgClass188();
      мк.add(ллxxx);
      мк.add(ллx);
      мк.add(ллxx);
      мк.add(ллxxx);
      itemStack.getOrCreateTag().put(ATTRIBUTES_NBT_KEY, мк);
      return itemStack;
   }
}
