// Module: Auto Crystal
// Category: Combat
// Original obfuscated class: sg.ec.Фд (ФД.java)
package sg.ec.modules.combat;

import com.mojang.blaze3d.platform.GlStateManager.class_4534;
import com.mojang.blaze3d.platform.GlStateManager.class_4535;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import net.minecraft.class_10142;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1657;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_2868;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_4587;
import net.minecraft.class_293.class_5596;
import org.joml.Matrix4f;

public class AutoCrystal extends Module implements Supplier {
   class_2338 К;
   class_1511 н;
   int 5ю;
   int 5э;
   class_1657 Э;
   int 5е;
   boolean 5Ъ;
   long 9;
   static class_310 I;
   float нЕ;
   float нв;
   ТЙ Й;
   Ь 5и;
   String 5I5;
   String 5Iн;
   Ь 5弟;
   String 5ЭЗ;
   long 5Эк;
   Ч3 5Ъ;
   Ч3 56;
   float 5I2;
   float 5Iь;
   float 5Iм;
   float 5Iф;
   float 5IЕ;
   float 5Iв;
   float 5IЩ;
   float 5Iд;
   float 5I7;
   float 5IН;
   double 5IШ;
   Ч3 5к;
   static String 5Эц = "Auto Crystal";
   static String 5ЭИ = "Автоматически ставит и ломает кристаллы";
   static String 5Э西 = "Радиус";
   static float 5Э1 = 5.0F;
   static float 5Э0 = 6.0F;
   static float 5Э> = 0.1F;
   static String 5ЭХ = "Задержка установки";
   static float 5Эж = 20.0F;
   static String 5ЭГ = "Задержка ломания";
   static float 5Эг = 20.0F;
   static String 5Э3 = "Авто установка";
   static String 5Эю = "Рендер";
   static String 5Ээ = "Режим свапа";
   static String 5Эе = "Рука";
   static String 5Э衣 = "Рука";
   static String 5ЭВ = "Пакет";
   static double 5Эо = Double.MAX_VALUE;
   double 5ЭЪ;
   double 5Э6;
   double 5Э9;
   double 5Э_;
   double 5Э<;
   float 5ЭЫ;
   float 5Э8;
   float 5ЭЬ;
   float 5Эщ;
   float 5Эб;
   double 5IЭ;
   double 5IР;
   double 5IТ;
   double 5II;

   private void Ь诶() {
      this.К = null;
      this.н = null;
      this.5ю = 0;
      this.5э = 0;
      this.Э = null;
      this.5е = -1;
      this.5Ъ = false;
      this.9 = 0L;
      if (I.field_1724 != null) {
         this.нЕ = I.field_1724.method_36454();
         this.нв = I.field_1724.method_36455();
      }
   }

   public int ЫД() {
      return this.5ю;
   }

   private void И(class_4587 var1, class_238 var2, float var3, float var4, float var5, float var6) {
      Matrix4f var7 = var1.method_23760().method_23761();
      class_287 var8 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      float var9 = (float)var2.field_1323;
      float var10 = (float)var2.field_1322;
      float var11 = (float)var2.field_1321;
      float var12 = (float)var2.field_1320;
      float var13 = (float)var2.field_1325;
      float var14 = (float)var2.field_1324;
      var8.method_22918(var7, var9, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var14).method_22915(var3, var4, var5, var6);
      class_286.method_43433(var8.method_60800());
   }

   public class_1657 Д() {
      return this.Э;
   }

   public ТЙ ф() {
      return this.Й;
   }

   public Ь 西() {
      return this.5и;
   }

   private void ф(class_2338 var1) {
      int var2 = this.ЫЬ();
      if (var2 != -1) {
         this.С(var1);
         int var3 = I.field_1724.method_31548().field_7545;
         if (this.Й.Я(5I5)) {
            I.field_1724.method_31548().field_7545 = var2;
            this.Ьф();
         } else {
            I.method_1562().method_52787(new class_2868(var2));
         }

         class_243 var4 = I.field_1724.method_33571();
         class_243 var5 = Т0.5(var4, new class_238(var1));
         class_243 var6 = var5.method_1020(var4).method_22882();
         I.field_1761
            .method_2896(
               I.field_1724,
               class_1268.field_5808,
               new class_3965(var5, class_2350.method_10147((float)var6.field_1352, (float)var6.field_1351, (float)var6.field_1350), var1, false)
            );
         I.field_1724.method_6104(class_1268.field_5808);
         if (this.Й.Я(5Iн)) {
            this.5е = var3;
            this.5Ъ = true;
            this.9 = System.currentTimeMillis();
         } else {
            I.method_1562().method_52787(new class_2868(var3));
         }
      }
   }

