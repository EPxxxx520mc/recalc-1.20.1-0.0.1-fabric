package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class ModEnZhlangProvider extends FabricLanguageProvider{
    public ModEnZhlangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "示例物品");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "示例物品 2");    
        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "示例方块");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "示例方块 2");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "示例方块 3");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "示例方块 4");
        translationBuilder.add("itemGroup.recalc", "Recalc");
    }
}
