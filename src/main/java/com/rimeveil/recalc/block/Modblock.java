package com.rimeveil.recalc.block;

import com.rimeveil.recalc.Recalc;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.SlabBlock;
import net.minecraft.registry.Registry;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.TrapdoorBlock;

public class Modblock {
    // 示例方块
    // 方块ID为test/example_block
    // 方块名称为block.recalc.test.example_block
    public static final Block EXAMPLE_BLOCK = register("test/example_block", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
    public static final Block EXAMPLE_BLOCK2 = register("test/example_block2", new Block(AbstractBlock.Settings.create().strength(0.1F, 0.1F).requiresTool()));
    public static final Block EXAMPLE_BLOCK3 = register("test/example_block3", new Block(AbstractBlock.Settings.create().strength(3.0F, 3.0F).requiresTool()));
    public static final Block EXAMPLE_BLOCK4 = register("test/example_block4", new Block(AbstractBlock.Settings.create().strength(0.5F, 0.5F).requiresTool()));
    public static final Block EXAMPLE_BLOCK5 = register("test/example_block5", new Block(AbstractBlock.Settings.create().strength(0.5F, 0.5F).requiresTool()));
    
    
    public static final Block SUPER_SUAR_ORE = register("super_sugar_ore", new Block(AbstractBlock.Settings.create().strength(0.5F, 0.5F).requiresTool()));
    public static final Block SUPER_SUGAR_BLOCK = register("super_sugar_block", new Block(AbstractBlock.Settings.create().strength(0.5F, 0.5F).requiresTool()));


    public static final Block SUPER_SUGAR_STAIRS = register("super_sugar_stairs", 
        new StairsBlock(SUPER_SUGAR_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK)));
    public static final Block SUPER_SUGAR_SLAB = register("super_sugar_slab", 
        new SlabBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK)));
    public static final Block SUPER_SUGAR_BUTTON = register("super_sugar_button", 
        new ButtonBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK), BlockSetType.STONE,30, false));
    public static final Block SUPER_SUGAR_PRESSURE_PLATE = register("super_sugar_pressure_plate", 
        new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK), BlockSetType.STONE));
    public static final Block SUPER_SUGAR_FENCE = register("super_sugar_fence", 
        new FenceBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK)));
    public static final Block SUPER_SUGAR_FENCE_GATE = register("super_sugar_fence_gate", 
        new FenceGateBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK), WoodType.OAK));
    public static final Block SUPER_SUGAR_WALL = register("super_sugar_wall", 
        new WallBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK)));
    public static final Block SUPER_SUGAR_DOOR = register("super_sugar_door", 
        new DoorBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK), BlockSetType.IRON));
    public static final Block SUPER_SUGAR_TRAPDOOR = register("super_sugar_trapdoor", 
        new TrapdoorBlock(AbstractBlock.Settings.copy(SUPER_SUGAR_BLOCK), BlockSetType.STONE));
    
    
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
