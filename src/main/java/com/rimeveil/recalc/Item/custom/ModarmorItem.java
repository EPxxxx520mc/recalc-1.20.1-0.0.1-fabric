package com.rimeveil.recalc.Item.custom;

import com.rimeveil.recalc.Item.ModArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ModarmorItem extends ArmorItem {
    private static final int HELMET_ARMOR_SLOT = 3;
    private static final int EFFECT_REFRESH_THRESHOLD_TICKS = 100;

    private static final Map<ArmorMaterial, List<StatusEffectInstance>> MATERIAL_EFFECTS = Map.of(
        ModArmorMaterials.SUPER_SUGAR,
        List.of(
            new StatusEffectInstance(StatusEffects.SPEED, 200, 1, false, false, true),
            new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1, false, false, true)
        )
    );

    public ModarmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (world.isClient || !(entity instanceof PlayerEntity player) || !isHelmetStack(player, stack)) {
            return;
        }

        applyFullSuitEffects(player);
    }

    private static boolean isHelmetStack(PlayerEntity player, ItemStack stack) {
        return player.getInventory().armor.get(HELMET_ARMOR_SLOT) == stack;
    }

    private static void applyFullSuitEffects(PlayerEntity player) {
        if (!isWearingFullSuit(player)) {
            return;
        }

        ArmorMaterial material = getArmorMaterial(player.getInventory().armor.get(0));
        if (material == null || !hasFullSuitOfMaterial(player, material)) {
            return;
        }

        List<StatusEffectInstance> effects = MATERIAL_EFFECTS.get(material);
        if (effects == null) {
            return;
        }

        for (StatusEffectInstance effect : effects) {
            refreshEffect(player, effect);
        }
    }

    private static boolean isWearingFullSuit(PlayerEntity player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (armorStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasFullSuitOfMaterial(PlayerEntity player, ArmorMaterial material) {
        for (ItemStack armorStack : player.getInventory().armor) {
            ArmorMaterial stackMaterial = getArmorMaterial(armorStack);
            if (stackMaterial != material) {
                return false;
            }
        }
        return true;
    }

    private static ArmorMaterial getArmorMaterial(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return armorItem.getMaterial();
        }
        return null;
    }

    private static void refreshEffect(PlayerEntity player, StatusEffectInstance effect) {
        StatusEffectInstance current = player.getStatusEffect(effect.getEffectType());
        if (current != null
            && current.getAmplifier() >= effect.getAmplifier()
            && current.getDuration() > EFFECT_REFRESH_THRESHOLD_TICKS) {
            return;
        }

        player.addStatusEffect(new StatusEffectInstance(effect));
    }
}
