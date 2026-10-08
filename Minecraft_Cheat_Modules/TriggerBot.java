package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.Hand;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Trigger Bot", method04432 = Category.field00395, method03909 = "modules.descriptions.trigger_bot")
public class TriggerBot extends Module {
   private BooleanSetting field00318;
   private BooleanSetting field01594;
   private BooleanSetting field00893;
   private MultiChoiceSetting field00331;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private MultiChoiceValue field01276;
   private MultiChoiceValue field01791;
   private MultiChoiceValue field01992;
   private MultiChoiceValue field01032;
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private ModeValue field00898;
   private final Class1325 field00606 = new Class1325();

   public TriggerBot() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00318 = new BooleanSetting(this, "modules.settings.aura.onlyCrits").method00203();
      this.field01594 = new BooleanSetting(this, "modules.settings.aura.smart_criticals", () -> !this.field00318.method04473());
      this.field00893 = new BooleanSetting(this, "modules.settings.aura.useHit");
      this.field00331 = new MultiChoiceSetting(this, "modules.settings.aura.targets");
      this.field00332 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.players").select();
      this.field01604 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.animals").select();
      this.field00901 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.mobs").select();
      this.field01276 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.invisibles").select();
      this.field01791 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.nakedPlayers").select();
      this.field01992 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.rockUsers");
      this.field01032 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.friends");
      this.field00327 = new ModeSetting(this, "modules.settings.aura.sprint_reset");
      this.field00328 = new ModeValue(this.field00327, "modules.settings.aura.sprint_reset.smart").select();
      this.field01600 = new ModeValue(this.field00327, "modules.settings.aura.sprint_reset.normal");
      this.field00898 = new ModeValue(this.field00327, "modules.settings.aura.sprint_reset.packet");
   }

   @Compile(obfuscation = 1)
   @Override
   public void method03678() {
      if (field00117.player != null && field00117.interactionManager != null) {
         if (this.method04335()) {
            super.method03678();
         } else {
            if (field00117.targetedEntity instanceof LivingEntity var1 && this.method00224().method01960(var1)) {
               if (Class0867.method00452()) {
                  super.method03678();
                  return;
               }

               if (this.method05168() && Class0860.method04769(field00117.player)) {
                  Class0867.method02037(field00117.player);
                  super.method03678();
                  return;
               }

               if (this.method01995(var1)) {
                  if (this.method03794(var1)) {
                     super.method03678();
                     return;
                  }

                  this.method01994(var1);
               }
            }

            super.method03678();
         }
      }
   }

   @Compile(obfuscation = 1)
   private boolean method01995(LivingEntity var1) {
      if (field00117.player == null) {
         return false;
      }

      if (this.method04335()) {
         return false;
      }

      if (AntiBot.method01995(var1)) {
         return false;
      }

      if (var1 != field00117.player && !var1.isRemoved() && var1.isAlive()) {
         if (!this.method00224().method01960(var1)) {
            return false;
         } else if (!(field00117.player.getAttackCooldownProgress(0.0F) < 0.8F) && this.field00606.method00947(500L)) {
            Criticals var2 = Rockstar.method00215().method00264().method01099(Criticals.class);
            return var2.method05168() && !var2.method04272() ? false : !this.method05168() || !this.method04762(var1) || Class0802.method02020(var1, true);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean method04335() {
      return this.field00893.method04473() && field00117.player != null && field00117.player.isUsingItem()
         ? field00117.player.getActiveItem().getItem().getUseAction(field00117.player.getActiveItem()) == UseAction.EAT
         : false;
   }

   private Class0465 method00224() {
      return new Class0466()
         .method03536(this.field00332.isSelected())
         .method05032(this.field01604.isSelected())
         .method03897(this.field00901.isSelected())
         .method05150(this.field01276.isSelected())
         .method05311(this.field01791.isSelected())
         .method03960(this.field01032.isSelected())
         .method04252(this.field01992.isSelected())
         .method04292(false)
         .method00224();
   }

   private boolean method04762(LivingEntity var1) {
      float var2 = Rockstar.method00215().method00264().method01099(Aura.class).method01989(var1);
      return var2 <= var1.getHealth();
   }

   private void method01994(LivingEntity var1) {
      field00117.interactionManager.attackEntity(field00117.player, var1);
      field00117.player.swingHand(Hand.MAIN_HAND);
      this.field00606.method00451();
   }

   private boolean method05168() {
      return !this.field01594.method04473()
         ? this.field00318.method04473()
         : field00117.options != null && field00117.options.jumpKey.isPressed() || !field00117.player.isOnGround();
   }

   private boolean method03794(LivingEntity var1) {
      boolean var2 = this.field00327.method02964(this.field01600);
      boolean var3 = this.field00327.method02964(this.field00898);
      if (Rockstar.method00215().method00264().method01099(KnockbackTweaks.class).method04473()) {
         return false;
      }

      if ((var2 || var3) && field00117.player != null) {
         if (Class0867.method00452() || Class0867.method01960(field00117.player)) {
            return true;
         } else if (!field00117.player.isSprinting()) {
            Class0867.method04754(field00117.player);
            return false;
         } else {
            Class0867.method02043(field00117.player, () -> this.method04761(var1), var3);
            return true;
         }
      } else {
         return false;
      }
   }

   private void method04761(LivingEntity var1) {
      if (this.method04473() && field00117.player != null && field00117.interactionManager != null && var1 != null && !var1.isRemoved() && var1.isAlive()) {
         if (this.method01995(var1)) {
            this.method01994(var1);
         }
      }
   }

   public boolean method04272() {
      if (!this.field00327.method02964(this.field00328)) {
         return false;
      } else if (Rockstar.method00215().method00264().method01099(KnockbackTweaks.class).method04473()) {
         return false;
      } else if (!(field00117.targetedEntity instanceof LivingEntity var1 && field00117.player != null)) {
         return false;
      } else {
         if (!this.method00224().method01960(var1)) {
            return false;
         }

         if (field00117.player.isSubmergedInWater()) {
            return false;
         }

         Criticals var4 = Rockstar.method00215().method00264().method01099(Criticals.class);
         boolean var3 = var4.method05168() && (var4.method04335() && this.field00606.method00947(500L) || field00117.player.isOnGround())
            || !field00117.player.isOnGround()
               && Class0906.method01827(field00117.player)
                  .method00752(
                     Class0802.method01989(var1),
                     !Class0930.method03288(Class0927.field00498) && !Class0930.method03288(Class0927.field01445) && !Class0930.method04122()
                        ? 1
                        : Class1010.field00062.nextInt(3)
                  );
         return this.method05168()
            && this.method04762(var1)
            && (
               var3
                  || Class0802.method02020(var1, true)
                  || !this.field00606
                     .method00947(!Class0930.method03288(Class0927.field01667) && !Class0930.method04122() ? 50L : (long)Class1010.method05090(50.0F, 150.0F))
            );
      }
   }

   @Override
   public void method05070() {
      super.method05070();
   }

   @Override
   public void method05256() {
      super.method05256();
      if (field00117.player != null) {
         Class0867.method04754(field00117.player);
      }
   }
}
