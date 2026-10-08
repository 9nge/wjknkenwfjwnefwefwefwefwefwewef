package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.core.managers.TargetManager;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;
import rockstar.feature.settings.impl.SliderSetting;
import rockstar.utils.ChatUtils;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyrock.events.game.AttackEvent;
import pyrock.events.player.ClientPlayerTickEvent;
import pyrock.events.render.GameRendererEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Aim Assist", method04432 = Category.field00395, method03909 = "modules.descriptions.aim_assist")
public class AimAssist extends Module {
   private static final Set<String> field00081 = Set.of("sword", "trident", "_axe", "mace", "stick", "pickaxe", "shovel");
   private static final double field00003 = Math.PI * 2;
   private static final float field00004 = 1.0E-4F;
   private static final float field01494 = 0.05F;
   private static final float field00832 = 90.0F;
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private SliderSetting field00340;
   private MultiChoiceSetting field00331;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private MultiChoiceValue field01276;
   private MultiChoiceValue field01791;
   private MultiChoiceValue field01992;
   private MultiChoiceValue field01032;
   private ModeSetting field01599;
   private ModeValue field00898;
   private ModeValue field01274;
   private ModeValue field01789;
   private ModeSetting field00897;
   private ModeValue field01991;
   private ModeValue field01031;
   private ModeValue field01147;
   private SliderSetting field01607;
   private SliderSetting field00904;
   private SliderSetting field01279;
   private SliderSetting field01793;
   private BooleanSetting field00318;
   private BooleanSetting field01594;
   private SliderSetting field01994;
   private SliderSetting field01034;
   private BooleanSetting field00893;
   private BooleanSetting field01270;
   private BooleanSetting field01785;
   private SliderSetting field01150;
   private SliderSetting field01359;
   private SliderSetting field01438;
   private SliderSetting field01851;
   private BooleanSetting field01987;
   private BooleanSetting field01028;
   private SliderSetting field01913;
   private SliderSetting field02039;
   private SliderSetting field02092;
   private BooleanSetting field01145;
   private SliderSetting field01068;
   private final Class1108 field00541 = new Class1108();
   private final Class1019 field00528 = new Class1019();
   private final Class1437 field00645 = new Class1437();
   private final float[] field00692 = new float[2];
   private float field01226;
   private float field01753;
   private float field01958;
   private boolean field00688;
   private LivingEntity field00145;
   private LivingEntity field01530;
   private long field00006;
   private long field01496;
   private long field00834;
   private boolean field01735 = true;
   private boolean field00995;
   private float field01006;
   private float field01127;
   private float field01341;
   private float field01422;
   private float field01837;
   private float field01899;
   private float field02026;
   private float field02082 = Float.NaN;
   private float field01059;
   private float field01094;
   private float field01173;
   private float field01201;
   private float field01378;
   private double field01493;
   private double field00831;
   private double field01225;
   private boolean field01336;
   private double field01752;
   private double field01957;
   private double field01005;
   private float field01401;
   private float field01455;
   private float field01474;
   private float field01866;
   private float field01884;
   private float field01927;
   private float field01942;
   private float field02052;
   private float field02067;
   private boolean field01834;
   private float field02102;
   private float field02116;
   private float field01077;
   private float field01086;
   private float field01112;
   private float field01119;
   private float field01189;
   private float field01195;
   private float field01214;
   private float field01219;
   private float field01390;
   private boolean field02023;
   private float field01395;
   private float field01411;
   private float field01416;
   private float field01463;
   private long field01228;
   private float field01468 = 0.15F;
   private boolean field01056;
   private float field01481;
   private final float[] field01738 = new float[2];
   private final float[] field00998 = new float[2];
   private final float[] field01338 = new float[2];
   private final double[] field00691 = new double[3];
   private final EventListener<ClientPlayerTickEvent> field00346 = var1 -> {
      if (this.method04076()) {
         this.method05167();
      } else if (this.field00893.method04473() && !this.method04272()) {
         this.method05167();
      } else {
         this.method04059();
         this.method04075();
         this.method03993();
         if (this.method04335()) {
            this.method05209();
         } else {
            if (this.field00145 != null) {
               Vec3d var2 = this.field00145.getVelocity();
               if (var2.lengthSquared() > 1.0E-6) {
                  this.field00541.method02004(this.field00145, this.method03626(), 100);
               }

               if (this.field00318.method04473() && !this.field00995) {
                  this.field01127 += 0.05F;
                  if (this.field01127 >= this.field01341) {
                     this.field00995 = true;
                  }
               }
            }
         }
      }
   };
   private final EventListener<GameRendererEvent> field01611 = var1 -> {
      if (!this.method04076() && field00117.player != null) {
         long var2 = System.nanoTime();
         float var4 = this.method00939(var2);
         this.field00834 = var2;
         if (var4 < 1.0E-4F || var4 > 0.1F) {
            var4 = 0.016666668F;
         }

         if (this.method04335()) {
            this.method00681(var4);
         } else {
            boolean var5 = this.field01270.method04473();
            if (var5) {
               this.method05354();
            }

            this.field01201 = this.field01378 = 0.0F;
            if (this.method04060()) {
               this.method03970();
            } else {
               Vec3d var6 = field00117.player.getEyePos();
               this.field01752 = var6.x;
               this.field01957 = var6.y;
               this.field01005 = var6.z;
               float var7 = this.method03626();
               this.method02002(this.field00145, var7, var4);
               float var8 = this.field01338[0];
               float var9 = this.field01338[1];
               if (Float.isFinite(var8) && Float.isFinite(var9)) {
                  float var10 = var8 * var8 + var9 * var9;
                  if (var10 < 0.01F) {
                     this.method03970();
                  } else {
                     float var11 = MathHelper.sqrt(var10);
                     if (this.field01594.method04473()) {
                        float var12 = this.method04507(field00117.player.distanceTo(this.field00145));
                        var9 += var12;
                     }

                     float var21 = this.method01989(this.field00145);
                     float var13;
                     float var14;
                     if (this.field00318.method04473()) {
                        float var15 = this.method03711(var11, var21);
                        float var16 = Math.max(var15 / this.field01793.method04086(), 0.01F);
                        this.method04544(var11, var21, var4, var16);
                        float var17 = this.field01735 ? this.method03687(this.field01006) : this.method04534(var8, var9);
                        float var18 = var11 / var16 * var17 * this.field01279.method04086();
                        float var19 = var11 > 1.0E-4F ? 1.0F / var11 : 0.0F;
                        var13 = MathHelper.clamp(var8 * var19 * var18 * var4, -20.0F, 20.0F);
                        var14 = this.field01594.method04473() ? this.method00718(var9, var11, var21, var4) : 0.0F;
                     } else {
                        float var22 = this.field01793.method04086() * this.field01279.method04086() * 10.0F * var4;
                        float var24 = Math.min(var11 / 10.0F, 1.0F);
                        float var26 = var11 > 1.0E-4F ? 1.0F / var11 : 0.0F;
                        var13 = var8 * var26 * var22 * var24;
                        var14 = this.field01594.method04473() ? var9 * var26 * var22 * var24 * this.field01994.method04086() : 0.0F;
                     }

                     float var23 = this.field01034.method04086();
                     float var25 = this.method00685(var11, var4);
                     float var27 = var23 * var25;
                     if (var27 > 1.0E-4F) {
                        this.method00720(var13, var14, var27, var4);
                        float var28 = this.method00638(var4);
                        var13 += this.field01738[0] + var28 * 0.5F;
                        var14 += this.field01738[1] + var28 * 0.25F;
                     }

                     if (this.field01145.method04473()) {
                        this.method03714(var11, var4);
                        var13 += this.field01395;
                        var14 += this.field01411;
                     }

                     if (var5) {
                        var13 = this.method00713(var13, this.field01094, var4);
                        var14 = this.method00713(var14, this.field01173, var4);
                        if (this.field01785.method04473() && !this.method00717(var8, var9, var4)) {
                           var14 = 0.0F;
                           var13 = 0.0F;
                        }
                     }

                     float var29 = MathHelper.clamp(1.0F - (float)Math.exp(-20.0F * var4), 0.05F, 0.95F);
                     this.field01422 = this.method04542(this.field01422, var13, var29);
                     this.field01837 = this.method04542(this.field01837, var14, var29 * 0.75F);
                     float var30 = Math.abs(var8) * 1.5F + 0.5F;
                     float var20 = Math.abs(var9) * 1.5F + 0.5F;
                     this.field01422 = MathHelper.clamp(this.field01422, -var30, var30);
                     this.field01837 = MathHelper.clamp(this.field01837, -var20, var20);
                     this.method05326();
                     this.method03970();
                  }
               } else {
                  this.method03970();
               }
            }
         }
      }
   };
   private final EventListener<AttackEvent> field00906 = var1 -> {
      if (this.method04335() && var1.getEntity() instanceof LivingEntity var2 && var2 != field00117.player) {
         this.field00645.method00451();
      }
   };

