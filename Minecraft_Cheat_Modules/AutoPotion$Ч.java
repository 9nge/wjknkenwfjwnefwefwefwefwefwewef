package sg.ec.modules.combat;

import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.class_243;

public class ТЬ$Ч implements Predicate {
   public boolean ю() {
      return this.Ш2;
   }

   public void _/* $VF was: 5*/() {
      if (this.Ш2) {
         this.西.Пи(0.0F);
      }
   }

   public void я() {
      if (!this.Ш2) {
         this.Ш2 = true;
         this.西.Пи(0.0F);
      }
   }

   public boolean Ь() {
      return this.Ш2 && this.西.ьр();
   }

   public float я() {
      return this.西.э1();
   }

   public ТЬ$Ч(class_243 var1) {
      this.Я = new Т4();
      this.5й = шд;
      this.Ш2 = false;
      this.西 = new ТЩ(1.0F, ш7, ПО.Ш);
      this.й = var1;
   }

   public class_243 м() {
      return this.й;
   }

   public Т4 Э() {
      return this.Я;
   }

   public boolean ф() {
      return !this.Ш2 || this.я() > шН;
   }

   public long Э() {
      Objects.requireNonNull(this);
      return шР;
   }

   static {
      шд = 0L;
      ш7 = 0.0F;
      шН = 0.0F;
      шР = 0L;
   }
}
