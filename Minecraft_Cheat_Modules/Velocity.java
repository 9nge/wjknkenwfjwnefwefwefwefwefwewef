package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;
import rockstar.feature.settings.impl.SliderSetting;

import moscow.rockstar.mixin.accessors.EntityVelocityUpdateAccessor;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Vec3d;
import pyrock.events.network.ReceivePacketEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Velocity", method04432 = Category.field00395, method03909 = "modules.descriptions.velocity")
public class Velocity extends Module {
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private ModeValue field00898;
   private ModeValue field01274;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private SliderSetting field00904;
   private SliderSetting field01279;
   private final Class1380 field00617 = new Class1380();
   private final EventListener<ReceivePacketEvent> field00346 = var1 -> {
      if (field00117.player != null && !field00117.player.isDead()) {
         Packet var2 = var1.getPacket();
         boolean var3 = var2 instanceof EntityVelocityUpdateS2CPacket var4 && var4.getEntityId() == field00117.player.getId();
         boolean var11 = var2 instanceof ExplosionS2CPacket;
         if (this.field00327.method02964(this.field00898)) {
            if (var2 instanceof EntityVelocityUpdateS2CPacket var13 && var13.getEntityId() == field00117.player.getId()) {
               this.field00617.method04565(this.field00617.method04373() + 1);
               int var15 = Math.max(1, (int)this.field01279.method04086());
               if (this.field00617.method04373() > var15) {
                  var1.cancel();
                  this.field00617.method04565(0);
               }
            }
         } else {
            if (var3 || var11) {
               if (this.field00327.method02964(this.field00328)
                  && var2 instanceof EntityVelocityUpdateS2CPacket var5
                  && var5.getEntityId() == field00117.player.getId()) {
                  var1.cancel();
               } else if (this.field00327.method02964(this.field01274)
                  && var2 instanceof EntityVelocityUpdateS2CPacket var6
                  && var6.getEntityId() == field00117.player.getId()) {
                  int var7 = (int)(var6.getVelocityX() * 8000.0 * this.field00340.method04086() / 100.0);
                  int var8 = (int)(var6.getVelocityY() * 8000.0 * this.field01607.method04086() / 100.0);
                  int var9 = (int)(var6.getVelocityZ() * 8000.0 * this.field00904.method04086() / 100.0);
                  EntityVelocityUpdateAccessor var10 = (EntityVelocityUpdateAccessor)(Object)var6;
                  var10.setVelocityX(var7);
                  var10.setVelocityY(var8);
                  var10.setVelocityZ(var9);
               }
            }

            if (this.field01600.isSelected()
               && var1.getPacket() instanceof EntityVelocityUpdateS2CPacket var12
               && var12.getEntityId() == field00117.player.getId()) {
               this.field00617.field00173 = new Vec3d(var12.getVelocityX() / 8000.0, var12.getVelocityY() / 8000.0, var12.getVelocityZ() / 8000.0);
               this.field00617.field00005 = 4 + field00117.player.getRandom().nextInt(4);
            }
         }
      }
   };

   public Velocity() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00327 = new ModeSetting(this, "modules.settings.velocity.mode");
      this.field00328 = new ModeValue(this.field00327, "modules.settings.velocity.default");
      this.field01600 = new ModeValue(this.field00327, "modules.settings.velocity.compensation");
      this.field00898 = new ModeValue(this.field00327, "Grim");
      this.field01274 = new ModeValue(this.field00327, "modules.settings.velocity.modify");
      this.field00340 = new SliderSetting(this, "modules.settings.velocity.velocity_x", () -> !this.field00327.method02964(this.field01274))
         .method01202("%")
         .method04137(50.0F)
         .method00660(0.0F)
         .method04520(100.0F)
         .method03699(1.0F);
      this.field01607 = new SliderSetting(this, "modules.settings.velocity.velocity_y", () -> !this.field00327.method02964(this.field01274))
         .method01202("%")
         .method04137(50.0F)
         .method00660(0.0F)
         .method04520(100.0F)
         .method03699(1.0F);
      this.field00904 = new SliderSetting(this, "modules.settings.velocity.velocity_z", () -> !this.field00327.method02964(this.field01274))
         .method01202("%")
         .method04137(50.0F)
         .method00660(0.0F)
         .method04520(100.0F)
         .method03699(1.0F);
      this.field01279 = new SliderSetting(this, "modules.settings.velocity.grim_hits_before_cancel", () -> !this.field00327.method02964(this.field00898))
         .method00660(1.0F)
         .method04520(10.0F)
         .method03699(1.0F)
         .method04137(3.0F);
   }

   @Override
   public final void method03678() {
      if (field00117.player == null || field00117.player.isDead()) {
         this.field00617.method00451();
      } else if (this.field01600.isSelected() && this.field00617.field00005 > 0) {
         Vec3d var1 = field00117.player.getVelocity();
         double var2 = this.field00617.field00005 / 6.0;
         double var4 = 0.3 + (1.0 - var2) * 0.35;
         var4 += field00117.player.getRandom().nextDouble() * 0.05;
         Vec3d var6 = new Vec3d(-this.field00617.field00173.x * var4, 0.0, -this.field00617.field00173.z * var4);
         Vec3d var7 = var1.add(var6);
         field00117.player.setVelocity(var7);
         this.field00617.field00005--;
         super.method03678();
      }
   }

   @Override
   public void method05256() {
      this.field00617.method00451();
   }
}
