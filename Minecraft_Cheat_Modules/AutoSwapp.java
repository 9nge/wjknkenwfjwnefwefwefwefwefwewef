// Module: AutoSwap
// Category: Combat
// Original obfuscated class: sg.ec.25 (25.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1739;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1829;
import net.minecraft.class_2815;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_490;
import net.minecraft.class_9334;
import sLM.4ZXYEo;

public class AutoSwap extends Module implements Supplier {
   static String 5еы = "AutoSwap";
   static String 5еЮ = "Автоматически свапает выбранный предмет";
   static String 5еl = "Режим";
   static String 5еш = "Предметы";
   static String 5еА = "Предметы";
   static String 5еЯ = "Колесо";
   ТЙ С = new ТЙ(5еl, 5еш, 5еА, 5еЯ);
   static String 5еЧ = "Кнопка свапа";
   ФМ Н = new ФМ(5еЧ, () -> this.С.Я(5衣п));
   static String 5еЖ = "Кнопка колеса";
   ФМ Р = new ФМ(5еЖ, () -> this.С.Я(5衣с));
   static String 5е诶 = "Первый предмет";
   static String 5еЦ = "Тотем";
   static String 5еч = "Щит";
   static String 5ер = "Тотем";
   static String 5е4 = "Фейерверк";
   static String 5ец = "Яблоко";
   static String 5еИ = "Любая еда";
   static String 5е西 = "Шар, Cфера";
   ТЙ й = new ТЙ(5е诶, 5еЦ, () -> this.С.Я(5衣я), 5еч, 5ер, 5е4, 5ец, 5еИ, 5е西);
   static String 5е1 = "Второй предмет";
   static String 5е0 = "Шар, Cфера";
   static String 5е> = "Щит";
   static String 5еХ = "Тотем";
   static String 5еж = "Фейерверк";
   static String 5еГ = "Яблоко";
   static String 5ег = "Любая еда";
   static String 5е3 = "Шар, Cфера";
   ТЙ П = new ТЙ(5е1, 5е0, () -> this.С.Я(5衣М), 5е>, 5еХ, 5еж, 5еГ, 5ег, 5е3);
   static String 5ею = "Игнорировать обычные тотемы";
   Ь 5т = new Ь(5ею, true, () -> this.С.Я(5衣т));
   static String 5еэ = "Авто-шар кнопка";
   ФМ Т = new ФМ(5еэ);
   static String 5ее = "Брать шар";
   static String 5е衣 = "Всегда";
   static String 5еВ = "Когда без меча";
   static String 5еЗ = "Когда ливает";
   static String 5ек = "Когда в элитре";
   Фи У = new Фи(5ее, new Ь(5е衣, false), new Ь(5еВ, true), new Ь(5еЗ, true), new Ь(5ек, true));
   static String 5еЪ = "Убирать без таргета";
   Ь 5М = new Ь(5еЪ, true, () -> this.У.4(5衣з));
   boolean 5Ж = false;
   int 5М = 0;
   int 5я = -1;
   int 5с = 0;
   Т4 я = new Т4();
   boolean 5诶 = false;
   int 5п = -1;
   boolean 5Ц = false;
   boolean 5ч = false;
   int 5О = -1;
   Т4 с = new Т4();
   static class_310 I;
   static String 5е9 = "Всегда";
   long 5е_;
   static String 5е< = "Предметы";
   static String 5еЫ = "Колесо";
   float 5衣К;
   static String 5衣я = "Предметы";
   double 5衣У;
   double 5衣Л;
   double 5衣Ф;
   static String 5衣п = "Предметы";
   String 5е6;
   String 5衣7;
   String 5衣Н;
   String 5衣Р;
   String 5衣Т;
   String 5衣Э;
   String 5衣I;
   String 5衣2;
   String 5衣ь;
   int 5衣ф;
   String 5衣Е;
   String 5衣в;
   int 5衣Щ;
   String 5衣д;
   static String 5衣М = "Предметы";
   static String 5衣з = "Всегда";
   static String 5衣с = "Колесо";
   long 5е8;
   long 5еЬ;
   static String 5衣т = "Предметы";
   String 5ещ;
   String 5еб;
   String 5ео;
   String 5衣5;
   String 5衣н;
   String 5衣Ш;

   private class_1792 К(String var1) {
      class_1792 var10000;
      switch (var1) {
         case 5ещ:
            var10000 = class_1802.field_8288;
            break;
         case 5еб:
            var10000 = class_1802.field_8255;
            break;
         case 5ео:
            var10000 = class_1802.field_8639;
            break;
         case 5衣5:
            var10000 = class_1802.field_8463;
            break;
         case 5衣н:
            var10000 = class_1802.field_8575;
            break;
         case 5衣Ш:
            var10000 = null;
            break;
         default:
            var10000 = class_1802.field_8162;
      }

      return var10000;
   }

