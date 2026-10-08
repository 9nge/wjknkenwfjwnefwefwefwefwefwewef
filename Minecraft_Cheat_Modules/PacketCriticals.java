// Module: Packet Criticals
// Category: Combat
// Original obfuscated class: sg.ec.Пф (ПФ.java)
package sg.ec.modules.combat;

import java.lang.constant.Constable;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_2828.class_2829;
import net.minecraft.class_2828.class_2830;

public class PacketCriticals extends Module implements Constable, Comparable {
   ТЙ 4;
   static String 5ье = "Packet Criticals";
   static String 5ь衣 = "Наносит критические удары без надобности прыгать";
   static String 5ьВ = "Режим";
   static String 5ьЗ = "Post";
   static String 5ьк = "Post";
   static String 5ьЪ = "Old Holyworld";
   static String 5ь6 = "KrystalMC";
   static String 5ь9 = "Grim";
   static class_310 I;
   static String 5ь_ = "Old Holyworld";
   double 5ь<;
   static String 5ьЫ = "KrystalMC";
   double 5ь8;
   static String 5ьЬ = "Grim";
   boolean нл;
   float 5ьщ;
   float 5ьб;

   public ТЙ я() {
      return this.4;
   }

   public PacketCriticals() {
      super(5ье, 5ь衣, Пй.Combat);
      this.4 = new ТЙ(5ьВ, 5ьЗ, 5ьк, 5ьЪ, 5ь6, 5ь9);
      this.ф(new ФЮ[]{this.4});
   }

   private void Я(ПЧ var1) {
      if (I.field_1724 != null && I.method_1562() != null) {
         if (this.4.Я(5ь_)) {
            I.method_1562()
               .method_52787(new class_2829(I.field_1724.method_23317(), I.field_1724.method_23318() - 5ь<, I.field_1724.method_23321(), false, false));
         } else if (this.4.Я(5ьЫ)) {
            if (this.Юп() || I.field_1724.method_6059(class_1294.field_5902)) {
               I.method_1562()
                  .method_52787(new class_2829(I.field_1724.method_23317(), I.field_1724.method_23318() - 5ь8, I.field_1724.method_23321(), false, false));
            }
         } else {
            if (this.4.Я(5ьЬ) && !нл) {
               class_1297 var2 = var1.в();
               if (var2 == null || var2 instanceof class_1511) {
                  return;
               }

               this.Ж>();
            }
         }
      }
   }

   private void Ж_/* $VF was: Ж>*/() {
      Й var1 = Ч.getInstance().getModuleManager().ю();
      Тм var2 = (Тм)Ч.getInstance().getModuleManager().ь(Тм.class);
      class_1309 var3 = var1 != null && var1.7() ? sg.ec.Й.П() : null;
      class_1309 var4 = var2 != null && var2.7() ? Тм.Н : null;
      boolean var5 = var3 != null || var4 != null;
      if (var5) {
         if (!I.field_1724.method_24828()) {
            double var6 = I.field_1724.method_23318();
            if (var6 != (double)((int)var6)) {
               boolean var8 = this.Юп();
               boolean var9 = I.field_1724.method_5771();
               if (var8 || var9) {
                  float var10 = ThreadLocalRandom.current().nextFloat() * 5ьщ + 5ьб;
                  I.field_1724.field_6017 = var10;
                  I.method_1562()
                     .method_52787(
                        new class_2830(
                           I.field_1724.method_23317(),
                           I.field_1724.method_23318() - (double)var10,
                           I.field_1724.method_23321(),
                           I.field_1724.method_36454(),
                           I.field_1724.method_36455(),
                           false,
                           false
                        )
                     );
               }
            }
         }
      }
   }

   private boolean Юп() {
      if (I.field_1724 != null && I.field_1687 != null) {
         class_238 var1 = I.field_1724.method_5829();

         for (class_2338 var3 : class_2338.method_10094(
            class_3532.method_15357(var1.field_1323),
            class_3532.method_15357(var1.field_1322),
            class_3532.method_15357(var1.field_1321),
            class_3532.method_15357(var1.field_1320),
            class_3532.method_15357(var1.field_1325),
            class_3532.method_15357(var1.field_1324)
         )) {
            if (I.field_1687.method_8320(var3).method_27852(class_2246.field_10343)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean ЖЦ() {
      return this.7() && I != null && I.field_1724 != null && (I.field_1724.method_6059(class_1294.field_5906) || this.Юп());
   }
}
