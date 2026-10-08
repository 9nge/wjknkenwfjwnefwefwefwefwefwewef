package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.core.managers.TargetManager;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.feature.settings.impl.MultiChoiceValue;
import rockstar.feature.settings.impl.SliderSetting;
import rockstar.utils.render.ColorUtils;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import pyrock.events.player.ClientPlayerTickEvent;
import pyrock.events.render.GameRendererEvent;
import pyrock.events.render.Render3DEvent;
import pyrock.utility.render.ColorRGBA;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Aim Bot", method04432 = Category.field00395, method03909 = "modules.descriptions.aim_bot")
public class AimBot extends Module {
   private static final float field00004 = 0.05F;
   private static final float field01494 = 0.99F;
   private static final float field00832 = 3.0F;
   private static final float field01226 = 3.15F;
   private static final float field01753 = 2.5F;
   private static final int field00005 = 80;
   private static final int field01495 = 6;
   private static final int field00833 = 6;
   private static final float field01958 = 0.08F;
   private static final float field01006 = 0.98F;
   private static final float field01127 = 1.0F;
   private static final double field00003 = 12.0;
   private static final double field01493 = 8.0;
   private static final double field00831 = 0.05;
   private static final double field01225 = 1000.0;
   private static final int field01227 = 3;
   private MultiChoiceValue field00332;
   private MultiChoiceValue field01604;
   private MultiChoiceValue field00901;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private BooleanSetting field00318;
   private BooleanSetting field01594;
   private BooleanSetting field00893;
   private MultiChoiceValue field01276;
   private MultiChoiceValue field01791;
   private MultiChoiceValue field01992;
   private MultiChoiceValue field01032;
   private MultiChoiceValue field01148;
   private MultiChoiceValue field01358;
   private MultiChoiceValue field01437;
   private boolean field00688 = false;
   private int field01754 = 0;
   private Class1271 field00592 = new Class1271(0.0F, 0.0F);
   private Class1271 field01706 = null;
   private long field00006 = 0L;
   private int field01959 = -1;
   private int field01007 = -1;
   private final Deque<Vec3d> field00069 = new ArrayDeque<>();
   private Box field00170 = null;
   private boolean field01735 = false;
   private final EventListener<ClientPlayerTickEvent> field00346 = var1 -> {
      if (field00117.player != null && field00117.world != null) {
         if (!this.method04272()) {
            this.field00688 = false;
            this.field01754 = 0;
            this.field01706 = null;
            this.field00170 = null;
            this.field01007 = -1;
         } else {
            boolean var2 = field00117.player.isUsingItem();
            if (var2 && !this.field00688) {
               this.field01754 = 1;
               this.field00592 = new Class1271(field00117.player.getYaw(), field00117.player.getPitch());
            }

            this.field00688 = var2;
            if (this.field01754 > 0) {
               this.field01754--;
               this.method03412(this.field00592);
               this.field00170 = null;
            } else {
               Class0465 var3 = new Class0466()
                  .method03536(this.field01276.isSelected())
                  .method05032(this.field01791.isSelected())
                  .method03897(this.field01992.isSelected())
                  .method05150(this.field01032.isSelected())
                  .method05311(this.field01148.isSelected())
                  .method03960(this.field01358.isSelected())
                  .method04252(this.field01437.isSelected())
                  .method00661(this.field00340.method04086())
                  .method00224();
               Class1293 var4 = this.method03006(var3);
               if (var4 == null) {
                  this.field01706 = null;
                  this.field00170 = null;
               } else {
                  this.method03412(var4.field00592);
               }
            }
         }
      }
   };
   private final EventListener<GameRendererEvent> field01611 = var1 -> {
      if (field00117.player != null) {
         if (!this.field00893.method04473()) {
            if (this.field01706 == null) {
               this.field00006 = 0L;
            } else {
               long var2 = System.nanoTime();
               float var4;
               if (this.field00006 == 0L) {
                  var4 = 0.016666668F;
               } else {
                  var4 = MathHelper.clamp((float)(var2 - this.field00006) / 1.0E9F, 0.004166667F, 0.1F);
               }

               this.field00006 = var2;
               float var5 = 20.0F;
               float var6 = MathHelper.clamp(1.0F - (float)Math.exp(-var5 * var4), 0.01F, 0.95F);
               float var7 = MathHelper.wrapDegrees(this.field01706.method00003() - field00117.player.getYaw());
               float var8 = MathHelper.clamp(this.field01706.method04372(), -89.9F, 89.9F) - field00117.player.getPitch();
               float var9 = field00117.player.getYaw() + var7 * var6;
               float var10 = MathHelper.clamp(field00117.player.getPitch() + var8 * var6, -90.0F, 90.0F);
               field00117.player.setYaw(var9);
               field00117.player.setPitch(var10);
               field00117.player.setHeadYaw(var9);
               field00117.player.setBodyYaw(var9);
            }
         }
      }
   };
   private final EventListener<Render3DEvent> field00906 = var1 -> {
      if (this.field01594.method04473() && this.field00318.method04473()) {
         if (this.field00170 != null && field00117.player != null) {
            ColorRGBA var2 = ColorUtils.method04404();
            MatrixStack var3 = var1.getMatrices();
            var3.push();
            Class1169.method03551(true);
            Class1169.method04739(var3);
            RenderSystem.enableDepthTest();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            Camera var4 = field00117.gameRenderer.getCamera();
            Vec3d var5 = var4.getPos();
            Box var6 = this.field00170.offset(-var5.getX(), -var5.getY(), -var5.getZ());
            var3.push();
            var3.translate(var5.getX(), var5.getY(), var5.getZ());
            BufferBuilder var7 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            Class1139.method04744(var3, var7, var6, var2.mulAlpha(0.25F));
            Class1169.method01844(var7);
            BufferBuilder var8 = RenderSystem.renderThreadTesselator().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
            Class1139.method04191(var3, var8, var6, var2);
            Class1169.method01844(var8);
            var3.pop();
            Class1169.method00451();
            var3.pop();
         }
      }
   };

