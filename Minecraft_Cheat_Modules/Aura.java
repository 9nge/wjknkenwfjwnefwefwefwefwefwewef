package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.modules.movement.AirStuck;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.ModeSetting;
import rockstar.feature.settings.impl.ModeValue;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;
import rockstar.feature.settings.impl.SliderSetting;
import rockstar.utils.I18n;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import moscow.rockstar.mixin.accessors.ItemCooldownEntryAccessor;
import moscow.rockstar.mixin.accessors.ItemCooldownManagerAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import pyrock.events.game.EntityJumpEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Aura", method04432 = Category.field00395, method03909 = "modules.descriptions.aura")
public class Aura extends Module {
   private SliderSetting field00340;
   private SliderSetting field01607;
   private MultiChoiceSetting field00331;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private MultiChoiceValue field01276;
   private MultiChoiceValue field01791;
   private MultiChoiceValue field01992;
   private MultiChoiceValue field01032;
   private ModeSetting field00327;
   private ModeValue field00328;
   private ModeValue field01600;
   private ModeValue field00898;
   private ModeSetting field01599;
   private ModeValue field01274;
   private ModeValue field01789;
   private ModeSetting field00897;
   private ModeValue field01991;
   private ModeValue field01031;
   private ModeValue field01147;
   private ModeSetting field01273;
   private ModeValue field01357;
   private ModeValue field01436;
   private ModeValue field01849;
   private ModeValue field01911;
   private BooleanSetting field00318;
   private BooleanSetting field01594;
   private ModeSetting field01788;
   private ModeValue field02037;
   private ModeValue field02090;
   private Class0424 field00330;
   private BooleanSetting field00893;
   private BooleanSetting field01270;
   private BooleanSetting field01785;
   private BooleanSetting field01987;
   private BooleanSetting field01028;
   private BooleanSetting field01145;
   private BooleanSetting field01356;
   private ModeSetting field01990;
   private ModeValue field01066;
   private ModeValue field01101;
   private ModeValue field01179;
   private ModeValue field01205;
   private ModeValue field01381;
   private ModeSetting field01030;
   private ModeValue field01404;
   private ModeValue field01458;
   private ModeValue field01477;
   private ModeSetting field01146;
   private ModeValue field01869;
   private ModeValue field01887;
   private ModeValue field01930;
   private MultiChoiceSetting field01603;
   private MultiChoiceValue field01148;
   private MultiChoiceValue field01358;
   private MultiChoiceValue field01437;
   private MultiChoiceValue field01850;
   private MultiChoiceValue field01912;
   private Class1325 field00606;
   private long field00006;
   private float field00004;
   boolean field00688;
   boolean field01735;
   boolean field00995;
   int field00005;
   private final Class1423 field00639 = new Class1423();
   private Class1430 field00641;
   private int field01495;
   private Class1271 field00592;
   private boolean field01336;
   private boolean field01834;
   private int field00833 = -1;
   private boolean field02023;
   private static final float field01494 = 1.5F;
   private static final int field01227 = 4;
   private static final long field01496 = 200L;
   private float field00832 = Class1010.method05090(0.0F, 1.0F);
   private long field00834;
   private final Map<String, Integer> field00078 = new LinkedHashMap<>();
   private final EventListener<EntityJumpEvent> field00346 = var1 -> {
      if (field00117.player == var1.getEntity()) {
         if (this.field01030.method02964(this.field01458)
            && field00117.player.isOnGround()
            && field00117.player.getMainHandStack().getItem() instanceof SwordItem) {
            LivingEntity var2 = Rockstar.method00215().method00222().method00088();
            if (Class0906.method01827(field00117.player).method00756(Class0802.method01989(var2), 40, true) > 10) {
               var1.cancel();
            }
         }
      }
   };

