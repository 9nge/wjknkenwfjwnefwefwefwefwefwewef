package rockstar.feature.modules.combat;
import rockstar.utils.player.InventoryUtils;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.SliderSetting;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Auto Soup", method04432 = Category.field00395)
public class AutoSoup extends Module {
   int field00005 = -1;
   int field01495 = -1;
   int field00833 = -1;
   private SliderSetting field00340;
   private final Class1325 field00606 = new Class1325();

   public AutoSoup() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00340 = new SliderSetting(this, "modules.settings.auto_soup.health").method03699(1.0F).method00660(1.0F).method04520(20.0F).method04137(10.0F);
   }

   @Override
   public void method03678() {
      if (this.field00833 >= 0) {
         if (this.field00833 == 2) {
            field00117.player.getInventory().selectedSlot = this.field01495;
         } else if (this.field00833 == 1) {
            field00117.interactionManager.interactItem(field00117.player, Hand.MAIN_HAND);
         } else if (this.field00833 == 0) {
            field00117.player.dropSelectedItem(true);
            field00117.player.getInventory().selectedSlot = this.field00005;
         }

         this.field00833--;
      } else if (!(field00117.player.getHealth() >= this.field00340.method04086()) && this.field00606.method00947(300L)) {
         Class1004 var1 = Class0993.method00338().method02069(Items.MUSHROOM_STEW);
         if (var1 != null) {
            this.field00005 = field00117.player.getInventory().selectedSlot;
            this.field01495 = var1.method03627();
            field00117.player.getInventory().selectedSlot = this.field01495;
            this.field00833 = 1;
         } else {
            List var2 = Class0993.method04443().method02065(Items.MUSHROOM_STEW);
            List var3 = Class0993.method00338().method01712(ItemStack::isEmpty);
            if (!var2.isEmpty() && !var3.isEmpty()) {
               int var4 = Math.min(var2.size(), var3.size());
               var4 = Math.min(var4, 8);

               for (int var5 = 0; var5 < var4; var5++) {
                  Class1007 var6 = (Class1007)var2.get(var5);
                  Class1004 var7 = (Class1004)var3.get(var5);
                  InventoryUtils.method05095(var6.method00004(), var7.method03627());
               }

               this.field00005 = field00117.player.getInventory().selectedSlot;
               this.field01495 = ((Class1004)var3.get(0)).method03627();
               this.field00833 = 2;
            }
         }

         this.field00606.method00451();
      }
   }
}
