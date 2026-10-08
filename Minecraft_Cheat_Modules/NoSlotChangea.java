// Module: No Slot Change
// Category: Combat
// Original obfuscated class: sg.ec.Пс (ПС.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;
import net.minecraft.class_10192;
import net.minecraft.class_1304;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1738;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2596;
import net.minecraft.class_2735;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_476;
import net.minecraft.class_481;
import net.minecraft.class_495;
import net.minecraft.class_9279;
import net.minecraft.class_9334;

public class NoSlotChange extends Module implements Supplier {
   static class_310 I;
   Фи М;
   static String ар = "Не выкидывать элитру";
   static String а4 = "Не выкидывать нагрудник";
   static String ац = "Не выкидывать шар";
   static String аИ = "Не выкидывать осколки";
   String а1;
   String а0;
   static String а西 = "Не свапать слоты";
   static String аl = "No Slot Change";
   static String аш = "Исправляет ошибки, связанные со сменой предметов в инвентаре";
   static String аА = "На что работать";
   static String аЯ = "Не свапать слоты";
   static String аЧ = "Не выкидывать элитру";
   static String аЖ = "Не выкидывать нагрудник";
   static String а诶 = "Не выкидывать шар";
   static String аЦ = "Не выкидывать осколки";
   String ач;

   private boolean _/* $VF was: 4*/(class_1799 var1) {
      if (var1 == null || var1.method_7960()) {
         return false;
      } else if (!var1.method_57826(class_9334.field_54196)) {
         return false;
      } else {
         class_10192 var2 = (class_10192)var1.method_57824(class_9334.field_54196);
         return var2 != null && var2.comp_3174() == class_1304.field_6174;
      }
   }

   public void ЯЖ(Ц var1) {
      if (!(I.field_1755 instanceof class_476) && !(I.field_1755 instanceof class_481) && !(I.field_1755 instanceof class_495)) {
         class_1799 var2 = I.field_1724.field_7512.method_34255();
         int var3 = П4.Из();
         boolean var4 = var3 != -1;
         if (this.М.4(ар) && П4.Е(class_1802.field_8833) == -1 && var4 && var2.method_7909() == class_1802.field_8833) {
            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 1, class_1713.field_7790, I.field_1724);
         }

         if (this.М.4(а4) && П4.И衣() == -1 && var4 && var2.method_7909() instanceof class_1738 && this.4(var2)) {
            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 1, class_1713.field_7790, I.field_1724);
         }

         if (this.М.4(ац) && П4.Е(class_1802.field_8575) == -1 && var4 && var2.method_7909() == class_1802.field_8575) {
            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 1, class_1713.field_7790, I.field_1724);
         }

         if (this.М.4(аИ) && this.к(var2)) {
            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 1, class_1713.field_7790, I.field_1724);
         }
      }
   }

   private boolean к(class_1799 var1) {
      if (var1 != null && !var1.method_7960()) {
         class_9279 var3 = (class_9279)var1.method_57825(class_9334.field_49628, class_9279.field_49302);
         boolean var2;
         if (!var3.method_57458() && var3.method_57450(а1)) {
            var2 = true;
         } else {
            class_9279 var4 = (class_9279)var1.method_57825(class_9334.field_49611, class_9279.field_49302);
            var2 = !var4.method_57458() && var4.method_57450(а0);
         }

         boolean var5 = var1.method_57826(class_9334.field_49637);
         return var2 && var5;
      } else {
         return false;
      }
   }

   public void ч(ЧЫ var1) {
      if (this.М.4(а西)) {
         class_2596 var4 = var1.а();
         if (var4 instanceof class_2735) {
            class_2735 var2 = (class_2735)var4;
            int var5 = var2.comp_3325();
            if (true && var1.аБ() && var5 != I.field_1724.method_31548().field_7545) {
               int var7 = class_3532.method_15340(
                  I.field_1724.method_31548().field_7545 >= 8 ? I.field_1724.method_31548().field_7545 - 1 : I.field_1724.method_31548().field_7545 + 1, 0, 8
               );
               I.field_1724.field_3944.method_52787(new class_2868(var7));
               I.field_1724.field_3944.method_52787(new class_2868(I.field_1724.method_31548().field_7545));
               var1.必(true);
            }
         }
      }
   }

   public NoSlotChange() {
      super(аl, аш, Пй.Combat);
      this.М = new Фи(аА, new Ь(аЯ, true), new Ь(аЧ, false), new Ь(аЖ, false), new Ь(а诶, false), new Ь(аЦ, false));
      this.ф(new ФЮ[]{this.М});
   }

   public void а(ЧЛ var1) {
      class_1735 var2 = var1.I();
      class_1713 var3 = var1.I();
      if (var2 != null
         && var3 != class_1713.field_7791
         && var3 != class_1713.field_7794
         && !(I.field_1755 instanceof class_476)
         && !(I.field_1755 instanceof class_481)
         && !(I.field_1755 instanceof class_495)) {
         class_1799 var4 = var2.method_7677();
         class_1792 var5 = var4.method_7909();
         if ((Boolean)this.М.4(1).о() && var5 == class_1802.field_8833) {
            var1.必(true);
         }

         if ((Boolean)this.М.4(2).о() && var5 instanceof class_1738 && this.4(var4)) {
            var1.必(true);
         }

         if ((Boolean)this.М.4(3).о() && var5 == class_1802.field_8575) {
            var1.必(true);
         }

         if (this.М.4(ач) && this.к(var4)) {
            var1.必(true);
         }
      }
   }
}
