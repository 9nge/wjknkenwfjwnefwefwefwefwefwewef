// Module: AimBot
// Category: Combat
// Original obfuscated class: sg.ec.21 (21.java)
package sg.ec.modules.combat;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Comparator;
import java.util.function.BooleanSupplier;
import java.util.stream.StreamSupport;
import net.minecraft.class_10142;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_293.class_5596;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class AimBot extends Module implements BooleanSupplier {
   static class_310 I;
   Ь 5Л;
   Ч3 51;
   static float фЦ = 360.0F;
   double фч;
   double фр;
   float ф4;
   float фц;
   double фИ;
   class_1309 Е;
   Ч3 5И;
   Ь 5з;
   float ф3;
   double фю;
   Фи К;
   float ф西;
   float ф1;
   double ф0;
   double ф>;
   static String фп = "AimBot";
   static String фО = "Автоматически наводится на цель при использовании лука, арбалета или трезубца";
   static String фД = "Дистанция";
   static float фа = 64.0F;
   static float фС = 8.0F;
   static float фй = 128.0F;
   static String фП = "Скорость наводки";
   static float фO = 80.0F;
   static float фл = 10.0F;
   static float фЙ = 180.0F;
   Ч3 5西;
   static String фи = "FOV";
   static float ф弟 = 90.0F;
   static float фу = 10.0F;
   static float ф必 = 360.0F;
   static String фБ = "Отрисовка FOV";
   static String фх = "Предикт снаряда";
   Ь 5Ф;
   static String фъ = "Цели";
   static String фы = "Игроков";
   static String фЮ = "Друзей";
   static String фl = "Голых";
   static String фш = "Животных";
   static String фА = "Монстров";
   static String фЯ = "Только видимых";
   double фЧ;
   double фЖ;
   float ф诶;
   float фХ;
   float фж;
   float фГ;
   float фг;

   private boolean НУ() {
      class_1799 var1 = I.field_1724.method_6047();
      class_1792 var2 = var1.method_7909();
      if (var2 instanceof class_1753) {
         return I.field_1724.method_6115() && I.field_1724.method_6030() == var1;
      } else if (var2 instanceof class_1764) {
         return class_1764.method_7781(var1);
      } else {
         return var2 != class_1802.field_8547 ? false : I.field_1724.method_6115() && I.field_1724.method_6030() == var1;
      }
   }

   private void Ч(Ф6 var1) {
      if ((Boolean)this.5Л.о() && I.field_1724 != null && !((Float)this.51.о() >= фЦ) && this.Нж()) {
         float var2 = (float)I.method_22683().method_4480() / 2.0F;
         float var3 = (float)I.method_22683().method_4507() / 2.0F;
         float var4 = var2 / 2.0F;
         float var5 = var3 / 2.0F;
         float var6 = (float)((Integer)I.field_1690.method_41808().method_41753()).intValue();
         float var7 = var3 / 2.0F;
         float var8 = (float)(
            (double)var7 * Math.tan(Math.toRadians((double)((Float)this.51.о()).floatValue() / фч)) / Math.tan(Math.toRadians((double)var6 / фр))
         );
         if (!(var8 <= 0.0F) && !(var8 > var2) && !(var8 > var3)) {
            int var9 = Math.clamp((long)((int)(var8 * 2.0F)), 32, 360);
            int var10 = ПЙ.ю(ь.TEXT);
            int var11 = 2Д.у(var10, ф4);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(class_10142.field_53876);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderSystem.lineWidth(фц);
            Matrix4f var12 = var1.9().method_51448().method_23760().method_23761();
            class_287 var13 = class_289.method_1348().method_60827(class_5596.field_29345, class_290.field_1576);

            for (int var14 = 0; var14 <= var9; var14++) {
               double var15 = фИ * (double)var14 / (double)var9;
               float var17 = var4 + (float)Math.cos(var15) * var8;
               float var18 = var5 + (float)Math.sin(var15) * var8;
               var13.method_22918(var12, var17, var18, 0.0F).method_39415(var11);
            }

            class_286.method_43433(var13.method_60800());
            GL11.glDisable(2848);
            RenderSystem.lineWidth(2.0F);
            RenderSystem.disableBlend();
         }
      }
   }

   private boolean Нж() {
      class_1792 var1 = I.field_1724.method_6047().method_7909();
      return var1 instanceof class_1753 || var1 instanceof class_1764 || var1 == class_1802.field_8547;
   }

   @Override
   public void Щ() {
      this.Е = null;
      super.Щ();
   }

   private boolean Я(class_1309 var1) {
      if (var1 != null && var1.method_5805() && var1 != I.field_1724 && !(var1 instanceof class_1531)) {
         if (Т0.Р(var1) > (double)((Float)this.5И.о()).floatValue()) {
            return false;
         } else if ((Boolean)this.5з.о() && !I.field_1724.method_6057(var1)) {
            return false;
         } else {
            if ((Float)this.51.о() < ф3) {
               class_243 var2 = var1.method_19538()
                  .method_1031(0.0, (double)(var1.method_17682() / 2.0F), 0.0)
                  .method_1020(I.field_1724.method_33571())
                  .method_1029();
               class_243 var3 = I.field_1724.method_5828(1.0F);
               double var4 = Math.toDegrees(Math.acos(Math.min(1.0, var3.method_1026(var2))));
               if (var4 > (double)((Float)this.51.о()).floatValue() / фю) {
                  return false;
               }
            }

            if (var1 instanceof class_1657) {
               class_1657 var6 = (class_1657)var1;
               if (Тр.З(var6)) {
                  return false;
               }
            }

            return ПУ.Ж(var1, this.К, true) || ПУ.э(var1, this.К) || ПУ.и(var1, this.К);
         }
      } else {
         return false;
      }
   }

   private int _/* $VF was: 1*/(double var1, float var3, float var4) {
      double var5 = 0.0;
      double var7 = (double)var3;

      for (int var9 = 0; var9 < 300; var9++) {
         var5 += var7;
         if (var5 >= var1) {
            return var9 + 1;
         }

         var7 *= (double)var4;
      }

      return 300;
   }

   private void _/* $VF was: 6*/(М var1) {
      this.Е = null;
   }

   private float[] _/* $VF was: 1*/(class_1309 var1, class_243 var2) {
      float var3 = this.8ы();
      float var4 = ф西;
      float var5 = ф1;
      class_243 var6 = var1.method_19538().method_1031(0.0, (double)var1.method_17682() / ф0, 0.0);
      class_243 var7 = new class_243(var1.method_23317() - var1.field_6014, var1.method_23318() - var1.field_6036, var1.method_23321() - var1.field_5969);
      class_243 var8 = var6;
      int var9 = 0;

      for (int var10 = 0; var10 < 5; var10++) {
         double var11 = var8.field_1352 - var2.field_1352;
         double var13 = var8.field_1350 - var2.field_1350;
         var9 = this.1(Math.sqrt(var11 * var11 + var13 * var13), var3, var5);
         var8 = var6.method_1019(var7.method_1021((double)var9));
      }

      class_243 var15 = var8.method_1031(0.0, this.1(var9, var4, var5), 0.0).method_1020(var2);
      return new float[]{
         (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var15.field_1350, var15.field_1352)) - ф>),
         (float)(-Math.toDegrees(Math.atan2(var15.field_1351, Math.hypot(var15.field_1352, var15.field_1350))))
      };
   }

   public _1/* $VF was: 21*/() {
      super(фп, фО, Пй.Combat);
      this.5И = new Ч3(фД, фа, фС, фй, 1.0F);
      this.5西 = new Ч3(фП, фO, фл, фЙ, 1.0F);
      this.51 = new Ч3(фи, ф弟, фу, ф必, 1.0F);
      this.5Л = new Ь(фБ, true);
      this.5Ф = new Ь(фх, true);
      this.К = new Фи(фъ, new Ь(фы, true), new Ь(фЮ, false), new Ь(фl, true), new Ь(фш, false), new Ь(фА, false));
      this.5з = new Ь(фЯ, true);
      this.ф(new ФЮ[]{this.5И, this.5西, this.51, this.5Л, this.5Ф, this.К, this.5з});
   }

   private void 必(Ц var1) {
      if (I.field_1724 != null && I.field_1687 != null && this.Нж() && this.НУ()) {
         this.Е = this.т();
         if (this.Е != null) {
            class_243 var2 = I.field_1724.method_33571();
            float var3;
            float var4;
            if ((Boolean)this.5Ф.о()) {
               float[] var5 = this.1(this.Е, var2);
               var3 = var5[0];
               var4 = var5[1];
            } else {
               class_243 var6 = this.Е.method_19538().method_1031(0.0, (double)this.Е.method_17682() / фЧ, 0.0).method_1020(var2);
               var3 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(var6.field_1350, var6.field_1352)) - фЖ);
               var4 = (float)(-Math.toDegrees(Math.atan2(var6.field_1351, Math.hypot(var6.field_1352, var6.field_1350))));
            }

            С8.И(new 2щ(var3, var4), (Float)this.5西.о(), ф诶, 2, 1);
         }
      } else {
         this.Е = null;
      }
   }

   private class_1309 т() {
      return StreamSupport.<class_1297>stream(I.field_1687.method_18112().spliterator(), false)
         .filter(var0 -> var0 instanceof class_1309)
         .map(var0 -> (class_1309)var0)
         .filter(this::Я)
         .min(Comparator.comparingDouble(var0 -> (double)var0.method_5739(I.field_1724)))
         .orElse(null);
   }

   private double _/* $VF was: 1*/(int var1, float var2, float var3) {
      double var4 = 0.0;
      double var6 = 0.0;

      for (int var8 = 0; var8 < var1; var8++) {
         double var9 = var4 * (double)var3;
         var4 = var9 - (double)var2;
         var6 += var4;
      }

      return -var6;
   }

   private float _ы/* $VF was: 8ы*/() {
      class_1799 var1 = I.field_1724.method_6047();
      class_1792 var2 = var1.method_7909();
      if (var2 instanceof class_1753) {
         return фХ;
      } else if (var2 instanceof class_1764) {
         return фж;
      } else {
         return var2 == class_1802.field_8547 ? фГ : фг;
      }
   }
}
