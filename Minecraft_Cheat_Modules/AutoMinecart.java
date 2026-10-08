// Module: Auto Minecart
// Category: Combat
// Original obfuscated class: sg.ec.Фч (Фч.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1667;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_2828.class_2831;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;

public class AutoMinecart extends Module implements Supplier {
   static class_310 I;
   class_2338 з;
   boolean нз;
   boolean нФ;
   int нъ;
   double Ощ;
   double Об;
   double Оо;
   static String Ою = "Auto Minecart";
   static String Оэ = "Ставит рельсу и TNT-вагонетку туда, куда прилетит стрела";
   static String Ое = "Дистанция";
   static float О衣 = 4.5F;
   static float ОВ = 6.0F;
   static float ОЗ = 0.1F;
   Ч3 нп = new Ч3(Ое, О衣, 1.0F, ОВ, ОЗ);
   static String Ок = "Перекладывать с инвентаря";
   Ь 50 = new Ь(Ок, true);
   int ны = -1;
   int нЮ = -1;
   int нl = -1;
   int нш = -1;
   int нА = -1;
   int нБ;
   double О_;
   double О<;
   int нх;
   double ОЫ;
   double ОЪ;
   double О6;
   double О9;
   double О8;
   float ОЬ;

   @Override
   public void Щ() {
      super.Щ();
      if (I.field_1724 != null && I.field_1761 != null) {
         if (this.з != null || this.нз || this.нФ) {
            I.field_1724.method_31548().field_7545 = this.нъ;
            ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
         }

         this.Щь();
      }

      this.Щр();
   }

