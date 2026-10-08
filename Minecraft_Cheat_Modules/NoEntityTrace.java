// Module: No Entity Trace
// Category: Combat
// Original obfuscated class: sg.ec.л (Л.java)
package sg.ec.modules.combat;

import java.util.function.Supplier;

public class NoEntityTrace extends Module implements Supplier {
   static String 5кю = "No Entity Trace";
   static String 5кэ = "Отключает взаимодействие с сущностями";

   public NoEntityTrace() {
      super(5кю, 5кэ, Пй.Combat);
   }

   public void З(ПЫ var1) {
      var1.必(true);
   }
}
