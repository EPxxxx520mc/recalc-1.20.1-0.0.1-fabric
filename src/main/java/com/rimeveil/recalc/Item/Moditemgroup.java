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
                entries.add(Modblock.EXAMPLE_BLOCK4);
                entries.add(Moditem.EXAMPLE_ITEM2);
                entries.add(Modblock.EXAMPLE_BLOCK5);


                entries.add(Moditem.SUPER_SUGAR);
                entries.add(Moditem.CORN);
                entries.add(Moditem.SUPER_COAL);
                entries.add(Modblock.SUPER_SUGAR_ORE);
                entries.add(Modblock.SUPER_SUGAR_BLOCK);
                entries.add(Modblock.SUPER_SUGAR_STAIRS);
                entries.add(Modblock.SUPER_SUGAR_SLAB);
                entries.add(Modblock.SUPER_SUGAR_BUTTON);
                entries.add(Modblock.SUPER_SUGAR_PRESSURE_PLATE);
                entries.add(Modblock.SUPER_SUGAR_FENCE);
                entries.add(Modblock.SUPER_SUGAR_FENCE_GATE);
                entries.add(Modblock.SUPER_SUGAR_WALL);
                entries.add(Modblock.SUPER_SUGAR_DOOR);
                entries.add(Modblock.SUPER_SUGAR_TRAPDOOR);
                entries.add(Moditem.SUPER_SUGAR_SWORD);
                entries.add(Moditem.SUPER_SUGAR_PICKAXE);
                entries.add(Moditem.SUPER_SUGAR_AXE);
                entries.add(Moditem.SUPER_SUGAR_SHOVEL);
                entries.add(Moditem.SUPER_SUGAR_HOE);
                entries.add(Moditem.SUPER_SUGAR_PICKAXE_AXE);
                entries.add(Moditem.SUPER_SUGAR_HELMET);
                entries.add(Moditem.SUPER_SUGAR_CHESTPLATE);
                entries.add(Moditem.SUPER_SUGAR_LEGGINGS);
                entries.add(Moditem.SUPER_SUGAR_BOOTS);
                entries.add(Moditem.SUPER_SUGAR_HORSE_ARMOR);
                entries.add(Moditem.SUPER_SUGAR_3d_TOY_SWORD);
            }).build()
   );
   
   //辅助加载物品组
   public static void regitemtogroup() {
    }
}
