// Module: Auto Explosion
// Category: Combat
// Original obfuscated class: sg.ec.Т必 (Т必.java)
package sg.ec.modules.combat;

import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1542;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_4969;

public class AutoExplosion extends Module {
   static class_310 I;
   Ь н2;
   class_2338 М;
   int ШO;
   boolean н6;
   boolean н_;
   double 衣Г;
   class_238 Э;
   static String 衣4 = "Auto Explosion";
   static String 衣ц = "Автоматичeски взрывает кристаллы";
   static String 衣И = "Не взрывать себя";
   Ь нI = new Ь(衣И, true);
   static String 衣西 = "Не взрывать, если рядом предметы";
   static String 衣1 = "Оставлять кристал в руке после того как поставил";
   Ь нь;
   double 衣Х;
   float 衣ж;
   boolean н9;
   int Шл;
   double 衣0;
   float 衣>;

   public void н(Ъ var1) {
      if (var1.И() == class_2246.field_10540) {
         if (I.field_1724.method_7357().method_7904(class_1802.field_8301.method_7854())) {
            return;
         }

         if ((Boolean)this.н2.о() && this.н(var1.7())) {
            return;
         }

         int var2 = this.н(class_1802.field_8301, true);
         if (var2 == -1) {
            return;
         }

         this.М = var1.7();
         this.ШO = var2;
      } else if (var1.И() instanceof class_4969) {
         int var3 = this.н(class_1802.field_8801, true);
         if (var3 == -1) {
            return;
         }

         this.М = var1.7();
         this.ШO = var3;
         this.н6 = true;
      }

      this.н_ = true;
   }

   private boolean н(class_2338 var1) {
      class_238 var2 = new class_238(var1).method_1014(1.0);

      for (class_1297 var4 : I.field_1687.method_18467(class_1297.class, var2)) {
         if (var4 instanceof class_1542) {
            return true;
         }
      }

      return false;
   }

   private void н(Фр var1) {
      if (!I.field_1724.method_7357().method_7904(class_1802.field_8301.method_7854())) {
         class_2248 var2 = var1.а().method_8320(var1.6().method_17777()).method_26204();
         if (var2 == class_2246.field_10540 || var2 == class_2246.field_9987) {
            this.Э = new class_238(var1.6().method_17777().method_10084()).method_1014(衣Г);
         }
      }
   }

   public AutoExplosion() {
      super(衣4, 衣ц, Пй.Combat);
      this.н2 = new Ь(衣西, false);
      this.нь = new Ь(衣1, false);
      this.ф(new ФЮ[]{this.нI, this.н2, this.нь});
   }

   private void р(Ц var1) {
      if (this.Э != null) {
         for (class_1297 var3 : I.field_1687.method_18112()) {
            if (var3 instanceof class_1511 && this.Э.method_1006(var3.method_19538())) {
               if ((Boolean)this.нI.о()) {
                  double var4 = I.field_1724.method_23318();
                  double var6 = var3.method_23318();
                  if (Math.abs(var4 - var6) < 1.0) {
                     this.Э = null;
                     return;
                  }
               }

               class_2338 var8 = class_2338.method_49638(this.Э.method_1005()).method_10074();
               if ((Boolean)this.н2.о() && this.н(var8)) {
                  this.Э = null;
                  return;
               }

               if (!var3.method_5829().method_1006(I.field_1724.method_5836(I.method_61966().method_60637(true)))) {
                  class_243 var5 = Т0.5(var3);
                  float var9 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var5.field_1350, var5.field_1352)) - 衣Х);
                  float var7 = (float)(-Math.toDegrees(Math.atan2(var5.field_1351, Math.hypot(var5.field_1352, var5.field_1350))));
                  С8.И(new 2щ(var9, var7), 衣ж, 1, 6);
               }

               I.field_1761.method_2918(I.field_1724, var3);
               I.field_1724.method_6104(class_1268.field_5808);
               this.Э = null;
               return;
            }
         }
      }
   }

   private void П(Ц var1) {
      if (this.н9) {
         this.н9 = false;
         I.field_1724.method_31548().field_7545 = this.Шл;
         ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
      } else if (this.М != null) {
         if (I.field_1687.method_8320(this.М).method_26215()) {
            this.М = null;
         } else if (this.н_) {
            this.н_ = false;
         } else {
            class_243 var2 = I.field_1724.method_5836(I.method_61966().method_60637(true));
            class_243 var3 = Т0.5(var2, new class_238(this.М));
            class_243 var4 = var3.method_1020(var2);
            float var5 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var4.field_1350, var4.field_1352)) - 衣0);
            float var6 = (float)(-Math.toDegrees(Math.atan2(var4.field_1351, Math.hypot(var4.field_1352, var4.field_1350))));
            С8.И(new 2щ(var5, var6), 衣>, 1, 6);
            this.Шл = I.field_1724.method_31548().field_7545;
            I.field_1724.method_31548().field_7545 = this.ШO;
            ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
            var4 = var4.method_22882();
            I.field_1761
               .method_2896(
                  I.field_1724,
                  class_1268.field_5808,
                  new class_3965(var3, class_2350.method_10147((float)var4.field_1352, (float)var4.field_1351, (float)var4.field_1350), this.М, false)
               );
            I.field_1724.method_6104(class_1268.field_5808);
            this.н9 = true;
            if (this.н6) {
               this.ШO = this.Шл;
               this.н6 = false;
            } else {
               this.М = null;
               if ((Boolean)this.нь.о()) {
                  this.н9 = false;
               }
            }
         }
      }
   }

   public int н(class_1792 var1, boolean var2) {
      byte var3 = 0;
      int var4 = var2 ? 9 : 36;
      int var5 = -1;

      for (int var6 = var3; var6 < var4; var6++) {
         if (I.field_1724.method_31548().method_5438(var6).method_7909() == var1) {
            var5 = var6;
         }
      }

      return var5;
   }
}
