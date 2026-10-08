package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.ModeSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PacketEntry;
import catlavan.network.PlayerMovePacket;
import catlavan.util.McContextHolder;
import sg.SgClass034;
import sg.Packet;
import sg.ClientTickEvent;
import sg.MinecraftClient;

public class VelocityBypassModule extends Module implements McContextHolder {
   String nameRu;
   String nameEn;
   String modeReallyWorld;
   String modeHolyWorld;
   String modeFunTime;
   String modeLabelBypass;
   String defaultMode;
   static ModeSetting field_Lsg;
   static String modeMatrix = "Matrix (Vanilla)";
   static MinecraftClient mc;
   static String modeReallyWorld2 = "ReallyWorld";
   static String modeHolyWorld2 = "HolyWorld";
   static String modeFunTime2 = "FunTime";
   boolean wasKnocked = false;
   long knockTime;
   boolean wasHurt = false;
   long knockbackDuration;

   public static void initModeEnum() {
      field_Lsg = new ModeSetting(nameRu, nameEn, modeReallyWorld, modeHolyWorld, modeFunTime, modeLabelBypass, defaultMode);
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof PacketEntry) {
         PacketEntry packetentry = (PacketEntry)event;
         if (field_Lsg.is(modeMatrix)) {
            Packet вяx = packetentry.getPacket();
            if (вяx instanceof SgClass034) {
               SgClass034 44x = (SgClass034)вяx;
               if (44x.getEntityID() == mc.player.getEntityId()) {
                  packetentry.setCancelled(true);
               }
            }
         }
      }

      if (field_Lsg.is(modeReallyWorld2) || field_Lsg.is(modeHolyWorld2) || field_Lsg.is(modeFunTime2)) {
         if (event instanceof PacketEntry) {
            PacketEntry packetentry1 = (PacketEntry)event;
            if (packetentry1.isReceive()) {
               Packet packet = packetentry1.getPacket();
               if (packet instanceof SgClass034) {
                  SgClass034 SgClass034 = (SgClass034)packet;
                  if (!mc.player.isElytraFlying() && !mc.player.isInWater() && SgClass034.getEntityID() == mc.player.getEntityId()) {
                     if (mc.player.isOnGround() && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        mc.player.jump();
                     }

                     this.wasKnocked = true;
                     this.knockTime = System.currentTimeMillis();
                  }
               }
            }
         }

         if (event instanceof ClientTickEvent) {
            if (mc.player.hurtTime != 0) {
               this.wasHurt = true;
            } else {
               this.wasHurt = false;
            }
         }

         if (event instanceof PlayerMovePacket) {
            PlayerMovePacket playermovepacket = (PlayerMovePacket)event;
            if (this.wasKnocked && this.wasHurt) {
               if (System.currentTimeMillis() - this.knockTime <= knockbackDuration) {
                  playermovepacket.setYaw(1.0F);
               } else {
                  this.wasKnocked = false;
               }
            }
         }
      }
   }

   public VelocityBypassModule() {
      this.knockTime = 0L;
      this.addSettings(new AbstractModuleSetting[]{field_Lsg});
   }

   static {
      юЧ = 0L;
   }
}
