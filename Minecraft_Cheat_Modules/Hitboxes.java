package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;
import rockstar.feature.settings.impl.SliderSetting;

import lombok.Generated;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Hitboxes", method04432 = Category.field00395, method03909 = "modules.descriptions.hitboxes")
public class Hitboxes extends Module {
   private SliderSetting field00340;
   private MultiChoiceSetting field00331;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private MultiChoiceValue field01276;
   private MultiChoiceValue field01791;
   private MultiChoiceValue field01992;
   private MultiChoiceValue field01032;

   public Hitboxes() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00340 = new SliderSetting(this, "modules.settings.hitboxes.size").method00660(0.0F).method04520(1.0F).method03699(0.1F).method04137(0.3F);
      this.field00331 = new MultiChoiceSetting(this, "modules.settings.hitboxes.targets");
      this.field00332 = new MultiChoiceValue(this.field00331, "modules.settings.hitboxes.targets.players").select();
      this.field01604 = new MultiChoiceValue(this.field00331, "modules.settings.hitboxes.targets.animals").select();
      this.field00901 = new MultiChoiceValue(this.field00331, "modules.settings.hitboxes.targets.mobs").select();
      this.field01276 = new MultiChoiceValue(this.field00331, "modules.settings.hitboxes.targets.invisibles").select();
      this.field01791 = new MultiChoiceValue(this.field00331, "modules.settings.hitboxes.targets.naked_players").select();
      this.field01992 = new MultiChoiceValue(this.field00331, "rockUsers");
      this.field01032 = new MultiChoiceValue(this.field00331, "modules.settings.hitboxes.targets.friends");
   }

   public boolean method01995(LivingEntity var1) {
      if (var1 == null) {
         return false;
      } else {
         Class0465 var2 = new Class0466()
            .method03536(this.field00332.isSelected())
            .method05032(this.field01604.isSelected())
            .method03897(this.field00901.isSelected())
            .method05150(this.field01276.isSelected())
            .method05311(this.field01791.isSelected())
            .method03960(this.field01032.isSelected())
            .method04252(this.field01992.isSelected())
            .method00224();
         if (var1 instanceof ClientPlayerEntity) {
            return false;
         } else if (var1.isDead()) {
            return false;
         } else {
            return Rockstar.method00215().method04473() ? false : var2.method01960(var1);
         }
      }
   }

   @Generated
   public SliderSetting method00218() {
      return this.field00340;
   }
}
