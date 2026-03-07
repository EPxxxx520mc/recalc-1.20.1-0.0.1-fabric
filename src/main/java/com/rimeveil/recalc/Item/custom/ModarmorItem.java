package com.rimeveil.recalc.Item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import com.rimeveil.recalc.Item.ModArmorMaterials;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
public class ModarmorItem extends ArmorItem{
    private static final Map<ArmorMaterial, List<StatusEffectInstance>> MAP = 
        (new ImmutableMap.Builder<ArmorMaterial, List<StatusEffectInstance>>()
            .put(ModArmorMaterials.SUPER_SUGAR,
                Arrays.asList(
                    new StatusEffectInstance(StatusEffects.SPEED, 200, 1, false, false, true),
                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200, 1, false, false, true)
            ))
            .build());

    public ModarmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof PlayerEntity player) {
            if (isWearingAllArmor(player) && hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffect(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateArmorEffect(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, List<StatusEffectInstance>> entry : MAP.entrySet()) {
            ArmorMaterial material = entry.getKey();
            List<StatusEffectInstance> effects = entry.getValue();

            if (hasCorrectMaterialArmorOn(material, player)) {
                for (StatusEffectInstance effect : effects) {

                    player.addStatusEffect(new StatusEffectInstance(effect));
                }
            }
        }
    }

    private boolean hasCorrectMaterialArmorOn(ArmorMaterial material, PlayerEntity player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem armorItem)) {
                return false;
            }
            if (armorItem.getMaterial() != material) {
                return false;
            }
        }
        return true;
    }

    private boolean isWearingAllArmor(PlayerEntity player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (armorStack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        return isWearingAllArmor(player);
    }
}
