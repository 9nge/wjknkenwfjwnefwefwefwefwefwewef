package sg.ec.modules.combat;

import java.util.function.ToDoubleFunction;
import net.minecraft.class_1304;
import net.minecraft.class_1320;
import net.minecraft.class_1322;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_1893;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import net.minecraft.class_9285;
import net.minecraft.class_9304;
import net.minecraft.class_9334;
import net.minecraft.class_9285.class_9287;

class ФС$Ч implements ToDoubleFunction {
   private float п() {
      float var1 = 0.0F;
      if (this.Ш.method_57826(class_9334.field_49633)) {
         class_9304 var2 = (class_9304)this.Ш.method_57824(class_9334.field_49633);
         if (var2 != null) {
            for (class_6880 var4 : var2.method_57534()) {
               int var5 = var2.method_57536(var4);
               if (var4.method_40225(class_1893.field_9111)) {
                  var1 += (float)var5 * 5рИ;
               } else if (var4.method_40225(class_1893.field_9107)) {
                  var1 += (float)var5 * 5р西;
               } else if (var4.method_40225(class_1893.field_9095)) {
                  var1 += (float)var5 * 5р1;
               } else if (var4.method_40225(class_1893.field_9096)) {
                  var1 += (float)var5 * 5р0;
               } else if (var4.method_40225(class_1893.field_9097)) {
                  var1 += (float)var5 * 5р>;
               } else if (var4.method_40225(class_1893.field_9119)) {
                  var1 += (float)var5 * 5рХ;
               }
            }
         }
      }

      return var1;
   }

   ФС$Ч(int var1, class_1799 var2, class_1304 var3) {
      this.Р = var1;
      this.Ш = var2;
   }

   float _/* $VF was: >*/() {
      if (!this.Ш.method_7960() && this.Ш.method_7909() instanceof class_1738) {
         float var1 = 0.0F;
         float var2 = 0.0F;
         if (this.Ш.method_57826(class_9334.field_49636)) {
            class_9285 var3 = (class_9285)this.Ш.method_57824(class_9334.field_49636);
            if (var3 != null) {
               for (class_9287 var5 : var3.comp_2393()) {
                  class_6880 var6 = var5.comp_2395();
                  String var7 = class_7923.field_41190.method_10221((class_1320)var6.comp_349()).toString();
                  if (var7.contains(5р诶)) {
                     class_1322 var8 = var5.comp_2396();
                     double var9 = var8.comp_2449();
                     if (var7.contains(5рЦ)) {
                        var2 += (float)var9;
                     } else if (!var7.contains(5рч)) {
                        var1 += (float)var9;
                     }
                  }
               }
            }
         }

         float var11 = this.п();
         return var1 * 5рр + var11 * 5р4 + var2 * 5рц;
      } else {
         return 0.0F;
      }
   }

   float Ы() {
      if (!this.Ш.method_7963()) {
         return 5рж;
      } else {
         int var1 = this.Ш.method_7936();
         int var2 = this.Ш.method_7919();
         return (float)(var1 - var2) / (float)var1 * 5рГ;
      }
   }

   static {
      5рр = 0.0F;
      5р4 = 0.0F;
      5рц = 0.0F;
      5рИ = 0.0F;
      5р西 = 0.0F;
      5р1 = 0.0F;
      5р0 = 0.0F;
      5р> = 0.0F;
      5рХ = 0.0F;
      5рж = 0.0F;
      5рГ = 0.0F;
   }
}