   private boolean Хщ() {
      Щ var1 = Ч.getInstance().getModuleManager().ь();
      return var1 != null && var1.衣();
   }

   public _5/* $VF was: 25*/() {
      super(5еы, 5еЮ, Пй.Combat);
      this.ф(new ФЮ[]{this.С, this.Н, this.Р, this.й, this.П, this.5т, this.Т, this.У, this.5М});
   }

   public void Я(Ц var1) {
      if (I.field_1724 != null) {
         if (this.5с > 0) {
            this.5с--;
         }

         if (I.field_1724.field_6235 > 0) {
            this.с.кЬ();
         }

         boolean var2 = this.ХН();
         if (!var2) {
            this.5ч = false;
         }

         class_1309 var3 = sg.ec.Й.П();
         boolean var4 = var3 == null && (!Boolean.TRUE.equals(this.У.4(5е9)) || (Boolean)this.5М.о());
         if (!this.5ч || this.5О == -1 || !var4 && (var3 == null || this.е(var3))) {
            if (this.5Ц && var3 != null && this.е(var3) && !this.5ч && this.ХД()) {
               this.КС();
               this.5ч = true;
            }

            if (this.5诶 && this.5п != -1 && this.я.х(5е_)) {
               int var5 = this.5п < 9 ? this.5п + 36 : this.5п;
               I.method_1507(new class_490(I.field_1724));
               I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var5, 40, class_1713.field_7791, I.field_1724);
               I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
               I.method_1507(null);
               this.5诶 = false;
               this.5п = -1;
               this.5Ж = false;
            }

            if (!this.5诶) {
               if (this.5М == 1 && this.5я != -1 && this.5с == 0) {
                  Щ var11 = Ч.getInstance().getModuleManager().ь();
                  if (var11 != null && var11.й()) {
                     var11.Е = true;
                  }

                  int var12 = this.5я < 9 ? this.5я + 36 : this.5я;
                  I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var12, 40, class_1713.field_7791, I.field_1724);
                  I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
                  if (var11 != null && var11.й()) {
                     var11.Е = false;
                  }

                  this.5М = 0;
                  this.5я = -1;
                  this.5Ж = false;
               } else if (this.5М <= 0) {
                  if (this.С.Я(5е<)) {
                     if (this.5Ж) {
                        this.Кк();
                     }
                  } else if (this.С.Я(5еЫ)) {
                     ТШ var10 = this.К();
                     if (var10 == null) {
                        return;
                     }

                     int var6 = var10.а();
                     if (var6 >= 0) {
                        class_1799 var7 = var10.у(var6);
                        boolean var8 = var7.method_7960();
                        boolean var9 = !var8 && !this.Х(I.field_1724.method_6079(), var7);
                        if (!var10.А() && (var8 || var9)) {
                           var10.Р(-1);
                        }
                     }

                     if (var10.в() && this.5Ж) {
                        this.Кй();
                     }
                  }
               }
            }
         } else {
            this.З(this.5О);
            this.5О = -1;
            this.5ч = false;
         }
      }
   }

   private class_1799 _/* $VF was: 9*/() {
      ТШ var1 = this.К();
      return var1 == null ? class_1799.field_8037 : var1.O();
   }

   private void Кк() {
      int var1 = this.>();
      if (var1 < 0) {
         this.5Ж = false;
      } else if (this.Хб()) {
         this.я.кЬ();
         this.5诶 = true;
         this.5п = var1;
      } else {
         if (!this.ХЮ() && !this.ХЦ()) {
            if (this.Хщ()) {
               Щ var2 = Ч.getInstance().getModuleManager().ь();
               var2.Д();
            }

            int var3 = var1 < 9 ? var1 + 36 : var1;
            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 40, class_1713.field_7791, I.field_1724);
            I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
            this.5Ж = false;
         } else {
            this.5с = 2;
            this.5М = 1;
            this.5я = var1;
         }
      }
   }

   private boolean ХЦ() {
      Щ var1 = Ч.getInstance().getModuleManager().ь();
      return var1 != null && var1.й();
   }

   private boolean ХД() {
      2ъ var1 = (2ъ)Ч.getInstance().getModuleManager().ь(2ъ.class);
      if (var1 != null && var1.7()) {
         float var2 = I.field_1724.method_6032() + I.field_1724.method_6067();
         return var2 > 5衣К;
      } else {
         return true;
      }
   }

   private boolean ХЮ() {
      Щ var1 = Ч.getInstance().getModuleManager().ь();
      return var1 != null && var1.ы();
   }

   private int _/* $VF was: >*/() {
      class_1799 var1 = I.field_1724.method_6079();
      class_1792 var2 = var1.method_7909();
      class_1792 var3 = this.К((String)this.й.о());
      class_1792 var4 = this.К((String)this.П.о());
      if (var2 instanceof class_1739) {
         int var6 = this.Х(var3);
         return var6 >= 0 ? var6 : this.Х(var4);
      } else if ((var3 == null || var2 != var3) && (var3 != null || !var1.method_57826(class_9334.field_50075))) {
         if ((var4 == null || var2 != var4) && (var4 != null || !var1.method_57826(class_9334.field_50075))) {
            int var5 = this.Х(var3);
            return var5 >= 0 ? var5 : this.Х(var4);
         } else {
            return this.Х(var3);
         }
      } else {
         return this.Х(var4);
      }
   }

   private void Кй() {
      class_1799 var1 = this.П();
      class_1799 var2 = I.field_1724.method_6079();
      if (!var1.method_7960() && !this.Х(var2, var1)) {
         int var3 = this.9(var1);
         if (var3 < 0) {
            this.5Ж = false;
         } else {
            this.ц(var3);
         }
      }
   }

   private boolean ь(class_1309 var1) {
      if (I.field_1724 == null) {
         return false;
      } else {
         double var2 = Math.sqrt(
            var1.method_18798().field_1352 * var1.method_18798().field_1352 + var1.method_18798().field_1350 * var1.method_18798().field_1350
         );
         if (var2 < 5衣У) {
            return false;
         } else {
            double var4 = var1.method_23317() - I.field_1724.method_23317();
            double var6 = var1.method_23321() - I.field_1724.method_23321();
            double var8 = Math.sqrt(var4 * var4 + var6 * var6);
            if (var8 < 5衣Л) {
               return false;
            } else {
               double var10 = var4 / var8;
               double var12 = var6 / var8;
               double var14 = var1.method_18798().field_1352;
               double var16 = var1.method_18798().field_1350;
               double var18 = var10 * var14 + var12 * var16;
               return var18 > 5衣Ф;
            }
         }
      }
   }

   private void З(int var1) {
      Щ var2 = Ч.getInstance().getModuleManager().ь();
      if (var2 != null && var2.й()) {
         var2.Е = true;
      }

      int var3 = var1 < 9 ? var1 + 36 : var1;
      I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 40, class_1713.field_7791, I.field_1724);
      I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
      if (var2 != null && var2.й()) {
         var2.Е = false;
      }
   }

   private int п() {
      class_1799 var1 = this.9();
      if (var1.method_7960()) {
         return -1;
      } else {
         for (int var2 = 0; var2 < 36; var2++) {
            class_1799 var3 = I.field_1724.method_31548().method_5438(var2);
            if (this.Х(var3, var1)) {
               return var2;
            }
         }

         return -1;
      }
   }

   private boolean Хб() {
      Щ var1 = Ч.getInstance().getModuleManager().ь();
      return var1 != null && var1.7() && var1.н.Я(5е6);
   }

   public boolean Хм() {
      return this.5ч;
   }

   private void ц(int var1) {
      if (this.Хб()) {
         this.я.кЬ();
         this.5诶 = true;
         this.5п = var1;
      } else {
         if (!this.ХЮ() && !this.ХЦ()) {
            if (this.Хщ()) {
               Щ var2 = Ч.getInstance().getModuleManager().ь();
               var2.Д();
            }

            int var3 = var1 < 9 ? var1 + 36 : var1;
            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var3, 40, class_1713.field_7791, I.field_1724);
            I.field_1724.field_3944.method_52787(new class_2815(I.field_1724.field_7512.field_7763));
            this.5Ж = false;
         } else {
            this.5с = 2;
            this.5М = 1;
            this.5я = var1;
         }
      }
   }

   private boolean е(class_1309 var1) {
      if (!this.5Ц) {
         return false;
      } else if (Boolean.TRUE.equals(this.У.4(5衣7))) {
         return true;
      } else if (I.field_1724.field_6235 <= 0 && var1 != null) {
         if (Boolean.TRUE.equals(this.У.4(5衣Н)) && !(var1.method_6047().method_7909() instanceof class_1829)) {
            return true;
         } else if (Boolean.TRUE.equals(this.У.4(5衣Р)) && this.ь(var1) && !I.field_1724.method_5799()) {
            return true;
         } else {
            if (Boolean.TRUE.equals(this.У.4(5衣Т)) && var1 instanceof class_1657) {
               class_1657 var2 = (class_1657)var1;
               class_1799 var3 = var2.method_31548().method_7372(2);
               if (var3.method_7909() == class_1802.field_8833) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean ХН() {
      class_1799 var1 = this.9();
      return var1.method_7960() ? false : this.Х(I.field_1724.method_6079(), var1);
   }

   public void з(э var1) {
      if (I.field_1755 == null || I.field_1755 instanceof ПЬ) {
         int var2 = var1.М();
         if (this.С.Я(5衣Э) && var2 == (Integer)this.Н.о() && var1.8л()) {
            this.5Ж = true;
         }

         if (this.С.Я(5衣I) && var2 == (Integer)this.Р.о()) {
            if (var1.8л()) {
               this.5Ж = true;
               if (!(I.field_1755 instanceof ПЬ)) {
                  I.method_1507(new ПЬ());
               }
            } else {
               class_437 var4 = I.field_1755;
               if (var4 instanceof ПЬ) {
                  ПЬ var3 = (ПЬ)var4;
                  int var8 = var3.getHoveredSlot();
                  if (var8 >= 0) {
                     ТШ var5 = this.К();
                     if (var5 != null) {
                        class_1799 var6 = var5.у(var8);
                        if (!var6.method_7960()) {
                           var5.Р(var8);
                        }
                     }
                  }

                  I.method_1507(null);
               }
            }
         }

         if (!var1.8л() && (Integer)this.Т.о() == var2) {
            this.5Ц = !this.5Ц;
            if (this.5Ц) {
               class_1799 var7 = this.9();
               String var9 = var7.method_7960() ? 5衣2 : var7.method_7964().getString();
               ПЭ.ы(5衣ь, 4ZXYEo.фО<"makeConcatWithConstants">(var9), 5衣ф);
            } else {
               ПЭ.ы(5衣Е, 5衣в, 5衣Щ);
            }
         }
      }
   }

   private int _/* $VF was: 9*/(class_1799 var1) {
      if (var1 != null && !var1.method_7960()) {
         if (!this.С.Я(5衣д)) {
            return this.Х(var1.method_7909());
         } else {
            for (int var2 = 0; var2 < I.field_1724.method_31548().method_5439(); var2++) {
               class_1799 var3 = I.field_1724.method_31548().method_5438(var2);
               if (this.Х(var3, var1)) {
                  return var2;
               }
            }

            return -1;
         }
      } else {
         return -1;
      }
   }

   private boolean Х(class_1799 var1, class_1799 var2) {
      if (var1 == null || var2 == null || var1.method_7960() || var2.method_7960()) {
         return false;
      } else {
         return !class_1799.method_31577(var1, var2) ? false : var1.method_7964().getString().equals(var2.method_7964().getString());
      }
   }

   public void р(ЧН var1) {
      if (this.5с > 0) {
         var1.и(0.0F);
         var1.х(0.0F);
         var1.я(false);
         var1.ф(false);
      }

      if (this.5诶) {
         if (!this.я.х(5е8)) {
            var1.и(0.0F);
            var1.х(0.0F);
            var1.ф(false);
         }

         if (!this.я.х(5еЬ)) {
            var1.я(false);
         }
      }
   }

   private ТШ К() {
      return Ч.getInstance().getHandlerManager() != null ? Ч.getInstance().getHandlerManager().ч() : null;
   }

   private class_1799 П() {
      ТШ var1 = this.К();
      if (var1 != null && I.field_1724 != null) {
         int var2 = var1.а();
         return var2 < 0 ? class_1799.field_8037 : var1.у(var2);
      } else {
         return class_1799.field_8037;
      }
   }

   @Override
   public void Щ() {
      this.5Ж = false;
      this.5М = 0;
      this.5я = -1;
      this.5с = 0;
      this.5诶 = false;
      this.5п = -1;
      this.5Ц = false;
      this.5ч = false;
      this.5О = -1;
      super.Щ();
   }

   public boolean Х西() {
      return this.5Ц;
   }

   private int Х(class_1792 var1) {
      if (var1 == class_1802.field_8288 && (Boolean)this.5т.о()) {
         return П4.ИЖ();
      } else {
         return var1 == null ? П4.Ит() : П4.Е(var1);
      }
   }

   private void КС() {
      if (I.field_1755 == null) {
         int var1 = this.п();
         if (var1 >= 0) {
            this.5О = var1;
            this.З(var1);
         }
      }
   }

   static {
      5衣м = "Авто-шар включен: \u0001";
   }
}
