// Module: AimAssist
// Category: Combat
// Original obfuscated class: sg.ec.Тц (Тц.java)
package sg.ec.modules.combat;

import java.util.Comparator;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.stream.StreamSupport;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_1829;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;

public class AimAssist extends Module implements BooleanSupplier {
   static class_310 I;
   Ь нС;
   Ч3 Шь;
   Ч3 Шв;
   float Е0;
   Фи 弟;
   double Еэ;
   double Ее;
   double Е衣;
   double ЕВ;
   double ЕЗ;
   static String фе = "AimAssist";
   static String ф衣 = "Помощь в наводке";
   static String фВ = "Кого наводить";
   static String фЗ = "Игроков";
   static String фк = "Друзей";
   static String фЪ = "Голых";
   static String ф6 = "Жителей";
   static String ф9 = "Животных";
   static String ф_ = "Мобов";
   static String ф< = "Время блокировки";
   static float фЫ = 2.7F;
   static float ф8 = 8.0F;
   static float фЬ = 0.1F;
   Ч3 Ш2;
   static String фщ = "Дистанция наводки";
   static float фб = 4.0F;
   static float фо = 8.0F;
   static float Е5 = 0.1F;
   static String Ен = "Сила наводки";
   static float ЕШ = 1.25F;
   static float ЕЭ = 0.1F;
   static float ЕI = 0.01F;
   Ч3 Шм;
   static String Е2 = "Скорость прицеливания";
   static float Еь = 10.0F;
   static float Ем = 0.1F;
   static float Еф = 20.0F;
   static float ЕЕ = 0.1F;
   Ч3 Шф;
   static String Ев = "Значение плавности";
   static float ЕЩ = 8.5F;
   static float Ед = 10.0F;
   static float Е7 = 0.1F;
   Ч3 ШЕ;
   static String ЕН = "FOV";
   static float ЕР = 70.0F;
   static float ЕТ = 15.0F;
   static float ЕК = 180.0F;
   static String ЕУ = "Более \"человеческий\"";
   Ь нз;
   static String ЕЛ = "Включить вертикальную наводку";
   Ь нт;
   static String ЕФ = "Скорость вертикальн.";
   static float Ез = 0.23F;
   static float Ет = 0.01F;
   static float ЕМ = 0.01F;
   Ч3 ШЩ;
   static String Ея = "Шум вертикальной оси при горизонтальном движении";
   Ь нМ;
   static String Ес = "Значение шума";
   static float Еп = 0.13F;
   static float ЕО = 0.5F;
   static float ЕД = 0.01F;
   Ч3 Шд;
   static String Еа = "Генерировать \"затуп\"";
   Ь ня;
   static String ЕС = "Только при оружии";
   Ь нс;
   static String Ей = "Не двигать при противнике";
   Ь нп;
   static String ЕП = "От ввода";
   Ь нО;
   static String ЕO = "Предугадывание позиции";
   static float Ел = 1.5F;
   static float ЕЙ = 0.05F;
   Ч3 Ш7;
   static String Еи = "Предикт отталкивания";
   static float Е弟 = 1.5F;
   static float Еу = 0.05F;
   Ч3 ШН;
   static String Е必 = "Система мультипоинт";
   Ь нД;
   static String ЕБ = "Доводить при движении мыши";
   Ь на;
   static String Ех = "Только видимых";
   static String Еъ = "Выключать с AttackAura";
   Ь нй;
   Random Э;
   double Е>;
   double ЕХ;
   double Еж;
   double ЕГ;
   float Ег;
   double Е3;
   double Ею;
   class_1309 Р;
   long 5л;
   float Е1;
   String Еы;
   int ШЖ;
   float ЕЮ;
   float Еl;
   float Еш;
   float ЕА;
   float ЕЯ;
   float ЕЧ;
   float ЕЖ;
   float Е诶;
   float ЕЦ;
   float Еч;
   float Ер;
   float Е4;
   float Ец;
   float ЕИ;
   float Е西;

   private boolean ю(class_1297 var1) {
      if (!(var1 instanceof class_1309)) {
         return false;
      } else {
         class_1309 var2 = (class_1309)var1;
         if (var1 != I.field_1724 && var1.method_5805() && !(var1 instanceof class_1531)) {
            if (var1 instanceof class_1657) {
               class_1657 var3 = (class_1657)var1;
               if (Тр.З(var3)) {
                  return false;
               }
            }

            if ((Boolean)this.нС.о() && !I.field_1724.method_6057(var1)) {
               return false;
            } else if (I.field_1724.method_5739(var1) > (Float)this.Шь.о()) {
               return false;
            } else {
               return this.ф(var2) > (double)(this.Шв.о() * Е0)
                  ? false
                  : ПУ.Ж(var2, this.弟, true) || ПУ.Ж(var2, this.弟) || ПУ.э(var2, this.弟) || ПУ.诶(var2, this.弟);
            }
         } else {
            return false;
         }
      }
   }

