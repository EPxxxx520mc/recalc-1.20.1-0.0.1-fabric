package com.rimeveil.recalc.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider{
    public ModItemTagsProvider(FabricDataOutput Output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(Output, completableFuture);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        
    }
}
