// Module: Trigger Bot
// Category: Combat
// Original obfuscated class: sg.ec.Тм (ТМ.java)
package sg.ec.modules.combat;

import java.util.Comparator;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2815;
import net.minecraft.class_2846;
import net.minecraft.class_2848;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_745;
import net.minecraft.class_2846.class_2847;
import net.minecraft.class_2848.class_2849;

public class TriggerBot extends Module implements Supplier {
   Ч3 ШI;
   static class_310 I;
   Фи Й;
   Ь н7;
   static class_1309 Н;
   Ь нТ;
   boolean Шв;
   Ь нР;
   Фи и;
   Ь нФ;
   Ь нН;
   Т4 Ж;
   long IЫ;
   Ь нУ;
   float I8;
   float IЬ;
   String Iщ;
   String Iб;
   ФЯ Ш;
   float Ш8;
   double Iо;
   double 25;
   boolean ШЩ;
   Ь нК;
   static String IЮ = "Trigger Bot";
   static String Il = "Автоматически атакует существ в радиусе";
   static String Iш = "Кого атаковать";
   static String IА = "Игроков";
   static String IЯ = "Друзей";
   static String IЧ = "Голых";
   static String IЖ = "Жителей";
   static String I诶 = "Животных";
   static String IЦ = "Мобов";
   static String Iч = "Сброс спринта";
   static String Iр = "Легит";
   static String I4 = "Рейдж";
   static String Iц = "Легит";
   ТЙ 3;
   static String IИ = "Радиус атаки";
   static float I西 = 3.0F;
   static float I1 = 5.0F;
   static float I0 = 0.1F;
   static String I> = "Не бить если";
   static String IХ = "Открыт контейнер";
   static String Iж = "Используешь еду";
   static String IГ = "Бить только критами";
   static String Iг = "Ломать щит";
   static String I3 = "Только при зажатом пробеле";
   static String Iю = "Не бить через стены";
   static String Iэ = "Обход бить через стены ReallyWorld";
   static String Iе = "Синхронизация с ТПС";
   static String I衣 = "Обход идеального FallDistance";
   Ь нЛ;
   static String IВ = "Обход идеальной атаки";
   static float IЗ = 0.95F;
   int ШЯ;
   static float Iк = 30.0F;
   static float IЪ = 41.0F;
   int ШЧ;
   float I6;
   float I9;
   float I_;
   float I<;

   public Ч3 Ф() {
      return this.ШI;
   }

   private boolean Ф(class_1309 var1) {
      if (var1 != null && var1.method_5805() && var1 != I.field_1724 && !(var1 instanceof class_1531)) {
         if (var1 instanceof class_1657) {
            class_1657 var2 = (class_1657)var1;
            Тр var3 = (Тр)Ч.getInstance().getModuleManager().ь(Тр.class);
            if (Тр.З(var2)) {
               return false;
            }
         }

         return ПУ.Ж(var1, this.Й, true) || ПУ.Ж(var1, this.Й) || ПУ.э(var1, this.Й) || ПУ.诶(var1, this.Й);
      } else {
         return false;
      }
   }

   public void Ф(Пэ var1) {
      if (Н != null) {
         var1.必(true);
      }
   }

   public Ь Й() {
      return this.нТ;
   }

   public boolean Ч() {
      return this.Шв;
   }

   public void Ы() {
      for (class_1657 var2 : I.field_1687.method_18456()) {
         if (var2 instanceof class_745) {
            ((Ф1)var2).releaseResolver();
         }
      }
   }

   public Ь Ъ() {
      return this.нР;
   }

   public Фи _/* $VF was: 9*/() {
      return this.и;
   }

   public Ь Ф() {
      return this.н7;
   }

   public void _/* $VF was: 9*/(ЧН var1) {
      if (Н != null && this.Шв) {
         var1.и(0.0F);
         var1.х(0.0F);
         this.Шв = false;
      }
   }

   public Ь ь() {
      return this.нФ;
   }

   private void е() {
      if ((Boolean)this.нН.о()) {
         int var1 = П4.Их();
         if (var1 != -1) {
            if (Н.method_6079().method_7909() == class_1802.field_8255 || Н.method_6047().method_7909() == class_1802.field_8255) {
               if (var1 >= 9) {
                  I.field_1761
                     .method_2906(I.field_1724.field_7512.field_7763, var1, I.field_1724.method_31548().field_7545, class_1713.field_7791, I.field_1724);
                  I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
                  I.field_1761.method_2918(I.field_1724, Н);
                  I.field_1724.method_6104(class_1268.field_5808);
                  I.field_1761
                     .method_2906(I.field_1724.field_7512.field_7763, var1, I.field_1724.method_31548().field_7545, class_1713.field_7791, I.field_1724);
                  I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
               } else {
                  I.field_1724.field_3944.method_52787(new class_2868(var1));
                  I.field_1761.method_2918(I.field_1724, Н);
                  I.field_1724.method_6104(class_1268.field_5808);
                  I.field_1724.field_3944.method_52787(new class_2868(I.field_1724.method_31548().field_7545));
               }
            }
         }
      }
   }

