package catlavan.module.combat;

import S5iauy.Xe8;
import catlavan.config.AbstractModuleSetting;
import catlavan.config.BindSetting;
import catlavan.config.ModeSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PacketEntry;
import catlavan.util.IntValuePredicate;
import catlavan.util.McContextHolder;
import catlavan.util.Timer;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import sg.Packet;
import sg.SgClass169;
import sg.Container;
import sg.ClickType;
import sg.ChestContainer;
import sg.ClientTickEvent;
import sg.SgClass594;
import sg.MinecraftClient;
import sg.SgClass609;

public class AutoDuelModule extends Module implements McContextHolder {
   static String MODE_LABEL_RU = "Режим";
   static String MODE_LABEL = "Mode";
   static String MODE_BALL = "Ball";
   static String MODE_EXTRA;
   static String BIND_OFF_KEY_LABEL_RU;
   static String MODE_4;
   static String MODE_5;
   static String MODE_6;
   static String MODE_7;
   static String MODE_8;
   static String MODE_9;
   static String MODE_10;
   ModeSetting modeSetting;
   static String BIND_OFF_KEY_LABEL_RU2 = "Клавиша отключения";
   static String BIND_OFF_KEY_LABEL = "Bind off";
   BindSetting bindOffKey;
   List field_ilList;
   Timer actionTimer;
   int currentTargetIndex;
   static Pattern usernamePattern;
   MinecraftClient mc;
   long DUEL_INTERVAL_MS;
   long ACTION_DELAY_MS;
   String GUI_TITLE_KIT_SELECT;
   long KIT_CLICK_DELAY_MS;
   String GUI_TITLE_SETTINGS;
   long SETTINGS_CLICK_DELAY_MS;
   static String DETECT_KEYWORD_START = "начало";
   String DETECT_KEYWORD_THROUGH;
   String DETECT_KEYWORD_SEC;
   String USERNAME_REGEX;

   public AutoDuelModule() {
      this.modeSetting = new ModeSetting(
         MODE_LABEL_RU, MODE_LABEL, MODE_BALL, MODE_EXTRA, BIND_OFF_KEY_LABEL_RU, MODE_4, MODE_5, MODE_6, MODE_7, MODE_8, MODE_9, MODE_10
      );
      this.bindOffKey = new BindSetting(BIND_OFF_KEY_LABEL_RU2, -1, BIND_OFF_KEY_LABEL, null);
      this.field_ilList = Lists.newArrayList();
      this.actionTimer = new Timer();
      this.currentTargetIndex = 0;
      this.addSettings(new AbstractModuleSetting[]{this.modeSetting, this.bindOffKey});
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof IntValuePredicate) {
         IntValuePredicate intvaluepredicate = (IntValuePredicate)event;
         if (this.bindOffKey.getKeyCode() == intvaluepredicate.getValue()) {
            this.toggle();
         }
      }

      if (event instanceof ClientTickEvent) {
         List list = mc.player
            .connection
            .getPlayerInfoMap()
            .stream()
            .map(SgClass169::getGameProfile)
            .<String>map(GameProfile::getName)
            .filter(s -> usernamePattern.matcher(sx).matches())
            .collect(Collectors.toList());
         if (this.actionTimer.hasElapsed(DUEL_INTERVAL_MS * list.size())) {
            this.field_ilList.clear();
            this.currentTargetIndex = 0;
            this.actionTimer.reset();
         }

         if (!list.isEmpty()) {
            if (this.actionTimer.hasElapsed(ACTION_DELAY_MS)) {
               if (this.currentTargetIndex >= list.size()) {
                  this.currentTargetIndex = 0;
               }

               String s = (String)list.get(this.currentTargetIndex);
               if (!this.field_ilList.contains(s) && !s.equals(mc.session.getProfile().getName())) {
                  mc.player.sendChatMessage(Xe8.за<"makeConcatWithConstants">(s));
                  this.field_ilList.add(s);
               }

               this.currentTargetIndex++;
               this.actionTimer.reset();
            }

            Container container = mc.player.openContainer;
            if (container instanceof ChestContainer) {
               ChestContainer chestContainer = (ChestContainer)container;
               if (mc.currentScreen.getTitle().getString().contains(GUI_TITLE_KIT_SELECT)) {
                  if (this.actionTimer.hasElapsed(KIT_CLICK_DELAY_MS)) {
                     mc.playerController.windowClick(chestContainer.windowId, SgClass594.valueOf(this.modeSetting.getValue()).ordinal(), 0, ClickType.QUICK_MOVE, mc.player);
                     this.actionTimer.reset();
                  }
               } else if (mc.currentScreen.getTitle().getString().contains(GUI_TITLE_SETTINGS) && this.actionTimer.hasElapsed(SETTINGS_CLICK_DELAY_MS)) {
                  mc.playerController.windowClick(chestContainer.windowId, 0, 0, ClickType.QUICK_MOVE, mc.player);
                  this.actionTimer.reset();
               }
            }
         }
      }

      if (event instanceof PacketEntry) {
         PacketEntry packetentry = (PacketEntry)event;
         Packet packet = packetentry.getPacket();
         if (packet instanceof SgClass609) {
            SgClass609 诶o = (SgClass609)packet;
            String s1 = 诶o.getChatComponent().getString().toLowerCase();
            if (s1.contains(DETECT_KEYWORD_START) && s1.contains(DETECT_KEYWORD_THROUGH) && s1.contains(DETECT_KEYWORD_SEC) || s1.isEmpty()) {
               this.toggle();
            }
         }
      }
   }

   public static void compileUsernamePattern() {
      usernamePattern = Pattern.compile(USERNAME_REGEX);
   }

   static {
      SgClass384 = "/duel \u0001";
   }
}
