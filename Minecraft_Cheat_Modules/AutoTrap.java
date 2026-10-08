// Module: Auto Trap
// Category: Combat
// Original obfuscated class: sg.ec.I (I.java)
package sg.ec.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.class_1268;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;

public class AutoTrap extends Module implements Supplier {
   static class_310 I;
   Ь д;
   double Ыт;
   double ЫМ;
   double Ыя;
   boolean К;
   boolean Л;
   class_2338 Ш;
   class_2350 н;
   boolean Ф;
   boolean У;
   List 2;
   int 7;
   List I;
   ТЙ I;
   String ЫК;
   String ЫУ;
   Ь Щ;
   double Ыс;
   float Ып;
   static String <о = "Auto Trap";
   static String Ы5 = "Автоматически закрывает человека в паутину/блоки";
   static String Ын = "Режим";
   static String ЫШ = "Одиночный";
   static String ЫЭ = "Одиночный";
   static String ЫI = "Мульти";
   ТЙ Э = new ТЙ(Ын, ЫШ, ЫЭ, ЫI);
   static String Ы2 = "Блоки";
   static String Ыь = "Паутина";
   static String Ым = "Паутина";
   static String Ыф = "Обсидиан";
   static String ЫЕ = "Оба";
   static String Ыв = "Дистанция";
   static float ЫЩ = 4.0F;
   static float Ыд = 8.0F;
   static float Ы7 = 0.5F;
   Ч3 Е;
   static String ЫН = "Прыгать для верха";
   static String ЫР = "Работать из инвентаря";
   static String ЫТ = "Кнопка установки";
   ФМ I;
   double ЫЛ;
   double ЫФ;
   double Ыз;
   String Ый;
   static String Ыл = "Обсидиан";
   static String ЫЙ = "Оба";
   String ЫП;
   String ЫO;
   String ЫО;
   String ЫД;
   String Ыа;
   String ЫС;

   private boolean Д(class_1792 var1) {
      return var1 == class_1802.field_8281 || var1 == class_1802.field_22421;
   }

   private void Д(class_2338 var1, class_2350 var2, boolean var3) {
      if (I.field_1724 != null && I.field_1687 != null && I.field_1761 != null) {
         int var4 = I.field_1724.method_31548().field_7545;
         boolean var6 = false;
         int var7 = -1;
         int var8 = -1;
         int var5;
         if (var3) {
            int var9 = this.м();
            if (var9 != -1) {
               var5 = var9;
            } else {
               if (!(Boolean)this.д.о()) {
                  return;
               }

               var7 = this.Е();
               if (var7 == -1) {
                  return;
               }

               var8 = this.Р(var4);
               this.Д(var7, var8);
               var5 = var8;
               var6 = true;
            }
         } else {
            int var11 = this.б();
            if (var11 != -1) {
               var5 = var11;
            } else {
               if (!(Boolean)this.д.о()) {
                  return;
               }

               var7 = this.к();
               if (var7 == -1) {
                  return;
               }

               var8 = this.Р(var4);
               this.Д(var7, var8);
               var5 = var8;
               var6 = true;
            }
         }

         if (var5 != var4) {
            П4.б(var5);
         }

         class_2338 var12 = var1.method_10093(var2);
         class_243 var10 = class_243.method_24953(var12)
            .method_1031(
               (double)var2.method_10153().method_10148() * Ыт,
               (double)var2.method_10153().method_10164() * ЫМ,
               (double)var2.method_10153().method_10165() * Ыя
            );
         I.field_1761.method_2896(I.field_1724, class_1268.field_5808, new class_3965(var10, var2.method_10153(), var12, false));
         I.field_1724.method_6104(class_1268.field_5808);
         if (var5 != var4) {
            П4.б(var4);
         }

         if (var6 && var7 != -1 && var8 != -1) {
            this.Д(var7, var8);
         }
      }
   }

   private boolean _/* $VF was: >*/(class_2338 var1) {
      return I.field_1687.method_8320(var1).method_45474();
   }

   @Override
   public void Щ() {
      this.ъ();
      this.К = false;
      super.Щ();
   }

   private int к() {
      for (int var1 = 9; var1 < 36; var1++) {
         class_1799 var2 = I.field_1724.method_31548().method_5438(var1);
         if (!var2.method_7960() && this.Д(var2.method_7909())) {
            return var1;
         }
      }

      return -1;
   }

   private boolean зД() {
      return this.м() != -1 || this.Е() != -1;
   }

   private boolean Ь(class_2338 var1) {
      double var2 = I.field_1724.method_33571().field_1351;
      return (double)var1.method_10264() >= var2;
   }

