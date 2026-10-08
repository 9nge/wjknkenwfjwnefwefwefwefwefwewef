package rockstar.feature.modules.combat;
import rockstar.utils.player.InventoryUtils;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.modules.movement.ElytraStrafe;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;
import rockstar.feature.settings.impl.SliderSetting;

import java.util.Optional;
import lombok.Generated;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.math.Vec3d;
import pyrock.events.game.PostAttackEvent;
import pyrock.events.game.WorldChangeEvent;
import pyrock.events.player.InputEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Elytra Target", method04432 = Category.field00395, method03909 = "modules.descriptions.elytra_target")
public class ElytraTarget extends Module {
   private BooleanSetting field00318;
   private BooleanSetting field01594;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private SliderSetting field00904;
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private SliderSetting field01279;
   private BooleanSetting field00893;
   private SliderSetting field01793;
   private LivingEntity field00145;
   private Vec3d field00173 = Vec3d.ZERO;
   private double field00003 = Double.NaN;
   private boolean field00688 = false;
   private Vec3d field01538 = Vec3d.ZERO;
   private final EventListener<InputEvent> field00346 = var1 -> {
      if (!this.field00893.method04473()) {
         this.field00688 = false;
         this.field00003 = Double.NaN;
         this.field01538 = Vec3d.ZERO;
      } else if (field00117.player == null || !field00117.player.isGliding()) {
         this.field00688 = false;
         this.field00003 = Double.NaN;
         this.field01538 = Vec3d.ZERO;
      } else if (this.field00145 == null) {
         this.field00688 = false;
         this.field00003 = Double.NaN;
         this.field01538 = Vec3d.ZERO;
      } else {
         boolean var2 = field00117.player.distanceTo(this.field00145) < this.field01793.method04086();
         if (!var2) {
            this.field00688 = false;
            this.field00003 = Double.NaN;
            this.field01538 = Vec3d.ZERO;
         } else {
            double var3 = field00117.player.getY();
            if (field00117.player.isOnGround()) {
               this.field00003 = Double.NaN;
               this.field00688 = false;
            } else if (!this.field00688) {
               if (Double.isNaN(this.field00003)) {
                  this.field00003 = var3;
               } else if (var3 > this.field00003) {
                  this.field00003 = var3;
               } else if (var3 < this.field00003) {
                  this.field00688 = true;
                  this.field01538 = field00117.player.getPos();
                  this.field00003 = var3;
               }
            }

            if (this.field00688) {
               var1.setForward(0.0F);
               var1.setStrafe(0.0F);
               field00117.player.setVelocity(Vec3d.ZERO);
               field00117.player.setPosition(this.field01538);
            }
         }
      }
   };
   private final EventListener<PostAttackEvent> field01611 = var1 -> {
      if (this.method05168()) {
         if (Class0802.method00341() != null) {
            Class0822.method03551(false);
            if (field00117.player.isSprinting() && field00117.player.input.hasForwardMovement() && field00117.player.checkGliding()) {
               field00117.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(field00117.player, Mode.START_FALL_FLYING));
            }
         }

         LivingEntity var2 = Optional.ofNullable(this.field00145).orElseGet(this::method04395);
         if (var2 != null) {
            Class0822.method02351(Class0822.method04760(var2));
         }

         if (this.field00318.method04473()) {
            long var3 = this.method01998(var2, var2 != null ? field00117.player.distanceTo(var2) : Double.MAX_VALUE);
            if (this.method00947(var3)) {
               Class0822.method00681(this.field00340.method04086());
            }
         }
      }
   };
   private final EventListener<WorldChangeEvent> field00906 = var1 -> this.method00451();

   public ElytraTarget() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00318 = new BooleanSetting(this, "modules.settings.elytra_target.auto_fireworks").method00203();
      this.field01594 = new BooleanSetting(this, "modules.settings.elytra_target.smart_fireworks", () -> !this.field00318.method04473()).method00203();
      this.field00340 = new SliderSetting(this, "modules.settings.elytra_target.fireworkSlot", () -> !this.field00318.method04473())
         .method00660(1.0F)
         .method04520(9.0F)
         .method03699(1.0F)
         .method04137(7.0F)
         .method01202(" slot");
      this.field01607 = new SliderSetting(
            this, "modules.settings.elytra_target.fireworkDelay", () -> !this.field00318.method04473() && this.field01594.method04473()
         )
         .method00660(0.25F)
         .method04520(3.0F)
         .method03699(0.05F)
         .method04137(0.45F)
         .method01202(" s");
      this.field00904 = new SliderSetting(this, "modules.settings.elytra_target.engageRange")
         .method00660(6.0F)
         .method04520(50.0F)
         .method03699(1.0F)
         .method04137(24.0F)
         .method01202(" blocks");
      this.field00327 = new ModeSetting(this, "modules.settings.elytra_target.prediction_mode", "motion");
      this.field00328 = new ModeValue(this.field00327, "modules.settings.elytra_target.prediction_mode.motion");
      this.field01600 = new ModeValue(this.field00327, "modules.settings.elytra_target.prediction_mode.server_pos").select();
      this.field01279 = new SliderSetting(this, "modules.settings.elytra_target.lead_strength", () -> !this.field00327.method02964(this.field00328))
         .method00660(0.0F)
         .method04520(5.0F)
         .method03699(0.1F)
         .method04137(3.0F)
         .method01202(" ticks");
      this.field00893 = new BooleanSetting(this, "modules.settings.elytra_target.air_freeze");
      this.field01793 = new SliderSetting(this, "modules.settings.elytra_target.freeze_distance", () -> !this.field00893.method04473())
         .method00660(1.0F)
         .method04520(10.0F)
         .method03699(0.1F)
         .method04137(3.0F)
         .method01202(" blocks");
   }

   @Override
   public void method03678() {
      if (!Rockstar.method00215().method00264().method01099(Aura.class).method04473()) {
         this.method05167();
      } else if (!this.method05168()) {
         this.method05167();
      } else {
         this.field00145 = this.method04395();
         this.method04761(this.field00145);
         if (this.field00318.method04473()) {
            this.method01994(this.field00145);
         }

         if (this.field00145 != null) {
            this.method04334();
         }
      }
   }

   private LivingEntity method04395() {
      LivingEntity var1 = Rockstar.method00215().method00222().method00088();
      return var1 instanceof PlayerEntity ? var1 : null;
   }

   private void method01994(LivingEntity var1) {
      if (field00117.player != null && field00117.player.isGliding()) {
         if (!Rockstar.method00215().method00264().method01099(ElytraStrafe.class).method04473()) {
            double var2 = var1 == null ? Double.MAX_VALUE : field00117.player.distanceTo(var1);
            boolean var4 = var1 instanceof PlayerEntity var5 && this.method02038(var5);
            boolean var9 = field00117.player.getY() < (var1 != null ? var1.getY() + 3.0 : field00117.player.getY() + 5.0);
            long var6 = this.method01998(var1, var2);
            boolean var8 = this.field01594.method04473() ? var2 > 15.0 || var4 || this.method04335() || var9 : var2 > 15.0 || var4;
            if (var8 && this.method00947(var6)) {
               Class0822.method00681(this.field00340.method04086());
            }
         }
      }
   }

   private long method01998(LivingEntity var1, double var2) {
      long var4 = (long)(this.field01607.method04086() * 1000.0F);
      if (!this.field01594.method04473()) {
         return var4;
      } else if (var1 instanceof PlayerEntity var6 && this.method02038(var6)) {
         return (long)((float)var4 * 0.68F);
      } else if (var2 < 8.0) {
         return (long)((float)var4 * 1.35F);
      } else {
         return this.method04335() ? (long)((float)var4 * 0.78F) : var4;
      }
   }

   private boolean method04335() {
      return field00117.player.getY() < (this.field00145 != null ? this.field00145.getY() + 2.0 : field00117.world.getSeaLevel());
   }

   private void method04761(LivingEntity var1) {
      if (var1 != null && field00117.player.isGliding()) {
         Vec3d var2 = Class1360.method02016(var1, this.field00327, this.field00328, this.field01600, this.field01279.method04086());
         if (var2 == null) {
            this.field00173 = Vec3d.ZERO;
         } else {
            this.field00173 = var2;
            Class1274 var3 = Rockstar.method00215().method00392();
            Class1271 var4 = Class1275.method02350(this.field00173);
            var3.method03419(var4, Class1268.field01321, 180.0F, 180.0F, 180.0F, Class1277.field00971);
         }
      } else {
         this.field00173 = Vec3d.ZERO;
      }
   }

   private void method04334() {
      if (InventoryUtils.method04445().method00093() == Items.ELYTRA
         && field00117.player.isSprinting()
         && field00117.player.input.hasForwardMovement()
         && field00117.player.checkGliding()) {
         field00117.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(field00117.player, Mode.START_FALL_FLYING));
      }
   }

   private boolean method02038(PlayerEntity var1) {
      double var2 = field00117.player.getVelocity().horizontalLengthSquared();
      double var4 = var1.getVelocity().horizontalLengthSquared();
      return var2 + 1.0E-4 < var4;
   }

   private boolean method00947(long var1) {
      return Class0822.method00403().method00947(var1);
   }

   private boolean method05168() {
      return field00117.player.getInventory().getArmorStack(2).getItem() == Items.ELYTRA;
   }

   private void method05167() {
      this.field00145 = null;
      this.field00173 = Vec3d.ZERO;
   }

   @Override
   public void method05256() {
      this.method05167();
   }

   @Generated
   public BooleanSetting method00203() {
      return this.field00318;
   }

   @Generated
   public BooleanSetting method04418() {
      return this.field01594;
   }

   @Generated
   public SliderSetting method00218() {
      return this.field00340;
   }

   @Generated
   public SliderSetting method04426() {
      return this.field01607;
   }

   @Generated
   public SliderSetting method03657() {
      return this.field00904;
   }

   @Generated
   public ModeSetting method00210() {
      return this.field00327;
   }

   @Generated
   public ModeValue method00211() {
      return this.field00328;
   }

   @Generated
   public ModeValue method04423() {
      return this.field01600;
   }

   @Generated
   public SliderSetting method04107() {
      return this.field01279;
   }

   @Generated
   public BooleanSetting method03651() {
      return this.field00893;
   }

   @Generated
   public SliderSetting method05061() {
      return this.field01793;
   }

   @Generated
   public LivingEntity method00088() {
      return this.field00145;
   }

   @Generated
   public double method00002() {
      return this.field00003;
   }

   @Generated
   public boolean method04272() {
      return this.field00688;
   }

   @Generated
   public Vec3d method00116() {
      return this.field01538;
   }

   @Generated
   public EventListener<InputEvent> method00221() {
      return this.field00346;
   }

   @Generated
   public EventListener<PostAttackEvent> method04428() {
      return this.field01611;
   }

   @Generated
   public EventListener<WorldChangeEvent> method03658() {
      return this.field00906;
   }

   @Generated
   public Vec3d method04400() {
      return this.field00173;
   }
}
