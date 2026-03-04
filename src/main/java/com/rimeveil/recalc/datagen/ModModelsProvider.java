package com.rimeveil.recalc.datagen;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.client.ItemModelGenerator;
import com.rimeveil.recalc.block.Modblock;
import net.minecraft.data.client.BlockStateModelGenerator;

public class ModModelsProvider extends FabricModelProvider{
    public ModModelsProvider(FabricDataOutput Output) {
        super(Output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelProvider) {
        blockStateModelProvider.registerSimpleCubeAll(Modblock.EXAMPLE_BLOCK);
    }
}
