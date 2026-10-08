// Module: BackTrack
// Category: Combat
// Original obfuscated class: sg.ec.Сй (Сй.java)
package sg.ec.modules.combat;

import java.lang.runtime.SwitchBootstraps;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;
import net.minecraft.class_1297;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2684;
import net.minecraft.class_2739;
import net.minecraft.class_2749;
import net.minecraft.class_2767;
import net.minecraft.class_2777;
import net.minecraft.class_2797;
import net.minecraft.class_310;
import net.minecraft.class_3417;
import net.minecraft.class_4587;
import net.minecraft.class_7422;
import net.minecraft.class_7439;
import net.minecraft.class_7472;

public class BackTrack extends Module implements Supplier {
   class_1297 7;
   class_7422 н;
   ConcurrentLinkedQueue н;
   ConcurrentLinkedQueue Ш;
   static class_310 I;
   long 5必;
   Ч3 ШК;
   long 5у;
   Ч3 ШЛ;
   Ч3 ШФ;
   static double дв = 2.0;
   Ь нБ;
   static float дЕ = 0.2F;
   int ШГ;
   static String Щ6 = "BackTrack";
   static String Щ9 = "Задерживает пакеты для увеличения дальности атаки";
   static String Щ_ = "Дистанция";
   static float Щ< = 3.0F;
   static float ЩЫ = 10.0F;
   static String Щ8 = "Задержка (мс)";
   static float ЩЬ = 150.0F;
   static float Щщ = 10.0F;
   static float Щб = 1000.0F;
   static float Що = 10.0F;
   Ч3 ШУ;
   static String д5 = "Время удержания (мс)";
   static float дн = 500.0F;
   static float дШ = 2000.0F;
   static float дЭ = 50.0F;
   static String дI = "Работа после атаки (мс)";
   static float д2 = 1000.0F;
   static float дь = 5000.0F;
   static float дм = 50.0F;
   static String дф = "Рендер бокса";

   @Override
   public void Щ() {
      super.Щ();
      this.Р(true);
   }

   private void В(class_1297 var1) {
      if (this.з(var1)) {
         if (var1 != this.7) {
            this.Р(false);
            this.н = new class_7422();
            this.н.method_43494(var1.method_19538());
         }

         this.7 = var1;
      }
   }

   private boolean _С() {
      return this.н.isEmpty() && this.Ш.isEmpty();
   }

