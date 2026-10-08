// Module: Auto Potion
// Category: Combat
// Original obfuscated class: sg.ec.Ть (ТЬ.java)
package sg.ec.modules.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import net.minecraft.class_1268;
import net.minecraft.class_1291;
import net.minecraft.class_1294;
import net.minecraft.class_1713;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_6880;

public class AutoPotion extends Module implements Supplier {
   static class_310 I;
   Ь нЕ;
   int ШЙ;
   boolean нб;
   Ь нф;
   Ь нв;
   Фи П;
   static String 5Ж_ = "Сила";
   static String 5Ж< = "Скорость";
   static String 5ЖЫ = "Огнестойкость";
   String 5Ж8;
   String 5ЖЬ;
   String 5Жщ;
   Ь нм;
   float 5Жб;
   float 5Жо;
   float 5诶5;
   float 5诶н;
   static String 5Жю = "Auto Potion";
   static String 5Жэ = "Автоматически использует зелья, бросая их под себя";
   static String 5Же = "Бросать";
   static String 5Ж衣 = "Скорость";
   static String 5ЖВ = "Сила";
   static String 5ЖЗ = "Огнестойкость";
   static String 5Жк = "Только при пвп";
   static String 5ЖЪ = "Отключать после использования";
   static String 5Ж6 = "Только с хотбара";
   static String 5Ж9 = "Приоритет силы";

   private void Э(List var1, boolean var2, class_6880 var3) {
      if (var2) {
         if (!I.field_1724.method_6059(var3)) {
            int var4 = П4.7(true, false, true, false, (class_1291)var3.comp_349());
            if (var4 != -1) {
               var1.add(var4);
            } else {
               if (!(Boolean)this.нЕ.о()) {
                  int var5 = П4.7(false, false, true, false, (class_1291)var3.comp_349());
                  if (var5 != -1) {
                     var1.add(var5);
                  }
               }
            }
         }
      }
   }

   @Override
   public void ц() {
      super.ц();
      this.ШЙ = 20;
      this.нб = false;
   }

   @Override
   public void Щ() {
      super.Щ();
      this.нб = false;
   }

   private void г(Ц var1) {
      if (!(Boolean)this.нф.о() || !this.нб) {
         this.ШЙ++;
         ArrayList var2 = new ArrayList();
         if ((Boolean)this.нв.о()) {
            this.Э(var2, this.П.4(5Ж_), class_1294.field_5910);
            this.Э(var2, this.П.4(5Ж<), class_1294.field_5904);
            this.Э(var2, this.П.4(5ЖЫ), class_1294.field_5918);
         } else {
            this.Э(var2, this.П.4(5Ж8), class_1294.field_5904);
            this.Э(var2, this.П.4(5ЖЬ), class_1294.field_5910);
            this.Э(var2, this.П.4(5Жщ), class_1294.field_5918);
         }

         if ((!(Boolean)this.нм.о() || 2т.6С()) && this.ШЙ >= 20 && !Фо.р(5Жб) && !var2.isEmpty()) {
            if ((Boolean)this.нЕ.о()) {
               boolean var3 = var2.stream().allMatch(var0 -> var0 < 9);
               if (!var3) {
                  ArrayList var4 = new ArrayList();

                  for (Integer var6 : var2) {
                     if (var6 < 9) {
                        var4.add(var6);
                     }
                  }

                  if (var4.isEmpty()) {
                     return;
                  }

                  var2 = var4;
               }
            }

            float var8 = ThreadLocalRandom.current().nextFloat(5Жо, 5诶5);
            С8.И(new 2щ(I.field_1724.method_36454(), 5诶н), var8, 1, 10);
            if (!(new 2щ(I.field_1724).9(2щ.э()) > 1.0F)) {
               int var9 = I.field_1724.method_31548().field_7545;
               boolean var10 = false;

               for (Integer var7 : var2) {
                  if (var7 < 9) {
                     I.field_1724.field_3944.method_52787(new class_2868(var7));
                     I.field_1761.method_2919(I.field_1724, class_1268.field_5808);
                     var10 = true;
                  } else if (!(Boolean)this.нЕ.о()) {
                     I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var7, var9, class_1713.field_7791, I.field_1724);
                     I.field_1761.method_2919(I.field_1724, class_1268.field_5808);
                     I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var7, var9, class_1713.field_7791, I.field_1724);
                     var10 = true;
                  }
               }

               I.field_1724.field_3944.method_52787(new class_2868(var9));
               if (var10) {
                  this.ШЙ = 0;
                  this.нб = true;
                  if ((Boolean)this.нф.о()) {
                     this._();
                  }
               }
            }
         }
      }
   }

   public AutoPotion() {
      super(5Жю, 5Жэ, Пй.Combat);
      this.П = new Фи(5Же, new Ь(5Ж衣, true), new Ь(5ЖВ, true), new Ь(5ЖЗ, true));
      this.нм = new Ь(5Жк, true);
      this.нф = new Ь(5ЖЪ, false);
      this.нЕ = new Ь(5Ж6, false);
      this.нв = new Ь(5Ж9, true);
      this.ф(new ФЮ[]{this.П, this.нм, this.нф, this.нЕ, this.нв});
   }
}
