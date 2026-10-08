package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyrock.events.window.MouseEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Auto Explosion", method03909 = "modules.descriptions.auto_explosion", method04432 = Category.field00395)
public class AutoExplosion extends Module {
   private MultiChoiceSetting field00331;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private final Class1325 field00606 = new Class1325();
   private final Class1325 field01711 = new Class1325();
   private BlockPos field00169;
   private BlockPos field01535;
   private int field00005 = -1;
   private final EventListener<MouseEvent> field00346 = var1 -> {
      if (field00117.player != null && field00117.world != null) {
         if (field00117.currentScreen == null) {
            if (var1.getButton() == 1 && var1.getAction() == 1) {
               if (field00117.player.getMainHandStack().isEmpty() || field00117.player.getMainHandStack().getItem() instanceof SwordItem) {
                  if (field00117.crosshairTarget instanceof BlockHitResult var2) {
                     if (field00117.world.getBlockState(var2.getBlockPos()).isOf(Blocks.OBSIDIAN)) {
                        BlockPos var4 = var2.getBlockPos().up();
                        if (field00117.world.getBlockState(var4).isAir()) {
                           if (!this.method00585(var4.getY())) {
                              this.field00169 = var4.toImmutable();
                              this.field00606.method00451();
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   };

   public AutoExplosion() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00331 = new MultiChoiceSetting(this, "Не взрывать");
      this.field00332 = new MultiChoiceValue(this.field00331, "Себя").select();
      this.field01604 = new MultiChoiceValue(this.field00331, "Друзей").select();
      this.field00901 = new MultiChoiceValue(this.field00331, "Предметы").select();
   }

   @Override
   public void method05256() {
      this.field00169 = null;
      this.field01535 = null;
   }

   @Override
   public void method03678() {
      if (field00117.player != null && field00117.world != null) {
         Class0992 var1 = Class0993.method00338();
         Class1004 var2 = (Class1004)var1.method02069(Items.END_CRYSTAL);
         if (var2 == null) {
            this.field00169 = null;
            this.field01535 = null;
         } else {
            if (this.field00169 != null && this.field00606.method00947(1L)) {
               if (!this.method00585(this.field00169.getY())) {
                  this.method00918(var2.method00004(), this.field00169);
                  this.field01535 = this.field00169;
               }

               this.field00169 = null;
               this.field00606.method00451();
            }

            EndCrystalEntity var3 = this.method02290(this.field01535);
            if (var3 != null) {
               Vec3d var4 = var3.getPos().add(0.0, 0.5, 0.0);
               float[] var5 = this.method02353(var4);
               Rockstar.method00215().method00392().method03412(new Class1271(var5[0], var5[1]));
               this.method02025(var3);
            }

            super.method03678();
         }
      }
   }

   private void method00918(int var1, BlockPos var2) {
      if (field00117.player != null && field00117.world != null) {
         int var3 = var1 - 36;
         if (var3 >= 0 && var3 <= 8) {
            BlockPos var4 = var2.down();
            Vec3d var5 = new Vec3d(var4.getX() + 0.5, var4.getY() + 1.0, var4.getZ() + 0.5);
            float[] var6 = this.method02353(var5);
            Rockstar.method00215().method00392().method03412(new Class1271(var6[0], var6[1]));
            int var7 = field00117.player.getInventory().selectedSlot;
            field00117.player.getInventory().selectedSlot = var3;
            BlockHitResult var8 = new BlockHitResult(var5, Direction.UP, var4, false);
            field00117.interactionManager.interactBlock(field00117.player, Hand.MAIN_HAND, var8);
            field00117.player.swingHand(Hand.MAIN_HAND);
            field00117.player.getInventory().selectedSlot = var7;

            for (Entity var10 : field00117.world.getEntities()) {
               if (var10 instanceof EndCrystalEntity var11 && var11.squaredDistanceTo(var5) < 1.0) {
                  return;
               }
            }
         }
      }
   }

   private EndCrystalEntity method02290(BlockPos var1) {
      if (var1 == null) {
         return null;
      }

      Box var2 = new Box(var1.getX(), var1.getY(), var1.getZ(), var1.getX() + 1.0, var1.getY() + 2.0, var1.getZ() + 1.0);

      for (Entity var4 : field00117.world.getOtherEntities(null, var2)) {
         if (var4 instanceof EndCrystalEntity var5 && var5.isAlive()) {
            return var5;
         }
      }

      return null;
   }

   private void method02025(EndCrystalEntity var1) {
      if (this.method02026(var1)) {
         if (this.field01711.method00947(80L)) {
            field00117.interactionManager.attackEntity(field00117.player, var1);
            field00117.player.swingHand(Hand.MAIN_HAND);
            this.field00005 = var1.getId();
            this.field01711.method00451();
         }
      }
   }

   private boolean method02026(EndCrystalEntity var1) {
      if (var1 == null || !var1.isAlive()) {
         return false;
      } else if (this.method04766(var1)) {
         return false;
      } else if (field00117.player.distanceTo(var1) > 4.0) {
         return false;
      } else {
         return this.field00005 == var1.getId() && !this.field01711.method00947(300L) ? false : field00117.player.getAttackCooldownProgress(1.0F) >= 1.0F;
      }
   }

   private boolean method04766(EndCrystalEntity var1) {
      if (this.method00585(var1.getY())) {
         return true;
      } else {
         return this.field01604.isSelected() && this.method03795(var1) ? true : this.field00901.isSelected() && this.method04199(var1);
      }
   }

   private boolean method00585(double var1) {
      return this.field00332.isSelected() && var1 <= field00117.player.getY() + 0.1;
   }

   private boolean method03795(EndCrystalEntity var1) {
      Box var2 = var1.getBoundingBox().expand(6.0);

      for (PlayerEntity var4 : field00117.world.getEntitiesByClass(PlayerEntity.class, var2, var0 -> var0 != field00117.player)) {
         if (var4.isAlive() && Rockstar.method00215().method00238().method01267(var4.getName().getString())) {
            return true;
         }
      }

      return false;
   }

   private boolean method04199(EndCrystalEntity var1) {
      Box var2 = var1.getBoundingBox().expand(6.0);

      for (ItemEntity var4 : field00117.world.getEntitiesByClass(ItemEntity.class, var2, var0 -> true)) {
         if (var4 != null && var4.getStack() != null) {
            Item var5 = var4.getStack().getItem();
            if (var5 == Items.TOTEM_OF_UNDYING
               || var5 == Items.END_CRYSTAL
               || var5 == Items.ENCHANTED_GOLDEN_APPLE
               || var5 == Items.NETHERITE_HELMET
               || var5 == Items.NETHERITE_CHESTPLATE
               || var5 == Items.NETHERITE_LEGGINGS
               || var5 == Items.NETHERITE_BOOTS
               || var5 == Items.NETHERITE_SWORD
               || var5 == Items.DIAMOND_SWORD
               || var5 == Items.ELYTRA
               || var5 == Items.TRIDENT) {
               return true;
            }
         }
      }

      return false;
   }

   private float[] method02353(Vec3d var1) {
      Vec3d var2 = new Vec3d(
         field00117.player.getX(), field00117.player.getY() + field00117.player.getEyeHeight(field00117.player.getPose()), field00117.player.getZ()
      );
      double var3 = var1.x - var2.x;
      double var5 = var1.y - var2.y;
      double var7 = var1.z - var2.z;
      double var9 = Math.sqrt(var3 * var3 + var7 * var7);
      float var11 = (float)Math.toDegrees(Math.atan2(var7, var3)) - 90.0F;
      float var12 = (float)(-Math.toDegrees(Math.atan2(var5, var9)));
      return new float[]{
         field00117.player.getYaw() + MathHelper.wrapDegrees(var11 - field00117.player.getYaw()),
         field00117.player.getPitch() + MathHelper.wrapDegrees(var12 - field00117.player.getPitch())
      };
   }
}
