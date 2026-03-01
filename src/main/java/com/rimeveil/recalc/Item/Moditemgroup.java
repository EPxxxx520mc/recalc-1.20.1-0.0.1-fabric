package com.rimeveil.recalc.Item;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.block.Modblock;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Moditemgroup {
    // 往Recalc物品组添加物品 
    // 包括示例物品、石头、钻石、示例方块、示例方块2、示例方块3

   public static final ItemGroup RECALC_GROUP = Registry.register(
        Registries.ITEM_GROUP,
        new Identifier(Recalc.MOD_ID, "recalc"),
        ItemGroup.create(ItemGroup.Row.TOP, -1)
            .displayName(Text.translatable("itemGroup.recalc"))
            .icon(() -> new ItemStack(Moditem.EXAMPLE_ITEM))
            .entries((displayContext, entries) -> {
                entries.add(Moditem.EXAMPLE_ITEM);
                entries.add(Items.STONE);
                entries.add(Items.DIAMOND);
                entries.add(Modblock.EXAMPLE_BLOCK);
                entries.add(Modblock.EXAMPLE_BLOCK2);
                entries.add(Modblock.EXAMPLE_BLOCK3);
            }).build()
   );
   
   //辅助加载物品组
   public static void regitemtogroup() {
    }
}
