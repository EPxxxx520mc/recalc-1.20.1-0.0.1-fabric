package com.rimeveil.recalc.block;

import com.rimeveil.recalc.Recalc;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;

public class Modblock {
    // 示例方块
    // 方块ID为test/example_block
    // 方块名称为block.recalc.test.example_block
    public static final Block EXAMPLE_BLOCK = register("test/example_block", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block EXAMPLE_BLOCK2 = register("test/example_block2", new Block(AbstractBlock.Settings.create().strength(0.1F, 0.1F)));
    public static final Block EXAMPLE_BLOCK3 = register("test/example_block3", new Block(AbstractBlock.Settings.create().strength(3.0F, 3.0F)));

    public static Block register(String id, Block block) {
        regblocksitem(id, block);
        return Registry.register(Registries.BLOCK, new Identifier(Recalc.MOD_ID, id), block);
    }

    public static void regblocksitem(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier(Recalc.MOD_ID, id),
                new BlockItem(block, new Item.Settings()));
    }

    // 辅助加载方块物品
    public static void regblocks() {
    }
}
