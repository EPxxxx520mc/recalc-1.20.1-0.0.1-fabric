package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.block.Modblock;
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
    }
}