   public AimBot() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      MultiChoiceSetting var1 = new MultiChoiceSetting(this, "modules.settings.aim_bot.items");
      this.field00332 = new MultiChoiceValue(var1, "modules.settings.aim_bot.bow").select();
      this.field01604 = new MultiChoiceValue(var1, "modules.settings.aim_bot.crossbow").select();
      this.field00901 = new MultiChoiceValue(var1, "modules.settings.aim_bot.trident").select();
      this.field00318 = new BooleanSetting(this, "modules.settings.aim_bot.predict").method00203();
      this.field01594 = new BooleanSetting(this, "modules.settings.aim_bot.draw_predicted_box", () -> !this.field00318.method04473()).method00203();
      this.field00893 = new BooleanSetting(this, "modules.settings.aim_bot.silent_aim");
      this.field00340 = new SliderSetting(this, "modules.settings.aim_bot.distance").method00660(0.0F).method04520(100.0F).method03699(1.0F).method04137(30.0F);
      this.field01607 = new SliderSetting(this, "modules.settings.aim_bot.fov").method00660(1.0F).method04520(180.0F).method03699(1.0F).method04137(90.0F);
      MultiChoiceSetting var2 = new MultiChoiceSetting(this, "targets");
      this.field01276 = new MultiChoiceValue(var2, "players").select();
      this.field01791 = new MultiChoiceValue(var2, "animals").select();
      this.field01992 = new MultiChoiceValue(var2, "mobs").select();
      this.field01032 = new MultiChoiceValue(var2, "invisibles").select();
      this.field01148 = new MultiChoiceValue(var2, "nakedPlayers").select();
      this.field01437 = new MultiChoiceValue(var2, "rockUsers");
      this.field01358 = new MultiChoiceValue(var2, "friends");
   }

   @Override
   public void method05256() {
      super.method05256();
      this.field01706 = null;
      this.field00170 = null;
      this.field00069.clear();
      this.field01959 = -1;
      this.field01007 = -1;
      this.field00006 = 0L;
   }

   private Class1293 method03006(Class0465 var1) {
      float var2 = this.method00003();
      if (var2 < 0.5F) {
         this.field00170 = null;
         return null;
      }

      List var3 = this.method03004(var1);
      if (var3.isEmpty()) {
         this.field01007 = -1;
         this.field00170 = null;
         return null;
      }

      Class1293 var4 = null;
      int var5 = Math.min(var3.size(), 3);

      for (int var6 = 0; var6 < var5; var6++) {
         LivingEntity var7 = (LivingEntity)var3.get(var6);
         boolean var8 = this.field01007 != -1 ? var7.getId() == this.field01007 : var6 == 0;
         Class1293 var9 = this.method02005(var7, var2, var8);
         if (var9 != null) {
            if (var9.method00452()) {
               this.field01007 = var9.method00004();
               this.field00170 = var9.method00113();
               return var9;
            }

            if (var4 == null) {
               var4 = var9;
            }
         }
      }

      if (var4 == null) {
         this.field00170 = null;
         return null;
      } else {
         this.field01007 = var4.method00004();
         this.field00170 = var4.method00113();
         return var4;
      }
   }

   private List<LivingEntity> method03004(Class0465 var1) {
      Vec3d var2 = field00117.player.getEyePos();
      Vec3d var3 = field00117.player.getRotationVec(1.0F).normalize();
      double var4 = MathHelper.clamp(this.field01607.method04086(), 1.0F, 180.0F) * 0.5;
      TargetManager var6 = Rockstar.method00215().method00222();
      ArrayList<Class1294> var7 = new ArrayList();

      for (Entity var9 : field00117.world.getEntities()) {
         if (var9 instanceof LivingEntity var10 && var1.method01960(var9)) {
            boolean var11 = var10.getId() == this.field01007;
            double var12 = this.method02400(var2, var3, var10);
            if (!(var12 > (var11 ? var4 + 12.0 : var4))) {
               double var14 = var12 + var2.distanceTo(var10.getBoundingBox().getCenter()) * 0.05;
               if (var11) {
                  var14 -= 8.0;
               }

               if (var6 != null && var6.method01267(var10.getName().getString())) {
                  var14 -= 1000.0;
               }

               var7.add(new Class1294(var10, var14));
            }
         }
      }

      var7.sort(Comparator.comparingDouble(Class1294::method00002));
      ArrayList var16 = new ArrayList(var7.size());

      for (Class1294 var18 : (Iterable<Class1294>)(Iterable<?>) (var7)) {
         var16.add(var18.method00088());
      }

      return var16;
   }

   private double method02400(Vec3d var1, Vec3d var2, LivingEntity var3) {
      Vec3d var4 = var3.getBoundingBox().getCenter().subtract(var1);
      double var5 = var4.length();
      if (var5 < 1.0E-4) {
         return 0.0;
      }

      double var7 = MathHelper.clamp(var2.dotProduct(var4.multiply(1.0 / var5)), -1.0, 1.0);
      double var9 = Math.toDegrees(Math.acos(var7));
      double var11 = Math.toDegrees(Math.atan2(Math.max(var3.getWidth() * 0.5, var3.getHeight() * 0.35), var5));
      return Math.max(0.0, var9 - var11);
   }

   private Class1293 method02005(LivingEntity var1, float var2, boolean var3) {
      Vec3d var4 = field00117.player.getEyePos();
      Vec3d var5 = this.field00318.method04473() ? this.method02013(var1, var4, var2, var3).subtract(var1.getPos()) : Vec3d.ZERO;
      Box var6 = var1.getBoundingBox().offset(var5.x, var5.y, var5.z);
      Class1271 var7 = null;
      Class1271 var8 = null;
      double var9 = Double.MAX_VALUE;
      double var11 = Double.MAX_VALUE;
      List var13 = this.method02009(var1, var5);

      for (int var14 = 0; var14 < var13.size(); var14++) {
         Class1271 var15 = this.method02394(var4, (Vec3d)var13.get(var14), var2);
         if (var15 != null) {
            double var16 = this.method03407(var15) + var14 * 0.75;
            if (var16 < var11) {
               var11 = var16;
               var8 = var15;
            }

            if (var16 < var9 && this.method02408(var4, var15, var2, var1, var5)) {
               var9 = var16;
               var7 = var15;
            }
         }
      }

      if (var7 != null) {
         return new Class1293(var7, var6, true, var1.getId());
      } else {
         return var8 != null ? new Class1293(var8, var6, false, var1.getId()) : null;
      }
   }

   private Vec3d method02013(LivingEntity var1, Vec3d var2, float var3, boolean var4) {
      Vec3d var5 = var4 ? this.method01992(var1) : new Vec3d(var1.getX() - var1.prevX, var1.getY() - var1.prevY, var1.getZ() - var1.prevZ);
      Vec3d var6 = var1.getPos();
      boolean var7 = !var1.isOnGround() && !var1.isClimbing() && !var1.isTouchingWater() && !var1.hasNoGravity();
      double var8 = var4 ? this.method00002() : 0.9;
      Vec3d var10 = new Vec3d(var5.x * var8, 0.0, var5.z * var8);
      double var11 = var5.y;
      double var13 = var2.distanceTo(var6) / var3;
      Vec3d var15 = var6;

      for (int var16 = 0; var16 < 6; var16++) {
         Vec3d var17 = this.method02014(var1, var6, var10, var13);
         double var18 = this.method00626(var11, var13, var7);
         var15 = var6.add(var17.x, var18, var17.z);
         double var20 = this.method02390(var2, var15, var3);
         if (!Double.isNaN(var20) && !(var20 < 0.0)) {
            if (Math.abs(var20 - var13) < 0.05) {
               break;
            }

            var13 = var20;
         } else {
            var13 = var2.distanceTo(var15) / var3;
         }
      }

      return var15;
   }

   private double method00002() {
      if (this.field01735) {
         return 0.9;
      }

      int var1 = this.field00069.size();
      if (var1 < 3) {
         return 0.9;
      }

      Vec3d[] var2 = this.field00069.toArray(new Vec3d[0]);
      double var3 = 0.0;

      for (int var5 = 1; var5 < var1; var5++) {
         double var6 = var2[var5].x - var2[var5 - 1].x;
         double var8 = var2[var5].z - var2[var5 - 1].z;
         var3 += Math.sqrt(var6 * var6 + var8 * var8);
      }

      double var11 = var2[var1 - 1].x - var2[0].x;
      double var7 = var2[var1 - 1].z - var2[0].z;
      double var9 = Math.sqrt(var11 * var11 + var7 * var7);
      return var3 < 0.05 ? 1.0 : MathHelper.clamp(var9 / var3, 0.2, 1.0);
   }

   private double method00626(double var1, double var3, boolean var5) {
      if (!var5) {
         return var1 * var3;
      }

      int var6 = (int)Math.floor(var3);
      double var7 = var3 - var6;
      double var9 = var1;
      double var11 = 0.0;

      for (int var13 = 0; var13 < var6; var13++) {
         var9 -= 0.08F;
         var11 += var9;
         var9 *= 0.98F;
      }

      if (var7 > 0.001) {
         double var16 = var9 - 0.08F;
         var11 += var16 * var7;
      }

      return var11;
   }

   private Vec3d method02014(LivingEntity var1, Vec3d var2, Vec3d var3, double var4) {
      double var6 = Math.sqrt(var3.x * var3.x + var3.z * var3.z) * var4;
      if (var6 < 0.05) {
         return new Vec3d(var3.x * var4, 0.0, var3.z * var4);
      }

      Vec3d var8 = var2.add(0.0, var1.getHeight() * 0.5, 0.0);
      Vec3d var9 = var8.add(var3.x * var4, 0.0, var3.z * var4);
      BlockHitResult var10 = field00117.world.raycast(new RaycastContext(var8, var9, ShapeType.COLLIDER, FluidHandling.NONE, var1));
      if (var10.getType() == Type.BLOCK) {
         double var11 = var10.getPos().distanceTo(var8) - 0.3;
         if (var11 <= 0.0) {
            return Vec3d.ZERO;
         }

         double var13 = var11 / var6;
         return new Vec3d(var3.x * var4 * var13, 0.0, var3.z * var4 * var13);
      } else {
         return new Vec3d(var3.x * var4, 0.0, var3.z * var4);
      }
   }

   private Vec3d method01992(LivingEntity var1) {
      if (this.field01959 != var1.getId()) {
         this.field00069.clear();
         this.field01735 = false;
         this.field01959 = var1.getId();
      }

      this.field00069.addLast(var1.getPos());

      while (this.field00069.size() > 6) {
         this.field00069.removeFirst();
      }

      if (this.field00069.size() < 2) {
         this.field01735 = false;
         return new Vec3d(var1.getX() - var1.prevX, var1.getY() - var1.prevY, var1.getZ() - var1.prevZ);
      }

      Vec3d[] var2 = this.field00069.toArray(new Vec3d[0]);
      int var3 = var2.length;
      Vec3d var4 = this.method03607(var2);
      Vec3d var5 = var2[var3 - 1].subtract(var2[var3 - 2]);
      double var6 = var5.x * var5.x + var5.z * var5.z;
      double var8 = var4.x * var4.x + var4.z * var4.z;
      if (var6 > 0.005 && var8 > 0.0025) {
         double var10 = (var5.x * var4.x + var5.z * var4.z) / (Math.sqrt(var6) * Math.sqrt(var8));
         if (var10 < 0.3) {
            this.field01735 = true;
            return var5;
         }
      }

      this.field01735 = false;
      return var4;
   }

   private Vec3d method03607(Vec3d[] var1) {
      int var2 = var1.length;
      double var3 = 0.0;
      double var5 = 0.0;
      double var7 = 0.0;
      double var9 = 0.0;

      for (int var11 = 1; var11 < var2; var11++) {
         double var12 = var11;
         var3 += (var1[var11].x - var1[var11 - 1].x) * var12;
         var5 += (var1[var11].y - var1[var11 - 1].y) * var12;
         var7 += (var1[var11].z - var1[var11 - 1].z) * var12;
         var9 += var12;
      }

      if (var9 < 1.0E-6) {
         return Vec3d.ZERO;
      }

      double var14 = 1.0 / var9;
      return new Vec3d(var3 * var14, var5 * var14, var7 * var14);
   }

   private double method02390(Vec3d var1, Vec3d var2, double var3) {
      double var5 = var2.x - var1.x;
      double var7 = var2.z - var1.z;
      double var9 = var2.y - var1.y;
      double var11 = Math.sqrt(var5 * var5 + var7 * var7);
      if (var11 < 1.0E-4) {
         return Double.NaN;
      }

      double var13 = var3 * var3;
      double var15 = 0.05F;
      double var17 = var13 * var13 - var15 * (var15 * var11 * var11 + 2.0 * var9 * var13);
      if (var17 < 0.0) {
         return var11 / var3;
      }

      double var19 = (var13 - Math.sqrt(var17)) / (var15 * var11);
      double var21 = 1.0 / Math.sqrt(1.0 + var19 * var19);
      double var23 = var3 * var21;
      if (var23 < 1.0E-4) {
         return var11 / var3;
      }

      double var25 = 0.00999999F;
      double var27 = var11 * var25 / var23;
      return var27 >= 0.999 ? var11 / var23 * 1.5 : Math.log(1.0 - var27) / Math.log(0.99F);
   }

   private Class1271 method02394(Vec3d var1, Vec3d var2, float var3) {
      double var4 = var2.x - var1.x;
      double var6 = var2.z - var1.z;
      double var8 = var2.y - var1.y;
      double var10 = Math.sqrt(var4 * var4 + var6 * var6);
      if (var10 < 1.0E-4) {
         return null;
      }

      double var12 = var3 * var3;
      double var14 = 0.05F;
      double var16 = var12 * var12 - var14 * (var14 * var10 * var10 + 2.0 * var8 * var12);
      if (var16 < 0.0) {
         return null;
      }

      double var18 = (var12 - Math.sqrt(var16)) / (var14 * var10);
      float var20 = (float)(Math.toDegrees(Math.atan2(var6, var4)) - 90.0);
      float var21 = (float)(-Math.toDegrees(Math.atan(var18)));
      var21 = MathHelper.clamp(var21, -89.9F, 89.9F);
      return new Class1271(var20, var21);
   }

   private boolean method02408(Vec3d var1, Class1271 var2, float var3, LivingEntity var4, Vec3d var5) {
      double var6 = Math.toRadians(var2.method00003());
      double var8 = Math.toRadians(var2.method04372());
      double var10 = Math.cos(var8);
      Vec3d var12 = new Vec3d(-Math.sin(var6) * var10, -Math.sin(var8), Math.cos(var6) * var10).multiply(var3);
      Vec3d var13 = var1;
      Box var14 = var4.getBoundingBox().offset(var5.x, var5.y, var5.z).expand(0.1);
      int var15 = MathHelper.clamp((int)(var1.distanceTo(var14.getCenter()) / var3 * 3.0) + 10, 20, 80);
      double var16 = var14.minY - 5.0;

      for (int var18 = 0; var18 < var15; var18++) {
         Vec3d var19 = var13.add(var12);
         if (var19.y < var16 && var12.y < 0.0) {
            return false;
         }

         Optional var20 = var14.raycast(var13, var19);
         BlockHitResult var21 = field00117.world.raycast(new RaycastContext(var13, var19, ShapeType.COLLIDER, FluidHandling.NONE, field00117.player));
         if (var20.isPresent()) {
            if (var21.getType() == Type.BLOCK) {
               double var22 = var21.getPos().squaredDistanceTo(var13);
               double var24 = ((Vec3d)var20.get()).squaredDistanceTo(var13);
               if (var22 < var24) {
                  return false;
               }
            }

            return true;
         }

         if (var21.getType() == Type.BLOCK) {
            return false;
         }

         var13 = var19;
         var12 = var12.multiply(0.99F);
         var12 = new Vec3d(var12.x, var12.y - 0.05F, var12.z);
      }

      return false;
   }

   private List<Vec3d> method02009(LivingEntity var1, Vec3d var2) {
      Box var3 = var1.getBoundingBox().offset(var2.x, var2.y, var2.z);
      Vec3d var4 = var3.getCenter();
      double var5 = var1.getY() + var2.y;
      double var7 = var1.getHeight();
      ArrayList var9 = new ArrayList(8);
      var9.add(new Vec3d(var4.x, var5 + var7 * 0.85, var4.z));
      var9.add(new Vec3d(var4.x, var5 + var7 * 0.65, var4.z));
      var9.add(new Vec3d(var4.x, var5 + var7 * 0.5, var4.z));
      var9.add(new Vec3d(var4.x, var5 + var7 * 0.3, var4.z));
      var9.add(new Vec3d(var3.minX + 0.1, var5 + var7 * 0.55, var4.z));
      var9.add(new Vec3d(var3.maxX - 0.1, var5 + var7 * 0.55, var4.z));
      var9.add(new Vec3d(var4.x, var5 + var7 * 0.55, var3.minZ + 0.1));
      var9.add(new Vec3d(var4.x, var5 + var7 * 0.55, var3.maxZ - 0.1));
      return var9;
   }

   private float method03407(Class1271 var1) {
      double var2 = Math.toRadians(var1.method00003());
      double var4 = Math.toRadians(var1.method04372());
      double var6 = Math.cos(var4);
      Vec3d var8 = new Vec3d(-Math.sin(var2) * var6, -Math.sin(var4), Math.cos(var2) * var6);
      Vec3d var9 = field00117.player.getRotationVec(1.0F).normalize();
      double var10 = MathHelper.clamp(var9.dotProduct(var8), -1.0, 1.0);
      return (float)Math.toDegrees(Math.acos(var10));
   }

   private float method00003() {
      Item var1 = field00117.player.getMainHandStack().getItem();
      if (var1 == Items.BOW) {
         return 3.0F;
      } else if (var1 == Items.CROSSBOW) {
         return 3.15F;
      } else {
         return var1 == Items.TRIDENT ? 2.5F : 3.0F;
      }
   }

   private void method03412(Class1271 var1) {
      float var2 = MathHelper.clamp(var1.method04372(), -89.9F, 89.9F);
      if (this.field00893.method04473()) {
         float var3 = Rockstar.method00215().method00392().method03673().method00003();
         float var4 = var3 + MathHelper.wrapDegrees(var1.method00003() - var3);
         Rockstar.method00215().method00392().method03419(new Class1271(var4, var2), Class1268.field01704, 120.0F, 120.0F, 120.0F, Class1277.field00971);
         this.field01706 = null;
      } else {
         this.field01706 = new Class1271(var1.method00003(), var2);
      }
   }

   private boolean method04272() {
      ItemStack var1 = field00117.player.getMainHandStack();
      Item var2 = var1.getItem();
      if (var2 == Items.BOW && this.field00332.isSelected()) {
         return field00117.player.isUsingItem();
      } else if (var2 == Items.CROSSBOW && this.field01604.isSelected()) {
         return field00117.player.isUsingItem() || CrossbowItem.isCharged(var1);
      } else {
         return var2 == Items.TRIDENT && this.field00901.isSelected() ? field00117.player.isUsingItem() : false;
      }
   }
}
