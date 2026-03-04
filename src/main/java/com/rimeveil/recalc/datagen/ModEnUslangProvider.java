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
        // Item Translations
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "Sugar Crystal");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "Refined Sugar Ingot");
        translationBuilder.add(Moditem.SUPER_SUGAR, "Super Sugar");
        translationBuilder.add(Moditem.CORN, "Corn");
        
        // Block Translations
        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "Sugar Crystal Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "Refined Sugar Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "Sugar Ore");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "Compressed Sugar Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "Polished Sugar Block");
        
        // Item Group / Other Translations
        translationBuilder.add("itemGroup.recalc", "Recalc");
    }
}
