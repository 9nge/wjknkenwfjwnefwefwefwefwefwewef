package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BindSetting;
import catlavan.config.BooleanSetting;
import catlavan.event.Event;
import catlavan.event.RenderEventData;
import catlavan.font.CustomFontRenderer;
import catlavan.font.FontManager;
import catlavan.module.Module;
import catlavan.network.PacketEntry;
import catlavan.render.RenderUtil;
import catlavan.util.ChatMessageUtil;
import catlavan.util.IntValuePredicate;
import catlavan.util.InventoryUtil;
import catlavan.util.McContextHolder;
import catlavan.util.WorldToScreenUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.opengl.GL11;
import sg.Effect;
import sg.SgClass120;
import sg.Packet;
import sg.SgClass138;
import sg.SgClass148;
import sg.PlayerEntity;
import sg.ClickType;
import sg.Entity;
import sg.TextFormatting;
import sg.SgClass430;
import sg.ItemStack;
import sg.Items;
import sg.SgEnum030;
import sg.Hand;
import sg.Item;
import sg.BlockPos;
import sg.HeldItemChangeC2SPacket;
import sg.PlayerTryUseItemC2SPacket;
import sg.SgClass596;
import sg.MinecraftClient;

public class ItemTimerModule extends Module implements McContextHolder {
   String effectSpeedName;
   String effectSlownessName;
   String effectNauseaName;
   String effectFatigueName;
   String effectStrengthName;
   String effectInstantHealName;
   String effectInstantDamageName;
   String effectJumpBoostName;
   String effectNauseaName2;
   String effectRegenerationName;
   String effectResistanceName;
   String effectFireResistanceName;
   String effectWaterBreathingName;
   String effectInvisibilityName;
   String effectBlindnessName;
   String effectNightVisionName;
   String effectHungerName;
   String effectWeaknessName;
   String effectPoisonName;
   String effectWitherName;
   String effectHealthBoostName;
   String effectAbsorptionName;
   String effectSaturationName;
   String effectGlowingName;
   String effectLevitationName;
   String effectLuckName;
   String effectBadLuckName;
   String effectSlowFallingName;
   String effectConduitPowerName;
   String effectDolphinGraceName;
   String effectBadOmenName;
   String effectHeroOfVillageName;
   String effectUnknownName;
   MinecraftClient mc;
   String btnObviousDustLabel;
   String btnObviousDustTooltip;
   static BindSetting sugarButton;
   String btnGodsAuraLabel;
   String btnGodsAuraTooltip;
   static BindSetting phantomMembraneButton;
   String btnShulkerLabel;
   String btnShulkerTooltip;
   static BindSetting shulkerButton;
   String btnDisorientationLabel;
   String btnDisorientationTooltip;
   static BindSetting enderEyeButton;
   String btnPlastLabel;
   String btnPlastTooltip;
   static BindSetting driedKelpButton;
   String btnTrapLabel;
   String btnTrapTooltip;
   static BindSetting netheriteScrapButton;
   String itemTimerToggleLabel;
   String itemTimerToggleTooltip;
   static BooleanSetting itemTimerToggle;
   String effectMsgToggleLabel;
   String effectMsgToggleTooltip;
   static BooleanSetting effectMsgToggle;
   boolean initialized;
   CopyOnWriteArrayList activeTimers;
   Map field_tilMap = new HashMap();
   Timer effectTimer = new Timer();
   String actionDisorientation;
   String actionPlast;
   String actionTrap;
   String actionObviousDust;
   String actionGodsAura;
   static String soundPistonExtend = "block.piston.extend";
   String trapLabel;
   static String soundAnvilPlace = "block.anvil.place";
   String plastLabel;
   long effectScheduleDelay;
   float timerYOffset;
   String trapTypeKey;
   float trapOffsetX;
   float trapOffsetZ;
   String plastTypeKey;
   float timerMillisPerSecond;
   float timerMillisPerSecondAlt;
   float labelPaddingLeft;
   float labelPaddingRight;
   float boxHeight;
   float iconOffsetX;
   float iconOffsetY;
   float iconScale;
   float boxRounding;
   float textOffsetX;
   float textOffsetY;

   private String method9114(int i) {
      switch (i) {
         case 1:
            return effectSpeedName;
         case 2:
            return effectSlownessName;
         case 3:
            return effectNauseaName;
         case 4:
            return effectFatigueName;
         case 5:
            return effectStrengthName;
         case 6:
            return effectInstantHealName;
         case 7:
            return effectInstantDamageName;
         case SgClass057:
            return effectJumpBoostName;
         case 9:
            return effectNauseaName2;
         case 10:
            return effectRegenerationName;
         case 11:
            return effectResistanceName;
         case 12:
            return effectFireResistanceName;
         case 13:
            return effectWaterBreathingName;
         case 14:
            return effectInvisibilityName;
         case 15:
            return effectBlindnessName;
         case 16:
            return effectNightVisionName;
         case 17:
            return effectHungerName;
         case 18:
            return effectWeaknessName;
         case SgClass006:
            return effectPoisonName;
         case SgClass017:
            return effectWitherName;
         case 21:
            return effectHealthBoostName;
         case 22:
            return effectAbsorptionName;
         case 23:
            return effectSaturationName;
         case 24:
            return effectGlowingName;
         case 25:
            return effectLevitationName;
         case SgClass018:
            return effectLuckName;
         case 27:
            return effectBadLuckName;
         case 28:
            return effectSlowFallingName;
         case 29:
            return effectConduitPowerName;
         case 30:
            return effectDolphinGraceName;
         case 31:
            return effectBadOmenName;
         case SgClass025:
            return effectHeroOfVillageName;
         default:
            return effectUnknownName;
      }
   }

