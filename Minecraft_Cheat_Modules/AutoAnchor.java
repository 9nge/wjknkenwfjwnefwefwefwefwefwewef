package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.SliderSetting;
import rockstar.ui.notifications.NotificationType;
import rockstar.utils.I18n;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.Nullable;
import pyrock.events.network.SendPacketEvent;
import pyrock.events.player.ClientPlayerTickEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Auto Anchor", method04432 = Category.field00395, method03909 = "modules.descriptions.auto_anchor")
public class AutoAnchor extends Module {
   private BooleanSetting field00318;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private static final float field00004 = 4.5F;
   private static final float field01494 = 0.5F;
   private static final float field00832 = 5.0F;
   private static final double field00003 = 5.0;
   private static final int field00005 = 4;
   private static final float field01226 = 180.0F;
   private static final long field00006 = 4000L;
   private static final long field01496 = 25L;
   private static final long field00834 = 1L;
   private static final long field01228 = 5000L;
   private final Map<BlockPos, Class1317> field00078 = new ConcurrentHashMap<>();
   private final Class1325 field00606 = new Class1325();
   private final Class1325 field01711 = new Class1325();
   private final Class1325 field00974 = new Class1325();
   private int field01495 = Integer.MIN_VALUE;
   private final EventListener<SendPacketEvent> field00346 = var1 -> {
      if (this.method04473()) {
         if (!var1.isCancelled()) {
            if (var1.getPacket() instanceof UpdateSelectedSlotC2SPacket var2) {
               this.field01495 = var2.getSelectedSlot();
            }
         }
      }
   };
   private final EventListener<ClientPlayerTickEvent> field01611 = var1 -> {
      if (field00117.player != null && field00117.world != null && field00117.interactionManager != null && field00117.getNetworkHandler() != null) {
         if (!RespawnAnchorBlock.isNether(field00117.world)) {
            this.method04334();
            BlockPos var2 = this.method00112();
            if (var2 != null) {
               this.method02303(var2);
            } else {
               if (this.field00318.method04473()) {
                  this.method05167();
               }
            }
         }
      }
   };

