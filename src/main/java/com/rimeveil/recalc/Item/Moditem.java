package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Item.custom.FictionalFrameItem;
import com.rimeveil.recalc.Item.custom.ModarmorItem;
import com.rimeveil.recalc.Item.custom.PickaxeAxeItem;
import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Moditem {
    public static final Item EXAMPLE_ITEM = regitem("test/example_item", new Item(new Item.Settings()));
    public static final Item EXAMPLE_ITEM2 = regitem("test/example_item2", new Item(new Item.Settings()));
    public static final Item SUPER_SUGAR = regitem("super_sugar", new Item(new Item.Settings().food(ModFoodComponents.SUPER_SUGAR)));
    public static final Item CORN = regitem("corn", new Item(new Item.Settings().food(ModFoodComponents.CORN)));
    public static final Item SUPER_COAL = regitem("super_coal", new Item(new Item.Settings().fireproof()));

    public static final Item SUPER_SUGAR_SWORD = regitem("super_sugar_sword", new SwordItem(ModToolMaterials.SUPER_SUGAR, 7, -2.0F, fireproof()));
    public static final Item SUPER_SUGAR_PICKAXE = regitem("super_sugar_pickaxe", new PickaxeItem(ModToolMaterials.SUPER_SUGAR, 0, -2.4F, fireproof()));
    public static final Item SUPER_SUGAR_AXE = regitem("super_sugar_axe", new AxeItem(ModToolMaterials.SUPER_SUGAR, 8.5F, -3.5F, fireproof()));
    public static final Item SUPER_SUGAR_SHOVEL = regitem("super_sugar_shovel", new ShovelItem(ModToolMaterials.SUPER_SUGAR, 0, -2.4F, fireproof()));
    public static final Item SUPER_SUGAR_HOE = regitem("super_sugar_hoe", new HoeItem(ModToolMaterials.SUPER_SUGAR, 0, -2.4F, fireproof()));
    public static final Item SUPER_SUGAR_PICKAXE_AXE = regitem("super_sugar_pickaxe_axe", new PickaxeAxeItem(ModToolMaterials.SUPER_SUGAR, 8.5F, -3.5F, fireproof()));

    public static final Item SUPER_SUGAR_HELMET = regitem("super_sugar_helmet", new ModarmorItem(ModArmorMaterials.SUPER_SUGAR, ArmorItem.Type.HELMET, fireproof()));
    public static final Item SUPER_SUGAR_CHESTPLATE = regitem("super_sugar_chestplate", new ModarmorItem(ModArmorMaterials.SUPER_SUGAR, ArmorItem.Type.CHESTPLATE, fireproof()));
    public static final Item SUPER_SUGAR_LEGGINGS = regitem("super_sugar_leggings", new ModarmorItem(ModArmorMaterials.SUPER_SUGAR, ArmorItem.Type.LEGGINGS, fireproof()));
    public static final Item SUPER_SUGAR_BOOTS = regitem("super_sugar_boots", new ModarmorItem(ModArmorMaterials.SUPER_SUGAR, ArmorItem.Type.BOOTS, fireproof()));

    public static final Item SUPER_SUGAR_3D_TOY_SWORD = regitem("super_sugar_3d_toy_sword", new SwordItem(ModToolMaterials.SUPER_SUGAR, 20, -2.0F, fireproof()));
    public static final Item SUPER_SUGAR_HORSE_ARMOR = regitem("super_sugar_horse_armor", new HorseArmorItem(11, "super_sugar_horse_armor", new Item.Settings().maxCount(1).fireproof()));
    public static final Item ONION_SEEDS = regitem("onion_seeds", new AliasedBlockItem(Modblock.ONION_CROP, new Item.Settings()));
    public static final Item FICTIONAL_FRAME = regitem("fictional_frame", new FictionalFrameItem(new Item.Settings().maxCount(1)));

    public static Item regitem(String id, Item item) {
        return Registry.register(Registries.ITEM, Recalc.id(id), item);
    }

    public static void regitems() {
        FuelRegistry.INSTANCE.add(SUPER_COAL, 1600);
    }

    private static Item.Settings fireproof() {
        return new Item.Settings().fireproof();
    }
}
