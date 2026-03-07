package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public class ModLootTableProvider extends FabricBlockLootTableProvider{
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    @Override
    public void generate() {
        // 普通方块掉落其自身
        addDrop(Modblock.EXAMPLE_BLOCK);
        addDrop(Modblock.EXAMPLE_BLOCK2);
        addDrop(Modblock.EXAMPLE_BLOCK3);
        addDrop(Modblock.EXAMPLE_BLOCK4);
        
        // 特殊矿石掉落逻辑：掉落 EXAMPLE_ITEM2，数量 2-5 个，受时运影响
        addDrop(Modblock.EXAMPLE_BLOCK5, RecalccopperOreDrops(Modblock.EXAMPLE_BLOCK5, Moditem.EXAMPLE_ITEM2, 2, 5));
        
        // 超级糖系列方块掉落表
        addDrop(Modblock.SUPER_SUGAR_ORE, RecalccopperOreDrops(Modblock.SUPER_SUGAR_ORE, Moditem.SUPER_SUGAR, 2, 4));
        addDrop(Modblock.SUPER_SUGAR_BLOCK);
        addDrop(Modblock.SUPER_SUGAR_STAIRS);
        addDrop(Modblock.SUPER_SUGAR_SLAB, slabDrops(Modblock.SUPER_SUGAR_SLAB));
        addDrop(Modblock.SUPER_SUGAR_BUTTON);
        addDrop(Modblock.SUPER_SUGAR_PRESSURE_PLATE);
        addDrop(Modblock.SUPER_SUGAR_FENCE);
        addDrop(Modblock.SUPER_SUGAR_FENCE_GATE);
        addDrop(Modblock.SUPER_SUGAR_WALL);
        addDrop(Modblock.SUPER_SUGAR_DOOR, doorDrops(Modblock.SUPER_SUGAR_DOOR));
        addDrop(Modblock.SUPER_SUGAR_TRAPDOOR);
    }

    /**
     * 自定义矿石掉落逻辑
     * @param drop 挖掘的方块
     * @param item 掉落的物品
     * @param min 最小数量
     * @param max 最大数量
     * @return 战利品表构建器
     */
    public LootTable.Builder RecalccopperOreDrops(Block drop, Item item, int min, int max) {
		return dropsWithSilkTouch(
			drop,
			(LootPoolEntry.Builder<?>)this.applyExplosionDecay(
				drop,
				ItemEntry.builder(item)
					.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
					.apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
			)
		);
	}
}
