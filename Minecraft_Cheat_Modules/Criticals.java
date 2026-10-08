package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;

import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos.Mutable;
import pyrock.events.game.InternalAttackEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Criticals", method04432 = Category.field00395)
public class Criticals extends Module {
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private int field00005;
   private final EventListener<InternalAttackEvent> field00346 = var1 -> {
      if (!var1.isCancelled()) {
         if (field00117.player != null && field00117.world != null) {
            Class1274 var2 = Rockstar.method00215().method00392();
            if (this.field00327.method02964(this.field01600)) {
               this.method02506(var1);
            } else if (!field00117.player.isTouchingWater()) {
               if (this.method04272()) {
                  Class1271 var3 = var2.method00452() ? var2.method00391() : var2.method03673();
                  Class1271 var4 = Class1275.method03422(
                     var2.method03673(),
                     new Class1271(var3.method00003() + Class1010.method00587(-5.0, 5.0), var3.method04372() + Class1010.method00587(-5.0, 5.0))
                  );
                  field00117.player
                     .networkHandler
                     .sendPacket(
                        new Full(
                           field00117.player.getX(),
                           field00117.player.getY() - (field00117.player.fallDistance = Class1010.method00587(1.0E-5F, 1.0E-4F)),
                           field00117.player.getZ(),
                           var4.method00003(),
                           var4.method04372(),
                           field00117.player.isOnGround(),
                           field00117.player.horizontalCollision
                        )
                     );
               }
            }
         }
      }
   };

   public Criticals() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00327 = new ModeSetting(this, "modules.settings.criticals.mode");
      this.field00328 = new ModeValue(this.field00327, "modules.settings.criticals.mode.default").select();
      this.field01600 = new ModeValue(this.field00327, "modules.settings.criticals.mode.reallyworld");
   }

   @Override
   public void method03678() {
      if (this.method04335()) {
         this.field00005++;
      } else {
         this.field00005 = 0;
      }
   }

   public boolean method04272() {
      return this.field00327.method02964(this.field01600)
         ? !this.method05210() || this.method05355()
         : field00117.player != null && field00117.player.fallDistance <= 0.0F && !field00117.player.isOnGround() && this.field00005 > 0;
   }

   public boolean method04335() {
      if (!this.method04473() || field00117.player == null || field00117.world == null) {
         return false;
      } else {
         return this.field00327.method02964(this.field01600) ? this.method05210() : field00117.player.fallDistance <= 0.0F && !field00117.player.isOnGround();
      }
   }

   public boolean method05168() {
      return this.method04473() && !this.field00327.method02964(this.field01600);
   }

   private boolean method05355() {
      if (field00117.player != null && field00117.world != null && !field00117.player.isOnGround()) {
         double var1 = field00117.player.getY();
         return var1 != (int)var1 && (field00117.player.isInLava() || this.method05327());
      } else {
         return false;
      }
   }

   private void method02506(InternalAttackEvent var1) {
      if (var1.getEntity() != null && !(var1.getEntity() instanceof EndCrystalEntity) && this.method05355()) {
         float var2 = Class1010.method00587(1.0E-7F, 1.0E-6F);
         field00117.player.fallDistance = var2;
         field00117.player
            .networkHandler
            .sendPacket(
               new Full(
                  field00117.player.getX(),
                  field00117.player.getY() - var2,
                  field00117.player.getZ(),
                  field00117.player.getYaw(),
                  field00117.player.getPitch(),
                  false,
                  field00117.player.horizontalCollision
               )
            );
      }
   }

   public boolean method05210() {
      return this.method04473()
         && field00117.player != null
         && field00117.world != null
         && (field00117.player.hasStatusEffect(StatusEffects.SLOW_FALLING) || this.method05327());
   }

   public boolean method05327() {
      if (field00117.player != null && field00117.world != null) {
         Box var1 = field00117.player.getBoundingBox();
         int var2 = (int)Math.floor(var1.minX);
         int var3 = (int)Math.floor(var1.minY);
         int var4 = (int)Math.floor(var1.minZ);
         int var5 = (int)Math.ceil(var1.maxX);
         int var6 = (int)Math.ceil(var1.maxY);
         int var7 = (int)Math.ceil(var1.maxZ);
         Mutable var8 = new Mutable();

         for (int var9 = var2; var9 < var5; var9++) {
            for (int var10 = var3; var10 < var6; var10++) {
               for (int var11 = var4; var11 < var7; var11++) {
                  if (field00117.world.getBlockState(var8.set(var9, var10, var11)).isOf(Blocks.COBWEB)) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
