package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;
import rockstar.feature.settings.impl.SliderSetting;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Items;
import pyrock.events.player.ClientPlayerTickEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Auto Totem", method04432 = Category.field00395, method03909 = "modules.descriptions.auto_totem")
public class AutoTotem extends Module {
   private static final int field00005 = 20;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private BooleanSetting field00318;
   private MultiChoiceSetting field00331;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private MultiChoiceValue field01276;
   private SliderSetting field00904;
   private SliderSetting field01279;
   private final Class0977 field00513 = new Class0977(new Class0976(), new Class0985());
   private int field01495;
   private int field00833;
   private final EventListener<ClientPlayerTickEvent> field00346 = var1 -> {
      if (field00117.player != null && field00117.world != null) {
         if (!(field00117.player.getMaxHealth() <= 2.0F)) {
            this.method04334();
            this.field00513.method03543(false);
            boolean var2 = this.method04335();
            boolean var3 = this.method05210();
            boolean var4 = field00117.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;
            boolean var5 = var2 && (!var3 || var4);
            if (!var5 && this.field00833 > 0) {
               this.field00833--;
            }

            if (var5) {
               this.field00833 = 20;
            }

            boolean var6 = var5 || this.field00833 > 0;
            if (!var2 || !var3 || var4) {
               if (this.field01495 > 0) {
                  this.field00513.method02082(Items.TOTEM_OF_UNDYING, var6, var0 -> true, this::method05168);
               } else if (!var2) {
                  this.field00513.method02082(Items.TOTEM_OF_UNDYING, false, var0 -> true, this::method05168);
               }
            }
         }
      }
   };

   public AutoTotem() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00340 = new SliderSetting(this, "modules.settings.auto_totem.health").method00660(1.0F).method04520(20.0F).method03699(0.5F).method04137(6.0F);
      this.field01607 = new SliderSetting(this, "modules.settings.auto_totem.elytra_health")
         .method00660(1.0F)
         .method04520(20.0F)
         .method03699(0.5F)
         .method04137(6.0F);
      this.field00318 = new BooleanSetting(this, "modules.settings.auto_totem.stop_using");
      this.field00331 = new MultiChoiceSetting(this, "modules.settings.auto_totem.select_with");
      this.field00332 = new MultiChoiceValue(this.field00331, "modules.settings.auto_totem.select_with.fall").select();
      this.field01604 = new MultiChoiceValue(this.field00331, "modules.settings.auto_totem.select_with.crystal");
      this.field00901 = new MultiChoiceValue(this.field00331, "modules.settings.auto_totem.select_with.tnt");
      this.field01276 = new MultiChoiceValue(this.field00331, "modules.settings.auto_totem.select_with.trident");
      this.field00904 = new SliderSetting(this, "modules.settings.auto_totem.tnt_distance", () -> !this.field00901.isSelected())
         .method00660(1.0F)
         .method04520(40.0F)
         .method03699(1.0F)
         .method04137(19.0F);
      this.field01279 = new SliderSetting(this, "modules.settings.auto_totem.crystal_distance", () -> !this.field01604.isSelected())
         .method00660(1.0F)
         .method04520(40.0F)
         .method03699(1.0F)
         .method04137(19.0F);
   }

   @Override
   public void method05070() {
      super.method05070();
      this.field00513.method03310(new Class1347(this));
   }

   private boolean method05168() {
      return this.field00318.method04473() || !field00117.player.isUsingItem();
   }

   public boolean method04272() {
      if (field00117.player == null || !this.method04473()) {
         return false;
      } else {
         return this.method04335() ? true : this.field00513.method00452() && field00117.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;
      }
   }

   public boolean method04335() {
      if (field00117.player == null) {
         return false;
      } else if (this.method05327()) {
         return true;
      } else if (this.field01604.isSelected() && this.method05355()) {
         return true;
      } else if (this.field00901.isSelected() && this.method03971()) {
         return true;
      } else {
         return this.field01276.isSelected() && this.method04060() ? true : this.field00332.isSelected() && this.method03994();
      }
   }

   private boolean method05210() {
      if (!Class0930.method03288(Class0927.field00498)) {
         return false;
      } else {
         return field00117.player != null && field00117.player.getItemCooldownManager() != null
            ? field00117.player.getItemCooldownManager().isCoolingDown(Items.TOTEM_OF_UNDYING.getDefaultStack())
            : false;
      }
   }

   private boolean method05327() {
      float var1 = field00117.player.getHealth() + field00117.player.getAbsorptionAmount();
      float var2 = field00117.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA
         ? this.field01607.method04086()
         : this.field00340.method04086();
      return var1 <= var2;
   }

   private boolean method05355() {
      double var1 = this.field01279.method04086();
      return !field00117.world.getEntitiesByClass(EndCrystalEntity.class, field00117.player.getBoundingBox().expand(var1), var0 -> true).isEmpty();
   }

   private boolean method03971() {
      double var1 = this.field00904.method04086();
      return !field00117.world.getEntitiesByClass(TntEntity.class, field00117.player.getBoundingBox().expand(var1), var0 -> true).isEmpty();
   }

   private boolean method03994() {
      if (!field00117.player.isOnGround()
         && !field00117.player.isGliding()
         && !field00117.player.isTouchingWater()
         && !field00117.player.isClimbing()
         && !field00117.player.isInLava()) {
         float var1 = field00117.player.getHealth() + field00117.player.getAbsorptionAmount();
         float var2 = Class0904.method02039(field00117.player, 30);
         return var2 >= var1;
      } else {
         return false;
      }
   }

   private boolean method04060() {
      return field00117.world
         .getEntitiesByClass(
            TridentEntity.class, field00117.player.getBoundingBox().expand(5.0), var0 -> var0.isAlive() && var0.getOwner() != field00117.player
         )
         .stream()
         .anyMatch(var0 -> var0.getVelocity().lengthSquared() > 0.1);
   }

   private void method04334() {
      this.field01495 = Class0993.method04443()
         .method03317(Class0993.method00338())
         .method03317(Class0993.method04110())
         .method03317(Class0993.method03663())
         .method02065(Items.TOTEM_OF_UNDYING)
         .size();
   }

   @Override
   public void method05256() {
      this.field00513.method03310(null);
      super.method05256();
      this.field00513.method00451();
      this.field00833 = 0;
   }
}