   public boolean Л() {
      boolean var1 = this.Ж.х(IЫ) && I.field_1724.method_7261(this.нУ.о() ? 2Щ.А0() : I8) > IЬ;
      if ((!this.и.4(Iщ) || !Т0.Д弟()) && (!this.и.4(Iб) || I.field_1755 == null || I.field_1755 == Ч.getInstance().getDropDown())) {
         if (Т0.Дй()) {
            return var1;
         } else {
            return Ч.getInstance().getModuleManager().ь(ЧТ.class).7()
               ? var1
               : var1 && this.Ш.В((Boolean)this.н7.о(), (Boolean)this.нР.о(), (Boolean)this.нУ.о(), this.Ш8);
         }
      } else {
         return false;
      }
   }

   public Ь I() {
      return this.нУ;
   }

   public void Э() {
      for (class_1657 var2 : I.field_1687.method_18456()) {
         if (var2 instanceof class_745) {
            ((Ф1)var2).resolve();
         }
      }
   }

   public void Ф(Т8 var1) {
      if (Ч.getInstance().getModuleManager().ь(Пф.class).7()) {
         this.м();
      }
   }

   private void х() {
      if (Н != null) {
         class_243 var1 = I.field_1724.method_33571();
         class_243 var2 = Н.method_33571();
         class_243 var3 = var2.method_1020(var1);
         double var4 = var3.method_1033();
         if (!(var4 < Iо)) {
            int var6 = Math.max(1, (int)Math.ceil(var4 * 25));
            HashSet var7 = new HashSet();

            for (int var8 = 0; var8 <= var6; var8++) {
               double var9 = (double)var8 / (double)var6;
               class_2338 var11 = class_2338.method_49637(
                  var1.field_1352 + var3.field_1352 * var9, var1.field_1351 + var3.field_1351 * var9, var1.field_1350 + var3.field_1350 * var9
               );
               if (var7.add(var11) && !I.field_1687.method_8320(var11).method_26215()) {
                  class_243 var12 = var1.method_1020(var11.method_46558());
                  double var13 = Math.abs(var12.field_1352);
                  double var15 = Math.abs(var12.field_1351);
                  double var17 = Math.abs(var12.field_1350);
                  class_2350 var19;
                  if (var15 >= var13 && var15 >= var17) {
                     var19 = var12.field_1351 > 0.0 ? class_2350.field_11036 : class_2350.field_11033;
                  } else if (var13 >= var17) {
                     var19 = var12.field_1352 > 0.0 ? class_2350.field_11034 : class_2350.field_11039;
                  } else {
                     var19 = var12.field_1350 > 0.0 ? class_2350.field_11035 : class_2350.field_11043;
                  }

                  I.field_1724.field_3944.method_52787(new class_2846(class_2847.field_12968, var11, var19, 0));
                  I.field_1724.field_3944.method_52787(new class_2846(class_2847.field_12973, var11, var19, 0));
               }
            }
         }
      }
   }

   public void ц(Ц var1) {
      Н = this.Ф();
      if (Н == null) {
         this.ШЩ = true;
      } else {
         this.ШЩ = !Чф.O(I.field_1724.method_36454(), I.field_1724.method_36455(), (double)((Float)this.ШI.о()).floatValue(), Н);
      }

      if (!Ч.getInstance().getModuleManager().ь(Пф.class).7()) {
         this.м();
      }
   }

   public Ь О() {
      return this.нК;
   }

   public Т4 _/* $VF was: 9*/() {
      return this.Ж;
   }

   public ФЯ Ф() {
      return this.Ш;
   }

   @Override
   public void Щ() {
      this.Шв = false;
      Н = null;
      super.Щ();
   }

   public float А() {
      return this.Ш8;
   }

   public void Ъ(М var1) {
      Н = null;
      this.Шв = false;
   }

   public Ь _/* $VF was: 9*/() {
      return this.нН;
   }

   public Фи Ф() {
      return this.Й;
   }

