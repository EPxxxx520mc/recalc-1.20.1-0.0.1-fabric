package com.rimeveil.recalc.Item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.effect.StatusEffect;
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
                    new StatusEffectInstance(StatusEffects.SPEED, 20 * 60, 1,false,false,true),
                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20 * 60, 1,false,false,true)
            ))
            .build());
    public ModarmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            if (entity instanceof PlayerEntity player && hasFullSuitableArmor(player)) {
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
                    StatusEffect effects1 = effect.getEffectType();
                    if (!player.hasStatusEffect(effects1)) {
                        player.addStatusEffect(effect);
                    }
                    }
                }
            }
        }
    private boolean hasCorrectMaterialArmorOn(ArmorMaterial material, PlayerEntity player) {
        for (ItemStack stack: player.getInventory().armor) {
            if (stack.getItem() instanceof ArmorItem) {
                    return false;
           }
        }

        ArmorItem helmet = (ArmorItem) player.getInventory().getArmorStack(3).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmorStack(2).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmorStack(1).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmorStack(0).getItem();

        return helmet.getMaterial() == material 
            && chestplate.getMaterial() == material 
            && leggings.getMaterial() == material 
            && boots.getMaterial() == material;
    }

    private boolean hasFullSuitableArmor(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);  
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        return !helmet.isEmpty() 
            && !chestplate.isEmpty()
            && !leggings.isEmpty() 
            && !boots.isEmpty();
    }
}
