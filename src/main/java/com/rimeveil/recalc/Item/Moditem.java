package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Recalc;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.HoeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ShovelItem;
import com.rimeveil.recalc.Item.custom.PickaxeAxeItem;
public class Moditem {
    // 示例物品
    // 物品ID为test/example_item
    // 物品名称为item.recalc.test.example_item
    public static final Item EXAMPLE_ITEM = regitem("test/example_item", new Item(new Item.Settings()));
    // 示例物品2
    // 物品ID为test/example_item2
    // 物品名称为item.recalc.test.example_item2
    public static final Item EXAMPLE_ITEM2 = regitem("test/example_item2", new Item(new Item.Settings()));
    // 超级糖
    // 物品ID为super_sugar
    // 物品名称为item.recalc.super_sugar
    public static final Item SUPER_SUGAR = regitem("super_sugar", new Item(new Item.Settings().food(ModFoodComponents.SUPER_SUGAR)));
    // 玉米
    // 物品ID为corn
    public static final Item CORN = regitem("corn", new Item(new Item.Settings().food(ModFoodComponents.CORN)));
    // 超级煤炭
    // 物品ID为super_coal
    public static final Item SUPER_COAL = regitem("super_coal", new Item(new Item.Settings().fireproof()));
    public static final Item SUPER_SUGAR_SWORD = regitem("super_sugar_sword", new SwordItem(ModToolMaterials.SUPER_SUGAR, 
        7, -2.0F, new Item.Settings().fireproof()));
    public static final Item SUPER_SUGAR_PICKAXE = regitem("super_sugar_pickaxe", new PickaxeItem(ModToolMaterials.SUPER_SUGAR, 
        0, -2.4F, new Item.Settings().fireproof()));
    public static final Item SUPER_SUGAR_AXE = regitem("super_sugar_axe", new AxeItem(ModToolMaterials.SUPER_SUGAR, 
        8.5F, -3.5F, new Item.Settings().fireproof()));
    public static final Item SUPER_SUGAR_SHOVEL = regitem("super_sugar_shovel", new ShovelItem(ModToolMaterials.SUPER_SUGAR, 
        0, -2.4F, new Item.Settings().fireproof()));
    public static final Item SUPER_SUGAR_HOE = regitem("super_sugar_hoe", new HoeItem(ModToolMaterials.SUPER_SUGAR, 
        0, -2.4F, new Item.Settings().fireproof()));
    public static final Item SUPER_SUGAR_PICKAXE_AXE = regitem("super_sugar_pickaxe_axe", new PickaxeAxeItem(ModToolMaterials.SUPER_SUGAR, 
        8.5F, -3.5F, new Item.Settings().fireproof()));


    

    /**
     * 注册物品
     * @param id 物品的ID
     * @param item 物品实例
     * @return 注册后的物品
     */
    public static Item regitem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Recalc.MOD_ID, id), item);
    }

    // 辅助加载物品
    public static void regitems() {
        FuelRegistry.INSTANCE.add(Moditem.SUPER_COAL, 1600);
    }
}
