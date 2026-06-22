package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.block.Modblock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class Moditemgroup {
    public static final ItemGroup RECALC_GROUP = Registry.register(
        Registries.ITEM_GROUP,
        Recalc.id("recalc"),
        ItemGroup.create(ItemGroup.Row.TOP, -1)
            .displayName(Text.translatable("itemGroup.recalc"))
            .icon(() -> new ItemStack(Moditem.SUPER_SUGAR))
            .entries((displayContext, entries) -> {
                addExamples(entries);
                addSuperSugarContent(entries);
                entries.add(Moditem.FICTIONAL_FRAME);
            })
            .build()
    );

    public static void regitemtogroup() {
    }

    private static void addExamples(ItemGroup.Entries entries) {
        addAll(
            entries,
            Moditem.EXAMPLE_ITEM,
            Items.STONE,
            Items.DIAMOND,
            Modblock.EXAMPLE_BLOCK,
            Modblock.EXAMPLE_BLOCK2,
            Modblock.EXAMPLE_BLOCK3,
            Modblock.EXAMPLE_BLOCK4,
            Moditem.EXAMPLE_ITEM2,
            Modblock.EXAMPLE_BLOCK5
        );
    }

    private static void addSuperSugarContent(ItemGroup.Entries entries) {
        addAll(
            entries,
            Moditem.SUPER_SUGAR,
            Moditem.CORN,
            Moditem.SUPER_COAL,
            Modblock.SUPER_SUGAR_ORE,
            Modblock.SUPER_SUGAR_BLOCK,
            Modblock.SUPER_SUGAR_STAIRS,
            Modblock.SUPER_SUGAR_SLAB,
            Modblock.SUPER_SUGAR_BUTTON,
            Modblock.SUPER_SUGAR_PRESSURE_PLATE,
            Modblock.SUPER_SUGAR_FENCE,
            Modblock.SUPER_SUGAR_FENCE_GATE,
            Modblock.SUPER_SUGAR_WALL,
            Modblock.SUPER_SUGAR_DOOR,
            Modblock.SUPER_SUGAR_TRAPDOOR,
            Moditem.SUPER_SUGAR_SWORD,
            Moditem.SUPER_SUGAR_PICKAXE,
            Moditem.SUPER_SUGAR_AXE,
            Moditem.SUPER_SUGAR_SHOVEL,
            Moditem.SUPER_SUGAR_HOE,
            Moditem.SUPER_SUGAR_PICKAXE_AXE,
            Moditem.SUPER_SUGAR_HELMET,
            Moditem.SUPER_SUGAR_CHESTPLATE,
            Moditem.SUPER_SUGAR_LEGGINGS,
            Moditem.SUPER_SUGAR_BOOTS,
            Moditem.SUPER_SUGAR_HORSE_ARMOR,
            Moditem.SUPER_SUGAR_3D_TOY_SWORD
        );
    }

    private static void addAll(ItemGroup.Entries entries, ItemConvertible... items) {
        for (ItemConvertible item : items) {
            entries.add(item);
        }
    }
}
