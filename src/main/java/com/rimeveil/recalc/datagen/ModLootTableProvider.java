package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
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

        addDrop(Modblock.EXAMPLE_BLOCK5 ,oreDrop(Modblock.EXAMPLE_BLOCK5, Moditem.EXAMPLE_ITEM2));
    }
}
