// Module: Hit Boxes
// Category: Combat
// Original obfuscated class: sg.ec.Фщ (Фщ.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;
import net.minecraft.class_1309;

public class HitBoxes extends Module implements Supplier {
   String 5_д;
   float 5_7;
   float 5_Н;
   static Ч3 н7;
   String 5_Р;
   static Ь 5А;
   static String 5_в = "Hit Boxes";
   static String 5_Щ = "Изменяет размеры хитбоксов существ";

   public static void _ц/* $VF was: 7ц*/() {
      н7 = new Ч3(5_д, 5_7, 0.0F, 1.0F, 5_Н);
      5А = new Ь(5_Р, true);
   }

   public boolean _п/* $VF was: >п*/() {
      return this.7() && (Boolean)5А.о();
   }

   public HitBoxes() {
      super(5_в, 5_Щ, Пй.Combat);
      this.ф(new ФЮ[]{н7, 5А});
   }

   public void Х(Тэ var1) {
      if (var1.ф() instanceof class_1309) {
         var1.衣((Float)н7.о());
      }
   }
}