   public static void pickAndUseItem(int i, String s) {
      if (InventoryUtil.findEmptyHotbarSlot() != -1) {
         mc.playerController.pickItem(i);
         method1477(s);
      } else if (InventoryUtil.findEmptyHotbarSlot() == -1) {
         mc.playerController.pickItem(i);
         method1477(s);
         mc.playerController.pickItem(i);
      }
   }

   public static void initStatic() {
      sugarButton = new BindSetting(btnObviousDustLabel, 0, btnObviousDustTooltip, Items.SUGAR);
      phantomMembraneButton = new BindSetting(btnGodsAuraLabel, 0, btnGodsAuraTooltip, Items.PHANTOM_MEMBRANE);
      shulkerButton = new BindSetting(btnShulkerLabel, 0, btnShulkerTooltip, Items.SHULKER_BOX);
      enderEyeButton = new BindSetting(btnDisorientationLabel, 0, btnDisorientationTooltip, Items.ENDER_EYE);
      driedKelpButton = new BindSetting(btnPlastLabel, 0, btnPlastTooltip, Items.DRIED_KELP);
      netheriteScrapButton = new BindSetting(btnTrapLabel, 0, btnTrapTooltip, Items.NETHERITE_SCRAP);
      itemTimerToggle = new BooleanSetting(itemTimerToggleLabel, true, itemTimerToggleTooltip);
      effectMsgToggle = new BooleanSetting(effectMsgToggleLabel, true, effectMsgToggleTooltip);
      initialized = false;
      activeTimers = new CopyOnWriteArrayList();
   }

   public ItemTimerModule() {
      this.addSettings(
         new AbstractModuleSetting[]{
            sugarButton, phantomMembraneButton, shulkerButton, enderEyeButton, driedKelpButton, netheriteScrapButton, itemTimerToggle, effectMsgToggle
         }
      );
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof IntValuePredicate) {
         IntValuePredicate intvaluepredicate = (IntValuePredicate)event;
         if (intvaluepredicate.value == enderEyeButton.getKeyCode()) {
            useItemByType(Items.ENDER_EYE, actionDisorientation);
         }

         if (intvaluepredicate.value == driedKelpButton.getKeyCode()) {
            useItemByType(Items.DRIED_KELP, actionPlast);
         }

         if (intvaluepredicate.value == netheriteScrapButton.getKeyCode()) {
            useItemByType(Items.NETHERITE_SCRAP, actionTrap);
         }

         if (intvaluepredicate.value == sugarButton.getKeyCode()) {
            useItemByType(Items.SUGAR, actionObviousDust);
         }

         if (intvaluepredicate.value == phantomMembraneButton.getKeyCode()) {
            useItemByType(Items.PHANTOM_MEMBRANE, actionGodsAura);
         }

         if (intvaluepredicate.value == shulkerButton.getKeyCode()) {
            for (int i = 0; i < 36; i++) {
               if (mc.player.inventory.getStackInSlot(i).getItem() == Items.SHULKER_BOX) {
                  int j = i;
                  if (i < 9 && i != -1) {
                     j = i + 36;
                  }

                  mc.playerController.windowClick(0, j, 1, ClickType.PICKUP, mc.player);
                  break;
               }
            }
         }
      }

      if (event instanceof PacketEntry) {
         PacketEntry packetentry = (PacketEntry)event;
         Packet вяx = packetentry.getPacket();
         if (вяx instanceof SgClass138) {
            SgClass138 soundPacket = (SgClass138)вяx;
            float f2 = (float)((SgClass138)packetentry.getPacket()).getX();
            float f = (float)((SgClass138)packetentry.getPacket()).getY();
            float f1 = (float)((SgClass138)packetentry.getPacket()).getZ();
            if (soundPacket.getSound().getName().getPath().equals(soundPistonExtend)) {
               activeTimers.add(new SgEnum030(15000, f2, f, f1, trapLabel, Items.NETHERITE_SCRAP));
            } else if (soundPacket.getSound().getName().getPath().equals(soundAnvilPlace)) {
               activeTimers.add(new SgEnum030(20000, f2, f, f1, plastLabel, Items.DRIED_KELP));
            }
         }
      }

