package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Recalc;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
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

    /**
     * 将物品添加到自然物品组
     * @param entries 物品组条目
     */
    public static void regitemtogroup(FabricItemGroupEntries entries) {
        entries.add(EXAMPLE_ITEM);
    }

    public static void regitems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(Moditem::regitemtogroup);
    }
}
