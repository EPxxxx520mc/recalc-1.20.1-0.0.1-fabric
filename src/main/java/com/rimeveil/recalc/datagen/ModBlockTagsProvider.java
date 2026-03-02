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
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(Modblock.EXAMPLE_BLOCK)
            .add(Modblock.EXAMPLE_BLOCK2)
            .add(Modblock.EXAMPLE_BLOCK3)
            .add(Modblock.EXAMPLE_BLOCK4);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .add(Modblock.EXAMPLE_BLOCK)
            .add(Modblock.EXAMPLE_BLOCK2)
            .add(Modblock.EXAMPLE_BLOCK3)
            .add(Modblock.EXAMPLE_BLOCK4);
    }
}
