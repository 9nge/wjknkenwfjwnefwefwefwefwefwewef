package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BindSetting;
import catlavan.config.BooleanSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.network.PacketEntry;
import catlavan.util.IntValuePredicate;
import catlavan.util.McContextHolder;
import catlavan.util.Timer;
import sg.Packet;
import sg.SgClass138;
import sg.ClickType;
import sg.ClientTickEvent;
import sg.Items;
import sg.SgEnum030;
import sg.Hand;
import sg.Item;
import sg.PlayerTryUseItemC2SPacket;
import sg.MinecraftClient;

public class ExplosionTrapModule extends Module implements McContextHolder {
   boolean pendingSwapBack;
   int swappedFromSlot;
   MinecraftClient mc;
   Item itemType;
   int savedHotbarSlot;
   String explosionTrapRuName;
   String field_String;
   static BindSetting explosionTrapButton;
   String farewellRumbleRuName;
   String farewellRumbleEnName;
   static BindSetting farewellRumbleButton;
   String snowRuName;
   String snowEnName;
   static BindSetting snowButton;
   String stunRuName;
   String stunEnName;
   static BindSetting stunButton;
   String explosiveStuffRuName;
   String explosiveStuffEnName;
   static BindSetting explosiveStuffButton;
   String trapRuName;
   String trapEnName;
   static BindSetting field_Lsg;
   String itemTimerLabel;
   String itemTimerEnName;
   static BooleanSetting itemTimerOption;
   Timer field_Lsg2;
   String explosionTrapActionName;
   String farewellRumbleActionName;
   String field_String2;
   String stunActionName;
   String snowActionName;
   String explosiveStuffActionName;
   long swapDuration;
   static String explosionSoundId = "entity.generic.explode";
   String trapActionName;

   public static void swapBack() {
      if (pendingSwapBack && swappedFromSlot != -1) {
         if (mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() == itemType) {
            mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.MAIN_HAND));
            mc.playerController.windowClick(0, swappedFromSlot, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);
            pendingSwapBack = false;
            swappedFromSlot = -1;
         }
      } else {
         if (mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() == itemType) {
            mc.player.connection.sendPacket(new PlayerTryUseItemC2SPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
         }

         mc.player.inventory.currentItem = savedHotbarSlot;
         savedHotbarSlot = -1;
      }
   }

   public static void initStatic() {
      explosionTrapButton = new BindSetting(explosionTrapRuName, 0, field_String, Items.PRISMARINE_SHARD);
      farewellRumbleButton = new BindSetting(farewellRumbleRuName, 0, farewellRumbleEnName, Items.FIREWORK_STAR);
      snowButton = new BindSetting(snowRuName, 0, snowEnName, Items.SNOWBALL);
      stunButton = new BindSetting(stunRuName, 0, stunEnName, Items.NETHER_STAR);
      explosiveStuffButton = new BindSetting(explosiveStuffRuName, 0, explosiveStuffEnName, Items.FIRE_CHARGE);
      field_Lsg = new BindSetting(trapRuName, 0, trapEnName, Items.POPPED_CHORUS_FRUIT);
      itemTimerOption = new BooleanSetting(itemTimerLabel, true, itemTimerEnName);
      field_Lsg2 = new Timer();
      savedHotbarSlot = -1;
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   public ExplosionTrapModule() {
      this.addSettings(
         new AbstractModuleSetting[]{explosionTrapButton, farewellRumbleButton, snowButton, stunButton, explosiveStuffButton, field_Lsg, itemTimerOption}
      );
   }

   public static void swapToItem(Item Item, String s) {
      if (savedHotbarSlot == -1) {
         boolean flag = false;

         for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Item) {
               savedHotbarSlot = mc.player.inventory.currentItem;
               itemType = mc.player.inventory.getStackInSlot(i).getItem();
               mc.player.inventory.currentItem = i;
               flag = true;
               field_Lsg2.reset();
               break;
            }
         }

         if (!flag) {
            for (int j = 0; j < 36; j++) {
               if (mc.player.inventory.getStackInSlot(j).getItem() == Item) {
                  mc.playerController.windowClick(0, j, mc.player.inventory.currentItem, ClickType.SWAP, mc.player);
                  pendingSwapBack = true;
                  swappedFromSlot = j;
                  field_Lsg2.reset();
                  break;
               }
            }
         }
      }
   }

   @Override
   public void onEvent(Event event) {
      if (event instanceof IntValuePredicate) {
         IntValuePredicate intvaluepredicate = (IntValuePredicate)event;
         if (intvaluepredicate.value == explosionTrapButton.getKeyCode()) {
            swapToItem(Items.PRISMARINE_SHARD, explosionTrapActionName);
         }

         if (intvaluepredicate.value == farewellRumbleButton.getKeyCode()) {
            swapToItem(Items.FIREWORK_STAR, farewellRumbleActionName);
         }

         if (intvaluepredicate.value == stunButton.getKeyCode()) {
            swapToItem(Items.NETHER_STAR, field_String2);
         }

         if (intvaluepredicate.value == snowButton.getKeyCode()) {
            swapToItem(Items.SNOWBALL, stunActionName);
         }

         if (intvaluepredicate.value == explosiveStuffButton.getKeyCode()) {
            swapToItem(Items.FIRE_CHARGE, snowActionName);
         }

         if (intvaluepredicate.value == field_Lsg.getKeyCode()) {
            swapToItem(Items.POPPED_CHORUS_FRUIT, explosiveStuffActionName);
         }
      }

      if (event instanceof ClientTickEvent && (savedHotbarSlot != -1 || pendingSwapBack)) {
         if (!field_Lsg2.hasElapsed(swapDuration)) {
            return;
         }

         swapBack();
      }

      if (event instanceof PacketEntry) {
         PacketEntry packetentry = (PacketEntry)event;
         if (itemTimerOption.getValue()) {
            Packet packet = packetentry.getPacket();
            if (packet instanceof SgClass138) {
               SgClass138 soundPacket = (SgClass138)packet;
               float f2 = (float)((SgClass138)packetentry.getPacket()).getX();
               float f = (float)((SgClass138)packetentry.getPacket()).getY();
               float f1 = (float)((SgClass138)packetentry.getPacket()).getZ();
               if (soundPacket.getSound().getName().getPath().equals(explosionSoundId)) {
                  ItemTimerModule.activeTimers.add(new SgEnum030(10000, f2, f, f1, trapActionName, Items.POPPED_CHORUS_FRUIT));
               }
            }
         }
      }
   }
}