      if (event instanceof PacketEntry) {
         PacketEntry packetentry1 = (PacketEntry)event;
         if (effectMsgToggle.getValue()) {
            Packet packet = packetentry1.getPacket();
            if (packet instanceof SgClass120) {
               SgClass120 statusEffectPacket = (SgClass120)packet;
               Entity entity = mc.world.getEntityByID(statusEffectPacket.getEntityId());
               if (entity instanceof PlayerEntity) {
                  PlayerEntity м3 = (PlayerEntity)entity;
                  Effect lэ = Effect.get(statusEffectPacket.getEffectId());
                  int k = statusEffectPacket.getAmplifier() + 1;
                  int l = statusEffectPacket.getDuration() / SgClass017;
                  int i1 = statusEffectPacket.getDuration();
                  int j1 = i1 / SgClass017;
                  int k1 = j1 / 60;
                  int l1 = j1 % 60;
                  String s = TextFormatting.RED + this.method9114(statusEffectPacket.getEffectId()) + TextFormatting.GRAY + k + k1 + l1;
                  this.field_tilMap.computeIfAbsent(statusEffectPacket.getEntityId(), integer -> new ArrayList<>()).add(s);
                  this.effectTimer.schedule(new SgClass148(this, statusEffectPacket, м3), effectScheduleDelay);
               }
            }
         }
      }
   }

   public static void renderTimers(RenderEventData rendereventdata) {
      if (rendereventdata.is2D() && !activeTimers.isEmpty()) {
         for (SgEnum030 SgEnum030 : activeTimers) {
            if (System.currentTimeMillis() - SgEnum030.л > SgEnum030.фе) {
               activeTimers.remove(SgEnum030);
            }

            float f = SgEnum030.к西;
            float f1 = SgEnum030.кц + timerYOffset;
            float f2 = SgEnum030.SgClass430;
            if (SgEnum030.ЪВ == trapTypeKey) {
               f += trapOffsetX;
               f2 += trapOffsetZ;
            } else if (SgEnum030.ЪВ == plastTypeKey) {
            }

            SgClass596 SgClass596 = WorldToScreenUtil.worldToScreen(f, f1, f2);
            float f3 = (float)(SgEnum030.фе - (System.currentTimeMillis() - SgEnum030.л));
            int i = (int)(f3 / timerMillisPerSecond);
            String s = String.valueOf(i);
            int j = s.length();
            String s1 = String.valueOf(f3 / timerMillisPerSecondAlt);
            String s2 = s1.substring(0, Math.min(s1.length(), j));
            new BlockPos((double)SgEnum030.к西, (double)SgEnum030.кц, (double)SgEnum030.SgClass430);
            if (SgClass596 != null) {
               GL11.glPushMatrix();
               CustomFontRenderer customfontrenderer = FontManager.fontsSize18[17];
               String s3 = SgEnum030.ЪВ + TextFormatting.RED + s2;
               float f4 = customfontrenderer.measureStringWidth(s3) + labelPaddingLeft + labelPaddingRight;
               float f5 = boxHeight;
               float f6 = SgClass596.x - f4 / 2.0F;
               float f7 = SgClass596.y;
               int k = new Color(SgClass057, SgClass057, SgClass057, 168).getRGB();
               ItemStack itemStack = new ItemStack(SgEnum030.я);
               SgClass430.SgClass200(itemStack, f6 + iconOffsetX, f7 + iconOffsetY, false, true, iconScale);
               RenderUtil.drawRoundedRectWithRadius(f6, f7, f4, f5, boxRounding, k, 1.0F);
               int l = new Color(255, 255, 255, 255).getRGB();
               customfontrenderer.drawString(rendereventdata.renderType, s3, f6 + textOffsetX, f7 + f5 / 2.0F - textOffsetY, l);
               GL11.glPopMatrix();
            }
         }
      }
   }

   public static void method1477(String object) {
      mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.MAIN_HAND));
      ChatMessageUtil.sendPrefixedMessage(TextFormatting.GRAY + object);
   }

   public static void useItemByType(Item Item, String object) {
      boolean flag = false;

      for (int i = 0; i < 36; i++) {
         if (mc.player.inventory.getStackInSlot(i).getItem() == Item) {
            if (mc.player.getCooldownTracker().hasCooldown(Item)) {
               ChatMessageUtil.sendPrefixedMessage(TextFormatting.RED + object + TextFormatting.RED);
               return;
            }

            if (i >= 9) {
               pickAndUseItem(i, (String)object);
            } else {
               if (i <= SgClass057) {
                  if (i != mc.player.inventory.currentItem) {
                     mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(i));
                  }

                  method1477((String)object);
                  if (i != mc.player.inventory.currentItem) {
                     mc.player.connection.sendPacket(new HeldItemChangeC2SPacket(mc.player.inventory.currentItem));
                  }
               }

               flag = true;
            }
            break;
         }
      }
   }

   static {
      Ещ = "\u0001\u0001\u0001 \u0001 [\u0001m \u0001s]";
      Ем = "\u0001 \u0001\u0001с";
      ЕЭ = "\u0001Предмет ''\u0001'' находится на \u0001перезарядке";
      Ег = "\u0001Предмет: \u0001 был успешно использован!";
   }
}