   private void Д(int var1, int var2) {
      if (I.field_1724 != null && I.field_1761 != null) {
         int var3 = I.field_1724.field_7512.field_7763;
         int var4 = var1 < 9 ? var1 + 36 : var1;
         I.field_1761.method_2906(var3, var4, var2, class_1713.field_7791, I.field_1724);
      }
   }

   private boolean Д(class_2338 var1, List var2) {
      for (class_1657 var4 : var2) {
         class_2338 var5 = var4.method_24515();
         if (var1.equals(var5) || var1.equals(var5.method_10084())) {
            return true;
         }
      }

      return false;
   }

   public void Х(Ц var1) {
      if (I.field_1724 != null && I.field_1687 != null && I.field_1761 != null) {
         if (!this.К) {
            this.ъ();
         } else if (!this.з>()) {
            this.ъ();
         } else if (this.Л && this.Ш != null && this.н != null) {
            if (!this.Ф) {
               this.Д(this.Ш, this.н, this.У);
               this.2.add(this.Ш);
               this.Ш = null;
               this.н = null;
               this.Л = false;
               this.7++;
            } else {
               if (!I.field_1724.method_24828() && I.field_1724.method_18798().field_1351 > 0.0) {
                  this.Д(this.Ш, this.н, this.У);
                  this.2.add(this.Ш);
                  this.Ш = null;
                  this.н = null;
                  this.Л = false;
                  this.Ф = false;
                  this.7++;
               } else if (I.field_1724.method_24828()) {
                  I.field_1724.method_6043();
               }
            }
         } else {
            List var2 = this.х();
            if (var2.isEmpty()) {
               this.ъ();
            } else {
               if (this.I.isEmpty() || this.7 >= this.I.size()) {
                  this.I.clear();
                  this.2.clear();
                  this.7 = 0;

                  for (class_1657 var4 : var2) {
                     for (class_2338 var6 : this.Р((class_1309)var4)) {
                        if (!this.I.contains(var6)) {
                           this.I.add(var6);
                        }
                     }
                  }
               }

               while (this.7 < this.I.size()) {
                  class_2338 var9 = (class_2338)this.I.get(this.7);
                  if (!this.>(var9)) {
                     this.7++;
                  } else {
                     boolean var10 = this.Д(var9, var2);
                     boolean var11 = var10 && (this.I.Я(ЫК) || this.I.Я(ЫУ)) && this.зД();
                     class_2350 var12 = this.Р(var9);
                     if (var12 != null) {
                        this.Ф = (Boolean)this.Щ.о() && !var11 && this.Ь(var9);
                        this.Р(var9, var12, var11);
                        if (this.Ф && I.field_1724.method_24828()) {
                           I.field_1724.method_6043();
                        }

                        return;
                     }

                     class_2338 var7 = var9.method_10074();
                     if (this.>(var7) && !this.2.contains(var7)) {
                        class_2350 var8 = this.Р(var7);
                        if (var8 != null) {
                           this.Р(var7, var8, false);
                           this.I.add(this.7, var7);
                           return;
                        }
                     }

                     this.7++;
                  }
               }

               this.I.clear();
               this.7 = 0;
            }
         }
      } else {
         this.ъ();
      }
   }

   private void ъ() {
      this.Л = false;
      this.Ф = false;
      this.Ш = null;
      this.н = null;
      this.I.clear();
      this.2.clear();
      this.7 = 0;
   }

   private int Р(int var1) {
      return (var1 + 1) % 9;
   }

