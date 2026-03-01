package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Recalc;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Moditemgroup {
    public static final RegistryKey<ItemGroup> RECALC = register("recalc");
    private static RegistryKey<ItemGroup> register(String id) {
      return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Recalc.MOD_ID, id));
   }
   public static void ItemGroup () {
        Registry.register(
            Registries.ITEM_GROUP,
            RECALC,
            ItemGroup.create(ItemGroup.Row.TOP, 7)
            .displayName(Text.translatable("itemGroup.recalc"))
            .icon(() -> new ItemStack(Moditem.EXAMPLE_ITEM))
            .entries((displayContext, entries) -> {
                entries.add(Moditem.EXAMPLE_ITEM);
            })
            .build()
        );
    }
}
