package catlavan.module.combat;

import catlavan.config.AbstractModuleSetting;
import catlavan.config.BooleanSetting;
import catlavan.event.Event;
import catlavan.module.Module;
import catlavan.util.EntityConsumerNode;
import catlavan.util.McContextHolder;
import sg.BlockRayTraceResult;
import sg.Vec3d;
import sg.SgClass179;
import sg.SgClass241;
import sg.SgClass249;
import sg.SgClass276;
import sg.Entity;
import sg.SgClass402;
import sg.ClientTickEvent;
import sg.ItemEntity;
import sg.Items;
import sg.SgClass477;
import sg.Hand;
import sg.Item;
import sg.BlockPos;
import sg.RayTraceResultType;
import sg.Box;
import sg.MinecraftClient;

public class ItemProtectModule extends Module implements McContextHolder {
   SgClass249 targetEntity;
   static MinecraftClient mc;
   double AABB_EXPAND_NEG_X;
   double AABB_EXPAND_NEG_Y;
   double AABB_EXPAND_NEG_Z;
   double AABB_EXPAND_POS_X;
   double AABB_EXPAND_POS_Y;
   double AABB_EXPAND_POS_Z;
   double AABB_EXPAND_POS_Z2;
   double TARGET_RANGE;
   double RAY_TRACE_RANGE;
   double RAY_TRACE_ENTITY_RANGE;
   static String OPTION_DISPLAY_NAME = "Не взрывать ресурсы";
   static String OPTION_TAG = "Protect items";
   BooleanSetting field_Lsg = new BooleanSetting(OPTION_DISPLAY_NAME, true, OPTION_TAG);

   private void performAttack() {
      this.targetEntity = this.findTargetEntity();
      if (this.targetEntity != null && mc.gameSettings.keyBindUseItem.isKeyDown()) {
         mc.rightClickDelayTimer = 0;
         mc.playerController.attackEntity(mc.player, this.targetEntity);
         mc.player.swingArm(Hand.MAIN_HAND);
      }
   }

   @Override
   public void onEvent(Event event) {
      if (mc.player != null && mc.world != null && mc.playerController != null) {
         if (event instanceof ClientTickEvent) {
            this.performAttack();
         }

         if (event instanceof EntityConsumerNode) {
            EntityConsumerNode entityconsumernode = (EntityConsumerNode)event;
            this.onEntityRemove(entityconsumernode);
         }
      }
   }

   private boolean isValuableItemNearBlock(BlockPos blockPos) {
      Box Box = new Box(
         blockPos.getX() - AABB_EXPAND_NEG_X,
         blockPos.getY() - AABB_EXPAND_NEG_Y,
         blockPos.getZ() - AABB_EXPAND_NEG_Z,
         blockPos.getX() + AABB_EXPAND_POS_X + 1.0,
         blockPos.getY() + AABB_EXPAND_POS_Y + AABB_EXPAND_POS_Z,
         blockPos.getZ() + AABB_EXPAND_POS_Z2 + 1.0
      );

      for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, Box)) {
         if (entity instanceof ItemEntity) {
            ItemEntity ItemEntity = (ItemEntity)entity;
            Item Item = ItemEntity.getItem().getItem();
            if (Item == Items.NETHERITE_HELMET
               || Item == Items.NETHERITE_CHESTPLATE
               || Item == Items.NETHERITE_LEGGINGS
               || Item == Items.NETHERITE_BOOTS
               || Item == Items.DIAMOND_HELMET
               || Item == Items.DIAMOND_CHESTPLATE
               || Item == Items.DIAMOND_LEGGINGS
               || Item == Items.DIAMOND_BOOTS
               || Item == Items.NETHERITE_SWORD
               || Item == Items.DIAMOND_SWORD
               || Item == Items.NETHERITE_PICKAXE
               || Item == Items.DIAMOND_PICKAXE
               || Item == Items.NETHERITE_SHOVEL
               || Item == Items.DIAMOND_SHOVEL
               || Item == Items.NETHERITE_AXE
               || Item == Items.DIAMOND_AXE
               || Item == Items.TOTEM_OF_UNDYING
               || Item == Items.END_CRYSTAL
               || Item == Items.ENCHANTED_GOLDEN_APPLE
               || Item == Items.GOLDEN_APPLE
               || Item == Items.ENDER_PEARL
               || Item == Items.TRIDENT
               || Item == Items.CROSSBOW
               || Item == Items.ELYTRA) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isWithinTargetRange(SgClass249 target) {
      return mc.player.getDistance(target) <= TARGET_RANGE;
   }

   private void onEntityRemove(EntityConsumerNode entityconsumernode) {
      Entity entity = entityconsumernode.target;
      if (entity instanceof SgClass249) {
         SgClass249 target = (SgClass249)entity;
         target.remove();
      }
   }

   private boolean canSeeEntity(SgClass249 target) {
      BlockPos pos = target.getPosition();
      BlockPos posDown = pos.down();
      Vec3d eyePos = mc.player.getEyePosition(1.0F);
      Vec3d rayEnd = eyePos.add(mc.player.getLook(1.0F).scale(RAY_TRACE_RANGE));
      SgClass477 SgClass477 = SgClass276.rayTraceEntities(mc.player, eyePos, rayEnd, new Box(eyePos, rayEnd).grow(1.0), entity -> entity == target, RAY_TRACE_ENTITY_RANGE);
      if (SgClass477 != null) {
         return true;
      } else {
         BlockRayTraceResult BlockRayTraceResult = mc.world.rayTraceBlocks(new SgClass402(eyePos, rayEnd, SgClass241.OUTLINE, SgClass179.NONE, mc.player));
         return BlockRayTraceResult.getType() == RayTraceResultType.BLOCK && BlockRayTraceResult.getPos().equals(posDown);
      }
   }

   public ItemProtectModule() {
      this.addSettings(new AbstractModuleSetting[]{this.field_Lsg});
   }

   private SgClass249 findTargetEntity() {
      for (Entity entity : mc.world.getAllEntities()) {
         if (entity instanceof SgClass249) {
            SgClass249 target = (SgClass249)entity;
            if (!target.removed
               && this.isWithinTargetRange(target)
               && this.canSeeEntity(target)
               && (!this.field_Lsg.getValue() || !this.isValuableItemNearBlock(target.getPosition()))) {
               return target;
            }
         }
      }

      return null;
   }

   static {
      9 = 0.0;
      ш = 0.0;
   }
}
