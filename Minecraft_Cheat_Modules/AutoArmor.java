package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.SliderSetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.screen.slot.SlotActionType;
import pyrock.events.player.ClientPlayerTickEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Auto Armor", method04432 = Category.field00395, method03909 = "modules.descriptions.auto_armor")
public class AutoArmor extends Module {
   private final Class1325 field00606 = new Class1325();
   private SliderSetting field00340;
   private BooleanSetting field00318;
   private final EventListener<ClientPlayerTickEvent> field00346 = var1 -> {
      PlayerInventory var2 = field00117.player.getInventory();
      int[] var3 = new int[4];
      int[] var4 = new int[4];
      this.method02050(var2, var3, var4);
      ArrayList<Integer> var5 = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
      Collections.shuffle(var5);

      for (int var7 : (Iterable<Integer>)(Iterable<?>) (var5)) {
         int var8 = var3[var7];
         if (var8 != -1) {
            ItemStack var9 = var2.getArmorStack(var7);
            if ((var9.isEmpty() || var2.getEmptySlot() != -1)
               && (!this.field00318.method04473() || field00117.player.getEquippedStack(EquipmentSlot.CHEST).getItem() != Items.ELYTRA || var7 != 2)) {
               this.method02049(var2, var8, var7);
               break;
            }
         }
      }
   };

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00340 = new SliderSetting(this, "modules.settings.auto_armor.delay")
         .method00660(50.0F)
         .method04520(1000.0F)
         .method03699(1.0F)
         .method04137(250.0F)
         .method01202(" ms");
      this.field00318 = new BooleanSetting(this, "modules.settings.auto_armor.elytra");
   }

   public AutoArmor() {
      this.method04271();
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void method02050(PlayerInventory var1, int[] var2, int[] var3) {
      for (int var4 = 0; var4 < 4; var4++) {
         var2[var4] = -1;
         ItemStack var5 = var1.getArmorStack(var4);
         if (!var5.isEmpty() && var5.getItem() instanceof ArmorItem var6) {
            var3[var4] = this.method02061(var6, var5);
         }
      }

      for (int var10 = 0; var10 < 36; var10++) {
         ItemStack var11 = var1.getStack(var10);
         if (!var11.isEmpty() && var11.getItem() instanceof ArmorItem var12) {
            EquipmentSlot var14 = ((Class1109)var12).rockstar$getType().getEquipmentSlot();
            byte var8;
            switch (var14) {
               case HEAD:
                  var8 = 3;
                  break;
               case CHEST:
                  var8 = 2;
                  break;
               case LEGS:
                  var8 = 1;
                  break;
               case FEET:
                  var8 = 0;
                  break;
               default:
                  continue;
            }

            int var9 = this.method02061(var12, var11);
            if (var9 > var3[var8]) {
               var2[var8] = var10;
               var3[var8] = var9;
            }
         }
      }
   }

   private void method02049(PlayerInventory var1, int var2, int var3) {
      if (var2 < 9) {
         var2 += 36;
      }

      if (this.field00606.method00947((long)this.field00340.method04086())) {
         ItemStack var4 = var1.getArmorStack(var3);
         if (!var4.isEmpty()) {
            field00117.interactionManager.clickSlot(0, 8 - var3, 0, SlotActionType.QUICK_MOVE, field00117.player);
         }

         field00117.interactionManager.clickSlot(0, var2, 0, SlotActionType.QUICK_MOVE, field00117.player);
         this.field00606.method00451();
      }
   }

   private int method02061(ArmorItem var1, ItemStack var2) {
      Class0813 var3 = Class0809.method02098(var2);
      if (var3 != null && "SunHelmet".equals(var3.method00028())) {
         return Integer.MAX_VALUE;
      }

      ArmorMaterial var4 = ((Class1109)var1).rockstar$getMaterial();
      EquipmentType var5 = ((Class1109)var1).rockstar$getType();
      int var6 = var4.defense().getOrDefault(var5, 0);
      int var7 = (int)var4.toughness();
      int var8 = Class0964.method02129(var2, Enchantments.PROTECTION);
      return var6 * 5 + var8 * 3 + var7;
   }
}