   private boolean ф(class_2338 var1) {
      if (!this.С(var1)) {
         return false;
      } else {
         class_2338 var2 = var1.method_10084();
         if (!I.field_1687.method_8320(var2).method_26215()) {
            return false;
         } else if (!I.field_1687.method_8320(var2.method_10084()).method_26215()) {
            return false;
         } else {
            class_238 var3 = new class_238(
               (double)var2.method_10263(),
               (double)var2.method_10264(),
               (double)var2.method_10260(),
               (double)(var2.method_10263() + 1),
               (double)(var2.method_10264() + 2),
               (double)(var2.method_10260() + 1)
            );

            for (class_1297 var5 : I.field_1687.method_18112()) {
               if (var5 != I.field_1724 && var5.method_5829().method_994(var3)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private void И(class_1511 var1) {
      if (var1 != null) {
         I.field_1761.method_2918(I.field_1724, var1);
         I.field_1724.method_6104(class_1268.field_5808);
      }
   }

   @Override
   public void Щ() {
      if (I.field_1724 != null && I.field_1687 != null && this.5е != -1 && this.5е < 9) {
         I.field_1724.method_31548().field_7545 = this.5е;
         this.Ьф();
      }

      this.Ь诶();
      super.Щ();
   }

   public float Ыл() {
      return this.нв;
   }

   public Ь у() {
      return this.5弟;
   }

   public void Ы2(Ц var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         this.5ю++;
         this.5э++;
         if (this.5Ъ && this.Й.Я(5ЭЗ)) {
            long var2 = System.currentTimeMillis() - this.9;
            if (var2 >= 5Эк) {
               if (this.5е != -1 && this.5е < 9) {
                  I.field_1724.method_31548().field_7545 = this.5е;
                  this.Ьф();
                  this.5е = -1;
               }

               this.5Ъ = false;
            }
         }

         this.Э = this.Ь();
         if (this.Э == null) {
            this.К = null;
            this.нЕ = I.field_1724.method_36454();
            this.нв = I.field_1724.method_36455();
         } else {
            if ((Boolean)this.5и.о() && (float)this.5ю >= (Float)this.5Ъ.о()) {
               this.К = this.С();
               if (this.К != null) {
                  this.ф(this.К);
                  this.5ю = 0;
               }
            }

            if ((float)this.5э >= (Float)this.56.о()) {
               this.н = this.И();
               if (this.н != null) {
                  this.Ь(this.н);
                  this.И(this.н);
                  this.5э = 0;
               }
            }
         }
      }
   }

   private void ф(float var1, float var2) {
      float var3 = class_3532.method_15393(var1 - this.нЕ);
      float var4 = var2 - this.нв;
      float var5 = this.нЕ + var3;
      float var6 = this.нв + var4;
      float var7 = Т2.МС();
      var5 -= (var5 - this.нЕ) % var7;
      var6 -= (var6 - this.нв) % var7;
      if (var6 > 5I2) {
         var6 = 5Iь;
      }

      if (var6 < 5Iм) {
         var6 = 5Iф;
      }

      if (var5 == this.нЕ && var6 == this.нв) {
         int var8 = ThreadLocalRandom.current().nextInt(1, 4);
         if (ThreadLocalRandom.current().nextBoolean()) {
            var5 += var7 * (float)var8 * (float)(ThreadLocalRandom.current().nextBoolean() ? 1 : -1);
         } else {
            var6 += var7 * (float)var8 * (float)(ThreadLocalRandom.current().nextBoolean() ? 1 : -1);
         }

         var6 = class_3532.method_15363(var6, 5IЕ, 5Iв);
      }

      С8.И(new 2щ(var5, var6), 5IЩ, 5Iд, 5I7, 5IН, 1, 2, false);
      this.нЕ = var5;
      this.нв = var6;
   }

   @Override
   public void ц() {
      super.ц();
      if (I.field_1724 != null) {
         this.нЕ = I.field_1724.method_36454();
         this.нв = I.field_1724.method_36455();
      }

      this.Ь诶();
   }

   public long м() {
      return this.9;
   }

   private class_1511 И() {
      class_1511 var1 = null;
      double var2 = 5IШ;

      for (class_1297 var5 : I.field_1687.method_18112()) {
         if (var5 instanceof class_1511) {
            class_1511 var6 = (class_1511)var5;
            double var7 = (double)I.field_1724.method_5739(var5);
            if (!(var7 > (double)((Float)this.5к.о()).floatValue()) && this.Э != null) {
               double var9 = (double)this.Э.method_5739(var6);
               if (var9 < var2) {
                  var2 = var9;
                  var1 = var6;
               }
            }
         }
      }

      return var1;
   }

   private class_2338 С() {
      if (this.Э == null) {
         return null;
      } else {
         ArrayList var1 = new ArrayList();
         class_2338 var2 = I.field_1724.method_24515();
         int var3 = (int)Math.ceil((double)((Float)this.5к.о()).floatValue());

         for (int var4 = -var3; var4 <= var3; var4++) {
            for (int var5 = -var3; var5 <= var3; var5++) {
               for (int var6 = -var3; var6 <= var3; var6++) {
                  class_2338 var7 = var2.method_10069(var4, var5, var6);
                  double var8 = I.field_1724.method_19538().method_1022(class_243.method_24953(var7));
                  if (!(var8 > (double)((Float)this.5к.о()).floatValue()) && this.ф(var7)) {
                     var1.add(var7);
                  }
               }
            }
         }

         if (var1.isEmpty()) {
            return null;
         } else {
            var1.sort(Comparator.comparingDouble(var1x -> {
               class_243 var2x = new class_243((double)var1x.method_10263() + 5IР, (double)(var1x.method_10264() + 1), (double)var1x.method_10260() + 5IТ);
               return this.Э.method_19538().method_1022(var2x);
            }));
            return (class_2338)var1.get(0);
         }
      }
   }

   public int Ы2() {
      return this.5э;
   }

   public Ч3 Ж() {
      return this.5к;
   }

   private boolean С(class_2338 var1) {
      return I.field_1687.method_8320(var1).method_26204() == class_2246.field_10540 || I.field_1687.method_8320(var1).method_26204() == class_2246.field_9987;
   }

   private void Ь(class_4587 var1, class_238 var2, float var3, float var4, float var5, float var6) {
      Matrix4f var7 = var1.method_23760().method_23761();
      class_287 var8 = class_289.method_1348().method_60827(class_5596.field_29344, class_290.field_1576);
      float var9 = (float)var2.field_1323;
      float var10 = (float)var2.field_1322;
      float var11 = (float)var2.field_1321;
      float var12 = (float)var2.field_1320;
      float var13 = (float)var2.field_1325;
      float var14 = (float)var2.field_1324;
      var8.method_22918(var7, var9, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var11).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var12, var13, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var10, var14).method_22915(var3, var4, var5, var6);
      var8.method_22918(var7, var9, var13, var14).method_22915(var3, var4, var5, var6);
      class_286.method_43433(var8.method_60800());
   }

   public AutoCrystal() {
      super(5Эц, 5ЭИ, Пй.Combat);
      this.5к = new Ч3(5Э西, 5Э1, 1.0F, 5Э0, 5Э>);
      this.5Ъ = new Ч3(5ЭХ, 0.0F, 0.0F, 5Эж, 1.0F);
      this.56 = new Ч3(5ЭГ, 0.0F, 0.0F, 5Эг, 1.0F);
      this.5и = new Ь(5Э3, true);
      this.5弟 = new Ь(5Эю, true);
      this.Й = new ТЙ(5Ээ, 5Эе, 5Э衣, 5ЭВ);
      this.5е = -1;
      this.5Ъ = false;
      this.9 = 0L;
      this.ф(new ФЮ[]{this.5к, this.5Ъ, this.56, this.5и, this.5弟, this.Й});
   }

   private class_1657 Ь() {
      class_1657 var1 = null;
      double var2 = 5Эо;

      for (class_1657 var5 : I.field_1687.method_18456()) {
         if (var5 != I.field_1724
            && !var5.method_29504()
            && !(var5.method_6032() <= 0.0F)
            && !Ч.getInstance().getFriendManager().>(var5.method_5477().getString())) {
            double var6 = (double)I.field_1724.method_5739(var5);
            if (!(var6 > (double)((Float)this.5к.о() * 2.0F)) && var6 < var2) {
               var2 = var6;
               var1 = var5;
            }
         }
      }

      return var1;
   }

   public boolean ЬН() {
      return this.5Ъ;
   }

   public Ч3 ю() {
      return this.5Ъ;
   }

   private static int _/* $VF was: 2*/(int var0, int var1, int var2, int var3) {
      return var3 << 24 | var0 << 16 | var1 << 8 | var2;
   }

   public class_1511 Ь() {
      return this.н;
   }

   public void и(СЗ var1) {
      if ((Boolean)this.5弟.о() && I.field_1724 != null && I.field_1687 != null) {
         if (this.К != null && this.Э != null) {
            class_4587 var2 = var1.Ъ();
            class_243 var3 = I.field_1773.method_19418().method_19326();
            class_243 var4 = new class_243((double)this.К.method_10263() + 5ЭЪ, (double)(this.К.method_10264() + 1), (double)this.К.method_10260() + 5Э6);
            double var5 = this.Э.method_19538().method_1022(var4);
            double var7 = I.field_1724.method_19538().method_1022(var4);
            int var9;
            if (var5 <= 5Э9 && var7 > var5) {
               var9 = 2(0, 255, 50, 180);
            } else if (var5 <= 5Э_ && var7 >= var5 * 5Э<) {
               var9 = 2(255, 255, 0, 160);
            } else {
               var9 = 2(255, 80, 0, 120);
            }

            var2.method_22903();
            var2.method_22904(-var3.field_1352, -var3.field_1351, -var3.field_1350);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(class_4535.SRC_ALPHA, class_4534.ONE);
            RenderSystem.disableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.setShader(class_10142.field_53876);
            float var10 = (float)(var9 >> 16 & 0xFF) / 5ЭЫ;
            float var11 = (float)(var9 >> 8 & 0xFF) / 5Э8;
            float var12 = (float)(var9 & 0xFF) / 5ЭЬ;
            float var13 = (float)(var9 >> 24 & 0xFF) / 5Эщ;
            class_238 var14 = new class_238(this.К);
            this.И(var2, var14, var10, var11, var12, var13 * 5Эб);
            this.Ь(var2, var14, var10, var11, var12, var13);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            var2.method_22909();
         }
      }
   }

   private void С(class_2338 var1) {
      class_243 var2 = I.field_1724.method_33571();
      class_243 var3 = Т0.5(var2, new class_238(var1));
      class_243 var4 = var3.method_1020(var2);
      float var5 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var4.field_1350, var4.field_1352)) - 5IЭ);
      float var6 = (float)(-Math.toDegrees(Math.atan2(var4.field_1351, Math.hypot(var4.field_1352, var4.field_1350))));
      this.ф(var5, var6);
   }

   public class_2338 П() {
      return this.К;
   }

   private int ЫЬ() {
      for (int var1 = 0; var1 < 9; var1++) {
         if (I.field_1724.method_31548().method_5438(var1).method_7909() == class_1802.field_8301) {
            return var1;
         }
      }

      int var2 = П4.Е(class_1802.field_8301);
      return var2 != -1 && var2 < 9 ? var2 : -1;
   }

   private void Ьф() {
      if (I.field_1687 != null) {
         ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
      }
   }

   public int Ый() {
      return this.5е;
   }

   public float Ыд() {
      return this.нЕ;
   }

   private void Ь(class_1511 var1) {
      class_243 var2 = Т0.5(var1);
      float var3 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var2.field_1350, var2.field_1352)) - 5II);
      float var4 = (float)(-Math.toDegrees(Math.atan2(var2.field_1351, Math.hypot(var2.field_1352, var2.field_1350))));
      this.ф(var3, var4);
   }

   public Ч3 т() {
      return this.56;
   }
}
