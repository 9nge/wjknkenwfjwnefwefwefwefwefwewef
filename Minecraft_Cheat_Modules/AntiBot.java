package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.core.event.EventListener;
import rockstar.feature.modules.Module;
import rockstar.feature.settings.impl.BooleanSetting;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import moscow.rockstar.mixin.accessors.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import pyrock.events.game.WorldChangeEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028 = "Anti Bot", method04432 = Category.field00395, method03909 = "modules.descriptions.anti_bot")
public class AntiBot extends Module {
   public static List<Entity> field00076 = new ArrayList<>();
   private BooleanSetting field00318;
   private final Map<Integer, PlayerEntity> field00078 = new HashMap<>();
   private final Set<Integer> field00081 = new HashSet<>();
   private final EventListener<WorldChangeEvent> field00346 = var1 -> this.method05167();
   // $VF: synthetic field
   static final boolean field00688 = !AntiBot.class.desiredAssertionStatus();

   public AntiBot() {
      this.method04271();
   }

   @Compile(obfuscation = 4)
   private void method04271() {
      this.field00318 = new BooleanSetting(this, "modules.settings.anti_bot.remove_from_world").method00203();
   }

   @Override
   public void method03678() {
      if (field00117.world != null && field00117.player != null) {
         this.method04334();

         for (PlayerEntity var2 : (Iterable<PlayerEntity>)(Iterable<?>) (new ArrayList(field00117.world.getPlayers()))) {
            if (field00117.player != var2 && !(var2 instanceof Class0827)) {
               boolean var3 = this.method04769(var2);
               boolean var4 = this.method02036(var2).method00452();
               boolean var5 = this.method02038(var2);
               if (!var3 && !var4 && !var5) {
                  field00076.remove(var2);
               } else {
                  if (!field00076.contains(var2)) {
                     field00076.add(var2);
                  }

                  if (this.field00318.method04473() && !this.field00078.containsKey(var2.getId())) {
                     this.method02047(var2, var5);
                  }
               }
            }
         }
      }
   }

   private void method02047(PlayerEntity var1, boolean var2) {
      this.field00078.put(var1.getId(), var1);
      if (var2) {
         this.field00081.add(var1.getId());
      }

      assert field00117.world != null;
      field00117.world.removeEntity(var1.getId(), RemovalReason.DISCARDED);
   }

   private void method04334() {
      Iterator var1 = this.field00081.iterator();

      while (var1.hasNext()) {
         int var2 = (Integer)var1.next();
         PlayerEntity var3 = this.field00078.get(var2);
         if (var3 == null) {
            var1.remove();
         } else if (!(Class0826.method02031(var3) <= 0.0F)) {
            ((EntityAccessor)(Object)var3).invokeUnsetRemoved();
            field00117.world.addEntity(var3);
            field00076.remove(var3);
            this.field00078.remove(var2);
            var1.remove();
         }
      }
   }

   private boolean method02038(PlayerEntity var1) {
      return var1 == null ? false : !(Class0826.method02031(var1) > 0.0F);
   }

   private boolean method04769(PlayerEntity var1) {
      if (var1 == null) {
         return false;
      }

      String var2 = var1.getName().getString();
      UUID var3 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + var2).getBytes(StandardCharsets.UTF_8));
      boolean var4 = !var1.getUuid().equals(var3);
      boolean var5 = !var2.contains("NPC") && !var2.startsWith("[ZNPC]");
      return var4 && var5;
   }

   private Class1304 method02036(PlayerEntity var1) {
      String var2 = var1.getName().getString();
      String var3 = var1.getDisplayName().getString();
      int var4 = var3.indexOf(var2);
      String var5 = var4 > 0 ? var3.substring(0, var4).trim() : "";
      return new Class1304(var3, var5, var5.isBlank());
   }

   public static boolean method01995(LivingEntity var0) {
      return var0 instanceof PlayerEntity && field00076.contains(var0);
   }

   @Override
   public void method05256() {
      this.method05167();
      super.method05256();
   }

   private void method05167() {
      field00076.clear();
      this.field00078.clear();
      this.field00081.clear();
   }
}
