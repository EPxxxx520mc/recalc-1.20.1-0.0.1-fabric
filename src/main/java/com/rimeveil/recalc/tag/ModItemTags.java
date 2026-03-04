package com.rimeveil.recalc.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.RegistryKeys;
import com.rimeveil.recalc.Recalc;


public class ModItemTags {
    public static final TagKey<Item> EXAMPLE_ITEM = of("example_item");




    private static final TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Recalc.MOD_ID, id));
    }
}