   public AutoAnchor() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00318 = new BooleanSetting(this, "modules.settings.auto_anchor.place").method03531(true);
      this.field00340 = new SliderSetting(this, "modules.settings.auto_anchor.min_damage", "modules.settings.auto_anchor.min_damage.desc")
         .method00660(1.0F)
         .method04520(20.0F)
         .method03699(0.5F)
         .method04137(4.0F);
      this.field01607 = new SliderSetting(this, "modules.settings.auto_anchor.max_self_damage", "modules.settings.auto_anchor.max_self_damage.desc")
         .method00660(0.0F)
         .method04520(36.0F)
         .method03699(0.5F)
         .method04137(12.0F);
   }

   @Override
   public void method05070() {
      this.field01495 = field00117.player != null ? field00117.player.getInventory().selectedSlot : Integer.MIN_VALUE;
   }

   @Override
   public void method05256() {
      super.method05256();
      this.field00078.clear();
      this.field01495 = Integer.MIN_VALUE;
   }

   private void method04334() {
      long var1 = System.currentTimeMillis();
      Iterator var3 = this.field00078.entrySet().iterator();

      while (var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         if (var1 - ((Class1317)var4.getValue()).field00006 > 4000L) {
            var3.remove();
         } else if (!(field00117.world.getBlockState((BlockPos)var4.getKey()).getBlock() instanceof RespawnAnchorBlock)
            && var1 - ((Class1317)var4.getValue()).field00006 > 500L) {
            var3.remove();
         }
      }
   }

   private void method02303(BlockPos var1) {
      BlockState var2 = field00117.world.getBlockState(var1);
      if (var2.getBlock() instanceof RespawnAnchorBlock) {
         Class1317 var3 = this.field00078.computeIfAbsent(var1.toImmutable(), var0 -> new Class1317());
         int var4 = Math.max((Integer)var2.get(RespawnAnchorBlock.CHARGES), var3.field00005);
         boolean var5 = var4 >= 4;
         int var6 = this.method02063(Items.GLOWSTONE);
         boolean var7 = field00117.player.getMainHandStack().isOf(Items.GLOWSTONE);
         boolean var8 = field00117.player.getOffHandStack().isOf(Items.GLOWSTONE);
         if (!var5 && !var7 && !var8 && var6 == -1) {
            this.method01346("auto_anchor.no_glowstone", Items.GLOWSTONE.getName().getString());
         } else if (this.field00606.method00947(25L)) {
            Vec3d var9 = new Vec3d(var1.getX() + 0.5, var1.getY() + 1.0, var1.getZ() + 0.5);
            if (!(field00117.player.getEyePos().distanceTo(var9) > 4.5)) {
               if (this.method03814(var9)) {
                  this.method02351(var9);
                  Hand var10 = Hand.MAIN_HAND;
                  int var11 = field00117.player.getInventory().selectedSlot;
                  boolean var12 = false;
                  if (var7) {
                     var10 = Hand.MAIN_HAND;
                  } else if (var8) {
                     var10 = Hand.OFF_HAND;
                  } else if (var6 != -1 && !var5) {
                     field00117.player.getInventory().selectedSlot = var6;
                     this.method03723(var6);
                     var12 = true;
                  }

                  BlockHitResult var13 = new BlockHitResult(var9, Direction.UP, var1, false);
                  field00117.interactionManager.interactBlock(field00117.player, var10, var13);
                  field00117.player.swingHand(var10);
                  if (!var5) {
                     var3.field00005++;
                  }

                  var3.field00006 = System.currentTimeMillis();
                  this.field00606.method00451();
                  if (var12) {
                     field00117.player.getInventory().selectedSlot = var11;
                     this.method03723(var11);
                  }
               }
            }
         }
      }
   }

   @Nullable
   private BlockPos method00112() {
      BlockPos var1 = field00117.player.getBlockPos();
      int var2 = (int)Math.ceil(4.5);
      float var3 = -1.0F;
      double var4 = Double.MAX_VALUE;
      BlockPos var6 = null;

      for (int var7 = -var2; var7 <= var2; var7++) {
         for (int var8 = -var2; var8 <= var2; var8++) {
            for (int var9 = -var2; var9 <= var2; var9++) {
               BlockPos var10 = var1.add(var7, var8, var9);
               if (field00117.world.getBlockState(var10).getBlock() instanceof RespawnAnchorBlock) {
                  Vec3d var11 = new Vec3d(var10.getX() + 0.5, var10.getY() + 1.0, var10.getZ() + 0.5);
                  if (!(field00117.player.getEyePos().distanceTo(var11) > 4.5)
                     && this.method03814(var11)
                     && this.method02304(var10)
                     && !this.method04815(var10)) {
                     Vec3d var12 = var10.toCenterPos();
                     if (this.method04825(var12)) {
                        float var13 = this.method02343(var12);
                        if (this.method02352(var12)) {
                           double var14 = field00117.player.squaredDistanceTo(var12);
                           if (var13 > var3 || var13 == var3 && var14 < var4) {
                              var3 = var13;
                              var4 = var14;
                              var6 = var10.toImmutable();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var6;
   }

   private void method05167() {
      int var1 = this.method02063(Items.RESPAWN_ANCHOR);
      boolean var2 = field00117.player.getMainHandStack().isOf(Items.RESPAWN_ANCHOR) || field00117.player.getOffHandStack().isOf(Items.RESPAWN_ANCHOR);
      if (var1 == -1 && !var2) {
         this.method01346("auto_anchor.no_anchor", Items.RESPAWN_ANCHOR.getName().getString());
      } else if (this.method04772(Items.GLOWSTONE) < 4) {
         this.method01346("auto_anchor.no_glowstone", Items.GLOWSTONE.getName().getString());
      } else {
         BlockPos var3 = this.method04398();
         if (var3 != null) {
            if (this.field01711.method00947(1L)) {
               Vec3d var4 = new Vec3d(var3.getX() + 0.5, var3.getY() + 1.0, var3.getZ() + 0.5);
               this.method02351(var4);
               int var5 = field00117.player.getInventory().selectedSlot;
               boolean var6 = false;
               Hand var7 = field00117.player.getOffHandStack().isOf(Items.RESPAWN_ANCHOR) ? Hand.OFF_HAND : Hand.MAIN_HAND;
               if (var7 == Hand.MAIN_HAND && !field00117.player.getMainHandStack().isOf(Items.RESPAWN_ANCHOR)) {
                  field00117.player.getInventory().selectedSlot = var1;
                  this.method03723(var1);
                  var6 = true;
               }

               BlockHitResult var8 = new BlockHitResult(var4, Direction.UP, var3, false);
               field00117.interactionManager.interactBlock(field00117.player, var7, var8);
               field00117.player.swingHand(var7);
               this.field00078.put(var3.up().toImmutable(), new Class1317());
               this.field01711.method00451();
               if (var6) {
                  field00117.player.getInventory().selectedSlot = var5;
                  this.method03723(var5);
               }
            }
         }
      }
   }

   @Nullable
   private BlockPos method04398() {
      BlockPos var1 = field00117.player.getBlockPos();
      int var2 = (int)Math.ceil(4.5);
      BlockState var3 = Blocks.RESPAWN_ANCHOR.getDefaultState();
      float var4 = -1.0F;
      double var5 = Double.MAX_VALUE;
      BlockPos var7 = null;

      for (int var8 = -var2; var8 <= var2; var8++) {
         for (int var9 = -4; var9 <= 2; var9++) {
            for (int var10 = -var2; var10 <= var2; var10++) {
               BlockPos var11 = var1.add(var8, var9, var10);
               BlockPos var12 = var11.up();
               if (field00117.world.getBlockState(var11).isSolidBlock(field00117.world, var11)
                  && field00117.world.getBlockState(var12).isReplaceable()
                  && field00117.world.canPlace(var3, var12, ShapeContext.absent())) {
                  Vec3d var13 = new Vec3d(var11.getX() + 0.5, var11.getY() + 1.0, var11.getZ() + 0.5);
                  if (!(field00117.player.getEyePos().distanceTo(var13) > 4.5)
                     && this.method03814(var13)
                     && this.method02304(var12)
                     && !this.method04815(var12)) {
                     Vec3d var14 = var12.toCenterPos();
                     if (this.method04825(var14) && this.method02352(var14)) {
                        float var15 = this.method02343(var14);
                        double var16 = field00117.player.squaredDistanceTo(var14);
                        if (var15 > var4 || var15 == var4 && var16 < var5) {
                           var4 = var15;
                           var5 = var16;
                           var7 = var11.toImmutable();
                        }
                     }
                  }
               }
            }
         }
      }

      return var7;
   }

   private boolean method02352(Vec3d var1) {
      float var2 = this.field00340.method04086();
      LivingEntity var3 = Rockstar.method00215().method00222().method00088();
      if (var3 != null && var3.isAlive() && var3 != field00117.player) {
         return var3 instanceof PlayerEntity var4 && Rockstar.method00215().method00238().method01267(var4.getName().getString())
            ? this.method02343(var1) >= var2
            : this.method02376(var1, var3) >= var2;
      } else {
         return this.method02343(var1) >= var2;
      }
   }

   private boolean method04825(Vec3d var1) {
      float var2 = this.method02376(var1, field00117.player);
      return var2 > this.field01607.method04086() ? false : var2 < field00117.player.getHealth() + field00117.player.getAbsorptionAmount();
   }

   private float method02343(Vec3d var1) {
      float var2 = 0.0F;

      for (PlayerEntity var4 : field00117.world.getPlayers()) {
         if (var4 != field00117.player && var4.isAlive() && !Rockstar.method00215().method00238().method01267(var4.getName().getString())) {
            var2 = Math.max(var2, this.method02376(var1, var4));
         }
      }

      return var2;
   }

   private float method02376(Vec3d var1, LivingEntity var2) {
      Vec3d var3 = var2.getBoundingBox().getCenter();
      double var4 = var3.distanceTo(var1);
      if (var4 > 5.0) {
         return 0.0F;
      }

      double var6 = this.method02389(var1, var3) ? 1.0 : 0.35;
      double var8 = (1.0 - var4 / 5.0) * var6;
      float var10 = (float)((var8 * var8 + var8) / 2.0 * 7.0 * 10.0 + 1.0);
      Difficulty var11 = field00117.world.getDifficulty();

      var10 *= switch (var11) {
         case PEACEFUL -> 0.0F;
         case EASY -> 0.5F;
         case HARD -> 1.5F;
         default -> 1.0F;
      };
      float var12 = var2.getArmor();
      var10 *= 1.0F - Math.min(var12 / (var12 + 20.0F), 0.8F);
      return Math.max(0.0F, var10);
   }

   private boolean method02304(BlockPos var1) {
      return var1.getY() + 0.5 - field00117.player.getY() >= 0.5;
   }

   private boolean method04815(BlockPos var1) {
      Vec3d var2 = Vec3d.ofCenter(var1);
      Box var3 = new Box(var2, var2).expand(5.0);

      for (PlayerEntity var5 : field00117.world.getEntitiesByClass(PlayerEntity.class, var3, var0 -> var0 != field00117.player)) {
         if (var5.isAlive() && Rockstar.method00215().method00238().method01267(var5.getName().getString())) {
            return true;
         }
      }

      return false;
   }

   private boolean method02389(Vec3d var1, Vec3d var2) {
      return field00117.world.raycast(new RaycastContext(var1, var2, ShapeType.COLLIDER, FluidHandling.NONE, field00117.player)).getType() == Type.MISS;
   }

   private boolean method03814(Vec3d var1) {
      BlockHitResult var2 = field00117.world
         .raycast(new RaycastContext(field00117.player.getEyePos(), var1, ShapeType.COLLIDER, FluidHandling.NONE, field00117.player));
      return var2.getType() == Type.MISS;
   }

   private int method02063(Item var1) {
      for (int var2 = 0; var2 < 9; var2++) {
         if (field00117.player.getInventory().getStack(var2).isOf(var1)) {
            return var2;
         }
      }

      return -1;
   }

   private int method04772(Item var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < 9; var3++) {
         ItemStack var4 = field00117.player.getInventory().getStack(var3);
         if (var4.isOf(var1)) {
            var2 += var4.getCount();
         }
      }

      ItemStack var5 = field00117.player.getOffHandStack();
      if (var5.isOf(var1)) {
         var2 += var5.getCount();
      }

      return var2;
   }

   private void method01346(String var1, String var2) {
      if (this.field00974.method00947(5000L)) {
         this.field00974.method00451();
         Rockstar.method00215()
            .method00410()
            .method03454(NotificationType.field01716, I18n.method01151(var1), I18n.method01474("auto_anchor.need_item", var2));
      }
   }

   private void method03723(int var1) {
      if (field00117.getNetworkHandler() != null && var1 >= 0 && var1 <= 8) {
         if (var1 != this.field01495) {
            field00117.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(var1));
         }
      }
   }

   private void method02351(Vec3d var1) {
      Class1271 var2 = Class1275.method02350(var1);
      Rockstar.method00215().method00392().method03419(var2, Class1268.field01321, 180.0F, 180.0F, 180.0F, Class1277.field02014);
   }
}