   private static float а(float var0, float var1) {
      double var2 = (Double)I.field_1690.method_42495().method_41753() * Еэ + Ее;
      double var4 = var2 * var2 * var2 * Е衣;
      return (float)((double)var0 + Math.ceil((double)(var1 - var0) / var4 / ЕВ) * var4 * ЕЗ);
   }

   public AimAssist() {
      super(фе, ф衣, Пй.Combat);
      this.弟 = new Фи(фВ, new Ь(фЗ, true), new Ь(фк, false), new Ь(фЪ, true), new Ь(ф6, false), new Ь(ф9, false), new Ь(ф_, false));
      this.Ш2 = new Ч3(ф<, фЫ, 0.0F, ф8, фЬ);
      this.Шь = new Ч3(фщ, фб, 2.0F, фо, Е5);
      this.Шм = new Ч3(Ен, ЕШ, ЕЭ, 2.0F, ЕI);
      this.Шф = new Ч3(Е2, Еь, Ем, Еф, ЕЕ);
      this.ШЕ = new Ч3(Ев, ЕЩ, 0.0F, Ед, Е7);
      this.Шв = new Ч3(ЕН, ЕР, ЕТ, ЕК, 1.0F);
      this.нз = new Ь(ЕУ, false);
      this.нт = new Ь(ЕЛ, true);
      this.ШЩ = new Ч3(ЕФ, Ез, Ет, 1.0F, ЕМ, this.нт::о);
      this.нМ = new Ь(Ея, false, this.нт::о);
      this.Шд = new Ч3(Ес, Еп, 0.0F, ЕО, ЕД, this.нМ::о);
      this.ня = new Ь(Еа, false);
      this.нс = new Ь(ЕС, true);
      this.нп = new Ь(Ей, true);
      this.нО = new Ь(ЕП, false);
      this.Ш7 = new Ч3(ЕO, 0.0F, 0.0F, Ел, ЕЙ);
      this.ШН = new Ч3(Еи, 0.0F, 0.0F, Е弟, Еу);
      this.нД = new Ь(Е必, false);
      this.на = new Ь(ЕБ, true);
      this.нС = new Ь(Ех, true);
      this.нй = new Ь(Еъ, true);
      this.Э = new Random();
      this.ф(
         new ФЮ[]{
            this.弟,
            this.Ш2,
            this.Шь,
            this.Шм,
            this.Шф,
            this.ШЕ,
            this.Шв,
            this.нз,
            this.нт,
            this.ШЩ,
            this.нМ,
            this.Шд,
            this.ня,
            this.нс,
            this.нп,
            this.на,
            this.нО,
            this.Ш7,
            this.ШН,
            this.нД,
            this.нС,
            this.нй
         }
      );
   }

   private float[] _/* $VF was: 4*/(class_1309 var1) {
      class_243 var2 = this.ф(var1);
      double var3 = var2.field_1352 - I.field_1724.method_23317();
      double var5 = var2.field_1350 - I.field_1724.method_23321();
      double var7 = var1.method_23318() + (double)var1.method_17682() * Е>;
      if (var1.method_5715()) {
         var7 -= ЕХ;
      }

      double var9 = var7 - I.field_1724.method_23320();
      double var11 = Math.sqrt(var3 * var3 + var5 * var5);
      float var13 = (float)(Math.toDegrees(Math.atan2(var5, var3)) - Еж);
      float var14 = (float)(-Math.toDegrees(Math.atan2(var9, var11)));
      if ((Boolean)this.нД.о()) {
         double var15 = var1.method_23318() + (double)var1.method_17682() * ЕГ;
         double var17 = var15 - I.field_1724.method_23320();
         float var19 = (float)(-Math.toDegrees(Math.atan2(var17, var11)));
         var14 = (var14 + var19) * Ег;
      }

      return new float[]{var13, var14};
   }

   private class_243 ф(class_1309 var1) {
      double var2 = (double)((Float)this.Ш7.о()).floatValue();
      double var4 = (double)((Float)this.ШН.о()).floatValue();
      double var6 = var1.method_18798().field_1352 * (Е3 + var2);
      double var8 = var1.method_18798().field_1350 * (Ею + var2);
      if (var4 > 0.0) {
         var6 += (var1.method_23317() - var1.field_6014) * var4;
         var8 += (var1.method_23321() - var1.field_5969) * var4;
      }

      return new class_243(var1.method_23317() + var6, var1.method_23318(), var1.method_23321() + var8);
   }

