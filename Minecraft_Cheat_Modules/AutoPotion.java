/*
 * Decompiled with CFR 0.152.
 */
package rockstar.feature.modules.combat;
import rockstar.deobfuscated.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import pyrock.events.player.ClientPlayerTickEvent;
import rockstar.feature.settings.impl.MultiChoiceSetting;
import rockstar.core.Rockstar;
import rockstar.core.event.EventListener;
import rockstar.core.Category;
import rockstar.core.ModuleInfo;
import rockstar.deobfuscated.Class0852;
import rockstar.utils.player.InventoryUtils;
import rockstar.deobfuscated.Class0968;
import rockstar.deobfuscated.Class0993;
import rockstar.deobfuscated.Class1004;
import rockstar.deobfuscated.Class1007;
import rockstar.deobfuscated.Class1268;
import rockstar.deobfuscated.Class1271;
import rockstar.feature.modules.Module;
import rockstar.deobfuscated.Class1277;
import rockstar.deobfuscated.Class1333;
import ua.mintantileak.spk.Compile;

@ModuleInfo(method00028="Auto Potion", method04432=Category.field00395, method03909="modules.descriptions.auto_potion")
public class AutoPotion
extends Module {
    private MultiChoiceSetting field00331;
    private Class1333 field00608;
    private Class1333 field01712;
    private Class1333 field00975;
    private int field00005;
    private int field01495;
    private final EventListener<ClientPlayerTickEvent> field00346 = clientPlayerTickEvent -> {
        if (AutoPotion.field00117.player == null || AutoPotion.field00117.world == null || AutoPotion.field00117.interactionManager == null || field00117.getNetworkHandler() == null || AutoPotion.field00117.player.isGliding()) {
            return;
        }
        ++this.field00005;
        List<Class0968> list = this.method00046();
        if (this.field00005 < 20 || list.isEmpty()) {
            this.field01495 = 0;
            return;
        }
        float f = AutoPotion.field00117.player.getYaw();
        Class1271 class1271 = new Class1271(f, 90.0f);
        float f2 = ThreadLocalRandom.current().nextFloat(275.0f, 444.0f);
        Rockstar.method00215().method00392().method03419(class1271, Class1268.field01321, f2, f2, f2, Class1277.field01824);
        if (Rockstar.method00215().method00392().method04463().method03407(class1271) > 1.0f) {
            this.field01495 = 0;
            return;
        }
        if (this.field01495++ < 1) {
            return;
        }
        int n2 = AutoPotion.field00117.player.getInventory().selectedSlot;
        boolean bl = false;
        for (Class0968 class0968 : list) {
            if (!this.method03305(class0968)) continue;
            if (class0968 instanceof Class1004) {
                int n3;
                Class1004 class1004 = (Class1004)class0968;
                AutoPotion.field00117.player.getInventory().selectedSlot = n3 = class1004.method03627();
                field00117.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(n3));
                AutoPotion.field00117.interactionManager.sendSequencedPacket(AutoPotion.field00117.world, n -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, n, f, 90.0f));
            } else {
                InventoryUtils.method05095(class0968.method00004(), n2);
                field00117.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(n2));
                AutoPotion.field00117.interactionManager.sendSequencedPacket(AutoPotion.field00117.world, n -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, n, f, 90.0f));
                InventoryUtils.method05095(class0968.method00004(), n2);
            }
            bl = true;
        }
        AutoPotion.field00117.player.getInventory().selectedSlot = n2;
        field00117.getNetworkHandler().sendPacket((Packet)new UpdateSelectedSlotC2SPacket(n2));
        this.field01495 = 0;
        if (bl) {
            this.field00005 = 0;
            Rockstar.method00215().method00392().method05018(new Class1271(AutoPotion.field00117.player.getYaw(), AutoPotion.field00117.player.getPitch()));
        }
    };

    public AutoPotion() {
        this.method04271();
    }

    @Compile(obfuscation=4)
    private void method04271() {
        this.field00331 = new MultiChoiceSetting(this, "modules.settings.auto_potion.potions");
        this.field00608 = new Class1333(this.field00331, "modules.settings.auto_potion.potions.strength", (RegistryEntry<StatusEffect>)StatusEffects.STRENGTH);
        this.field00608.select();
        this.field01712 = new Class1333(this.field00331, "modules.settings.auto_potion.potions.speed", (RegistryEntry<StatusEffect>)StatusEffects.SPEED);
        this.field01712.select();
        this.field00975 = new Class1333(this.field00331, "modules.settings.auto_potion.potions.fire_resistance", (RegistryEntry<StatusEffect>)StatusEffects.FIRE_RESISTANCE);
        this.field00975.select();
    }

    @Override
    public void method05070() {
        this.field00005 = 20;
        this.field01495 = 0;
    }

    @Override
    public void method05256() {
        this.field01495 = 0;
        if (AutoPotion.field00117.player != null) {
            Rockstar.method00215().method00392().method05018(new Class1271(AutoPotion.field00117.player.getYaw(), AutoPotion.field00117.player.getPitch()));
        }
    }

    private List<Class0968> method00046() {
        ArrayList<Class0968> arrayList = new ArrayList<Class0968>();
        Predicate<ItemStack> predicate = itemStack -> !itemStack.isEmpty() && itemStack.getItem() instanceof SplashPotionItem;
        for (Class1333 class1333 : this.method04385()) {
            RegistryEntry<StatusEffect> registryEntry = class1333.field00155;
            if (AutoPotion.field00117.player.hasStatusEffect(registryEntry)) continue;
            Predicate<ItemStack> predicate2 = this.method02181(registryEntry);
            Class1004 class1004 = Class0993.method00338().method01715(itemStack -> predicate.test((ItemStack)itemStack) && predicate2.test((ItemStack)itemStack));
            if (class1004 != null && this.method03305(class1004)) {
                arrayList.add(class1004);
                continue;
            }
            Class1007 class1007 = Class0993.method04443().method01715(itemStack -> predicate.test((ItemStack)itemStack) && predicate2.test((ItemStack)itemStack));
            if (class1007 == null || !this.method03305(class1007)) continue;
            arrayList.add(class1007);
        }
        return arrayList;
    }

    private List<Class1333> method04385() {
        List<Class1333> list = List.of(this.field00608, this.field01712, this.field00975);
        List<Class1333> list2 = this.field00331.method04385().stream().map(Class1333.class::cast).toList();
        ArrayList<Class1333> arrayList = new ArrayList<Class1333>();
        for (Class1333 class1333 : list) {
            if (!list2.contains(class1333)) continue;
            arrayList.add(class1333);
        }
        return arrayList;
    }

    private boolean method03305(Class0968 class0968) {
        ItemStack itemStack = class0968.method00094();
        return !itemStack.isEmpty() && itemStack.getItem() instanceof SplashPotionItem;
    }

    private Predicate<ItemStack> method02181(RegistryEntry<StatusEffect> registryEntry) {
        return itemStack -> {
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof SplashPotionItem)) {
                return false;
            }
            return Class0852.method02090(itemStack).stream().anyMatch(statusEffectInstance -> statusEffectInstance.getEffectType() == registryEntry);
        };
    }
}

