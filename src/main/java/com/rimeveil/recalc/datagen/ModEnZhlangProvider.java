package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModEnZhlangProvider extends FabricLanguageProvider {
    public ModEnZhlangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "示例物品1");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "示例物品2");
        translationBuilder.add(Moditem.SUPER_SUGAR, "超级糖");
        translationBuilder.add(Moditem.CORN, "玉米");
        translationBuilder.add(Moditem.SUPER_COAL, "超级煤炭");
        translationBuilder.add(Moditem.SUPER_SUGAR_SWORD, "超级糖剑");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE, "超级糖镐");
        translationBuilder.add(Moditem.SUPER_SUGAR_AXE, "超级糖斧头");
        translationBuilder.add(Moditem.SUPER_SUGAR_SHOVEL, "超级糖铲子");
        translationBuilder.add(Moditem.SUPER_SUGAR_HOE, "超级糖锄");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE_AXE, "超级糖镐斧");
        translationBuilder.add(Moditem.SUPER_SUGAR_HELMET, "超级糖头盔");
        translationBuilder.add(Moditem.SUPER_SUGAR_CHESTPLATE, "超级糖胸甲");
        translationBuilder.add(Moditem.SUPER_SUGAR_LEGGINGS, "超级糖裤子");
        translationBuilder.add(Moditem.SUPER_SUGAR_BOOTS, "超级糖靴子");
        translationBuilder.add(Moditem.SUPER_SUGAR_HORSE_ARMOR, "超级糖马铠");
        translationBuilder.add(Moditem.SUPER_SUGAR_3D_TOY_SWORD, "超级糖3D玩具剑");
        translationBuilder.add(Moditem.FICTIONAL_FRAME, "虚构框架");

        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "示例方块1");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "示例方块2");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "示例方块3");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "示例方块4");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "示例方块5");
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

        translationBuilder.add("item.recalc.pickaxe_axe.tooltip", "按下\u00A71\u00A7l\u00A7oShift\u00A7r查看更多信息");
        translationBuilder.add("item.recalc.pickaxe_axe.tooltip.shift", "这个物品可以同时作为镐子和斧子使用");
        translationBuilder.add("itemGroup.recalc", "Recalc");
        translationBuilder.add("key.recalc.toggle_battle_ui", "切换战斗 UI");
        translationBuilder.add("category.recalc", "Recalc");
        translationBuilder.add("ui.recalc.battle", "Recalc 战斗");
        translationBuilder.add("ui.recalc.settings", "Recalc 设置");
        translationBuilder.add("ui.recalc.back", "返回");
        translationBuilder.add("hud.recalc.settings", "设置");
        translationBuilder.add("hud.recalc.mana_title", "魔力详情");
        translationBuilder.add("hud.recalc.mana_current", "当前魔力：%s");
        translationBuilder.add("hud.recalc.mana_capacity", "魔力上限：%s");
        translationBuilder.add("hud.recalc.mana_charge", "充能比例：%s%%");
        translationBuilder.add("hud.recalc.mana_state", "当前状态：%s");
        translationBuilder.add("hud.recalc.mana_state.full", "充盈");
        translationBuilder.add("hud.recalc.mana_state.stable", "稳定");
        translationBuilder.add("hud.recalc.mana_state.low", "低魔力");
        translationBuilder.add("hud.recalc.player_title", "玩家详情");
        translationBuilder.add("hud.recalc.health", "生命值：%s / %s");
        translationBuilder.add("hud.recalc.chunk", "所在区块：%s, %s");
        translationBuilder.add("hud.recalc.target_block", "准星方块：%s");
        translationBuilder.add("hud.recalc.target_position", "方块坐标：%s, %s, %s");
        translationBuilder.add("hud.recalc.target_none", "准星方块：无");
        translationBuilder.add("message.recalc.frame_attached", "虚构框架已附着在你身上！");
        translationBuilder.add("message.recalc.frame_already_attached", "你已经附着了虚构框架！");
        translationBuilder.add("message.recalc.no_frame", "没有附着框架！请先使用虚构框架。");
        translationBuilder.add("message.recalc.wait_for_animation", "请等待动画完成！");
        translationBuilder.add("command.recalc.frame_removed", "虚构框架已移除！");
        translationBuilder.add("command.recalc.only_player", "该命令只能由玩家执行！");
    }
}
