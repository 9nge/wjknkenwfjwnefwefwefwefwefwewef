// Module: Auto GApple
// Category: Combat
// Original obfuscated class: sg.ec.ПI (ПI.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public final class AutoGApple extends Module implements Supplier {
   static class_310 I;
   Ь 5к;
   Ч3 нЖ;
   boolean нД;
   static String хю = "Auto GApple";
   static String хэ = "Автоматически использует золотые яблоки при низком уровне здоровья";
   static String хе = "Здоровье";
   static float х衣 = 14.0F;
   static float хВ = 20.0F;
   static float хЗ = 0.5F;
   static String хк = "Золотые сердца";

   public void Б(Т8 var1) {
      if (!I.field_1724.method_6079().method_7960()
         && (I.field_1724.method_6079().method_7909() == class_1802.field_8463 || I.field_1724.method_6079().method_7909() == class_1802.field_8367)) {
         if (I.field_1724.method_6032() + (this.5к.о() ? I.field_1724.method_6067() : 0.0F) <= (Float)this.нЖ.о()) {
            this.нД = true;
            if (I.field_1755 != null && !I.field_1724.method_6115()) {
               ((sg.mx.д)I).onItemUse();
            } else {
               I.field_1690.field_1904.method_23481(true);
            }
         } else if (this.нД) {
            this.нД = false;
            I.field_1690.field_1904.method_23481(false);
         }
      } else if (this.нД) {
         this.нД = false;
         I.field_1690.field_1904.method_23481(false);
      }
   }

   public AutoGApple() {
      super(хю, хэ, Пй.Combat);
      this.нЖ = new Ч3(хе, х衣, 1.0F, хВ, хЗ);
      this.5к = new Ь(хк, false);
      this.ф(new ФЮ[]{this.нЖ, this.5к});
   }
}
