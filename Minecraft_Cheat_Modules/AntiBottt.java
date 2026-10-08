// Module: Anti Bot
// Category: Combat
// Original obfuscated class: sg.ec.Тр (ТР.java)
package sg.ec.modules.combat;

import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.stream.StreamSupport;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_742;
import net.minecraft.class_9334;
import sLM.4ZXYEo;

public class AntiBot extends Module implements BooleanSupplier {
   String 55ш;
   String 55А;
   static Set Р;
   static class_310 I;
   ТЙ И;
   String 55ъ;
   String 55Ю;
   String 55l;
   static String 55Й = "Anti Bot";
   static String 55и = "Определяет и метит ботов, вызванных системой античита";
   static String 55弟 = "Мод";
   static String 55у = "Matrix";
   static String 55必 = "Matrix";
   static String 55Б = "UniAC";
   static String 55х = "Reallyworld";

   private boolean Я(class_1657 var1) {
      if (2т.4(55ш) && 2т.4(55А)) {
         boolean var2 = ((class_1799)var1.method_31548().field_7548.get(0)).method_7909() == class_1802.field_8162;
         boolean var3 = ((class_1799)var1.method_31548().field_7548.get(1)).method_7909() == class_1802.field_8162;
         boolean var4 = ((class_1799)var1.method_31548().field_7548.get(2)).method_7909() == class_1802.field_8162;
         boolean var5 = ((class_1799)var1.method_31548().field_7548.get(3)).method_7909() == class_1802.field_8162;
         boolean var6 = ((class_1799)var1.method_31548().field_7548.get(0)).method_7909() != class_1802.field_8162;
         boolean var7 = ((class_1799)var1.method_31548().field_7548.get(1)).method_7909() != class_1802.field_8162;
         boolean var8 = ((class_1799)var1.method_31548().field_7548.get(2)).method_7909() != class_1802.field_8162;
         boolean var9 = ((class_1799)var1.method_31548().field_7548.get(3)).method_7909() != class_1802.field_8162;
         class_1799 var10 = (class_1799)var1.method_31548().field_7548.get(0);
         class_1799 var11 = (class_1799)var1.method_31548().field_7548.get(1);
         class_1799 var12 = (class_1799)var1.method_31548().field_7548.get(2);
         class_1799 var13 = (class_1799)var1.method_31548().field_7548.get(3);
         boolean var14 = !var10.method_7960() && var10.method_57826(class_9334.field_49633);
         boolean var15 = !var11.method_7960() && var11.method_57826(class_9334.field_49633);
         boolean var16 = !var12.method_7960() && var12.method_57826(class_9334.field_49633);
         boolean var17 = !var13.method_7960() && var13.method_57826(class_9334.field_49633);
         boolean var18 = var2 || var6 && !var14;
         boolean var19 = var3 || var7 && !var15;
         boolean var20 = var4 || var8 && !var16;
         boolean var21 = var5 || var9 && !var17;
         boolean var22 = var1.method_6096() == 0;
         boolean var23 = Ч.getInstance().getFriendManager().>(var1.method_7334().getName());
         boolean var24 = !var10.method_7960()
            && !var11.method_7960()
            && !var12.method_7960()
            && !var13.method_7960()
            && var10.method_7919() > 0
            && var11.method_7919() > 0
            && var12.method_7919() > 0
            && var13.method_7919() > 0;
         String var25 = var1.method_5477().getString();
         boolean var26 = var25.length() == 6;
         boolean var27 = var18 && var19 && var20 && var21;
         return var26 && !var23 && !var22 && !var24 && var27;
      } else {
         return false;
      }
   }

   private void l(class_1657 var1, boolean var2) {
      if (var2) {
         Р.add(var1.method_5667());
      } else {
         Р.remove(var1.method_5667());
      }
   }

   public static boolean З(class_1657 var0) {
      return Р.contains(var0.method_5667());
   }

   @Override
   public void Щ() {
      Р.clear();
      super.Щ();
   }

   public void м(Тт var1) {
      class_1297 var3 = var1.и();
      if (var3 instanceof class_1657) {
         class_1657 var2 = (class_1657)var3;
         if (var2 == I.field_1724) {
            return;
         }

         this.l(var2, this.И(var2));
      }
   }

   private boolean И(class_1297 var1) {
      if (var1 instanceof class_742) {
         class_742 var2 = (class_742)var1;
         if (I.field_1724 == null || var2 == I.field_1724) {
            return false;
         } else if (this.И.Я(55ъ)) {
            return !var2.method_5667()
               .equals(UUID.nameUUIDFromBytes(4ZXYEo.弟ш<"makeConcatWithConstants">(var2.method_5477().getString()).getBytes(StandardCharsets.UTF_8)));
         } else if (this.И.Я(55Ю)) {
            return this.Я(var2);
         } else {
            GameProfile var3 = var2.method_7334();
            String var4 = var3.getName();
            if (var4.contains(55l)) {
               return true;
            } else if (var2.method_7325()) {
               return true;
            } else {
               boolean var5 = StreamSupport.<class_1799>stream(var2.method_5661().spliterator(), false).anyMatch(var0 -> !var0.method_7960());
               boolean var6 = var3.getProperties().isEmpty();
               return var6 && var5 ? true : var6;
            }
         }
      } else {
         return false;
      }
   }

   public static void ЖЛ() {
      Р = new HashSet();
   }

   public AntiBot() {
      super(55Й, 55и, Пй.Combat);
      this.И = new ТЙ(55弟, 55у, 55必, 55Б, 55х);
      this.ф(new ФЮ[]{this.И});
   }

   public void 西(М var1) {
      Р.clear();
   }

   @Override
   public void ц() {
      if (I.field_1687 != null) {
         for (class_1657 var2 : I.field_1687.method_18456()) {
            if (var2 != I.field_1724) {
               this.l(var2, this.И(var2));
            }
         }
      }

      super.ц();
   }

   static {
      55ы = "OfflinePlayer:\u0001";
   }
}
