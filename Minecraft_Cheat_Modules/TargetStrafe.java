// Module: Target Strafe
// Category: Combat
// Original obfuscated class: sg.ec.ФР (Фр.java)
package sg.ec.modules.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.class_1309;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class TargetStrafe extends Module implements Supplier {
   static class_310 I;
   Ь 5l;
   class_243 М;
   ТЙ 弟;
   static String сз = "HolyWorld Correct";
   double с7;
   float сН;
   float сР;
   float сТ;
   static float я9 = 1.5F;
   static float я_ = 0.91F;
   static float я< = 0.99F;
   static float яЫ = 0.05F;
   static float я8 = 0.05F;
   static float яЬ = 0.05F;
   static double ящ = 0.8F;
   static float яб = 0.3F;
   static float яо = 0.3F;
   static float с5 = 0.3F;
   static float сн = 0.65F;
   static float сШ = 0.65F;
   static float сЭ = 0.65F;
   static long сI = 100L;
   double с2;
   double сь;
   double см;
   double сф;
   double сЕ;
   double св;
   double сЩ;
   double сд;
   static String сФ = "HolyWorld New";
   static String я6 = "Collision";
   static String яЪ = "HolyWorld Correct";
   Map ш;
   static String сЛ = "HolyWorld New";
   static String яр = "Target Strafe";
   static String я4 = "Позволяет двигаться вокруг цели по кругу, улучшая маневренность в бою";
   static String яц = "Режим";
   static String яИ = "Collision";
   static String я西 = "Collision";
   static String я1 = "HolyWorld New";
   static String я0 = "Обгон";
   static String я> = "Предсказание";
   static float яХ = 5.0F;
   static float яж = 10.0F;
   static float яГ = 0.1F;
   Ч3 нф;
   static String яг = "Радиус ускорения";
   static float я3 = 0.5F;
   static float яю = 5.0F;
   static float яэ = 0.1F;
   Ч3 нЕ;
   static String яе = "Сила";
   static float я衣 = 0.03F;
   static float яВ = 0.01F;
   static float яЗ = 0.1F;
   static float як = 0.01F;
   Ч3 нв;
   double сК;
   static String сУ = "HolyWorld New";

   private void з(ЧН var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         class_1309 var2 = this.м();
         if (var2 == null) {
            this.М6();
         } else {
            class_243 var3 = var2.method_19538();
            if ((Boolean)this.5l.о()) {
               var3 = this.М(var2, var3);
            } else {
               this.М = null;
            }

            float var4 = this.弟诶();
            this.щ(var1, var3, var4);
         }
      } else {
         this.М6();
      }
   }

   private class_1309 м() {
      Й var1 = Ч.getInstance().getModuleManager().ю();
      if (var1 != null && var1.7()) {
         ЧТ var2 = (ЧТ)Ч.getInstance().getModuleManager().ь(ЧТ.class);
         if (var2 != null && var2.7()) {
            return null;
         } else {
            class_1309 var3 = sg.ec.Й.П();
            return var3 != null && var3.method_5805() ? var3 : null;
         }
      } else {
         return null;
      }
   }

   public class_1309 _/* $VF was: 4*/() {
      return this.м();
   }

   private void щ(ЧН var1, class_243 var2, float var3) {
      double var4 = var2.field_1352 - I.field_1724.method_23317();
      double var6 = var2.field_1350 - I.field_1724.method_23321();
      double var8 = Math.toDegrees(Math.atan2(var6, var4)) - с7;
      var8 = class_3532.method_15338(var8);
      float var10 = 0.0F;
      float var11 = 0.0F;
      float var12 = сН;

      for (float var13 = сР; var13 <= 1.0F; var13++) {
         for (float var14 = сТ; var14 <= 1.0F; var14++) {
            if (var13 != 0.0F || var14 != 0.0F) {
               double var15 = Math.toDegrees(Фо.р(var3, (double)var13, (double)var14));
               double var17 = Math.abs(var8 - class_3532.method_15338(var15));
               if (var17 < (double)var12) {
                  var12 = (float)var17;
                  var10 = var13;
                  var11 = var14;
               }
            }
         }
      }

      var1.и(var10);
      var1.х(var11);
   }

   private void Мз() {
      if (Фо.аЫ()) {
         class_1309 var1 = sg.ec.Й.П();
         if (var1 == null || I.field_1724.method_5739(var1) > я9) {
            return;
         }

         class_243 var2 = I.field_1724.method_19538();
         class_243 var3 = var1.method_19538();
         class_243 var4 = var3.method_1020(var2).method_1029();
         class_2338 var5 = class_2338.method_49637(
            I.field_1724.method_23317() + I.field_1724.method_18798().field_1352,
            I.field_1724.method_23318() + I.field_1724.method_18798().field_1351,
            I.field_1724.method_23321() + I.field_1724.method_18798().field_1350
         );
         float var6 = I.field_1687.method_8320(var5).method_26204().method_9499();
         float var7 = I.field_1724.method_24828() ? var6 * 1.0F : я_;
         float var8 = I.field_1724.method_24828() ? var6 : я<;
         double var9 = I.field_1724.method_18798().field_1351;
         float var11 = яЫ;
         float var12 = я8;
         float var13 = яЬ;
         double var14 = (double)I.field_1724.method_5739(var1);
         if (var14 < ящ) {
            var11 *= яб;
            var12 *= яо;
            var13 *= с5;
         } else if (var14 < 1.0) {
            var11 *= сн;
            var12 *= сШ;
            var13 *= сЭ;
         }

         float var16 = (float)(System.currentTimeMillis() / сI);
         float var17 = 0.0F;
         float var18 = (float)Math.cos(Math.toDegrees((double)var16)) * var17;
         float var19 = (float)Math.sin(Math.toDegrees((double)var16)) * var17;
         var4 = var4.method_1031((double)var18, 0.0, (double)var19);
         double var20 = I.field_1724.method_24828() ? (double)var11 : (I.field_1724.field_6017 > 0.0F ? (double)var12 : (double)var13);
         double var22 = var4.field_1352 * var20 * (double)var8 / (double)var7;
         double var24 = var4.field_1350 * var20 * (double)var8 / (double)var7;
         I.field_1724.method_18800(I.field_1724.method_18798().field_1352 + var22, var9, I.field_1724.method_18798().field_1350 + var24);
      }
   }

   private class_243 М(class_1309 var1, class_243 var2) {
      double var3 = this.4(var1);
      if (var3 >= с2) {
         double var5 = var1.method_23317() - var1.field_6014;
         double var7 = var1.method_23321() - var1.field_5969;
         double var9 = Math.sqrt(var5 * var5 + var7 * var7);
         if (var9 > сь) {
            double var11 = var5 / var9;
            double var13 = var7 / var9;
            double var15;
            if (var3 >= см) {
               var15 = сф;
            } else if (var3 >= сЕ) {
               var15 = 1.0;
            } else if (var3 >= св) {
               var15 = сЩ;
            } else {
               var15 = сд;
            }

            this.М = var2.method_1031(var11 * var15, 0.0, var13 * var15);
            return this.М;
         }
      }

      this.М = var2;
      return this.М;
   }

   public void 弟Ф(Ц var1) {
      if (this.弟.Я(я6)) {
         this.Мз();
      }
   }

   public void м(ЧН var1) {
      if (this.弟.Я(яЪ)) {
         this.з(var1);
      }
   }

   @Override
   public void Щ() {
      super.Щ();
      this.М6();
      this.ш.clear();
   }

   private void М6() {
      this.М = null;
   }

   private float 弟诶() {
      return С8.И().па() && sg.ec.Й.П() != null ? ТI.ЧВ() : I.field_1724.method_36454();
   }

   public TargetStrafe() {
      super(яр, я4, Пй.Combat);
      this.弟 = new ТЙ(яц, яИ, я西, я1);
      this.5l = new Ь(я0, true, () -> this.弟.Я(сз));
      this.нф = new Ч3(я>, яХ, 0.0F, яж, яГ, () -> this.弟.Я(сФ));
      this.нЕ = new Ч3(яг, 2.0F, я3, яю, яэ, () -> this.弟.Я(сЛ));
      this.нв = new Ч3(яе, я衣, яВ, яЗ, як, () -> this.弟.Я(сУ));
      this.М = null;
      this.ш = new HashMap();
      this.ф(new ФЮ[]{this.弟, this.5l, this.нф, this.нЕ, this.нв});
   }

   private double _/* $VF was: 4*/(class_1309 var1) {
      class_243 var2 = var1.method_19538();
      class_243 var3 = this.ш.getOrDefault(var1, var2);
      double var4 = var2.field_1352 - var3.field_1352;
      double var6 = var2.field_1350 - var3.field_1350;
      double var8 = Math.sqrt(var4 * var4 + var6 * var6) * сК;
      this.ш.put(var1, var2);
      return var8;
   }
}