   public Aura() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field01599 = new ModeSetting(this, "modules.settings.aura.rotationMode");
      this.field01789 = new ModeValue(this.field01599, "modules.settings.aura.noRotation");
      this.field01274 = new Class1400(this.field01599).select();
      new Class1392(this.field01599);
      this.field00641 = new Class1430(this.field01599);
      this.field00897 = new ModeSetting(this, "modules.settings.aura.returnMode", () -> this.field01599.method02964(this.field01789));
      this.field01991 = new ModeValue(this.field00897, "modules.settings.aura.returnMode.none");
      this.field01031 = new ModeValue(this.field00897, "modules.settings.aura.returnMode.smooth").select();
      this.field01147 = new ModeValue(this.field00897, "modules.settings.aura.returnMode.camera");
      this.field00340 = new SliderSetting(this, "modules.settings.aura.attackDistance")
         .method00660(0.1F)
         .method04520(6.0F)
         .method03699(0.1F)
         .method04137(3.0F)
         .method02991(var0 -> " %s".formatted(I18n.method01151("block")) + Class0872.method04508(var0))
         .method02895(var1 -> {
            if (this.field01607 != null && this.field01607.method04086() < var1) {
               this.field01607.method04137(var1);
            }

            return var1;
         });
      this.field01607 = new SliderSetting(this, "modules.settings.aura.aimDistance")
         .method00660(0.1F)
         .method04520(9.0F)
         .method03699(0.1F)
         .method04137(3.0F)
         .method02991(var0 -> " %s".formatted(I18n.method01151("block")) + Class0872.method04508(var0))
         .method02895(var1 -> this.field00340 == null ? var1 : Math.max(this.field00340.method04086(), var1));
      this.field00893 = new BooleanSetting(this, "modules.settings.aura.onlyCrits").method00203();
      this.field01270 = new BooleanSetting(this, "modules.settings.aura.smart_criticals", () -> !this.field00893.method04473());
      this.field01990 = new ModeSetting(this, "modules.settings.aura.walls");
      this.field01066 = new ModeValue(this.field01990, "modules.settings.aura.walls.none").select();
      this.field01101 = new ModeValue(this.field01990, "modules.settings.aura.walls.all");
      this.field01179 = new ModeValue(this.field01990, "modules.settings.aura.walls.doors");
      this.field01205 = new ModeValue(this.field01990, "modules.settings.aura.walls.rw");
      this.field01381 = new ModeValue(this.field01990, "modules.settings.aura.walls.ft");
      this.field01785 = new BooleanSetting(this, "modules.settings.aura.rayTrace").method00203();
      this.field01145 = new BooleanSetting(this, "modules.settings.aura.targeting").method00203();
      this.field01987 = new BooleanSetting(this, "modules.settings.aura.onlyWeapon");
      this.field01028 = new BooleanSetting(this, "modules.settings.aura.auto_mace", "modules.settings.aura.auto_mace.description");
      this.field01356 = new BooleanSetting(this, "modules.settings.aura.no_hit_inv");
      this.field00331 = new MultiChoiceSetting(this, "modules.settings.aura.targets");
      this.field00332 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.players").select();
      this.field01604 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.animals").select();
      this.field00901 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.mobs").select();
      this.field01276 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.invisibles").select();
      this.field01791 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.nakedPlayers").select();
      this.field01032 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.rockUsers");
      this.field01992 = new MultiChoiceValue(this.field00331, "modules.settings.aura.targets.friends");
      this.field00327 = new ModeSetting(this, "modules.settings.aura.sorting");
      this.field00328 = new ModeValue(this.field00327, "modules.settings.aura.distanceSorting").select();
      this.field01600 = new ModeValue(this.field00327, "modules.settings.aura.healthSorting");
      this.field00898 = new ModeValue(this.field00327, "modules.settings.aura.fovSorting");
      this.field01273 = new ModeSetting(this, "modules.settings.aura.moveCorrectionMode");
      this.field01357 = new ModeValue(this.field01273, "modules.settings.aura.noMoveCorrection");
      this.field01436 = new ModeValue(this.field01273, "modules.settings.aura.directMoveCorrection");
      this.field01849 = new ModeValue(this.field01273, "modules.settings.aura.silentMoveCorrection").select();
      this.field01911 = new ModeValue(this.field01273, "modules.settings.aura.targeted_move_correction");
      this.field00318 = new BooleanSetting(this, "modules.settings.aura.force_targeted_ranged", () -> this.field01273.method02964(this.field01911));
      this.field01594 = new BooleanSetting(
         this, "modules.settings.aura.force_behind_targeted", () -> !this.field00318.method04473() || this.field01273.method02964(this.field01911)
      );
      this.field01788 = new ModeSetting(this, "modules.settings.aura.styleAttack");
      this.field02037 = new ModeValue(this.field01788, "1.8");
      this.field02090 = new ModeValue(this.field01788, "1.9").select();
      this.field00330 = new Class0424(this, "modules.settings.aura.cps_limiter", this.field02090::isSelected)
         .method03697(1.0F)
         .method04135(20.0F)
         .method05081(1.0F)
         .method00658(8.0F)
         .method04518(12.0F);
      this.field01030 = new ModeSetting(this, "modules.settings.aura.crit_calc");
      this.field01404 = new ModeValue(this.field01030, "modules.settings.aura.crit_calc.old").select();
      this.field01458 = new ModeValue(this.field01030, "modules.settings.aura.crit_calc.new");
      this.field01477 = new ModeValue(this.field01030, "modules.settings.aura.crit_calc.air");
      this.field01146 = new ModeSetting(this, "modules.settings.aura.sprint_reset");
      this.field01869 = new ModeValue(this.field01146, "modules.settings.aura.sprint_reset.smart");
      this.field01887 = new ModeValue(this.field01146, "modules.settings.aura.sprint_reset.normal");
      this.field01930 = new ModeValue(this.field01146, "modules.settings.aura.sprint_reset.packet");
      this.field01869.select();
      this.field01603 = new MultiChoiceSetting(this, "modules.settings.aura.utilities");
      this.field01148 = new MultiChoiceValue(this.field01603, "modules.settings.aura.resolver");
      this.field01358 = new MultiChoiceValue(this.field01603, "modules.settings.aura.useHit");
      this.field01912 = new MultiChoiceValue(this.field01603, "modules.settings.aura.no_teammates_1_8", () -> !this.field02037.isSelected());
      this.field01437 = new MultiChoiceValue(this.field01603, "modules.settings.aura.sync");
      this.field01850 = new MultiChoiceValue(this.field01603, "modules.settings.aura.sync_tps");
      this.field00606 = new Class1325();
   }

   public Class1419 method00420() {
      if (this.field01990 == null || this.field01990.method02964(this.field01066)) {
         return Class1419.field00637;
      } else if (this.field01990.method02964(this.field01101)) {
         return Class1419.field01719;
      } else if (this.field01990.method02964(this.field01179)) {
         return Class1419.field00981;
      } else if (this.field01990.method02964(this.field01381)) {
         return Class1419.field01329;
      } else {
         return this.field01990.method02964(this.field01205) ? Class1419.field01829 : Class1419.field00637;
      }
   }

   @Override
   public void method03678() {
      if (this.field01607.method04086() < this.field00340.method04086()) {
         this.field01607.method04137(this.field00340.method04086());
      }

      if (field00117.player != null) {
         if (this.field01599.method04423() instanceof Class1396 var1) {
            var1.update();
         }

         ElytraTarget var10 = Rockstar.method00215().method00264().method01099(ElytraTarget.class);
         boolean var11 = var10.method04473();
         float var3 = var11 ? var10.method03657().method04086() : Math.max(this.field01607.method04086(), this.method00003());
         Class0466 var4 = new Class0466()
            .method03536(this.field00332.isSelected())
            .method05032(!var11 && this.field01604.isSelected())
            .method03897(!var11 && this.field00901.isSelected())
            .method05150(this.field01276.isSelected())
            .method05311(this.field01791.isSelected())
            .method03960(this.field01992.isSelected())
            .method04252(this.field01032.isSelected())
            .method04292(this.field01912.isSelected() && this.field02037.isSelected())
            .method00661(var3);
         if (var11 || this.field00327.method02964(this.field00328)) {
            var4.method01545(Class0462.field00068);
         } else if (this.field00327.method02964(this.field01600)) {
            var4.method01545(Class0462.field01508);
         } else if (this.field00327.method02964(this.field00898)) {
            var4.method01545(Class0462.field00838);
         }

         Class0465 var5 = var4.method00224();
         LivingEntity var7 = Rockstar.method00215().method00222().method00086() instanceof LivingEntity var8 ? var8 : null;
         if (!this.field01145.method04473()
            || var7 == null
            || !var5.method01960(var7)
            || MathHelper.sqrt((float)field00117.player.squaredDistanceTo(Class1275.method01955(var7))) > var3
            || !field00117.world.hasEntity(var7)
            || !var7.isAlive()
            || AntiBot.method01995(var7)) {
            Rockstar.method00215().method00222().method03007(var5);
            var7 = Rockstar.method00215().method00222().method00086() instanceof LivingEntity var13 ? var13 : null;
         }

         if (var7 != null) {
            this.method05120(var7);
            this.field01735 = false;

            for (PlayerEntity var9 : field00117.world.getPlayers()) {
               if (field00117.player.distanceTo(var9) < 4.0F && Rockstar.method00215().method00238().method01267(var9.getNameForScoreboard())) {
                  this.field01735 = true;
               }
            }

            this.method03793(var7);
            if (this.method05355()) {
               this.field00078.merge("спринт-ресет (до удара)", 1, Integer::sum);
               return;
            }

            if (this.method02020(var7, true)) {
               if (this.method04198(var7)) {
                  this.field00078.merge("спринт-ресет (вместо удара)", 1, Integer::sum);
                  return;
               }

               this.field00078.merge("★ УДАР ВЫПОЛНЕН", 1, Integer::sum);
               this.method04761(var7);
            }
         } else {
            Class0452.field00508.method01120(this);
            this.method04334();
            if (this.field01599.method04423() instanceof Class1396 var15) {
               this.field01336 = false;
               var15.targetNull();
            }
         }
      }
   }

   public boolean method02020(LivingEntity var1, boolean var2) {
      if (!this.method04272()) {
         return this.method01267("кулдаун ауры");
      } else if (AntiBot.method01995(var1)) {
         return this.method01267("antibot");
      } else if (this.field01788.method02964(this.field02037)
         && this.field01912.isSelected()
         && var1 instanceof PlayerEntity var3
         && Class0465.method02046(field00117.player, var3)) {
         return this.method01267("союзник");
      } else {
         Criticals var8 = Rockstar.method00215().method00264().method01099(Criticals.class);
         if (var8.method05168() && !var8.method04272()) {
            return this.method01267("модуль Criticals");
         } else if (this.field01987.method04473() && !Class0826.method03679()) {
            return this.method01267("не оружие в руке");
         } else if (this.method04300()) {
            return this.method01267("используется предмет");
         } else if (this.field01599.method04423() instanceof Class1396 var4 && !var4.canAttack()) {
            return this.method01267("режим не хочет бить");
         } else {
            if (field00117.currentScreen instanceof InventoryScreen && this.field01356.method04473()) {
               return this.method01267("открыт инвентарь");
            }

            if (this.field01437.isSelected() && field00117.player.hurtTime > 0 && this.field01735) {
               return this.method01267("sync по hurtTime");
            }

            if (!this.method04762(var1)) {
               return this.method01267("далеко (attackDistance)");
            }

            if (this.method05292(var1) && Class0802.method00341() != null) {
               if (!this.method03971()) {
                  return this.method01267("ожидаем высоту для булавы");
               }

               if (Class0930.method03288(Class0927.field00498) && !this.method03994()) {
                  return this.method01267("ожидаем свап на булаву");
               }
            }

            ElytraTarget var9 = Rockstar.method00215().method00264().method01099(ElytraTarget.class);
            boolean var10 = var9.method04473() && field00117.player.isGliding() && field00117.player.getVelocity().length() < 6.0;
            if (var10) {
               return true;
            } else if (!(this.field01599.method04423() instanceof Class1392 var6 && var6.method00452())
               && !Class1010.method00630(
                  this.method00003(),
                  Rockstar.method00215().method00392().method03673().method00003(),
                  Rockstar.method00215().method00392().method03673().method04372(),
                  field00117.player,
                  var1,
                  this.method00420()
               )
               && this.field01785.method04473()
               && var2
               && (this.field00639.method00423() == null || this.field00639.method00004() <= 1)
               && !this.field00995) {
               return this.method01267("рейтрейс не проходит");
            } else {
               if (this.method05327() && this.method03794(var1) && !Class0802.method02020(var1, true)) {
                  return this.method01267("ждём крит");
               }

               this.field00078.merge("проверки пройдены", 1, Integer::sum);
               return true;
            }
         }
      }
   }

   private boolean method01267(String var1) {
      this.field00078.merge(var1, 1, Integer::sum);
      return false;
   }

   public boolean method01995(LivingEntity var1) {
      return this.method04763(var1, false);
   }

   public boolean method04763(LivingEntity var1, boolean var2) {
      Criticals var3 = Rockstar.method00215().method00264().method01099(Criticals.class);
      if (var3.method05168() && !var3.method04272()) {
         return false;
      } else if (this.field01987.method04473() && !Class0826.method03679()) {
         return false;
      } else if (this.method04300()) {
         return false;
      } else if (this.field01599.method04423() instanceof Class1396 var4 && !var4.canAttack()) {
         return false;
      } else {
         if (field00117.currentScreen instanceof InventoryScreen && this.field01356.method04473()) {
            return false;
         }

         if (this.field01437.isSelected() && field00117.player.hurtTime > 0 && this.field01735) {
            return false;
         }

         if (var2) {
            if (field00117.player
                  .getEyePos()
                  .add(0.0, -1.0, 0.0)
                  .distanceTo(Class1275.method02010(var1, Class0802.method01983(var1, this.field01148.isSelected())))
               > this.method00003()) {
               return false;
            }
         } else if (!this.method04762(var1)) {
            return false;
         }

         return !this.method05327() || !this.method03794(var1) || Class0802.method01995(var1);
      }
   }

   private boolean method03794(LivingEntity var1) {
      float var2 = this.method01989(var1);
      return var2 <= var1.getHealth();
   }

   public boolean method04272() {
      if (field00117.player == null) {
         return false;
      }

      if (field00117.player.isSubmergedInWater() && Class0930.method04122()) {
         return this.method05210();
      }

      float var1 = this.method03626();
      float var2 = this.field01850.isSelected() ? Math.min(1.0F, 0.8F * var1) : 0.8F;
      boolean var3 = field00117.player.getAttackCooldownProgress(0.0F) >= var2;
      return this.field01788.method02964(this.field02037)
         ? this.field00606.method00947(this.method00005())
         : var3 && this.field00606.method00947(Math.round(500.0F * var1));
   }

   private boolean method05210() {
      if (field00117.player.getAttackCooldownProgress(0.0F) < 1.0F) {
         this.field00834 = 0L;
         return false;
      }

      if (this.field00834 == 0L) {
         this.field00834 = System.currentTimeMillis();
      }

      long var1 = Math.round(Math.clamp(this.field00832, 0.0F, 1.0F) * 200.0F);
      return System.currentTimeMillis() - this.field00834 >= var1;
   }

   public float method01989(LivingEntity var1) {
      return 0.0F;
   }

   private void method01994(LivingEntity var1) {
      if (this.method00420() == Class1419.field01829 && field00117.player != null && field00117.world != null && var1 != null) {
         Class1271 var2 = this.field01599.method04423() instanceof Class1392 var3 && var3.method00452()
            ? var3.method00391()
            : Class1275.method02019(var1, this);
         List<BlockHitResult> var7 = this.method02017(var1, var2);
         if (!var7.isEmpty()) {
            for (BlockHitResult var5 : (Iterable<BlockHitResult>)(Iterable<?>) (var7)) {
               Direction var6 = var5.getSide();
               field00117.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, var5.getBlockPos(), var6));
               field00117.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, var5.getBlockPos(), var6));
            }
         }
      }
   }

   private List<BlockHitResult> method02017(LivingEntity var1, Class1271 var2) {
      ArrayList var3 = new ArrayList();
      if (var1 != null && field00117.player != null && field00117.world != null) {
         float var4 = field00117.getRenderTickCounter().getTickDelta(false);
         Vec3d var5 = field00117.player.getCameraPosVec(var4);
         Vec3d var6 = Class1010.method00689(var2.method04372(), var2.method00003());
         double var7 = var6.lengthSquared();
         if (var7 < 1.0E-8) {
            return var3;
         }

         Vec3d var9 = var6.multiply(1.0 / Math.sqrt(var7));
         double var10 = this.method00003();
         Vec3d var12 = var5.add(var9.multiply(var10));
         double var13 = method02404(var5, var9, var12, var10, var1);
         LinkedHashSet<Vec3d> var15 = new LinkedHashSet();
         var15.add(var5);
         Vec3d var16 = method02347(var9);
         if (var16.lengthSquared() > 1.0E-8) {
            var16 = var16.normalize().multiply(0.09);
            var15.add(var5.add(var16));
            var15.add(var5.subtract(var16));
         }

         List<BlockHitResult> var17 = new ArrayList<>();
         HashSet<BlockPos> var18 = new HashSet<>();

         for (Vec3d var20 : (Iterable<Vec3d>)(Iterable<?>) (var15)) {
            Vec3d var21 = var20.add(var9.multiply(var10));

            for (BlockHitResult var23 : this.method02405(var20, var21, var9, var5, var13)) {
               if (var18.add(var23.getBlockPos())) {
                  var17.add(var23);
               }
            }
         }

         var17.sort(Comparator.comparingDouble(var2x -> var2x.getPos().subtract(var5).dotProduct(var9)));
         var3.addAll(var17);
         return var3;
      } else {
         return var3;
      }
   }

   private static Vec3d method02347(Vec3d var0) {
      Vec3d var1 = new Vec3d(var0.x, 0.0, var0.z);
      if (var1.lengthSquared() < 1.0E-8) {
         return Vec3d.ZERO;
      }

      var1 = var1.normalize();
      return new Vec3d(-var1.z, 0.0, var1.x);
   }

   private static double method02404(Vec3d var0, Vec3d var1, Vec3d var2, double var3, LivingEntity var5) {
      Box var6 = var5.getBoundingBox();
      Optional var7 = var6.raycast(var0, var2);
      if (var7.isPresent()) {
         return ((Vec3d)var7.get()).subtract(var0).dotProduct(var1);
      }

      double var8 = var5.getEyePos().subtract(var0).dotProduct(var1);
      return var8 > 0.0 && var8 <= var3 ? var8 : var3;
   }

   private List<BlockHitResult> method02405(Vec3d var1, Vec3d var2, Vec3d var3, Vec3d var4, double var5) {
      ArrayList var7 = new ArrayList();
      Vec3d var8 = var1;
      HashSet var9 = new HashSet();
      double var10 = 1.0E-4;

      for (int var12 = 0; var12 < 40; var12++) {
         BlockHitResult var13 = field00117.world.raycast(new RaycastContext(var8, var2, ShapeType.COLLIDER, FluidHandling.NONE, field00117.player));
         if (var13.getType() != Type.BLOCK) {
            break;
         }

         BlockHitResult var14 = var13;
         double var15 = var14.getPos().subtract(var4).dotProduct(var3);
         if (var15 >= var5 - 1.0E-4) {
            break;
         }

         Block var17 = field00117.world.getBlockState(var14.getBlockPos()).getBlock();
         if (!(var17 instanceof DoorBlock) && !(var17 instanceof TrapdoorBlock)) {
            BlockPos var18 = var14.getBlockPos();
            if (!var9.add(var18)) {
               var8 = var14.getPos().add(var3.multiply(0.02));
            } else {
               var7.add(var14);
               var8 = var14.getPos().add(var3.multiply(0.01));
            }
         } else {
            var8 = var14.getPos().add(var3.multiply(0.01));
         }
      }

      return var7;
   }

   @Compile(obfuscation = 1)
   private void method04761(LivingEntity var1) {
      if (field00117.interactionManager != null && field00117.player != null) {
         this.method01994(var1);
         Hand var2 = null;
         this.field00688 = this.method04076();
         if (this.field00688) {
            var2 = field00117.player.getActiveHand();
            field00117.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
         }

         if (Class0802.method04762(var1) && Class0802.method03794(var1)) {
            Class0802.method04198(var1);
         }

         Class1004 var3 = this.method05292(var1) && this.method03971() ? Class0802.method00341() : null;
         if (var3 != null && !Class0930.method03288(Class0927.field00498)) {
            Class0972.method03327(var3, () -> field00117.interactionManager.attackEntity(field00117.player, var1));
         } else {
            field00117.interactionManager.attackEntity(field00117.player, var1);
         }

         Class0452.field00508.method01120(this);
         field00117.player.swingHand(Hand.MAIN_HAND);
         if (this.field00688 && var2 != null) {
            Hand var4 = var2;
            field00117.interactionManager
               .sendSequencedPacket(
                  field00117.world,
                  var1x -> new PlayerInteractItemC2SPacket(
                     var4,
                     var1x,
                     Rockstar.method00215().method00392().method03673().method00003(),
                     Rockstar.method00215().method00392().method03673().method04372()
                  )
               );
         }

         if (this.field01599.method04423() instanceof Class1396 var6) {
            var6.attack();
         }

         this.field00592 = new Class1271(Class1010.method00587(5.0, 20.0), Class1010.method00587(5.0, 10.0));
         this.field00606.method00451();
         this.field00832 = Class1010.method05090(0.0F, 1.0F);
         this.field00834 = 0L;
         this.field01495++;
         this.field00004 = this.field01495 % 9 == 0 ? 0.08F : 0.0F;
      }
   }

   @Compile(obfuscation = 1)
   private void method03793(LivingEntity var1) {
      if (!this.field01987.method04473() || Class0826.method03679()) {
         boolean var3 = this.field00318.method04473() && var1 != null && this.method03950(var1) && !this.field01273.method02964(this.field01911);
         this.field01336 = var3;
         Class1268 var2;
         if (this.field01273.method02964(this.field01849)) {
            var2 = Class1268.field01321;
         } else if (this.field01273.method02964(this.field01436)) {
            var2 = Class1268.field01704;
         } else if (!this.field01273.method02964(this.field01911) && !var3) {
            var2 = Class1268.field00589;
         } else {
            var2 = Class1268.field01049;
         }

         Class1274 var4 = Rockstar.method00215().method00392();
         if (this.field01599.method02964(this.field01789)) {
            if (var2 == Class1268.field01049 && var1 != null) {
               var4.method03419(var4.method00391(), Class1268.field01049, 180.0F, 180.0F, 180.0F, Class1277.field00971);
            }
         } else {
            if (this.field01599.method04423() instanceof Class1396 var5) {
               Class1284 var9 = var4.method00395();
               var5.rotate(var4, this.method00003(), this.method00420().method00452(), this.field01785.method04473(), var2, var1);
               Class1284 var7 = var4.method00395();
               if (var7 != null && var7 != var9) {
                  var7.method03405(this.method00390());
                  var7.method03427(var5 instanceof Class1430 var8 ? var8::method03422 : null);
               }
            }
         }
      }
   }

   private Class1270 method00390() {
      if (this.field00897.method02964(this.field01991)) {
         return Class1270.field00591;
      } else {
         return this.field00897.method02964(this.field01147) ? Class1270.field00969 : Class1270.field01705;
      }
   }

   private boolean method05327() {
      return this.field01270 != null && this.field01270.method04473() && !this.field01477.isSelected()
         ? field00117.options != null && field00117.options.jumpKey.isPressed() || !field00117.player.isOnGround()
         : this.field00893.method04473();
   }

   private boolean method05355() {
      if (field00117.player == null) {
         return false;
      } else if (Class0867.method00452()) {
         return true;
      } else if (this.method05327() && Class0860.method04769(field00117.player)) {
         Class0867.method02037(field00117.player);
         return true;
      } else {
         return false;
      }
   }

   private boolean method04198(LivingEntity var1) {
      boolean var2 = this.field01146.method02964(this.field01887);
      boolean var3 = this.field01146.method02964(this.field01930);
      if (Rockstar.method00215().method00264().method01099(KnockbackTweaks.class).method04473()) {
         return false;
      }

      if ((var2 || var3) && field00117.player != null) {
         if (Class0867.method00452() || Class0867.method01960(field00117.player)) {
            return true;
         } else if (!field00117.player.isSprinting()) {
            Class0867.method04754(field00117.player);
            return false;
         } else {
            Class0867.method02043(field00117.player, () -> this.method04197(var1), var3);
            return true;
         }
      } else {
         return false;
      }
   }

   private void method04197(LivingEntity var1) {
      if (this.method04473() && field00117.player != null && field00117.interactionManager != null && var1 != null && !var1.isRemoved() && var1.isAlive()) {
         if (this.method02020(var1, true)) {
            this.method04761(var1);
         }
      }
   }

   public boolean method04335() {
      if (!this.field01146.method02964(this.field01869)) {
         return false;
      } else if (Rockstar.method00215().method00264().method01099(KnockbackTweaks.class).method04473()) {
         return false;
      } else {
         LivingEntity var2 = Rockstar.method00215().method00222().method00086() instanceof LivingEntity var3 ? var3 : null;
         if (var2 == null || field00117.player == null) {
            return false;
         } else if (!this.field01788.method02964(this.field02037) && !field00117.player.isSubmergedInWater()) {
            Criticals var5 = Rockstar.method00215().method00264().method01099(Criticals.class);
            boolean var4 = var5.method05168() && (var5.method04335() && this.field00606.method00947(500L) || field00117.player.isOnGround())
               || !field00117.player.isOnGround()
                  && Class0906.method01827(field00117.player)
                     .method00752(
                        Class0802.method01989(var2),
                        !Class0930.method03288(Class0927.field00498) && !Class0930.method03288(Class0927.field01445) && !Class0930.method04122()
                           ? 1
                           : Class1010.field00062.nextInt(3)
                     );
            return this.method05327()
               && this.method03794(var2)
               && (
                  var4
                     || Class0802.method02020(var2, true)
                     || !this.field00606
                        .method00947(
                           !Class0930.method03288(Class0927.field01667) && !Class0930.method04122() ? 50L : (long)Class1010.method05090(50.0F, 150.0F)
                        )
               );
         } else {
            return false;
         }
      }
   }

   public float method00003() {
      float var1 = Rockstar.method00215().method00264().method01099(AirStuck.class).method00003();
      return var1 > 0.0F ? var1 : this.field00340.method04086();
   }

   public boolean method04762(LivingEntity var1) {
      return this.field00639.method00004() > 1 && this.field01477.isSelected()
         ? this.field00639.method00423().method00116().distanceTo(var1.getPos()) < 6.0
         : field00117.player.getEyePos().distanceTo(Class1275.method02010(var1, Class0802.method01983(var1, this.field01148.isSelected())))
            <= this.method00003();
   }

   @Override
   public void method05070() {
      if (this.field01599.method04423() instanceof Class1396 var1) {
         var1.enabled();
      }

      if (this.field01477.isSelected()) {
         this.field00639.method00451();
      }

      this.field01834 = false;
      this.method04334();
      super.method05070();
   }

   @Override
   public void method05256() {
      Class0452.field00508.method04606(this);
      this.method04334();
      Rockstar.method00215().method00222().method03678();
      if (field00117.player != null) {
         Class0867.method04754(field00117.player);
      }

      if (this.field00641 != null) {
         this.field00641.targetNull();
      }

      this.field00639.method04472();
      super.method05256();
   }

   private void method05120(LivingEntity var1) {
      if (Class0930.method03288(Class0927.field00498) && this.method05121(var1)) {
         if (!Class0452.field00508.method01121(this) && !field00117.player.getMainHandStack().isOf(Items.MACE)) {
            Class1004 var2 = Class0802.method00341();
            if (var2 != null && Class0452.field00508.method01131(this, var2)) {
               this.field00833 = field00117.player.age;
               this.field02023 = field00117.player.getItemCooldownManager().isCoolingDown(var2.method00094());
            }
         } else {
            if (field00117.player.getItemCooldownManager().isCoolingDown(Items.MACE.getDefaultStack())) {
               this.field02023 = true;
            }
         }
      } else {
         Class0452.field00508.method01120(this);
         this.method04334();
      }
   }

   private boolean method05121(LivingEntity var1) {
      if (!this.field01028.method04473()
         || var1 == null
         || field00117.player == null
         || field00117.player.isOnGround()
         || field00117.player.isGliding()
         || field00117.player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
         return false;
      }

      if (this.method05292(var1)) {
         return true;
      }

      Class1004 var2 = Class0802.method00341();
      if (var2 == null) {
         return false;
      }

      int var3 = this.method01990(var1);
      return var3 >= 0 && this.method03324(var2) >= var3 - 4;
   }

   private int method01990(LivingEntity var1) {
      double var2 = field00117.player.getY();
      double var4 = field00117.player.getVelocity().y;
      float var6 = field00117.player.fallDistance;
      double var7 = var1.getBoundingBox().maxY;

      for (int var9 = 1; var9 <= 40; var9++) {
         var2 += var4;
         if (var4 < 0.0) {
            var6 -= (float)var4;
         } else {
            var6 = 0.0F;
         }

         if (var4 < 0.0 && var6 + Math.max(0.0, var2 - var7) > 1.5) {
            return var9;
         }

         if (var4 < 0.0 && var2 < var1.getBoundingBox().minY - 2.0) {
            return -1;
         }

         var4 = (var4 - 0.08) * 0.98;
      }

      return -1;
   }

   private int method03324(Class1004 var1) {
      if (!field00117.player.getItemCooldownManager().isCoolingDown(var1.method00094())) {
         return 0;
      }

      ItemCooldownManagerAccessor var2 = (ItemCooldownManagerAccessor)(Object)field00117.player.getItemCooldownManager();
      Identifier var3 = var2.rockstar$getGroup(var1.method00094());
      Object var4 = var2.rockstar$getEntries().get(var3);
      return var4 == null ? 0 : Math.max(0, ((ItemCooldownEntryAccessor)(Object)var4).rockstar$getEndTick() - var2.rockstar$getTick());
   }

   private void method04334() {
      this.field00833 = -1;
      this.field02023 = false;
   }

   private boolean method05292(LivingEntity var1) {
      if (this.field01028.method04473()
         && var1 != null
         && field00117.player != null
         && !field00117.player.isOnGround()
         && !field00117.player.isGliding()
         && !field00117.player.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
         double var2 = Math.max(0.0, field00117.player.getY() - var1.getBoundingBox().maxY);
         return field00117.player.fallDistance + var2 > 1.5;
      } else {
         return false;
      }
   }

   private boolean method03971() {
      return field00117.player.fallDistance > 1.5F && field00117.player.getVelocity().y < 0.0;
   }

   private boolean method03994() {
      return field00117.player.getMainHandStack().isOf(Items.MACE)
            && !field00117.player.getItemCooldownManager().isCoolingDown(field00117.player.getMainHandStack())
         ? this.field00833 < 0 || this.field02023 || field00117.player.age - this.field00833 >= 4
         : false;
   }

   private long method00005() {
      int var1 = (int)this.field00330.method04372();
      int var2 = (int)this.field00330.method03626();
      if (var1 > var2) {
         int var3 = var1;
         var1 = var2;
         var2 = var3;
      }

      int var4 = Class1010.field00062.nextInt(var1, var2 + 1);
      return Math.max(1L, 1000L / var4);
   }

   private float method03626() {
      if (!this.field01850.isSelected()) {
         return 1.0F;
      } else {
         float var1 = Rockstar.method00215().method00327().method00003();
         if (var1 <= 0.0F || Float.isNaN(var1)) {
            return 1.0F;
         } else {
            return var1 >= 19.0F ? 1.0F : Math.max(25.0F / var1, 1.0F);
         }
      }
   }

   private boolean method04060() {
      if (field00117.player != null && field00117.player.isUsingItem()) {
         UseAction var1 = field00117.player.getActiveItem().getItem().getUseAction(field00117.player.getActiveItem());
         return var1 == UseAction.EAT || var1 == UseAction.DRINK;
      } else {
         return false;
      }
   }

   private boolean method04076() {
      if (field00117.player != null && field00117.player.isUsingItem()) {
         UseAction var1 = field00117.player.getActiveItem().getItem().getUseAction(field00117.player.getActiveItem());
         return var1 == UseAction.BLOCK;
      } else {
         return false;
      }
   }

   private boolean method04300() {
      if (field00117.player == null || !field00117.player.isUsingItem()) {
         return false;
      } else if (!this.field01358.isSelected()) {
         return false;
      } else {
         return this.method04060() ? true : field00117.player.getActiveHand() == Hand.OFF_HAND && !this.method04076();
      }
   }

   private boolean method03950(LivingEntity var1) {
      return var1.getMainHandStack().isOf(Items.CROSSBOW)
         || var1.getOffHandStack().isOf(Items.CROSSBOW)
         || var1.getMainHandStack().isOf(Items.TRIDENT)
         || var1.getOffHandStack().isOf(Items.TRIDENT);
   }

   private long method04374() {
      if (this.field01788.method02964(this.field02037)) {
         int var1 = (int)Math.max(this.field00330.method04372(), this.field00330.method03626());
         return Math.max(1L, 1000L / Math.max(1, var1));
      } else {
         return Math.round(500.0F * this.method03626());
      }
   }

   @Generated
   public SliderSetting method00218() {
      return this.field00340;
   }

   @Generated
   public ModeSetting method00210() {
      return this.field01599;
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
   public ModeValue method00211() {
      return this.field02037;
   }

   @Generated
   public ModeValue method04423() {
      return this.field02090;
   }

   @Generated
   public Class0424 method00212() {
      return this.field00330;
   }

   @Generated
   public BooleanSetting method03651() {
      return this.field00893;
   }

   @Generated
   public BooleanSetting method04102() {
      return this.field01270;
   }

   @Generated
   public BooleanSetting method05056() {
      return this.field01785;
   }

   @Generated
   public BooleanSetting method05244() {
      return this.field01987;
   }

   @Generated
   public BooleanSetting method03913() {
      return this.field01028;
   }

   @Generated
   public BooleanSetting method04017() {
      return this.field01145;
   }

   @Generated
   public BooleanSetting method04264() {
      return this.field01356;
   }

   @Generated
   public ModeValue method03654() {
      return this.field01404;
   }

   @Generated
   public ModeValue method04105() {
      return this.field01458;
   }

   @Generated
   public ModeValue method05059() {
      return this.field01477;
   }

   @Generated
   public MultiChoiceValue method00214() {
      return this.field01148;
   }

   @Generated
   public MultiChoiceValue method04425() {
      return this.field01358;
   }

   @Generated
   public MultiChoiceValue method03656() {
      return this.field01437;
   }

   @Generated
   public MultiChoiceValue method04106() {
      return this.field01850;
   }

   @Generated
   public MultiChoiceValue method05060() {
      return this.field01912;
   }

   @Generated
   public Class1325 method00403() {
      return this.field00606;
   }

   @Generated
   public float method04372() {
      return this.field00004;
   }

   @Generated
   public Class1423 method00422() {
      return this.field00639;
   }

   @Generated
   public Class1430 method00424() {
      return this.field00641;
   }

   @Generated
   public int method03627() {
      return this.field01495;
   }

   @Generated
   public Class1271 method00391() {
      return this.field00592;
   }

   @Generated
   public void method03412(Class1271 var1) {
      this.field00592 = var1;
   }

   @Generated
   public boolean method05168() {
      return this.field01336;
   }

   @Generated
   public Map<String, Integer> method04386() {
      return this.field00078;
   }
}
