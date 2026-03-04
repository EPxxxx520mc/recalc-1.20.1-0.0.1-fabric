package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class ModEnUslangProvider extends FabricLanguageProvider{
    public ModEnUslangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "Example Item");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "Example Item 2");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "Example Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "Example Block 2");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "Example Block 3");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "Example Block 4");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "Example Block 5");
        translationBuilder.add("itemGroup.recalc", "Recalc");
    }
}
