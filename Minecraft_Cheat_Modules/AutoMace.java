// Module: Auto Mace
// Category: Combat
// Original obfuscated class: sg.ec.Фс (ФС.java)
package sg.ec.modules.combat;

import java.util.function.BooleanSupplier;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class AutoMace extends Module implements BooleanSupplier {
   static String 诶з = "Auto Mace";
   static String 诶т = "При попытке удара кого либо, автоматически свапает предмет на булаву";
   int 5衣 = -1;
   boolean 59 = false;
   static class_310 I;

   public AutoMace() {
      super(诶з, 诶т, Пй.Combat);
   }

   public void Гп(Ц var1) {
      if (!I.field_1724.method_24828()) {
         if (this.59 && this.5衣 != -1) {
            I.field_1724.method_31548().field_7545 = this.5衣;
            ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
            this.5衣 = -1;
            this.59 = false;
         }
      }
   }

   @Override
   public void Щ() {
      this.5衣 = -1;
      this.59 = false;
      super.Щ();
   }

   public void п(ПЧ var1) {
      if (I.field_1724 != null && I.field_1687 != null) {
         if (!I.field_1724.method_24828()) {
            class_1799 var2 = I.field_1724.method_6047();
            if (var2.method_7909() != class_1802.field_49814) {
               int var3 = П4.Е(class_1802.field_49814);
               if (var3 == -1 || var3 >= 9) {
                  return;
               }

               this.5衣 = I.field_1724.method_31548().field_7545;
               I.field_1724.method_31548().field_7545 = var3;
               ((sg.mx.1)I.field_1761).invokeSyncSelectedSlot();
               this.59 = true;
            }
         }
      }
   }
}
