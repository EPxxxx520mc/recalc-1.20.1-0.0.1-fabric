package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.tag.ModBlockTags;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider{
    public ModBlockTagsProvider(FabricDataOutput Output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(Output, registriesFuture);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // 设置可用镐子挖掘的方块
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(Modblock.EXAMPLE_BLOCK)
            .add(Modblock.EXAMPLE_BLOCK2)
            .add(Modblock.EXAMPLE_BLOCK3)
            .add(Modblock.EXAMPLE_BLOCK4)
            .add(Modblock.EXAMPLE_BLOCK5);

        // 设置需要石镐或更高级别工具才能掉落的方块
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .add(Modblock.EXAMPLE_BLOCK)
            .add(Modblock.EXAMPLE_BLOCK2)
            .add(Modblock.EXAMPLE_BLOCK3)
            .add(Modblock.EXAMPLE_BLOCK4)
            .add(Modblock.EXAMPLE_BLOCK5);
            
        // 如果需要铁镐级别，请使用 BlockTags.NEEDS_IRON_TOOL
        // 如果需要钻石镐级别，请使用 BlockTags.NEEDS_DIAMOND_TOOL

        getOrCreateTagBuilder(ModBlockTags.EXAMPLE_BLOCK)
            .add(Modblock.EXAMPLE_BLOCK)
            .add(Modblock.EXAMPLE_BLOCK2)
            .add(Modblock.EXAMPLE_BLOCK3)
            .add(Modblock.EXAMPLE_BLOCK4)
            .add(Modblock.EXAMPLE_BLOCK5)
            .forceAddTag(BlockTags.DIAMOND_ORES)
            .forceAddTag(BlockTags.IRON_ORES)
            .forceAddTag(BlockTags.COAL_ORES)
            .forceAddTag(BlockTags.GOLD_ORES)
            .forceAddTag(BlockTags.REDSTONE_ORES)
            .forceAddTag(BlockTags.LAPIS_ORES)
            .forceAddTag(BlockTags.EMERALD_ORES);
        
        // 超级糖系列方块标签
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(Modblock.SUPER_SUAR_ORE)
            .add(Modblock.SUPER_SUGAR_BLOCK)
            .add(Modblock.SUPER_SUGAR_STAIRS)
            .add(Modblock.SUPER_SUGAR_SLAB)
            .add(Modblock.SUPER_SUGAR_BUTTON)
            .add(Modblock.SUPER_SUGAR_PRESSURE_PLATE)
            .add(Modblock.SUPER_SUGAR_FENCE)
            .add(Modblock.SUPER_SUGAR_FENCE_GATE)
            .add(Modblock.SUPER_SUGAR_WALL);
        
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .add(Modblock.SUPER_SUAR_ORE)
            .add(Modblock.SUPER_SUGAR_BLOCK);

        getOrCreateTagBuilder(BlockTags.WALLS)
            .add(Modblock.SUPER_SUGAR_WALL);
        getOrCreateTagBuilder(BlockTags.STAIRS)
            .add(Modblock.SUPER_SUGAR_STAIRS);
        getOrCreateTagBuilder(BlockTags.FENCES)
            .add(Modblock.SUPER_SUGAR_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
            .add(Modblock.SUPER_SUGAR_FENCE_GATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
            .add(Modblock.SUPER_SUGAR_FENCE);
    }
}