   public TriggerBot() {
      super(IЮ, Il, Пй.Combat);
      this.Й = new Фи(Iш, new Ь(IА, true), new Ь(IЯ, false), new Ь(IЧ, true), new Ь(IЖ, false), new Ь(I诶, false), new Ь(IЦ, false));
      this.3 = new ТЙ(Iч, Iр, I4, Iц);
      this.ШI = new Ч3(IИ, I西, 2.0F, I1, I0);
      this.и = new Фи(I>, new Ь(IХ, true), new Ь(Iж, false));
      this.н7 = new Ь(IГ, true);
      this.нН = new Ь(Iг, true);
      this.нР = new Ь(I3, false, () -> (Boolean)this.н7.о());
      this.нТ = new Ь(Iю, false);
      this.нК = new Ь(Iэ, true, () -> !(Boolean)this.нТ.о());
      this.нУ = new Ь(Iе, false);
      this.нЛ = new Ь(I衣, false);
      this.нФ = new Ь(IВ, false);
      this.Ж = new Т4();
      this.Ш = new ФЯ();
      this.Ш8 = IЗ;
      this.ШЯ = 0;
      this.ШЧ = (int)Тк.6(Iк, IЪ);
      this.ф(new ФЮ[]{this.Й, this.и, this.3, this.ШI, this.нТ, this.нК, this.н7, this.нР, this.нУ, this.нЛ, this.нФ, this.нН});
   }

   public static void _Р/* $VF was: 3Р*/() {
      Н = null;
   }

   public int Р() {
      return this.ШЧ;
   }

   public ТЙ Ф() {
      return this.3;
   }

   public Ь К() {
      return this.нЛ;
   }

   public int Н() {
      return this.ШЯ;
   }

   private class_1309 Ф() {
      return StreamSupport.<class_1297>stream(I.field_1687.method_18112().spliterator(), false)
         .filter(var0 -> var0 instanceof class_1309)
         .map(var0 -> (class_1309)var0)
         .filter(this::Ф)
         .filter(var1 -> Чф.O(I.field_1724.method_36454(), I.field_1724.method_36455(), (double)((Float)this.ШI.о()).floatValue(), var1))
         .sorted(Comparator.comparingDouble(var0 -> (double)var0.method_5739(I.field_1724)))
         .findFirst()
         .orElse(null);
   }

   private void м() {
      if (Н != null && Н.method_5805()) {
         if (I.field_1724 != null && I.field_1687 != null && I.field_1761 != null) {
            if (!(Boolean)this.нТ.о() || Чф.г(I.field_1724.method_36454(), I.field_1724.method_36455(), (double)((Float)this.ШI.о()).floatValue(), Н, true)) {
               if (!this.ШЩ) {
                  if (this.Л() && !(Т0.5(Н).method_1033() > (double)((Float)this.ШI.о()).floatValue())) {
                     if ((Boolean)this.нФ.о()) {
                        this.ШЯ++;
                        if (this.ШЯ >= this.ШЧ) {
                           this.ШЧ = (int)Тк.6(I6, I9);
                           this.ШЯ = 0;
                           I.field_1724.method_6104(class_1268.field_5808);
                           return;
                        }
                     }

                     if (!(Boolean)this.нТ.о() && (Boolean)this.нК.о() && !I.field_1724.method_6057(Н)) {
                        this.х();
                     }

                     boolean var1 = I.field_1724.method_5799() || I.field_1724.method_5771() || I.field_1724.method_5681() || I.field_1724.method_6128();
                     if (!var1 && I.field_1724.method_5624()) {
                        if (this.3.К() == 0) {
                           ((sg.mx.9)I.field_1724).setLastSprinting(false);
                           I.field_1724.method_5728(false);
                           I.field_1724.field_3944.method_52787(new class_2848(I.field_1724, class_2849.field_12985));
                        }

                        this.Шв = true;
                        if (((sg.mx.9)I.field_1724).getLastSprinting()) {
                           return;
                        }
                     }

                     this.Э();
                     I.field_1761.method_2918(I.field_1724, Н);
                     ЧШ var2 = (ЧШ)Ч.getInstance().getModuleManager().ь(ЧШ.class);
                     if (var2 != null && var2.7()) {
                        var2.5(Н);
                     }

                     I.field_1724.method_6104(class_1268.field_5808);
                     if ((Boolean)this.нН.о()) {
                        this.е();
                     }

                     this.Ы();
                     this.Ш8 = ThreadLocalRandom.current().nextFloat(I_, I<);
                     this.Ж.кЬ();
                  }
               }
            }
         }
      }
   }

   public static class_1309 _/* $VF was: 9*/() {
      return Н;
   }

   public boolean У() {
      return this.ШЩ;
   }
}