   public AimAssist() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00327 = new ModeSetting(this, "aimassist.mode");
      this.field00328 = new ModeValue(this.field00327, "aimassist.mode_normal").select();
      this.field01600 = new ModeValue(this.field00327, "aimassist.mode_neuro");
      this.field00340 = new SliderSetting(this, "aimassist.neuro_strength", "aimassist.neuro_strength.desc", () -> !this.method04335())
         .method00660(0.1F)
         .method04520(1.0F)
         .method03699(0.05F)
         .method04137(1.0F);
      this.field00331 = new MultiChoiceSetting(this, "modules.settings.aura.targets");
      this.field00332 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.players").select();
      this.field01604 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.animals").select();
      this.field00901 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.mobs").select();
      this.field01276 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.invisibles").select();
      this.field01791 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.nakedPlayers").select();
      this.field01992 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.rockUsers");
      this.field01032 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.friends");
      this.field01599 = new ModeSetting(this, "aura.targets_sort");
      this.field00898 = new ModeValue(this.field01599, "aura.ts_dist");
      this.field01274 = new ModeValue(this.field01599, "aura.ts_health");
      this.field01789 = new ModeValue(this.field01599, "aura.ts_fov").select();
      this.field00897 = new ModeSetting(this, "aimassist.target_lock_mode", this::method04335);
      this.field01991 = new ModeValue(this.field00897, "aimassist.lock_off");
      this.field01031 = new ModeValue(this.field00897, "aimassist.lock_on_attack");
      this.field01147 = new ModeValue(this.field00897, "aimassist.lock_auto").select();
      this.field01607 = new SliderSetting(this, "aimassist.lock_timeout", this::method04335)
         .method00660(0.0F)
         .method04520(10.0F)
         .method03699(0.1F)
         .method04137(5.0F);
      this.field00904 = new SliderSetting(this, "modules.settings.aura.aimDistance").method00660(3.0F).method04520(10.0F).method03699(0.1F).method04137(4.5F);
      this.field01279 = new SliderSetting(this, "aimassist.strength", this::method04335).method00660(0.1F).method04520(2.0F).method03699(0.01F).method04137(1.0F);
      this.field01793 = new SliderSetting(this, "aimassist.aim_speed", this::method04335).method00660(1.0F).method04520(60.0F).method03699(0.5F).method04137(18.0F);
      this.field00318 = new BooleanSetting(this, "aimassist.repit_aim", this::method04335).method03531(false);
      this.field01594 = new BooleanSetting(this, "aimassist.enable_vertical", this::method04335).method00203();
      this.field01994 = new SliderSetting(this, "projectile.vertical_factor", this::method05168)
         .method00660(0.1F)
         .method04520(1.5F)
         .method03699(0.01F)
         .method04137(0.4F);
      this.field01034 = new SliderSetting(this, "aimassist.motor_noise", "motornoise.desc", this::method04335)
         .method00660(0.0F)
         .method04520(0.4F)
         .method03699(0.01F)
         .method04137(0.15F);
      this.field00893 = new BooleanSetting(this, "aimassist.only_on_weapon").method00203();
      this.field01270 = new BooleanSetting(this, "aimassist.yield_to_mouse", this::method04335).method03531(false);
      this.field01785 = new BooleanSetting(this, "aimassist.input_based", this::method03971).method03531(false);
      this.field01150 = new SliderSetting(this, "aimassist.input_threshold", this::method03994)
         .method00660(0.1F)
         .method04520(3.0F)
         .method03699(0.1F)
         .method04137(0.8F);
      this.field01359 = new SliderSetting(this, "aimassist.prediction_ticks", this::method04335)
         .method00660(0.0F)
         .method04520(3.0F)
         .method03699(1.0F)
         .method04137(2.0F);
      this.field01438 = new SliderSetting(this, "aimassist.velo_pr_ticks", this::method04335)
         .method00660(0.0F)
         .method04520(3.0F)
         .method03699(1.0F)
         .method04137(1.0F);
      this.field01851 = new SliderSetting(this, "aimassist.prediction_chance", "aimassist.prediction_chance.desc", this::method04335)
         .method00660(0.0F)
         .method04520(100.0F)
         .method03699(5.0F)
         .method04137(65.0F)
         .method01202(" %");
      this.field01987 = new BooleanSetting(this, "aimassist.multipoint", () -> this.method04335() || !this.field01270.method04473()).method03531(false);
      this.field01028 = new BooleanSetting(this, "aimassist.mp_adaptive", this::method05210).method00203();
      this.field01913 = new SliderSetting(this, "aimassist.mp_count", this::method05327).method00660(3.0F).method04520(50.0F).method03699(1.0F).method04137(8.0F);
      this.field02039 = new SliderSetting(this, "aimassist.mp_spread", this::method05327).method00660(0.2F).method04520(2.0F).method03699(0.05F).method04137(0.7F);
      this.field02092 = new SliderSetting(this, "aimassist.regen", this::method05210).method00660(0.005F).method04520(0.2F).method03699(0.005F).method04137(0.025F);
      this.field01145 = new BooleanSetting(this, "aimassist.overshoot", "aimassist.overshoot.desc", this::method04335).method03531(true);
      this.field01068 = new SliderSetting(
            this, "aimassist.overshoot_chance", "aimassist.overshoot_chance.desc", () -> this.method04335() || !this.field01145.method04473()
         )
         .method00660(0.0F)
         .method04520(10.0F)
         .method03699(1.0F)
         .method04137(3.0F)
         .method01202(" %");
   }

   private boolean method04335() {
      return this.field00327.method02964(this.field01600);
   }

   private boolean method05168() {
      return this.method04335() || !this.field01594.method04473();
   }

   private boolean method05210() {
      return this.method04335() || !this.field01987.method04473() || !this.field01270.method04473();
   }

   private boolean method05327() {
      return this.method05210() || this.field01028.method04473();
   }

   private boolean method05355() {
      return this.field00897.method02964(this.field01991);
   }

   private boolean method03971() {
      return this.method04335() || !this.field01270.method04473();
   }

   private boolean method03994() {
      return this.method03971() || !this.field01785.method04473();
   }

   @Override
   public void method05070() {
      super.method05070();
      this.field00688 = false;
      this.method04334();
      this.method05167();
      this.field00541.method00451();
      this.field00528.method00451();
      TargetManager var1 = Rockstar.method00215().method00222();
      if (var1 != null) {
         var1.method03678();
      }
   }

   @Override
   public void method05256() {
      super.method05256();
      this.method05167();
      this.field00541.method00451();
      this.field00528.method00451();
      TargetManager var1 = Rockstar.method00215().method00222();
      if (var1 != null) {
         var1.method03678();
      }
   }

   private void method04334() {
      ThreadLocalRandom var1 = ThreadLocalRandom.current();
      this.field02102 = 0.1F + var1.nextFloat() * 0.5F;
      this.field02116 = (0.5F + var1.nextFloat() * 0.75F) * 2.0F;
      this.field01077 = 0.5F + var1.nextFloat() * 0.5F;
      this.field01086 = 3.0F + var1.nextFloat() * 0.25F;
      this.field01112 = 0.5F + var1.nextFloat() * 0.7F;
      this.field01119 = 0.08F + var1.nextFloat() * 0.12F;
      this.field01189 = 0.018F + var1.nextFloat() * 0.005F;
      this.field01195 = 0.45F + var1.nextFloat() * 0.05F;
      this.field01214 = 2.0F + var1.nextFloat();
      this.field01219 = 8.0F + var1.nextFloat() * 3.0F;
      this.field01390 = 0.15F + var1.nextFloat() * 0.05F;
      this.field02052 = Class1010.method05090(this.field01077, this.field01086);
      this.field01474 = 0.3F + var1.nextFloat() * 0.4F;
      this.field01866 = 0.2F + var1.nextFloat() * 0.3F;
      this.field01884 = 0.04F + var1.nextFloat() * 0.06F;
      this.field00528.method00681(0.05F + Class1010.method05090(-0.03F, 0.15F));
      this.field01468 = this.field02092.method04086();
   }

   private void method05167() {
      this.field00145 = this.field01530 = null;
      this.field01127 = this.field01341 = this.field01006 = 0.0F;
      this.field00995 = false;
      this.field01336 = false;
      this.field01735 = true;
      this.field01422 = this.field01837 = 0.0F;
      this.field01899 = this.field02026 = 0.0F;
      this.field02082 = Float.NaN;
      this.field01059 = 0.0F;
      this.field01094 = this.field01173 = 0.0F;
      this.field01201 = this.field01378 = 0.0F;
      this.field02067 = 0.0F;
      this.field01927 = 0.0F;
      this.field00834 = 0L;
      this.field00006 = 0L;
      this.field01496 = 0L;
      this.field01493 = this.field00831 = this.field01225 = 0.0;
      this.field01228 = 0L;
      this.field01056 = false;
      this.field02023 = false;
      this.field01395 = this.field01411 = 0.0F;
      this.field01416 = this.field01463 = 0.0F;
      this.field01401 = this.field01455 = 0.0F;
      this.field01738[0] = this.field01738[1] = 0.0F;
      this.field00645.method04472();
      this.field01226 = this.field01753 = 0.0F;
      this.field01958 = 0.0F;
   }

   private void method05209() {
      if (this.field00145 == null || field00117.player == null || field00117.world == null) {
         this.field00645.method04472();
         this.field01226 = this.field01753 = 0.0F;
      } else if (!this.field00645.method00452()) {
         if (!this.field00688) {
            this.field00688 = true;
            ChatUtils.method03809(Text.of("Aim Assist: модель нейро не найдена — обучи её через .neuro"));
         }
      } else if (this.field00645.method01834(field00117.player, field00117.world, this.field00145, Class1430.method00003(), this.field00692)) {
         float var1 = this.field00340.method04086();
         this.field01226 = MathHelper.clamp(this.field01226 + this.field00692[0] * var1, -90.0F, 90.0F);
         this.field01753 = MathHelper.clamp(this.field01753 + this.field00692[1] * var1, -90.0F, 90.0F);
         this.field01958 = 0.0F;
      }
   }

   private void method00681(float var1) {
      if (field00117.player != null) {
         if (Math.abs(this.field01226) < 1.0E-4F && Math.abs(this.field01753) < 1.0E-4F) {
            this.method03970();
         } else {
            this.field01958 += var1;
            float var2 = Math.max(0.05F - (this.field01958 - var1), var1);
            float var3 = MathHelper.clamp(var1 / var2, 0.0F, 1.0F);
            float var4 = this.field01226 * var3;
            float var5 = this.field01753 * var3;
            this.field01226 -= var4;
            this.field01753 -= var5;
            this.method00710(var4, var5);
            this.method03970();
         }
      }
   }

   private void method02002(LivingEntity var1, float var2, float var3) {
      if (var1 != null && field00117.player != null) {
         double var4 = MathHelper.lerp(var2, var1.lastRenderX, var1.getX());
         double var6 = MathHelper.lerp(var2, var1.lastRenderY, var1.getY());
         double var8 = MathHelper.lerp(var2, var1.lastRenderZ, var1.getZ());
         int var10 = (int)this.field01359.method04086();
         if (var10 > 0 && ThreadLocalRandom.current().nextFloat() < this.field01851.method04086()) {
            Vec3d var11 = this.field00541.method04400();
            if (var11 != null) {
               double var12 = var11.lengthSquared();
               if (var12 > 1.0E-6) {
                  var4 += var11.x * var10;
                  var6 += var11.y * var10;
                  var8 += var11.z * var10;
               }
            }
         }

         float var30 = field00117.player.getAttackCooldownProgress(1.5F);
         if (var30 > 0.85F && !field00117.player.isUsingItem()) {
            double var31 = var1.getX() - this.field01752;
            double var14 = var1.getZ() - this.field01005;
            double var16 = var31 * var31 + var14 * var14;
            if (var16 < 12.25 && var16 > 2.25) {
               float var18 = (var30 - 0.85F) / 0.15F;
               var18 *= var18;
               Class1104.method03576(
                  this.field00691,
                  this.field01752,
                  this.field01005,
                  field00117.player.getYaw(),
                  var1,
                  field00117.player.isSprinting(),
                  Class1104.method01989(field00117.player),
                  (int)this.field01438.method04086()
               );
               var4 -= this.field00691[0] * var18;
               var6 -= this.field00691[1] * var18;
               var8 -= this.field00691[2] * var18;
            }
         }

         double var32;
         if (this.field01987.method04473() && this.field01270.method04473()) {
            this.method01999(var1, var4, var6, var8);
            double[] var39 = this.field00528.method00454();
            if (var39 != null) {
               var32 = var39[0];
               double var33 = var39[1];
               double var36 = var39[2];
            } else {
               var32 = var4;
               double var34 = var6 + var1.getHeight() * 0.5F;
            }
         } else {
            var32 = var4;
         }

         double var35 = var6 + var1.getHeight() * 0.5F;
         double var37 = var8;
         if (!this.field01336) {
            this.field01493 = var32;
            this.field00831 = var35;
            this.field01225 = var37;
            this.field01336 = true;
         } else {
            double var40 = MathHelper.clamp(1.0 - Math.exp(-25.0 * var3), 0.05, 0.95);
            this.field01493 = this.field01493 + (var32 - this.field01493) * var40;
            this.field00831 = this.field00831 + (var35 - this.field00831) * var40;
            this.field01225 = this.field01225 + (var37 - this.field01225) * var40;
         }

         double var41 = this.field01493 - this.field01752;
         double var20 = this.field00831 - this.field01957;
         double var22 = this.field01225 - this.field01005;
         double var24 = var41 * var41 + var22 * var22;
         double var26 = Math.sqrt(var24);
         if (var26 < 1.0E-4F && Math.abs(var20) < 1.0E-4F) {
            this.field01338[0] = this.field01338[1] = 0.0F;
         } else {
            float var28 = (float)Math.toDegrees(Math.atan2(var22, var41)) - 90.0F;
            float var29 = MathHelper.clamp((float)(-Math.toDegrees(Math.atan2(var20, var26))), -90.0F, 90.0F);
            this.field01338[0] = MathHelper.wrapDegrees(var28 - field00117.player.getYaw());
            this.field01338[1] = var29 - field00117.player.getPitch();
         }
      } else {
         this.field01338[0] = this.field01338[1] = 0.0F;
      }
   }

   private void method05326() {
      this.method00710(this.field01422, this.field01837);
   }

   private void method00710(float var1, float var2) {
      if (field00117.player != null) {
         this.method04541(var1, var2);
         float var3 = Float.isFinite(this.field00998[0]) ? this.field00998[0] : 0.0F;
         float var4 = Float.isFinite(this.field00998[1]) ? this.field00998[1] : 0.0F;
         float var5 = field00117.player.getYaw() + var3;
         float var6 = MathHelper.clamp(field00117.player.getPitch() + var4, -90.0F, 90.0F);
         field00117.player.setYaw(var5);
         field00117.player.setPitch(var6);
         field00117.player.headYaw = var5;
         this.field01201 = var3;
         this.field01378 = var4;
      }
   }

   private void method04541(float var1, float var2) {
      float var3 = this.method00003();
      if (var3 < 1.0E-4F) {
         this.field00998[0] = var1;
         this.field00998[1] = var2;
      } else {
         this.field01899 += var1;
         this.field02026 += var2;
         float var4 = Math.round(this.field01899 / var3) * var3;
         float var5 = Math.round(this.field02026 / var3) * var3;
         this.field01899 = MathHelper.clamp(this.field01899 - var4, -var3 * 2.0F, var3 * 2.0F);
         this.field02026 = MathHelper.clamp(this.field02026 - var5, -var3 * 2.0F, var3 * 2.0F);
         this.field00998[0] = var4;
         this.field00998[1] = var5;
      }
   }

   private float method00003() {
      double var1 = (Double)field00117.options.getMouseSensitivity().getValue();
      double var3 = var1 * 0.6 + 0.2;
      double var5 = var3 * var3 * var3 * 8.0;
      return (float)(var5 * 0.15);
   }

   private void method00720(float var1, float var2, float var3, float var4) {
      float var5 = (float)Math.exp(-var4 / 0.04F);
      float var6 = this.method04372();
      float var7 = this.method04372();
      float var8 = MathHelper.sqrt(var1 * var1 + var2 * var2) + 1.0E-4F;
      float var9 = var3 * var8 * (1.0F - var5);
      float var10 = var1 / var8;
      float var11 = var2 / var8;
      float var12 = -var11;
      float var13 = var10;
      float var14 = var6 * var9;
      float var15 = var7 * var9 * 0.4F;
      this.field01738[0] = this.field01738[0] * var5 + var14 * var10 + var15 * var12;
      this.field01738[1] = this.field01738[1] * var5 + var14 * var11 + var15 * var13;
   }

   private float method00638(float var1) {
      this.field01401 = this.field01401 + this.field01474 * var1;
      this.field01455 = this.field01455 + this.field01866 * var1;
      if (this.field01401 > Math.PI * 2) {
         this.field01401 -= (float) (Math.PI * 2);
      }

      if (this.field01455 > Math.PI * 2) {
         this.field01455 -= (float) (Math.PI * 2);
      }

      return this.field01884 * (0.6F * (float)Math.sin(this.field01401) + 0.4F * (float)Math.sin(this.field01455 * 1.618F));
   }

   private float method04372() {
      if (this.field01056) {
         this.field01056 = false;
         return this.field01481;
      } else {
         ThreadLocalRandom var1 = ThreadLocalRandom.current();
         double var2 = Math.max(1.0E-10, var1.nextDouble());
         double var4 = var1.nextDouble();
         double var6 = Math.sqrt(-2.0 * Math.log(var2));
         this.field01481 = (float)(var6 * Math.sin((Math.PI * 2) * var4));
         this.field01056 = true;
         return (float)(var6 * Math.cos((Math.PI * 2) * var4));
      }
   }

   private float method00685(float var1, float var2) {
      this.field01942 += var2;
      if (this.field01942 >= this.field02052) {
         this.field01834 = !this.field01834;
         this.field01942 = 0.0F;
         ThreadLocalRandom var3 = ThreadLocalRandom.current();
         this.field02052 = this.field01834
            ? this.field02102 + var3.nextFloat() * (this.field02116 - this.field02102)
            : this.field01077 + var3.nextFloat() * (this.field01086 - this.field01077);
      }

      float var8 = this.field01834 ? 7.0F : 2.5F;
      float var4 = this.field01834 ? this.field01112 : this.field01119;
      this.field01927 = this.field01927 + (var4 - this.field01927) * (1.0F - (float)Math.exp(-var8 * var2));
      float var5 = this.field01927;
      float var6 = this.field01189 * (7.0F / Math.max(this.field01793.method04086(), 1.0F));
      this.field02067 = MathHelper.clamp(this.field02067 + var2 * var6, 0.0F, this.field01195);
      var5 += this.field02067;
      if (var1 < this.field01214) {
         var5 *= this.field01390;
      } else if (var1 < this.field01219) {
         float var7 = (var1 - this.field01214) / (this.field01219 - this.field01214);
         var5 *= this.field01390 + (1.0F - this.field01390) * var7;
      }

      return Math.max(var5, 0.0F);
   }

   private void method03714(float var1, float var2) {
      if (this.field02023) {
         this.field01416 += var2;
         if (this.field01416 >= this.field01463) {
            this.field02023 = false;
            this.field01395 = this.field01411 = 0.0F;
         }
      } else {
         if (var1 < 3.0F && var1 > 0.5F) {
            if (ThreadLocalRandom.current().nextFloat() < this.field01068.method04086() * var2 * 20.0F) {
               this.field02023 = true;
               this.field01416 = 0.0F;
               this.field01463 = 0.08F + ThreadLocalRandom.current().nextFloat() * 0.12F;
               float var3 = 1.0F + ThreadLocalRandom.current().nextFloat() * 2.0F;
               float var4 = ThreadLocalRandom.current().nextBoolean() ? 1.0F : -1.0F;
               this.field01395 = var4 * var3 * var2;
               this.field01411 = (ThreadLocalRandom.current().nextFloat() - 0.5F) * var3 * var2 * 0.3F;
            }
         } else {
            this.field01395 = this.field01411 = 0.0F;
         }
      }
   }

   private float method04507(float var1) {
      return -MathHelper.clamp(var1 * 0.12F, 0.0F, 1.5F);
   }

   private void method04544(float var1, float var2, float var3, float var4) {
      if (var1 < 0.1F) {
         this.field01735 = false;
         this.field01006 = 1.0F;
      } else if (this.field01735) {
         this.field01006 = this.field01006 + var3 / Math.max(var4 * 0.4F, 0.01F);
         if (this.field01006 >= 1.0F || var1 < var2 * 2.0F) {
            this.field01735 = false;
            this.field01006 = 1.0F;
         }
      }
   }

   private float method03687(float var1) {
      var1 = MathHelper.clamp(var1, 0.0F, 1.0F);
      return 24.0F * var1 * var1 * (1.0F - var1) * (1.0F - var1);
   }

   private float method04534(float var1, float var2) {
      float var3 = MathHelper.sqrt(var1 * var1 + var2 * var2);
      return var3 < 1.0E-4F ? 0.1F : 0.4F + 0.3F * Math.min(var3 / 5.0F, 1.0F);
   }

   private float method00718(float var1, float var2, float var3, float var4) {
      float var5 = Math.abs(var1);
      float var6 = var1 > 0.0F ? 0.4F : 0.8F;
      float var7 = this.field01994.method04086();
      if (var5 > 7.0F) {
         float var8 = this.method03711(var5, var3);
         float var9 = Math.max(var8 / (this.field01793.method04086() * 0.4F), 0.02F);
         return MathHelper.clamp(Math.signum(var1) * (var5 / var9) * 0.5F * var4 * this.field01279.method04086() * var6 * var7, -20.0F, 20.0F);
      } else {
         return var5 > 0.3F ? MathHelper.clamp(var1 * 0.6F * var6 * var7 * var4 * 10.0F, -20.0F, 20.0F) : var1 * 2.0F * 0.4F * var6 * var7 * var4 * 10.0F;
      }
   }

   private float method03711(float var1, float var2) {
      return var1 < 1.0E-4F ? 0.0F : (float)(Math.log(var1 / Math.max(var2, 0.1F) + 1.0) / Math.log(2.0));
   }

   private float method01989(LivingEntity var1) {
      if (var1 == null) {
         return 1.0F;
      }

      double var2 = var1.getX() - this.field01752;
      double var4 = var1.getY() + var1.getHeight() * 0.5 - this.field01957;
      double var6 = var1.getZ() - this.field01005;
      double var8 = var2 * var2 + var4 * var4 + var6 * var6;
      double var10 = Math.max(Math.sqrt(var8), 0.5);
      return (float)Math.toDegrees(Math.atan2(var1.getWidth() * 0.5, var10));
   }

   private void method05354() {
      if (!Float.isNaN(this.field02082) && field00117.player != null) {
         this.field01094 = MathHelper.wrapDegrees(field00117.player.getYaw() - this.field02082) - this.field01201;
         this.field01173 = field00117.player.getPitch() - this.field01059 - this.field01378;
      } else {
         this.field01094 = this.field01173 = 0.0F;
      }
   }

   private void method03970() {
      if (field00117.player != null) {
         this.field02082 = field00117.player.getYaw();
         this.field01059 = field00117.player.getPitch();
      }
   }

   private float method00713(float var1, float var2, float var3) {
      if (Math.abs(var1) < 1.0E-4F) {
         return var1;
      }

      float var4 = var3 > 1.0E-4F ? var2 / var3 : 0.0F;
      if (Math.abs(var4) < 0.1F) {
         return var1;
      }

      if (var1 > 0.0F == var4 > 0.0F) {
         return var1 * 0.95F;
      }

      float var5 = 1.0F - MathHelper.clamp(Math.abs(var4) / Math.abs(var1), 0.0F, 0.8F);
      return var1 * var5;
   }

   private boolean method00717(float var1, float var2, float var3) {
      if (var3 < 1.0E-4F) {
         return false;
      }

      float var4 = this.field01094 / var3;
      float var5 = this.field01173 / var3;
      float var6 = this.field01150.method04086();
      if (Math.abs(var4) < var6 && Math.abs(var5) < var6) {
         return false;
      }

      boolean var7 = Math.signum(var1) == Math.signum(var4) || Math.abs(var1) < 0.5F;
      boolean var8 = Math.signum(var2) == Math.signum(var5) || Math.abs(var2) < 0.5F;
      return var7 || var8;
   }

   private void method01999(LivingEntity var1, double var2, double var4, double var6) {
      if (this.field01987.method04473() && var1 != null && field00117.player != null && field00117.world != null) {
         long var8 = field00117.world.getTime();
         long var10 = Math.max(1L, (long)(this.field02092.method04086() * 20.0F));
         if (var8 - this.field01228 >= var10) {
            this.field01228 = var8;
            Vec3d var12 = new Vec3d(var2, var4, var6);
            this.field00528.method00845((int)this.field01913.method04086(), this.field02039.method04086(), this.field01028.method04473());
            this.field00528.method02379(var12, var1, field00117.player.getPos(), field00117.player.distanceTo(var1));
         }
      }
   }

   private void method03993() {
      if (this.field00145 != null && field00117.player != null && field00117.world != null) {
         Entity var1 = field00117.world.getEntityById(this.field00145.getId());
         boolean var2 = var1 != this.field00145
            || this.field00145.distanceTo(field00117.player) > this.field00904.method04086() + 1.0F
            || this.field00145.isDead()
            || this.field00145.getHealth() <= 0.0F;
         if (var2) {
            if (this.field01530 == this.field00145) {
               this.field01530 = null;
            }

            this.field00145 = null;
            this.field01336 = false;
            this.field00006 = System.currentTimeMillis();
         }
      }
   }

   private void method04059() {
      if (this.method05355()) {
         this.field01530 = null;
      } else {
         long var1 = System.currentTimeMillis();
         if (this.field01530 != null && var1 - this.field01496 > (long)(this.field01607.method04086() * 1000.0F)) {
            this.field01530 = null;
         }

         if (this.field00897.method02964(this.field01031) && this.field01530 == null && field00117.options.attackKey.isPressed() && this.field00145 != null) {
            this.field01530 = this.field00145;
            this.field01496 = var1;
         }

         if (this.field00897.method02964(this.field01147) && this.field01530 == null && this.field00145 != null && var1 - this.field00006 < 500L) {
            this.field01530 = this.field00145;
            this.field01496 = var1;
         }

         if (this.field01530 != null
            && field00117.player != null
            && (
               this.field01530.isDead() || this.field01530.getHealth() <= 0.0F || this.field01530.distanceTo(field00117.player) > this.field00904.method04086()
            )) {
            this.field01530 = null;
         }
      }
   }

   private void method04075() {
      if (field00117.player != null) {
         if (this.field01530 != null && !this.method05355()) {
            this.field00145 = this.field01530;
         } else {
            TargetManager var1 = Rockstar.method00215().method00222();
            if (var1 != null) {
               LivingEntity var3 = var1.method00086() instanceof LivingEntity var4 ? var4 : null;
               boolean var7 = var3 == null || !var3.isAlive() || var3.distanceTo(field00117.player) > this.field00904.method04086();
               if (var7) {
                  var1.method03007(this.method00224());
                  var3 = var1.method00086() instanceof LivingEntity var6 ? var6 : null;
               }

               if (var3 != this.field00145) {
                  this.method01994(var3);
               }
            }
         }
      }
   }

   private void method01994(LivingEntity var1) {
      this.field00145 = var1;
      this.field00006 = System.currentTimeMillis();
      this.field01336 = false;
      this.field01493 = this.field00831 = this.field01225 = 0.0;
      this.field01127 = 0.0F;
      this.field00995 = false;
      this.field01341 = this.field00145 != null ? this.method04759(this.field00145) : 0.0F;
      this.field01735 = true;
      this.field01006 = 0.0F;
      this.field01228 = 0L;
      this.field00528.method00451();
      this.field00541.method00451();
      this.field02023 = false;
      this.field01395 = this.field01411 = 0.0F;
      this.field01422 = this.field01837 = 0.0F;
      this.field01738[0] = this.field01738[1] = 0.0F;
   }

   private float method04759(LivingEntity var1) {
      float var2 = 0.5F;
      if (field00117.player != null && var1 != null) {
         double var3 = var1.getX() - field00117.player.getX();
         double var5 = var1.getY() - field00117.player.getY();
         double var7 = var1.getZ() - field00117.player.getZ();
         double var9 = var3 * var3 + var5 * var5 + var7 * var7;
         if (var9 > 1.0E-4F) {
            double var11 = Math.sqrt(var9);
            Vec3d var13 = field00117.player.getRotationVector();
            float var14 = (float)((var3 * var13.x + var5 * var13.y + var7 * var13.z) / var11);
            var2 += (1.0F - MathHelper.clamp(var14, -1.0F, 1.0F)) * 0.08F;
         }

         return var2;
      } else {
         return var2;
      }
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
         .method00661(this.field00904.method04086())
         .method01545(
            this.field01599.method02964(this.field01274)
               ? Class0462.field01508
               : (this.field01599.method02964(this.field01789) ? Class0462.field00838 : Class0462.field00068)
         )
         .method00224();
   }

   private boolean method04060() {
      return this.field00145 != null && field00117.player != null ? this.field00318.method04473() && !this.field00995 : true;
   }

   private float method00939(long var1) {
      if (this.field00834 == 0L) {
         return 0.016666668F;
      }

      long var3 = var1 - this.field00834;
      return var3 <= 0L ? 0.016666668F : Math.min((float)var3 / 1.0E9F, 0.1F);
   }

   private float method03626() {
      try {
         return field00117.getRenderTickCounter().getTickDelta(false);
      } catch (NoSuchMethodError var2) {
         return 1.0F;
      }
   }

   private float method04542(float var1, float var2, float var3) {
      return var1 + (var2 - var1) * var3;
   }

   private boolean method04076() {
      return field00117.player == null || field00117.world == null || field00117.player.isDead();
   }

   public boolean method04272() {
      if (field00117.player == null) {
         return false;
      }

      ItemStack var1 = field00117.player.getStackInHand(Hand.MAIN_HAND);
      if (var1.isEmpty()) {
         return false;
      }

      Item var2 = var1.getItem();

      try {
         Reference var3 = var2.getRegistryEntry();
         if (var3 != null) {
            String var8 = var3.getIdAsString();

            for (String var10 : field00081) {
               if (var8.contains(var10)) {
                  return true;
               }
            }
         }
      } catch (Exception var7) {
         String var4 = var2.getName().getString().toLowerCase();

         for (String var6 : field00081) {
            if (var4.contains(var6)) {
               return true;
            }
         }
      }

      return false;
   }

   @Generated
   public Class1437 method00427() {
      return this.field00645;
   }
}
