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
        translationBuilder.add(Modblock.SUPER_SUGAR_ORE, "超级糖矿石");
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
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE_AXE, "超级糖镐斧");
        translationBuilder.add("item.recalc.pickaxe_axe.tooltip.shift", "这个物品可以作为镐子以及斧子同时使用");
        translationBuilder.add("item.recalc.pickaxe_axe.tooltip", "按下§1§l§oShift§r§r§r查看更多信息");
        translationBuilder.add(Moditem.SUPER_SUGAR_HELMET, "超级糖头盔");
        translationBuilder.add(Moditem.SUPER_SUGAR_CHESTPLATE, "超级糖胸甲");
        translationBuilder.add(Moditem.SUPER_SUGAR_LEGGINGS, "超级糖裤子");
        translationBuilder.add(Moditem.SUPER_SUGAR_BOOTS, "超级糖靴子");
        translationBuilder.add(Moditem.SUPER_SUGAR_HORSE_ARMOR, "超级糖马铠");
        translationBuilder.add(Moditem.SUPER_SUGAR_3D_TOY_SWORD, "超级糖3D玩具剑");
        translationBuilder.add(Moditem.FICTIONAL_FRAME, "虚构框架");
        translationBuilder.add("key.recalc.toggle_battle_ui", "切换战斗UI");
        translationBuilder.add("category.recalc", "Recalc");

        //\u00A7 = § 
        //§0	黑色	\u00A70
        //§1	深蓝色	\u00A71
        //§2	深绿色	\u00A72
        //§3	湖蓝色	\u00A73
        //§4	深红色	\u00A74
        //§5	紫色	\u00A75
        //§6	金色	\u00A76
        //§7	灰色	\u00A77
        //§8	深灰色	\u00A78
        //§9	蓝色	\u00A79
        //§a	绿色	\u00A7a
        //§b	天蓝色	\u00A7b
        //§c	红色	\u00A7c
        //§d	粉红色	\u00A7d
        //§e	黄色	\u00A7e
        //§f	白色	\u00A7f
        //§k	随机字符	\u00A7k
        //§l	粗体	\u00A7l
        //§m	删除线	\u00A7m
        //§n	下划线	\u00A7n
        //§o	斜体	\u00A7o
        //§r	重置	\u00A7r
        //\n	换行	\n
        // Item Group / Other Translations

    }
}
