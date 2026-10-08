package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;
import rockstar.feature.settings.impl.SliderSetting;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyrock.events.player.ClientPlayerTickEvent;
import pyrock.events.render.Render3DEvent;
import pyrock.utility.render.ColorRGBA;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Back Track", method03909 = "modules.descriptions.back_track", method04432 = Category.field00395)
public class BackTrack extends Module {
   private BooleanSetting field00318;
   private BooleanSetting field01594;
   private BooleanSetting field00893;
   private BooleanSetting field01270;
   private SliderSetting field00340;
   private SliderSetting field01607;
   private SliderSetting field00904;
   private static final double field00003 = 6.0;
   private static final double field01493 = 180.0;
   private static final double field00831 = 0.6;
   private static final double field01225 = 0.4;
   private final EventListener<ClientPlayerTickEvent> field00346 = var1 -> {
      if (field00117.world != null && field00117.player != null) {
         long var2 = System.currentTimeMillis();
         boolean var4 = this.method04272();

         for (Entity var6 : field00117.world.getEntities()) {
            if (var6 instanceof Class1111 var7) {
               List var8 = var7.rockstar2_0$getBackTracks();
               if (var4) {
                  this.method01968(var6, var8, var2);
               } else if (!this.method01960(var6)) {
                  var8.clear();
               } else {
                  this.method01968(var6, var8, var2);
               }
            }
         }
      }
   };
   private final EventListener<Render3DEvent> field01611 = var1 -> {
      if (this.field00318.method04473()) {
         if (field00117.world != null && field00117.player != null) {
            if (!this.method04272()) {
               MatrixStack var2 = var1.getMatrices();
               Vec3d var3 = field00117.gameRenderer.getCamera().getPos();

               for (PlayerEntity var5 : field00117.world.getPlayers()) {
                  if (var5 != field00117.player
                     && !Rockstar.method00215().method00238().method01267(var5.getName().getString())
                     && var5 instanceof Class1111 var6) {
                     List var7 = var6.rockstar2_0$getBackTracks();
                     if (!var7.isEmpty()) {
                        long var8 = System.currentTimeMillis();
                        this.method01968(var5, var7, var8);
                        if (!var7.isEmpty()) {
                           Vec3d var10 = this.method02044(var5, var7);
                           if (var10 != null) {
                              BufferBuilder var11 = RenderSystem.renderThreadTesselator().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
                              var2.push();
                              RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
                              RenderSystem.disableCull();
                              RenderSystem.enableBlend();
                              RenderSystem.defaultBlendFunc();
                              Class1139.method04191(
                                 var2,
                                 var11,
                                 var5.getBoundingBox().offset(var10.subtract(var5.getPos())).offset(-var3.x, -var3.y, -var3.z),
                                 ColorRGBA.WHITE.withAlpha(180.0F)
                              );
                              BuiltBuffer var12 = var11.endNullable();
                              if (var12 != null) {
                                 BufferRenderer.drawWithGlobalProgram(var12);
                              }

                              RenderSystem.enableCull();
                              RenderSystem.disableBlend();
                              var2.pop();
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   };

   public BackTrack() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00318 = new BooleanSetting(this, "modules.settings.backtrack.visual");
      this.field01594 = new BooleanSetting(this, "modules.settings.backtrack.autoreset");
      this.field00893 = new BooleanSetting(this, "Ping Based");
      this.field01270 = new BooleanSetting(this, "TPS Based");
      this.field00340 = new SliderSetting(this, "Delay").method01202("ms").method00660(50.0F).method04520(1200.0F).method03699(25.0F).method04137(150.0F);
      this.field01607 = new SliderSetting(this, "Ping Multiplier", () -> !this.field00893.method04473())
         .method01202("x")
         .method00660(0.6F)
         .method04520(2.0F)
         .method03699(0.1F)
         .method04137(1.1F);
      this.field00904 = new SliderSetting(this, "Min TPS", () -> !this.field01270.method04473())
         .method01202("")
         .method00660(14.0F)
         .method04520(20.0F)
         .method03699(0.5F)
         .method04137(17.0F);
   }

   private int method03627() {
      if (field00117.getNetworkHandler() != null && field00117.player != null) {
         PlayerListEntry var1 = field00117.getNetworkHandler().getPlayerListEntry(field00117.player.getUuid());
         return var1 != null ? var1.getLatency() : 0;
      } else {
         return 0;
      }
   }

   private int method02032(PlayerEntity var1) {
      if (field00117.getNetworkHandler() == null) {
         return 0;
      }

      PlayerListEntry var2 = field00117.getNetworkHandler().getPlayerListEntry(var1.getUuid());
      return var2 != null ? var2.getLatency() : 0;
   }

   private float method00003() {
      Class0934 var1 = Rockstar.method00215().method00327();
      return var1 != null ? var1.method00003() : 20.0F;
   }

   private boolean method04272() {
      return this.field01270.method04473() && this.method00003() < this.field00904.method04086();
   }

   private long method01949(Entity var1) {
      long var2 = (long)this.field00340.method04086();
      if (this.field00893.method04473() && var1 instanceof PlayerEntity var4) {
         int var5 = this.method03627() + this.method02032(var4);
         long var6 = (long)(var5 * this.field01607.method04086());
         var2 = Math.clamp(var6, (long)this.field00340.method00003(), (long)this.field00340.method04372());
      }

      if (this.field01270.method04473()) {
         float var8 = this.method00003();
         if (var8 > 0.0F) {
            float var9 = MathHelper.clamp(20.0F / var8, 1.0F, this.field00340.method04372() / Math.max(this.field00340.method00003(), (float)var2));
            var2 = (long)((float)var2 * var9);
         }
      }

      return Math.clamp(var2, (long)this.field00340.method00003(), (long)this.field00340.method04372());
   }

   private Vec3d method02044(PlayerEntity var1, List<Class1351> var2) {
      if (var2.isEmpty()) {
         return null;
      }

      Vec3d var3 = ((Class1351)var2.getLast()).method00116();
      if (!this.field01594.method04473()) {
         return var3;
      }

      Vec3d var4 = field00117.player.getEyePos();
      Vec3d var5 = var1.getPos();
      Box var6 = var1.getBoundingBox();
      double var7 = this.method02383(var4, var6);

      for (int var9 = var2.size() - 1; var9 >= 0; var9--) {
         Class1351 var10 = (Class1351)var2.get(var9);
         Box var11 = var6.offset(var10.method00116().subtract(var5));
         double var12 = this.method02383(var4, var11);
         if (var12 < var7) {
            return var10.method00116();
         }
      }

      return null;
   }

   private double method02383(Vec3d var1, Box var2) {
      double var3 = this.method04827(var1, var2);
      double var5 = this.method03816(var1, var2);
      double var7 = Math.min(var3 / 6.0, 1.0);
      double var9 = Math.min(var5 / 180.0, 1.0);
      return var7 * 0.6 + var9 * 0.4;
   }

   private double method04827(Vec3d var1, Box var2) {
      double var3 = MathHelper.clamp(var1.x, var2.minX, var2.maxX);
      double var5 = MathHelper.clamp(var1.y, var2.minY, var2.maxY);
      double var7 = MathHelper.clamp(var1.z, var2.minZ, var2.maxZ);
      return var1.distanceTo(new Vec3d(var3, var5, var7));
   }

   private double method03816(Vec3d var1, Box var2) {
      float var3 = field00117.player.getYaw();
      float var4 = field00117.player.getPitch();
      Vec3d var5 = this.method00689(var4, var3);
      Vec3d var6 = this.method02384(var1, var2);
      Vec3d var7 = var6.subtract(var1).normalize();
      double var8 = var5.dotProduct(var7);
      var8 = MathHelper.clamp(var8, -1.0, 1.0);
      return Math.toDegrees(Math.acos(var8));
   }

   private Vec3d method02384(Vec3d var1, Box var2) {
      double var3 = MathHelper.clamp(var1.x, var2.minX, var2.maxX);
      double var5 = MathHelper.clamp(var1.y, var2.minY, var2.maxY);
      double var7 = MathHelper.clamp(var1.z, var2.minZ, var2.maxZ);
      return new Vec3d(var3, var5, var7);
   }

   private Vec3d method00689(float var1, float var2) {
      float var3 = (float)Math.toRadians(var1);
      float var4 = (float)Math.toRadians(var2);
      float var5 = MathHelper.cos(-var4 - (float) Math.PI);
      float var6 = MathHelper.sin(-var4 - (float) Math.PI);
      float var7 = MathHelper.cos(-var3);
      float var8 = MathHelper.sin(-var3);
      return new Vec3d(var6 * var7, var8, var5 * var7);
   }

   public Vec3d method01955(Entity var1) {
      if (!this.method04473()) {
         return null;
      }

      if (field00117.player == null) {
         return null;
      }

      if (this.method04272()) {
         return null;
      }

      if (var1 instanceof Class1111 var2) {
         if (var1 instanceof PlayerEntity var3) {
            if (!this.method01960(var1)) {
               return null;
            }

            List var4 = var2.rockstar2_0$getBackTracks();
            if (var4.isEmpty()) {
               return null;
            }

            long var5 = System.currentTimeMillis();
            this.method01968(var1, var4, var5);
            return var4.isEmpty() ? null : this.method02044(var3, var4);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Override
   public void method05256() {
      this.method04334();
   }

   public boolean method01960(Entity var1) {
      if (!this.method04473()) {
         return false;
      }

      if (field00117.player == null) {
         return false;
      }

      if (var1 instanceof PlayerEntity var2) {
         if (var2 == field00117.player) {
            return false;
         } else {
            return Rockstar.method00215().method00238().method01267(var2.getName().getString()) ? false : !this.method04272();
         }
      } else {
         return false;
      }
   }

   public void method01979(Entity var1, Vec3d var2, long var3) {
      if (this.method01960(var1)) {
         if (var1 instanceof Class1111 var5) {
            List var6 = var5.rockstar2_0$getBackTracks();
            this.method01968(var1, var6, var3);
            var6.add(new Class1351(var2, var3));
         }
      }
   }

   public void method04757(Entity var1, Vec3d var2, long var3) {
      if (var1 instanceof Class1111 var5) {
         List var6 = var5.rockstar2_0$getBackTracks();
         var6.clear();
         if (this.method01960(var1)) {
            var6.add(new Class1351(var2, var3));
         }
      }
   }

   private void method01968(Entity var1, List<Class1351> var2, long var3) {
      long var5 = this.method01949(var1);
      var2.removeIf(var4 -> var3 - var4.method00005() > var5);
   }

   private void method04334() {
      if (field00117.world != null) {
         for (Entity var2 : field00117.world.getEntities()) {
            if (var2 instanceof Class1111 var3) {
               var3.rockstar2_0$getBackTracks().clear();
            }
         }
      }
   }
}