   private void Д(class_243 var1) {
      class_243 var2 = I.field_1724.method_5836(I.method_61966().method_60637(true));
      class_243 var3 = var1.method_1020(var2);
      float var4 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var3.field_1350, var3.field_1352)) - Ыс);
      float var5 = (float)(-Math.toDegrees(Math.atan2(var3.field_1351, Math.hypot(var3.field_1352, var3.field_1350))));
      С8.И(new 2щ(var4, var5), Ып, 1, 6);
   }

   public AutoTrap() {
      super(<о, Ы5, Пй.Combat);
      this.I = new ТЙ(Ы2, Ыь, Ым, Ыф, ЫЕ);
      this.Е = new Ч3(Ыв, ЫЩ, 1.0F, Ыд, Ы7);
      this.Щ = new Ь(ЫН, true, () -> this.I.Я(Ыл) || this.I.Я(ЫЙ));
      this.д = new Ь(ЫР, false);
      this.I = new ФМ(ЫТ);
      this.К = false;
      this.I = new ArrayList();
      this.2 = new ArrayList();
      this.7 = 0;
      this.Л = false;
      this.Ф = false;
      this.ф(new ФЮ[]{this.Э, this.I, this.Е, this.Щ, this.д, this.I});
   }

   private void Р(class_2338 var1, class_2350 var2, boolean var3) {
      this.Ш = var1;
      this.н = var2;
      this.У = var3;
      this.Л = true;
      class_2338 var4 = var1.method_10093(var2);
      class_243 var5 = class_243.method_24953(var4)
         .method_1031(
            (double)var2.method_10153().method_10148() * ЫЛ, (double)var2.method_10153().method_10164() * ЫФ, (double)var2.method_10153().method_10165() * Ыз
         );
      this.Д(var5);
   }

   private boolean Р(class_1792 var1) {
      return var1 == class_1802.field_8786;
   }

   private int б() {
      for (int var1 = 0; var1 < 9; var1++) {
         class_1799 var2 = I.field_1724.method_31548().method_5438(var1);
         if (!var2.method_7960() && this.Д(var2.method_7909())) {
            return var1;
         }
      }

      return -1;
   }

   private class_2350 Р(class_2338 var1) {
      for (class_2338 var3 : this.2) {
         for (class_2350 var7 : class_2350.values()) {
            if (var1.method_10093(var7).equals(var3)) {
               return var7;
            }
         }
      }

      for (class_2350 var11 : class_2350.values()) {
         class_2338 var12 = var1.method_10093(var11);
         class_2680 var13 = I.field_1687.method_8320(var12);
         if (!var13.method_26215() && (var13.method_26212(I.field_1687, var12) || var13.method_27852(class_2246.field_10343))) {
            return var11;
         }
      }

      return null;
   }

   private int м() {
      for (int var1 = 0; var1 < 9; var1++) {
         class_1799 var2 = I.field_1724.method_31548().method_5438(var1);
         if (!var2.method_7960() && this.Р(var2.method_7909())) {
            return var1;
         }
      }

      return -1;
   }

   private int Е() {
      for (int var1 = 9; var1 < 36; var1++) {
         class_1799 var2 = I.field_1724.method_31548().method_5438(var1);
         if (!var2.method_7960() && this.Р(var2.method_7909())) {
            return var1;
         }
      }

      return -1;
   }

   private List х() {
      class_243 var1 = I.field_1724.method_19538();
      double var2 = (double)((Float)this.Е.о()).floatValue();
      class_238 var4 = new class_238(
         var1.field_1352 - var2, var1.field_1351 - var2, var1.field_1350 - var2, var1.field_1352 + var2, var1.field_1351 + var2, var1.field_1350 + var2
      );
      List var5 = I.field_1687
         .method_8390(
            class_1657.class, var4, var0 -> var0.method_5805() && var0 != I.field_1724 && !Ч.getInstance().getFriendManager().>(var0.method_5477().getString())
         );
      var5.sort(Comparator.comparingDouble(var1x -> var1x.method_5707(var1)));
      if (this.Э.Я(Ый)) {
         return var5.isEmpty() ? List.of() : List.of((class_1657)var5.getFirst());
      } else {
         return var5;
      }
   }

   public void Ь(э var1) {
      if (I.field_1724 != null && I.field_1755 == null) {
         if ((Integer)this.I.о() != -1 && var1.М() == (Integer)this.I.о()) {
            this.К = var1.8л();
            if (!this.К) {
               this.ъ();
            }
         }
      }
   }

   private boolean з_/* $VF was: з>*/() {
      if (this.I.Я(ЫП)) {
         return this.зД();
      } else {
         return this.I.Я(ЫO) ? this.зЬ() : this.зД() || this.зЬ();
      }
   }

   private boolean зЬ() {
      return this.б() != -1 || this.к() != -1;
   }

   private List Р(class_1309 var1) {
      class_2338 var2 = var1.method_24515();
      class_2338 var3 = var2.method_10084();
      class_2338 var4 = var3.method_10084();
      ArrayList var5 = new ArrayList();
      if (this.I.Я(ЫО) || this.I.Я(ЫД)) {
         if (this.>(var2)) {
            var5.add(var2);
         }

         if (this.>(var3)) {
            var5.add(var3);
         }
      }

      if (this.I.Я(Ыа) || this.I.Я(ЫС)) {
         class_2338 var6 = var4.method_10084();
         ArrayList var7 = new ArrayList();
         var7.add(var2.method_10095());
         var7.add(var2.method_10072());
         var7.add(var2.method_10078());
         var7.add(var2.method_10067());
         var7.add(var3.method_10095());
         var7.add(var3.method_10072());
         var7.add(var3.method_10078());
         var7.add(var3.method_10067());
         var7.add(var4);
         var7.add(var6);

         for (class_2338 var9 : var7) {
            if (this.>(var9) && !var5.contains(var9)) {
               var5.add(var9);
            }
         }
      }

      return var5;
   }
}