   public void _/* $VF was: >*/(ПЧ var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         this.5必 = System.currentTimeMillis();
         class_1297 var2 = var1.в();
         this.В(var2);
      }
   }

   private boolean з(class_1297 var1) {
      if (var1 == null) {
         return false;
      } else {
         double var2 = (double)var1.method_5739(I.field_1724);
         boolean var4 = var2 <= (double)((Float)this.ШК.о()).floatValue();
         if (var4) {
            this.5у = System.currentTimeMillis();
         }

         boolean var5 = (float)(System.currentTimeMillis() - this.5у) < (Float)this.ШЛ.о();
         boolean var6 = (float)(System.currentTimeMillis() - this.5必) < (Float)this.ШФ.о();
         return (var4 || var5) && var1.method_5805() && I.field_1724.field_6012 > 10 && var6;
      }
   }

   private class_238 Ж() {
      if (this.7 != null && this.н != null) {
         class_243 var1 = this.н.method_60933();
         double var2 = (double)this.7.method_17681() / дв;
         double var4 = (double)this.7.method_17682();
         return new class_238(
            var1.field_1352 - var2, var1.field_1351, var1.field_1350 - var2, var1.field_1352 + var2, var1.field_1351 + var4, var1.field_1350 + var2
         );
      } else {
         return new class_238(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      }
   }

   private boolean _4() {
      return this.7 != null && this.7.method_5805() && this.з(this.7);
   }

   public void В(СЗ var1) {
      if ((Boolean)this.нБ.о()) {
         if (this.7 != null && this.н != null) {
            if (I.field_1724 != null && I.field_1687 != null) {
               class_4587 var2 = var1.Ъ();
               class_243 var3 = I.field_1773.method_19418().method_19326();
               var2.method_22903();
               var2.method_22904(-var3.field_1352, -var3.field_1351, -var3.field_1350);
               class_238 var4 = this.Ж();
               Пр.д(var2, var4, 2Д.Р(ПЙ.ю(ь.MODULE_VISUAL), ПЙ.ю(ь.MODULE_VISUAL) - дЕ), 2.0F);
               var2.method_22909();
            }
         }
      }
   }

   public void з(М var1) {
      this.н.clear();
      this.Ш.clear();
      this.7 = null;
      this.н = null;
   }

   private double Ж(class_243 var1, class_238 var2) {
      double var3 = Math.max(var2.field_1323 - var1.field_1352, Math.max(0.0, var1.field_1352 - var2.field_1320));
      double var5 = Math.max(var2.field_1322 - var1.field_1351, Math.max(0.0, var1.field_1351 - var2.field_1325));
      double var7 = Math.max(var2.field_1321 - var1.field_1350, Math.max(0.0, var1.field_1350 - var2.field_1324));
      return var3 * var3 + var5 * var5 + var7 * var7;
   }

   public BackTrack() {
      super(Щ6, Щ9, Пй.Combat);
      this.ШК = new Ч3(Щ_, Щ<, 1.0F, ЩЫ, 1.0F);
      this.ШУ = new Ч3(Щ8, ЩЬ, Щщ, Щб, Що);
      this.ШЛ = new Ч3(д5, дн, 0.0F, дШ, дЭ);
      this.ШФ = new Ч3(дI, д2, 0.0F, дь, дм);
      this.нБ = new Ь(дф, true);
      this.н = new ConcurrentLinkedQueue();
      this.Ш = new ConcurrentLinkedQueue();
      this.5у = 0L;
      this.5必 = 0L;
      this.7 = null;
      this.н = null;
      this.ШГ = 0;
      this.ф(new ФЮ[]{this.ШК, this.ШУ, this.ШЛ, this.ШФ, this.нБ});
   }

   public void _/* $VF was: 3*/(ЧЫ var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         if (var1.аБ() && !var1.э()) {
            if (!this._С() || this._4()) {
               class_2596 var2 = var1.а();
               if (!(var2 instanceof class_2797) && !(var2 instanceof class_7439) && !(var2 instanceof class_7472)) {
                  if (var2 instanceof class_2767) {
                     class_2767 var3 = (class_2767)var2;
                     if (var3.method_11894().comp_349() == class_3417.field_15115) {
                        return;
                     }
                  }

                  if (var2 instanceof class_2749) {
                     class_2749 var12 = (class_2749)var2;
                     if (var12.method_11833() <= 0.0F) {
                        this.Р(true);
                        return;
                     }
                  }

                  if (this.7 != null) {
                     boolean var13 = var2 instanceof class_2684 && ((class_2684)var2).method_11645(I.field_1687) == this.7;
                     boolean var4 = var2 instanceof class_2777 && ((class_2777)var2).comp_3237() == this.7.method_5628();
                     boolean var5 = var2 instanceof class_2739 && ((class_2739)var2).comp_1127() == this.7.method_5628();
                     if (var13 || var4 || var5) {
                        Objects.requireNonNull(var2);
                        byte var8 = 0;
                        class_243 var10000;
                        switch (SwitchBootstraps.typeSwitch<"typeSwitch",class_2684,class_2777,class_2739>(var2, var8)) {
                           case 0:
                              class_2684 var9 = (class_2684)var2;
                              var10000 = this.н != null
                                 ? this.н.method_43489((long)var9.method_36150(), (long)var9.method_36151(), (long)var9.method_36152())
                                 : null;
                              break;
                           case 1:
                              class_2777 var10 = (class_2777)var2;
                              class_243 var15 = var10.comp_3238().comp_3148();
                              var10000 = new class_243(var15.field_1352, var15.field_1351, var15.field_1350);
                              break;
                           case 2:
                              class_2739 var11 = (class_2739)var2;
                              var10000 = this.7.method_19538();
                              break;
                           default:
                              var10000 = null;
                        }

                        class_243 var6 = var10000;
                        if (this.н != null && var6 != null) {
                           this.н.method_43494(var6);
                        }

                        if (var6 != null) {
                           double var7 = this.Ж(I.field_1724.method_19538(), this.Ж());
                           double var14 = this.7.method_5858(I.field_1724);
                           if (var14 < var7) {
                              this.к(true);
                              return;
                           }
                        }
                     }
                  }

                  var1.必(true);
                  this.н.add(new Сй$Ч(var2, System.currentTimeMillis()));
               }
            }
         }
      }
   }

   private void к(boolean var1) {
      this.н.removeIf(var2 -> {
         if (!var1 && System.currentTimeMillis() - var2.52 < (long)this.ШГ) {
            return false;
         } else {
            this.Ш.add(var2.Ш);
            return true;
         }
      });
   }

   @Override
   public void ц() {
      super.ц();
      this.Р(false);
   }

   public void _/* $VF was: 7*/(Ц var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         if (this._4()) {
            this.к(false);
         } else {
            this.Р(false);
         }

         class_2596 var2;
         while ((var2 = (class_2596)this.Ш.poll()) != null) {
            var2.method_65081(I.field_1724.field_3944);
         }

         if (this._С()) {
            this.ШГ = ((Float)this.ШУ.о()).intValue();
         }
      } else {
         this.Р(true);
      }
   }

   private void Р(boolean var1) {
      if (var1) {
         this.к(true);
      } else {
         this.н.clear();
      }

      this.7 = null;
      this.н = null;
   }
}
