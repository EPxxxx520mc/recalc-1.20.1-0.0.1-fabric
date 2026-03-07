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
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "示例物品1");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "示例物品2");

        // 方块翻译
        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "示例方块1");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "示例方块2");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "示例方块3");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "示例方块4");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "示例方块5");
        
        // 物品组/其他翻译
        translationBuilder.add("itemGroup.recalc", "Recalc");

        translationBuilder.add(Moditem.SUPER_SUGAR, "超级糖");
        translationBuilder.add(Moditem.CORN, "玉米");
        translationBuilder.add(Moditem.SUPER_COAL, "超级煤炭");
        
        // 超级糖系列方块翻译
        translationBuilder.add(Modblock.SUPER_SUAR_ORE, "超级糖矿石");
        translationBuilder.add(Modblock.SUPER_SUGAR_BLOCK, "超级糖块");
        translationBuilder.add(Modblock.SUPER_SUGAR_STAIRS, "超级糖楼梯");
        translationBuilder.add(Modblock.SUPER_SUGAR_SLAB, "超级糖台阶");
        translationBuilder.add(Modblock.SUPER_SUGAR_BUTTON, "超级糖按钮");
        translationBuilder.add(Modblock.SUPER_SUGAR_PRESSURE_PLATE, "超级糖压力板");
        translationBuilder.add(Modblock.SUPER_SUGAR_FENCE, "超级糖栅栏");
        translationBuilder.add(Modblock.SUPER_SUGAR_FENCE_GATE, "超级糖栅栏门");
        translationBuilder.add(Modblock.SUPER_SUGAR_WALL, "超级糖墙");
        translationBuilder.add(Modblock.SUPER_SUGAR_DOOR, "超级糖门");
        translationBuilder.add(Modblock.SUPER_SUGAR_TRAPDOOR, "超级糖活板门");
        translationBuilder.add(Moditem.SUPER_SUGAR_SWORD, "超级糖剑");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE, "超级糖镐");
        translationBuilder.add(Moditem.SUPER_SUGAR_AXE, "超级糖斧头");
        translationBuilder.add(Moditem.SUPER_SUGAR_SHOVEL, "超级糖铲子");
        translationBuilder.add(Moditem.SUPER_SUGAR_HOE, "超级糖锄");


    }
}
