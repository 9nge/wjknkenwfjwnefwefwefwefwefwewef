// Module: No Friend Damage
// Category: Combat
// Original obfuscated class: sg.ec.П西 (П西.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;
import net.minecraft.class_1297;
import net.minecraft.class_1657;

public class NoFriendDamage extends Module implements Supplier {
   static String 5Д衣 = "No Friend Damage";
   static String 5ДВ = "Отключает возможность наносить урон друзьям";

   public NoFriendDamage() {
      super(5Д衣, 5ДВ, Пй.Combat);
   }

   public void Г(ПЧ var1) {
      Й var2 = (Й)Ч.getInstance().getModuleManager().ь(Й.class);
      class_1297 var4 = var1.в();
      if (var4 instanceof class_1657) {
         class_1657 var3 = (class_1657)var4;
         if (Ч.getInstance().getFriendManager().>(var3.method_7334().getName()) && (!var2.7() || sg.ec.Й.П() != var1.в())) {
            var1.必(true);
         }
      }
   }
}
