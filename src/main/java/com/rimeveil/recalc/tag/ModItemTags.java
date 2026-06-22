package com.rimeveil.recalc.tag;

import com.rimeveil.recalc.Recalc;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {
    public static final TagKey<Item> EXAMPLE_ITEM = of("example_item");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Recalc.id(id));
    }
}
