package com.rimeveil.recalc.datagen;

import java.util.concurrent.CompletableFuture;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.tag.ModItemTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider{
    public ModItemTagsProvider(FabricDataOutput Output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(Output, completableFuture);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // 在此处添加物品标签 (Item Tags)
        // 例如：getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(Moditem.EXAMPLE_ITEM);
        getOrCreateTagBuilder(ModItemTags.EXAMPLE_ITEM)
        .add(Moditem.EXAMPLE_ITEM)
        .add(Moditem.EXAMPLE_ITEM2)
        .add(Moditem.SUPER_COAL)
        .add(Moditem.SUPER_SUGAR);
    }
}
