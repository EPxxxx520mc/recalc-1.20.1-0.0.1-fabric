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
        // 物品翻译
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "糖结晶");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "精制糖锭");    
        translationBuilder.add(Moditem.SUPER_SUGAR, "超级糖");
        translationBuilder.add(Moditem.CORN, "玉米");
        translationBuilder.add(Moditem.SUPER_COAL, "超级煤炭");
        
        // 方块翻译
        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "糖结晶块");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "精制糖块");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "糖矿石");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "压缩糖块");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "磨制糖块");
        
        // 物品组/其他翻译
        translationBuilder.add("itemGroup.recalc", "Recalc");
    }
}
