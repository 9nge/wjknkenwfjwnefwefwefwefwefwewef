package rockstar.feature.modules.combat;
import rockstar.utils.player.InventoryUtils;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.modules.player.GuiMove;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;
import rockstar.feature.settings.impl.SliderSetting;
import rockstar.ui.notifications.NotificationType;
import rockstar.utils.I18n;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import pyrock.events.player.ClientPlayerTickEvent;
import pyrock.events.window.KeyPressEvent;
import pyrock.events.window.MouseEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Auto Throw", method04432 = Category.field00395, method03909 = "modules.descriptions.auto_throw")
public class AutoThrow extends Module {
   private static final int field00005 = 8;
   private static final int field01495 = 10;
   private static final long field00006 = 5000L;
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private Class0395 field00316;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private Class0968 field00512;
   private int field00833 = -1;
   private int field01227;
   private int field01754 = -1;
   private boolean field00688;
   private final Class1325 field00606 = new Class1325();
   private final EventListener<ClientPlayerTickEvent> field00346 = var1 -> {
      if (field00117.player != null && field00117.world != null && field00117.interactionManager != null && field00117.getNetworkHandler() != null) {
         if (this.field00833 >= 0) {
            this.method04334();
         } else if (this.field00327.method02964(this.field00328) && this.field00606.method00947(5000L)) {
            if (Rockstar.method00215().method00222().method00088() instanceof PlayerEntity var3 && var3.isAlive()) {
               if (this.method01995(var3)) {
                  if (!(Class0826.method02031(var3) + var3.getAbsorptionAmount() >= this.field00340.method04086())) {
                     if (!(field00117.player.distanceTo(var3) > this.field01607.method04086())) {
                        if (this.method04762(var3)) {
                           this.method05313(false);
                        }
                     }
                  }
               }
            }
         }
      }
   };
   private final EventListener<KeyPressEvent> field01611 = var1 -> {
      if (var1.getAction() == 1 && this.field00316.method00827(var1.getKey())) {
         this.method05313(true);
      }
   };
   private final EventListener<MouseEvent> field00906 = var1 -> {
      if (var1.getAction() == 1 && this.field00316.method00827(var1.getButton())) {
         this.method05313(true);
      }
   };

   public AutoThrow() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00327 = new ModeSetting(this, "modules.settings.auto_throw.mode");
      this.field00328 = new ModeValue(this.field00327, "modules.settings.auto_throw.mode.automatic").select();
      this.field01600 = new ModeValue(this.field00327, "modules.settings.auto_throw.mode.bind");
      this.field00316 = new Class0395(this, "modules.settings.auto_throw.key", () -> this.field00327.method02964(this.field00328));
      this.field00340 = new SliderSetting(this, "modules.settings.auto_throw.health", () -> this.field00327.method02964(this.field01600))
         .method00660(1.0F)
         .method04520(20.0F)
         .method03699(0.5F)
         .method04137(15.0F);
      this.field01607 = new SliderSetting(this, "modules.settings.auto_throw.distance", () -> this.field00327.method02964(this.field01600))
         .method00660(1.0F)
         .method04520(6.0F)
         .method03699(0.1F)
         .method04137(3.0F);
   }

   private void method05313(boolean var1) {
      if (field00117.player != null && field00117.currentScreen == null && this.field00833 < 0) {
         if (var1 == this.field00327.method02964(this.field01600)) {
            if (this.method02793(Rockstar.method00215().method00264().method01099(GuiMove.class))) {
               Class0968 var2 = this.method00335();
               if (var2 == null) {
                  if (var1) {
                     Rockstar.method00215()
                        .method00410()
                        .method03454(
                           NotificationType.field01716,
                           I18n.method01151("swap.item_not_found"),
                           I18n.method01474("swap.item_required", Items.SPLASH_POTION.getName().getString())
                        );
                  }
               } else {
                  this.field00512 = var2;
                  this.field01754 = field00117.player.getInventory().selectedSlot;
                  this.field00688 = false;
                  this.field01227 = 0;
                  this.field00833 = var2 instanceof Class1004 ? 2 : 0;
               }
            }
         }
      }
   }

   private void method04334() {
      if (this.field00512 != null && !field00117.player.isDead() && ++this.field01227 <= 60) {
         GuiMove var1 = Rockstar.method00215().method00264().method01099(GuiMove.class);
         switch (this.field00833) {
            case 0:
               InventoryUtils.method05095(this.field00512.method00004(), 8);
               this.field00688 = true;
               this.field00833 = 1;
               break;
            case 1:
               if (this.method02793(var1)) {
                  this.field00833 = 2;
               }
               break;
            case 2:
               int var2 = this.field00512 instanceof Class1004 var3 ? var3.method03627() : 8;
               if (!method02103(InventoryUtils.method00819(var2).method00094())) {
                  this.method05167();
                  return;
               }

               this.method03723(var2);
               field00117.interactionManager.interactItem(field00117.player, Hand.MAIN_HAND);
               this.field00606.method00451();
               this.field00833 = 3;
               break;
            case 3:
               if (this.method02793(var1)) {
                  this.method05167();
               }
         }
      } else {
         this.method05167();
      }
   }

   private void method05167() {
      if (this.field00688 && this.field00512 != null) {
         InventoryUtils.method05095(this.field00512.method00004(), 8);
      }

      if (this.field01754 >= 0) {
         this.method03723(this.field01754);
      }

      this.field00512 = null;
      this.field00833 = -1;
      this.field01227 = 0;
      this.field01754 = -1;
      this.field00688 = false;
   }

   private boolean method02793(GuiMove var1) {
      if (var1 == null || !var1.method04473()) {
         return true;
      } else {
         return var1.method04335() ? !var1.method05210() && !var1.method04060() && var1.method05168() : var1.method00046().isEmpty() && !var1.method04060();
      }
   }

   private void method03723(int var1) {
      if (field00117.player.getInventory().selectedSlot != var1) {
         field00117.player.getInventory().selectedSlot = var1;
         field00117.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(var1));
      }
   }

   private Class0968 method00335() {
      Class0968 var1 = Class0993.method00338().method01715(AutoThrow::method02103);
      return var1 != null ? var1 : Class0993.method04443().method01715(AutoThrow::method02103);
   }

   private boolean method01995(LivingEntity var1) {
      if (var1.isUsingItem() && var1.getItemUseTime() >= 10) {
         ItemStack var2 = var1.getActiveItem();
         return !(var2.getItem() instanceof SplashPotionItem) && Class0852.method02131(var2, StatusEffects.INSTANT_HEALTH);
      } else {
         return false;
      }
   }

   private boolean method04762(LivingEntity var1) {
      Class1271 var2 = Rockstar.method00215().method00392().method04463();
      Vec3d var3 = field00117.player.getEyePos();
      Vec3d var4 = var3.add(Class1010.method00689(var2.method04372(), var2.method00003()).multiply(this.field01607.method04086() + 1.0F));
      Box var5 = var1.getBoundingBox().expand(0.15);
      return var5.contains(var3) || var5.raycast(var3, var4).isPresent();
   }

   private static boolean method02103(ItemStack var0) {
      return !var0.isEmpty() && var0.getItem() instanceof SplashPotionItem && Class0852.method02131(var0, StatusEffects.INSTANT_HEALTH);
   }

   @Override
   public void method05070() {
      this.field00606.method00946(0L);
      super.method05070();
   }

   @Override
   public void method05256() {
      if (this.field00833 >= 0) {
         this.method05167();
      }

      super.method05256();
   }
}
