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
        addDrop(Modblock.EXAMPLE_BLOCK);
        addDrop(Modblock.EXAMPLE_BLOCK2);
        addDrop(Modblock.EXAMPLE_BLOCK3);
        addDrop(Modblock.EXAMPLE_BLOCK4);
        addDrop(Modblock.EXAMPLE_BLOCK5, RecalccopperOreDrops(Modblock.EXAMPLE_BLOCK5, Moditem.EXAMPLE_ITEM2, 2, 5));
    }

//        addDrop(Modblock.EXAMPLE_BLOCK5 ,oreDrop(Modblock.EXAMPLE_BLOCK5, Moditem.EXAMPLE_ITEM2));

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