   private class_1309 ю() {
      long var1 = System.currentTimeMillis();
      if (this.Р != null && var1 <= this.5л && this.ю((class_1297)this.Р)) {
         return this.Р;
      } else {
         class_1309 var3 = this.ь();
         this.Р = var3;
         if (var3 != null) {
            this.5л = var1 + (long)((Float)this.Ш2.о() * Е1);
         } else {
            this.5л = 0L;
         }

         return var3;
      }
   }

   private class_1309 ь() {
      return StreamSupport.<class_1297>stream(I.field_1687.method_18112().spliterator(), false)
         .filter(this::ю)
         .map(var0 -> (class_1309)var0)
         .min(Comparator.<class_1309>comparingDouble(this::ф).thenComparingDouble(var0 -> I.field_1724.method_5858(var0)))
         .orElse(null);
   }

   public void б(Ц var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         if (I.field_1755 == null) {
            if (I.field_1690.method_31044().method_31034()) {
               if ((Boolean)this.нй.о()) {
                  Й var2 = Ч.getInstance().getModuleManager().ю();
                  if (var2 != null && var2.7() && sg.ec.Й.П() != null) {
                     String var3 = var2.ъ() != null ? (String)var2.ъ().о() : "";
                     boolean var4 = var3 != null && var3.toLowerCase().contains(Еы);
                     if (!var4) {
                        return;
                     }
                  }
               }

               if (!(Boolean)this.нс.о() || I.field_1724.method_6047().method_7909() instanceof class_1829) {
                  if (!(Boolean)this.нО.о() || I.field_1690.field_1886.method_1434()) {
                     class_1309 var15 = this.ю();
                     if (var15 != null) {
                        if ((Boolean)this.ня.о()) {
                           if (this.ШЖ > 0) {
                              this.ШЖ--;
                              return;
                           }

                           if (this.Э.nextFloat() < ЕЮ) {
                              this.ШЖ = 1 + this.Э.nextInt(2);
                              return;
                           }
                        }

                        float[] var16 = this.4(var15);
                        float var17 = class_3532.method_15393(var16[0] - I.field_1724.method_36454());
                        float var5 = class_3532.method_15393(var16[1] - I.field_1724.method_36455());
                        if (!(Math.abs(var17) > (Float)this.Шв.о() * Еl)) {
                           if (!(Boolean)this.нп.о() || (Boolean)this.на.о() || !(Math.abs(var17) < Еш) || !(Math.abs(var5) < ЕА)) {
                              float var6 = class_3532.method_15363((ЕЯ - (Float)this.ШЕ.о()) / ЕЧ, ЕЖ, 1.0F);
                              float var7 = (Float)this.Шм.о();
                              float var8 = (Float)this.Шф.о();
                              float var9 = class_3532.method_15363(var17 * var7 * var6, -var8, var8);
                              if ((Boolean)this.нз.о()) {
                                 var9 += (this.Э.nextFloat() - Е诶) * ЕЦ;
                              }

                              float var10 = 0.0F;
                              if ((Boolean)this.нт.о()) {
                                 float var11 = (Float)this.ШЩ.о();
                                 var10 = class_3532.method_15363(var5 * var7 * var6, -var11, var11);
                                 if ((Boolean)this.нМ.о() && Math.abs(var9) > Еч) {
                                    var10 += (this.Э.nextFloat() - Ер) * this.Шд.о();
                                 }

                                 if ((Boolean)this.нз.о()) {
                                    var10 += (this.Э.nextFloat() - Е4) * Ец;
                                 }
                              }

                              float var18 = I.field_1724.method_36454() + var9;
                              float var12 = class_3532.method_15363(I.field_1724.method_36455() + var10, ЕИ, Е西);
                              float var13 = а(I.field_1724.method_36454(), var18);
                              float var14 = а(I.field_1724.method_36455(), var12);
                              I.field_1724.method_36456(var13);
                              I.field_1724.field_6241 = var13;
                              I.field_1724.field_6283 = var13;
                              I.field_1724.method_36457(var14);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private double ф(class_1309 var1) {
      float[] var2 = this.4(var1);
      return (double)Math.abs(class_3532.method_15393(var2[0] - I.field_1724.method_36454()));
   }

   @Override
   public void Щ() {
      super.Щ();
      this.Р = null;
      this.5л = 0L;
      this.ШЖ = 0;
   }
}
