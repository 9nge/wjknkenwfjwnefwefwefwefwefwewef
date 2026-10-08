package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyrock.events.game.InternalAttackEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Knockback Tweaks", method04432 = Category.field00395, method03909 = "modules.descriptions.knockback_tweaks")
public class KnockbackTweaks extends Module {
   public BooleanSetting field00318;
   private boolean field00688;
   public boolean field01735;
   private Entity field00143;
   private int field00005;
   public boolean field00995;
   private final EventListener<InternalAttackEvent> field00346 = new Class1370(this);

   public KnockbackTweaks() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00318 = new BooleanSetting(this, "modules.settings.knockback_tweaks.fake_sprint");
   }

   @Override
   public void method03678() {
      if (field00117.player != null && field00117.world != null) {
         if (!this.field00318.method04473()) {
            this.method04334();
            this.method05167();
         }

         if (this.field00688) {
            this.field00688 = false;
            Class1271 var1 = this.method00391();
            field00117.player
               .networkHandler
               .sendPacket(new LookAndOnGround(var1.method00003(), var1.method04372(), field00117.player.isOnGround(), field00117.player.horizontalCollision));
         }

         if (this.field01735) {
            this.field01735 = false;
            field00117.player.networkHandler.sendPacket(new ClientCommandC2SPacket(field00117.player, Mode.START_SPRINTING));
         }
      }
   }

   @Override
   public void method05256() {
      this.field00688 = false;
      this.field01735 = false;
      this.field00143 = null;
      this.field00005 = 0;
      this.field00995 = false;
   }

   public void method01959(Entity var1) {
      Class1271 var2 = this.method01958(var1);
      field00117.player
         .networkHandler
         .sendPacket(new LookAndOnGround(var2.method00003(), var2.method04372(), field00117.player.isOnGround(), field00117.player.horizontalCollision));
      this.field00688 = true;
   }

   private Class1271 method01958(Entity var1) {
      Vec3d var2 = var1.getPos().subtract(field00117.player.getPos());
      float var3 = MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(var2.z, var2.x)) + 90.0F);
      return new Class1271(var3, this.method00391().method04372());
   }

   private Class1271 method00391() {
      Class1274 var1 = Rockstar.method00215().method00392();
      return var1.method00452() ? var1.method00391() : var1.method03673();
   }

   public void method04334() {
      field00117.options.sprintKey.setPressed(true);
      if (!field00117.player.isSprinting() && this.method04272()) {
         field00117.player.setSprinting(true);
      }
   }

   public void method04754(Entity var1) {
      this.field00143 = var1;
      this.field00005 = 1;
   }

   private void method05167() {
      if (this.field00143 != null && field00117.interactionManager != null) {
         if (this.field00143.isRemoved() || !this.method04272()) {
            this.field00143 = null;
            this.field00005 = 0;
         } else if (this.field00005 > 0) {
            this.field00005--;
         } else {
            Entity var1 = this.field00143;
            this.field00143 = null;
            this.field00995 = true;

            try {
               field00117.interactionManager.attackEntity(field00117.player, var1);
               field00117.player.swingHand(Hand.MAIN_HAND);
            } finally {
               this.field00995 = false;
            }
         }
      }
   }

   public boolean method04272() {
      return field00117.player.input.hasForwardMovement()
         && !field00117.player.horizontalCollision
         && !field00117.player.isSneaking()
         && !field00117.player.isTouchingWater()
         && !field00117.player.isSubmergedInWater()
         && (field00117.player.getHungerManager().getFoodLevel() > 6 || field00117.player.getAbilities().allowFlying);
   }
}
