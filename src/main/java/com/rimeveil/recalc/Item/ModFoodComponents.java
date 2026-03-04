package com.rimeveil.recalc.Item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent CORN = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build();
    public static final FoodComponent SUPER_SUGAR = new FoodComponent.Builder().hunger(10).saturationModifier(1F)
        .alwaysEdible()
        .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 200), 1.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200), 1.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200), 2.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 200), 2.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 200), 10.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200), 5.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200), 3.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200), 1.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 200), 1.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 200), 1.0F)
        .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 200), 1.0F)
        .build();
}