   private class_3965 Э(class_1667 var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         class_243 var2 = var1.method_19538();
         class_243 var3 = var1.method_18798();

         for (int var4 = 0; var4 <= 150; var4++) {
            class_243 var5 = var2;
            var2 = var2.method_1019(var3);
            class_243 var6;
            if (!var1.method_5799() && !I.field_1687.method_8320(class_2338.method_49638(var5)).method_27852(class_2246.field_10382)) {
               var6 = var3.method_1021(Об);
            } else {
               var6 = var3.method_1021(Ощ);
            }

            if (!var1.method_5740()) {
               var6 = new class_243(var6.field_1352, var6.field_1351 - Оо, var6.field_1350);
            }

            var3 = var6;
            class_3959 var7 = new class_3959(var5, var2, class_3960.field_17558, class_242.field_1348, I.field_1724);
            class_3965 var8 = I.field_1687.method_17742(var7);
            if (var8.method_17783() == class_240.field_1332) {
               return var8;
            }

            if (var2.field_1351 <= 0.0) {
               break;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public AutoMinecart() {
      super(Ою, Оэ, Пй.Combat);
      this.ф(new ФЮ[]{this.нп, this.50});
   }

   private void Уы(Ц var1) {
      if (I.field_1724 == null || I.field_1687 == null || I.field_1761 == null) {
         this.Щр();
      } else if (this.нз) {
         this.нз = false;
         I.field_1724.method_31548().field_7545 = this.нъ;
         ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
         this.Щь();
      } else if (this.з != null) {
         double var2 = (double)((Float)this.нп.о()).floatValue();
         double var4 = var2 * var2;
         if (I.field_1724.method_5836(I.method_61966().method_60637(true)).method_1025(this.з.method_46558()) > var4) {
            this.з = null;
            this.нФ = false;
            this.нз = true;
         } else {
            if (this.нФ) {
               if (!I.field_1687.method_8320(this.з).method_45474()) {
                  this.з = null;
                  this.нФ = false;
                  this.нз = true;
                  return;
               }

               class_2338 var6 = this.з.method_10074();
               if (!I.field_1687.method_8320(var6).method_26212(I.field_1687, var6)) {
                  this.з = null;
                  this.нФ = false;
                  this.нз = true;
                  return;
               }

               this.ц(this.з.method_46558());
               I.field_1724.method_31548().field_7545 = this.нБ;
               ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
               class_243 var7 = new class_243((double)var6.method_10263() + О_, (double)var6.method_10264() + 1.0, (double)var6.method_10260() + О<);
               I.field_1761.method_2896(I.field_1724, class_1268.field_5808, new class_3965(var7, class_2350.field_11036, var6, false));
               I.field_1724.method_6104(class_1268.field_5808);
               this.нБ = this.нх;
               this.нФ = false;
            } else {
               this.ц(this.з.method_46558());
               I.field_1724.method_31548().field_7545 = this.нБ;
               ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
               class_243 var8 = this.з.method_46558().method_1031(0.0, ОЫ, 0.0);
               I.field_1761.method_2896(I.field_1724, class_1268.field_5808, new class_3965(var8, class_2350.field_11036, this.з, false));
               I.field_1724.method_6104(class_1268.field_5808);
               this.нз = true;
               this.з = null;
            }
         }
      }
   }

   private int Э(class_1792 var1, int var2, boolean var3) {
      if (I.field_1724 != null && I.field_1761 != null) {
         int var4 = -1;

         for (int var5 = 9; var5 < 36; var5++) {
            if (I.field_1724.method_31548().method_5438(var5).method_7909() == var1) {
               var4 = var5;
               break;
            }
         }

         if (var4 == -1) {
            return -1;
         } else {
            int var7 = -1;

            for (int var6 = 0; var6 < 9; var6++) {
               if (var6 != var2 && I.field_1724.method_31548().method_5438(var6).method_7960()) {
                  var7 = var6;
                  break;
               }
            }

            if (var7 == -1) {
               var7 = var2 == 8 ? 7 : 8;
            }

            I.field_1761.method_2906(I.field_1724.field_7512.field_7763, var4, var7, class_1713.field_7791, I.field_1724);
            if (var3) {
               this.нЮ = var4;
               this.нl = var7;
            } else {
               this.нш = var4;
               this.нА = var7;
            }

            return var7;
         }
      } else {
         return -1;
      }
   }

   private class_2338 э(class_2338 var1) {
      if (I.field_1687 == null) {
         return null;
      } else {
         int[][] var2 = new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

         for (int var3 = 0; var3 <= 6; var3++) {
            int var4 = var1.method_10264() - var3;

            for (int[] var8 : var2) {
               class_2338 var9 = new class_2338(var1.method_10263() + var8[0], var4, var1.method_10260() + var8[1]);
               if (I.field_1687.method_8320(var9).method_45474()) {
                  class_2338 var10 = var9.method_10074();
                  if (I.field_1687.method_8320(var10).method_26212(I.field_1687, var10)) {
                     return var9;
                  }
               }
            }
         }

         return null;
      }
   }

   public void Э(Тт var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         if (this.з == null) {
            class_1297 var2 = var1.и();
            if (var2 instanceof class_1667) {
               class_1667 var3 = (class_1667)var2;
               class_1297 var4 = var3.method_24921();
               if (var4 == null || var4 == I.field_1724) {
                  if (var4 != null || !(var3.method_5858(I.field_1724) > ОЪ)) {
                     if (var3.method_5628() != this.ны) {
                        if (!(var3.method_18798().method_1027() < О6)) {
                           class_3965 var5 = this.Э(var3);
                           if (var5 != null && var5.method_17783() == class_240.field_1332) {
                              class_243 var6 = var5.method_17784();
                              class_2350 var7 = var5.method_17780();
                              class_243 var8 = new class_243((double)var7.method_10148(), (double)var7.method_10164(), (double)var7.method_10165())
                                 .method_1021(О9);
                              class_2338 var9 = class_2338.method_49638(var6.method_1019(var8));
                              class_2338 var10 = this.э(var9);
                              if (var10 != null) {
                                 double var11 = (double)((Float)this.нп.о()).floatValue();
                                 double var13 = var11 * var11;
                                 if (!(I.field_1724.method_5836(I.method_61966().method_60637(true)).method_1025(var10.method_46558()) > var13)) {
                                    int var15 = this.э(class_1802.field_8129);
                                    int var16 = this.э(class_1802.field_8069);
                                    if (var15 == -1 && (Boolean)this.50.о()) {
                                       var15 = this.Э(class_1802.field_8129, var16, true);
                                    }

                                    if (var16 == -1 && (Boolean)this.50.о()) {
                                       var16 = this.Э(class_1802.field_8069, var15, false);
                                    }

                                    if (var15 != -1 && var16 != -1) {
                                       this.з = var10;
                                       this.нБ = var15;
                                       this.нх = var16;
                                       this.нФ = true;
                                       this.нъ = I.field_1724.method_31548().field_7545;
                                       this.ны = var3.method_5628();
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void ц(class_243 var1) {
      class_243 var2 = I.field_1724.method_5836(I.method_61966().method_60637(true));
      class_243 var3 = var1.method_1020(var2);
      float var4 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var3.field_1350, var3.field_1352)) - О8);
      float var5 = (float)(-Math.toDegrees(Math.atan2(var3.field_1351, Math.hypot(var3.field_1352, var3.field_1350))));
      С8.И(new 2щ(var4, var5), ОЬ, 5, 12);
      I.field_1724
         .field_3944
         .method_52787(new class_2831(I.field_1724.method_36454(), I.field_1724.method_36455(), I.field_1724.method_24828(), I.field_1724.field_5976));
   }

   @Override
   public void ц() {
      super.ц();
      this.Щр();
   }

   private void Щь() {
      if (I.field_1724 != null && I.field_1761 != null) {
         int var1 = I.field_1724.field_7512.field_7763;
         if (this.нЮ != -1) {
            I.field_1761.method_2906(var1, this.нЮ, this.нl, class_1713.field_7791, I.field_1724);
            this.нЮ = -1;
         }

         if (this.нш != -1) {
            I.field_1761.method_2906(var1, this.нш, this.нА, class_1713.field_7791, I.field_1724);
            this.нш = -1;
         }
      }
   }

   private void Щр() {
      this.з = null;
      this.нз = false;
      this.нФ = false;
      this.нЮ = -1;
      this.нl = -1;
      this.нш = -1;
      this.нА = -1;
   }

   private int э(class_1792 var1) {
      if (I.field_1724 == null) {
         return -1;
      } else {
         for (int var2 = 0; var2 < 9; var2++) {
            if (I.field_1724.method_31548().method_5438(var2).method_7909() == var1) {
               return var2;
            }
         }

         return -1;
      }
   }
}
