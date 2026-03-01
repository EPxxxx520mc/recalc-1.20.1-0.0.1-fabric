package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Recalc;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Moditem {

    public static final Item EXAMPLE_ITEM = regitem("test/example_item", new Item(new Item.Settings()));







    /**
     * 注册物品
     * @param id 物品的ID
     * @param item 物品实例
     * @return 注册后的物品
     */
    public static Item regitem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Recalc.MOD_ID, id), item);
    }
    public static void regitems() {

    }
}
