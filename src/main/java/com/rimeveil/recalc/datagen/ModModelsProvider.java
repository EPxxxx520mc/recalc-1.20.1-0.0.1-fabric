package com.rimeveil.recalc.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.client.ItemModelGenerator;
import com.rimeveil.recalc.block.Modblock;

import net.minecraft.data.client.BlockStateModelGenerator;
import com.rimeveil.recalc.Item.Moditem;
import net.minecraft.data.client.Models;
import net.minecraft.data.family.BlockFamily;
import com.rimeveil.recalc.block.ModBlockFamilis;

public class ModModelsProvider extends FabricModelProvider{
    public ModModelsProvider(FabricDataOutput Output) {
        super(Output);
    }


    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelProvider) {
        ModBlockFamilis.getFamilies()
            .filter(BlockFamily::shouldGenerateModels)
            .forEach(family -> 
                blockStateModelProvider.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));
        // 注册方块模型（默认为立方体，所有面相同贴图）
        blockStateModelProvider.registerSimpleCubeAll(Modblock.EXAMPLE_BLOCK);
        blockStateModelProvider.registerSimpleCubeAll(Modblock.EXAMPLE_BLOCK2);
        blockStateModelProvider.registerSimpleCubeAll(Modblock.EXAMPLE_BLOCK3);
        blockStateModelProvider.registerSimpleCubeAll(Modblock.EXAMPLE_BLOCK4);
        blockStateModelProvider.registerSimpleCubeAll(Modblock.EXAMPLE_BLOCK5);
        
        // 超级糖系列方块模型
        blockStateModelProvider.registerSimpleCubeAll(Modblock.SUPER_SUAR_ORE);
        //blockStateModelProvider.registerSimpleCubeAll(Modblock.SUPER_SUGAR_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // 注册手持物品模型
        itemModelGenerator.register(Moditem.EXAMPLE_ITEM, Models.GENERATED);
        itemModelGenerator.register(Moditem.EXAMPLE_ITEM2, Models.GENERATED);
        itemModelGenerator.register(Moditem.SUPER_SUGAR, Models.GENERATED);
        itemModelGenerator.register(Moditem.CORN, Models.GENERATED);
        itemModelGenerator.register(Moditem.SUPER_COAL, Models.GENERATED);
    }
}